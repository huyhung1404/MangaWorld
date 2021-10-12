package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.ColorStateList;
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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Interface.CallbackData;
import com.example.mangaworld.Interface.ISelectCategoryGroup;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.CreateNewGroupFragment;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.SettingGroupFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CreateGroup;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.Model.ListTagCategory;
import com.example.mangaworld.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpSelectCategoryGroup extends AppCompatDialogFragment implements ISelectCategoryGroup {
    public static final String TAG = PopUpSelectCategoryGroup.class.getName();
    private Groups m_Group = null;
    private CallbackData.SelectCategoryChange m_SettingGroup;
    private CallbackData.SelectCategoryCreate m_CreateNewGroup;
    private long idCategorySelect;
    private CardView cardViewSelect = null;
    private ColorStateList NONE_SELECT_COLOR;
    private String nameCategory;


    public PopUpSelectCategoryGroup(CallbackData.SelectCategoryCreate _createNew) {
        m_CreateNewGroup = _createNew;
    }

    public PopUpSelectCategoryGroup(Groups _group, CallbackData.SelectCategoryChange _settingGroup) {
        m_Group = _group;
        m_SettingGroup = _settingGroup;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_select_category_group, null);
        NONE_SELECT_COLOR = ContextCompat.getColorStateList(requireContext(), R.color.white);
        APIClient.getAPIHome().getAllCategory().enqueue(new Callback<List<ListTagCategory>>() {
            @Override
            public void onResponse(@NonNull Call<List<ListTagCategory>> call, @NonNull Response<List<ListTagCategory>> response) {
                if (response.isSuccessful()) {
                    setData(response.body(), view);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ListTagCategory>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.button_select_group_accept).setOnClickListener(v -> sendCategory());
        view.findViewById(R.id.button_select_group_refuse).setOnClickListener(v -> dismiss());
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private void setData(List<ListTagCategory> tagCategories, View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rcv_select_category_group);
        recyclerView.setItemViewCacheSize(15);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        PopUpSelectCategoryAdapter adapter = new PopUpSelectCategoryAdapter(tagCategories, requireContext(), this);
        recyclerView.setAdapter(adapter);
    }

    private void sendCategory() {
        if (cardViewSelect == null) {
            Toast.makeText(requireContext(), "Chọn thể loại trước khi xác nhận", Toast.LENGTH_SHORT).show();
            return;
        }
        if(m_Group != null){
            changeAPI();
            return;
        }
        m_CreateNewGroup.setData(idCategorySelect,nameCategory);
        dismiss();
    }

    @Override
    public void selectCategory(long id,String name, CardView cardView) {
        idCategorySelect = id;
        nameCategory = name;
        if (cardViewSelect != null) {
            cardViewSelect.setCardBackgroundColor(NONE_SELECT_COLOR);
        }
        cardViewSelect = cardView;
    }
    private void changeAPI() {
        APIClient.getAPICommunity().updateInformationGroup("Bearer " + MainActivity.user.getToken(), new CreateGroup(
                m_Group.getId(),
                m_Group.getName(),
                idCategorySelect,
                m_Group.getDescription(),
                m_Group.getImageId(),
                (m_Group.isPublicGroup()) ? 1 : null
        )).enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(@NonNull Call<Groups> call, @NonNull Response<Groups> response) {
                if (response.isSuccessful()) {
                    m_SettingGroup.setData(response.body());
                    successful();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Groups> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void successful() {
        dismiss();
    }
}
