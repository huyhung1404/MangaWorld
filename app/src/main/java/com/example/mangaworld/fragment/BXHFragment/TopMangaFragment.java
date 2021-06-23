package com.example.mangaworld.fragment.BXHFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.object.Category;
import com.example.mangaworld.object.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopMangaFragment extends Fragment {
    private int type;
    private float typeAmount;

    public TopMangaFragment(int type, float typeAmount) {
        this.type = type;
        this.typeAmount = typeAmount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_manga, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rcv_top_manga);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        RankFragmentAdapter rankFragmentAdapter = new RankFragmentAdapter(typeAmount);
        getData(rankFragmentAdapter);
        recyclerView.setAdapter(rankFragmentAdapter);
        DividerItemDecoration dividerHorizontal = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);
        return view;
    }

    private void getData(RankFragmentAdapter rankFragmentAdapter) {
        APIClient.getAPIRank().dataRank(type).enqueue(new Callback<List<Manga>>() {
            @Override
            public void onResponse(@NonNull Call<List<Manga>> call, @NonNull Response<List<Manga>> response) {
                if (response.isSuccessful()) {
                    rankFragmentAdapter.setData(response.body(), new CategoryAdapter.IClickItem() {
                        @Override
                        public void onClickItemBook(Manga manga) {
                            ((MainActivity) requireActivity()).nextReadMangaActivity(manga);
                        }

                        @Override
                        public void onClickItemCategory(Long id,boolean isViewMore) {

                        }

                        @Override
                        public void onClickItemIcon(float id) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Manga>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),"Lỗi đường truyền",Toast.LENGTH_SHORT).show();
            }
        });

    }


}