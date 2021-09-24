package com.example.mangaworld.Main.HomeFragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.Interface.SelectItemInRecycleView;
import com.example.mangaworld.R;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Model.Manga;

import java.util.List;

public class MangaInHomeAdapter extends RecyclerView.Adapter<MangaInHomeAdapter.BookViewHolder> {
    private List<Manga> mMangas;
    private SelectItemInRecycleView selectItemInRecycleView;
    private final boolean isHasLoadMore;
    private long idCategory;

    public MangaInHomeAdapter(boolean isHasLoadMore) {
        this.isHasLoadMore = isHasLoadMore;
    }

    public void setData(List<Manga> mMangas,long idCategory, SelectItemInRecycleView selectItemInRecycleView) {
        this.mMangas = mMangas;
        this.idCategory = idCategory;
        this.selectItemInRecycleView = selectItemInRecycleView;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new BookViewHolder(view, (v, position) -> {
            if (v.getId() == R.id.item_view_more){
                selectItemInRecycleView.onClickItemCategory(idCategory,true);
                return;
            }
            selectItemInRecycleView.onClickItemBook(mMangas.get(position).getIdManga());
        });
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if(!isHasLoadMore){
            Glide.with(holder.imgBookRecommend.getContext()).load(mMangas.get(position).getResourceId()).into(holder.imgBookRecommend);
            holder.textNameBookRecommend.setText(mMangas.get(position).getNameManga());
            holder.textSummaryBookRecommend.setText(mMangas.get(position).getSummaryManga());
            return;
        }
        if (position == mMangas.size()) return;
        Glide.with(holder.imgBook.getContext()).load(mMangas.get(position).getResourceId()).into(holder.imgBook);
        holder.textLikeBook.setText(String.valueOf(mMangas.get(position).getLikeManga()));
        holder.textViewBook.setText(String.valueOf(mMangas.get(position).getViewManga()));
        holder.textNameBook.setText(mMangas.get(position).getNameManga());
    }

    @Override
    public int getItemCount() {
        if (!isHasLoadMore){
            return 4;
        }
        return mMangas.size() +1;
    }

    @Override
    public int getItemViewType(int position) {
        if (!isHasLoadMore){
            return R.layout.item_recommend_layout;
        }
        return (position == mMangas.size()) ? R.layout.item_view_more : R.layout.item_manga;
    }


    public static class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView imgBook;
        private final TextView textLikeBook;
        private final TextView textViewBook;
        private final TextView textNameBook;
        private final ImageView imgBookRecommend;
        private final TextView textNameBookRecommend;
        private final TextView textSummaryBookRecommend;
        private final OnClickListenerRecyclerView onClickListenerRecyclerView;

        public BookViewHolder(@NonNull View itemView,OnClickListenerRecyclerView onClickListenerRecyclerView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.img_book);
            textLikeBook = itemView.findViewById(R.id.text_like_book);
            textViewBook = itemView.findViewById(R.id.text_view_book);
            textNameBook = itemView.findViewById(R.id.name_book);
            imgBookRecommend = itemView.findViewById(R.id.img_item_book_recommend);
            textNameBookRecommend = itemView.findViewById(R.id.name_book_recommend);
            textSummaryBookRecommend = itemView.findViewById(R.id.summary_book_recommend);
            this.onClickListenerRecyclerView = onClickListenerRecyclerView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerRecyclerView.onClick(v,getAdapterPosition());
        }
    }
}
