package com.example.mangaworld.Main.CommunityFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mangaworld.Interface.IReceiveNotification;
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.GroupsFragment;
import com.example.mangaworld.Main.CommunityFragment.MyNotificationFragment.FirebasePushNotification.MyFirebaseMessagingService;
import com.example.mangaworld.Main.CommunityFragment.MyNotificationFragment.MyNotificationFragment;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyProfileFragment;
import com.example.mangaworld.Main.CommunityFragment.NewsFragment.NewsFragment;
import com.example.mangaworld.Main.InfoUserFragment.AccountManager.LoginFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.R;

public class CommunityFragment extends Fragment {
    private static FragmentManager fragmentManager;
    private boolean isCreateNew = true;
    private TextView textNumber;
    private int num = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!MainActivity.isLogin) {
            return createRequiresLoginScreen(inflater, container);
        }
        return createCommunityFragment(inflater, container);
    }

    private View createRequiresLoginScreen(@NonNull LayoutInflater _inflater, ViewGroup _container) {
        View view = _inflater.inflate(R.layout.requires_login, _container, false);
        view.findViewById(R.id.relative_layout_requires_login).setBackgroundResource(R.drawable.background_login);
        view.findViewById(R.id.btn_next_login_fragment).setOnClickListener(v -> {
            ((MainActivity) requireActivity()).loadFragment(new LoginFragment());
            MainActivity.bottomNavigationView.getMenu().findItem(R.id.menuInfo).setChecked(true);
        });
        return view;
    }

    private View createCommunityFragment(@NonNull LayoutInflater _inflater, ViewGroup _container) {
        View view = _inflater.inflate(R.layout.fragment_community, _container, false);
        textNumber = view.findViewById(R.id.number_notification);
        registerReceiveNotification();
        fragmentManager = getChildFragmentManager();
        if (isCreateNew) {
            fragmentManager.beginTransaction().add(R.id.fragmentContainerView, new NewsFragment()).commit();
            isCreateNew = false;
        }
        initButton(view);
        settingNumberNotification(num);
        return view;
    }


    private void initButton(View _view) {
        _view.findViewById(R.id.button_news).setOnClickListener(v -> ChangeFragment(new NewsFragment()));
        _view.findViewById(R.id.button_groups).setOnClickListener(v -> ChangeFragment(new GroupsFragment()));
        _view.findViewById(R.id.button_bell).setOnClickListener(v -> ChangeFragment(new MyNotificationFragment()));
        _view.findViewById(R.id.button_mine).setOnClickListener(v -> ChangeFragment(new MyProfileFragment()));
    }

    private void settingNumberNotification(int _numberNotification) {
        if (_numberNotification == 0) {
            textNumber.setVisibility(View.GONE);
            return;
        }
        textNumber.setVisibility(View.VISIBLE);
        textNumber.setText(String.valueOf(_numberNotification));
    }

    private void ChangeFragment(Fragment _fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, _fragment);
        transaction.addToBackStack(null);
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.commit();
    }

    public static void GoToScreenInCommunity(Fragment _fragment, String _tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, _fragment);
        fragmentTransaction.addToBackStack(_tag);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterReceiveNotification();
    }

    private void registerReceiveNotification() {
        MyFirebaseMessagingService.iReceiveNotification = () -> requireActivity()
                .runOnUiThread(() -> settingNumberNotification(++num));
    }

    private void unregisterReceiveNotification() {
        MyFirebaseMessagingService.iReceiveNotification = null;
    }
}