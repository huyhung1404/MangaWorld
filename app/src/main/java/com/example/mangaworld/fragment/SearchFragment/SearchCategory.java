package com.example.mangaworld.fragment.SearchFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class SearchCategory extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rcv_search);
        recyclerView.setItemViewCacheSize(10);
        MainActivity mainActivity = (MainActivity) getActivity();

        SearchCategoryAdapter searchCategoryAdapter = new SearchCategoryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchCategoryAdapter.setData(setData(),mainActivity);
        recyclerView.setAdapter(searchCategoryAdapter);
        return view;
    }

    private List<String> setData() {
        List<String> list = new ArrayList<>();
        list.add("Tiên hiệp");
        list.add("Hệ thống");
        list.add("Tiên hiệp huyền huyễn");
        list.add("Tiên hiệp");
        list.add("Tiên hiệp");
        list.add("Tiên hiệp");
        list.add("Tiên hiệp");
        list.add("Tiên hiệp");
        list.add("Tiên hiệp");
        list.add("Tiên hiệp");
        list.add("Tiên hiệp");
        return list;
    }
}