package com.example.mangaworld.api;

import com.example.mangaworld.object.Message;
import com.example.mangaworld.object.Password;
import com.example.mangaworld.object.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APILogin {
    //Login
    @POST("authentication")
    Call<User> postUser(@Body User user);

    //Sign up
    @POST("api/auth")
    Call<User> postUserSignUp(@Body User user);

    //change password
    @PUT("api/auth/change-password/{id}")
    Call<Message> changePassword(@Path("id") Long id, @Body Password password);

    //Send OTP
    @GET("api/auth/verify-email/{gmail}")
    Call<Message> sendOTP(@Path("gmail") String gmail);

    @POST("api/auth/verify-otp")
    Call<Message> checkOTP(@Body Password password);

    @POST("api/auth/reset-password")
    Call<Message> resetPassword(@Body Password password);

    @POST("api/auth/info/update/avatar")
    Call<User> postImage(@Header("Authorization") String token, @Body User user);
    @GET("api/auth/info/update")
    Call<User> changeInfo(@Header("Authorization") String token, @Query("fullName") String fullName,@Query("phone") String phone);
}
