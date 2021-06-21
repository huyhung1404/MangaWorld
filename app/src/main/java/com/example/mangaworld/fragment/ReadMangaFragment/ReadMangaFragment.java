package com.example.mangaworld.fragment.ReadMangaFragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.mainActivityAdapter.ViewPagerAdapter;
import com.example.mangaworld.object.Manga;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;


public class ReadMangaFragment extends Fragment {
    public static final String TAG = ReadMangaFragment.class.getName();
    private Manga manga;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_read_manga, container, false);
        TabLayout mTabLayout = mView.findViewById(R.id.tab_layout_read_book);
        ViewPager mViewPager = mView.findViewById(R.id.view_pager_read_book);
        androidx.appcompat.widget.Toolbar mToolBar = mView.findViewById(R.id.tool_bar);
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            this.manga = (Manga) bundleReceive.get("object_book");
        }
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(mToolBar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(manga.getNameManga());
        }
        setHasOptionsMenu(true);

        ViewPagerAdapter viewPageAdapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPageAdapter.AddFragment(new ChapterFragment(manga.getIdManga()), "Chương");
        viewPageAdapter.AddFragment(new SummaryFragment(manga.getIdManga(), manga.getListTagCategory(), manga.getSummaryManga()), "Giới thiệu");
        viewPageAdapter.AddFragment(new CommentFragment(manga.getIdManga()), "Bình luận");
        setViewReadBook();
        mViewPager.setAdapter(viewPageAdapter);
        mViewPager.setCurrentItem(1);
        mTabLayout.setupWithViewPager(mViewPager);
        setTabLayAnimation();
        return mView;
    }

    private void setTabLayAnimation() {
        final CollapsingToolbarLayout collapsingToolbarLayout = mView.findViewById(R.id.collapsing_tool_bar_layout);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_read_book);
        Palette.from(bitmap).generate(palette -> {
            assert palette != null;
            int myColor = palette.getVibrantColor(getResources().getColor(R.color.gray));
            int myDarkColor = palette.getVibrantColor(getResources().getColor(R.color.black_trans));
            collapsingToolbarLayout.setContentScrimColor(myColor);
            collapsingToolbarLayout.setStatusBarScrimColor(myDarkColor);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(ReadMangaFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void setViewReadBook() {
        ImageView imageView = mView.findViewById(R.id.img_title_book);
        TextView tvAuthor = mView.findViewById(R.id.text_author_read_book);
        TextView tvStatus = mView.findViewById(R.id.text_status_read_book);
        TextView tvNumberChap = mView.findViewById(R.id.text_number_chap_read_book);
        TextView tvView = mView.findViewById(R.id.text_number_view_read_book);
        TextView tvLike = mView.findViewById(R.id.text_number_like_read_book);

        Glide.with(requireContext()).load(manga.getResourceId()).into(imageView);
        tvAuthor.setText("Tác giả: " + manga.getMangaAuthor());
        if (!manga.getStatus()) {
            tvStatus.setText(R.string.trang_thai_false);
        } else if (manga.getStatus()) {
            tvStatus.setText(R.string.trang_thai_true);
        }
        tvNumberChap.setText("Số chương: " + manga.getNumberChap());
        tvView.setText("Lượt xem: " + manga.getViewManga());
        tvLike.setText("Lượt thích: " + manga.getLikeManga());
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.hideBottomNav();
    }

    @Override
    public void onStop() {
        super.onStop();
        MainActivity.showBottomNav();
    }

}