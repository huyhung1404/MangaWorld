package com.example.mangaworld.Model.Community;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentValues {
    private Long userId;
    private UserForum userDTO;
    private String content;
    private Date createdDate;
    private List<CommentValues> commentValues;

    public CommentValues(String content) {
        this.content = content;
    }
}
