package com.example.mangaworld.Model.Community;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageCall {
    private List<Long> errorFiles;
    private List<Long> ids;
}
