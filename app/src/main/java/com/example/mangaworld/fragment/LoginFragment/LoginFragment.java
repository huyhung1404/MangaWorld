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
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.api.APIClient;
import com.example.mangaworld.fragment.InfoFragment;
import com.example.mangaworld.object.User;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewLogin = inflater.inflate(R.layout.fragment_login, container, false);
        //Init
        Button btnLogin = viewLogin.findViewById(R.id.btn_login);
        TextView btnSignUp = viewLogin.findViewById(R.id.btn_signup);
        EditText editUserName = viewLogin.findViewById(R.id.edt_username);
        EditText editUserPassword = viewLogin.findViewById(R.id.edt_password);
        TextView btnForgot = viewLogin.findViewById(R.id.forgot_button);
        //Set hint edit text
        editUserName.setHint(R.string.taikhoan);
        editUserPassword.setHint(R.string.password);
        editUserPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //Button click
        btnLogin.setOnClickListener(v -> {
            User user = new User(editUserName.getText().toString().trim(), editUserPassword.getText().toString().trim());
            APIClient.getAPILogin().postUser(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    if (response.code() == 401) {
                        Toast.makeText(getContext(), "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                        editUserName.setText("");
                        editUserPassword.setText("");
                        return;
                    }
                    MainActivity.user = response.body();
                    MainActivity.isLogin = true;
                    ((MainActivity) requireActivity()).loadFragment(new InfoFragment(MainActivity.user));
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            });
        });
        btnSignUp.setOnClickListener(v -> ((MainActivity) requireActivity()).loadFragment(new SignUpFragment()));
        btnForgot.setOnClickListener(v -> ((MainActivity) requireActivity()).loadFragment(new ForgotFragment()));
        return viewLogin;
    }
}
