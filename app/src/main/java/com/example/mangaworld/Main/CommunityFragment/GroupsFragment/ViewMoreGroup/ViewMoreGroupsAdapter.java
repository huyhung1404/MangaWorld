package com.example.mangaworld.Main.CommunityFragment.GroupsFragment.ViewMoreGroup;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.Interface.ChangePageNews;
import com.example.mangaworld.Interface.IChangeToInformationGroup;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.InformationGroup.InformationGroupFragment;
import com.example.mangaworld.Main.CommunityFragment.PaginationViewHolder;
import com.example.mangaworld.Model.Community.CallBackItems;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.R;


public class ViewMoreGroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ChangePageNews changePageNews;
    private CallBackItems<Groups> callBackGroup;
    private int size;
    private final IChangeToInformationGroup iChangeToInformationGroup;

    public ViewMoreGroupsAdapter(ChangePageNews _changePageNews, IChangeToInformationGroup _iChangeToInformationGroup) {
        changePageNews = _changePageNews;
        iChangeToInformationGroup = _iChangeToInformationGroup;
    }

    public void setData(CallBackItems<Groups> callBackGroup) {
        this.callBackGroup = callBackGroup;
        size = (callBackGroup.getItems() == null) ? 0 : callBackGroup.getItems().size();
        notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewType == R.layout.view_more_groups) {
            return new ViewMoreGroupViewHolder(view, (v, position) -> iChangeToInformationGroup.ChangeTo(callBackGroup.getItems().get(position).getId()));
        }
        return new PaginationViewHolder(view, ((v, position) -> {
            EditText text = view.findViewById(R.id.inputPageStatus);
            switch (v.getId()) {
                case R.id.nextPagerStatus:
                    changePageNews.nextPage();
                    break;
                case R.id.lastPagerStatus:
                    changePageNews.lastPage();
                    break;
                default:
                    changePageNews.goPage(Long.parseLong(text.getText().toString()), text);
                    text.setText("");
            }
        }));
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == size && size >= 10) {
            PaginationViewHolder paginationHolder = (PaginationViewHolder) holder;
            long lastPage = (long) Math.ceil((double) callBackGroup.getTotal()/callBackGroup.getSize());
            paginationHolder.textPaginationPager.setText(String.format("%d/%d", callBackGroup.getPage(), lastPage));
            if (callBackGroup.getPage() == 1) {
                paginationHolder.lastPagerButton.setVisibility(View.GONE);
            } else {
                paginationHolder.lastPagerButton.setVisibility(View.VISIBLE);
            }

            if (callBackGroup.getPage() == lastPage) {
                paginationHolder.nextPagerButton.setVisibility(View.GONE);
            } else {
                paginationHolder.nextPagerButton.setVisibility(View.VISIBLE);
            }
            return;
        }

        if (callBackGroup.getItems().get(position) == null) {
            return;
        }
        Groups group = callBackGroup.getItems().get(position);
        ViewMoreGroupViewHolder viewHolder = (ViewMoreGroupViewHolder) holder;
        Glide.with(viewHolder.avatarGroup.getContext())
                .load(group.getAvatarGroup())
                .into(viewHolder.avatarGroup);

        if (group.isPublicGroup()) {
            viewHolder.textPermission.setBackgroundResource(R.drawable.border_text_view_group);
            viewHolder.textPermission.setText("Public");
        } else {
            viewHolder.textPermission.setBackgroundResource(R.drawable.border_comment);
            viewHolder.textPermission.setText("Private");
        }
        viewHolder.textDescription.setText(group.getDescription());
        viewHolder.nameGroup.setText(group.getName());

        if(group.getCategoryId() == null || group.getCategoryName() == null){
            viewHolder.nameCategory.setVisibility(View.GONE);
        }else{
            viewHolder.nameCategory.setVisibility(View.VISIBLE);
            viewHolder.nameCategory.setText(group.getCategoryName());
        }
        viewHolder.textPost.setText(String.format("Bài viết: %d", group.getNumberOfPosts()));
        viewHolder.textMember.setText(String.format("Thành viên: %d", group.getNumberOfUsers()));
    }

    @Override
    public int getItemCount() {
        if(size == 0)
            return 0;
        if(size >= 10)
            return size + 1;
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == size && size >= 10) ? R.layout.item_paging_pager : R.layout.view_more_groups;
    }

    public static class ViewMoreGroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView avatarGroup;
        private final TextView nameGroup;
        private final TextView textPost;
        private final TextView textMember;
        private final TextView textPermission;
        private final TextView textDescription;
        private final TextView nameCategory;

        private final OnClickListenerRecyclerView onClickListener;

        public ViewMoreGroupViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListener) {
            super(itemView);
            avatarGroup = itemView.findViewById(R.id.avatar_group_view_more);
            nameGroup = itemView.findViewById(R.id.name_group_view_more);
            textPost = itemView.findViewById(R.id.post_view_more);
            textMember = itemView.findViewById(R.id.member_view_more);
            textPermission = itemView.findViewById(R.id.text_permission);
            textDescription = itemView.findViewById(R.id.content_view_more);
            nameCategory = itemView.findViewById(R.id.name_category_group_view_more_group);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v, getAdapterPosition());
        }
    }
}
