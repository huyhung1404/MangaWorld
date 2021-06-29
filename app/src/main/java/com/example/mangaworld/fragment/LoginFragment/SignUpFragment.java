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
import com.example.mangaworld.main.MainActivity;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.object.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private EditText editGmail, editName, editUserName, editPassword, editPasswordConfirm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewSignUp = inflater.inflate(R.layout.fragment_sign_up, container, false);
        //Init
        Button buttonSignUp = viewSignUp.findViewById(R.id.btn_sign_up_account);
        TextView backLoginFragment = viewSignUp.findViewById(R.id.btn_signIn);
        editGmail = viewSignUp.findViewById(R.id.sign_up_gmail);
        editName = viewSignUp.findViewById(R.id.sign_up_name);
        editUserName = viewSignUp.findViewById(R.id.sign_up_user_name);
        editPassword = viewSignUp.findViewById(R.id.sign_up_password);
        editPasswordConfirm = viewSignUp.findViewById(R.id.sign_up_password_confirm);
        //Set hint edit text
        editGmail.setHint("Gmail");
        editName.setHint("Tên đăng nhập");
        editUserName.setHint("Tài khoản");
        editPassword.setHint("Mật khẩu");
        editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editPasswordConfirm.setHint("Nhập lại mật khẩu");
        editPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //Trở lại màn hình login
        backLoginFragment.setOnClickListener(v -> ((MainActivity) requireActivity()).loadFragment(new LoginFragment()));
        //Đăng kí
        buttonSignUp.setOnClickListener(v -> onClickButtonSignUp());
        return viewSignUp;
    }

    private void onClickButtonSignUp() {
        if (!editPassword.getText().toString().trim()
                .equals(editPasswordConfirm.getText().toString().trim())) {
            notification("Mật khẩu không trùng khớp");
            return;
        }
        User user = new User(editUserName.getText().toString().trim(),
                editPassword.getText().toString().trim(),
                editName.getText().toString().trim(),
                editGmail.getText().toString().trim());
        APIClient.getAPILogin().postUserSignUp(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(response.isSuccessful()){
                    notification("Tạo tài khoản thành công");
                    return;
                }
                notification("Tài khoản này đã được sử dụng");
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                notification("Lỗi kết nối vui lòng thử lại sau");
            }
        });

    }

    private void notification(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
        editGmail.setText("");
        editName.setText("");
        editUserName.setText("");
        editPassword.setText("");
        editPasswordConfirm.setText("");
    }

}
