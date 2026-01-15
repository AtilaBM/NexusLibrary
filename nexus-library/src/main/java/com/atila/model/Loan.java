package com.atila.model;

import java.util.Date;

import com.atila.Enums.StatusLoan;

public class Loan {
    public Integer id;

    public Date loanDate;

    public Date dueDate;

    public Date returnDate;

    public StatusLoan status;

    public Integer member_id;

    public Integer book_id;

    public Loan() {
    }

    public Loan(Integer id, Date loanDate, Date dueDate, Date returnDate, StatusLoan status, Integer member_id,
            Integer book_id) {

        if (loanDate == null) {
            throw new IllegalArgumentException("Loan date cannot be null");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        this.id = id;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.member_id = member_id;
        this.book_id = book_id;
    }

    public Integer getId() {
        return id;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public StatusLoan getStatus() {
        return status;
    }

    public void setStatus(StatusLoan status) {
        this.status = status;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

}
