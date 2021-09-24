package com.example.mangaworld.Main.SearchFragment.Author;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Extension.Pagination.PaginationRecyclerView;
import com.example.mangaworld.Model.Author;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchAuthor extends Fragment {
    private List<Author> authors;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);
        APIClient.getAPIHome().getAllAuthor().enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(@NonNull Call<List<Author>> call, @NonNull Response<List<Author>> response) {
                if (response.isSuccessful()) {
                    authors = response.body();
                    assert authors != null;
                    setDataFragment(authors, view);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Author>> call, @NonNull Throwable t) {

            }
        });
        return view;
    }

    private void setDataFragment(List<Author> authors, View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rcv_search);
        CardView cardView = view.findViewById(R.id.search_item_card_view);

        SearchView searchView = view.findViewById(R.id.search_fragment_search_view);

        androidx.appcompat.widget.Toolbar mToolBar = view.findViewById(R.id.search_fragment_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(mToolBar);
            setHasOptionsMenu(true);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);

        MainActivity mainActivity = (MainActivity) getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);

        int MAX_ITEM = authors.size();

        SearchAuthorAdapter searchAuthorAdapter = new SearchAuthorAdapter(authors,MAX_ITEM <= 8 ? authors : authors.subList(0, 8),
                id -> Toast.makeText(getContext(),id+"",Toast.LENGTH_SHORT).show());
        recyclerView.setAdapter(searchAuthorAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAuthorAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAuthorAdapter.getFilter().filter(newText);
                return false;
            }
        });
        recyclerView.addOnScrollListener(new PaginationRecyclerView(linearLayoutManager,MAX_ITEM) {
            @Override
            public void setData(int totalItems) {
                cardView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    searchAuthorAdapter.setData(authors.subList(0, Math.min(totalItems + 8, MAX_ITEM)));
                    cardView.setVisibility(View.GONE);
                }, 1500);
            }
        });
    }

}