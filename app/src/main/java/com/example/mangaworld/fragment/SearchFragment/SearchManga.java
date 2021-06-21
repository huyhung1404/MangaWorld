package com.example.mangaworld.fragment.SearchFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.object.ListTagCategory;
import com.example.mangaworld.object.Manga;

import java.util.ArrayList;
import java.util.List;

public class SearchManga extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rcv_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        MainActivity mainActivity = (MainActivity) getActivity();

        SearchMangaAdapter searchMangaAdapter = new SearchMangaAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchMangaAdapter.setData(setData(), new CategoryAdapter.IClickItem() {
            @Override
            public void onClickItemBook(Manga manga) {
                mainActivity.nextReadMangaActivity(manga);
            }

            @Override
            public void onClickItemCategory(Long id) {

            }

            @Override
            public void onClickItemIcon(float id) {

            }
        });
        recyclerView.setAdapter(searchMangaAdapter);
        return view;
    }

    private List<Manga> setData() {
        List<ListTagCategory> listTagCategory = new ArrayList<>();
        listTagCategory.add(new ListTagCategory((long) 5,"Thể loại"));

        List<Manga> listManga = new ArrayList<>();
        listManga.add(new Manga(1, "Nguyên Tôn", "https://cdn.nettruyen.vn/file/nettruyen/thumbnails/nguyen-ton.jpg",
                14, 20, 30, "Hùng Bá", false, listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết .Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hếtDùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết."));
        listManga.add(new Manga(1, "Nguyên Tôn", "https://cdn.nettruyen.vn/file/nettruyen/thumbnails/nguyen-ton.jpg",
                14, 20, 30, "Hùng Bá", false, listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết .Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hếtDùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết."));
        listManga.add(new Manga(1, "Nguyên Tôn", "https://cdn.nettruyen.vn/file/nettruyen/thumbnails/nguyen-ton.jpg",
                14, 20, 30, "Hùng Bá", false, listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết .Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hếtDùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết."));
        listManga.add(new Manga(1, "Nguyên Tôn", "https://cdn.nettruyen.vn/file/nettruyen/thumbnails/nguyen-ton.jpg",
                14, 20, 30, "Hùng Bá", false, listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết .Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hếtDùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết."));
        return listManga;
    }
}