package com.example.mangaworld.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
    private String message;
    private String emailVerify;
    private boolean verify;
    private String change;
}
