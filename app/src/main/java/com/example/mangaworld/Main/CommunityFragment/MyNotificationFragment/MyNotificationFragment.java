package com.example.mangaworld.Main.CommunityFragment.MyNotificationFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mangaworld.R;

public class MyNotificationFragment extends Fragment {
    private boolean haveNotification = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_notification, container, false);
        TextView noneNotification = view.findViewById(R.id.none_notification);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_notification);
        if (!haveNotification){
            noneNotification.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return view;
        }
        noneNotification.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyNotificationAdapter myNotificationAdapter = new MyNotificationAdapter();
        recyclerView.setAdapter(myNotificationAdapter);
        return view;
    }
}