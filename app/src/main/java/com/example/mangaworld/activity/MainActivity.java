package com.example.mangaworld.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.ScrollHandler;
import com.example.mangaworld.fragment.CategoryFragment;
import com.example.mangaworld.fragment.readChapFragment.ReadChapFragment;
import com.example.mangaworld.object.Book;
import com.example.mangaworld.fragment.BookcaseFragment;
import com.example.mangaworld.fragment.HomeFragment;
import com.example.mangaworld.fragment.InfoFragment;
import com.example.mangaworld.fragment.readBookFragment.ReadBookFragment;
import com.example.mangaworld.fragment.SearchFragment;
import com.example.mangaworld.object.Chap;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());
        setBottomNavigationView();
    }

    @SuppressLint("NonConstantResourceId")
    private void setBottomNavigationView(){
        loadFragment(new HomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.menuHome:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menuBookCase:
                    fragment = new BookcaseFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menuSearch:
                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menuInfo:
                    fragment = new InfoFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        });
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.commit();
    }

    public void nextReadBookActivity(Book book){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ReadBookFragment readBookFragment = new ReadBookFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_book", book);
        readBookFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.main_frame,readBookFragment);
        fragmentTransaction.addToBackStack(ReadBookFragment.TAG);
        fragmentTransaction.commit();
    }
    public void nextCategoryFragment(String string){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("object_string", string);
        categoryFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.main_frame,categoryFragment);
        fragmentTransaction.addToBackStack(CategoryFragment.TAG);
        fragmentTransaction.commit();
    }
    public void nextChapFragment(Chap chap,String idBook,int maxChap,Boolean isNextChapFragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ReadChapFragment readChapFragment = new ReadChapFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_chap", chap);
        bundle.putString("idBook",idBook);
        bundle.putInt("maxChap",maxChap);
        readChapFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.main_frame,readChapFragment);
        if (!isNextChapFragment){
            fragmentTransaction.addToBackStack(null);
        }else if(isNextChapFragment){
            fragmentTransaction.addToBackStack(ReadChapFragment.TAG);
        }
        fragmentTransaction.commit();
    }

    public static void hideBottomNav(){
        bottomNavigationView.setVisibility(View.GONE);
    }

    public static void showBottomNav(){
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
}

