package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.InformationGroup.InformationGroupFragment;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.R;

import java.util.List;

public class MyGroupsAdapter extends RecyclerView.Adapter<MyGroupsAdapter.MyGroupsViewHolder> {
    private List<Groups> groups;

    public MyGroupsAdapter() {
    }

    public void setData(List<Groups> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_group, parent, false);
        return new MyGroupsViewHolder(view, (v, position) -> CommunityFragment
                .GoToScreenInCommunity(new InformationGroupFragment(groups.get(position).getId()),
                        InformationGroupFragment.TAG));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MyGroupsViewHolder holder, int position) {
        if (groups.get(position) == null) {
            return;
        }
        Glide.with(holder.avatarGroup.getContext())
                .load(groups.get(position).getAvatarGroup())
                .into(holder.avatarGroup);

        holder.nameGroup.setText(groups.get(position).getName());
        holder.textPost.setText(String.format("Bài viết: %d", groups.get(position).getNumberOfPosts()));
        holder.textMember.setText(String.format("Thành viên: %d", groups.get(position).getNumberOfUsers()));
    }

    @Override
    public int getItemCount() {
        return (groups == null) ? 0 : groups.size();
    }

    public static class MyGroupsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView avatarGroup;
        private final TextView nameGroup;
        private final TextView textPost;
        private final TextView textMember;

        private final OnClickListenerRecyclerView onClickListener;

        public MyGroupsViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListener) {
            super(itemView);
            avatarGroup = itemView.findViewById(R.id.avatar_group_my_join);
            nameGroup = itemView.findViewById(R.id.name_group_my_join);
            textPost = itemView.findViewById(R.id.post_item_my_group);
            textMember = itemView.findViewById(R.id.member_item_my_group);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v, getAdapterPosition());
        }
    }
}