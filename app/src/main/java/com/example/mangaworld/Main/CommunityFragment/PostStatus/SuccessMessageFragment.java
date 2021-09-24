package com.example.mangaworld.Main.CommunityFragment.PostStatus;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.Main.CommunityFragment.NewsFragment.CommentStatusFragment.CommentStatusAdapter;
import com.example.mangaworld.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SuccessMessageFragment extends AppCompatDialogFragment {
    public static final String TAG = SuccessMessageFragment.class.getName();

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(requireActivity().getLayoutInflater().inflate(R.layout.layout_success_mesage, null));
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }
}
