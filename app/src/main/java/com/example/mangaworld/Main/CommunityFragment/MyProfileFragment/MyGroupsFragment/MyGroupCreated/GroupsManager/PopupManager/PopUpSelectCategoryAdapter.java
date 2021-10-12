package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.Interface.ISelectCategoryGroup;
import com.example.mangaworld.Main.CommunityFragment.PostStatus.SelectGroupAdapter;
import com.example.mangaworld.Model.ListTagCategory;
import com.example.mangaworld.R;

import java.util.List;

public class PopUpSelectCategoryAdapter extends RecyclerView.Adapter<PopUpSelectCategoryAdapter.CategoryGroupViewHolder> {
    private final List<ListTagCategory> mListTag;
    private final ColorStateList SELECT_COLOR;
    private final ISelectCategoryGroup ISelect;

    public PopUpSelectCategoryAdapter(List<ListTagCategory> mListTag, Context _context, ISelectCategoryGroup _iSelect) {
        this.mListTag = mListTag;
        SELECT_COLOR = ContextCompat.getColorStateList(_context, R.color.select);
        ISelect = _iSelect ;
    }

    @NonNull
    @Override
    public PopUpSelectCategoryAdapter.CategoryGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_category_group, parent, false);
        return new CategoryGroupViewHolder(view, (cardView, position) -> {
            if (cardView.getCardBackgroundColor() == SELECT_COLOR) {
                return;
            }
            ISelect.selectCategory(mListTag.get(position).getIdCategory(),mListTag.get(position).getNameCategory(),cardView);
            cardView.setCardBackgroundColor(SELECT_COLOR);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull PopUpSelectCategoryAdapter.CategoryGroupViewHolder holder, int position) {
        holder.nameCategory.setText(mListTag.get(position).getNameCategory());
    }

    @Override
    public int getItemCount() {
        return (mListTag == null) ? 0 : mListTag.size();
    }

    public static class CategoryGroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nameCategory;
        private final SelectGroupAdapter.SelectGroupsViewHolder.OnClickCardView onClick;

        public CategoryGroupViewHolder(@NonNull View itemView, SelectGroupAdapter.SelectGroupsViewHolder.OnClickCardView _onClick) {
            super(itemView);
            nameCategory = itemView.findViewById(R.id.text_name_category_group_select);
            onClick = _onClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClick.onClick((CardView) v, getAdapterPosition());
        }
    }

}
