package com.example.mangaworld.Main.BookcaseFragment.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.mangaworld.Interface.SelectItemInRecycleView;
import com.example.mangaworld.R;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Manga;
import com.example.mangaworld.Model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookcaseAdapter extends RecyclerView.Adapter<BookcaseAdapter.BookCaseViewHolder> {
    private List<Manga> listManga;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private Context context;
    private SelectItemInRecycleView selectItemInRecycleView;

    public void setData(Context context, List<Manga> listManga, SelectItemInRecycleView selectItemInRecycleView) {
        this.context = context;
        this.listManga = listManga;
        this.selectItemInRecycleView = selectItemInRecycleView;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookCaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manga_case, parent, false);
        return new BookCaseViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookCaseViewHolder holder, int position) {
        Manga manga = listManga.get(position);
        if (manga == null)
            return;
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(manga.getIdManga()));
        Glide.with(holder.imgBook.getContext()).load(manga.getResourceId()).into(holder.imgBook);
        holder.textNameBook.setText(manga.getNameManga());
        holder.textChapSave.setText("Chương " + manga.getReadingIndex());
        holder.frameLayout.setOnClickListener(v -> deleteManga(listManga.get(holder.getAdapterPosition()).getIdManga(),holder.getAdapterPosition()));
        holder.relativeLayout.setOnClickListener(v -> selectItemInRecycleView.onClickItemBook(manga.getIdManga()));
    }

    @Override
    public int getItemCount() {
        if (listManga != null) {
            return listManga.size();
        }
        return 0;
    }

    public static class BookCaseViewHolder extends RecyclerView.ViewHolder {
        private final SwipeRevealLayout swipeRevealLayout;
        private final FrameLayout frameLayout;
        private final RelativeLayout relativeLayout;
        private final ImageView imgBook;
        private final TextView textNameBook;
        private final TextView textChapSave;

        public BookCaseViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_layout);
            frameLayout = itemView.findViewById(R.id.layout_delete);
            relativeLayout = itemView.findViewById(R.id.manga_case_relative_layout);
            imgBook = itemView.findViewById(R.id.item_img_book_case);
            textNameBook = itemView.findViewById(R.id.item_name_book_in_book_case);
            textChapSave = itemView.findViewById(R.id.item_chap_book_in_book_case);

        }
    }

    private void deleteManga(long idManga,int position) {
        APIClient.getAPIChapter().deleteMangaInBookCase("Bearer " + MainActivity.user.getToken(), idManga).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getMessage().equals("delete")) {
                        listManga.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Đã xoá", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
