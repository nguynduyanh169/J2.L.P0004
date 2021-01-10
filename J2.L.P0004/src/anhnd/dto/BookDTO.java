/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anhnd.dto;

import java.util.Vector;

/**
 *
 * @author anhnd
 */
public class BookDTO {

    private String bookID;
    private String bookName;
    private String author;
    private String publisher;
    private int publishedYear;
    private boolean forRent;

    public BookDTO(String bookID, String bookName, String author, String publisher, int publishedYear, boolean forRent) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.forRent = forRent;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public boolean isForRent() {
        return forRent;
    }

    public void setForRent(boolean forRent) {
        this.forRent = forRent;
    }

    public Vector toVector() {
        Vector v = new Vector();
        v.add(this.bookID);
        v.add(this.bookName);
        v.add(this.author);
        v.add(this.publisher);
        v.add(this.publishedYear);
        v.add(this.forRent);

        return v;
    }
}
