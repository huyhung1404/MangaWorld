package com.example.mangaworld.Extension.Pagination;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.mangaworld.Model.Author;

import java.util.List;

public class DiffUtilAuthor extends DiffUtil.Callback {
    List<Author> oldAuthors;
    List<Author> newAuthors;

    public DiffUtilAuthor(List<Author> oldAuthors, List<Author> newAuthors) {
        this.oldAuthors = oldAuthors;
        this.newAuthors = newAuthors;
    }

    @Override
    public int getOldListSize() {
        return oldAuthors != null ? oldAuthors.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newAuthors != null ? newAuthors.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = newAuthors.get(newItemPosition).compareTo(oldAuthors.get(oldItemPosition));
        return result == 0;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Author newAuthor = newAuthors.get(newItemPosition);
        Author oldAuthor = oldAuthors.get(oldItemPosition);

        Bundle bundle = new Bundle();
        if (newAuthor.getId() != oldAuthor.getId()){
//            bundle.putString("link",newAuthor.getLinj);
            bundle.putString("name",newAuthor.getName());
            bundle.putString("age",newAuthor.getAge());
            bundle.putString("nationality",newAuthor.getNationality());
            bundle.putLong("numberComic",newAuthor.getNumberComic());
        }
        if (bundle.size()==0){
            return null;
        }
        return bundle;
        //return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
