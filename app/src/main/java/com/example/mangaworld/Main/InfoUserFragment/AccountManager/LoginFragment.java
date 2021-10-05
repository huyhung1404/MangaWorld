package com.example.mangaworld.Main.InfoUserFragment.AccountManager;

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

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Main.InfoUserFragment.InfoUserFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.User;
import com.example.mangaworld.R;
import com.google.firebase.messaging.FirebaseMessaging;

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
            if (!checkExceptionText(editUserName, editUserPassword)) return;
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(getContext(), "Không thể lấy được token", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User(editUserName.getText().toString().trim(), editUserPassword.getText().toString().trim(), task.getResult());
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
                        ((MainActivity) requireActivity()).loadFragment(new InfoUserFragment());
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
        btnSignUp.setOnClickListener(v -> ((MainActivity) requireActivity()).loadFragment(new SignUpFragment()));
        btnForgot.setOnClickListener(v -> ((MainActivity) requireActivity()).loadFragment(new ForgotFragment()));
        return viewLogin;
    }

    private boolean checkExceptionText(EditText editText, EditText editTextPassword) {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Tài khoản không thể để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextPassword.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Mật khẩu không thể để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
