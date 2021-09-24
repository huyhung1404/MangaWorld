package com.example.mangaworld.Model.Community;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Groups {
    private long id;
    private String name;
    private long categoryId;
    private String description;
    private long numberOfPosts;
    private long numberOfUsers;
    private String avatarGroup;
    private UserForum admin;
    private boolean invitationSent;
    private boolean invitation;
    private boolean publicGroup;
    private long usersDaft;
    private long postsDaft;
}
