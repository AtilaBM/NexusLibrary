package com.atila.repository;

import java.sql.Connection;

import com.atila.config.JdbcConnection;

public class LoanRepository {
    Connection conn = JdbcConnection.getConnection();

    public void loanBook(Integer id){
        
    }
}
