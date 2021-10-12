package com.example.mangaworld.Interface;

import com.example.mangaworld.Model.Community.Groups;

public class CallbackData {
    public interface SelectCategoryCreate{
        void setData(long id,String name);
    }
    public interface SelectCategoryChange{
        void setData(Groups groups);
    }
}
