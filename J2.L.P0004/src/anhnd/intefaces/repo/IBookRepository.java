/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhnd.intefaces.repo;

import anhnd.dto.BookDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author anhnd
 */
public interface IBookRepository {
    public ArrayList<BookDTO> getAllBooks() throws SQLException, ClassNotFoundException;
    
    public boolean insertBook(BookDTO bookDTO) throws SQLException, ClassNotFoundException;
    
    public BookDTO findBook(String bookId) throws SQLException, ClassNotFoundException;
    
    public boolean editBook(BookDTO bookDTO) throws SQLException, ClassNotFoundException;
    
    public boolean deleteBook(String bookId) throws SQLException, ClassNotFoundException;
    
    public ArrayList<BookDTO> findBooksByLikeName(String keywords) throws SQLException, ClassNotFoundException;
}
