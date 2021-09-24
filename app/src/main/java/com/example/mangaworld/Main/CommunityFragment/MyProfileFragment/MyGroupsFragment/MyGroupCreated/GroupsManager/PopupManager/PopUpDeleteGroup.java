package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mangaworld.R;

public class PopUpDeleteGroup extends AppCompatDialogFragment {
    public static final String TAG = PopUpDeleteGroup.class.getName();

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_fragment_delete_group, null);

        view.findViewById(R.id.accept_popup_delete_group).setOnClickListener(v -> sendAPI());
        view.findViewById(R.id.refuse_popup_delete_group).setOnClickListener(v -> dismiss());
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }
    private void sendAPI(){
        dismiss();
    }
}
