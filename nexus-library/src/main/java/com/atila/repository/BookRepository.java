package com.atila.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.atila.Enums.Categories;
import com.atila.Enums.StatusBook;
import com.atila.config.JdbcConnection;
import com.atila.services.LibraryService;

public class BookRepository {
    Connection conn = JdbcConnection.getConnection();

    public void createBook(String title, Integer publicationYear, String status, String author,
            String category) {

        String query = "INSERT INTO book(title,publication_year,status_book,library_id,author,categories) VALUES(?,?,?,?,?,?);";

        String titleBd = title;
        Integer publicationYearBd = publicationYear;
        String statusBd = status;
        String authorBd = author;
        String categoryBd = category;
        Integer libraryIdBd = 1;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, titleBd);
            ps.setInt(2, publicationYearBd);
            ps.setString(3, statusBd);
            ps.setInt(4, libraryIdBd);
            ps.setString(5, authorBd);
            ps.setString(6, categoryBd);
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<String> allBooks() {
        String query = ("SELECT title FROM book WHERE status_book = 'AVAILABLE' ORDER BY title;");
        List<String> books = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                books.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return books;
    }

    public List<String> searchBooks(String bookName) {
        String query = ("SELECT title FROM book WHERE title ILIKE ? AND status_book = 'AVAILABLE' ORDER BY title;");
        String titleDb = bookName;
        List<String> books = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + titleDb + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(rs.getString("title"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return books;
    }

    public Integer getBookId(String bookName)throws SQLException{
        String query = ("SELECT id FROM book WHERE title = ?;");

        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, bookName);
            
            try(ResultSet rs = ps.executeQuery()){
             if (rs.next()) {
                Integer bookId = rs.getInt("id");
                return bookId;
             }else{
                return null;
             }   
            }
                   
                
            
        }
    }

   public void toggleBookStatus(Integer bookId) {
    String query = "UPDATE book SET status_book = CASE " +
                 "WHEN status_book = 'AVAILABLE' THEN 'BORROWED' " +
                 "WHEN status_book = 'BORROWED' THEN 'AVAILABLE' " +
                 "END WHERE id = ?";
    
    try (PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, bookId);
        ps.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Error toggling book status: " + e.getMessage());
    }
}
}
