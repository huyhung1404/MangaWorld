package com.example.mangaworld.Main.CommunityFragment.NewsFragment.CommentStatusFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Interface.IReplyComment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CommentStatus;
import com.example.mangaworld.Model.Community.CommentValues;
import com.example.mangaworld.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentStatusFragment extends AppCompatDialogFragment implements IReplyComment {
    public static final String TAG = CommentStatusFragment.class.getName();
    private final String mongoId;
    private CommentStatus commentStatus;
    private final List<Integer> commentValueTypes = new ArrayList<>();
    private final Map<Integer, Integer> initComment = new HashMap<>();
    private EditText editContent;
    private CommentStatusAdapter commentStatusAdapter;
    private TextView textNoComment;
    private ImageView destroyReplyButton;
    private boolean isNestedComment = false;
    private int positionNested;

    public CommentStatusFragment(String mongoId) {
        this.mongoId = mongoId;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_comment_status_fragment, null);
        APIClient.getAPICommunity().getCommentData("Bearer " + MainActivity.user.getToken(), mongoId).enqueue(new Callback<CommentStatus>() {
            @Override
            public void onResponse(@NonNull Call<CommentStatus> call, @NonNull Response<CommentStatus> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    commentStatus = response.body();
                    getDataComment(view);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentStatus> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
        bottomSheetDialog.setContentView(view);
        return bottomSheetDialog;
    }


    private void getDataComment(View view) {
        textNoComment = view.findViewById(R.id.text_no_comment_in_post);
        editContent = view.findViewById(R.id.edit_text_send_comment_status);
        destroyReplyButton = view.findViewById(R.id.destroy_reply_button);
        destroyReplyButton.setOnClickListener(this::onClickDestroyReply);
        view.findViewById(R.id.send_comment_status).setOnClickListener(this::sendComment);

        RecyclerView recyclerView = view.findViewById(R.id.rcv_comment_status);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        commentStatusAdapter = new CommentStatusAdapter(this);
        recyclerView.setAdapter(commentStatusAdapter);

        if (commentStatus.getCommentValues() == null) {
            textNoComment.setVisibility(View.VISIBLE);
            return;
        }
        commentStatusAdapter.setData(listCommentHandling(), commentValueTypes);
    }

    private List<CommentValues> listCommentHandling() {
        List<CommentValues> commentValues = new ArrayList<>();
        commentValueTypes.clear();
        initComment.clear();
        int index = 0;
        for (CommentValues value : commentStatus.getCommentValues()) {
            commentValues.add(value);
            initComment.put(commentValues.size() - 1, index++);
            commentValueTypes.add(1);
            if (value.getCommentValues() != null) {
                commentValues.addAll(value.getCommentValues());
                int i = 0;
                while (i < value.getCommentValues().size()) {
                    commentValueTypes.add(2);
                    ++i;
                }
            }
        }
        return commentValues;
    }

    private void sendComment(View v) {
        if (editContent.length() == 0) {
            Toast.makeText(getContext(), "Bình luận không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isNestedComment) {
            if (commentStatus.getCommentValues()
                    .get(positionNested).getCommentValues() == null) {
                commentStatus.getCommentValues()
                        .get(positionNested).setCommentValues(new ArrayList<>());
            }
            commentStatus.getCommentValues()
                    .get(positionNested).getCommentValues()
                    .add(new CommentValues(editContent.getText().toString()));
            postAPISendComment(v);
            return;
        }
        if (commentStatus.getCommentValues() == null) {
            commentStatus.setCommentValues(new ArrayList<>());
        }
        commentStatus.getCommentValues()
                .add(new CommentValues(editContent.getText().toString()));
        postAPISendComment(v);
    }

    private void postAPISendComment(View v) {
        APIClient.getAPICommunity()
                .postCommentData("Bearer " + MainActivity.user.getToken(), commentStatus)
                .enqueue(new Callback<CommentStatus>() {
                    @Override
                    public void onResponse(@NonNull Call<CommentStatus> call, @NonNull Response<CommentStatus> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            commentStatus = response.body();
                            commentStatusAdapter.setData(listCommentHandling(), commentValueTypes);
                            textNoComment.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommentStatus> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Lỗi đường truyền", Toast.LENGTH_SHORT).show();
                    }
                });
        editContent.setText("");
        isNestedComment = false;
        editContent.setHint("Nhập bình luận");
        hideKeyboard(v);
    }

    @Override
    public void replyComment(int position, String name) {
        destroyReplyButton.setVisibility(View.VISIBLE);
        editContent.setHint(String.format("Trả lời %s", name));
        isNestedComment = true;
        positionNested = initComment.get(position);
    }

    private void onClickDestroyReply(View v) {
        destroyReplyButton.setVisibility(View.GONE);
        editContent.setHint("Nhập bình luận");
        isNestedComment = false;
        hideKeyboard(v);
    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
