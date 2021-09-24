package com.example.mangaworld.Model.Community;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GroupsCallBack {
    private long total;
    private long page;
    private long size;
    private List<Groups> items;
}
