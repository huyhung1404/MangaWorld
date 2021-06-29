package com.example.mangaworld.fragment.SearchFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangaworld.R;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.object.ListTagCategory;
import com.example.mangaworld.object.Manga;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;


public class SearchCategory extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);
        AppBarLayout  appBarLayout = view.findViewById(R.id.search_fragment_app_bar_layout);
        appBarLayout.setVisibility(View.GONE);

        RecyclerView recyclerView = view.findViewById(R.id.rcv_search);
        recyclerView.setItemViewCacheSize(8);
        MainActivity mainActivity = (MainActivity) requireActivity();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        SearchCategoryAdapter searchCategoryAdapter = new SearchCategoryAdapter(setData(), mainActivity,
                new CategoryAdapter.IClickItem() {
            @Override
            public void onClickItemBook(Manga manga) {

            }

            @Override
            public void onClickItemCategory(Long id, boolean isViewMore) {
                mainActivity.nextCategoryFragment(id,isViewMore);
            }

            @Override
            public void onClickItemIcon(float id) {

            }
        });
        recyclerView.setAdapter(searchCategoryAdapter);
        return view;
    }

    private List<ListTagCategory> setData() {
        List<ListTagCategory> list = new ArrayList<>();
        list.add(new ListTagCategory((long)1,"Ngôn tình"));
        list.add(new ListTagCategory((long)2,"Truyện teen"));
        list.add(new ListTagCategory((long)3,"Tiên hiệp"));
        list.add(new ListTagCategory((long)4,"Kiếm hiệp"));
        list.add(new ListTagCategory((long)5,"Hiện đại"));
        list.add(new ListTagCategory((long)6,"Đô thị"));
        return list;
    }
}