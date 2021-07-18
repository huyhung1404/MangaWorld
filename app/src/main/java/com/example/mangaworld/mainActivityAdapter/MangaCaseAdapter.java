package com.example.mangaworld.mainActivityAdapter;

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
import com.example.mangaworld.R;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.main.OnClickListenerRecyclerView;
import com.example.mangaworld.object.Manga;
import com.example.mangaworld.object.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

public class MangaCaseAdapter extends RecyclerView.Adapter<MangaCaseAdapter.BookCaseViewHolder> {
    private List<Manga> listManga;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private Context context;
    private CategoryAdapter.IClickItem iClickItem;

    public void setData(Context context, List<Manga> listManga, CategoryAdapter.IClickItem iClickItem) {
        this.context = context;
        this.listManga = listManga;
        this.iClickItem = iClickItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookCaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new BookCaseViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookCaseViewHolder holder, int position) {
        if (position == listManga.size()) return;
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(listManga.get(position).getIdManga()));
        Glide.with(holder.imgBook.getContext()).load(listManga.get(position).getResourceId()).into(holder.imgBook);
        holder.textNameBook.setText(listManga.get(position).getNameManga());
        holder.textChapSave.setText("Chương " + listManga.get(position).getReadingIndex());

        holder.frameLayout.setOnClickListener(v -> deleteManga(listManga.get(holder.getAdapterPosition()).getIdManga(),holder.getAdapterPosition()));
        holder.relativeLayout.setOnClickListener(v -> iClickItem.onClickItemBook(listManga.get(position).getIdManga()));

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
        return (position == listManga.size()) ? R.layout.item_null_2 : R.layout.item_manga_case;
    }

    public static class BookCaseViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeRevealLayout;
        private FrameLayout frameLayout;
        private RelativeLayout relativeLayout;
        private ImageView imgBook;
        private TextView textNameBook;
        private TextView textChapSave;

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
