package com.example.mangaworld.Interface;

import android.content.Intent;

public interface DataReceivedListener {
    void onReceived(int requestCode, int resultCode, Intent data);
}
