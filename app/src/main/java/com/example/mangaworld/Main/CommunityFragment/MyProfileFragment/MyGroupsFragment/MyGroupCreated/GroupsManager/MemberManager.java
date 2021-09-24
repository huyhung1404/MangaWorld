package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
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
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.UserCallBack;
import com.example.mangaworld.Model.Community.UserForum;
import com.example.mangaworld.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberManager extends Fragment {
    public static final String TAG = MemberManager.class.getName();
    private final long idGroup;
    private long page;
    private final long SIZE = 10;
    private List<UserForum> userForumList;
    private MemberAdapter memberAdapter;
    private CardView cardView;

    public MemberManager(long idGroup) {
        this.idGroup = idGroup;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_mangaer, container, false);
        page = 1;
        APIClient.getAPICommunity().getUserDraftInGroup("Bearer " + MainActivity.user.getToken(), idGroup, page, SIZE).enqueue(new Callback<UserCallBack>() {
            @Override
            public void onResponse(@NonNull Call<UserCallBack> call, @NonNull Response<UserCallBack> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    userForumList = response.body().getItems();
                    setData(view, (int) response.body().getTotal());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserCallBack> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy danh dách nhóm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        initToolBar(view);
        return view;
    }

    private void initToolBar(View _view) {
        Toolbar toolbar = _view.findViewById(R.id.member_manager_toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Duyệt thành viên");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    private void setData(View view, int total) {
        if(total == 0){
            view.findViewById(R.id.none_member_manager).setVisibility(View.VISIBLE);
            return;
        }
        RecyclerView recyclerView = view.findViewById(R.id.rcv_member_manager);
        cardView = view.findViewById(R.id.card_view_progress_bar_member_manager);
        view.findViewById(R.id.none_member_manager).setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize((int) SIZE);
        memberAdapter = new MemberAdapter(idGroup,requireContext());
        memberAdapter.setData(userForumList);
        recyclerView.setAdapter(memberAdapter);
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

    private void changeGroupList() {
        APIClient.getAPICommunity().getUserDraftInGroup("Bearer " + MainActivity.user.getToken(), idGroup, ++page, SIZE).enqueue(new Callback<UserCallBack>() {
            @Override
            public void onResponse(@NonNull Call<UserCallBack> call, @NonNull Response<UserCallBack> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    userForumList.addAll(response.body().getItems());
                    memberAdapter.setData(userForumList);
                    cardView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserCallBack> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy danh dách nhóm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(MemberManager.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }
}