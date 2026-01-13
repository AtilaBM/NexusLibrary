package com.atila.repository;

import java.sql.Statement;

import javax.print.DocFlavor.STRING;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

import com.atila.config.JdbcConnection;

public class LibraryRepository {
    private Connection conn = JdbcConnection.getConnection();

    public void createTables() {
        String query = ("CREATE TABLE IF NOT EXISTS Library(id SERIAL PRIMARY KEY,name VARCHAR(100) NOT NULL,address VARCHAR(100) NOT NULL,phone VARCHAR(20) NOT NULL,email VARCHAR(50) NOT NULL);"
                + "CREATE TABLE IF NOT EXISTS book(id SERIAL PRIMARY KEY,title VARCHAR(100) NOT NULL,publication_year VARCHAR(100) NOT NULL,status_book VARCHAR(20) NOT NULL,library_id INTEGER REFERENCES library(id),author VARCHAR(100) NOT NULL,categories VARCHAR(20) NOT NULL);"
                +
                "CREATE TABLE IF NOT EXISTS member(id SERIAL PRIMARY KEY,name VARCHAR(100) NOT NULL,email VARCHAR(100) NOT NULL,phone VARCHAR(20) NOT NULL,status_member VARCHAR(20) NOT NULL,library_id INTEGER REFERENCES library(id),user_name VARCHAR(30) NOT NULL UNIQUE, password_hash VARCHAR(255) NOT NULL);"
                + "CREATE TABLE IF NOT EXISTS loan(id SERIAL PRIMARY KEY,loan_date DATE NOT NULL,due_date Date NOT NULL,return_date Date NOT NULL,status_loan VARCHAR(20) NOT NULL,member_id INTEGER REFERENCES member(id),book_id INTEGER REFERENCES book(id));");

        try (Statement st = conn.createStatement()) {
            st.execute(query);
            System.out.println("Tables Criated!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void insertLibraryInfo(){
        String query = ("INSERT INTO library(name,address,phone,email) VALUES ('Nexus Library','QNM 12 VIA NM 12A','(61) 99554-5675','nexuslibrary@gmail.com');");

        try(Statement st = conn.createStatement()) {
            st.execute(query);
            System.out.println("Inserted library infomation");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
