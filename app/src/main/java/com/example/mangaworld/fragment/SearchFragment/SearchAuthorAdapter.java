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
import com.example.mangaworld.object.Author;

import java.util.List;


public class SearchAuthorAdapter extends RecyclerView.Adapter<SearchAuthorAdapter.SearchAuthorViewHolder> {
    private List<Author> list;

    public void setData(List<Author> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAuthorAdapter.SearchAuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new SearchAuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAuthorAdapter.SearchAuthorViewHolder holder, int position) {
        if (position == list.size()) return;

        Glide.with(holder.circleImageView.getContext()).load(list.get(position).getImageAuthor()).into(holder.circleImageView);
        holder.textAuthor.setText(list.get(position).getNameAuthor());
        holder.textSumManga.setText("Số truyện: " + list.get(position).getSumManga());
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size()) ? R.layout.item_null : R.layout.item_search_author;
    }

    public static class SearchAuthorViewHolder extends RecyclerView.ViewHolder {
        private ImageView circleImageView;
        private CardView cardView;
        private TextView textAuthor, textSumManga;

        public SearchAuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circle_image_search_author);
            cardView = itemView.findViewById(R.id.card_view_search_author);
            textAuthor = itemView.findViewById(R.id.text_name_search_author);
            textSumManga = itemView.findViewById(R.id.text_sum_manga_author);
        }
    }
}
