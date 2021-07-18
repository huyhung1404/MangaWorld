package com.example.mangaworld.fragment.ReadMangaFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.fragment.BXHFragment.RankFragment;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.object.Chapter;
import com.example.mangaworld.fragment.ReadMangaFragment.ReadMangaAdapter.ChapAdapter;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterFragment extends Fragment {
    private final long idBook;
    private final RankFragment.IsLoadingApi isLoadingApi;
    public ChapterFragment(long idBook, RankFragment.IsLoadingApi isLoadingApi) {
        this.idBook = idBook;
        this.isLoadingApi = isLoadingApi;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_chap, container, false);
        APIClient.getAPIChapter().dataChapFragment(idBook).enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(@NonNull Call<List<Long>> call, @NonNull Response<List<Long>> response) {
                List<Long> listIndex = response.body();
                if (response.isSuccessful() && listIndex != null){
                    RecyclerView rcvChap = mView.findViewById(R.id.rcv_chap);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rcvChap.setLayoutManager(linearLayoutManager);
                    MainActivity mainActivity = (MainActivity) requireActivity();
                    Collections.sort(listIndex);
                    ChapAdapter chapAdapter= new ChapAdapter(position -> mainActivity.nextChapFragment(listIndex,idBook,position,true));
                    chapAdapter.setData(listIndex);
                    rcvChap.setAdapter(chapAdapter);
                    isLoadingApi.loadDone();
                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mainActivity,DividerItemDecoration.VERTICAL);
                    rcvChap.addItemDecoration(itemDecoration);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Long>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });
        return mView;
    }

}