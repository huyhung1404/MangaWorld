package com.example.mangaworld.fragment;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.adapter.CategoryAdapter;
import com.example.mangaworld.object.Book;
import com.example.mangaworld.object.Category;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView rcvCategory = mView.findViewById(R.id.rcv_category);
        MainActivity mMainActivity = (MainActivity) getActivity();
        CategoryAdapter categoryAdapter = new CategoryAdapter(mMainActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mMainActivity,RecyclerView.VERTICAL,false);
        rcvCategory.setLayoutManager(linearLayoutManager);
        categoryAdapter.setData(getListCategoryData(), new CategoryAdapter.IClickItem() {
            @Override
            public void onClickItemBook(Book book) {
                mMainActivity.nextReadBookActivity(book);
            }

            @Override
            public void onClickItemCategory(String string) {
                mMainActivity.nextCategoryFragment(string);
            }
        });
        rcvCategory.setAdapter(categoryAdapter);
        return mView;
    }
    private List<Category> getListCategoryData(){
        List<Category> listCategory = new ArrayList<>();

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

        listCategory.add(new Category("Thịnh Hành",listBook));
        listCategory.add(new Category("Mới Cập Nhập",listBook));
        listCategory.add(new Category("Đã hoàn thành",listBook));
        listCategory.add(new Category("Yêu thích",listBook));

        return listCategory;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("aaa", "HomeFragment 0nCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("aaa", "HOmeFragment 0nStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("aaa", "HomeFragment 0nPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("aaa", "HomeFragment Ondestroy");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("aaa", "HomeFragment 0nAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("aaa", "HomeFragment 0nActivityCreated");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("aaa", "Home 0nDestroyView");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("aaa", "HomeFragment 0nDetach");

    }
}