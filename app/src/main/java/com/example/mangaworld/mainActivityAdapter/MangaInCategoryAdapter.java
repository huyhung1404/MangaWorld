package com.example.mangaworld.mainActivityAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.main.DiffUtilManga;
import com.example.mangaworld.main.OnClickListenerRecyclerView;
import com.example.mangaworld.object.Manga;

import java.util.List;

public class MangaInCategoryAdapter extends RecyclerView.Adapter<MangaInCategoryAdapter.MangaInCategoryViewHolder> {
    private List<Manga> mMangas;
    private final CategoryAdapter.IClickItem iClickItem;

    public MangaInCategoryAdapter(List<Manga> mMangas, CategoryAdapter.IClickItem iClickItem) {
        this.mMangas = mMangas;
        this.iClickItem = iClickItem;
    }
//    public void setData(List<Manga> mMangas){
//        this.mMangas = mMangas;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public MangaInCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manga_category, parent, false);
        return new MangaInCategoryViewHolder(view, (v, position) -> iClickItem.onClickItemBook(mMangas.get(position)));
    }

    @Override
    public void onBindViewHolder(@NonNull MangaInCategoryViewHolder holder, int position) {
        Manga manga = mMangas.get(position);
        Glide.with(holder.imgBook.getContext()).load(manga.getResourceId()).into(holder.imgBook);
        holder.textLikeBook.setText(String.valueOf(manga.getLikeManga()));
        holder.textViewBook.setText(String.valueOf(manga.getViewManga()));
        holder.textNameBook.setText(manga.getNameManga());
        holder.textSummaryBook.setText(manga.getSummaryManga());
        holder.textAuthorBook.setText(manga.getMangaAuthor());
    }

    @Override
    public int getItemCount() {
        return mMangas.size();
    }

    public void setData(List<Manga> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilManga(mMangas, newData));
        diffResult.dispatchUpdatesTo(this);
        this.mMangas = newData;
    }

    @Override
    public void onBindViewHolder(@NonNull MangaInCategoryViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
            return;
        }
        Bundle bundle = (Bundle) payloads.get(0);
//        Manga manga = (Manga) bundle.get("new_manga");
//        Glide.with(holder.imgBook.getContext()).load(manga.getResourceId()).into(holder.imgBook);
//        holder.textLikeBook.setText(String.valueOf(manga.getLikeManga()));
//        holder.textViewBook.setText(String.valueOf(manga.getViewManga()));
//        holder.textNameBook.setText(manga.getNameManga());
//        holder.textSummaryBook.setText(manga.getSummaryManga());
//        holder.textAuthorBook.setText(manga.getMangaAuthor());

        Glide.with(holder.imgBook.getContext()).load(bundle.getString("link")).into(holder.imgBook);
        holder.textLikeBook.setText(String.valueOf(bundle.getInt("like")));
        holder.textViewBook.setText(String.valueOf(bundle.getInt("view")));
        holder.textNameBook.setText(bundle.getString("name"));
        holder.textSummaryBook.setText(bundle.getString("summary"));
        holder.textAuthorBook.setText(bundle.getString("author"));
    }

    public static class MangaInCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imgBook;

        private final TextView textLikeBook;
        private final TextView textViewBook;
        private final TextView textNameBook;
        private final TextView textAuthorBook;
        private final TextView textSummaryBook;

        private final OnClickListenerRecyclerView onClickListenerRecyclerView;

        public MangaInCategoryViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListenerRecyclerView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.img_item_category);
            textLikeBook = itemView.findViewById(R.id.tv_like_item_category);
            textViewBook = itemView.findViewById(R.id.tv_view_item_category);
            textNameBook = itemView.findViewById(R.id.tv_name_book_category);
            textAuthorBook = itemView.findViewById(R.id.tv_author_category);
            textSummaryBook = itemView.findViewById(R.id.tv_summary_category);
            this.onClickListenerRecyclerView = onClickListenerRecyclerView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerRecyclerView.onClick(v, getAdapterPosition());
        }
    }
}
