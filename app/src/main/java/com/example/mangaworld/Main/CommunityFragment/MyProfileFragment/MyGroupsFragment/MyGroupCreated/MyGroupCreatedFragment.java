package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Extension.Pagination.PaginationRecyclerView;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CallBackItems;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyGroupCreatedFragment extends Fragment {
    public static final String TAG = MyGroupCreatedFragment.class.getName();
    private long page;
    private final long SIZE = 8;
    private MyGroupCreatedAdapter myGroupCreatedAdapter;
    private List<Groups> groupsList;
    private CardView cardView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_group_created, container, false);
        page = 1;
        APIClient.getAPICommunity().getMyGroupCreated("Bearer " + MainActivity.user.getToken(), page, SIZE)
                .enqueue(new Callback<CallBackItems<Groups>>() {
                    @Override
                    public void onResponse(@NonNull Call<CallBackItems<Groups>> call, @NonNull Response<CallBackItems<Groups>> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            groupsList = response.body().getItems();
                            getDataGroups(view, (int) response.body().getTotal());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CallBackItems<Groups>> call, @NonNull Throwable t) {
                        Toast.makeText(requireContext(), "Lấy danh dách nhóm thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
        initToolBar(view);
        return view;
    }

    private void getDataGroups(View view, int total) {
        if(groupsList == null || groupsList.size() == 0){
            view.findViewById(R.id.none_my_group_create_text).setVisibility(View.VISIBLE);
            return;
        }
        view.findViewById(R.id.none_my_group_create_text).setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_group_created);
        cardView = view.findViewById(R.id.card_view_progress_bar_my_created);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize((int) SIZE);
        myGroupCreatedAdapter = new MyGroupCreatedAdapter();
        myGroupCreatedAdapter.setData(groupsList);
        recyclerView.setAdapter(myGroupCreatedAdapter);
        DividerItemDecoration dividerHorizontal = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);
        recyclerView.addOnScrollListener(new PaginationRecyclerView(linearLayoutManager, total) {
            @Override
            public void setData(int totalItems) {
                cardView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> changeGroupList(), 1500);
            }
        });
    }

    private void initToolBar(View _view) {
        Toolbar toolbar = _view.findViewById(R.id.group_create_toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Nhóm của tôi");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(MyGroupCreatedFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_group, menu);
        MenuItem setting = menu.findItem(R.id.create_group);
        setting.setOnMenuItemClickListener(item -> {
            CommunityFragment
                    .GoToScreenInCommunity(new CreateNewGroupFragment(), CreateNewGroupFragment.TAG);
            return true;
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void changeGroupList() {
        APIClient.getAPICommunity().getMyGroupCreated("Bearer " + MainActivity.user.getToken(), ++page, SIZE).enqueue(new Callback<CallBackItems<Groups>>() {
            @Override
            public void onResponse(@NonNull Call<CallBackItems<Groups>> call, @NonNull Response<CallBackItems<Groups>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    groupsList.addAll(response.body().getItems());
                    myGroupCreatedAdapter.setData(groupsList);
                    cardView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallBackItems<Groups>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy danh dách nhóm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}