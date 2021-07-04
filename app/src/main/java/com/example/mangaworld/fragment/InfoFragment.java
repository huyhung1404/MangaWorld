package com.example.mangaworld.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.fragment.LoginFragment.LoginFragment;
import com.example.mangaworld.object.Message;
import com.example.mangaworld.object.Password;
import com.example.mangaworld.object.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends Fragment {
    private boolean flagButton = false;
//    private final User user;

//    public InfoFragment(User user) {
//        this.user = user;
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewInfo = inflater.inflate(R.layout.fragment_info, container, false);
        //Ảnh đại diện
        CircleImageView avatar = viewInfo.findViewById(R.id.img_avatar_info);
        Glide.with(requireContext()).load(MainActivity.user.getAvatar()).into(avatar);

        setDataInformation(viewInfo);

        Button buttonChange = viewInfo.findViewById(R.id.btn_change_password);
        buttonChange.setOnClickListener(v -> changePassword(viewInfo));

        return viewInfo;
    }

    private void setDataInformation(View mView) {
        //Phần thông tin
        RelativeLayout layoutInfo = mView.findViewById(R.id.relative_layout_info_fragment);
        RelativeLayout layoutPassword = mView.findViewById(R.id.relative_layout_password_info_fragment);
        //Nút mở rộng
        Button buttonExpandInfo = mView.findViewById(R.id.button_expand_info);
        Button buttonExpandPassword = mView.findViewById(R.id.button_expand_password);
        //Cardview lưu thông tin
        CardView cardViewInfo = mView.findViewById(R.id.card_view_info);
        CardView cardViewPassword = mView.findViewById(R.id.card_view_password);
        //Animation expand infomation
        buttonExpandInfo.setOnClickListener(v -> expandInformation(cardViewInfo, layoutInfo, buttonExpandInfo, layoutPassword, buttonExpandPassword));
        buttonExpandPassword.setOnClickListener(v -> expandInformation(cardViewPassword, layoutPassword, buttonExpandPassword, layoutInfo, buttonExpandInfo));

        //Infomation user
        ArrayList<EditText> editTexts = new ArrayList<>();
        //Tên người dùng
        EditText editTextName = mView.findViewById(R.id.edit_text_name_info);
        editTexts.add(editTextName);
        editTextName.setText(MainActivity.user.getFullName());
        //Số điện thoại
        EditText editTextPhone = mView.findViewById(R.id.edit_text_sdt_info);
        editTexts.add(editTextPhone);
        editTextPhone.setText(MainActivity.user.getPhone());
        //Gmail
        EditText editTextGmail = mView.findViewById(R.id.edit_text_gmail_info);
        editTexts.add(editTextGmail);
        editTextGmail.setText(MainActivity.user.getEmail());
        //Button sửa lại thông tin
        ImageView buttonEdit = mView.findViewById(R.id.button_edit_text_name);
        buttonEdit.setOnClickListener(v -> flagButton = editButtonClick(editTexts, flagButton));

        //Button đăng xuất
        CardView logOutButton = mView.findViewById(R.id.card_view_log_out);
        logOutButton.setOnClickListener(v -> {
            MainActivity.isLogin = false;
            MainActivity.user = null;
            ((MainActivity) requireActivity()).loadFragment(new LoginFragment());
        });
    }

    //Animation Cardview
    private void expandInformation(CardView cardVisible, RelativeLayout layoutVisible, Button buttonVisible,
                                   RelativeLayout layoutGone, Button buttonGone) {
        if (layoutVisible.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(cardVisible, new AutoTransition());
            layoutGone.setVisibility(View.GONE);
            layoutVisible.setVisibility(View.VISIBLE);
            buttonVisible.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            buttonGone.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            return;
        }
        layoutVisible.setVisibility(View.GONE);
        buttonVisible.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
    }

    //ButtonEdit
    private boolean editButtonClick(ArrayList<EditText> editTexts, boolean flag) {
        for (EditText editText : editTexts) {
            editText.setFocusableInTouchMode(!flag);
            editText.setFocusable(!flag);
        }
        Toast.makeText(getContext(), flag ? "Đã lưu" : "Có thể chỉnh sửa", Toast.LENGTH_SHORT).show();
        return !flag;
    }

    //Đổi mật khẩu
    private void changePassword(View view) {
        EditText oldPassword = view.findViewById(R.id.old_password);
        EditText newPassword = view.findViewById(R.id.new_password);
        EditText reNewPassword = view.findViewById(R.id.new_password_2);
        if (!checkInputException(oldPassword, newPassword, reNewPassword)) return;
        Password password = new Password(oldPassword.getText().toString().trim(), newPassword.getText().toString().trim(), reNewPassword.getText().toString().trim());
        APIClient.getAPILogin().changePassword(MainActivity.user.getId(), password).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.body() != null) {
                    Toast.makeText(requireContext(), response.body().getMessage()
                            .equals("successfully") ? "Đổi mật khẩu thành công" : "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT)
                            .show();
                    oldPassword.setText("");
                    newPassword.setText("");
                    reNewPassword.setText("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean checkInputException(EditText oldPassword, EditText newPassword, EditText reNewPassword) {
        if (oldPassword.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPassword.getText().toString().length() < 6) {
            Toast.makeText(requireContext(), "Mật khẩu không được ít hơn 6 kí tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPassword.getText().toString().trim()
                .equals(reNewPassword.getText().toString().trim())) {
            Toast.makeText(requireContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            newPassword.setText("");
            reNewPassword.setText("");
            return false;
        }
        return true;
    }
}