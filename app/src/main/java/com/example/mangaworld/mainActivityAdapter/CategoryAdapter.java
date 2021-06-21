package com.example.mangaworld.mainActivityAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.object.Manga;
import com.example.mangaworld.object.Category;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final Context mContext;
    private List<Category> mListCategory;
    private IClickItem iClickItem;
    private final String[] images = {
            "https://cdn.popsww.com/blog/sites/2/2021/04/truyen-ngon-tinh-tong-tai-hay-nhat-1280x720.jpg",
            "https://cdn.popsww.com/blog/sites/2/2021/04/ngon-tinh-tong-tai.jpg",
            "https://3.pik.vn/202033d1f54b-9e4a-4cb5-b0fb-771aa7cfa65b.png"
    };

    public interface IClickItem {
        void onClickItemBook(Manga manga);

        void onClickItemCategory(Long id);

        void onClickItemIcon(float id);
    }

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Category> mListCategory, IClickItem iClickItem) {
        this.iClickItem = iClickItem;
        this.mListCategory = mListCategory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (position == 0) {
            SliderAdapter sliderAdapter = new SliderAdapter(images);
            if (holder.sliderView.getChildCount() >= 4) return;
            holder.sliderView.setSliderAdapter(sliderAdapter);
            holder.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
            holder.sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
            holder.sliderView.startAutoCycle();
            holder.iconBXH.setOnClickListener(v -> iClickItem.onClickItemIcon(0));
            holder.iconLike.setOnClickListener(v -> iClickItem.onClickItemIcon(1));
            return;
        }
        int finalPosition = position - 1;
        holder.textNameCategory.setText(mListCategory.get(finalPosition).getNameCategory());
//        holder.textNameCategory.setOnClickListener(v -> iClickItem.onClickItemCategory(mListCategory.get(finalPosition).getIdCategory()));
        //Rcv manga
        holder.rcvBook.setItemViewCacheSize(5);
        holder.rcvBook.setHasFixedSize(true);
        //
        holder.rcvBook.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
        MangaAdapter mangaAdapter = new MangaAdapter(position != 1);
        mangaAdapter.setData(mListCategory.get(finalPosition).getMangas(), iClickItem);
        holder.rcvBook.setAdapter(mangaAdapter);
    }

    @Override
    public int getItemCount() {
        if (mListCategory != null) {
            return mListCategory.size() + 1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? R.layout.item_slider_view : R.layout.item_category;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        //List manga
        private final TextView textNameCategory;
        private final RecyclerView rcvBook;
        //Slider view
        private final SliderView sliderView;
        private final ImageView iconBXH;
        private final ImageView iconLike;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textNameCategory = itemView.findViewById(R.id.name_category);
            rcvBook = itemView.findViewById(R.id.rcv_book);
            sliderView = itemView.findViewById(R.id.slider_view);
            iconBXH = itemView.findViewById(R.id.img_bxh);
            iconLike = itemView.findViewById(R.id.img_bxh_like);
        }
    }
}
