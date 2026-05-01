package com.library.model;

public class Book {
    private int id;
    private String title, author, isbn;
    private int quantity, available;

    public Book() {}

    public Book(int id, String title, String author, String isbn, int quantity, int available) {
        this.id = id; this.title = title; this.author = author;
        this.isbn = isbn; this.quantity = quantity; this.available = available;
    }

    public int getId()           { return id; }
    public String getTitle()     { return title; }
    public String getAuthor()    { return author; }
    public String getIsbn()      { return isbn; }
    public int getQuantity()     { return quantity; }
    public int getAvailable()    { return available; }

    public void setId(int id)              { this.id = id; }
    public void setTitle(String title)     { this.title = title; }
    public void setAuthor(String author)   { this.author = author; }
    public void setIsbn(String isbn)       { this.isbn = isbn; }
    public void setQuantity(int quantity)  { this.quantity = quantity; }
    public void setAvailable(int available){ this.available = available; }

    @Override
    public String toString() { return title + " by " + author; }
}