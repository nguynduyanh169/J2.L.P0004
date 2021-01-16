/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhnd.interfaces.dao;

import anhnd.dto.BookDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author anhnd
 */
public interface IBookDAO {
    
    public boolean create(BookDTO bookDTO) throws SQLException, ClassNotFoundException;
    
    public ArrayList<BookDTO> getAll() throws SQLException, ClassNotFoundException ;
    
    public BookDTO findBookByID(String bookID) throws SQLException, ClassNotFoundException;
    
    public boolean update(BookDTO bookDTO) throws SQLException, ClassNotFoundException;
    
    public boolean delete(String bookId) throws SQLException, ClassNotFoundException;
    
    public ArrayList<BookDTO> findBooksByLikeName(String keywords) throws SQLException, ClassNotFoundException;
}
