package com.example.mangaworld.Model.Community;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentStatus {
    private String id;
    private Long postId;
    private List<CommentValues> commentValues;
}
