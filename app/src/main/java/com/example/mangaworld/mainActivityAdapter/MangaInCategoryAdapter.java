package com.example.mangaworld.mainActivityAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.object.Manga;

import java.util.List;

public class MangaInCategoryAdapter extends RecyclerView.Adapter<MangaInCategoryAdapter.MangaInCategoryViewHolder> {
    private List<Manga> mMangas;
    private CategoryAdapter.IClickItem iClickItem;

    public void setData(List<Manga> mMangas, CategoryAdapter.IClickItem iClickItem) {
        this.mMangas = mMangas;
        this.iClickItem = iClickItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MangaInCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manga, parent, false);
        return new MangaInCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaInCategoryViewHolder holder, int position) {
        Glide.with(holder.imgBook.getContext()).load(mMangas.get(position).getResourceId()).into(holder.imgBook);
        holder.textLikeBook.setText(String.valueOf(mMangas.get(position).getLikeManga()));
        holder.textViewBook.setText(String.valueOf(mMangas.get(position).getViewManga()));
        holder.textNameBook.setText(mMangas.get(position).getNameManga());
        holder.imgBook.setOnClickListener(v -> iClickItem.onClickItemBook(mMangas.get(position)));
    }

    @Override
    public int getItemCount() {
        if (mMangas != null) {
            return mMangas.size();
        }
        return 0;
    }

    public static class MangaInCategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgBook;
        private final TextView textLikeBook;
        private final TextView textViewBook;
        private final TextView textNameBook;

        public MangaInCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.img_book);
            textLikeBook = itemView.findViewById(R.id.text_like_book);
            textViewBook = itemView.findViewById(R.id.text_view_book);
            textNameBook = itemView.findViewById(R.id.name_book);
        }
    }
}
