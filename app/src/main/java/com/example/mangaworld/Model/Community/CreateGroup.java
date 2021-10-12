package com.example.mangaworld.Model.Community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateGroup {
    private Long id;
    private final String name;
    private final Long categoryId;
    private final String description;
    private final Long avatar_id;
    private final Integer publicGroup;
}
