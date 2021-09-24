package com.example.mangaworld.Main.ReadManga.Element.InfomationMangaFragment.SummaryFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaworld.Interface.IsLoadingApi;
import com.example.mangaworld.Interface.SelectItemInRecycleView;
import com.example.mangaworld.Model.Message;
import com.example.mangaworld.R;
import com.example.mangaworld.Main.HomeFragment.RankFragment.RankFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Model.ListTagCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryFragment extends Fragment {
    private final List<ListTagCategory> listTagCategory;
    private final String summaryBook;
    private final long idBook;
    private String readingIndex = null;
    private final IsLoadingApi isLoadingApi;
    private boolean likedTheBook = false;

    public SummaryFragment(long idBook, List<ListTagCategory> listTagCategory, String summaryBook, IsLoadingApi isLoadingApi) {
        this.idBook = idBook;
        this.listTagCategory = listTagCategory;
        this.summaryBook = summaryBook;
        this.isLoadingApi = isLoadingApi;
    }
    public SummaryFragment(long idBook, List<ListTagCategory> listTagCategory, String summaryBook, String readingIndex,boolean likedTheBook, IsLoadingApi isLoadingApi) {
        this.idBook = idBook;
        this.listTagCategory = listTagCategory;
        this.summaryBook = summaryBook;
        this.readingIndex = readingIndex;
        this.isLoadingApi = isLoadingApi;
        this.likedTheBook = likedTheBook;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_summary, container, false);

        RecyclerView rcv = mView.findViewById(R.id.rcv_summary_fragment);
        TextView textSummaryBook = mView.findViewById(R.id.text_summary_fragment);

        textSummaryBook.setText(summaryBook);
        rcv.setItemViewCacheSize(5);
        SummaryAdapter summaryAdapter = new SummaryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        summaryAdapter.setData(listTagCategory, new SelectItemInRecycleView() {
            @Override
            public void onClickItemBook(long idManga) {

            }

            @Override
            public void onClickItemCategory(Long id,boolean isViewMore) {
                ((MainActivity) requireActivity()).goToCategoryMangaScreen(id,isViewMore);
            }

            @Override
            public void onClickItemIcon(float id) {

            }
        });
        rcv.setAdapter(summaryAdapter);
        isLoadingApi.loadDone();
        initButtonRead(mView);
        setButtonShare(mView);
        ImageView buttonLike = mView.findViewById(R.id.button_like_summary_fragment);
        if (likedTheBook){
            buttonLike.setImageResource(R.drawable.icon_like_book);
        }
        buttonLike.setOnClickListener(v -> setButtonLike(buttonLike));
        return mView;
    }
    private void setButtonLike(ImageView buttonLike){
        if (!MainActivity.isLogin){
            Toast.makeText(requireContext(), "Cần đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
            return;
        }
        if (likedTheBook){
            buttonLike.setImageResource(R.drawable.icon_none_like);
            sendAPILikeManga(0);
            likedTheBook = false;
            return;
        }
        buttonLike.setImageResource(R.drawable.icon_like_book);
        sendAPILikeManga(1);
        likedTheBook = true;
    }
    private void sendAPILikeManga(int like){
        APIClient.getAPIChapter().likeManga("Bearer " + MainActivity.user.getToken(),idBook,like).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {

            }
        });
    }
    private void setButtonShare(View view){
        view.findViewById(R.id.button_share_summary_fragment).setOnClickListener(v -> {
            if (!MainActivity.isLogin){
                Toast.makeText(requireContext(), "Cần đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                return;
            }
            ((MainActivity) requireActivity()).postStatusAttachManga(idBook);
        });
    }

    @SuppressLint("SetTextI18n")
    private void initButtonRead(View mView){
        AppCompatButton buttonRead = mView.findViewById(R.id.button_read_summary_fragment);
        if (readingIndex!=null){
            buttonRead.setText("Đọc tiếp chương "+ readingIndex);
            buttonRead.setTextSize(13);
            buttonRead.setOnClickListener(v -> setButtonRead(Integer.parseInt(readingIndex)-1));
        }else {
            buttonRead.setOnClickListener(v -> setButtonRead(0));
        }
    }

    private void setButtonRead(int indexChap){
        APIClient.getAPIChapter().dataChapFragment(idBook).enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(@NonNull Call<List<Long>> call, @NonNull Response<List<Long>> response) {
                if (response.isSuccessful()){
                    ((MainActivity) requireActivity()).goToReadMangaScreen(response.body(),idBook,indexChap,true);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Long>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),"Lỗi",Toast.LENGTH_SHORT).show();
            }
        });
    }
}