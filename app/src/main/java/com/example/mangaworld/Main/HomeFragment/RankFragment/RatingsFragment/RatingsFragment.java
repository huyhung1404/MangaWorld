package com.example.mangaworld.Main.HomeFragment.RankFragment.RatingsFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mangaworld.Interface.IsLoadingApi;
import com.example.mangaworld.Interface.SelectItemInRecycleView;
import com.example.mangaworld.Main.HomeFragment.RankFragment.RankFragment;
import com.example.mangaworld.R;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Extension.Pagination.PaginationRecyclerView;
import com.example.mangaworld.Model.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingsFragment extends Fragment {
    private final int type;
    private final float typeAmount;

    private List<Manga> mangas;
    private final IsLoadingApi isLoadingApi;


    public RatingsFragment(int type, float typeAmount, IsLoadingApi isLoadingApi) {
        this.type = type;
        this.typeAmount = typeAmount;
        this.isLoadingApi = isLoadingApi;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_manga, container, false);
        MainActivity.hideBottomNav();
        APIClient.getAPIRank().dataRank(type).enqueue(new Callback<List<Manga>>() {
            @Override
            public void onResponse(@NonNull Call<List<Manga>> call, @NonNull Response<List<Manga>> response) {
                if (response.isSuccessful()) {
                    mangas = response.body();
                    if (mangas != null) {
                        getData(mangas,view);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Manga>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),"Lỗi đường truyền",Toast.LENGTH_SHORT).show();
                MainActivity.showBottomNav();
            }
        });

        return view;
    }

    private void getData(List<Manga> mangas,View view) {
        RecyclerView recyclerView = view.findViewById(R.id.top_manga_rcv);
        CardView cardView = view.findViewById(R.id.top_manga_card_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize(6);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerHorizontal = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);
        while (mangas.remove(null));
        int MAX_ITEM = mangas.size();

        RatingsAdapter ratingsAdapter = new RatingsAdapter(typeAmount, MAX_ITEM <= 8 ? mangas : mangas.subList(0, 8), new SelectItemInRecycleView() {
            @Override
            public void onClickItemBook(long idManga) {
                ((MainActivity) requireActivity()).goToInformationMangaScreen(idManga);
            }

            @Override
            public void onClickItemCategory(Long id, boolean isViewMore) {

            }

            @Override
            public void onClickItemIcon(float id) {

            }
        });
        recyclerView.setAdapter(ratingsAdapter);
        isLoadingApi.loadDone();
        MainActivity.showBottomNav();
        recyclerView.addOnScrollListener(new PaginationRecyclerView(linearLayoutManager,MAX_ITEM) {
            @Override
            public void setData(int totalItems) {
                cardView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    ratingsAdapter.setData(mangas.subList(0, Math.min(totalItems + 8, MAX_ITEM)));
                    cardView.setVisibility(View.GONE);
                }, 1500);
            }
        });

    }
}
