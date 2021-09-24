package com.example.mangaworld.Extension.Pagination;

import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationRecyclerView extends RecyclerView.OnScrollListener {
    private final LinearLayoutManager linearLayoutManager;
    private boolean isScrolling = false;
    private final int MAX_ITEM;

    public PaginationRecyclerView(LinearLayoutManager linearLayoutManager, int MAX_ITEM) {
        this.linearLayoutManager = linearLayoutManager;
        this.MAX_ITEM = MAX_ITEM;
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
        int totalItems = linearLayoutManager.getItemCount();
        if (totalItems == MAX_ITEM) {
            return;
        }
        int currentItem = linearLayoutManager.getChildCount();
        int scrollOutItem = linearLayoutManager.findFirstVisibleItemPosition();
        if (isScrolling && (currentItem + scrollOutItem == totalItems)) {
            isScrolling = false;
            setData(totalItems);
        }
    }

    public abstract void setData(int totalItems);
}
