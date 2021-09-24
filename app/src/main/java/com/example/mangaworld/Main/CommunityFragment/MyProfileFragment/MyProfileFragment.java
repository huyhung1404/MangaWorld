package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MyProfileFragment extends Fragment {
//    public static final String TAG = MyProfileFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_post, container, false);
        settingTagLayout(view);
        setHasOptionsMenu(true);
        return view;
    }

    private void settingTagLayout(View view){
        ViewPager2 viewPager2 = view.findViewById(R.id.view_pager_my_profile);
        viewPager2.setAdapter(new MyProfileAdapter(requireActivity()));
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_my_profile);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText("Bài viết");
                tab.setIcon(R.drawable.icon_my_post);
            } else {
                tab.setText("Nhóm");
                tab.setIcon(R.drawable.icon_my_group);
            }
        });
        tabLayoutMediator.attach();
    }
}