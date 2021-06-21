package com.example.mangaworld.fragment.readChapFragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.object.Chapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReadChapFragment extends Fragment {
    public static final String TAG = ReadChapFragment.class.getName();
    private Chapter chapter;
    private View mView;
    private Long idBook;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_read_chap, container, false);

        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            this.chapter = (Chapter) bundleReceive.get("object_chap");
            this.position = bundleReceive.getInt("position");
            this.idBook = bundleReceive.getLong("idBook");
        }
        //Init
        androidx.appcompat.widget.Toolbar mToolBar = mView.findViewById(R.id.tool_bar_read_chap);
        RecyclerView rcvReadChap = mView.findViewById(R.id.rcv_read_chap);
        //Recycler view
        rcvReadChap.setHasFixedSize(true);
        rcvReadChap.setItemViewCacheSize(4);
        rcvReadChap.setItemAnimator(null);
        ReadChapAdapter readChapAdapter = new ReadChapAdapter();
        //Set layout
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rcvReadChap.setLayoutManager(staggeredGridLayoutManager);
        //Set data
        setDataChap(mToolBar, readChapAdapter);
        rcvReadChap.setAdapter(readChapAdapter);
        //Click next chap
        buttonClickFunction();

        return mView;
    }

    private void buttonClickFunction() {
        //Init
        FloatingActionButton btnNextChap = mView.findViewById(R.id.btn_next_chap);
        FloatingActionButton btnLastChap = mView.findViewById(R.id.btn_last_chap);
        //Condition on/off button
        if (position == 0) {
            btnLastChap.hide();
        } else if (position == chapter.getIndexChapter().size() - 1) {
            btnNextChap.hide();
        }
        //
        btnNextChap.setOnClickListener(v -> ((MainActivity) requireActivity()).nextChapFragment(chapter, idBook, ++position, false));
        btnLastChap.setOnClickListener(v -> ((MainActivity) requireActivity()).nextChapFragment(chapter, idBook, --position, false));
    }

    private void setDataChap(Toolbar mToolBar, ReadChapAdapter readChapAdapter) {
        APIClient.getAPIChapter().getChapData(idBook, chapter.getIndexChapter().get(position)).enqueue(new Callback<Chapter>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<Chapter> call, @NonNull Response<Chapter> response) {
                Chapter chapterData = response.body();
                if (response.isSuccessful() && chapterData != null) {
                    //Set toolbar
                    AppCompatActivity activity = (AppCompatActivity) requireActivity();
                    activity.setSupportActionBar(mToolBar);
                    ActionBar actionBar = activity.getSupportActionBar();
                    setHasOptionsMenu(true);
                    Objects.requireNonNull(actionBar).setTitle("Chương " + chapter.getIndexChapter().get(position) + " " + chapterData.getContent());
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    //Set data recycler view
                    readChapAdapter.setData(chapterData.getLinkImage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Chapter> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(ReadChapFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    //Hide/show Nav
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