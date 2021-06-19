package com.example.mangaworld.object;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Author implements Serializable {
    private String imageAuthor;
    private String nameAuthor;
    private int sumManga;
}
