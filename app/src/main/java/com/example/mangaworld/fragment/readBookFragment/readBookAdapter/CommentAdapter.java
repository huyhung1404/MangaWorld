package com.example.mangaworld.fragment.readBookFragment.readBookAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.object.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> listComment;

    public void setData(List<Comment> listComment){
        this.listComment = listComment;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = listComment.get(position);
        if (comment == null) {
            return;
        }
        Glide.with(holder.imgComment.getContext()).load(comment.getResourceAvatar()).into(holder.imgComment);
        holder.textNameComment.setText(comment.getNameComment());
        holder.textContentComment.setText(comment.getCommentContent());
    }

    @Override
    public int getItemCount() {
        if (listComment != null) {
            return listComment.size();
        }
        return 0;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgComment;
        private TextView textNameComment,textContentComment;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgComment = itemView.findViewById(R.id.item_img_avatar);
            textNameComment = itemView.findViewById(R.id.text_name_comment);
            textContentComment = itemView.findViewById(R.id.text_content_comment);
        }
    }
}
