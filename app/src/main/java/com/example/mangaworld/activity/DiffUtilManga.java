package com.example.mangaworld.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.mangaworld.object.Manga;

import java.util.ArrayList;
import java.util.List;

public class DiffUtilManga extends DiffUtil.Callback {
    List<Manga> oldMangas;
    List<Manga> newMangas;

    public DiffUtilManga(List<Manga> oldMangas, List<Manga> newMangas) {
        this.oldMangas = oldMangas;
        this.newMangas = newMangas;
    }

    @Override
    public int getOldListSize() {
        return oldMangas != null ? oldMangas.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newMangas != null ? newMangas.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = newMangas.get(newItemPosition).compareTo(oldMangas.get(oldItemPosition));
        return result == 0;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Manga newManga = newMangas.get(newItemPosition);
        Manga oldManga = oldMangas.get(oldItemPosition);

        Bundle bundle = new Bundle();
        if (newManga.getIdManga() == oldManga.getIdManga()){
            bundle.putString("link",newManga.getResourceId());
            bundle.putString("name",newManga.getNameManga());
            bundle.putString("summary",newManga.getSummaryManga());
            bundle.putInt("view",newManga.getViewManga());
            bundle.putInt("like",newManga.getLikeManga());
        }
        if (bundle.size()==0){
            return null;
        }
        return bundle;
        //return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
