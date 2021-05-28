package com.example.mangaworld.fragment.readBookFragment.readBookAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.object.Chap;

import java.util.List;

public class ChapAdapter extends RecyclerView.Adapter<ChapAdapter.ChapViewHolder>{
    private List<Chap> mListChap;
    private final IClickItemChapAdapter iClickItemChapAdapter;
    public interface IClickItemChapAdapter{
        void onClickItemChap(Chap chap);
    }
    public void setData(List<Chap> list){
        this.mListChap = list;
        notifyDataSetChanged();
    }

    public ChapAdapter(List<Chap> mListChap, IClickItemChapAdapter iClickItemChapAdapter) {
        this.mListChap = mListChap;
        this.iClickItemChapAdapter = iClickItemChapAdapter;
    }

    @NonNull
    @Override
    public ChapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chap,parent,false);
        return new ChapViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChapAdapter.ChapViewHolder holder, int position) {
        Chap chap = mListChap.get(position);
        if (chap == null) {
            return;
        }
        holder.tvChap.setText("Chap " + chap.getNumberChap());
        holder.tvChap.setOnClickListener(v -> iClickItemChapAdapter.onClickItemChap(chap));
    }

    @Override
    public int getItemCount() {
        if (mListChap != null) {
            return mListChap.size();
        }
        return 0;
    }

    public static class ChapViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvChap;
        public ChapViewHolder( View itemView) {
            super(itemView);
            tvChap = itemView.findViewById(R.id.text_view_chap);
        }
    }
}
