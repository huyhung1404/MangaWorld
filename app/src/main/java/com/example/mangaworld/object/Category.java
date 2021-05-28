package com.example.mangaworld.object;

import java.util.List;

public class Category {
    private String nameCategory;
    private List<Book> books;

    public Category(String nameCategory, List<Book> books) {
        this.nameCategory = nameCategory;
        this.books = books;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public List<Book> getBooks() {
        return books;
    }

}
