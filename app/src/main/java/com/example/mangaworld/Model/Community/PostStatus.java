package com.example.mangaworld.Model.Community;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostStatus {
    private String content;
    private Long comicId;
    private List<Long> imageList;
    private List<Long> groups;
}
