package com.example.mangaworld.Main.CommunityFragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.R;


public class PaginationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textPaginationPager;
    public TextView lastPagerButton;
    public TextView nextPagerButton;
    private final OnClickListenerRecyclerView onClickListener;

    public PaginationViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListener) {
        super(itemView);
        textPaginationPager = itemView.findViewById(R.id.text_pagination_pager_status);
        lastPagerButton = itemView.findViewById(R.id.lastPagerStatus);
        nextPagerButton = itemView.findViewById(R.id.nextPagerStatus);
        this.onClickListener = onClickListener;
        lastPagerButton.setOnClickListener(this);
        nextPagerButton.setOnClickListener(this);
        itemView.findViewById(R.id.goToPageStatus).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onClickListener.onClick(v, getAdapterPosition());
    }
}

