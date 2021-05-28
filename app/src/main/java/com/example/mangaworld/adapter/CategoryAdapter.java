package com.example.mangaworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.object.Book;
import com.example.mangaworld.object.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private final Context mContext;
    private List<Category> mListCategory;
    private IClickItem iClickItem;
    public interface IClickItem{
        void onClickItemBook(Book book);
        void onClickItemCategory(String string);
    }

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Category> mListCategory ,IClickItem iClickItem){
        this.iClickItem = iClickItem;
        this.mListCategory = mListCategory;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if (category == null) {
            return;
        }
        holder.textNameCategory.setText(category.getNameCategory());
        holder.textNameCategory.setOnClickListener(v -> iClickItem.onClickItemCategory(category.getNameCategory()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
        holder.rcvBook.setLayoutManager(linearLayoutManager);
        BookAdapter bookAdapter = new BookAdapter();
        bookAdapter.setData(category.getBooks(),iClickItem);
        holder.rcvBook.setAdapter(bookAdapter);
    }

    @Override
    public int getItemCount() {
        if (mListCategory != null) {
            return mListCategory.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        private final TextView textNameCategory;
        private final RecyclerView rcvBook;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textNameCategory = itemView.findViewById(R.id.name_category);
            rcvBook = itemView.findViewById(R.id.rcv_book);
        }
    }
}
