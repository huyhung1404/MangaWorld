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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.fragment.BXHFragment.RankFragment;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.mainActivityAdapter.ViewPagerAdapter;
import com.example.mangaworld.object.Manga;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReadMangaFragment extends Fragment {
    public static final String TAG = ReadMangaFragment.class.getName();
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_read_manga, container, false);
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            long idManga = bundleReceive.getLong("idManga");
            Call<Manga> mangaCall;
            if (MainActivity.user != null) {
                mangaCall = APIClient.getAPIChapter().getMangaByIdHasUser(idManga, MainActivity.user.getId());
            } else {
                mangaCall = APIClient.getAPIChapter().getMangaById(idManga);
            }
            mangaCall.enqueue(new Callback<Manga>() {
                @Override
                public void onResponse(@NonNull Call<Manga> call, @NonNull Response<Manga> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Manga manga = response.body();
                        setData(manga, idManga);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Manga> call, @NonNull Throwable t) {
                    Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return mView;
    }

    private void setData(Manga manga, long idManga) {
        TabLayout mTabLayout = mView.findViewById(R.id.tab_layout_read_book);
        ViewPager mViewPager = mView.findViewById(R.id.view_pager_read_book);
        androidx.appcompat.widget.Toolbar mToolBar = mView.findViewById(R.id.tool_bar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(mToolBar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(manga.getNameManga());
        }
        RankFragment.IsLoadingApi isLoadingApi = new RankFragment.IsLoadingApi() {
            private int flag = 0;
            @Override
            public void loadDone() {
                flag++;
                if (flag == 3) {
                    setHasOptionsMenu(true);
                    flag = 0;
                }
            }
        };

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.AddFragment(new ChapterFragment(idManga,isLoadingApi), "Chương");
        if (MainActivity.user != null) {
            viewPagerAdapter.AddFragment(new SummaryFragment(idManga, manga.getListTagCategory(), manga.getSummaryManga(), manga.getReadingIndex(),isLoadingApi), "Giới thiệu");
        } else {
            viewPagerAdapter.AddFragment(new SummaryFragment(idManga, manga.getListTagCategory(), manga.getSummaryManga(),isLoadingApi), "Giới thiệu");
        }
        viewPagerAdapter.AddFragment(new CommentFragment(idManga,isLoadingApi), "Bình luận");
        setViewReadBook(manga);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        setTabLayAnimation();
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
    private void setViewReadBook(Manga manga) {
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