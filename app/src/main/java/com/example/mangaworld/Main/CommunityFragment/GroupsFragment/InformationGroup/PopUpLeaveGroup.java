package com.example.mangaworld.Main.CommunityFragment.GroupsFragment.InformationGroup;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Message;
import com.example.mangaworld.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpLeaveGroup extends AppCompatDialogFragment {
    public static final String TAG = PopUpLeaveGroup.class.getName();
    private final long idGroup;
    private final InfoPopupFragment infoPopupFragment;

    public PopUpLeaveGroup(long idGroup, InfoPopupFragment infoPopupFragment) {
        this.idGroup = idGroup;
        this.infoPopupFragment = infoPopupFragment;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_fragment_leave_group, null);

        view.findViewById(R.id.accept_popup_leave_group).setOnClickListener(v -> sendAPI());
        view.findViewById(R.id.refuse_popup_leave_group).setOnClickListener(v -> dismiss());
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private void sendAPI() {
        APIClient.getAPICommunity().sendLeaveGroupRequest("Bearer " + MainActivity.user.getToken(), idGroup).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    responseSuccessful();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void responseSuccessful(){
        this.dismiss();
        infoPopupFragment.dismiss();
        CommunityFragment.GoToScreenInCommunity(new InformationGroupFragment(idGroup),InformationGroupFragment.TAG);
    }
}
