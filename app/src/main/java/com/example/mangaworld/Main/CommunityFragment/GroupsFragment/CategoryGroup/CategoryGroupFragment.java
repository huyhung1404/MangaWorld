package com.example.mangaworld.Main.CommunityFragment.GroupsFragment.CategoryGroup;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.InformationGroup.InformationGroupFragment;
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.ViewMoreGroup.ViewMoreGroupsAdapter;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CallBackItems;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.Model.ListTagCategory;
import com.example.mangaworld.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryGroupFragment extends Fragment implements ChangePageNews {
    public static final String TAG = CategoryGroupFragment.class.getName();
    private String m_Tag ;
    private long m_Page = 1;
    private final long SIZE = 10;
    private ViewMoreGroupsAdapter m_Adapter;
    private LinearLayoutManager m_LayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_group, container, false);
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            m_Tag = bundleReceive.getString("listTagCategory");
        }
        initToolBar(view);
        MainActivity.hideBottomNav();
        APIClient.getAPICommunity().getGroupByCategory("Bearer " + MainActivity.user.getToken(), m_Page, SIZE, m_Tag).enqueue(new Callback<CallBackItems<Groups>>() {
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
        if(groupsList.getItems() == null || groupsList.getItems().size() == 0){
            view.findViewById(R.id.text_none_group_category).setVisibility(View.VISIBLE);
            return;
        }
        RecyclerView recyclerView = view.findViewById(R.id.rcv_category_group_fragment);
        m_LayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(m_LayoutManager);
        m_Adapter = new ViewMoreGroupsAdapter(this, id -> ((MainActivity) requireActivity())
                .goToScreen(new InformationGroupFragment(id), InformationGroupFragment.TAG));
        m_Adapter.setData(groupsList);
        recyclerView.setAdapter(m_Adapter);
    }

    private void initToolBar(View _view) {
        Toolbar toolbar = _view.findViewById(R.id.category_group_fragment_tool_bar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Nhóm liên quan");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(CategoryGroupFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    private void changePage(long _page) {
        APIClient.getAPICommunity().getGroupByCategory("Bearer " + MainActivity.user.getToken(), _page, SIZE, m_Tag).enqueue(new Callback<CallBackItems<Groups>>() {
            @Override
            public void onResponse(@NonNull Call<CallBackItems<Groups>> call, @NonNull Response<CallBackItems<Groups>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    m_Adapter.setData(response.body());
                    m_LayoutManager.scrollToPosition(0);
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
        changePage(++m_Page);
    }

    @Override
    public void lastPage() {
        changePage(--m_Page);
    }

    @Override
    public void goPage(long page, EditText view) {
        m_Page = page;
        changePage(page);
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}