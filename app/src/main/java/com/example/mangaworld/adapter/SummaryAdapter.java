package com.example.mangaworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.BookViewHolder>{
    private List<String> mTextCategory;
    private CategoryAdapter.IClickItem iClickItem;
    public void setData(List<String> mTextCategory, CategoryAdapter.IClickItem iClickItem){
        this.mTextCategory= mTextCategory;
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
        String string = mTextCategory.get(position);
        if (string == null) {
            return;
        }
        holder.textCategory.setText(string);
        holder.textCategory.setOnClickListener(v -> iClickItem.onClickItemCategory(string));
    }

    @Override
    public int getItemCount() {
        if(mTextCategory != null){
            return mTextCategory.size();
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
