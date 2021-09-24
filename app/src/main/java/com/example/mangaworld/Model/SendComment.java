package com.example.mangaworld.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SendComment {
    private String content;
    private String username;
    private long comicId;
}
