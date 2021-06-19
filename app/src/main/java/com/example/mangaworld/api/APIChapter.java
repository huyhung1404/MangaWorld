package com.example.mangaworld.api;

import com.example.mangaworld.object.Chapter;
import com.example.mangaworld.object.ChapterData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIChapter {
    //Nhận danh sách chapter từ API
    @GET("api/chapter")
    Call<Chapter> dataChapFragment(@Query("comic-id") Long id);

    //Nhận danh sách hình ảnh của chapter từ API
    @GET("api/chapter/get-only-image")
    Call<ChapterData> getChapData(@Query("comic-id") Long id, @Query("index") Long index);
}
