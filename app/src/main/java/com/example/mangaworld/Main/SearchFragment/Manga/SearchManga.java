package com.example.mangaworld.Main.SearchFragment.Manga;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mangaworld.Interface.SelectItemInRecycleView;
import com.example.mangaworld.R;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Extension.Pagination.PaginationRecyclerView;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Model.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchManga extends Fragment {
    private List<Manga> mangas;
    private RecyclerView recyclerView;
    private SearchMangaAdapter searchMangaAdapter;
    private int MAX_ITEM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);
        MainActivity.hideBottomNav();
        recyclerView = view.findViewById(R.id.rcv_search);
        CardView cardView = view.findViewById(R.id.search_item_card_view);
        SearchView searchView = view.findViewById(R.id.search_fragment_search_view);
        androidx.appcompat.widget.Toolbar mToolBar = view.findViewById(R.id.search_fragment_toolbar);
        MainActivity mainActivity = (MainActivity) requireActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);

        initialInformationSettings(mToolBar,linearLayoutManager);

        APIClient.getAPIHome().getAllManga().enqueue(new Callback<List<Manga>>() {
            @Override
            public void onResponse(@NonNull Call<List<Manga>> call, @NonNull Response<List<Manga>> response) {
                if (response.isSuccessful()) {
                    mangas = response.body();
                    assert mangas != null;
                    setDataFragment(mainActivity);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Manga>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),R.string.ErrorAPI,Toast.LENGTH_SHORT).show();
                MainActivity.showBottomNav();
            }
        });

        searchInformationRegister(searchView,cardView,linearLayoutManager);

        return view;
    }
    private void initialInformationSettings(Toolbar _toolBar,LinearLayoutManager _manager) {
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(_toolBar);
        setHasOptionsMenu(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setLayoutManager(_manager);
    }

    private void setDataFragment(MainActivity _mainActivity) {
        MAX_ITEM = mangas.size();
        searchMangaAdapter = new SearchMangaAdapter(mangas,MAX_ITEM <= 10 ? mangas : mangas.subList(0, 10), new SelectItemInRecycleView() {
            @Override
            public void onClickItemBook(long idManga) {
                _mainActivity.goToInformationMangaScreen(idManga);
            }

            @Override
            public void onClickItemCategory(Long id, boolean isViewMore) {

            }

            @Override
            public void onClickItemIcon(float id) {

            }
        });
        recyclerView.setAdapter(searchMangaAdapter);
        MainActivity.showBottomNav();
    }
    private void searchInformationRegister(SearchView _searchView,CardView _cardView,LinearLayoutManager _manager){
        _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        recyclerView.addOnScrollListener(new PaginationRecyclerView(_manager,MAX_ITEM) {
            @Override
            public void setData(int totalItems) {
                _cardView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    searchMangaAdapter.setData(mangas.subList(0, Math.min(totalItems + 10, MAX_ITEM)));
                    _cardView.setVisibility(View.GONE);
                }, 1500);
            }
        });
    }

}
