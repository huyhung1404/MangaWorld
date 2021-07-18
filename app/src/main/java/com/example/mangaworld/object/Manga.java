package com.example.mangaworld.object;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Manga implements Serializable,Comparable {
    @SerializedName("id")
    private final long idManga;
    @SerializedName("name")
    private final String nameManga;
    @SerializedName("avatarImage")
    private final String resourceId;
    @SerializedName("view")
    private int viewManga;
    @SerializedName("likes")
    private int likeManga;
    @SerializedName("numberChapter")
    private final int numberChap;
    @SerializedName("authorComic")
    private String mangaAuthor;
    @SerializedName("enabled")
    private Boolean status;
    @SerializedName("listCate")
    private List<ListTagCategory> listTagCategory;
    @SerializedName("description")
    private String summaryManga;
    private String readingIndex;

    @Override
    public int compareTo(Object o) {
        Manga manga = (Manga) o;
        if (manga.idManga == this.idManga){
            return 0;

        }
        return 1;
    }
}
