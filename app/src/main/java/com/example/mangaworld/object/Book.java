package com.example.mangaworld.object;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    private String idBook;
    private String resourceId;
    private int likeBook;
    private int viewBook;
    private String nameBook;
    private int numberChap;
    private String bookAuthor;
    private Boolean status;
    private List<String> listTagCategory;
    private String summaryBook;

    public Book(String idBook, String resourceId, String nameBook, int numberChap) {
        this.idBook = idBook;
        this.resourceId = resourceId;
        this.nameBook = nameBook;
        this.numberChap = numberChap;
    }

    public Book(String idBook, String resourceId, int likeBook, int viewBook, String nameBook, int numberChap, String bookAuthor, Boolean status, List<String> listTagCategory, String summaryBook) {
        this.idBook = idBook;
        this.resourceId = resourceId;
        this.likeBook = likeBook;
        this.viewBook = viewBook;
        this.nameBook = nameBook;
        this.numberChap = numberChap;
        this.bookAuthor = bookAuthor;
        this.status = status;
        this.listTagCategory = listTagCategory;
        this.summaryBook = summaryBook;
    }

    public String getIdBook() {
        return idBook;
    }

    public String getResourceId() {
        return resourceId;
    }

    public int getLikeBook() {
        return likeBook;
    }

    public int getViewBook() {
        return viewBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public int getNumberChap() {
        return numberChap;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public Boolean getStatus() {
        return status;
    }

    public List<String> getListTagCategory() {
        return listTagCategory;
    }

    public String getSummaryBook() {
        return summaryBook;
    }
}
