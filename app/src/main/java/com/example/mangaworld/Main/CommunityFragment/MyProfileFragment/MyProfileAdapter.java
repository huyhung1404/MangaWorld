package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupsFragment;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyPostFragment.MyPostFragment;


public class MyProfileAdapter extends FragmentStateAdapter {
    public MyProfileAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new MyPostFragment();
        }
        return new MyGroupsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
