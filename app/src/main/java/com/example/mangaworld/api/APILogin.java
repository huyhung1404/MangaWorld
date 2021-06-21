package com.example.mangaworld.api;

import com.example.mangaworld.object.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APILogin {
    //Login
    @POST("authentication")
    Call<User> postUser(@Body User user);
    //Sign up
    @POST("api/auth")
    Call<User> postUserSignUp(@Body User user);
}
