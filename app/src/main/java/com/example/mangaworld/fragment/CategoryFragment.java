package com.example.mangaworld.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.adapter.BookAdapter;
import com.example.mangaworld.adapter.CategoryAdapter;
import com.example.mangaworld.fragment.readChapFragment.ReadChapFragment;
import com.example.mangaworld.object.Book;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    public static final String TAG = CategoryFragment.class.getName();
    private String nameCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.fragment_category, container, false);

        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            this.nameCategory = (String) bundleReceive.get("object_string");
        }
        RecyclerView recyclerView = mView.findViewById(R.id.rcv_category_fragment);
        androidx.appcompat.widget.Toolbar mToolBar = mView.findViewById(R.id.tool_bar_category_fragment);

        MainActivity mMainActivity = (MainActivity) getActivity();
        mMainActivity.setSupportActionBar(mToolBar);
        ActionBar actionBar = mMainActivity.getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(nameCategory);
        setHasOptionsMenu(true);

        BookAdapter bookAdapter = new BookAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mMainActivity,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        bookAdapter.setData(setDataAdapter(), new CategoryAdapter.IClickItem() {
            @Override
            public void onClickItemBook(Book book) {
                mMainActivity.nextReadBookActivity(book);
            }
            @Override
            public void onClickItemCategory(String string) {

            }
        });
        recyclerView.setAdapter(bookAdapter);
        return mView;
    }

    private List<Book> setDataAdapter() {
        List<String> listTagCategory = new ArrayList<>();
        listTagCategory.add("Kiếm hiệp");
        listTagCategory.add("Võ thuật");
        listTagCategory.add("Tình cảm");
        listTagCategory.add("Khoa học viễn tưởng");

        List<Book> listBook = new ArrayList<>();
        listBook.add(new Book("book1","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/nguyen-ton.jpg",
                14,20,"Nguyên Tôn",30,"Hùng Bá", false,listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết .Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hếtDùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết."));
        listBook.add(new Book("book2","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/hoa-son-quyen.jpg",
                50,100,"Hỏa Sơn Quyền",20,"Hùng Bá", false,listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết"));
        listBook.add(new Book("book3","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/tu-tien-gia-dai-chien-sieu-nang-luc.jpg",
                1664,2000,"Tu Tiên Giả Đại Chiến Siêu Năng Lực",20,"Hùng Bá", false,listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết"));
        listBook.add(new Book("book4","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/nghich-thien-ta-than.jpg",
                14,20,"Nghịch Thiên Tà Thần",20,"Hùng Bá",false,listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết"));
        listBook.add(new Book("book5","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/tang-phong.jpg",
                14,20,"Tàng Phong",20,"Hùng Bá",false,listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết"));
        listBook.add(new Book("book6","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/ta-la-ta-de.jpg",
                50,100,"Ta Là Tà Đế",20,"Hùng Bá",false,listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết"));
        listBook.add(new Book("book7","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/bach-luyen-thanh-than.jpg",
                1664,2000,"Bách Luyện Thành Thần",20,"Hùng Bá",false,listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết"));
        listBook.add(new Book("book8","https://cdn.nettruyen.vn/file/nettruyen/thumbnails/vo-dao-doc-ton.jpg",
                14,20,"Võ Đạo Độc Tôn",20,"Hùng Bá",false,listTagCategory,
                "Dùng huyền hoàng khí bạn thân sở hữu, mọi thứ thiện địa nhật nguyệt tinh đều bị ta nuốt hết"));

        return listBook;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getFragmentManager().popBackStack(CategoryFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("aaa", "CategoryFragment 0nCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("aaa", "CategoryFragment 0nStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("aaa", "CategoryFragment 0nPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("aaa", "CategoryFragment OnDestroy");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("aaa", "CategoryFragment 0nAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("aaa", "CategoryFragment 0nActivityCreated");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("aaa", "CategoryFragment 0nDestroyView");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("aaa", "CategoryFragment 0nDetach");

    }
}