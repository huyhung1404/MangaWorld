package com.example.mangaworld.Model.Community;

import com.example.mangaworld.Model.Manga;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Status {
    private long id;
    private String creatorName;
    private String content;
    private Manga comicDto;
    private List<ImageStatus> media;
    private long numberOfLikePosts;
    private List<Groups> groupDTOS;
    private Date createdDate;
    private long likes;
    private boolean myLike;
    private long comments;
    private String mongoId;
}
