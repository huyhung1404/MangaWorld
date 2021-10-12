package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.API.RealPathUtil;
import com.example.mangaworld.Interface.CallbackData;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.InformationGroup.InformationGroupFragment;
import com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager.PopupManager.PopUpSelectCategoryGroup;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CreateGroup;
import com.example.mangaworld.Model.Community.Groups;
import com.example.mangaworld.Model.Community.ImageCall;
import com.example.mangaworld.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewGroupFragment extends Fragment implements CallbackData.SelectCategoryCreate{
    public static final String TAG = CreateNewGroupFragment.class.getName();
    private RadioGroup radioGroup;
    private ImageView imageAttach;
    private FrameLayout loadingLayout;
    private Uri imageUri = null;
    private Long idCategory = null;
    private EditText textName;
    private EditText textIntro;
    private TextView textCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_group, container, false);
        radioGroup = view.findViewById(R.id.radio_group_create);
        loadingLayout = view.findViewById(R.id.loading_create_group);
        textName = view.findViewById(R.id.edit_text_name_create);
        textName.setTextColor(Color.BLACK);
        textIntro = view.findViewById(R.id.edit_text_intro_create_group);
        textIntro.setTextColor(Color.BLACK);
        textCategory = view.findViewById(R.id.text_category_create_group);
        textCategory.setOnClickListener(v -> new PopUpSelectCategoryGroup(this)
                .show(requireActivity().getSupportFragmentManager(),PopUpSelectCategoryGroup.TAG));
        view.findViewById(R.id.button_create_group).setOnClickListener(v -> createGroup());
        imageAttach = view.findViewById(R.id.avatar_group_create_fragment);
        imageAttach.setOnClickListener(v -> ((MainActivity) requireActivity()).onClickRequestPermission(this::addImage));
        initToolBar(view);
        return view;
    }

    private void initToolBar(View _view) {
        Toolbar toolbar = _view.findViewById(R.id.create_group_fragment_toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("Tạo nhóm");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    private void addImage() {
        Intent image = new Intent();
        image.setType("image/*");
        image.setAction(Intent.ACTION_GET_CONTENT);
        requireActivity().startActivityForResult(Intent.createChooser(image, "Chọn ảnh"), 1);
        ((MainActivity) requireActivity()).setDataReceivedListener((requestCode, resultCode, data) -> {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                try {
                    imageUri = data.getData();
                    imageAttach.setImageBitmap(MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addImage();
            }
        }
    }

    private void createGroup() {
        if (textName.length() == 0) {
            Toast.makeText(requireContext(), "Tên nhóm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        if (textIntro.length() == 0) {
            Toast.makeText(requireContext(), "Giới thiệu nhóm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri == null) {
            Toast.makeText(requireContext(), "Ảnh đại diện nhóm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        String realPath = RealPathUtil.getRealPath(requireContext(), imageUri);
        if (realPath == null) {
            Toast.makeText(requireContext(), "Không thế lấy được đường dẫn ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingLayout.setVisibility(View.VISIBLE);
        File file = new File(realPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        APIClient.getAPICommunity().postImageStatus("Bearer " + MainActivity.user.getToken(), multipartBody).enqueue(new Callback<ImageCall>() {
            @Override
            public void onResponse(@NonNull Call<ImageCall> call, @NonNull Response<ImageCall> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    sendAPICreate(response.body().getIds().get(0));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageCall> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Đính kèm ảnh không thành công", Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    private void sendAPICreate(long idImage) {
        CreateGroup group = new CreateGroup(
                textName.getText().toString(),
                idCategory,
                textIntro.getText().toString(),
                idImage,
                (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_public) ? 1 : null);

        APIClient.getAPICommunity().createGroup("Bearer " + MainActivity.user.getToken(),group).enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(@NonNull Call<Groups> call, @NonNull Response<Groups> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    CommunityFragment.GoToScreenInCommunity(new InformationGroupFragment(response.body().getId()),
                            InformationGroupFragment.TAG);
                    loadingLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Groups> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Tạo nhóm thất bại", Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
            }
        });
        textName.setText("");
        textIntro.setText("");
        radioGroup.check(R.id.radio_button_public);
        imageUri = null;
        imageAttach.setImageResource(R.drawable.icon_camera);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        requireFragmentManager().popBackStack(CreateNewGroupFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    @Override
    public void setData(long id, String name) {
        idCategory = id;
        textCategory.setText(name);
    }
}