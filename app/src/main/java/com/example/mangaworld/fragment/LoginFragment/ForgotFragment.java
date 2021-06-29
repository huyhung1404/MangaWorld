package com.example.mangaworld.fragment.LoginFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mangaworld.R;
import com.example.mangaworld.main.MainActivity;

public class ForgotFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewForgot = inflater.inflate(R.layout.fragment_forgot_password,container,false);
        //Init
        TextView backLoginFragment = viewForgot.findViewById(R.id.btn_login_forgot);
        EditText editGmailForgot = viewForgot.findViewById(R.id.gmail_forgot);
        EditText editNewPassword = viewForgot.findViewById(R.id.new_password_forgot);
        EditText editRePassword = viewForgot.findViewById(R.id.re_new_password_forgot);
        //Set hint edit text
        editGmailForgot.setHint("Gmail đăng kí");
        editNewPassword.setHint("Mật khẩu mới");
        editRePassword.setHint("Nhập lại mật khẩu");
        //
        backLoginFragment.setOnClickListener(v -> ((MainActivity) requireActivity()).loadFragment(new LoginFragment()));
        return viewForgot;
    }
}
