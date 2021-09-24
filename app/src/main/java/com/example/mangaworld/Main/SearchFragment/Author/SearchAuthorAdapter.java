package com.example.mangaworld.Main.SearchFragment.Author;

import android.annotation.SuppressLint;
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
import com.example.mangaworld.R;
import com.example.mangaworld.Extension.Pagination.DiffUtilAuthor;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Model.Author;

import java.util.ArrayList;
import java.util.List;


public class SearchAuthorAdapter extends RecyclerView.Adapter<SearchAuthorAdapter.SearchAuthorViewHolder> implements Filterable {
    private List<Author> authors;
    private final OnClickAuthorInSearchView onClickAuthorInSearchView;
    private final List<Author> authorsFull;
    private final List<Author> authorsDefault;

    public interface OnClickAuthorInSearchView {
        void OnClickAuthor(long id);
    }

    public SearchAuthorAdapter(List<Author> authorsFull,List<Author> authors, OnClickAuthorInSearchView onClickAuthorInSearchView) {
        this.authorsFull = authorsFull;
        this.authors = authors;
        this.onClickAuthorInSearchView = onClickAuthorInSearchView;
        this.authorsDefault = authors;
    }


    @NonNull
    @Override
    public SearchAuthorAdapter.SearchAuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_author, parent, false);
        return new SearchAuthorViewHolder(view, (v, position) -> onClickAuthorInSearchView.OnClickAuthor(position));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SearchAuthorAdapter.SearchAuthorViewHolder holder, int position) {
        Glide.with(holder.circleImageView.getContext())
                .load("https://scontent.fhan3-2.fna.fbcdn.net/v/t1.6435-1/p240x240/81905866_1411704438989292_2901155012929388544_n.jpg?_nc_cat=100&ccb=1-3&_nc_sid=7206a8&_nc_ohc=nPG6Cc4_lBcAX8KqW90&_nc_ht=scontent.fhan3-2.fna&tp=6&oh=847879c8f083ecd45eee6bb4ec4635d9&oe=60DAB0CE")
                .into(holder.circleImageView);
        holder.nameAuthor.setText(authors.get(position).getName());
        holder.ageAuthor.setText("Tuổi: " + authors.get(position).getAge());
        holder.nationalityAuthor.setText("Quốc gia: " + authors.get(position).getNationality());
        holder.numberComic.setText("Số truyện đã viết: " + authors.get(position).getNumberComic());
    }

    @Override
    public int getItemCount() {
        if (authors != null) {
            return authors.size();
        }
        return 0;
    }

    public void setData(List<Author> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilAuthor(authors, newData));
        diffResult.dispatchUpdatesTo(this);
        this.authors = newData;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SearchAuthorViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
            return;
        }
        Bundle bundle = (Bundle) payloads.get(0);
        Glide.with(holder.circleImageView.getContext())
                .load("https://scontent.fhan3-2.fna.fbcdn.net/v/t1.6435-1/p240x240/81905866_1411704438989292_2901155012929388544_n.jpg?_nc_cat=100&ccb=1-3&_nc_sid=7206a8&_nc_ohc=nPG6Cc4_lBcAX8KqW90&_nc_ht=scontent.fhan3-2.fna&tp=6&oh=847879c8f083ecd45eee6bb4ec4635d9&oe=60DAB0CE")
                .into(holder.circleImageView);
        holder.nameAuthor.setText(bundle.getString("name"));
        holder.ageAuthor.setText("Tuổi: " + bundle.getString("age"));
        holder.nationalityAuthor.setText("Quốc gia: " + bundle.getString("nationality"));
        holder.numberComic.setText("Số truyện đã viết: " + bundle.getLong("numberComic"));
    }

    public static class SearchAuthorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView circleImageView;
        private final TextView nameAuthor;
        private final TextView ageAuthor;
        private final TextView nationalityAuthor;
        private final TextView numberComic;
        private final OnClickListenerRecyclerView onClickListenerRecyclerView;

        public SearchAuthorViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListenerRecyclerView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circle_image_search_author);
            nameAuthor = itemView.findViewById(R.id.text_name_search_author);
            ageAuthor = itemView.findViewById(R.id.item_search_category_age);
            nationalityAuthor = itemView.findViewById(R.id.item_search_category_nationality);
            numberComic = itemView.findViewById(R.id.item_search_category_numberComic);
            this.onClickListenerRecyclerView = onClickListenerRecyclerView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerRecyclerView.onClick(v, getAdapterPosition());
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keySearch = constraint.toString();
                if (keySearch.isEmpty()){
                    authors = authorsDefault;
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = authors;
                    return filterResults;
                }
                List<Author> authorsSearch = new ArrayList<>();
                for (Author author: authorsFull){
                    if (author.getName().toLowerCase().contains(keySearch.toLowerCase())){
                        authorsSearch.add(author);
                    }
                }
                authors = authorsSearch;
                FilterResults filterResults = new FilterResults();
                filterResults.values = authors;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                authors = (List<Author>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
