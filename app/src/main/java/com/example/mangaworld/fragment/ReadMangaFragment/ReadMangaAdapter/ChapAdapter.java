package com.example.mangaworld.fragment.ReadMangaFragment.ReadMangaAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.main.OnClickListenerRecyclerView;

import java.util.List;

public class ChapAdapter extends RecyclerView.Adapter<ChapAdapter.ChapViewHolder>{
    private List<Long> mListChapter;
    private final IClickItemChapAdapter iClickItemChapAdapter;
    public interface IClickItemChapAdapter{
        void onClickItemChap(int position);
    }
    public void setData(List<Long> list){
        this.mListChapter = list;
        notifyDataSetChanged();
    }

    public ChapAdapter(IClickItemChapAdapter iClickItemChapAdapter) {
        this.iClickItemChapAdapter = iClickItemChapAdapter;
    }

    @NonNull
    @Override
    public ChapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chap,parent,false);
        return new ChapViewHolder(view, (v, position) -> iClickItemChapAdapter.onClickItemChap(position));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChapAdapter.ChapViewHolder holder, int position) {
        holder.tvChap.setText("Chương " + mListChapter.get(position));
    }

    @Override
    public int getItemCount() {
        if (mListChapter != null) {
            return mListChapter.size();
        }
        return 0;
    }

    public static class ChapViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvChap;

        private OnClickListenerRecyclerView onClickListenerRecyclerView;
        public ChapViewHolder( View itemView,OnClickListenerRecyclerView onClickListenerRecyclerView) {
            super(itemView);
            tvChap = itemView.findViewById(R.id.text_view_chap);
            this.onClickListenerRecyclerView = onClickListenerRecyclerView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerRecyclerView.onClick(v,getAdapterPosition());
        }
    }
}
