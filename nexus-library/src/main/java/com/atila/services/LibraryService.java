package com.atila.services;

import com.atila.repository.BookRepository;
import com.atila.repository.MemberRepository;

import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.atila.Enums.Categories;
import com.atila.Enums.StatusBook;
import com.atila.model.*;;

public class LibraryService {
    private MemberRepository mrp = new MemberRepository();
    private BookRepository br = new BookRepository();

    private Member mb = new Member();
    private Book book = new Book();

    public void createMember(String name, String email, String phone, String user, String password) {
        String status = mb.getStatus().name();

        if (user == null || password == null) {
            throw new IllegalArgumentException("User or password cannot be null");
        }
        if (name == null || email == null || phone == null) {
            throw new IllegalArgumentException("Error");
        }
        try {
            if (mrp.verifyUser(user)) {
                throw new IllegalArgumentException("User already exists");
            }
            mrp.createMember(name, email, phone, status, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public void createBook(String title, Integer publicationYear, String author, Categories category) {
        if (title == null || publicationYear == null || author == null || category == null) {
            throw new IllegalArgumentException("Error: null argument!");
        }

        String categoryBd = category.name();
        String status = book.getStatus().name();

        br.createBook(title, publicationYear, status, author, categoryBd);
    }

    public boolean verifyPassword(String user, String password) {
        try {
            String pwHash = mrp.getPassword(user);
            boolean valid = BCrypt.checkpw(password, pwHash);
            return valid;
        } catch (SQLException e) {
            return false;
        }
    }

    public Integer login(String user, String password) throws SQLException {
        if (verifyPassword(user, password)) {
            try {
                return mrp.getId(user);
            } catch (SQLException e) {
                System.out.printf("Login error" + "%s", e);
            }
        }
        return null;
    }

    public User userInfo(Integer id) {
        try {
            User user = mrp.seeUserInfo(id);
            return user;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
