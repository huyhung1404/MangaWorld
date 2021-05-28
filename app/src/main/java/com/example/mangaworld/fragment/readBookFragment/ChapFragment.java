package com.example.mangaworld.fragment.readBookFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.fragment.readChapFragment.ReadChapFragment;
import com.example.mangaworld.object.Chap;
import com.example.mangaworld.fragment.readBookFragment.readBookAdapter.ChapAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChapFragment extends Fragment {
    public static final String TAG = ChapFragment.class.getName();
    private int numberChap;
    private String idBook;

    public ChapFragment(String idBook,int numberChap) {
        this.idBook = idBook;
        this.numberChap = numberChap;
    }

    public ChapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_chap, container, false);
        RecyclerView rcvChap = mView.findViewById(R.id.rcv_chap);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvChap.setLayoutManager(linearLayoutManager);
        MainActivity mainActivity = (MainActivity) getActivity();
        ChapAdapter chapAdapter= new ChapAdapter(getListChap(), chap -> mainActivity.nextChapFragment(chap,idBook,numberChap,true));
        chapAdapter.setData(getListChap());
        rcvChap.setAdapter(chapAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rcvChap.addItemDecoration(itemDecoration);
        return mView;
    }

    private List<Chap> getListChap() {
        List<Chap> listChap = new ArrayList<>();
        for (int i = 1; i <= numberChap ; i++) {
            listChap.add(new Chap(i));
        }
        return listChap;
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.i("aaa","ChapFragment OnCreate");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.i("aaa", "ChapFragment 0nStart");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.i("aaa", "ChapFragment 0nPause");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i("aaa", "ChapFragment Ondestroy");
//
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        Log.i("aaa", "ChapFragment 0nAttach");
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Log.i("aaa", "ChapFragment 0nActivityCreated");
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.i("aaa", "ChapFragment 0nDestroyView");
//
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.i("aaa", "ChapFragment 0nDetach");
//
//    }
}