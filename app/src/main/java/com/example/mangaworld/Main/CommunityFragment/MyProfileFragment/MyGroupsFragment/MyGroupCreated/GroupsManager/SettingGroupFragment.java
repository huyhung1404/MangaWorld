package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager.PopUpDeleteGroup;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager.PopUpEditContentGroup;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager.PopUpEditNameGroup;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingGroupFragment extends Fragment {
    public static final String TAG = SettingGroupFragment.class.getName();
    private final long idGroup;

    public SettingGroupFragment(long idGroup) {
        this.idGroup = idGroup;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_group, container, false);
        APIClient.getAPICommunity().getGroupByID("Bearer " + MainActivity.user.getToken(), idGroup).enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(@NonNull Call<Groups> call, @NonNull Response<Groups> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    setData(view, response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Groups> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy thông tin thất bại", Toast.LENGTH_SHORT).show();
            }
        });
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
    private void setData(View view, Groups group) {
        ImageView avatar = view.findViewById(R.id.avatar_setting_group);
        Glide.with(avatar.getContext())
                .load(group.getAvatarGroup())
                .into(avatar);

        TextView nameGroup = view.findViewById(R.id.name_setting_group);
        nameGroup.setText(group.getName());
        nameGroup.setOnClickListener(v -> editName());

        TextView permission = view.findViewById(R.id.permission_setting_group);
        if (group.isPublicGroup()) {
            permission.setText("Công khai");
        } else {
            permission.setText("Riêng tư");
        }
        permission.setOnClickListener(this::popupMenuPermissions);

        view.findViewById(R.id.delete_group_setting).setOnClickListener(v -> deleteGroup());

        TextView descriptionGroup = view.findViewById(R.id.content_setting_group);
        descriptionGroup.setText(group.getDescription());
        descriptionGroup.setOnClickListener(v -> editContent());

        ((TextView) view.findViewById(R.id.number_member_setting_group))
                .setText(String.valueOf(group.getNumberOfUsers()));

    }

    @SuppressLint("NonConstantResourceId")
    private void popupMenuPermissions(View button) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), button);
        popupMenu.inflate(R.menu.menu_setting_permissions);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_public:
                    Log.e("tag", "pulic");
                    return true;
                case R.id.item_private:
                    Log.e("tag", "private");
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
        new PopUpEditNameGroup()
                .show(requireActivity().getSupportFragmentManager(), PopUpEditNameGroup.TAG);
    }

    private void editContent() {
        new PopUpEditContentGroup()
                .show(requireActivity().getSupportFragmentManager(), PopUpEditContentGroup.TAG);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(SettingGroupFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }
}