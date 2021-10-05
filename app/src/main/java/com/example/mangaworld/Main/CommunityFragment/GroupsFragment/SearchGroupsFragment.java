package com.example.mangaworld.Main.CommunityFragment.GroupsFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Extension.Pagination.PaginationRecyclerView;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CallBackItems;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchGroupsFragment extends Fragment {
    public static final String TAG = SearchGroupsFragment.class.getName();
    private final long SIZE = 10;

    private String m_TextSearch;
    private EditText m_Search;
    private long m_Page;
    private SearchGroupsAdapter m_Adapter;
    private List<Groups> m_Groups;
    private CardView m_CardView;
    private TextView m_SearchNull;

    public SearchGroupsFragment(String textSearch) {
        m_TextSearch = textSearch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_groups, container, false);
        m_Page = 1;
        m_Search = view.findViewById(R.id.edit_text_search_in_search);
        m_Search.setText(m_TextSearch);
        m_SearchNull = view.findViewById(R.id.none_search_anything);
        APIClient.getAPICommunity().searchGroups("Bearer " + MainActivity.user.getToken(),m_TextSearch,m_Page,SIZE).enqueue(new Callback<CallBackItems<Groups>>() {
            @Override
            public void onResponse(@NonNull Call<CallBackItems<Groups>> call, @NonNull Response<CallBackItems<Groups>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    m_Groups = response.body().getItems();
                    setData(view, (int) response.body().getTotal());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallBackItems<Groups>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy danh dách nhóm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.button_search_in_search).setOnClickListener(this::clickSearch);
        initToolBar(view);
        return view;
    }

    private void initToolBar(View view) {
        Toolbar toolbar = view.findViewById(R.id.search_group_toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    private void setData(View view, int total) {
        if(m_Groups == null || m_Groups.size() == 0){
            m_SearchNull.setVisibility(View.VISIBLE);
        }else {
            m_SearchNull.setVisibility(View.GONE);
        }
        RecyclerView recyclerView = view.findViewById(R.id.rcv_in_search);
        m_CardView = view.findViewById(R.id.card_view_search_loading);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize((int) SIZE);
        m_Adapter = new SearchGroupsAdapter();
        m_Adapter.SetData(m_Groups);
        recyclerView.setAdapter(m_Adapter);
        recyclerView.addOnScrollListener(new PaginationRecyclerView(linearLayoutManager, total) {
            @Override
            public void setData(int totalItems) {
                m_CardView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> changeGroupList(), 1500);
            }
        });
    }

    private void changeGroupList() {
        APIClient.getAPICommunity().searchGroups("Bearer " + MainActivity.user.getToken(),m_TextSearch,++m_Page,SIZE).enqueue(new Callback<CallBackItems<Groups>>() {
            @Override
            public void onResponse(@NonNull Call<CallBackItems<Groups>> call, @NonNull Response<CallBackItems<Groups>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    m_Groups.addAll(response.body().getItems());
                    m_Adapter.SetData(m_Groups);
                    m_CardView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallBackItems<Groups>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy danh dách nhóm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickSearch(View v){
        if(m_Groups != null){
            m_Groups.clear();
        }
        if (m_Search.length() == 0) {
            Toast.makeText(getContext(), "Nhập tên nhóm cần tìm", Toast.LENGTH_SHORT).show();
            return;
        }
        m_TextSearch = m_Search.getText().toString();
        APIClient.getAPICommunity().searchGroups("Bearer " + MainActivity.user.getToken(),m_TextSearch,m_Page,SIZE).enqueue(new Callback<CallBackItems<Groups>>() {
            @Override
            public void onResponse(@NonNull Call<CallBackItems<Groups>> call, @NonNull Response<CallBackItems<Groups>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    m_Groups = response.body().getItems();
                    if(m_Groups == null || m_Groups.size() == 0){
                        m_SearchNull.setVisibility(View.VISIBLE);
                    }else {
                        m_SearchNull.setVisibility(View.GONE);
                    }
                    m_Adapter.SetData(m_Groups);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallBackItems<Groups>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy danh dách nhóm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        hideKeyBoard(v);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(SearchGroupsFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }
    private void hideKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}