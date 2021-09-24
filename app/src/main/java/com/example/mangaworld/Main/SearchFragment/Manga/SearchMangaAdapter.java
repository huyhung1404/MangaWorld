package com.example.mangaworld.Main.SearchFragment.Manga;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.Interface.SelectItemInRecycleView;
import com.example.mangaworld.R;
import com.example.mangaworld.Extension.Pagination.DiffUtilManga;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Model.Manga;

import java.util.ArrayList;
import java.util.List;

public class SearchMangaAdapter extends RecyclerView.Adapter<SearchMangaAdapter.SearchMangaViewHolder> implements Filterable {
    private List<Manga> mangas;
    private final SelectItemInRecycleView selectItemInRecycleView;
    private final List<Manga> mangasFull;
    private final List<Manga> mangasDefault;

    public SearchMangaAdapter(List<Manga> mangasFull,List<Manga> mangas, SelectItemInRecycleView selectItemInRecycleView){
        this.mangasFull = mangasFull;
        this.mangas = mangas;
        this.selectItemInRecycleView = selectItemInRecycleView;
        this.mangasDefault = mangas;
    }

    @NonNull
    @Override
    public SearchMangaAdapter.SearchMangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_manga, parent, false);
        return new SearchMangaViewHolder(view, (v, position) -> selectItemInRecycleView.onClickItemBook(mangas.get(position).getIdManga()));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMangaAdapter.SearchMangaViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext()).load(mangas.get(position).getResourceId()).into(holder.imageView);
        holder.textName.setText(mangas.get(position).getNameManga());
        holder.textAuthor.setText(mangas.get(position).getMangaAuthor());
    }

    @Override
    public int getItemCount() {
        if (mangas!= null){
            return mangas.size();
        }
        return 0;
    }

    public void setData(List<Manga> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilManga(mangas, newData));
        diffResult.dispatchUpdatesTo(this);
        this.mangas = newData;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMangaViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
            return;
        }
        Bundle bundle = (Bundle) payloads.get(0);
        Glide.with(holder.imageView.getContext()).load(bundle.getString("link")).into(holder.imageView);
        holder.textName.setText(bundle.getString("name"));
        holder.textAuthor.setText(bundle.getString("author"));
    }

    public static class SearchMangaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView imageView;
        private final TextView textName;
        private final TextView textAuthor;

        private final OnClickListenerRecyclerView onClickListenerRecyclerView;

        public SearchMangaViewHolder(@NonNull View itemView,OnClickListenerRecyclerView onClickListenerRecyclerView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_manga_search);
            textName = itemView.findViewById(R.id.text_name_manga_search);
            textAuthor = itemView.findViewById(R.id.text_author_manga_search);
            this.onClickListenerRecyclerView = onClickListenerRecyclerView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerRecyclerView.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keySearch = constraint.toString();
                if (keySearch.isEmpty()){
                    mangas = mangasDefault;
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mangas;
                    return filterResults;
                }
                List<Manga> mangasSearch = new ArrayList<>();
                for (Manga manga: mangasFull){
                    if (manga.getNameManga().toLowerCase().contains(keySearch.toLowerCase())){
                        mangasSearch.add(manga);
                    }
                }
                mangas = mangasSearch;
                FilterResults filterResults = new FilterResults();
                filterResults.values = mangas;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mangas = (List<Manga>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
