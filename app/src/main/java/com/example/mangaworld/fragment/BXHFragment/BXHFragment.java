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
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.mainActivityAdapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class BXHFragment extends Fragment {
    public static final String TAG = BXHFragment.class.getName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b_x_h, container, false);
        androidx.appcompat.widget.Toolbar mToolBar = view.findViewById(R.id.tool_bar_bxh_fragment);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_bxh_fragment);
        ViewPager viewPager = view.findViewById(R.id.view_pager_bxh_fragment);
        //ToolBar
        MainActivity mMainActivity = (MainActivity) requireActivity();
        mMainActivity.setSupportActionBar(mToolBar);
        ActionBar actionBar = mMainActivity.getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Bảng xếp hạng");
        setHasOptionsMenu(true);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.AddFragment(new TopMangaFragment(), "bxh tổng");
        viewPagerAdapter.AddFragment(new TopMangaFragment(), "theo tháng");
        viewPagerAdapter.AddFragment(new TopMangaFragment(), "theo tuần");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(BXHFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }
}