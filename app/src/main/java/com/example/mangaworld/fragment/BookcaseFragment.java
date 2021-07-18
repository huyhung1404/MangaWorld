package com.example.mangaworld.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.mangaworld.R;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.fragment.LoginFragment.LoginFragment;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.mainActivityAdapter.MangaCaseAdapter;
import com.example.mangaworld.mainActivityAdapter.StartSnapHelper;
import com.example.mangaworld.object.Manga;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookcaseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView;
        MainActivity.hideBottomNav();
        if (!MainActivity.isLogin) {
            mView = inflater.inflate(R.layout.requires_login, container, false);
            TextView textView = mView.findViewById(R.id.btn_next_login_fragment);
            textView.setOnClickListener(v -> {
                ((MainActivity) requireActivity()).loadFragment(new LoginFragment());
                MainActivity.bottomNavigationView.getMenu().findItem(R.id.menuInfo).setChecked(true);
            });
            MainActivity.showBottomNav();
            return mView;
        }
        mView = inflater.inflate(R.layout.fragment_manga_case, container, false);
        RecyclerView recyclerView = mView.findViewById(R.id.rcv_book_case);
        APIClient.getAPIChapter().getMangaInBookCase("Bearer " + MainActivity.user.getToken()).enqueue(new Callback<List<Manga>>() {
            @Override
            public void onResponse(@NonNull Call<List<Manga>> call, @NonNull Response<List<Manga>> response) {
                if (response.isSuccessful()) {
                    setData(response.body(), recyclerView);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Manga>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

        return mView;
    }

    private void setData(List<Manga> mangas, RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(6);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        SnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(recyclerView);

        MangaCaseAdapter mangaCaseAdapter = new MangaCaseAdapter();
        mangaCaseAdapter.setData(requireContext(), mangas, new CategoryAdapter.IClickItem() {
            @Override
            public void onClickItemBook(long idManga) {
                ((MainActivity) requireActivity()).nextReadMangaActivity(idManga);
            }

            @Override
            public void onClickItemCategory(Long id, boolean isViewMore) {

            }

            @Override
            public void onClickItemIcon(float id) {

            }
        });
        recyclerView.setAdapter(mangaCaseAdapter);
        MainActivity.showBottomNav();
    }
}