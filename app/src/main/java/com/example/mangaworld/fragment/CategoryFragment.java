package com.example.mangaworld.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.mainActivityAdapter.MangaInCategoryAdapter;
import com.example.mangaworld.object.Category;
import com.example.mangaworld.object.Manga;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    public static final String TAG = CategoryFragment.class.getName();
    private Long idCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        //Bundle
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            this.idCategory = (Long) bundleReceive.get("id_category");
        }
        //Init
        APIClient.getAPIHome().dataCategoryFragment(idCategory).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(@NonNull Call<Category> call, @NonNull Response<Category> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    setData(response.body(), view);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Category> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void setData(Category category, View view) {
        //init
        RecyclerView recyclerView = view.findViewById(R.id.rcv_category_fragment);
        androidx.appcompat.widget.Toolbar mToolBar = view.findViewById(R.id.tool_bar_category_fragment);
        //ToolBar
        MainActivity mMainActivity = (MainActivity) getActivity();
        assert mMainActivity != null;
        mMainActivity.setSupportActionBar(mToolBar);
        ActionBar actionBar = mMainActivity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Rcv
        recyclerView.setItemViewCacheSize(18);
        MangaInCategoryAdapter mangaAdapter = new MangaInCategoryAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mMainActivity, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        //SetData
        actionBar.setTitle(category.getNameCategory());
        setHasOptionsMenu(true);

        mangaAdapter.setData(category.getMangas(), new CategoryAdapter.IClickItem() {
            @Override
            public void onClickItemBook(Manga manga) {
                mMainActivity.nextReadMangaActivity(manga);
            }

            @Override
            public void onClickItemCategory(Long id) {

            }
        });
        recyclerView.setAdapter(mangaAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack(CategoryFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return true;
    }
}