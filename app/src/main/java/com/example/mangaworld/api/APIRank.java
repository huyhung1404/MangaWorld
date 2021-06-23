package com.example.mangaworld.api;

import com.example.mangaworld.object.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIRank {
    @GET("api/comic/charts")
    Call<List<Manga>> dataRank(@Query("type") int type);
}
