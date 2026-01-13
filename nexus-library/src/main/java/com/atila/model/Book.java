package com.atila.model;

import com.atila.Enums.Categories;
import com.atila.Enums.StatusBook;

public class Book {
    public Integer id;

    public String title;

    public String publicationYear;

    public StatusBook status = StatusBook.AVAILABLE;

    public Integer library_id;

    public String author;

    public Categories category;


    public Book() {
    }

    public Book(Integer id, String title, String publicationYear, StatusBook status, Integer library_id,
            String author,Categories category) {

        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        if (publicationYear == null) {
            throw new IllegalArgumentException("Publication year cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        this.id = id;
        this.title = title;
        this.publicationYear = publicationYear;
        this.status = status;
        this.library_id = library_id;
        this.author = author;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public StatusBook getStatus() {
        return status;
    }

    public void setStatus(StatusBook status) {
        this.status = status;
    }

    public Integer getLibrary_id() {
        return library_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

     public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }
}
