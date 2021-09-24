package com.example.mangaworld.Main.BookcaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.mangaworld.Interface.SelectItemInRecycleView;
import com.example.mangaworld.R;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Main.InfoUserFragment.AccountManager.LoginFragment;
import com.example.mangaworld.Main.BookcaseFragment.Adapter.BookcaseAdapter;
import com.example.mangaworld.Extension.StartSnapHelper;
import com.example.mangaworld.Model.Manga;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookcaseFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!MainActivity.isLogin) {
            return createRequiresLoginScreen(inflater,container);
        }
        return createMangaCaseScreen(inflater,container);
    }
    private View createRequiresLoginScreen(@NonNull LayoutInflater _inflater, ViewGroup _container){
        View view = _inflater.inflate(R.layout.requires_login, _container, false);

        view.findViewById(R.id.relative_layout_requires_login).setBackgroundResource(R.drawable.background_book_case);
        view.findViewById(R.id.btn_next_login_fragment).setOnClickListener(v -> {
            ((MainActivity) requireActivity()).loadFragment(new LoginFragment());
            MainActivity.bottomNavigationView.getMenu().findItem(R.id.menuInfo).setChecked(true);
        });
        return view;
    }
    private View createMangaCaseScreen(@NonNull LayoutInflater _inflater, ViewGroup _container){
        View view = _inflater.inflate(R.layout.fragment_manga_case, _container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rcv_book_case);
        settingRecycleView(recyclerView);
        APIClient.getAPIChapter().getMangaInBookCase("Bearer " + MainActivity.user.getToken())
                .enqueue(new Callback<List<Manga>>() {
            @Override
            public void onResponse(@NonNull Call<List<Manga>> call, @NonNull Response<List<Manga>> response) {
                if (response.isSuccessful()) {
                    setDataAdapter(response.body(), recyclerView);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Manga>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void settingRecycleView(RecyclerView _recyclerView){
        _recyclerView.setHasFixedSize(true);
        _recyclerView.setItemViewCacheSize(6);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        _recyclerView.setLayoutManager(linearLayoutManager);

        SnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(_recyclerView);
    }
    private void setDataAdapter(List<Manga> mangas, RecyclerView recyclerView) {
        BookcaseAdapter bookcaseAdapter = new BookcaseAdapter();
        bookcaseAdapter.setData(requireContext(), mangas, new SelectItemInRecycleView() {
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
        recyclerView.setAdapter(bookcaseAdapter);
    }
}