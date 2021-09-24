package com.example.mangaworld.Model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Author implements Serializable,Comparable {
    private long id;
//    private String avatar;
    private String name;
    private String age;
    private String nationality;
    private long numberComic;

    @Override
    public int compareTo(Object o) {
        Author author = (Author) o;
        if (author.id == this.id){
            return 0;
        }
        return 1;
    }
}
