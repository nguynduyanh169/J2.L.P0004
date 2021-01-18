/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhnd.controllers;

import anhnd.dto.BookDTO;
import anhnd.impls.repo.BookRepositoryImpl;
import anhnd.view.BookManageView;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author anhnd
 */
public class BookController {

    private BookRepositoryImpl bookRepository;
    private BookManageView bookManageView;
    private DefaultTableModel bookModel;
    private boolean isAddNewBook = true;
    private static final String BOOKID_REGEX = "^[a-zA-Z0-9 ]+$";

    public BookController(BookManageView main) {
        this.bookManageView = main;
    }

    public void init() {
        bookModel = (DefaultTableModel) bookManageView.getTblBook().getModel();
        bookManageView.getCbPublishedYear().removeAllItems();
        bookManageView.getCbSortByName().removeAllItems();
        bookManageView.getCbSortByName().addItem("Ascending");
        bookManageView.getCbSortByName().addItem("Descending");
        for (int i = Calendar.getInstance().get(Calendar.YEAR) - 1; i >= 1960; i--) {
            bookManageView.getCbPublishedYear().addItem(i + "");
        }
        bookManageView.getCbSortByName().addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sortNameAction(evt);
            }
        });
        bookManageView.getBtnAddNew().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewBook(evt);
            }
        });
        bookManageView.getBtnSave().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBook(evt);
            }
        });
        bookManageView.getTblBook().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseBookOnTblBook(evt);
            }
        });
        bookManageView.getBtnGetAllBook().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGetAllBook(evt);
            }
        });
        bookManageView.getBtnFindID().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findBookById(evt);
            }
        });
        bookManageView.getBtnSearchByName().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBook(evt);
            }
        });
        bookManageView.getBtnRemove().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBook(evt);
            }
        });
        getBooks();
        bookManageView.setVisible(true);
    }

    public void chooseBookOnTblBook(java.awt.event.MouseEvent evt) {
        try {
            isAddNewBook = false;
            int pos = bookManageView.getTblBook().getSelectedRow();
            String bookID = (String) bookManageView.getTblBook().getValueAt(pos, 0);
            bookRepository = new BookRepositoryImpl();
            BookDTO bookDTO = bookRepository.findBook(bookID);
            if (bookDTO == null) {
                JOptionPane.showMessageDialog(bookManageView, "Cannot view information of Book :" + bookID);
            } else {
                bookManageView.getTxtBookID().setText(bookDTO.getBookID());
                bookManageView.getTxtBookID().setEditable(false);
                bookManageView.getTxtBookName().setText(bookDTO.getBookName());
                bookManageView.getTxtAuthor().setText(bookDTO.getAuthor());
                bookManageView.getTxtPublisher().setText(bookDTO.getPublisher());
                bookManageView.getCbPublishedYear().setSelectedItem(bookDTO.getPublishedYear());
                bookManageView.getCheckBoxRent().setSelected(bookDTO.isForRent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewBook(java.awt.event.ActionEvent evt) {
        isAddNewBook = true;
        bookManageView.getTxtBookID().setText("");
        bookManageView.getTxtBookID().setEditable(true);
        bookManageView.getTxtBookName().setText("");
        bookManageView.getTxtPublisher().setText("");
        bookManageView.getTxtAuthor().setText("");
        bookManageView.getCbPublishedYear().setSelectedIndex(0);

    }

    public void buttonGetAllBook(java.awt.event.ActionEvent evt) {
        try {
            bookManageView.getTxtSearchName().setText("");
            bookManageView.getCbSortByName().setSelectedIndex(0);
            getBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveBook(java.awt.event.ActionEvent evt) {
        String bookID = bookManageView.getTxtBookID().getText().trim();
        String bookName = bookManageView.getTxtBookName().getText().trim();
        String author = bookManageView.getTxtAuthor().getText().trim();
        String publisher = bookManageView.getTxtPublisher().getText().trim();
        int publishedYear = Integer.valueOf(bookManageView.getCbPublishedYear().getSelectedItem().toString());
        boolean forRent = bookManageView.getCheckBoxRent().isSelected();
        String errorMessage = "";
        boolean invalid = false;
        if (bookID.length() > 10 || bookID.isEmpty() || !bookID.matches(BOOKID_REGEX)) {
            errorMessage += "\n BookID: max length is 10, not contains special characters";
            invalid = true;
        }
        if (bookName.isEmpty() || bookName.length() > 50) {
            errorMessage += "\n BookName: max length is 50";
            invalid = true;
        }
        if (author.isEmpty() || author.length() > 50) {
            errorMessage += "\n Author: max length is 50";
            invalid = true;
        }
        if (publisher.isEmpty() || publisher.length() > 50) {
            errorMessage += "\n Publisher: max length is 50";
            invalid = true;
        }
        if (invalid == true) {
            JOptionPane.showMessageDialog(bookManageView, errorMessage);
        } else {
            if (isAddNewBook) {
                try {
                    BookDTO bookDTO = new BookDTO(bookID, bookName, author, publisher, publishedYear, forRent);
                    bookRepository = new BookRepositoryImpl();
                    boolean check = bookRepository.insertBook(bookDTO);
                    if (check) {
                        getBooks();
                    } else {
                        JOptionPane.showMessageDialog(bookManageView, "Save book failed!");
                    }
                } catch (Exception e) {
                    boolean checkDuplicate = e.getMessage().contains("duplicate");
                    if (checkDuplicate) {
                        JOptionPane.showMessageDialog(bookManageView, "BookID has been exist!");
                    } else {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    BookDTO bookDTO = new BookDTO(bookID, bookName, author, publisher, publishedYear, forRent);
                    bookRepository = new BookRepositoryImpl();
                    boolean check = bookRepository.editBook(bookDTO);
                    if (check) {
                        getBooks();
                    } else {
                        JOptionPane.showMessageDialog(bookManageView, "Save book failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getBooks() {
        try {
            bookRepository = new BookRepositoryImpl();
            ArrayList<BookDTO> bookDTOs = bookRepository.getAllBooks();
            bookModel.setRowCount(0);
            for (BookDTO bookDTO : bookDTOs) {
                bookModel.addRow(bookDTO.toVector());
            }
            bookManageView.getTblBook().updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findBookById(java.awt.event.ActionEvent evt) {
        try {
            String bookID = bookManageView.getTxtBookID().getText();
            bookRepository = new BookRepositoryImpl();
            BookDTO bookDTO = bookRepository.findBook(bookID);
            if (bookDTO == null) {
                JOptionPane.showMessageDialog(bookManageView, "Cannot find book which has ID: " + bookID);
            } else {
                isAddNewBook = false;
                bookManageView.getTxtBookID().setText(bookDTO.getBookID());
                bookManageView.getTxtBookID().setEditable(false);
                bookManageView.getTxtBookName().setText(bookDTO.getBookName());
                bookManageView.getTxtAuthor().setText(bookDTO.getAuthor());
                bookManageView.getTxtPublisher().setText(bookDTO.getPublisher());
                bookManageView.getCbPublishedYear().setSelectedItem(String.valueOf(bookDTO.getPublishedYear()));
                bookManageView.getCheckBoxRent().setSelected(bookDTO.isForRent());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchBook(java.awt.event.ActionEvent evt) {
        try {
            String searchKey = bookManageView.getTxtSearchName().getText();
            bookRepository = new BookRepositoryImpl();
            List<BookDTO> booksByName = bookRepository.findBooksByLikeName(searchKey);
            if (booksByName.isEmpty()) {
                JOptionPane.showMessageDialog(bookManageView, "Cannot find any book with keywords: " + searchKey);
            } else {
                bookModel.setRowCount(0);
                for (BookDTO bookDTO : booksByName) {
                    bookModel.addRow(bookDTO.toVector());
                }
                bookManageView.getTblBook().updateUI();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(java.awt.event.ActionEvent evt) {
        int pos = bookManageView.getTblBook().getSelectedRow();
        if (pos != -1) {
            String bookID = (String) bookManageView.getTblBook().getValueAt(pos, 0);
            int confirm = JOptionPane.showConfirmDialog(bookManageView, "Are you sure to delete this book?", "Confirm delete book " + bookID, JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    bookRepository = new BookRepositoryImpl();
                    boolean check = bookRepository.deleteBook(bookID);
                    if (!check) {
                        JOptionPane.showMessageDialog(bookManageView, "Delete Failed!");
                        getBooks();
                    } else {
                        getBooks();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void sortNameAction(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selected = (String) evt.getItem();
            if (selected.equals("Ascending")) {
                ArrayList<BookDTO> books = new ArrayList<>();
                for (int i = 0; i < bookModel.getRowCount(); i++) {
                    String bookID = (String) bookManageView.getTblBook().getValueAt(i, 0);
                    String bookName = (String) bookManageView.getTblBook().getValueAt(i, 1);
                    String author = (String) bookManageView.getTblBook().getValueAt(i, 2);
                    String publisher = (String) bookManageView.getTblBook().getValueAt(i, 3);
                    String publishedYear = (String) bookManageView.getTblBook().getValueAt(i, 4).toString();
                    boolean forRent = (boolean) bookManageView.getTblBook().getValueAt(i, 5);
                    BookDTO bookDTO = new BookDTO(bookID, bookName, author, publisher, Integer.valueOf(publishedYear), forRent);
                    books.add(bookDTO);
                }
                sortAscendingByBookName(books);
            } else if (selected.equals("Descending")) {
                ArrayList<BookDTO> books = new ArrayList<>();
                for (int i = 0; i < bookModel.getRowCount(); i++) {
                    String bookID = (String) bookManageView.getTblBook().getValueAt(i, 0);
                    String bookName = (String) bookManageView.getTblBook().getValueAt(i, 1);
                    String author = (String) bookManageView.getTblBook().getValueAt(i, 2);
                    String publisher = (String) bookManageView.getTblBook().getValueAt(i, 3);
                    String publishedYear = (String) bookManageView.getTblBook().getValueAt(i, 4).toString();
                    boolean forRent = (boolean) bookManageView.getTblBook().getValueAt(i, 5);
                    BookDTO bookDTO = new BookDTO(bookID, bookName, author, publisher, Integer.valueOf(publishedYear), forRent);
                    books.add(bookDTO);
                }
                sortDescendingByBookName(books);
            }
        }
    }

    public void sortAscendingByBookName(ArrayList<BookDTO> books) {
        Collections.sort(books, new Comparator<BookDTO>() {
            @Override
            public int compare(BookDTO o1, BookDTO o2) {
                return o1.getBookName().compareTo(o2.getBookName());
            }

        });
        bookModel.setRowCount(0);
        for (BookDTO bookDTO : books) {
            bookModel.addRow(bookDTO.toVector());
        }
        bookManageView.getTblBook().updateUI();
    }

    public void sortDescendingByBookName(ArrayList<BookDTO> books) {
        Collections.sort(books, new Comparator<BookDTO>() {
            @Override
            public int compare(BookDTO o1, BookDTO o2) {
                return o2.getBookName().compareTo(o1.getBookName());
            }
        });
        bookModel.setRowCount(0);
        for (BookDTO bookDTO : books) {
            bookModel.addRow(bookDTO.toVector());
        }
        bookManageView.getTblBook().updateUI();
    }

}
