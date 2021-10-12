package com.example.mangaworld.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.mangaworld.API.RealPathUtil;
import com.example.mangaworld.Interface.DataReceivedListener;
import com.example.mangaworld.Interface.IOpenGallery;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.CategoryGroup.CategoryGroupFragment;
import com.example.mangaworld.Main.CommunityFragment.PostStatus.PostStatusFragment;
import com.example.mangaworld.Main.CommunityFragment.PostStatus.SelectGroupsFragment;
import com.example.mangaworld.Model.ListTagCategory;
import com.example.mangaworld.R;
import com.example.mangaworld.Main.HomeFragment.RankFragment.RankFragment;
import com.example.mangaworld.Main.InfoUserFragment.AccountManager.LoginFragment;
import com.example.mangaworld.Extension.ScrollHandler;
import com.example.mangaworld.Main.HomeFragment.CategoryFragment.CategoryFragment;
import com.example.mangaworld.Main.ReadManga.Element.ReadChapFragment.ReadChapFragment;
import com.example.mangaworld.Main.BookcaseFragment.BookcaseFragment;
import com.example.mangaworld.Main.HomeFragment.HomeFragment;
import com.example.mangaworld.Main.InfoUserFragment.InfoUserFragment;
import com.example.mangaworld.Main.ReadManga.Element.InfomationMangaFragment.InformationMangaFragment;
import com.example.mangaworld.Main.SearchFragment.SearchFragment;
import com.example.mangaworld.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    public static boolean isLogin = false;
    public static User user = null;
    private DataReceivedListener dataReceivedListener;

    private FragmentManager fragmentManager;

    public void setDataReceivedListener(DataReceivedListener dataReceivedListener) {
        this.dataReceivedListener = dataReceivedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());
        setBottomNavigationView();
    }

    @SuppressLint("NonConstantResourceId")
    private void setBottomNavigationView() {
        loadFragment(new HomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.menuCommunity:
                    loadFragment(new CommunityFragment());
                    return true;
                case R.id.menuBookCase:
                    loadFragment(new BookcaseFragment());
                    return true;
                case R.id.menuSearch:
                    loadFragment(new SearchFragment());
                    return true;
                case R.id.menuInfo:
                    if (isLogin) {
                        loadFragment(new InfoUserFragment());
                        return true;
                    }
                    loadFragment(new LoginFragment());
                    return true;
            }
            return false;
        });
    }

    public void loadFragment(Fragment _fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, _fragment);
        transaction.addToBackStack(null);
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.commit();
    }

    public void goToInformationMangaScreen(long _idManga) {
        InformationMangaFragment informationMangaFragment = new InformationMangaFragment();
        Bundle bundle = new Bundle();

        bundle.putLong("idManga", _idManga);
        informationMangaFragment.setArguments(bundle);

        goToScreen(informationMangaFragment, InformationMangaFragment.TAG);
    }

    public void goToCategoryMangaScreen(Long _idCategory, boolean _isViewMore) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();

        bundle.putLong("id_category", _idCategory);
        bundle.putBoolean("isViewMore", _isViewMore);
        categoryFragment.setArguments(bundle);

        goToScreen(categoryFragment, CategoryFragment.TAG);
    }

    public void goToRankMangaScreen(float _id) {
        RankFragment rankFragment = new RankFragment();
        Bundle bundle = new Bundle();

        bundle.putFloat("type_bxh", _id);
        rankFragment.setArguments(bundle);

        goToScreen(rankFragment, RankFragment.TAG);
    }

    public void goToReadMangaScreen(List<Long> _listIndexChapter, long _idBook, int _position, Boolean _isAddToBackStack) {
        ReadChapFragment readChapFragment = new ReadChapFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("object_chap", (Serializable) _listIndexChapter);
        bundle.putLong("idBook", _idBook);
        bundle.putInt("position", _position);
        readChapFragment.setArguments(bundle);

        goToScreen(readChapFragment, (_isAddToBackStack) ? ReadChapFragment.TAG : null);
    }

    public void postStatusAttachManga(long _idManga){
        PostStatusFragment postStatusFragment = new PostStatusFragment((bundle) -> {
            SelectGroupsFragment selectGroupsFragment = new SelectGroupsFragment();
            selectGroupsFragment.setArguments(bundle);
            goToScreen(selectGroupsFragment,SelectGroupsFragment.TAG);
        });

        Bundle bundle = new Bundle();
        bundle.putLong("idManga", _idManga);
        postStatusFragment.setArguments(bundle);
        goToScreen(postStatusFragment,PostStatusFragment.TAG);
    }

    public void GoToCategoryGroup(String listTagCategories){
        CategoryGroupFragment categoryGroupFragment = new CategoryGroupFragment();
        Bundle bundle = new Bundle();
        bundle.putString("listTagCategory",listTagCategories);
        categoryGroupFragment.setArguments(bundle);
        goToScreen(categoryGroupFragment,CategoryGroupFragment.TAG);
    }

    public void goToScreen(Fragment _fragment, String _tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, _fragment);
        fragmentTransaction.addToBackStack(_tag);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dataReceivedListener.onReceived(requestCode, resultCode, data);
    }

    public void onClickRequestPermission(IOpenGallery openGallery) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery.openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            openGallery.openGallery();
            return;
        }
        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permission, 10);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public static void hideBottomNav() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    public static void showBottomNav() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
}

