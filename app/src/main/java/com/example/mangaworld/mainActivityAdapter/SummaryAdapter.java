package com.example.mangaworld.mainActivityAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.object.ListTagCategory;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.BookViewHolder>{
    private List<ListTagCategory> mListTag;
    private CategoryAdapter.IClickItem iClickItem;
    public void setData(List<ListTagCategory> mListTag, CategoryAdapter.IClickItem iClickItem){
        this.mListTag = mListTag;
        this.iClickItem = iClickItem;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_read_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.textCategory.setText(mListTag.get(position).getNameCategory());
        holder.textCategory.setOnClickListener(v -> iClickItem.onClickItemCategory(mListTag.get(position).getIdCategory()));
    }

    @Override
    public int getItemCount() {
        if(mListTag != null){
            return mListTag.size();
        }
        return 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder{
        private final TextView textCategory;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.text_item_category_read_book);
        }
    }
}
