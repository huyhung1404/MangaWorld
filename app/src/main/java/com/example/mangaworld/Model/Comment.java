package com.example.mangaworld.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
//    private long id;
//    private long userId;
//    private long comicId;
    private String content;
    private String avatarImage;
//    private String username;
    private String fullName;
}
