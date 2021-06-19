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
import com.example.mangaworld.mainActivityAdapter.ScrollHandler;
import com.example.mangaworld.fragment.CategoryFragment;
import com.example.mangaworld.fragment.readChapFragment.ReadChapFragment;
import com.example.mangaworld.object.Manga;
import com.example.mangaworld.fragment.BookcaseFragment;
import com.example.mangaworld.fragment.HomeFragment;
import com.example.mangaworld.fragment.InfoFragment;
import com.example.mangaworld.fragment.ReadMangaFragment.ReadMangaFragment;
import com.example.mangaworld.fragment.SearchFragment.SearchFragment;
import com.example.mangaworld.object.Chapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    private final ArrayList<Fragment> listFragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());
        listFragment.add(new HomeFragment());
        listFragment.add(new BookcaseFragment());
        listFragment.add(new SearchFragment());
        listFragment.add(new InfoFragment());
        setBottomNavigationView();
    }

    @SuppressLint("NonConstantResourceId")
    private void setBottomNavigationView() {
        loadFragment(listFragment.get(0));
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    loadFragment(listFragment.get(0));
                    return true;
                case R.id.menuBookCase:
                    loadFragment(listFragment.get(1));
                    return true;
                case R.id.menuSearch:
                    loadFragment(listFragment.get(2));
                    return true;
                case R.id.menuInfo:
                    loadFragment(listFragment.get(3));
                    return true;
            }
            return false;
        });
    }

    //Chuyển fragment
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.commit();
    }

    //Truyền dữ liệu từ Home đến ReadManga
    public void nextReadMangaActivity(Manga manga) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ReadMangaFragment readMangaFragment = new ReadMangaFragment();
        //Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_book", manga);
        readMangaFragment.setArguments(bundle);
        //
        fragmentTransaction.replace(R.id.main_frame, readMangaFragment);
        fragmentTransaction.addToBackStack(ReadMangaFragment.TAG);
        fragmentTransaction.commit();
    }

    //Truyền dữ liệu từ Home đến Category
    public void nextCategoryFragment(Long idCategory) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CategoryFragment categoryFragment = new CategoryFragment();
        //Bundle
        Bundle bundle = new Bundle();
        bundle.putLong("id_category", idCategory);
        categoryFragment.setArguments(bundle);
        //
        fragmentTransaction.replace(R.id.main_frame, categoryFragment);
        fragmentTransaction.addToBackStack(CategoryFragment.TAG);
        fragmentTransaction.commit();
    }

    //Truyền dữ liệu từ ReadManga đến ReadChap / Chuyển chap
    public void nextChapFragment(Chapter chapter, long idBook, int position, Boolean isAddToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ReadChapFragment readChapFragment = new ReadChapFragment();
        //Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_chap", chapter);
        bundle.putLong("idBook", idBook);
        bundle.putInt("position", position);
        readChapFragment.setArguments(bundle);
        //
        fragmentTransaction.replace(R.id.main_frame, readChapFragment);
        fragmentTransaction.addToBackStack((isAddToBackStack) ? ReadChapFragment.TAG : null);
        fragmentTransaction.commit();
    }

    public static void hideBottomNav() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    public static void showBottomNav() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
}

