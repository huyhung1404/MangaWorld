package com.example.mangaworld.fragment.ReadMangaFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.fragment.ReadMangaFragment.ReadMangaAdapter.CommentAdapter;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.main.PaginationRecyclerView;
import com.example.mangaworld.object.Comment;
import com.example.mangaworld.object.SendComment;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {
    private final long idBook;
    private EditText contentComment;
    private CommentAdapter commentAdapter;
    private List<Comment> comments;
    private View view;

    public CommentFragment(long idBook) {
        this.idBook = idBook;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_comment, container, false);
        CircleImageView circleImageView = view.findViewById(R.id.img_my_avatar);
        ImageView btnSend = view.findViewById(R.id.btn_comment);
        contentComment = view.findViewById(R.id.edit_text_comment);
        //View Comment
        if (MainActivity.user != null) {
            RelativeLayout relativeLayout = view.findViewById(R.id.relative_layout_comment);
            relativeLayout.setVisibility(View.VISIBLE);
            if (MainActivity.user.getAvatar() != null) {
                Glide.with(requireContext()).load(MainActivity.user.getAvatar()).into(circleImageView);
            }
        }
        getDataAPI();
        btnSend.setOnClickListener(v -> sendComment());
        return view;
    }

    private void sendComment() {
        SendComment sendComment = new SendComment(contentComment.getText().toString().trim(), MainActivity.user.getUserName(), idBook);
        APIClient.getAPIChapter().sendComment("Bearer " + MainActivity.user.getToken(), sendComment).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(@NonNull Call<Comment> call, @NonNull Response<Comment> response) {
                if (response.isSuccessful()) {
                    if (comments == null) {
                        getDataAPI();
                        return;
                    }
                    commentAdapter.sendComment(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Comment> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(List<Comment> comments) {
        RecyclerView rcvComment = view.findViewById(R.id.rcv_comment);
        CardView cardView = view.findViewById(R.id.card_view_progress_bar_comment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvComment.setItemViewCacheSize(8);
        rcvComment.setLayoutManager(linearLayoutManager);
        int MAX_ITEM = comments.size();
        DividerItemDecoration dividerHorizontal = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        rcvComment.addItemDecoration(dividerHorizontal);
        commentAdapter = new CommentAdapter(MAX_ITEM <= 8 ? comments : comments.subList(0, 8));
        rcvComment.setAdapter(commentAdapter);
        rcvComment.addOnScrollListener(new PaginationRecyclerView(linearLayoutManager,MAX_ITEM) {
            @Override
            public void setData(int totalItems) {
                cardView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    commentAdapter.setData(comments.subList(0, Math.min(totalItems + 8, MAX_ITEM)));
                    cardView.setVisibility(View.GONE);
                }, 1500);
            }
        });
    }

    private void getDataAPI() {
        APIClient.getAPIChapter().getCommentByID(idBook).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call, @NonNull Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    comments = response.body();
                    setData(comments);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Comment>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

}