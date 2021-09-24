package com.example.mangaworld.API;

import com.example.mangaworld.Model.Community.CommentStatus;
import com.example.mangaworld.Model.Community.CreateGroup;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.Model.Community.GroupsCallBack;
import com.example.mangaworld.Model.Community.ImageCall;
import com.example.mangaworld.Model.Community.ImageSend;
import com.example.mangaworld.Model.Community.News;
import com.example.mangaworld.Model.Community.PostStatus;
import com.example.mangaworld.Model.Community.Status;
import com.example.mangaworld.Model.Community.UserCallBack;
import com.example.mangaworld.Model.Message;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICommunity {
    @GET("api/posts/new-feed")
    Call<News> getDataNews(@Header("Authorization") String token, @Query("page") Long page, @Query("size") Long size);

    @GET("api/posts/users")
    Call<News> getMyPost(@Header("Authorization") String token, @Query("page") Long page, @Query("size") Long size);

    @GET("api/posts/featured-posts")
    Call<News> getFeaturedPosts(@Header("Authorization") String token, @Query("page") Long page, @Query("size") Long size);

    @GET("api/posts/group/{id}")
    Call<News> getPostInGroup(@Header("Authorization") String token, @Path("id") Long id, @Query("page") Long page, @Query("size") Long size);

    @GET("api/posts/{id}/draft")
    Call<News> getPostDraftInGroup(@Header("Authorization") String token, @Path("id") Long id, @Query("page") Long page, @Query("size") Long size);

    @POST("api/attach/base64/upload")
    Call<ImageCall> postImageStatusBase64(@Header("Authorization") String token, @Body List<ImageSend> imageSends);

    @Multipart
    @POST("api/attach/upload")
    Call<ImageCall> postImageStatus(@Header("Authorization") String token, @Part MultipartBody.Part file);

    @POST("api/posts")
    Call<Status> postStatus(@Header("Authorization") String token, @Body PostStatus postStatus);

    @GET("api/groups/user")
    Call<GroupsCallBack> getGroupsJoined(@Header("Authorization") String token, @Query("page") Long page, @Query("size") Long size);

    @GET("api/groups/{id}")
    Call<Groups> getGroupByID(@Header("Authorization") String token, @Path("id") Long id);

    @GET("api/comment-post")
    Call<CommentStatus> getCommentData(@Header("Authorization") String token, @Query("id") String id);

    @POST("api/comment-post")
    Call<CommentStatus> postCommentData(@Header("Authorization") String token, @Body CommentStatus commentStatus);

    @GET("api/like-post/{id}/{like}")
    Call<Message> likePost(@Header("Authorization") String token, @Path("id") Long idPost, @Path("like") Integer like);

    @GET("api/groups/nominations")
    Call<List<Groups>> getGroupRecommend(@Header("Authorization") String token);

    @GET("api/groups/nominations-full")
    Call<GroupsCallBack> getViewMoreGroup(@Header("Authorization") String token, @Query("page") Long page, @Query("size") Long size);

    @GET("api/groups/admin")
    Call<GroupsCallBack> getMyGroupCreated(@Header("Authorization") String token, @Query("page") Long page, @Query("size") Long size);

    @GET("api/groups/users/draft/{id}")
    Call<UserCallBack> getUserDraftInGroup(@Header("Authorization") String token, @Path("id") Long idGroup, @Query("page") Long page, @Query("size") Long size);

    @GET("api/groups/join/draft/{id}")
    Call<Message> sendJoinGroupRequest(@Header("Authorization") String token, @Path("id") Long idGroup);

    @GET("api/groups/join")
    Call<Message> acceptUserJoinGroup(@Header("Authorization") String token,@Query("groupId") Long groupId, @Query("userId") Long userId);

    @DELETE("api/groups/users/draft")
    Call<Message> refuseUserJoinGroup(@Header("Authorization") String token,@Query("groupId") Long groupId, @Query("userId") Long userId);

    @GET("api/groups/leave/{id}")
    Call<Message> sendLeaveGroupRequest(@Header("Authorization") String token, @Path("id") Long idGroup);

    @GET("api/posts/{id}/draft/accept")
    Call<Message> acceptPostInGroup(@Header("Authorization") String token, @Path("id") Long idGroup,@Query("postId") Long postId);

    @DELETE("api/posts/{id}")
    Call<Message> deletePost(@Header("Authorization") String token, @Path("id") Long idPost);

    @POST("api/groups")
    Call<Groups> createGroup(@Header("Authorization") String token, @Body CreateGroup createGroup);
}