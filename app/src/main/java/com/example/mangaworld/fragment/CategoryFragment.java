package com.example.mangaworld.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.main.PaginationRecyclerView;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.mainActivityAdapter.MangaInCategoryAdapter;
import com.example.mangaworld.object.Category;
import com.example.mangaworld.object.Manga;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    public static final String TAG = CategoryFragment.class.getName();
    private Long idCategory;
    private boolean isViewMore;
    private String stringGet, nameCategory;
    private List<Manga> mangas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        MainActivity.hideBottomNav();
        //Bundle
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            this.idCategory = (Long) bundleReceive.get("id_category");
            this.isViewMore = (boolean) bundleReceive.get("isViewMore");
        }
        //Init
        if (!isViewMore) {
            APIClient.getAPIHome().dataCategoryFragment(idCategory).enqueue(new Callback<Category>() {
                @Override
                public void onResponse(@NonNull Call<Category> call, @NonNull Response<Category> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        mangas = response.body().getMangas();
                        setData(response.body().getNameCategory(), view);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Category> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    MainActivity.showBottomNav();
                }
            });
            return view;
        }
        if (idCategory == 2) {
            stringGet = "maybe-you-will-like";
            nameCategory = "Có thể bạn cũng muốn xem";
        } else if (idCategory == 3) {
            stringGet = "recently-updated-comic";
            nameCategory = "Mới cập nhập";
        } else if (idCategory == 4) {
            stringGet = "hot-comic";
            nameCategory = "Truyện HOT";
        }
        APIClient.getAPIHome().dataViewMore(stringGet).enqueue(new Callback<List<Manga>>() {
            @Override
            public void onResponse(@NonNull Call<List<Manga>> call, @NonNull Response<List<Manga>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    mangas = response.body();
                    setData(nameCategory, view);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Manga>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                MainActivity.showBottomNav();
            }
        });
        return view;
    }

    private void setData(String nameTitle, View view) {
        //init
        RecyclerView recyclerView = view.findViewById(R.id.rcv_category_fragment);
        CardView cardView = view.findViewById(R.id.card_view_progress_bar_category);
        androidx.appcompat.widget.Toolbar mToolBar = view.findViewById(R.id.tool_bar_category_fragment);
        //ToolBar
        MainActivity mMainActivity = (MainActivity) requireActivity();
        mMainActivity.setSupportActionBar(mToolBar);
        ActionBar actionBar = mMainActivity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        int MAX_ITEM = mangas.size();
        //Rcv
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(8);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerHorizontal = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);
        //SetData
        actionBar.setTitle(nameTitle);
        setHasOptionsMenu(true);
        //Manga Adapter
        MangaInCategoryAdapter mangaAdapter = new MangaInCategoryAdapter(MAX_ITEM <= 8 ? mangas : mangas.subList(0, 8), new CategoryAdapter.IClickItem() {
            @Override
            public void onClickItemBook(long idManga) {
                mMainActivity.nextReadMangaActivity(idManga);
            }

            @Override
            public void onClickItemCategory(Long id, boolean isViewMore) {

            }

            @Override
            public void onClickItemIcon(float id) {

            }
        });
        recyclerView.setAdapter(mangaAdapter);
        MainActivity.showBottomNav();
        recyclerView.addOnScrollListener(new PaginationRecyclerView(linearLayoutManager,MAX_ITEM) {
            @Override
            public void setData(int totalItems) {
                cardView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    mangaAdapter.setData(mangas.subList(0, Math.min(totalItems + 8, MAX_ITEM)));
                    cardView.setVisibility(View.GONE);
                }, 1500);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(CategoryFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

}