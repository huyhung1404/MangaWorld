package com.example.mangaworld.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.mangaworld.R;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.fragment.LoginFragment.LoginFragment;
import com.example.mangaworld.mainActivityAdapter.MangaCaseAdapter;
import com.example.mangaworld.mainActivityAdapter.StartSnapHelper;
import com.example.mangaworld.object.Manga;

import java.util.ArrayList;
import java.util.List;

public class BookcaseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView;
        if (!MainActivity.isLogin){
            mView = inflater.inflate(R.layout.requires_login, container, false);
            TextView textView = mView.findViewById(R.id.btn_next_login_fragment);
            textView.setOnClickListener(v -> {
                ((MainActivity) requireActivity()).loadFragment(new LoginFragment());
                MainActivity.bottomNavigationView.getMenu().findItem(R.id.menuInfo).setChecked(true);
            });
            return mView;
        }
        mView = inflater.inflate(R.layout.fragment_manga_case, container, false);
        RecyclerView recyclerView = mView.findViewById(R.id.rcv_book_case);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(6);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        SnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(recyclerView);

        MangaCaseAdapter mangaCaseAdapter = new MangaCaseAdapter();
        mangaCaseAdapter.setData(setDataAdapter());
        recyclerView.setAdapter(mangaCaseAdapter);
        return mView;
    }

    private List<Manga> setDataAdapter() {
        List<Manga> listManga = new ArrayList<>();
        listManga.add(new Manga(1,"Nguyên Tôn","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/nguyen-ton.jpg",30));
        listManga.add(new Manga(2,"Hỏa Sơn Quyền","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/hoa-son-quyen.jpg", 20));
        listManga.add(new Manga(2,"Hỏa Sơn Quyền","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/hoa-son-quyen.jpg", 20));
        listManga.add(new Manga(2,"Hỏa Sơn Quyền","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/hoa-son-quyen.jpg", 20));
        listManga.add(new Manga(2,"Hỏa Sơn Quyền","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/hoa-son-quyen.jpg", 20));
        listManga.add(new Manga(2,"Hỏa Sơn Quyền","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/hoa-son-quyen.jpg", 20));
        listManga.add(new Manga(2,"Hỏa Sơn Quyền","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/hoa-son-quyen.jpg", 20));
        listManga.add(new Manga(2,"Hỏa Sơn Quyền","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/hoa-son-quyen.jpg", 20));
        return listManga;
    }
}