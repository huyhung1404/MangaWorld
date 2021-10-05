package com.example.mangaworld.Main.CommunityFragment.GroupsFragment.ViewMoreGroup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Interface.ChangePageNews;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CallBackItems;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMoreGroupsFragment extends Fragment implements ChangePageNews {
    public static final String TAG = ViewMoreGroupsFragment.class.getName();
    private long page = 1;
    private final long SIZE = 10;
    private ViewMoreGroupsAdapter viewMoreGroupsAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_more_groups, container, false);
        initToolBar(view);
        MainActivity.hideBottomNav();
        APIClient.getAPICommunity().getViewMoreGroup("Bearer " + MainActivity.user.getToken(),page,SIZE).enqueue(new Callback<CallBackItems<Groups>>() {
            @Override
            public void onResponse(@NonNull Call<CallBackItems<Groups>> call, @NonNull Response<CallBackItems<Groups>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    getData(view, response.body());
                }
                MainActivity.showBottomNav();
            }

            @Override
            public void onFailure(@NonNull Call<CallBackItems<Groups>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                MainActivity.showBottomNav();
            }
        });
        return view;
    }

    private void getData(View view, CallBackItems<Groups> groupsList) {
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_view_more_group);
        linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        viewMoreGroupsAdapter = new ViewMoreGroupsAdapter(this);
        viewMoreGroupsAdapter.setData(groupsList);
        recyclerView.setAdapter(viewMoreGroupsAdapter);
    }

    private void initToolBar(View _view) {
        Toolbar toolbar = _view.findViewById(R.id.view_more_groups_toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Danh sách nhóm");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(ViewMoreGroupsFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    private void changePage(long page) {
        APIClient.getAPICommunity().getViewMoreGroup("Bearer " + MainActivity.user.getToken(), page, SIZE).enqueue(new Callback<CallBackItems<Groups>>() {
            @Override
            public void onResponse(@NonNull Call<CallBackItems<Groups>> call, @NonNull Response<CallBackItems<Groups>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    viewMoreGroupsAdapter.setData(response.body());
                    linearLayoutManager.scrollToPosition(0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallBackItems<Groups>> call, @NonNull Throwable t) {
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