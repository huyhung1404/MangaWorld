package com.example.mangaworld.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
    private String resourceAvatar;
    private String nameComment;
    private String commentContent;
}
