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
import com.example.mangaworld.object.Author;

import java.util.ArrayList;
import java.util.List;


public class SearchAuthor extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rcv_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        MainActivity mainActivity = (MainActivity) getActivity();
        SearchAuthorAdapter searchAuthorAdapter = new SearchAuthorAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchAuthorAdapter.setData(setData());
        recyclerView.setAdapter(searchAuthorAdapter);
        return view;
    }

    private List<Author> setData() {
        List<Author> list = new ArrayList<>();
        list.add(new Author("https://scontent.fhph1-2.fna.fbcdn.net/v/t1.6435-9/81905866_1411704438989292_2901155012929388544_n.jpg?_nc_cat=100&ccb=1-3&_nc_sid=09cbfe&_nc_ohc=gOO8la6RgucAX9vJzE6&_nc_ht=scontent.fhph1-2.fna&oh=6a780a02af5ba2804cb64c8c295ac557&oe=60D132AF","Hùng Bá",15));
        list.add(new Author("https://scontent.fhph1-2.fna.fbcdn.net/v/t1.6435-9/81905866_1411704438989292_2901155012929388544_n.jpg?_nc_cat=100&ccb=1-3&_nc_sid=09cbfe&_nc_ohc=gOO8la6RgucAX9vJzE6&_nc_ht=scontent.fhph1-2.fna&oh=6a780a02af5ba2804cb64c8c295ac557&oe=60D132AF","Hùng Bá",15));
        list.add(new Author("https://scontent.fhph1-2.fna.fbcdn.net/v/t1.6435-9/81905866_1411704438989292_2901155012929388544_n.jpg?_nc_cat=100&ccb=1-3&_nc_sid=09cbfe&_nc_ohc=gOO8la6RgucAX9vJzE6&_nc_ht=scontent.fhph1-2.fna&oh=6a780a02af5ba2804cb64c8c295ac557&oe=60D132AF","Hùng Bá",15));
        list.add(new Author("https://scontent.fhph1-2.fna.fbcdn.net/v/t1.6435-9/81905866_1411704438989292_2901155012929388544_n.jpg?_nc_cat=100&ccb=1-3&_nc_sid=09cbfe&_nc_ohc=gOO8la6RgucAX9vJzE6&_nc_ht=scontent.fhph1-2.fna&oh=6a780a02af5ba2804cb64c8c295ac557&oe=60D132AF","Hùng Bá",15));
        list.add(new Author("https://scontent.fhph1-2.fna.fbcdn.net/v/t1.6435-9/81905866_1411704438989292_2901155012929388544_n.jpg?_nc_cat=100&ccb=1-3&_nc_sid=09cbfe&_nc_ohc=gOO8la6RgucAX9vJzE6&_nc_ht=scontent.fhph1-2.fna&oh=6a780a02af5ba2804cb64c8c295ac557&oe=60D132AF","Hùng Bá",15));
        list.add(new Author("https://scontent.fhph1-2.fna.fbcdn.net/v/t1.6435-9/81905866_1411704438989292_2901155012929388544_n.jpg?_nc_cat=100&ccb=1-3&_nc_sid=09cbfe&_nc_ohc=gOO8la6RgucAX9vJzE6&_nc_ht=scontent.fhph1-2.fna&oh=6a780a02af5ba2804cb64c8c295ac557&oe=60D132AF","Hùng Bá",15));
        return list;
    }
}