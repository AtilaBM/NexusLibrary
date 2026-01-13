package com.atila.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.atila.Enums.Categories;
import com.atila.Enums.StatusBook;
import com.atila.config.JdbcConnection;

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
}
