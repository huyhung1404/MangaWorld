package com.example.mangaworld.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.object.Manga;
import com.example.mangaworld.object.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        //Init recycler view
        RecyclerView rcvCategory = mView.findViewById(R.id.rcv_category);
        rcvCategory.setHasFixedSize(true);
        rcvCategory.setItemViewCacheSize(5);
        //
        MainActivity mMainActivity = (MainActivity) requireActivity();
        //Recycler view
        CategoryAdapter categoryAdapter = new CategoryAdapter(mMainActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity, RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);
        //Set data
        getHomeData(categoryAdapter, mMainActivity);
        rcvCategory.setAdapter(categoryAdapter);
        return mView;
    }

    private void getHomeData(CategoryAdapter categoryAdapter, MainActivity mMainActivity) {
        APIClient.getAPIHome().dataHomeFragment().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoryAdapter.setData(response.body(), new CategoryAdapter.IClickItem() {
                        @Override
                        public void onClickItemBook(Manga manga) {
                            mMainActivity.nextReadMangaActivity(manga);
                        }

                        @Override
                        public void onClickItemCategory(Long id) {
                            mMainActivity.nextCategoryFragment(id);
                        }

                        @Override
                        public void onClickItemIcon(float id) {
                            mMainActivity.nextBXHFragment(id);
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}