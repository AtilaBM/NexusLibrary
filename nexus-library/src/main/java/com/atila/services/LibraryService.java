package com.atila.services;

import com.atila.repository.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;

import com.atila.Enums.*;
import com.atila.dto.LoanDetailsDTO;
import com.atila.model.*;;

public class LibraryService {
    public List<String> books = new ArrayList<>();

    private Member member;
    private Book book;
    private Loan loan;
    private BookRepository bookRepository;
    private MemberRepository memberRepository;
    private LoanRepository loanRepository;

    public LibraryService() {
        this.bookRepository = new BookRepository();
        this.memberRepository = new MemberRepository();
        this.loanRepository = new LoanRepository();
        this.member = new Member();
        this.book = new Book();
        this.loan = new Loan();
    }

    public void createMember(String name, String email, String phone, String user, String password) {
        String status = member.getStatus().name();

        if (user == null || password == null) {
            throw new IllegalArgumentException("User or password cannot be null");
        }
        if (name == null || email == null || phone == null) {
            throw new IllegalArgumentException("Error");
        }
        try {
            if (memberRepository.verifyUser(user)) {
                throw new IllegalArgumentException("User already exists");
            }
            memberRepository.createMember(name, email, phone, status, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public void createEmployee(String name, String email, String phone, String user, String password) {
        String status = member.getStatus().name();

        if (user == null || password == null) {
            throw new IllegalArgumentException("User or password cannot be null");
        }
        if (name == null || email == null || phone == null) {
            throw new IllegalArgumentException("Error");
        }
        try {
            if (memberRepository.verifyUser(user)) {
                throw new IllegalArgumentException("User already exists");
            }
            memberRepository.createEmployee(name, email, phone, status, user, password);
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

        bookRepository.createBook(title, publicationYear, status, author, categoryBd);

    }

    public boolean verifyPassword(String user, String password) {
        try {
            String pwHash = memberRepository.getPassword(user);
            boolean valid = BCrypt.checkpw(password, pwHash);
            return valid;
        } catch (SQLException e) {
            return false;
        }
    }

    public Integer login(String user, String password) throws SQLException {
        if (verifyPassword(user, password)) {
            try {
                return memberRepository.getId(user);
            } catch (SQLException e) {
                System.out.printf("Login error" + "%s", e);
            }
        }
        return null;
    }

    public User userInfo(Integer id) {
        try {
            User user = memberRepository.seeUserInfo(id);
            return user;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<String> searchBooks(String nameBook) {
        return bookRepository.searchBooks(nameBook);
    }

    public List<String> allBooks() {
        return bookRepository.allBooks();
    }

    public void loanBook(LocalDateTime loanDate, String bookName, Integer memberId) {
        try {

            if (loanDate == null) {
                throw new IllegalArgumentException("Loan date cannot be null");
            }

            if (bookName == null) {
                throw new IllegalArgumentException("Book name cannot be null");
            }

            if (memberId == null) {
                throw new IllegalArgumentException("Member ID cannot be null");
            }

            Integer bookId = bookRepository.getBookId(bookName);

            if (bookId == null) {
                throw new IllegalArgumentException("Book not found");
            }

            LocalDateTime returnDate = loanDate.plusDays(12);
            loan.setStatus(StatusLoan.OPEN);
            String statusLoan = loan.getStatus().name();

            loanRepository.loanBook(loanDate, returnDate, statusLoan, memberId, bookId);
            bookRepository.toggleBookStatus(bookId);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<LoanDetailsDTO> seeAllLoans() throws SQLException{
        return loanRepository.seeAllLoans();
    }

    public List<LoanDetailsDTO> seeUserLoans(Integer memberId) throws SQLException{
        return loanRepository.seeUserLoans(memberId);
    }
}
