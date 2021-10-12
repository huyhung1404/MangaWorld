package com.example.mangaworld.Model.Community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserForum {
    private Long id;
    private String userName;
    private String fullName;
    private String phone;
    private String email;
    private boolean status;
    private String linkImage;
}
