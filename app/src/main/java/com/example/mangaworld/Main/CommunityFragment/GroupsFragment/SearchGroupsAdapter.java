package com.example.mangaworld.Main.CommunityFragment.GroupsFragment;

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

public class SearchGroupsAdapter extends RecyclerView.Adapter<SearchGroupsAdapter.SearchGroupsViewHolder> {
    private List<Groups> m_Groups;

    public void SetData(List<Groups> groups) {
        m_Groups = groups;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_more_groups, parent, false);
        return new SearchGroupsViewHolder(view, (v, position) ->
                CommunityFragment.GoToScreenInCommunity(
                        new InformationGroupFragment(m_Groups.get(position).getId()),
                        InformationGroupFragment.TAG)
        );
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull SearchGroupsViewHolder holder, int position) {
        Groups group = m_Groups.get(position);
        if(group == null){
            return;
        }
        Glide.with(holder.avatarGroup.getContext())
                .load(group.getAvatarGroup())
                .into(holder.avatarGroup);

        if (group.isPublicGroup()) {
            holder.textPermission.setBackgroundResource(R.drawable.border_text_view_group);
            holder.textPermission.setText("Public");
        } else {
            holder.textPermission.setBackgroundResource(R.drawable.border_comment);
            holder.textPermission.setText("Private");
        }
        holder.textDescription.setText(group.getDescription());
        holder.nameGroup.setText(group.getName());
        holder.textPost.setText(String.format("Bài viết: %d", group.getNumberOfPosts()));
        holder.textMember.setText(String.format("Thành viên: %d", group.getNumberOfUsers()));
    }

    @Override
    public int getItemCount() {
        return (m_Groups == null) ? 0 : m_Groups.size();
    }

    public static class SearchGroupsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView avatarGroup;
        private final TextView nameGroup;
        private final TextView textPost;
        private final TextView textMember;
        private final TextView textPermission;
        private final TextView textDescription;

        private final OnClickListenerRecyclerView onClickListener;

        public SearchGroupsViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListener) {
            super(itemView);
            avatarGroup = itemView.findViewById(R.id.avatar_group_view_more);
            nameGroup = itemView.findViewById(R.id.name_group_view_more);
            textPost = itemView.findViewById(R.id.post_view_more);
            textMember = itemView.findViewById(R.id.member_view_more);
            textPermission = itemView.findViewById(R.id.text_permission);
            textDescription = itemView.findViewById(R.id.content_view_more);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v, getAdapterPosition());
        }
    }
}
