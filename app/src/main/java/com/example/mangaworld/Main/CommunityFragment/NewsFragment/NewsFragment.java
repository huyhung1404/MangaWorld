package com.example.mangaworld.Main.CommunityFragment.NewsFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Extension.HideAndShowBottomNavRecycleView;
import com.example.mangaworld.Interface.ChangePageNews;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.CommunityFragment.PostStatus.PostStatusFragment;
import com.example.mangaworld.Main.CommunityFragment.PostStatus.SelectGroupsFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.News;
import com.example.mangaworld.Model.Community.Status;
import com.example.mangaworld.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment implements ChangePageNews {
    private long page = 1;
    private final long SIZE = 6;
    private StatusAdapter statusAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        MainActivity.hideBottomNav();
        Glide.with(requireContext()).load(MainActivity.user.getAvatar())
                .into((CircleImageView) view.findViewById(R.id.img_avatar_community));
        APIClient.getAPICommunity().getDataNews("Bearer " + MainActivity.user.getToken(), page, SIZE).enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    getDataNews(view, response.body());
                }
                MainActivity.showBottomNav();
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                MainActivity.showBottomNav();
            }
        });
        initPostCardView(view);
        return view;
    }

    private void initPostCardView(View _view) {
        _view.findViewById(R.id.card_view_news).setOnClickListener(v -> CommunityFragment
                .GoToScreenInCommunity(new PostStatusFragment((bundle) -> {
                    SelectGroupsFragment selectGroupsFragment = new SelectGroupsFragment();
                    selectGroupsFragment.setArguments(bundle);
                    CommunityFragment.GoToScreenInCommunity(selectGroupsFragment, SelectGroupsFragment.TAG);
                }), PostStatusFragment.TAG));
    }

    @SuppressLint("NonConstantResourceId")
    private void setClickPopupMenu(PopupMenu popupMenu, Status status) {
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_1:
                    Log.e("tag", "item1");
                    return true;
                case R.id.item_2:
                    Log.e("tag", "item2");
                    return true;
                default:
                    return false;
            }
        });
    }

    private void getDataNews(View _view, News news) {
        RecyclerView recyclerView = _view.findViewById(R.id.recycle_view_news);
        TextView noneNews = _view.findViewById(R.id.none_news_text);

        if (news.getItems() == null || news.getItems().size() == 0) {
            recyclerView.setVisibility(View.GONE);
            noneNews.setVisibility(View.VISIBLE);
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        noneNews.setVisibility(View.GONE);
        linearLayoutManager =
                new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize((int) SIZE);
        statusAdapter = new StatusAdapter((MainActivity) requireActivity(), this::setClickPopupMenu, this, R.menu.menu_status);
        statusAdapter.setData(news);
        recyclerView.setAdapter(statusAdapter);
        recyclerView.addOnScrollListener(new HideAndShowBottomNavRecycleView(linearLayoutManager));
    }

    private void changePage(long page) {
        APIClient.getAPICommunity().getDataNews("Bearer " + MainActivity.user.getToken(), page, SIZE).enqueue(new Callback<News>() {
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
}