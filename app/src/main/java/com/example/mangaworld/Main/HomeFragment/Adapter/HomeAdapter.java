package com.example.mangaworld.Main.HomeFragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.Interface.SelectItemInRecycleView;
import com.example.mangaworld.R;
import com.example.mangaworld.Model.Category;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.CategoryViewHolder> {
    private final List<Category> mListCategory;
    private final SelectItemInRecycleView selectItemInRecycleView;
    private final RecyclerView.RecycledViewPool viewPool;
    private boolean isRecommend;
    private MangaInHomeAdapter mangaInHomeAdapter;
    String[] images = {
            "https://cdn.popsww.com/blog/sites/2/2021/04/truyen-ngon-tinh-tong-tai-hay-nhat-1280x720.jpg",
            "https://cdn.popsww.com/blog/sites/2/2021/03/nhung-bo-truyen-tranh-trung-quoc-hay-nhat-2021_Website.jpg",
            "https://top.trangdangtin.com/htdocs/images/news/2020/12/09/800/5fd0a2d6db947_truyen_tranh_co_dai_trung_quoc_hay_nhat_1.png",
            "https://nhattientuu.com/wp-content/uploads/2020/12/truyen-tranh-nhat-ban.jpg"
    };
    private final SliderAdapter sliderAdapter = new SliderAdapter(images);

    public HomeAdapter(List<Category> mListCategory, SelectItemInRecycleView selectItemInRecycleView) {
        this.mListCategory = mListCategory;
        this.selectItemInRecycleView = selectItemInRecycleView;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        CategoryViewHolder holder = new CategoryViewHolder(view);
        if (viewType == R.layout.item_category && !isRecommend) {
            mangaInHomeAdapter = new MangaInHomeAdapter(true);
            holder.rcvBook.setRecycledViewPool(viewPool);
            holder.rcvBook.setItemViewCacheSize(8);
            holder.rcvBook.setHasFixedSize(true);
            holder.rcvBook.setLayoutManager(new LinearLayoutManager(parent.getContext(),RecyclerView.HORIZONTAL,false));
            return holder;
        }
        if (viewType == R.layout.item_category){
            mangaInHomeAdapter = new MangaInHomeAdapter(false);
            holder.rcvBook.setHasFixedSize(true);
            holder.rcvBook.setLayoutManager(new GridLayoutManager(parent.getContext(),2));
            return holder;
        }
        holder.btnBXH.setOnClickListener(v -> selectItemInRecycleView.onClickItemIcon(0));
        holder.btnLike.setOnClickListener(v -> selectItemInRecycleView.onClickItemIcon(1));
        holder.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        holder.sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        holder.sliderView.startAutoCycle();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (position==0){
            holder.sliderView.setSliderAdapter(sliderAdapter);
            return;
        }
        holder.textNameCategory.setText(mListCategory.get(--position).getNameCategory());
        mangaInHomeAdapter.setData(mListCategory.get(position).getMangas(),mListCategory.get(position).getIdCategory(), selectItemInRecycleView);
        holder.rcvBook.setAdapter(mangaInHomeAdapter);
    }

    @Override
    public int getItemCount() {
        return mListCategory.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 1){
            isRecommend = true;
            return R.layout.item_category;
        }
        isRecommend = false;
        return (position == 0) ? R.layout.item_slider_view : R.layout.item_category;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView textNameCategory;
        private final RecyclerView rcvBook;
        private final SliderView sliderView;
        private final ImageView btnBXH;
        private final ImageView btnLike;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textNameCategory = itemView.findViewById(R.id.name_category);
            rcvBook = itemView.findViewById(R.id.rcv_book);
            sliderView = itemView.findViewById(R.id.slider_view);
            btnBXH = itemView.findViewById(R.id.img_bxh);
            btnLike = itemView.findViewById(R.id.img_bxh_like);
        }
    }
}
