package com.example.mangaworld.fragment.BXHFragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.activity.OnClickListenerRecyclerView;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.object.Manga;

import java.util.List;

public class RankFragmentAdapter extends RecyclerView.Adapter<RankFragmentAdapter.RankFragmentViewHolder> {
    private List<Manga> mMangas;
    private CategoryAdapter.IClickItem iClickItem;
    private final float typeAmount;

    public RankFragmentAdapter(float typeAmount) {
        this.typeAmount = typeAmount;
    }

    public void setData(List<Manga> mMangas, CategoryAdapter.IClickItem iClickItem) {
        this.mMangas = mMangas;
        this.iClickItem = iClickItem;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RankFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manga_bxh,parent,false);
        return new RankFragmentViewHolder(view,typeAmount, (v, position) -> iClickItem.onClickItemBook(mMangas.get(position)));
    }

    @Override
    public void onBindViewHolder(@NonNull RankFragmentViewHolder holder, int position) {
        Manga manga = mMangas.get(position);
        Glide.with(holder.imgManga.getContext()).load(manga.getResourceId()).into(holder.imgManga);
        holder.nameManga.setText(manga.getNameManga());
        holder.summaryManga.setText(manga.getSummaryManga());
        holder.authorManga.setText(manga.getMangaAuthor());
        holder.rankManga.setText(String.valueOf(position+1));
        holder.amountManga.setText((typeAmount==0)?String.valueOf(manga.getViewManga()):String.valueOf(manga.getLikeManga()));
    }

    @Override
    public int getItemCount() {
        if (mMangas != null) {
            return mMangas.size();
        }
        return 0;
    }

    public static class RankFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imgManga;
        private final TextView nameManga;
        private final TextView summaryManga;
        private final TextView authorManga;
        private final TextView rankManga;
        private final TextView amountManga;

        private final OnClickListenerRecyclerView onClickListenerRecyclerView;

        public RankFragmentViewHolder(@NonNull View itemView,float typeAmount, OnClickListenerRecyclerView onClickListenerRecyclerView) {
            super(itemView);
            imgManga = itemView.findViewById(R.id.img_book_item_bxh);
            nameManga = itemView.findViewById(R.id.name_book_item_bxh);
            summaryManga = itemView.findViewById(R.id.summary_item_bxh);
            authorManga = itemView.findViewById(R.id.author_item_bxh);
            rankManga = itemView.findViewById(R.id.rank_item_bxh);

            ImageView amountType = itemView.findViewById(R.id.img_amount_type);
            amountType.setImageResource((typeAmount == 0)?R.drawable.view_book : R.drawable.like_book);
            amountManga = itemView.findViewById(R.id.text_amount_item_bxh);

            this.onClickListenerRecyclerView = onClickListenerRecyclerView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerRecyclerView.onClick(v,getAdapterPosition());
        }
    }
}
