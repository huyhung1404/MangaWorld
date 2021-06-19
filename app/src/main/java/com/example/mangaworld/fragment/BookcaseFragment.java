package com.example.mangaworld.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.mainActivityAdapter.MangaCaseAdapter;
import com.example.mangaworld.object.Manga;

import java.util.ArrayList;
import java.util.List;

public class BookcaseFragment extends Fragment {
    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_manga_case, container, false);
        RecyclerView recyclerView = mView.findViewById(R.id.rcv_book_case);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        MangaCaseAdapter mangaCaseAdapter = new MangaCaseAdapter();
        mangaCaseAdapter.setData(setDataAdapter());
        recyclerView.setAdapter(mangaCaseAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return mView;
    }

    private List<Manga> setDataAdapter() {
        List<Manga> listManga = new ArrayList<>();
        listManga.add(new Manga(1,"Nguyên Tôn","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/nguyen-ton.jpg",30));
        listManga.add(new Manga(2,"Hỏa Sơn Quyền","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/hoa-son-quyen.jpg", 20));
        return listManga;
    }
}