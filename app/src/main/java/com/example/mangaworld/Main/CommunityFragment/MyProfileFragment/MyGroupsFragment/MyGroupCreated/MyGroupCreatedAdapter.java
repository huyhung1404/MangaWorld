package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated;

import android.annotation.SuppressLint;
import android.util.Log;
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
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.MemberManager;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PostManager;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.SettingGroupFragment;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.R;

import java.util.List;

public class MyGroupCreatedAdapter extends RecyclerView.Adapter<MyGroupCreatedAdapter.MyGroupsCreatedViewHolder> {
    private List<Groups> groups;

    public void setData(List<Groups> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public MyGroupsCreatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_group_created, parent, false);
        return new MyGroupsCreatedViewHolder(view, (v, position) -> {
            switch (v.getId()) {
                case R.id.name_group_my_created:
                    CommunityFragment
                            .GoToScreenInCommunity(new InformationGroupFragment(groups.get(position).getId()),
                                    InformationGroupFragment.TAG);
                    break;
                case R.id.button_browser_post:
                    CommunityFragment
                            .GoToScreenInCommunity(new PostManager(groups.get(position).getId()),
                                    PostManager.TAG);
                    break;
                case R.id.button_browser_member:
                    CommunityFragment
                            .GoToScreenInCommunity(new MemberManager(groups.get(position).getId()),
                                    MemberManager.TAG);
                    break;
                default:
                    CommunityFragment
                            .GoToScreenInCommunity(new SettingGroupFragment(groups.get(position).getId()),
                                    SettingGroupFragment.TAG);
            }
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MyGroupsCreatedViewHolder holder, int position) {
        if (groups.get(position) == null) {
            return;
        }
        Groups group = groups.get(position);
        //Avatar
        Glide.with(holder.avatar.getContext())
                .load(group.getAvatarGroup())
                .into(holder.avatar);
        //Tên
        holder.nameGroup.setText(group.getName());
        holder.textPost.setText(String.format("Bài viết: %d", group.getNumberOfPosts()));
        holder.textMember.setText(String.format("Thành viên: %d", group.getNumberOfUsers()));

        if (group.isPublicGroup()) {
            holder.permission.setBackgroundResource(R.drawable.border_text_view_group);
            holder.permission.setText("Public");
            holder.buttonMember.setVisibility(View.GONE);
        } else {
            holder.permission.setBackgroundResource(R.drawable.border_comment);
            holder.permission.setText("Private");
            holder.buttonMember.setVisibility(View.VISIBLE);
        }

        if (group.getUsersDaft() == 0 || group.isPublicGroup()) {
            holder.numberMember.setVisibility(View.GONE);
        } else {
            holder.numberMember.setText(String.valueOf(group.getUsersDaft()));
            holder.numberMember.setVisibility(View.VISIBLE);
        }

        if (group.getPostsDaft() == 0) {
            holder.numberPost.setVisibility(View.GONE);
        } else {
            holder.numberPost.setText(String.valueOf(group.getPostsDaft()));
            holder.numberPost.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (groups == null) ? 0 : groups.size();
    }

    public static class MyGroupsCreatedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView avatar;
        private final TextView nameGroup;
        private final TextView textPost;
        private final TextView textMember;
        private final TextView permission;
        private final TextView numberPost;
        private final TextView numberMember;
        private final TextView buttonMember;
        private final OnClickListenerRecyclerView onClickListener;

        public MyGroupsCreatedViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListener) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_group_my_created);
            nameGroup = itemView.findViewById(R.id.name_group_my_created);
            textPost = itemView.findViewById(R.id.post_item_my_created);
            textMember = itemView.findViewById(R.id.member_item_my_created);
            permission = itemView.findViewById(R.id.text_permission_group_created);
            numberPost = itemView.findViewById(R.id.number_notification_browser_post);
            numberMember = itemView.findViewById(R.id.number_notification_browser_member);
            buttonMember = itemView.findViewById(R.id.button_browser_member);
            this.onClickListener = onClickListener;
            nameGroup.setOnClickListener(this);
            buttonMember.setOnClickListener(this);
            itemView.findViewById(R.id.button_browser_post).setOnClickListener(this);
            itemView.findViewById(R.id.button_setting_my_group).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v, getAdapterPosition());
        }
    }
}
