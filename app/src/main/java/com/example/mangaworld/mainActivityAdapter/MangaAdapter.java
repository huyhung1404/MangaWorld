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

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.BookViewHolder> {
    private List<Manga> mMangas;
    private CategoryAdapter.IClickItem iClickItem;
    private final boolean isHasLoadMore;

    public MangaAdapter(boolean isHasLoadMore) {
        this.isHasLoadMore = isHasLoadMore;
    }

    public void setData(List<Manga> mMangas, CategoryAdapter.IClickItem iClickItem) {
        this.mMangas = mMangas;
        this.iClickItem = iClickItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if (position == mMangas.size()) {
//            holder.itemViewMore.setOnClickListener(v -> iClickItem.onClickItemCategory(idCategory));
            return;
        }
        Glide.with(holder.imgBook.getContext()).load(mMangas.get(position).getResourceId()).into(holder.imgBook);
        holder.textLikeBook.setText(String.valueOf(mMangas.get(position).getLikeManga()));
        holder.textViewBook.setText(String.valueOf(mMangas.get(position).getViewManga()));
//        String string = mMangas.get(position).getNameManga();
//        if (string.length() >= 45){
//            holder.textNameBook.setText(string.substring(0,45).concat("..."));
//            holder.imgBook.setOnClickListener(v -> iClickItem.onClickItemBook(mMangas.get(position)));
//            return;
//        }
        holder.textNameBook.setText(mMangas.get(position).getNameManga());
        holder.imgBook.setOnClickListener(v -> iClickItem.onClickItemBook(mMangas.get(position)));
    }

    @Override
    public int getItemCount() {
        if (mMangas != null && isHasLoadMore) return mMangas.size() + 1;
        if (mMangas != null) return mMangas.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mMangas.size()) ? R.layout.item_view_more : R.layout.item_manga;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgBook;
        private final TextView textLikeBook;
        private final TextView textViewBook;
        private final TextView textNameBook;
        private final LinearLayout itemViewMore;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.img_book);
            textLikeBook = itemView.findViewById(R.id.text_like_book);
            textViewBook = itemView.findViewById(R.id.text_view_book);
            textNameBook = itemView.findViewById(R.id.name_book);
            itemViewMore = itemView.findViewById(R.id.item_view_more);
        }
    }
}
