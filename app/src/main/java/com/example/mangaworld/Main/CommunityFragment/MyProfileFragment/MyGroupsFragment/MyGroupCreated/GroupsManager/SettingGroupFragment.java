package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.API.RealPathUtil;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager.PopUpDeleteGroup;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager.PopUpEditContentGroup;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager.PopUpEditNameGroup;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CreateGroup;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.Model.Community.ImageCall;
import com.example.mangaworld.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingGroupFragment extends Fragment {
    public static final String TAG = SettingGroupFragment.class.getName();
    private final long idGroup;
    private Groups m_Group;
    private ImageView m_AvatarGroup;
    private TextView m_NameGroup;
    private TextView m_PermissionGroup;
    private TextView m_DescriptionGroup;
    private TextView m_NumberMember;

    public SettingGroupFragment(long idGroup) {
        this.idGroup = idGroup;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_group, container, false);
        m_AvatarGroup = view.findViewById(R.id.avatar_setting_group);
        m_NameGroup = view.findViewById(R.id.name_setting_group);
        m_PermissionGroup = view.findViewById(R.id.permission_setting_group);
        m_DescriptionGroup = view.findViewById(R.id.content_setting_group);
        m_NumberMember = view.findViewById(R.id.number_member_setting_group);
        APIClient.getAPICommunity().getGroupByID("Bearer " + MainActivity.user.getToken(), idGroup).enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(@NonNull Call<Groups> call, @NonNull Response<Groups> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    SetData(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Groups> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy thông tin thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        m_AvatarGroup.setOnClickListener(v -> changeAvatarGroup());
        m_PermissionGroup.setOnClickListener(this::popupMenuPermissions);
        m_DescriptionGroup.setOnClickListener(v -> editContent());
        view.findViewById(R.id.delete_group_setting).setOnClickListener(v -> deleteGroup());
        initToolBar(view);
        return view;
    }

    private void initToolBar(View _view) {
        Toolbar toolbar = _view.findViewById(R.id.setting_group_toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Cài đặt");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @SuppressLint("SetTextI18n")
    public void SetData(Groups _group) {
        m_Group = _group;
        Glide.with(m_AvatarGroup.getContext())
                .load(m_Group.getAvatarGroup())
                .into(m_AvatarGroup);

        m_NameGroup.setText(m_Group.getName());
        m_NameGroup.setOnClickListener(v -> editName());

        if (m_Group.isPublicGroup()) {
            m_PermissionGroup.setText("Công khai");
        } else {
            m_PermissionGroup.setText("Riêng tư");
        }
        m_DescriptionGroup.setText(m_Group.getDescription());
        m_NumberMember.setText(String.valueOf(m_Group.getNumberOfUsers()));
    }

    @SuppressLint("NonConstantResourceId")
    private void popupMenuPermissions(View button) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), button);
        popupMenu.inflate(R.menu.menu_setting_permissions);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_public:
                    m_Group.setPublicGroup(true);
                    seenAPIPermissions();
                    return true;
                case R.id.item_private:
                    m_Group.setPublicGroup(false);
                    seenAPIPermissions();
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    private void deleteGroup() {
        new PopUpDeleteGroup()
                .show(requireActivity().getSupportFragmentManager(), PopUpDeleteGroup.TAG);
    }

    private void editName() {
        new PopUpEditNameGroup(m_Group, this)
                .show(requireActivity().getSupportFragmentManager(), PopUpEditNameGroup.TAG);
    }

    private void editContent() {
        new PopUpEditContentGroup(m_Group, this)
                .show(requireActivity().getSupportFragmentManager(), PopUpEditContentGroup.TAG);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(SettingGroupFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    private void seenAPIPermissions() {
        APIClient.getAPICommunity().updateInformationGroup("Bearer " + MainActivity.user.getToken(), new CreateGroup(
                m_Group.getId(),
                m_Group.getName(),
                m_Group.getCategoryId(),
                m_Group.getDescription(),
                m_Group.getImageId(),
                (m_Group.isPublicGroup()) ? 1 : null
        )).enqueue(new Callback<Groups>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Groups> call, @NonNull Response<Groups> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), String
                            .format("Nhóm đã được đổi thành %s",
                                    (m_Group.isPublicGroup()) ? "công khai" : "riêng tư"
                            ), Toast.LENGTH_SHORT).show();
                    if (m_Group.isPublicGroup()) {
                        m_PermissionGroup.setText("Công khai");
                    } else {
                        m_PermissionGroup.setText("Riêng tư");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Groups> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeAvatarGroup() {
        Intent image = new Intent();
        image.setType("image/*");
        image.setAction(Intent.ACTION_GET_CONTENT);
        requireActivity().startActivityForResult(Intent.createChooser(image, "Chọn ảnh"), 1);
        ((MainActivity) requireActivity()).setDataReceivedListener((requestCode, resultCode, data) -> {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                try {
                    Uri imageUri = data.getData();
                    m_AvatarGroup.setImageBitmap(MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri));
                    sendApiChangeAvatar(imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendApiChangeAvatar(Uri _uri) {
        String realPath = RealPathUtil.getRealPath(requireContext(), _uri);
        if (realPath == null) {
            Toast.makeText(requireContext(), "Không thế lấy được đường dẫn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(realPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        APIClient.getAPICommunity().postImageStatus("Bearer " + MainActivity.user.getToken(), multipartBody).enqueue(new Callback<ImageCall>() {
            @Override
            public void onResponse(@NonNull Call<ImageCall> call, @NonNull Response<ImageCall> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    sendAPICreate(response.body().getIds().get(0));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageCall> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Đính kèm ảnh không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendAPICreate(long _idImage) {
        APIClient.getAPICommunity().updateInformationGroup("Bearer " + MainActivity.user.getToken(), new CreateGroup(
                m_Group.getId(),
                m_Group.getName(),
                m_Group.getCategoryId(),
                m_Group.getDescription(),
                _idImage,
                (m_Group.isPublicGroup()) ? 1 : null
        )).enqueue(new Callback<Groups>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Groups> call, @NonNull Response<Groups> response) {
                if (response.isSuccessful()) {
                    m_Group.setImageId(_idImage);
                    Toast.makeText(requireContext(), "Đổi ảnh thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Groups> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                changeAvatarGroup();
            }
        }
    }
}