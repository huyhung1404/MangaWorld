package com.example.mangaworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.mangaworld.R;
import com.example.mangaworld.object.Book;

import java.util.List;

public class BookCaseAdapter extends RecyclerView.Adapter<BookCaseAdapter.BookCaseViewHolder> {
    private List<Book> listBook;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public void setData(List<Book> listBook){
        this.listBook = listBook;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookCaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_case,parent,false);
        return new BookCaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCaseViewHolder holder, int position) {
        Book book = listBook.get(position);
        if (book == null) {
            return;
        }
        viewBinderHelper.bind(holder.swipeRevealLayout,book.getIdBook());
        Glide.with(holder.imgBook.getContext()).load(book.getResourceId()).into(holder.imgBook);
        holder.textNameBook.setText(book.getNameBook());
        holder.textChapSave.setText("Chương 0");
        holder.frameLayout.setOnClickListener(v -> {
            listBook.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });

    }

    @Override
    public int getItemCount() {
        if (listBook != null) {
            return listBook.size();
        }
        return 0;
    }

    public static class BookCaseViewHolder extends RecyclerView.ViewHolder{
        private SwipeRevealLayout swipeRevealLayout;
        private FrameLayout frameLayout;
        private ImageView imgBook;
        private TextView textNameBook;
        private TextView textChapSave;
        public BookCaseViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout);
            frameLayout = itemView.findViewById(R.id.layout_delete);
            imgBook = itemView.findViewById(R.id.item_img_book_case);
            textNameBook = itemView.findViewById(R.id.item_name_book_in_book_case);
            textChapSave = itemView.findViewById(R.id.item_chap_book_in_book_case);
        }
    }
}
