package com.example.mangaworld.Model.Community;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CommentValues {
    private long userId;
    private UserForum userDTO;
    private String content;
    private Date createdDate;
    private List<CommentValues> commentValues;

    public CommentValues(String content) {
        this.content = content;
    }
}
