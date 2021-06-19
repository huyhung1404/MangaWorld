package com.example.mangaworld.fragment.SearchFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.object.Manga;

import java.util.List;

public class SearchMangaAdapter extends RecyclerView.Adapter<SearchMangaAdapter.SearchMangaViewHolder> {
    private List<Manga> mMangas;
    private CategoryAdapter.IClickItem iClickItem;

    public void setData(List<Manga> mMangas, CategoryAdapter.IClickItem iClickItem) {
        this.mMangas = mMangas;
        this.iClickItem = iClickItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchMangaAdapter.SearchMangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_manga, parent, false);
        return new SearchMangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMangaAdapter.SearchMangaViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext()).load(mMangas.get(position).getResourceId()).into(holder.imageView);
        holder.textName.setText(mMangas.get(position).getNameManga());
        holder.textAuthor.setText(mMangas.get(position).getMangaAuthor());
        holder.cardView.setOnClickListener(v -> iClickItem.onClickItemBook(mMangas.get(position)));
    }

    @Override
    public int getItemCount() {
        return mMangas.size();
    }

    public static class SearchMangaViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textName;
        private TextView textAuthor;
        private CardView cardView;

        public SearchMangaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_manga_search);
            textName = itemView.findViewById(R.id.text_name_manga_search);
            textAuthor = itemView.findViewById(R.id.text_author_manga_search);
            cardView = itemView.findViewById(R.id.card_view_search_manga);

        }
    }
}
