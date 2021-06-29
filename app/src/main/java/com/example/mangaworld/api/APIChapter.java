package com.example.mangaworld.api;


import com.example.mangaworld.object.Chapter;
import com.example.mangaworld.object.Comment;
import com.example.mangaworld.object.SendComment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIChapter {
    //Nhận danh sách chapter từ API
    @GET("api/chapter")
    Call<Chapter> dataChapFragment(@Query("comic-id") Long id);
    //Nhận danh sách hình ảnh của chapter từ API
    @GET("api/chapter/get-only-image")
    Call<Chapter> getChapData(@Query("comic-id") Long id, @Query("index") Long index);
    //Nhận comment
    @GET("api/comment/get")
    Call<List<Comment>> getCommentByID(@Query("id") Long id);
    //Gửi comment
    @POST("api/comment/")
    Call<Comment> sendComment(@Header("Authorization")String token,@Body SendComment sendComment);
}
