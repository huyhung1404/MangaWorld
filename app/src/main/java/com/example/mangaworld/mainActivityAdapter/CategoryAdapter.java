package com.example.mangaworld.mainActivityAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
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
    private final List<Category> mListCategory;
    private final IClickItem iClickItem;
    private final RecyclerView.RecycledViewPool viewPool;
    private boolean isRecommend;
    String[] images = {
            "https://cdn.popsww.com/blog/sites/2/2021/04/truyen-ngon-tinh-tong-tai-hay-nhat-1280x720.jpg",
            "https://cdn.popsww.com/blog/sites/2/2021/04/ngon-tinh-tong-tai.jpg",
            "https://3.pik.vn/202033d1f54b-9e4a-4cb5-b0fb-771aa7cfa65b.png"
    };
    private final SliderAdapter sliderAdapter = new SliderAdapter(images);
    private MangaAdapter mangaAdapter;

    public interface IClickItem {
        void onClickItemBook(Manga manga);

        void onClickItemCategory(Long id,boolean isViewMore);

        void onClickItemIcon(float id);
    }

    public CategoryAdapter(List<Category> mListCategory,IClickItem iClickItem) {
        this.mListCategory = mListCategory;
        this.iClickItem = iClickItem;
        viewPool = new RecyclerView.RecycledViewPool();
    }
//
//    public void setData(List<Category> mListCategory) {
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        CategoryViewHolder holder = new CategoryViewHolder(view);
        if (viewType == R.layout.item_category && !isRecommend) {
            mangaAdapter = new MangaAdapter(true);
            holder.rcvBook.setRecycledViewPool(viewPool);
            holder.rcvBook.setItemViewCacheSize(8);
            holder.rcvBook.setHasFixedSize(true);
            holder.rcvBook.setLayoutManager(new LinearLayoutManager(parent.getContext(),RecyclerView.HORIZONTAL,false));
//            holder.rcvBook.setLayoutManager(holder.linearLayoutManager);
            return holder;
        }
        if (viewType == R.layout.item_category){
            mangaAdapter = new MangaAdapter(false);
            holder.rcvBook.setHasFixedSize(true);
            holder.rcvBook.setLayoutManager(new GridLayoutManager(parent.getContext(),2));
//            holder.rcvBook.setLayoutManager(holder.gridLayoutManager);
            return holder;
        }
        holder.btnBXH.setOnClickListener(v -> iClickItem.onClickItemIcon(0));
        holder.btnLike.setOnClickListener(v -> iClickItem.onClickItemIcon(1));
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
        mangaAdapter.setData(mListCategory.get(position).getMangas(),mListCategory.get(position).getIdCategory(), iClickItem);
        holder.rcvBook.setAdapter(mangaAdapter);
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
        //    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        //List manga
        private final TextView textNameCategory;
        private final RecyclerView rcvBook;
        //Slider view
        private final SliderView sliderView;
        //Layout
//        private final LinearLayoutManager linearLayoutManager;
//        private final GridLayoutManager gridLayoutManager;
        //
        private final ImageView btnBXH;
        private final ImageView btnLike;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textNameCategory = itemView.findViewById(R.id.name_category);
            rcvBook = itemView.findViewById(R.id.rcv_book);
            sliderView = itemView.findViewById(R.id.slider_view);
            btnBXH = itemView.findViewById(R.id.img_bxh);
            btnLike = itemView.findViewById(R.id.img_bxh_like);
            //Layout
//            linearLayoutManager = new LinearLayoutManager(itemView.getContext(), RecyclerView.HORIZONTAL, false);
//            gridLayoutManager = new GridLayoutManager(itemView.getContext(), 2);
        }

    }
}
