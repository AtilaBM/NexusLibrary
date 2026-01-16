package com.atila.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.atila.config.JdbcConnection;
import com.atila.dto.LoanDetailsDTO;

public class LoanRepository {
    Connection conn = JdbcConnection.getConnection();

    public void loanBook(LocalDateTime loanDate, LocalDateTime dueDate, String statusLoan, Integer memberId,
            Integer bookId) {
        String query = ("INSERT INTO loan(loan_date,due_date,status_loan,member_id,book_id) VALUES(?,?,?,?,?);");

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(loanDate.toLocalDate()));
            ps.setDate(2, Date.valueOf(dueDate.toLocalDate()));
            ps.setString(3, statusLoan);
            ps.setInt(4, memberId);
            ps.setInt(5, bookId);
            ps.execute();
        } catch (Exception e) {
            System.out.println("Errot to loan book " + e);
        }
    }

    public List<LoanDetailsDTO> seeAllLoans() throws SQLException {
        String query = ("SELECT member.name,book.title,loan.loan_date,due_date,loan.status_loan FROM loan LEFT JOIN member ON loan.member_id = member.id  LEFT JOIN book ON loan.book_id = book.id;");

        List<LoanDetailsDTO> listLoanDetails = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    String name = rs.getString("name");
                    String title = rs.getString("title");
                    LocalDateTime loanDate = rs.getTimestamp("loan_date").toLocalDateTime();
                    LocalDateTime dueDate = rs.getTimestamp("due_date").toLocalDateTime();
                    String statusLoan = rs.getString("status_loan");

                    LoanDetailsDTO loanDetailsDTO = new LoanDetailsDTO(name, title, loanDate, dueDate, statusLoan);

                    listLoanDetails.add(loanDetailsDTO);
                }
            }
            return listLoanDetails;
        }
    }

    public List<LoanDetailsDTO> seeUserLoans(Integer memberId) throws SQLException {
        String query = ("SELECT member.name,book.title,loan.loan_date,due_date,loan.status_loan FROM loan LEFT JOIN member ON loan.member_id = member.id  LEFT JOIN book ON loan.book_id = book.id WHERE member.id = ? and status_loan = 'OPEN';");

        List<LoanDetailsDTO> listLoanDetails = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    String name = rs.getString("name");
                    String title = rs.getString("title");
                    LocalDateTime loanDate = rs.getTimestamp("loan_date").toLocalDateTime();
                    LocalDateTime dueDate = rs.getTimestamp("due_date").toLocalDateTime();
                    String statusLoan = rs.getString("status_loan");

                    LoanDetailsDTO loanDetailsDTO = new LoanDetailsDTO(name, title, loanDate, dueDate, statusLoan);

                    listLoanDetails.add(loanDetailsDTO);
                }
            }
            return listLoanDetails;
        }
    }

    public void returnBook(LocalDateTime returnDate, Integer memberId,Integer bookId) {
        String query = ("UPDATE loan SET status_loan = 'RETURNED', return_date = ? WHERE member_id = ? AND book_id = ?;");

        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setDate(1, Date.valueOf(returnDate.toLocalDate()));
            ps.setInt(2,memberId);
            ps.setInt(3,bookId);
            ps.execute();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
