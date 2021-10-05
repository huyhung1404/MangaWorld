package com.example.mangaworld.Main.CommunityFragment.PostStatus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Extension.Pagination.PaginationRecyclerView;
import com.example.mangaworld.Interface.ISelectGroups;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CallBackItems;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.Model.Community.PostStatus;
import com.example.mangaworld.Model.Community.Status;
import com.example.mangaworld.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectGroupsFragment extends Fragment implements ISelectGroups {
    public static final String TAG = SelectGroupsFragment.class.getName();
    private final List<Long> idGroups = new ArrayList<>();
    private List<Long> idImages;
    private long idManga = -1;
    private String content;
    private TextView notificationSelectGroups;
    private FrameLayout loadingLayout;
    //Pagination
    private long page;
    private final long SIZE = 10;
    private SelectGroupAdapter selectGroupAdapter;
    private List<Groups> groupsList;
    private CardView cardView;

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_groups, container, false);
        page = 1;
        notificationSelectGroups = view.findViewById(R.id.content_select_groups);
        loadingLayout = view.findViewById(R.id.loading_select_group);
        view.findViewById(R.id.button_port_select_group).setOnClickListener(v -> sendApi());
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            idManga = bundleReceive.getLong("idManga");
            long idImg = bundleReceive.getLong("idImage");
            if (idImg == -1) {
                idImages = null;
            } else {
                idImages = new ArrayList<>();
                idImages.add(idImg);
            }
            content = bundleReceive.getString("content");
        }
        APIClient.getAPICommunity().getGroupsJoined("Bearer " + MainActivity.user.getToken(), page, SIZE).enqueue(new Callback<CallBackItems<Groups>>() {
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
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_select_group);
        cardView = view.findViewById(R.id.card_view_progress_bar_select_group);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize((int) SIZE);
        selectGroupAdapter = new SelectGroupAdapter(requireContext(), this);
        selectGroupAdapter.setData(groupsList);
        recyclerView.setAdapter(selectGroupAdapter);
        recyclerView.addOnScrollListener(new PaginationRecyclerView(linearLayoutManager, total) {
            @Override
            public void setData(int totalItems) {
                cardView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> changeGroupList(), 1500);
            }
        });
    }

    private void initToolBar(View _view) {
        Toolbar toolbar = _view.findViewById(R.id.select_group_toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Chọn nhóm");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    private void sendApi() {
        if (idGroups.size() == 0) {
            Toast.makeText(requireContext(), "Vui lòng chọn nhóm để đăng tin", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingLayout.setVisibility(View.VISIBLE);
        APIClient.getAPICommunity().postStatus("Bearer " + MainActivity.user.getToken(),
                new PostStatus(content, (idManga == -1) ? null : idManga, idImages, idGroups)).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(@NonNull Call<Status> call, @NonNull Response<Status> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    loadingLayout.setVisibility(View.GONE);
                    requireFragmentManager()
                            .popBackStack(PostStatusFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    new SuccessMessageFragment().show(requireActivity().getSupportFragmentManager(), SuccessMessageFragment.TAG);
                    MainActivity.showBottomNav();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Status> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(SelectGroupsFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void selectGroup(long id) {
        idGroups.add(id);
        notificationSelectGroups.setText(String.format("Nhóm đã chọn: %d", idGroups.size()));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void unselectGroup(long id) {
        idGroups.remove(id);
        notificationSelectGroups.setText(String.format("Nhóm đã chọn: %d", idGroups.size()));
    }

    private void changeGroupList() {
        APIClient.getAPICommunity().getGroupsJoined("Bearer " + MainActivity.user.getToken(), ++page, SIZE).enqueue(new Callback<CallBackItems<Groups>>() {
            @Override
            public void onResponse(@NonNull Call<CallBackItems<Groups>> call, @NonNull Response<CallBackItems<Groups>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    groupsList.addAll(response.body().getItems());
                    selectGroupAdapter.setData(groupsList);
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