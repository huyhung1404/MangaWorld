package com.example.mangaworld.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.R;
import com.example.mangaworld.adapter.BookCaseAdapter;
import com.example.mangaworld.object.Book;

import java.util.ArrayList;
import java.util.List;

public class BookcaseFragment extends Fragment {
    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bookcase, container, false);
        RecyclerView recyclerView = mView.findViewById(R.id.rcv_book_case);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        BookCaseAdapter bookCaseAdapter = new BookCaseAdapter();
        bookCaseAdapter.setData(setDataAdapter());
        recyclerView.setAdapter(bookCaseAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return mView;
    }

    private List<Book> setDataAdapter() {
        List<Book> listBook = new ArrayList<>();
        listBook.add(new Book("book1","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/nguyen-ton.jpg","Nguyên Tôn",30));
        listBook.add(new Book("book2","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/hoa-son-quyen.jpg", "Hỏa Sơn Quyền",20));
        listBook.add(new Book("book3","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/tu-tien-gia-dai-chien-sieu-nang-luc.jpg", "Tu Tiên Giả Đại Chiến Siêu Năng Lực",20));
        listBook.add(new Book("book4","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/nghich-thien-ta-than.jpg", "Nghịch Thiên Tà Thần",20));
        listBook.add(new Book("book5","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/tang-phong.jpg", "Tàng Phong",20));
        return  listBook;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("aaa", "BookCaseFragment 0nCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("aaa", "BookCaseFragment 0nStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("aaa", "BookCaseFragment 0nPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("aaa", "BookCaseFragment OnDestroy");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("aaa", "BookCaseFragment 0nAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("aaa", "BookCaseFragment 0nActivityCreated");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("aaa", "BookCaseFragment 0nDestroyView");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("aaa", "BookCaseFragment 0nDetach");

    }
}