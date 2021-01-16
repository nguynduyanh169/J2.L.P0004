/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhnd.impls.repo;

import anhnd.dto.BookDTO;
import anhnd.impls.dao.JDBCBookDAOImpl;
import anhnd.intefaces.repo.IBookRepository;
import anhnd.interfaces.dao.IBookDAO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author anhnd
 */
public class BookRepositoryImpl implements IBookRepository{
    
    private IBookDAO bookDAO;

    @Override
    public ArrayList<BookDTO> getAllBooks() throws SQLException, ClassNotFoundException {
        bookDAO = new JDBCBookDAOImpl();
        return bookDAO.getAll();
    }

    @Override
    public boolean insertBook(BookDTO bookDTO) throws SQLException, ClassNotFoundException {
        bookDAO = new JDBCBookDAOImpl();
        return bookDAO.create(bookDTO);
    }

    @Override
    public BookDTO findBook(String bookId) throws SQLException, ClassNotFoundException {
        bookDAO = new JDBCBookDAOImpl();
        return bookDAO.findBookByID(bookId);
    }

    @Override
    public boolean editBook(BookDTO bookDTO) throws SQLException, ClassNotFoundException {
        bookDAO = new JDBCBookDAOImpl();
        return bookDAO.update(bookDTO);
    }

    @Override
    public boolean deleteBook(String bookId) throws SQLException, ClassNotFoundException {
        bookDAO = new JDBCBookDAOImpl();
        return bookDAO.delete(bookId);
    }

    @Override
    public ArrayList<BookDTO> findBooksByLikeName(String keywords) throws SQLException, ClassNotFoundException {
        bookDAO = new JDBCBookDAOImpl();
        return bookDAO.findBooksByLikeName(keywords);
    }
    
}
