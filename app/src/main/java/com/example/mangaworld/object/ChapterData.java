package com.example.mangaworld.object;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChapterData implements Serializable {
    private String content;
    private List<String> linkImage;
}
