package com.example.mangaworld.fragment.ReadMangaFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.mainActivityAdapter.CategoryAdapter;
import com.example.mangaworld.fragment.ReadMangaFragment.ReadMangaAdapter.SummaryAdapter;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.object.ListTagCategory;
import com.example.mangaworld.object.Manga;
import com.example.mangaworld.object.Chapter;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryFragment extends Fragment {
    private final List<ListTagCategory> listTagCategory;
    private final String summaryBook;
    private final long idBook;
    private AppCompatButton buttonLike;

    public SummaryFragment(long idBook,List<ListTagCategory> listTagCategory, String summaryBook) {
        this.idBook = idBook;
        this.listTagCategory = listTagCategory;
        this.summaryBook = summaryBook;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_summary, container, false);
        //Init
        RecyclerView rcv = mView.findViewById(R.id.rcv_summary_fragment);
        TextView textSummaryBook = mView.findViewById(R.id.text_summary_fragment);
        //Set text mô tả
        textSummaryBook.setText(summaryBook);
        //Rcv thể loại
        rcv.setItemViewCacheSize(5);
        SummaryAdapter summaryAdapter = new SummaryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        summaryAdapter.setData(listTagCategory, new CategoryAdapter.IClickItem() {
            @Override
            public void onClickItemBook(Manga manga) {

            }

            @Override
            public void onClickItemCategory(Long id,boolean isViewMore) {
                ((MainActivity) requireActivity()).nextCategoryFragment(id,isViewMore);
            }

            @Override
            public void onClickItemIcon(float id) {

            }
        });
        rcv.setAdapter(summaryAdapter);

        //Set button
        buttonLike = mView.findViewById(R.id.button_like_summary_fragment);
        AppCompatButton buttonRead = mView.findViewById(R.id.button_read_summary_fragment);
        buttonRead.setOnClickListener(v -> APIClient.getAPIChapter().dataChapFragment(idBook).enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(@NonNull Call<Chapter> call, @NonNull Response<Chapter> response) {
                if (response.isSuccessful()){
                    ((MainActivity) requireActivity()).nextChapFragment(response.body(),idBook,0,true);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Chapter> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),"Lỗi",Toast.LENGTH_SHORT).show();
            }
        }));
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
}