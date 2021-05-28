package com.example.mangaworld.fragment.readBookFragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.adapter.CategoryAdapter;
import com.example.mangaworld.adapter.SummaryAdapter;
import com.example.mangaworld.object.Book;
import com.example.mangaworld.object.Chap;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SummaryFragment extends Fragment {
    private List<String> listTagCategory;
    private String summaryBook,idBook;
    private AppCompatButton buttonLike;
    private int maxChap;

    public SummaryFragment(String idBook,List<String> listTagCategory, String summaryBook,int maxChap) {
        this.idBook = idBook;
        this.listTagCategory = listTagCategory;
        this.summaryBook = summaryBook;
        this.maxChap = maxChap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_summary, container, false);
        RecyclerView rcv = mView.findViewById(R.id.rcv_summary_fragment);
        TextView textSummaryBook = mView.findViewById(R.id.text_summary_fragment);
        textSummaryBook.setText(summaryBook);
        SummaryAdapter summaryAdapter = new SummaryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        summaryAdapter.setData(listTagCategory, new CategoryAdapter.IClickItem() {
            @Override
            public void onClickItemBook(Book book) {

            }

            @Override
            public void onClickItemCategory(String string) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.nextCategoryFragment(string);
            }
        });
        rcv.setAdapter(summaryAdapter);
        buttonLike = mView.findViewById(R.id.button_like_summary_fragment);
        AppCompatButton buttonRead = mView.findViewById(R.id.button_read_summary_fragment);
        buttonRead.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.nextChapFragment(new Chap(1),idBook,maxChap,true);
        });
        AtomicReference<Boolean> like = new AtomicReference<>(false);
        like.set(setButtonLike(like.get()));
        buttonLike.setOnClickListener(v -> like.set(setButtonLike(like.get())));
        return mView;
    }
    private boolean setButtonLike(Boolean like){
        if (like){
            buttonLike.setBackgroundResource(R.drawable.custom_button_diss_like_book);
            buttonLike.setText(R.string.dis_like);
            return false;
        }
        buttonLike.setBackgroundResource(R.drawable.custom_button_like_book);
        buttonLike.setText(R.string.like);
        return true;
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.i("aaa", "SummaryFragment 0nCreate");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.i("aaa", "SummaryFragment 0nStart");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.i("aaa", "SummaryFragment 0nPause");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i("aaa", "SummaryFragment Ondestroy");
//
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        Log.i("aaa", "SummaryFragment 0nAttach");
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Log.i("aaa", "SummaryFragment 0nActivityCreated");
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.i("aaa", "Summary 0nDestroyView");
//
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.i("aaa", "SummryFragment 0nDetach");
//
//    }
}