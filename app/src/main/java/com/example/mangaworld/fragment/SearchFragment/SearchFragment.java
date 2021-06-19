package com.example.mangaworld.fragment.SearchFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mangaworld.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_search_fragment);
        ViewPager viewPager = view.findViewById(R.id.view_pager_search_fragment);

        androidx.appcompat.widget.Toolbar mToolBar = view.findViewById(R.id.tool_bar_search_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(mToolBar);
            setHasOptionsMenu(true);
        }
        SearchAdapter searchAdapter = new SearchAdapter(getChildFragmentManager());
        searchAdapter.AddFragment(new SearchManga(), "Truyện");
        searchAdapter.AddFragment(new SearchCategory(), "Thể loại");
        searchAdapter.AddFragment(new SearchAuthor(), "Tác giả");
        viewPager.setAdapter(searchAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    // PageView Adapter
    private static class SearchAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        private final ArrayList<String> stringArrayList = new ArrayList<>();

        public void AddFragment(Fragment fragment, String s) {
            fragmentArrayList.add(fragment);
            stringArrayList.add(s);
        }

        public SearchAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return stringArrayList.get(position);
        }
    }
}