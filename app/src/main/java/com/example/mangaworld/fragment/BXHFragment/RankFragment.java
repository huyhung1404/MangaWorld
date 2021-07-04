package com.example.mangaworld.fragment.BXHFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangaworld.R;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.mainActivityAdapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class RankFragment extends Fragment {
    public static final String TAG = RankFragment.class.getName();
    private float typeBXH;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            this.typeBXH = (float) bundleReceive.get("type_bxh");
        }
        View view = inflater.inflate(R.layout.fragment_b_x_h, container, false);
        androidx.appcompat.widget.Toolbar mToolBar = view.findViewById(R.id.tool_bar_bxh_fragment);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_bxh_fragment);
        ViewPager viewPager = view.findViewById(R.id.view_pager_bxh_fragment);
        //ToolBar
        MainActivity mMainActivity = (MainActivity) requireActivity();
        mMainActivity.setSupportActionBar(mToolBar);
        ActionBar actionBar = mMainActivity.getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        if (typeBXH == 0){
            actionBar.setTitle("Bảng xếp hạng");
        }else if(typeBXH == 1){
            actionBar.setTitle("Yêu thích");
        }
        setHasOptionsMenu(true);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.AddFragment(new TopMangaFragment(0,typeBXH), "bxh tổng");
        viewPagerAdapter.AddFragment(new TopMangaFragment(7,typeBXH), "theo tháng");
        viewPagerAdapter.AddFragment(new TopMangaFragment(30,typeBXH), "theo tuần");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(RankFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }
}