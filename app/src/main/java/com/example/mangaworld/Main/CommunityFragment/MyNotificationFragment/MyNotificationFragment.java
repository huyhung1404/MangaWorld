package com.example.mangaworld.Main.CommunityFragment.MyNotificationFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Extension.Pagination.PaginationRecyclerView;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.SearchGroupsAdapter;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CallBackItems;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.Model.Community.Notification;
import com.example.mangaworld.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNotificationFragment extends Fragment {
    private final CommunityFragment.ISeenNotification m_ISeenNotification;
    private final long SIZE = 10;

    private long m_Page;
    private MyNotificationAdapter m_Adapter;
    private List<Notification> m_Notification;
    private CardView m_CardView;

    public MyNotificationFragment(CommunityFragment.ISeenNotification m_ISeenNotification) {
        this.m_ISeenNotification = m_ISeenNotification;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_notification, container, false);
        APIClient.getAPICommunity().numberNotification("Bearer " + MainActivity.user.getToken()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if(response.isSuccessful()){
                    m_ISeenNotification.load(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {

            }
        });

        APIClient.getAPICommunity().getNotification("Bearer " + MainActivity.user.getToken(),m_Page,SIZE).enqueue(new Callback<CallBackItems<Notification>>() {
            @Override
            public void onResponse(@NonNull Call<CallBackItems<Notification>> call, @NonNull Response<CallBackItems<Notification>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    m_Notification = response.body().getItems();
                    setData(view, (int) response.body().getTotal());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallBackItems<Notification>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(),"Lỗi kết nối",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void setData(View view, int total) {
        if(m_Notification == null || m_Notification.size() == 0){
            view.findViewById(R.id.none_notification).setVisibility(View.VISIBLE);
        }else {
            view.findViewById(R.id.none_notification).setVisibility(View.GONE);
        }
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_notification);
        m_CardView = view.findViewById(R.id.card_view_notification_loading);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize((int) SIZE);
        m_Adapter = new MyNotificationAdapter(requireContext(),m_ISeenNotification);
        m_Adapter.SetData(m_Notification);
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
        APIClient.getAPICommunity().getNotification("Bearer " + MainActivity.user.getToken(),++m_Page,SIZE).enqueue(new Callback<CallBackItems<Notification>>() {
            @Override
            public void onResponse(@NonNull Call<CallBackItems<Notification>> call, @NonNull Response<CallBackItems<Notification>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    m_Notification.addAll(response.body().getItems());
                    m_Adapter.SetData(m_Notification);
                    m_CardView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallBackItems<Notification>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(),"Lỗi kết nối",Toast.LENGTH_SHORT).show();
            }
        });
    }
}