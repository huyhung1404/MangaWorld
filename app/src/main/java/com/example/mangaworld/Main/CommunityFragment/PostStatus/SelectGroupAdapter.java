package com.example.mangaworld.Main.CommunityFragment.PostStatus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.Interface.ISelectGroups;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.R;

import java.util.List;


public class SelectGroupAdapter extends RecyclerView.Adapter<SelectGroupAdapter.SelectGroupsViewHolder> {
    private final ColorStateList SELECT_COLOR;
    private final ColorStateList NONE_SELECT_COLOR;
    private final ISelectGroups iSelectGroups;

    private List<Groups> groups;

    public SelectGroupAdapter(Context _context, ISelectGroups iSelectGroups) {
        this.iSelectGroups = iSelectGroups;
        SELECT_COLOR = ContextCompat.getColorStateList(_context, R.color.select);
        NONE_SELECT_COLOR = ContextCompat.getColorStateList(_context, R.color.white);
    }

    public void setData(List<Groups> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public SelectGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_group, parent, false);
        return new SelectGroupsViewHolder(view, (cardView, position) -> {
            if (cardView.getCardBackgroundColor() == SELECT_COLOR) {
                cardView.setCardBackgroundColor(NONE_SELECT_COLOR);
                iSelectGroups.unselectGroup(groups.get(position).getId());
                return;
            }
            iSelectGroups.selectGroup(groups.get(position).getId());
            cardView.setCardBackgroundColor(SELECT_COLOR);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull SelectGroupsViewHolder holder, int position) {
        if (groups.get(position) == null) {
            return;
        }
        Glide.with(holder.groupsImage.getContext())
                .load(groups.get(position).getAvatarGroup())
                .into(holder.groupsImage);
        holder.nameGroup.setText(groups.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (groups == null) ? 0 : groups.size();
    }

    public static class SelectGroupsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnClickCardView onClickListener;
        private final ImageView groupsImage;
        private final TextView nameGroup;

        public SelectGroupsViewHolder(@NonNull View itemView, OnClickCardView onClickListener) {
            super(itemView);
            groupsImage = itemView.findViewById(R.id.group_image_select);
            nameGroup = itemView.findViewById(R.id.name_group_select);
            this.onClickListener = onClickListener;
            itemView.findViewById(R.id.item_select_group).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick((CardView) v, getAdapterPosition());
        }

        public interface OnClickCardView {
            void onClick(CardView cardView, int position);
        }
    }
}