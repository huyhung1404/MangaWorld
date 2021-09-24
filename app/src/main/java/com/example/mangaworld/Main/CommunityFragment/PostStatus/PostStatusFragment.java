package com.example.mangaworld.Main.CommunityFragment.PostStatus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.API.RealPathUtil;
import com.example.mangaworld.Interface.GoToSelectGroupsFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.ImageCall;
import com.example.mangaworld.Model.Community.ImageSend;
import com.example.mangaworld.Model.Manga;
import com.example.mangaworld.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostStatusFragment extends Fragment {
    public static final String TAG = PostStatusFragment.class.getName();
    private final GoToSelectGroupsFragment goToSelectGroupsFragment;
    private TextView buttonAddImage;
    private ImageView imageAttach;
    private TextView buttonDeleteImgAttach;
    private long idManga = -1;
    private EditText contentEditText;
    private FrameLayout loadingLayout;
    private Uri imageUri = null;

    public PostStatusFragment(GoToSelectGroupsFragment goToSelectGroupsFragment) {
        this.goToSelectGroupsFragment = goToSelectGroupsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popup_port_status, container, false);
        view.findViewById(R.id.hide_key_board).setOnClickListener(this::hideKeyBoard);
        MainActivity.hideBottomNav();
        loadingLayout = view.findViewById(R.id.loading_port_status);
        contentEditText = view.findViewById(R.id.edit_text_port);
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            idManga = bundleReceive.getLong("idManga");
            attachMangaHandle(view.findViewById(R.id.manga_port));
        }
        buttonImage(view);
        initButton(view);
        return view;
    }

    private void initButton(View _view) {
        _view.findViewById(R.id.button_close_port)
                .setOnClickListener(v -> {
                    requireFragmentManager()
                            .popBackStack(PostStatusFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    MainActivity.showBottomNav();
                });

        _view.findViewById(R.id.button_select_group).setOnClickListener(v -> sendDataToSelectGroup());
    }

    private void buttonImage(View view) {
        buttonAddImage = view.findViewById(R.id.button_add_image);
        imageAttach = view.findViewById(R.id.image_attach_port_status);
        buttonDeleteImgAttach = view.findViewById(R.id.button_delete_image_attach);
        buttonAddImage.setOnClickListener(v -> ((MainActivity) requireActivity()).onClickRequestPermission(this::addImage));
        buttonDeleteImgAttach.setOnClickListener(v -> {
            buttonAddImage.setVisibility(View.VISIBLE);
            imageAttach.setVisibility(View.GONE);
            buttonDeleteImgAttach.setVisibility(View.GONE);
            imageUri = null;
        });
    }

    private void attachMangaHandle(View mangaView) {
        APIClient.getAPIChapter().getMangaById(idManga).enqueue(new Callback<Manga>() {
            @Override
            public void onResponse(@NonNull Call<Manga> call, @NonNull Response<Manga> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Manga manga = response.body();
                    ImageView imgBook = mangaView.findViewById(R.id.img_item_category);

                    Glide.with(imgBook.getContext()).load(manga.getResourceId()).into(imgBook);

                    ((TextView) mangaView.findViewById(R.id.tv_like_item_category))
                            .setText(String.valueOf(manga.getLikeManga()));
                    ((TextView) mangaView.findViewById(R.id.tv_view_item_category))
                            .setText(String.valueOf(manga.getViewManga()));
                    ((TextView) mangaView.findViewById(R.id.tv_name_book_category))
                            .setText(manga.getNameManga());
                    ((TextView) mangaView.findViewById(R.id.tv_summary_category))
                            .setText(manga.getSummaryManga());
                    ((TextView) mangaView.findViewById(R.id.tv_author_category))
                            .setText(manga.getMangaAuthor());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Manga> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Không thể đính kèm được truyện", Toast.LENGTH_SHORT).show();
            }
        });
        mangaView.setVisibility(View.VISIBLE);
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
                    buttonAddImage.setVisibility(View.GONE);
                    imageAttach.setVisibility(View.VISIBLE);
                    buttonDeleteImgAttach.setVisibility(View.VISIBLE);
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

    private void sendDataToSelectGroup() {
        if (contentEditText.length() == 0) {
            Toast.makeText(requireContext(), "Nội dung không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingLayout.setVisibility(View.VISIBLE);
        Bundle bundle = new Bundle();
        bundle.putLong("idManga", idManga);
        bundle.putString("content", contentEditText.getText().toString());
        if (imageUri == null) {
            bundle.putLong("idImage", -1);
            loadingLayout.setVisibility(View.GONE);
            goToSelectGroupsFragment.goToSelectGroup(bundle);
            return;
        }
        String realPath = RealPathUtil.getRealPath(requireContext(), imageUri);
        if (realPath == null) {
            try {
                uploadImageByBase64(bundle, MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri));
            } catch (IOException e) {
                Toast.makeText(requireContext(), "Không thế lấy được đường dẫn ảnh", Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
            }
            return;
        }
        File file = new File(realPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        APIClient.getAPICommunity().postImageStatus("Bearer " + MainActivity.user.getToken(), multipartBody).enqueue(new Callback<ImageCall>() {
            @Override
            public void onResponse(@NonNull Call<ImageCall> call, @NonNull Response<ImageCall> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    bundle.putLong("idImage", response.body().getIds().get(0));
                    loadingLayout.setVisibility(View.GONE);
                    goToSelectGroupsFragment.goToSelectGroup(bundle);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageCall> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Đính kèm ảnh không thành công", Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
            }
        });


    }

    private void uploadImageByBase64(Bundle bundle, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        List<ImageSend> imageSends = new ArrayList<>();
        imageSends.add(new ImageSend(MainActivity.user.getUserName(), Base64.encodeToString(byteArray, Base64.DEFAULT)));

        APIClient.getAPICommunity().postImageStatusBase64("Bearer " + MainActivity.user.getToken(), imageSends).enqueue(new Callback<ImageCall>() {
            @Override
            public void onResponse(@NonNull Call<ImageCall> call, @NonNull Response<ImageCall> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    bundle.putLong("idImage", response.body().getIds().get(0));
                    loadingLayout.setVisibility(View.GONE);
                    goToSelectGroupsFragment.goToSelectGroup(bundle);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageCall> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Đính kèm ảnh không thành công", Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    private void hideKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
