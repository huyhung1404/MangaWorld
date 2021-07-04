package com.example.mangaworld.fragment.LoginFragment;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mangaworld.R;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.object.Message;
import com.example.mangaworld.object.Password;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewForgot = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        //Init
        TextView backLoginFragment = viewForgot.findViewById(R.id.btn_login_forgot);
        Button accessButton = viewForgot.findViewById(R.id.btn_forgot);
        EditText editGmailForgot = viewForgot.findViewById(R.id.gmail_forgot);
        EditText editNewPassword = viewForgot.findViewById(R.id.new_password_forgot);
        EditText editRePassword = viewForgot.findViewById(R.id.re_new_password_forgot);
        EditText OTP = viewForgot.findViewById(R.id.forgot_otp);
        Button sendOTP = viewForgot.findViewById(R.id.send_otp);
        //Set hint edit text
        editGmailForgot.setHint("Gmail đăng kí");
        editNewPassword.setHint("Mật khẩu mới");
        editNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editRePassword.setHint("Nhập lại mật khẩu");
        editRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //
        backLoginFragment.setOnClickListener(v -> ((MainActivity) requireActivity()).loadFragment(new LoginFragment()));
        accessButton.setOnClickListener(v -> {
            if (!checkExceptionInput(editGmailForgot, editNewPassword, editRePassword, OTP)) return;
            resetPassword(OTP, editGmailForgot, editNewPassword, editRePassword);

        });
        sendOTP.setOnClickListener(v -> sendOTPApi(editGmailForgot));
        return viewForgot;
    }

    private boolean checkExceptionInput(EditText editGmail, EditText editPassword, EditText editPasswordConfirm, EditText OTP) {
        if (editGmail.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Gmail không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!editGmail.getText().toString().trim().contains("@gmail.com")) {
            Toast.makeText(requireContext(), "Gmail không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editPassword.getText().toString().length() < 6) {
            Toast.makeText(requireContext(), "Mật khẩu không được ít hơn 6 kí tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!editPassword.getText().toString().trim()
                .equals(editPasswordConfirm.getText().toString().trim())) {
            Toast.makeText(requireContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            editPassword.setText("");
            editPasswordConfirm.setText("");
            OTP.setText("");
            return false;
        }
        if (OTP.getText().toString().length() != 6) {
            Toast.makeText(requireContext(), "Mã xác nhận gồm 6 ký tự", Toast.LENGTH_SHORT).show();
            OTP.setText("");
            return false;
        }
        return true;
    }

    private void sendOTPApi(EditText editGmail) {
        if (!editGmail.getText().toString().trim().contains("@gmail.com")) {
            Toast.makeText(requireContext(), "Gmail không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        APIClient.getAPILogin().sendOTP(editGmail.getText().toString().trim()).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Mã OTP đã được gửi vào gmail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword(EditText OTP, EditText gmail, EditText password, EditText rePassword) {
        APIClient.getAPILogin().checkOTP(
                new Password(OTP.getText().toString(), gmail.getText().toString().trim(), null, false))
                .enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().isVerify()) {
                                changePassword(gmail.getText().toString().trim(), password);
                                OTP.setText("");
                                gmail.setText("");
                                password.setText("");
                                rePassword.setText("");
                                return;
                            }
                            Toast.makeText(requireContext(), "Mã xác nhận không đúng", Toast.LENGTH_SHORT).show();
                            OTP.setText("");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                        Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void changePassword(String gmail, EditText password) {
        APIClient.getAPILogin().resetPassword(
                new Password(null, gmail, password.getText().toString().trim(), true))
                .enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                        assert response.body() != null;
                        if (response.body().getChange().equals("successfully")) {
                            Toast.makeText(requireContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                        Toast.makeText(requireContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
