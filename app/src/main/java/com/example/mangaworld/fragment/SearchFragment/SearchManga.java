package com.example.mangaworld.fragment.SearchFragment;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.main.PaginationRecyclerView;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.object.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchManga extends Fragment {
    private List<Manga> mangas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);
        APIClient.getAPIHome().getAllManga().enqueue(new Callback<List<Manga>>() {
            @Override
            public void onResponse(@NonNull Call<List<Manga>> call, @NonNull Response<List<Manga>> response) {
                if (response.isSuccessful()) {
                    mangas = response.body();
                    assert mangas != null;
                    setDataFragment(mangas, view);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Manga>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),"Lỗi đường truyền",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void setDataFragment(List<Manga> mangas, View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rcv_search);
        CardView cardView = view.findViewById(R.id.search_item_card_view);

        SearchView searchView = view.findViewById(R.id.search_fragment_search_view);

        androidx.appcompat.widget.Toolbar mToolBar = view.findViewById(R.id.search_fragment_toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(mToolBar);
        setHasOptionsMenu(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);

        MainActivity mainActivity = (MainActivity) requireActivity();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);

        int MAX_ITEM = mangas.size();

        SearchMangaAdapter searchMangaAdapter = new SearchMangaAdapter(mangas,MAX_ITEM <= 10 ? mangas : mangas.subList(0, 10), new CategoryAdapter.IClickItem() {
            @Override
            public void onClickItemBook(Manga manga) {
                mainActivity.nextReadMangaActivity(manga);
            }

            @Override
            public void onClickItemCategory(Long id, boolean isViewMore) {

            }

            @Override
            public void onClickItemIcon(float id) {

            }
        });
        recyclerView.setAdapter(searchMangaAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMangaAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMangaAdapter.getFilter().filter(newText);
                return false;
            }
        });

        recyclerView.addOnScrollListener(new PaginationRecyclerView(linearLayoutManager,MAX_ITEM) {
            @Override
            public void setData(int totalItems) {
                cardView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    searchMangaAdapter.setData(mangas.subList(0, Math.min(totalItems + 10, MAX_ITEM)));
                    cardView.setVisibility(View.GONE);
                }, 1500);
            }
        });
    }

}
