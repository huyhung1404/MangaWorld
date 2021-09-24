package com.example.mangaworld.Model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ListTagCategory {
    @SerializedName(value="idCategory", alternate={"categoryId", "id"})
    private long idCategory;
    @SerializedName(value="nameCategory", alternate={"categoryDTOList", "name"})
    private String nameCategory;
}
