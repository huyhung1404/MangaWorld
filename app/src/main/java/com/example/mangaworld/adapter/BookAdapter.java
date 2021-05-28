package com.example.mangaworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.object.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private List<Book> mBooks;
    private CategoryAdapter.IClickItem iClickItem;

    public void setData(List<Book> mBooks, CategoryAdapter.IClickItem iClickItem){
        this.mBooks= mBooks;
        this.iClickItem= iClickItem;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = mBooks.get(position);
        if (book == null) {
            return;
        }
        Glide.with(holder.imgBook.getContext()).load(book.getResourceId()).into(holder.imgBook);
        holder.textLikeBook.setText(String.valueOf(book.getLikeBook()));
        holder.textViewBook.setText(String.valueOf(book.getViewBook()));
        holder.textNameBook.setText(book.getNameBook());
        holder.imgBook.setOnClickListener(v -> iClickItem.onClickItemBook(book));
    }

    @Override
    public int getItemCount() {
        if(mBooks != null){
            return mBooks.size();
        }
        return 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imgBook;
        private final TextView textLikeBook;
        private final TextView textViewBook;
        private final TextView textNameBook;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.img_book);
            textLikeBook = itemView.findViewById(R.id.text_like_book);
            textViewBook = itemView.findViewById(R.id.text_view_book);
            textNameBook = itemView.findViewById(R.id.name_book);
        }
    }
}
