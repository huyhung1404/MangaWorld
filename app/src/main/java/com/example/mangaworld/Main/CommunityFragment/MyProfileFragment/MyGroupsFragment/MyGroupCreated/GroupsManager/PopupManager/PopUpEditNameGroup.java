package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.SettingGroupFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CreateGroup;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpEditNameGroup extends AppCompatDialogFragment {
    public static final String TAG = PopUpEditNameGroup.class.getName();
    private final Groups m_Group;
    private final SettingGroupFragment m_SettingGroupFragment;

    public PopUpEditNameGroup(Groups _group, SettingGroupFragment _settingGroupFragment) {
        this.m_Group = _group;
        this.m_SettingGroupFragment = _settingGroupFragment;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_fragment_edit_name, null);
        EditText editText = view.findViewById(R.id.edit_name_popup_fragment);
        editText.setHint(m_Group.getName());
        view.findViewById(R.id.accept_popup_edit_name).setOnClickListener(v -> changeName(editText));
        view.findViewById(R.id.refuse_popup_edit_name).setOnClickListener(v -> dismiss());
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private void changeName(EditText _textName) {
        if (_textName.length() == 0) {
            Toast.makeText(requireContext(), "Tên không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        APIClient.getAPICommunity().updateInformationGroup("Bearer " + MainActivity.user.getToken(), new CreateGroup(
                m_Group.getId(),
                _textName.getText().toString(),
                m_Group.getCategoryId(),
                m_Group.getDescription(),
                m_Group.getImageId(),
                (m_Group.isPublicGroup()) ? 1 : null
        )).enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(@NonNull Call<Groups> call, @NonNull Response<Groups> response) {
                if (response.isSuccessful()) {
                    m_SettingGroupFragment.SetData(response.body());
                    successful();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Groups> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(),"Lỗi kết nối",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void successful() {
        dismiss();
    }
}
