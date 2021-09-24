package com.example.mangaworld.Main.SearchFragment.Category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mangaworld.Interface.SelectItemInRecycleView;
import com.example.mangaworld.R;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.ListTagCategory;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchCategory extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);
        AppBarLayout appBarLayout = view.findViewById(R.id.search_fragment_app_bar_layout);
        appBarLayout.setVisibility(View.GONE);

        APIClient.getAPIHome().getAllCategory().enqueue(new Callback<List<ListTagCategory>>() {
            @Override
            public void onResponse(@NonNull Call<List<ListTagCategory>> call, @NonNull Response<List<ListTagCategory>> response) {
                if (response.isSuccessful()) {
                    setData(response.body(), view);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ListTagCategory>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setData(List<ListTagCategory> tagCategories, View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rcv_search);
        recyclerView.setItemViewCacheSize(15);
        MainActivity mainActivity = (MainActivity) requireActivity();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);

        SearchCategoryAdapter searchCategoryAdapter =
                new SearchCategoryAdapter(tagCategories, mainActivity,new SelectItemInRecycleView() {
                    @Override
                    public void onClickItemBook(long idManga) {

                    }

                    @Override
                    public void onClickItemCategory(Long id, boolean isViewMore) {
                        mainActivity.goToCategoryMangaScreen(id, isViewMore);
                    }

                    @Override
                    public void onClickItemIcon(float id) {

                    }
                });
        recyclerView.setAdapter(searchCategoryAdapter);
    }
}