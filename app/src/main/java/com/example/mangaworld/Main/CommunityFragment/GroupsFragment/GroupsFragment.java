package com.example.mangaworld.Main.CommunityFragment.GroupsFragment;

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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Extension.HideAndShowBottomNavRecycleView;
import com.example.mangaworld.Interface.ChangePageNews;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.InformationGroup.InformationGroupFragment;
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.ViewMoreGroup.ViewMoreGroupsFragment;
import com.example.mangaworld.Main.CommunityFragment.NewsFragment.StatusAdapter;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.Model.Community.News;
import com.example.mangaworld.Model.Community.Status;
import com.example.mangaworld.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GroupsFragment extends Fragment implements ChangePageNews {
    private final int[] groupsRecommend = new int[]{
            R.id.group1,
            R.id.group2,
            R.id.group3,
            R.id.group4
    };
    private long page = 1;
    private final long SIZE = 6;
    private StatusAdapter statusAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        initGroupsRecommend(view);
        MainActivity.hideBottomNav();
        APIClient.getAPICommunity().getFeaturedPosts("Bearer " + MainActivity.user.getToken(), page, SIZE).enqueue(new Callback<News>() {
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
                Toast.makeText(getContext(), "Lỗi lấy tin đề cử", Toast.LENGTH_SHORT).show();
                MainActivity.showBottomNav();
            }
        });
        view.findViewById(R.id.view_more_groups).setOnClickListener(v -> CommunityFragment
                .GoToScreenInCommunity(new ViewMoreGroupsFragment(),ViewMoreGroupsFragment.TAG));
        return view;
    }

    private void getData(View view, News news) {
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_groups);
        linearLayoutManager =
                new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize((int) SIZE);
        statusAdapter = new StatusAdapter((MainActivity) requireActivity(), this::setClickPopupMenu, this,R.menu.menu_status);
        statusAdapter.setData(news);
        recyclerView.setAdapter(statusAdapter);
        recyclerView.addOnScrollListener(new HideAndShowBottomNavRecycleView(linearLayoutManager));
    }

    private void initGroupsRecommend(View _view) {
        APIClient.getAPICommunity().getGroupRecommend("Bearer " + MainActivity.user.getToken()).enqueue(new Callback<List<Groups>>() {
            @Override
            public void onResponse(@NonNull Call<List<Groups>> call, @NonNull Response<List<Groups>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    for (int i = 0; i < 4; ++i) {
                        createGroupRecommend(_view.findViewById(groupsRecommend[i]), response.body().get(i));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Groups>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi lấy nhóm đề cử", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createGroupRecommend(CardView view, Groups group) {
        CircleImageView circleImageView = view.findViewById(R.id.group_item_image);
        Glide.with(circleImageView.getContext())
                .load(group.getAvatarGroup())
                .into(circleImageView);
        ((TextView) view.findViewById(R.id.group_item_name)).setText(group.getName());
        ((TextView) view.findViewById(R.id.group_item_content)).setText(group.getDescription());
        view.setOnClickListener(v -> CommunityFragment
                .GoToScreenInCommunity(new InformationGroupFragment(group.getId()), InformationGroupFragment.TAG));
    }

    @SuppressLint("NonConstantResourceId")
    private void setClickPopupMenu(PopupMenu popupMenu, Status status) {
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_1:
//                    Log.e("tag", "item1" + position);
                    return true;
                case R.id.item_2:
//                    Log.e("tag", "item2" + position);
                    return true;
                default:
                    return false;
            }
        });
    }

    private void changePage(long page) {
        APIClient.getAPICommunity().getFeaturedPosts("Bearer " + MainActivity.user.getToken(), page, SIZE).enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    statusAdapter.setData(response.body());
                    linearLayoutManager.scrollToPosition(0);
                    MainActivity.showBottomNav();
                }
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                MainActivity.showBottomNav();
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