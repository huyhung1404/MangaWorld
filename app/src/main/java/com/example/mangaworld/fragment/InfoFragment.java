package com.example.mangaworld.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.mangaworld.R;

public class InfoFragment extends Fragment {
    private View mView;
    private LinearLayout linearLayoutInfo,linearLayoutPassword;
    private Button buttonExpandInfo,buttonExpandPassword;
    private CardView cardViewInfo,cardViewPassword;
    private EditText editTextName,editTextPhone,editTextGmail;
    private ImageView buttonEdit;
    private boolean flagButton = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_info, container, false);
        initUI();
        expandInformation();
        buttonEdit.setOnClickListener(v -> flagButton = flagButtonClick(editTextName,editTextPhone,editTextGmail,flagButton));
        return mView;
    }
    private void initUI(){
        linearLayoutInfo = mView.findViewById(R.id.linear_layout_info_fragment);
        linearLayoutPassword = mView.findViewById(R.id.linear_layout_password_info_fragment);
        buttonExpandInfo = mView.findViewById(R.id.button_expand_info);
        buttonExpandPassword = mView.findViewById(R.id.button_expand_password);
        cardViewInfo = mView.findViewById(R.id.card_view_info);
        cardViewPassword = mView.findViewById(R.id.card_view_password);
        editTextName = mView.findViewById(R.id.edit_text_name_info);
        editTextPhone = mView.findViewById(R.id.edit_text_sdt_info);
        editTextGmail = mView.findViewById(R.id.edit_text_gmail_info);
        buttonEdit = mView.findViewById(R.id.button_edit_text_name);
    }
    private void expandInformation(){
        buttonExpandInfo.setOnClickListener(v -> {
            if (linearLayoutInfo.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(cardViewInfo, new AutoTransition());
                linearLayoutPassword.setVisibility(View.GONE);
                linearLayoutInfo.setVisibility(View.VISIBLE);
                buttonExpandInfo.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                buttonExpandPassword.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                return;
            }
            linearLayoutInfo.setVisibility(View.GONE);
            buttonExpandInfo.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
        });
        buttonExpandPassword.setOnClickListener(v -> {
            if (linearLayoutPassword.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(cardViewPassword, new AutoTransition());
                linearLayoutInfo.setVisibility(View.GONE);
                linearLayoutPassword.setVisibility(View.VISIBLE);
                buttonExpandInfo.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                buttonExpandPassword.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                return;
            }
            linearLayoutPassword.setVisibility(View.GONE);
            buttonExpandPassword.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
        });
    }

    private boolean flagButtonClick(EditText editText1,EditText editText2,EditText editText3,boolean flag){
        if (flag){
            editText1.setFocusableInTouchMode(false);
            editText2.setFocusableInTouchMode(false);
            editText3.setFocusableInTouchMode(false);
            editText1.setFocusable(false);
            editText2.setFocusable(false);
            editText3.setFocusable(false);
            Toast.makeText(getContext(),"Đã lưu", Toast.LENGTH_SHORT).show();
            return false;
        }
        editText1.setFocusableInTouchMode(true);
        editText2.setFocusableInTouchMode(true);
        editText3.setFocusableInTouchMode(true);
        editText1.setFocusable(true);
        editText2.setFocusable(true);
        editText3.setFocusable(true);
        Toast.makeText(getContext(),"Có thể chỉnh sửa", Toast.LENGTH_SHORT).show();
        return true;
    }
}