package com.example.mangaworld.Main.CommunityFragment.GroupsFragment.InformationGroup;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager.PopUpEditNameGroup;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class InfoPopupFragment extends AppCompatDialogFragment {
    public static final String TAG = InfoPopupFragment.class.getName();
    private final Groups group;

    public InfoPopupFragment(Groups group) {
        this.group = group;
    }

    @SuppressLint({"InflateParams", "DefaultLocale"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_popup_info_group, null);
        //Tên group
        ((TextView) view.findViewById(R.id.name_group_info)).setText(group.getName());
        //Thông tin group
        ((TextView) view.findViewById(R.id.intro_group_info)).setText(group.getDescription());
        //Số lượng thành viên
        ((TextView) view.findViewById(R.id.number_member_info)).setText(String.format("Thành viên: %d",group.getNumberOfUsers()));
        //Số lượng bài đăng
        ((TextView) view.findViewById(R.id.number_post_info)).setText(String.format("Bài viết: %d",group.getNumberOfPosts()));
        //Ảnh đại diện admin
        ImageView avatarAdmin = view.findViewById(R.id.avatar_admin_info_group);
        Glide.with(avatarAdmin.getContext())
                .load(group.getAdmin().getLinkImage())
                .into(avatarAdmin);
        //Tên admin
        ((TextView) view.findViewById(R.id.name_admin_info_group)).setText(group.getAdmin().getFullName());

        //Rời nhóm
        TextView leaveGroup = view.findViewById(R.id.leave_the_group);
        if(group.isInvitation() && MainActivity.user.getId() != group.getAdmin().getId()){
            leaveGroup.setVisibility(View.VISIBLE);
            leaveGroup.setOnClickListener(v -> onClickLeaveGroup());
        }else {
            leaveGroup.setVisibility(View.GONE);
        }

        bottomSheetDialog.setContentView(view);
        return bottomSheetDialog;
    }

    private void onClickLeaveGroup(){
        new PopUpLeaveGroup(group.getId(),this)
                .show(requireActivity().getSupportFragmentManager(), PopUpLeaveGroup.TAG);
    }
}
