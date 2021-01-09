/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhnd.dao;

import anhnd.db.DBUtils;
import anhnd.dto.BookDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author anhnd
 */
public class BookDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void closeConnection() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public ArrayList<BookDTO> getBooks() throws SQLException, ClassNotFoundException {

        ArrayList<BookDTO> books = null;
        BookDTO bookDTO = null;
        try {
            connection = DBUtils.getMyConnection();
            if (connection != null) {
                String sql = "Select bookID, bookName, author, publisher, publishedYear, forRent from Book";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                books = new ArrayList<>();
                while (resultSet.next()) {
                    String bookID = resultSet.getString("bookID");
                    String bookName = resultSet.getString("bookName");
                    String publisher = resultSet.getString("publisher");
                    String author = resultSet.getString("author");
                    int publishedYear = resultSet.getInt("publishedYear");
                    boolean forRent = resultSet.getBoolean("forRent");
                    bookDTO = new BookDTO(bookID, bookName, author, publisher, publishedYear, forRent);
                    books.add(bookDTO);
                }

            }

        } finally {
            closeConnection();
        }
        return books;
    }

    public boolean insertBook(BookDTO bookDTO) throws SQLException, ClassNotFoundException {
        boolean check = false;
        try {
            connection = DBUtils.getMyConnection();
            if (connection != null) {
                String sql = "Insert into Book(bookID, bookName, author, publisher, publishedYear, forRent) values(?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bookDTO.getBookID());
                preparedStatement.setString(2, bookDTO.getBookName());
                preparedStatement.setString(3, bookDTO.getAuthor());
                preparedStatement.setString(4, bookDTO.getPublisher());
                preparedStatement.setInt(5, bookDTO.getPublishedYear());
                preparedStatement.setBoolean(6, bookDTO.isForRent());
                check = preparedStatement.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }

        return check;
    }

    public boolean updateBook(BookDTO bookDTO) throws SQLException, ClassNotFoundException {
        boolean check = false;
        try {
            connection = DBUtils.getMyConnection();
            if (connection != null) {
                String sql = "Update Book set bookName = ?, author = ?, publisher = ?, publishedYear = ?, forRent = ? where bookID = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bookDTO.getBookName());
                preparedStatement.setString(2, bookDTO.getAuthor());
                preparedStatement.setString(3, bookDTO.getPublisher());
                preparedStatement.setInt(4, bookDTO.getPublishedYear());
                preparedStatement.setBoolean(5, bookDTO.isForRent());
                preparedStatement.setString(6, bookDTO.getBookID());
                check = preparedStatement.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }

        return check;
    }

    public BookDTO findBookByID(String bookID) throws SQLException, ClassNotFoundException {
        BookDTO bookDTO = null;
        try {
            connection = DBUtils.getMyConnection();
            if (connection != null) {
                String sql = "Select bookName, author, publisher, publishedYear, forRent from Book where bookID = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bookID);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String bookName = resultSet.getString("bookName");
                    String publisher = resultSet.getString("publisher");
                    String author = resultSet.getString("author");
                    int publishedYear = resultSet.getInt("publishedYear");
                    boolean forRent = resultSet.getBoolean("forRent");
                    bookDTO = new BookDTO(bookID, bookName, author, publisher, publishedYear, forRent);
                }
            }
        } finally {
            closeConnection();
        }

        return bookDTO;
    }
    
    public ArrayList<BookDTO> findBooksByLikeName(String searchKey) throws SQLException, ClassNotFoundException{
        ArrayList<BookDTO> books = null;
        BookDTO bookDTO = null;
        try {
            connection = DBUtils.getMyConnection();
            if (connection != null) {
                String sql = "Select bookID, bookName, author, publisher, publishedYear, forRent from Book where bookName like N'%"+ searchKey + "%'";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                books = new ArrayList<>();
                while (resultSet.next()) {
                    String bookID = resultSet.getString("bookID");
                    String bookName = resultSet.getString("bookName");
                    String publisher = resultSet.getString("publisher");
                    String author = resultSet.getString("author");
                    int publishedYear = resultSet.getInt("publishedYear");
                    boolean forRent = resultSet.getBoolean("forRent");
                    bookDTO = new BookDTO(bookID, bookName, author, publisher, publishedYear, forRent);
                    books.add(bookDTO);
                }
            }

        } finally {
            closeConnection();
        }
        return books;
    }
    
    public boolean deleteBook(String bookID) throws SQLException, ClassNotFoundException{
        boolean check = false;
        try {
            connection = DBUtils.getMyConnection();
            if(connection != null){
                String sql = "Delete from Book where bookID = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bookID);
                check = preparedStatement.executeUpdate() > 0;
            }
        } finally{
            closeConnection();
        }
        return check;
    }
}
