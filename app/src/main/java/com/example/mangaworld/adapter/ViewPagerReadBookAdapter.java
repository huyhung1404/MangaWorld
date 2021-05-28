package com.example.mangaworld.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mangaworld.object.Book;
import com.example.mangaworld.fragment.readBookFragment.ChapFragment;
import com.example.mangaworld.fragment.readBookFragment.CommentFragment;
import com.example.mangaworld.fragment.readBookFragment.SummaryFragment;

public class ViewPagerReadBookAdapter extends FragmentStatePagerAdapter {
    private Book book;
    public ViewPagerReadBookAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    public void setBook(Book book) {
        this.book = book;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChapFragment(book.getIdBook(),book.getNumberChap());
            case 1:
                return new SummaryFragment(book.getIdBook(),book.getListTagCategory(),book.getSummaryBook(),book.getNumberChap());
            default:
                return new CommentFragment(book.getIdBook());
        }
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "CHƯƠNG";
            case 1:
                return "GIỚI THIỆU";
            default:
                return  "BÌNH LUẬN";
        }
    }
}
