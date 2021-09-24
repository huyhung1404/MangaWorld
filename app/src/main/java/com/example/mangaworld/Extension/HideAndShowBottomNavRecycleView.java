package com.example.mangaworld.Extension;

import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.Main.MainActivity;

public class HideAndShowBottomNavRecycleView extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager;
    private boolean isHideBottomNav = false;
    private boolean isScrolling = false;

    public HideAndShowBottomNavRecycleView(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            isScrolling = true;
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (isScrolling && (linearLayoutManager.getChildCount() + linearLayoutManager.findFirstVisibleItemPosition()
                == linearLayoutManager.getItemCount())) {
            MainActivity.hideBottomNav();
            isScrolling = false;
            isHideBottomNav = true;
            return;
        }
        if (isScrolling && isHideBottomNav) {
            MainActivity.showBottomNav();
            isHideBottomNav = false;
        }
    }
}
