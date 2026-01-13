package com.atila.model;

import com.atila.Enums.Role;

public class User {

    public Integer id;

    public String name;

    public String email;

    public String phone;

    public String user;

    public String role;

   

    public User() {
    }

    public User(String name, String email, String phone, String user, Integer id,String role) {

        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (phone == null) {
            throw new IllegalArgumentException("Phone cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.user = user;
        this.id = id;
        this.role=role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
     public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
