package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Interface.IShowTextNone;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Main.HomeFragment.CategoryFragment.MangaInCategoryAdapter;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.Status;
import com.example.mangaworld.Model.Manga;
import com.example.mangaworld.Model.Message;
import com.example.mangaworld.R;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Status> statuses;
    private final long idGroup;
    private final Context context;
    private final Date dateNow = new Date();
    private IShowTextNone m_Show;

    public PostAdapter(long idGroup, Context context, IShowTextNone _show) {
        this.idGroup = idGroup;
        this.context = context;
        m_Show = _show;
    }

    public void setData(List<Status> statuses) {
        this.statuses = statuses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status_post_manager, parent, false);
        return new PostViewHolder(view, (v, position) -> {
            if (v.getId() == R.id.button_post_accept) {
                acceptButtonClick(statuses.get(position).getId(),position);
                return;
            }
            refuseButtonClick(statuses.get(position).getId(),position);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if (statuses.get(position) == null) {
            return;
        }
        Glide.with(holder.avatarGroup.getContext())
                .load(statuses.get(position).getGroupDTOS().get(0).getAvatarGroup())
                .into(holder.avatarGroup);

        holder.creatorName.setText(statuses.get(position).getCreatorName());
        holder.timeCreator.setText(timeHandling(statuses.get(position).getCreatedDate()));
        holder.content.setText(statuses.get(position).getContent());

        if (statuses.get(position).getMedia() != null) {
            if (statuses.get(position).getMedia().size() != 0) {
                Glide.with(holder.imageAttach.getContext())
                        .load(statuses.get(position).getMedia().get(0).getUrl())
                        .into(holder.imageAttach);
                holder.imageAttach.setVisibility(View.VISIBLE);
            }
        } else {
            holder.imageAttach.setVisibility(View.GONE);
        }

        if (statuses.get(position).getComicDto() != null) {
            handleMangaAttach(statuses.get(position).getComicDto(), holder.mangaAttach);
            holder.mangaAttach.setVisibility(View.VISIBLE);
            return;
        }
        holder.mangaAttach.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return (statuses == null) ? 0 : statuses.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView creatorName;
        private final TextView timeCreator;
        private final TextView content;
        private final ImageView imageAttach;
        private final CircleImageView avatarGroup;
        private final View mangaAttach;

        private final OnClickListenerRecyclerView onClickListener;

        public PostViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListener) {
            super(itemView);
            creatorName = itemView.findViewById(R.id.name_post_manager_item);
            timeCreator = itemView.findViewById(R.id.time_post_manager_item);
            content = itemView.findViewById(R.id.content_post_manager_item);
            imageAttach = itemView.findViewById(R.id.image_content_post_manager_item);
            mangaAttach = itemView.findViewById(R.id.book_post_manager_item);
            avatarGroup = itemView.findViewById(R.id.avatar_post_manager_item);
            this.onClickListener = onClickListener;
            itemView.findViewById(R.id.button_post_accept).setOnClickListener(this);
            itemView.findViewById(R.id.button_post_refuse).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v, getAdapterPosition());
        }
    }

    private String timeHandling(Date createDate) {
        long diffInSeconds = (dateNow.getTime() - createDate.getTime()) / 1000;
        if (diffInSeconds <= 60) {
            return "Vài giây trước";
        } else if (diffInSeconds < 3600) {
            return diffInSeconds / 60 + " phút trước";
        } else if (diffInSeconds < 86400) {
            return diffInSeconds / 3600 + " giờ trước";
        }
        return diffInSeconds / 86400 + " ngày trước";
    }

    private void handleMangaAttach(Manga manga, View view) {
        MangaInCategoryAdapter.MangaInCategoryViewHolder holder =
                new MangaInCategoryAdapter.MangaInCategoryViewHolder(view, (v, position) -> {
                });
        Glide.with(holder.getImgBook().getContext()).load(manga.getResourceId()).into(holder.getImgBook());
        holder.getTextLikeBook().setText(String.valueOf(manga.getLikeManga()));
        holder.getTextViewBook().setText(String.valueOf(manga.getViewManga()));
        holder.getTextNameBook().setText(manga.getNameManga());
        holder.getTextSummaryBook().setText(manga.getSummaryManga());
        holder.getTextAuthorBook().setText(manga.getMangaAuthor());
    }

    private void acceptButtonClick(long idPost,int position) {
        APIClient.getAPICommunity().acceptPostInGroup("Bearer " + MainActivity.user.getToken(),idGroup,idPost).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    statuses.remove(position);
                    notifyItemRemoved(position);
                    if (statuses.size() == 0)
                        m_Show.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(context, "Gửi yêu cầu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refuseButtonClick(long idPost,int position) {
        APIClient.getAPICommunity().deletePost("Bearer " + MainActivity.user.getToken(),idPost).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    statuses.remove(position);
                    notifyItemRemoved(position);
                    if (statuses.size() == 0)
                        m_Show.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(context, "Gửi yêu cầu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
