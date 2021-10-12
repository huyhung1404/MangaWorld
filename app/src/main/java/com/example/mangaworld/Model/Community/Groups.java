package com.example.mangaworld.Model.Community;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Groups {
    private Long id;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String description;
    private long numberOfPosts;
    private long numberOfUsers;
    private String avatarGroup;
    private Long imageId;
    private UserForum admin;
    private boolean invitationSent;
    private boolean invitation;
    private boolean publicGroup;
    private long usersDaft;
    private long postsDaft;
}
