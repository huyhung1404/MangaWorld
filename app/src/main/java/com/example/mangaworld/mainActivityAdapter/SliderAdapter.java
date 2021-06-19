package com.example.mangaworld.mainActivityAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderHolder> {
    private String[] images;
    public SliderAdapter(String[] images){
        this.images = images;
    }


    @Override
    public SliderHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_slider_view,parent,false);
        return new SliderHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderHolder viewHolder, int position) {
        Glide.with(viewHolder.imageView.getContext()).load(images[position]).into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public static class SliderHolder extends SliderViewAdapter.ViewHolder{
        private ImageView imageView;
        public SliderHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_in_slider_view);
        }
    }
}
