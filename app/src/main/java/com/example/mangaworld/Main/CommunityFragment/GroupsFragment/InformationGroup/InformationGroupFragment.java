package com.example.mangaworld.Main.CommunityFragment.GroupsFragment.InformationGroup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Interface.ChangePageNews;
import com.example.mangaworld.Main.CommunityFragment.NewsFragment.StatusAdapter;
import com.example.mangaworld.Main.CommunityFragment.PostStatus.SuccessMessageFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.Model.Community.News;
import com.example.mangaworld.Model.Community.Status;
import com.example.mangaworld.Model.Message;
import com.example.mangaworld.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InformationGroupFragment extends Fragment implements ChangePageNews {
    public static final String TAG = InformationGroupFragment.class.getName();

    private long page = 1;
    private final long SIZE = 6;
    private StatusAdapter statusAdapter;
    private LinearLayoutManager linearLayoutManager;

    private final long idGroup;
    private Groups group;
    private TextView buttonJoin;
    private CardView requestToJoin;

    public InformationGroupFragment(long idGroup) {
        this.idGroup = idGroup;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information_group, container, false);
        APIClient.getAPICommunity().getGroupByID("Bearer " + MainActivity.user.getToken(), idGroup).enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(@NonNull Call<Groups> call, @NonNull Response<Groups> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    group = response.body();
                    setDataInformationGroup(view);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Groups> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Lấy thông tin nhóm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void setDataInformationGroup(View view) {
        initToolBar(view, group.getName());
        buttonJoin = view.findViewById(R.id.button_join_information_group);
        requestToJoin = view.findViewById(R.id.card_view_request_to_join);
        if (!group.isInvitation()) {
            handlingRequestToJoin(group.isInvitationSent());
            buttonJoin.setOnClickListener(v -> onClickButtonJoin());
        }else {
            requestToJoin.setVisibility(View.GONE);
        }
        if (group.isPublicGroup() || group.isInvitation()) {
            APIClient.getAPICommunity().getPostInGroup("Bearer " + MainActivity.user.getToken(), idGroup, page, SIZE).enqueue(new Callback<News>() {
                @Override
                public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        getData(view, response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            view.findViewById(R.id.text_private_group).setVisibility(View.VISIBLE);
        }
    }

    private void initToolBar(View _view, String nameGroup) {
        Toolbar toolbar = _view.findViewById(R.id.information_group_toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(nameGroup);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(InformationGroupFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_information_group, menu);
        MenuItem setting = menu.findItem(R.id.information_group);
        setting.setOnMenuItemClickListener(item -> {
            new InfoPopupFragment(group).show(requireActivity().getSupportFragmentManager(), InfoPopupFragment.TAG);
            return true;
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    private void setClickPopupMenu(PopupMenu popupMenu,Status status) {
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_admin_group_delete:
                    deletePostByAdmin(status.getId());
                    return true;
                case R.id.item_1:
//                    Log.e("tag", "item1" + position);
                    return true;
                case R.id.item_2:
//                    Log.e("tag", "item2" + position);
                    return true;
                default:
                    return false;
            }
        });
    }

    private void getData(View view, News news) {
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_information_group);
        linearLayoutManager =
                new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemViewCacheSize((int) SIZE);
        if(group.getAdmin().getId() == MainActivity.user.getId()){
            statusAdapter = new StatusAdapter((MainActivity) requireActivity(), this::setClickPopupMenu, this,R.menu.menu_admin_group);
        }else {
            statusAdapter = new StatusAdapter((MainActivity) requireActivity(), this::setClickPopupMenu, this,R.menu.menu_status);
        }
        statusAdapter.setData(news);
        recyclerView.setAdapter(statusAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void handlingRequestToJoin(boolean isInvitationSend) {
        if (isInvitationSend) {
            buttonJoin.setText("Đã gửi");
            buttonJoin.setTextColor(Color.parseColor("#5D5E61"));
            buttonJoin.setBackgroundResource(R.drawable.border_comment_text);
            requestToJoin.setVisibility(View.VISIBLE);
            return;
        }
        buttonJoin.setText("Tham gia");
        buttonJoin.setTextColor(Color.parseColor("#5167E6"));
        buttonJoin.setBackgroundResource(R.drawable.border_text_view_group);
        requestToJoin.setVisibility(View.VISIBLE);
    }

    private void onClickButtonJoin(){
        if(group.isInvitationSent()){
            Toast.makeText(getContext(), "Đã gửi yêu cầu", Toast.LENGTH_SHORT).show();
            return;
        }
        APIClient.getAPICommunity().sendJoinGroupRequest("Bearer " + MainActivity.user.getToken(),idGroup).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    group.setInvitationSent(true);
                    handlingRequestToJoin(group.isInvitationSent());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Gửi yêu cầu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changePage(long page) {
        APIClient.getAPICommunity().getPostInGroup("Bearer " + MainActivity.user.getToken(), idGroup, page, SIZE).enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    statusAdapter.setData(response.body());
                    linearLayoutManager.scrollToPosition(0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void nextPage() {
        changePage(++page);
    }

    @Override
    public void lastPage() {
        changePage(--page);
    }

    @Override
    public void goPage(long page, EditText view) {
        this.page = page;
        changePage(page);
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void deletePostByAdmin(long idPost){
        APIClient.getAPICommunity().deletePost("Bearer " + MainActivity.user.getToken(),idPost).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if(response.isSuccessful()){
                    changePage(page);
                    new SuccessMessageFragment().show(requireActivity().getSupportFragmentManager(), SuccessMessageFragment.TAG);
                    MainActivity.showBottomNav();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}