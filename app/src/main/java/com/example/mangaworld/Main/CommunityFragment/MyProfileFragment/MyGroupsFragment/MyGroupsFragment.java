package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Extension.Pagination.PaginationRecyclerView;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.MyGroupCreatedFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.Model.Community.GroupsCallBack;
import com.example.mangaworld.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyGroupsFragment extends Fragment {
    private long page;
    private final long SIZE = 10;
    private MyGroupsAdapter myGroupsAdapter;
    private List<Groups> groupsList;
    private CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_groups, container, false);
        page = 1;
        APIClient.getAPICommunity().getGroupsJoined("Bearer " + MainActivity.user.getToken(), page, SIZE).enqueue(new Callback<GroupsCallBack>() {
            @Override
            public void onResponse(@NonNull Call<GroupsCallBack> call, @NonNull Response<GroupsCallBack> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    groupsList = response.body().getItems();
                    getDataGroups(view, (int) response.body().getTotal());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GroupsCallBack> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy danh dách nhóm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        initButtonMyGroup(view);
        return view;
    }

    private void getDataGroups(View view, int total) {
        if(groupsList == null || groupsList.size() == 0){
            view.findViewById(R.id.none_my_group_join_text).setVisibility(View.VISIBLE);
            return;
        }
        view.findViewById(R.id.none_my_group_join_text).setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.my_group_recycler_view);
        cardView = view.findViewById(R.id.card_view_progress_bar_my_group);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize((int) SIZE);
        myGroupsAdapter = new MyGroupsAdapter();
        myGroupsAdapter.setData(groupsList);
        recyclerView.setAdapter(myGroupsAdapter);
        recyclerView.addOnScrollListener(new PaginationRecyclerView(linearLayoutManager, total) {
            @Override
            public void setData(int totalItems) {
                cardView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> changeGroupList(), 1500);
            }
        });
    }

    private void initButtonMyGroup(View _view) {
        _view.findViewById(R.id.my_group_create_button).setOnClickListener(v -> CommunityFragment
                .GoToScreenInCommunity(new MyGroupCreatedFragment(), MyGroupCreatedFragment.TAG));
    }

    private void changeGroupList() {
        APIClient.getAPICommunity().getGroupsJoined("Bearer " + MainActivity.user.getToken(), ++page, SIZE).enqueue(new Callback<GroupsCallBack>() {
            @Override
            public void onResponse(@NonNull Call<GroupsCallBack> call, @NonNull Response<GroupsCallBack> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    groupsList.addAll(response.body().getItems());
                    myGroupsAdapter.setData(groupsList);
                    cardView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GroupsCallBack> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy danh dách nhóm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}