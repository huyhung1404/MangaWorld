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
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.object.Chapter;
import com.example.mangaworld.fragment.ReadMangaFragment.ReadMangaAdapter.ChapAdapter;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterFragment extends Fragment {
    private final long idBook;
    public ChapterFragment(long idBook) {
        this.idBook = idBook;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_chap, container, false);
        RecyclerView rcvChap = mView.findViewById(R.id.rcv_chap);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvChap.setLayoutManager(linearLayoutManager);

        getChapterData(rcvChap, (MainActivity) requireActivity());
        return mView;
    }
    private void getChapterData(RecyclerView rcvChap, MainActivity mainActivity){
        APIClient.getAPIChapter().dataChapFragment(idBook).enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(@NonNull Call<Chapter> call, @NonNull Response<Chapter> response) {
                Chapter chapter = response.body();
                if (response.isSuccessful() && chapter != null){
                    Collections.sort(chapter.getIndexChapter());
                    ChapAdapter chapAdapter= new ChapAdapter(position -> mainActivity.nextChapFragment(chapter,idBook,position,true));
                    chapAdapter.setData(chapter.getIndexChapter());
                    rcvChap.setAdapter(chapAdapter);
                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mainActivity,DividerItemDecoration.VERTICAL);
                    rcvChap.addItemDecoration(itemDecoration);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Chapter> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });
    }

}