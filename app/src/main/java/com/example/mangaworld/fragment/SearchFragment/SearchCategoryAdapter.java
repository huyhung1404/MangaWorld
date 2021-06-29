package com.example.mangaworld.fragment.SearchFragment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.main.OnClickListenerRecyclerView;
import com.example.mangaworld.main.ValueInterpolator;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.object.ListTagCategory;

import java.util.List;


public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.SearchCategoryViewHolder> {
    private final List<ListTagCategory> listTagCategories;
    private final int[] mColors;
    private final CategoryAdapter.IClickItem iClickItem;

    public SearchCategoryAdapter(List<ListTagCategory> listTagCategories, Context context, CategoryAdapter.IClickItem iClickItem) {
        this.listTagCategories = listTagCategories;
        mColors = getGradientColors(ContextCompat.getColor(context, R.color.startColor), ContextCompat.getColor(context, R.color.endColor), listTagCategories.size());
        this.iClickItem = iClickItem;
    }

    @NonNull
    @Override
    public SearchCategoryAdapter.SearchCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_category, parent, false);
        return new SearchCategoryViewHolder(view, (v, position) -> iClickItem.onClickItemCategory(listTagCategories.get(position).getIdCategory(),false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCategoryAdapter.SearchCategoryViewHolder holder, int position) {
        holder.textCategory.setText(listTagCategories.get(position).getNameCategory());
        holder.cardView.setCardBackgroundColor(mColors[position]);
    }

    @Override
    public int getItemCount() {
        return listTagCategories.size();
    }

    public static class SearchCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final CardView cardView;
        private final TextView textCategory;

        private OnClickListenerRecyclerView onClickListenerRecyclerView;

        public SearchCategoryViewHolder(@NonNull View itemView,OnClickListenerRecyclerView onClickListenerRecyclerView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_search_category);
            textCategory = itemView.findViewById(R.id.text_item_category_search_category);
            this.onClickListenerRecyclerView = onClickListenerRecyclerView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerRecyclerView.onClick(v,getAdapterPosition());
        }
    }

    private int[] getGradientColors(int startColor, int endColor, int size) {
        int[] colors = new int[size];

        int startR = Color.red(startColor);
        int startG = Color.green(startColor);
        int startB = Color.blue(startColor);

        int endR = Color.red(endColor);
        int endG = Color.green(endColor);
        int endB = Color.blue(endColor);

        ValueInterpolator interpolatorR = new ValueInterpolator(0, size - 1, endR, startR);
        ValueInterpolator interpolatorG = new ValueInterpolator(0, size - 1, endG, startG);
        ValueInterpolator interpolatorB = new ValueInterpolator(0, size - 1, endB, startB);

        for (int i = 0; i < size; ++i) {
            colors[i] = Color.argb(255, (int) interpolatorR.map(i), (int) interpolatorG.map(i), (int) interpolatorB.map(i));
        }
        return colors;
    }
}
