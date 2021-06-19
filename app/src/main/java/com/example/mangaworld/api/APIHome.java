package com.example.mangaworld.api;

import com.example.mangaworld.object.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIHome {
    //Get data danh sách category vào home
    @GET("api/category")
    Call<List<Category>> dataHomeFragment();
    //Get danh sách truyện theo category
    @GET("api/category/get")
    Call<Category> dataCategoryFragment(@Query("id")Long id);
}
