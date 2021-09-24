package com.example.mangaworld.Main.ReadManga.Element.InfomationMangaFragment.SummaryFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.Interface.SelectItemInRecycleView;
import com.example.mangaworld.R;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Model.ListTagCategory;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.BookViewHolder>{
    private List<ListTagCategory> mListTag;
    private SelectItemInRecycleView selectItemInRecycleView;
    public void setData(List<ListTagCategory> mListTag, SelectItemInRecycleView selectItemInRecycleView){
        this.mListTag = mListTag;
        this.selectItemInRecycleView = selectItemInRecycleView;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_read_book,parent,false);
        return new BookViewHolder(view, (v, position) -> selectItemInRecycleView.onClickItemCategory(mListTag.get(position).getIdCategory(),false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.textCategory.setText(mListTag.get(position).getNameCategory());
    }

    @Override
    public int getItemCount() {
        return mListTag.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textCategory;

        private OnClickListenerRecyclerView onClickListenerRecyclerView;
        public BookViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListenerRecyclerView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.text_item_category_read_book);
            this.onClickListenerRecyclerView = onClickListenerRecyclerView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerRecyclerView.onClick(v,getAdapterPosition());
        }
    }
}
