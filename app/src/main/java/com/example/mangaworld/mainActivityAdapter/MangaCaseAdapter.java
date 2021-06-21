package com.example.mangaworld.mainActivityAdapter;

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
import com.example.mangaworld.object.Manga;

import java.util.List;

public class MangaCaseAdapter extends RecyclerView.Adapter<MangaCaseAdapter.BookCaseViewHolder> {
    private List<Manga> listManga;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public void setData(List<Manga> listManga){
        this.listManga = listManga;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookCaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        return new BookCaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCaseViewHolder holder, int position) {
        if (position == listManga.size()) return;
        viewBinderHelper.bind(holder.swipeRevealLayout,String.valueOf(listManga.get(position).getIdManga()));
        Glide.with(holder.imgBook.getContext()).load(listManga.get(position).getResourceId()).into(holder.imgBook);
        holder.textNameBook.setText(listManga.get(position).getNameManga());
        holder.textChapSave.setText("Chương 0");
        holder.frameLayout.setOnClickListener(v -> {
            listManga.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });

    }

    @Override
    public int getItemCount() {
        if (listManga != null) {
            return listManga.size() + 1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == listManga.size())? R.layout.item_null_2 : R.layout.item_manga_case;
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
