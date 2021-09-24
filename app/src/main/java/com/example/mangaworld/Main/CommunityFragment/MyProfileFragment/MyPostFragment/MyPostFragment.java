package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyPostFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Extension.HideAndShowBottomNavRecycleView;
import com.example.mangaworld.Interface.ChangePageNews;
import com.example.mangaworld.Main.CommunityFragment.NewsFragment.StatusAdapter;
import com.example.mangaworld.Main.CommunityFragment.PostStatus.SuccessMessageFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.News;
import com.example.mangaworld.Model.Community.Status;
import com.example.mangaworld.Model.Message;
import com.example.mangaworld.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPostFragment extends Fragment implements ChangePageNews {
    private long page = 1;
    private final long SIZE = 6;
    private StatusAdapter statusAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_post2, container, false);
        MainActivity.hideBottomNav();

        APIClient.getAPICommunity().getMyPost("Bearer " + MainActivity.user.getToken(), page, SIZE).enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    getData(view, response.body());
                }
                MainActivity.showBottomNav();
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                MainActivity.showBottomNav();
            }
        });
        return view;
    }

    private void getData(View _view, News news) {
        if(news.getItems() == null || news.getItems().size() == 0){
            _view.findViewById(R.id.none_my_post_text).setVisibility(View.VISIBLE);
            return;
        }
        _view.findViewById(R.id.none_my_post_text).setVisibility(View.GONE);

        RecyclerView recyclerView = _view.findViewById(R.id.my_post_recycle_view);
        linearLayoutManager =
                new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize((int) SIZE);

        statusAdapter = new StatusAdapter((MainActivity) requireActivity(), this::setClickPopupMenu, this,R.menu.menu_my_post);
        statusAdapter.setData(news);
        recyclerView.setAdapter(statusAdapter);
    }

    @SuppressLint("NonConstantResourceId")
    private void setClickPopupMenu(PopupMenu popupMenu, Status status) {
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.item_my_post_delete) {
                deletePost(status.getId());
                return true;
            }
            return false;
        });
    }
    private void changePage(long page) {
        APIClient.getAPICommunity().getMyPost("Bearer " + MainActivity.user.getToken(), page, SIZE).enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    statusAdapter.setData(response.body());
                    linearLayoutManager.scrollToPosition(0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void nextPage() {
        changePage(++page);
    }

    @Override
    public void lastPage() {
        changePage(--page);
    }

    @Override
    public void goPage(long page, EditText view) {
        this.page = page;
        changePage(page);
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void deletePost(long idPost){
        APIClient.getAPICommunity().deletePost("Bearer " + MainActivity.user.getToken(),idPost).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if(response.isSuccessful()){
                    changePage(page);
                    new SuccessMessageFragment().show(requireActivity().getSupportFragmentManager(), SuccessMessageFragment.TAG);
                    MainActivity.showBottomNav();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}