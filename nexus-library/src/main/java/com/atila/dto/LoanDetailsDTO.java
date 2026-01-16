package com.atila.dto;

import java.time.LocalDateTime;

public class LoanDetailsDTO {
    private String memberName;
    private String bookTitle;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private String statusLoan;

    public LoanDetailsDTO() {
    }

    public LoanDetailsDTO(String memberName, String bookTitle, LocalDateTime loanDate,
            LocalDateTime returnDate, String statusLoan) {
        this.memberName = memberName;
        this.bookTitle = bookTitle;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.statusLoan = statusLoan;
    }

    // Getters e Setters
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatusLoan() {
        return statusLoan;
    }

    public void setStatusLoan(String statusLoan) {
        this.statusLoan = statusLoan;
    }

    @Override
    public String toString() {
        return memberName + " - " + bookTitle + " (Loan date: " +
                loanDate.toLocalDate() + ", Due date: " + returnDate.toLocalDate() +
                ") - " + statusLoan;
    }
}