package com.example.mangaworld.Model.Community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateGroup {
    private String name;
    private String description;
    private Long avatar_id;
    private Integer publicGroup;
}
