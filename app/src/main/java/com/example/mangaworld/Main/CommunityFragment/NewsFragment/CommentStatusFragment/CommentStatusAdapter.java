package com.example.mangaworld.Main.CommunityFragment.NewsFragment.CommentStatusFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.Interface.IReplyComment;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Model.Community.CommentValues;
import com.example.mangaworld.R;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentStatusAdapter extends RecyclerView.Adapter<CommentStatusAdapter.CommentStatusViewHolder> {
    private final RelativeLayout.LayoutParams params1nd = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    private final RelativeLayout.LayoutParams params2nd = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    private List<CommentValues> commentValues;
    private List<Integer> commentValueTypes;
    private final Date dateNow = new Date();
    private final IReplyComment iReplyComment;

    public CommentStatusAdapter(IReplyComment iReplyComment) {
        this.iReplyComment = iReplyComment;
        params1nd.setMargins(0, 0, 0, 0);
        params2nd.setMargins(150, 0, 0, 0);
    }

    public void setData(List<CommentValues> commentValues, List<Integer> commentValueTypes) {
        this.commentValues = commentValues;
        this.commentValueTypes = commentValueTypes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_status, parent, false);
        return new CommentStatusViewHolder(view, (v, position) ->
                iReplyComment.replyComment(position, commentValues.get(position)
                        .getUserDTO().getFullName()));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentStatusViewHolder holder, int position) {
        if (commentValues.get(position) == null) {
            return;
        }
        switch (commentValueTypes.get(position)) {
            case 1:
                holder.relativeLayout.setLayoutParams(params1nd);
                holder.replyButton.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.relativeLayout.setLayoutParams(params2nd);
                holder.replyButton.setVisibility(View.GONE);
                break;
        }
        if(commentValues.get(position).getUserDTO() != null){
            Glide.with(holder.avatar.getContext())
                    .load(commentValues.get(position).getUserDTO().getLinkImage())
                    .into(holder.avatar);
            holder.name.setText(commentValues.get(position).getUserDTO().getFullName());
        }
        holder.content.setText(commentValues.get(position).getContent());
        if(commentValues.get(position).getCreatedDate() == null) return;
        holder.timeComment.setText(timeHandling(commentValues.get(position).getCreatedDate()));

    }

    @Override
    public int getItemCount() {
        return (commentValues == null) ? 0 : commentValues.size();
    }

    public static class CommentStatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnClickListenerRecyclerView onClickListener;
        private final RelativeLayout relativeLayout;
        private final CircleImageView avatar;
        private final TextView name;
        private final TextView content;
        private final TextView timeComment;
        private final TextView replyButton;

        public CommentStatusViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListener) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.form_comment_status);
            avatar = itemView.findViewById(R.id.avatar_comment_status);
            name = itemView.findViewById(R.id.name_comment_status);
            content = itemView.findViewById(R.id.content_comment_status);
            timeComment = itemView.findViewById(R.id.time_comment_status);
            replyButton = itemView.findViewById(R.id.button_reply_comment_status);
            this.onClickListener = onClickListener;
            replyButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v, getAdapterPosition());
        }
    }

    private String timeHandling(Date createDate) {
        long diffInSeconds = (dateNow.getTime() - createDate.getTime()) / 1000;
        if (diffInSeconds <= 60) {
            return "Vài giây";
        } else if (diffInSeconds < 3600) {
            return diffInSeconds / 60 + " phút";
        } else if (diffInSeconds < 86400) {
            return diffInSeconds / 3600 + " giờ";
        }
        return diffInSeconds / 86400 + " ngày";
    }
}
