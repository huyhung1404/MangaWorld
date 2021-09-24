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

import com.example.mangaworld.R;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Model.User;

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
        if(!checkExceptionInput()) return;
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
    private boolean checkExceptionInput(){
        if (editGmail.getText().toString().isEmpty()){
            Toast.makeText(requireContext(),"Gmail không được để trống",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!editGmail.getText().toString().trim().contains("@gmail.com")){
            Toast.makeText(requireContext(),"Gmail không hợp lệ",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editName.getText().toString().isEmpty()){
            Toast.makeText(requireContext(),"Tên đăng nhập không được để trống",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editUserName.getText().toString().length()<6){
            Toast.makeText(requireContext(),"Tài khoản không được ít hơn 6 ký tự",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editPassword.getText().toString().length()<6){
            Toast.makeText(requireContext(),"Mật khẩu không được ít hơn 6 kí tự",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!editPassword.getText().toString().trim()
                .equals(editPasswordConfirm.getText().toString().trim())) {
            Toast.makeText(requireContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
            editPassword.setText("");
            editPasswordConfirm.setText("");
            return false;
        }
        return true;
    }
}
