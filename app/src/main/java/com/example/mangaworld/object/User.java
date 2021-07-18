package com.example.mangaworld.object;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long id;
    private String userName;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    private String avatar;
    private String token;
    private String imageBase64;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, String fullName, String email) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    public User(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
