package com.example.mangaworld.object;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ListTagCategory {
    @SerializedName("categoryId")
    private long idCategory;
    @SerializedName("categoryDTOList")
    private String nameCategory;
}
