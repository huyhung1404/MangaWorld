package com.example.mangaworld.Interface;

import android.widget.EditText;

public interface ChangePageNews {
    void nextPage();
    void lastPage();
    void goPage(long page, EditText view);
}
