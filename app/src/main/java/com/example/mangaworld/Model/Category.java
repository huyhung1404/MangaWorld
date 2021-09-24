package com.example.mangaworld.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Category {
    @SerializedName("id")
    private long idCategory;
    @SerializedName("name")
    private String nameCategory;
    @SerializedName("listComic")
    private List<Manga> mangas;
}
