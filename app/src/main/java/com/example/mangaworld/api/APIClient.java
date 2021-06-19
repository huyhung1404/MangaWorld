package com.example.mangaworld.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String URL = "http://192.168.1.7:8081/";
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    private static Retrofit getRetrofit(){
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public static APIHome getAPIHome(){
        return getRetrofit().create(APIHome.class);
    }
    public static APIChapter getAPIChapter(){
        return getRetrofit().create(APIChapter.class);
    }
}
