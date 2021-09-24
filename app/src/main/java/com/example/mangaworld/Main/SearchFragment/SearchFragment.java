package com.example.mangaworld.Main.SearchFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mangaworld.Main.SearchFragment.Author.SearchAuthor;
import com.example.mangaworld.Main.SearchFragment.Category.SearchCategory;
import com.example.mangaworld.Main.SearchFragment.Manga.SearchManga;
import com.example.mangaworld.R;
import com.example.mangaworld.Main.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_search_fragment);
        ViewPager viewPager = view.findViewById(R.id.view_pager_search_fragment);
        viewPager.setOffscreenPageLimit(2);
        ViewPagerAdapter searchAdapter = new ViewPagerAdapter(getChildFragmentManager());
        searchAdapter.AddFragment(new SearchManga(), "Truyện");
        searchAdapter.AddFragment(new SearchCategory(), "Thể loại");
        searchAdapter.AddFragment(new SearchAuthor(), "Tác giả");
        viewPager.setAdapter(searchAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}