package com.example.mangaworld.Model.Community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageStatus {
    private Long id;
    private String fileName;
    private String fileType;
    private String fileKey;
    private String url;
}
