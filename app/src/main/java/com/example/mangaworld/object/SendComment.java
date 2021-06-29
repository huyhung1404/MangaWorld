package com.example.mangaworld.object;

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
