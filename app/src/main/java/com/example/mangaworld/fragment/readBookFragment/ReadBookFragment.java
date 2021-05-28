package com.example.mangaworld.fragment.readBookFragment;

import android.annotation.SuppressLint;
import android.content.Context;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.adapter.ViewPagerReadBookAdapter;
import com.example.mangaworld.object.Book;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

public class ReadBookFragment extends Fragment {
    public static final String TAG = ReadBookFragment.class.getName();
    private Book book;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_read_book, container, false);
        TabLayout mTabLayout = mView.findViewById(R.id.tab_layout_read_book);
        ViewPager mViewPager = mView.findViewById(R.id.view_pager_read_book);
        androidx.appcompat.widget.Toolbar mToolBar = mView.findViewById(R.id.tool_bar);
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            this.book = (Book) bundleReceive.get("object_book");
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolBar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(book.getNameBook());
            }
        setHasOptionsMenu(true);
        ViewPagerReadBookAdapter viewPagerReadBookAdapter = new ViewPagerReadBookAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerReadBookAdapter.setBook(book);
        setViewReadBook();
        mViewPager.setAdapter(viewPagerReadBookAdapter);
        mViewPager.setCurrentItem(1);
        mTabLayout.setupWithViewPager(mViewPager);
        setTabLayAnimation();
        return mView;
    }
    private void setTabLayAnimation(){
        final CollapsingToolbarLayout collapsingToolbarLayout = mView.findViewById(R.id.collapsing_tool_bar_layout);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.background_read_book);
        Palette.from(bitmap).generate(palette -> {
            int myColor = palette.getVibrantColor(getResources().getColor(R.color.gray));
            int myDarkColor = palette.getVibrantColor(getResources().getColor(R.color.black_trans));
            collapsingToolbarLayout.setContentScrimColor(myColor);
            collapsingToolbarLayout.setStatusBarScrimColor(myDarkColor);
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getFragmentManager().popBackStack(ReadBookFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }
    @SuppressLint("SetTextI18n")
    private void setViewReadBook(){
        ImageView imageView = mView.findViewById(R.id.img_title_book);
        TextView tvAuthor = mView.findViewById(R.id.text_author_read_book);
        TextView tvStatus = mView.findViewById(R.id.text_status_read_book);
        TextView tvNumberChap = mView.findViewById(R.id.text_number_chap_read_book);
        TextView tvView = mView.findViewById(R.id.text_number_view_read_book);
        TextView tvLike = mView.findViewById(R.id.text_number_like_read_book);

        Glide.with(getContext()).load(book.getResourceId()).into(imageView);
        tvAuthor.setText("Tác giả: " + book.getBookAuthor());
        if(!book.getStatus()){
            tvStatus.setText(R.string.trang_thai_false);
        }else if (book.getStatus()){
            tvStatus.setText(R.string.trang_thai_true);
        }
        tvNumberChap.setText("Số chương: " + book.getNumberChap());
        tvView.setText("Lượt xem: " + book.getViewBook());
        tvLike.setText("Lượt thích: "+book.getLikeBook());
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("aaa", "ReadBookFragment 0nCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("aaa", "ReadBookFragment 0nStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("aaa", "ReadBookFragment 0nPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("aaa", "ReadBookFragment Ondestroy");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("aaa", "ReadBookFragment 0nAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("aaa", "ReadBookFragment 0nActivityCreated");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("aaa", "ReadBookFragment 0nDestroyView");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("aaa", "ReadBookFragment 0nDetach");

    }
}