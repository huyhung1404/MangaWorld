package com.example.mangaworld.fragment.readChapFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.object.Chap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ReadChapFragment extends Fragment {
    public static final String TAG = ReadChapFragment.class.getName();
    private String idBook;
    private int maxChap,currentlyChap;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_read_chap, container, false);

        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            Chap chap = (Chap) bundleReceive.get("object_chap");
            this.idBook = bundleReceive.getString("idBook");
            this.maxChap = bundleReceive.getInt("maxChap");
            this.currentlyChap = chap.getNumberChap();
        }

        androidx.appcompat.widget.Toolbar mToolBar = mView.findViewById(R.id.tool_bar_read_chap);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolBar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(currentlyChap + idBook);
        }
        setHasOptionsMenu(true);
        buttonClickFunction();
        RecyclerView rcvReadChap = mView.findViewById(R.id.rcv_read_chap);
        ReadChapAdapter readChapAdapter = new ReadChapAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rcvReadChap.setLayoutManager(linearLayoutManager);
        readChapAdapter.setData(setDataImg());
        rcvReadChap.setAdapter(readChapAdapter);

        return mView;
    }

    private void buttonClickFunction(){
        FloatingActionButton btnNextChap = mView.findViewById(R.id.btn_next_chap);
        FloatingActionButton btnLastChap = mView.findViewById(R.id.btn_last_chap);
        if(currentlyChap == 1){
            btnLastChap.hide();
        }else if (currentlyChap == maxChap){
            btnNextChap.hide();
        }

        btnNextChap.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.nextChapFragment(new Chap(++currentlyChap),idBook,maxChap,false);
        });
        btnLastChap.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.nextChapFragment(new Chap(--currentlyChap),idBook,maxChap,false);
        });
    }

    private List<String> setDataImg() {
        List<String> list = new ArrayList<>();
        list.add("https://icdn.nettruyen.vn/images/giai-thoat-99-nu-chinh-o-mat-the/chuong-1/c3c3bbda652d337b3ccaf8c73ff36c37.jpg");
        list.add("https://icdn.nettruyen.vn/images/giai-thoat-99-nu-chinh-o-mat-the/chuong-1/505eca4c602d091328f0bbc246edfc2d.jpg");
        list.add("https://icdn.nettruyen.vn/images/giai-thoat-99-nu-chinh-o-mat-the/chuong-1/361e8c616d089382efcd6366e53e18d2.jpg");
        list.add("https://icdn.nettruyen.vn/images/giai-thoat-99-nu-chinh-o-mat-the/chuong-1/a2a18132fbb677731012adcf0a8d7401.jpg");
        list.add("https://icdn.nettruyen.vn/images/giai-thoat-99-nu-chinh-o-mat-the/chuong-1/820843b04330dfb940e3d9553eb9fcf3.jpg");
        list.add("https://icdn.nettruyen.vn/images/giai-thoat-99-nu-chinh-o-mat-the/chuong-1/6d93b4eb15c76a8067b172179329d8ba.jpg");
        list.add("https://icdn.nettruyen.vn/images/giai-thoat-99-nu-chinh-o-mat-the/chuong-1/f4d7fdd380c1cfa015e61d159402b80d.jpg");
        list.add("https://icdn.nettruyen.vn/images/giai-thoat-99-nu-chinh-o-mat-the/chuong-1/d47156dfd9801e33178b1549a46a3853.jpg");
        list.add("https://icdn.nettruyen.vn/images/giai-thoat-99-nu-chinh-o-mat-the/chuong-1/e35d436a7ac1494fcc7968867a301a6f.jpg");
        list.add("https://icdn.nettruyen.vn/images/giai-thoat-99-nu-chinh-o-mat-the/chuong-1/70aca5ff46f6d3edd15e00823ed3b38c.jpg");
        return list;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      getFragmentManager().popBackStack(ReadChapFragment.TAG,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
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