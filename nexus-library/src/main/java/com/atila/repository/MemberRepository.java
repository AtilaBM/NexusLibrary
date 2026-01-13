package com.atila.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;

import com.atila.config.JdbcConnection;
import com.atila.model.*;;

public class MemberRepository {

    private Connection conn = JdbcConnection.getConnection();

    public void createMember(String name, String email, String phone, String statusMember, String user,
            String password) {
        String query = ("INSERT INTO member(name,email,phone,status_member,library_id,user_name,password_hash) VALUES(?,?,?,?,?,?,?);");

        String nameBd = name;
        String emailBd = email;
        String phoneBd = phone;
        String statusBd = statusMember;
        String userBd = user;
        String passwordBd = password;
        Integer libraryIdBd = 1;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nameBd);
            ps.setString(2, emailBd);
            ps.setString(3, phoneBd);
            ps.setString(4, statusBd);
            ps.setInt(5, libraryIdBd);
            ps.setString(6, userBd);
            ps.setString(7, passwordBd);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean verifyUser(String user) throws SQLException {
        String query = "SELECT 1 FROM member WHERE user_name = ?";
        String userBd = user;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userBd);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;

        }

    }

    public String getPassword(String user) throws SQLException {
        String query = ("SELECT password_hash FROM member WHERE user_name = ?;");
        String userBd = user;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userBd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password_hash");
                }
                return null;
            }
        }
    }

    public Integer getId(String user) throws SQLException {
        String query = ("SELECT id FROM member WHERE user_name = ?;");
        String userDb = user;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userDb);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                return null;
            }
        }
    }

    public User seeUserInfo(Integer id) throws SQLException {

        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        String query = ("SELECT name,email,phone,user_name FROM member WHERE id = ?;");
        Integer idBd = id;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idBd);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String userName = rs.getString("user_name");

                    return new User(name, email, phone, userName, id);

                } else {

                    return null;
                }
            }
        }
    }
}