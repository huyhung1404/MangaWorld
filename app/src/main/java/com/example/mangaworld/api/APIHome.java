package com.example.mangaworld.api;

import com.example.mangaworld.object.Author;
import com.example.mangaworld.object.Category;
import com.example.mangaworld.object.ListTagCategory;
import com.example.mangaworld.object.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIHome {
    //Get data danh sách category vào home
    @GET("api/comic/home")
    Call<List<Category>> dataHomeFragment();
    //Get danh sách truyện theo category
    @GET("api/category/get")
    Call<Category> dataCategoryFragment(@Query("id")Long id);
    //ViewMore
    @GET("api/comic/home/see-more")
    Call<List<Manga>> dataViewMore(@Query("type")String type);
    //Get all manga
    @GET("api/comic")
    Call<List<Manga>> getAllManga();
    //Get author
    @GET("api/author")
    Call<List<Author>> getAllAuthor();
    @GET("api/category/simple")
    Call<List<ListTagCategory>> getAllCategory();
}
