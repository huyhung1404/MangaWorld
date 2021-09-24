package com.example.mangaworld.Main.HomeFragment;

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
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Main.HomeFragment.Adapter.HomeAdapter;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        MainActivity.hideBottomNav();
        RecyclerView rcvCategory = view.findViewById(R.id.rcv_category);
        rcvCategory.setItemViewCacheSize(3);
        MainActivity mainActivity = (MainActivity) requireActivity();

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);

        getHomeData(rcvCategory, mainActivity);
        return view;
    }

    private void getHomeData(RecyclerView recyclerView, MainActivity mMainActivity) {
        APIClient.getAPIHome().dataHomeFragment().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    HomeAdapter homeAdapter = new HomeAdapter(response.body(), new SelectItemInRecycleView() {
                        @Override
                        public void onClickItemBook(long idManga) {
                            mMainActivity.goToInformationMangaScreen(idManga);
                        }

                        @Override
                        public void onClickItemCategory(Long id, boolean isViewMore) {
                            mMainActivity.goToCategoryMangaScreen(id, isViewMore);
                        }

                        @Override
                        public void onClickItemIcon(float id) {
                            mMainActivity.goToRankMangaScreen(id);
                        }
                    });
                    recyclerView.setAdapter(homeAdapter);
                    MainActivity.showBottomNav();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                MainActivity.showBottomNav();
            }
        });
    }
}