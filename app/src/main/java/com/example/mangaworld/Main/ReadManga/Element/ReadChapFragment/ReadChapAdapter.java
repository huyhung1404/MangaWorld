package com.example.mangaworld.Main.ReadManga.Element.ReadChapFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;

import java.util.List;

public class ReadChapAdapter extends RecyclerView.Adapter<ReadChapAdapter.ReadChapViewHolder> {

    private List<String> listImg;

    public void setData(List<String> list) {
        this.listImg = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReadChapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_chap, parent, false);
        return new ReadChapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadChapViewHolder holder, int position) {
        Glide.with(holder.itemImg.getContext())
                .load(listImg.get(position))
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.icon_loading)
                .into(holder.itemImg);
    }

    @Override
    public int getItemCount() {
        if (listImg != null) {
            return listImg.size();
        }
        return 0;
    }

    public static class ReadChapViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemImg;

        public ReadChapViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.item_img_read_chap);
        }
    }
}
