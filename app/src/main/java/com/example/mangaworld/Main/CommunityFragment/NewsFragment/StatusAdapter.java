package com.example.mangaworld.Main.CommunityFragment.NewsFragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Interface.ChangePageNews;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Interface.OnClickMenuPopUpStatus;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.CommunityFragment.GroupsFragment.InformationGroup.InformationGroupFragment;
import com.example.mangaworld.Main.CommunityFragment.NewsFragment.CommentStatusFragment.CommentStatusFragment;
import com.example.mangaworld.Main.CommunityFragment.PaginationViewHolder;
import com.example.mangaworld.Main.HomeFragment.CategoryFragment.MangaInCategoryAdapter;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.CallBackItems;
import com.example.mangaworld.Model.Community.Status;
import com.example.mangaworld.Model.Manga;
import com.example.mangaworld.Model.Message;
import com.example.mangaworld.R;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int BLUE = Color.parseColor("#064673");
    private final int BLACK = Color.parseColor("#373234");
    private final MainActivity mainActivity;
    private final OnClickMenuPopUpStatus onClickMenuPopUpStatus;
    private final ChangePageNews changePageNews;
    private final int MENU_LAYOUT;
    private CallBackItems<Status> news;
    private int size;
    private final Date dateNow = new Date();


    public StatusAdapter(MainActivity _mainActivity, OnClickMenuPopUpStatus _onClickMenuPopUpStatus, ChangePageNews _changePageNews, int menuLayout) {
        mainActivity = _mainActivity;
        onClickMenuPopUpStatus = _onClickMenuPopUpStatus;
        changePageNews = _changePageNews;
        MENU_LAYOUT = menuLayout;
    }

    public void setData(CallBackItems<Status> _news) {
        news = _news;
        size = (_news.getItems() == null) ? 0 : _news.getItems().size();
        notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewType == R.layout.item_community_status) {
            return new CommunityStatusViewHolder(view, (v, position) -> {
                switch (v.getId()) {
                    case R.id.icon_menu_status:
                        PopupMenu popupMenu = new PopupMenu(mainActivity, v);
                        popupMenu.inflate(MENU_LAYOUT);
                        onClickMenuPopUpStatus.OnClick(popupMenu,news.getItems().get(position));
                        popupMenu.show();
                        break;
                    case R.id.comment_status:
                        new CommentStatusFragment(news.getItems().get(position).getMongoId())
                                .show(mainActivity.getSupportFragmentManager(), CommentStatusFragment.TAG);
                        break;
                    case R.id.name_status_community:
                        CommunityFragment.GoToScreenInCommunity(new InformationGroupFragment(
                                        news.getItems().get(position).getGroupDTOS().get(0).getId()),
                                InformationGroupFragment.TAG);
                        break;
                    default:
                        onClickButtonLike((TextView) v, position);
                }
            });
        }
        return new PaginationViewHolder(view, ((v, position) -> {
            EditText text = view.findViewById(R.id.inputPageStatus);
            switch (v.getId()) {
                case R.id.nextPagerStatus:
                    changePageNews.nextPage();
                    break;
                case R.id.lastPagerStatus:
                    changePageNews.lastPage();
                    break;
                default:
                    changePageNews.goPage(Long.parseLong(text.getText().toString()), text);
                    text.setText("");
            }
        }));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == size) {
            PaginationViewHolder paginationHolder = (PaginationViewHolder) holder;
            long lastPage = (long) Math.ceil((double) news.getTotal() / news.getSize());
            paginationHolder.textPaginationPager.setText(String.format("%d/%d", news.getPage(), lastPage));
            if (news.getPage() == 1) {
                paginationHolder.lastPagerButton.setVisibility(View.GONE);
            } else {
                paginationHolder.lastPagerButton.setVisibility(View.VISIBLE);
            }

            if (news.getPage() == lastPage) {
                paginationHolder.nextPagerButton.setVisibility(View.GONE);
            } else {
                paginationHolder.nextPagerButton.setVisibility(View.VISIBLE);
            }
            return;
        }
        CommunityStatusViewHolder statusHolder = (CommunityStatusViewHolder) holder;
        Status status = news.getItems().get(position);

        Glide.with(statusHolder.avatarGroup.getContext())
                .load(status.getGroupDTOS().get(0).getAvatarGroup())
                .into(statusHolder.avatarGroup);

        statusHolder.nameGroup.setText(status.getGroupDTOS().get(0).getName());
        statusHolder.creatorName.setText(status.getCreatorName());
        statusHolder.timeCreator.setText(timeHandling(status.getCreatedDate()));
        statusHolder.content.setText(status.getContent());

        if (status.getMedia() != null) {
            Glide.with(statusHolder.imageAttach.getContext())
                    .load(status.getMedia().get(0).getUrl())
                    .into(statusHolder.imageAttach);
            statusHolder.imageAttach.setVisibility(View.VISIBLE);
        } else {
            statusHolder.imageAttach.setVisibility(View.GONE);
        }

        statusHolder.likes.setText(String.format("%d lượt thích", status.getLikes()));

        if (status.isMyLike()) {
            setColorAndDrawable(statusHolder.likeButton, BLUE, R.drawable.icon_like_true);
        } else {
            setColorAndDrawable(statusHolder.likeButton, BLACK, R.drawable.icon_like);
        }

        statusHolder.comments.setText(String.format("%d bình luận", status.getComments()));

        if (status.getComicDto() != null) {
            handleMangaAttach(status.getComicDto(), statusHolder.mangaAttach);
            statusHolder.mangaAttach.setVisibility(View.VISIBLE);
            return;
        }
        statusHolder.mangaAttach.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return (size == 0) ? 0 : size + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == size) ? R.layout.item_paging_pager : R.layout.item_community_status;
    }

    public static class CommunityStatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nameGroup;
        private final TextView creatorName;
        private final TextView timeCreator;
        private final TextView content;
        private final TextView likes;
        private final TextView comments;
        private final ImageView imageAttach;
        private final CircleImageView avatarGroup;
        private final View mangaAttach;
        private final TextView likeButton;

        private final OnClickListenerRecyclerView onClickListener;

        public CommunityStatusViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListener) {
            super(itemView);
            nameGroup = itemView.findViewById(R.id.name_status_community);
            creatorName = itemView.findViewById(R.id.name_sec_status_community);
            timeCreator = itemView.findViewById(R.id.time_status_community);
            content = itemView.findViewById(R.id.content_status_community);
            imageAttach = itemView.findViewById(R.id.image_content_status_community);
            likes = itemView.findViewById(R.id.quantity_like);
            comments = itemView.findViewById(R.id.quantity_comment);
            mangaAttach = itemView.findViewById(R.id.book_status_community);
            avatarGroup = itemView.findViewById(R.id.avatar_status_community);
            likeButton = itemView.findViewById(R.id.like_status);
            this.onClickListener = onClickListener;
            nameGroup.setOnClickListener(this);
            itemView.findViewById(R.id.icon_menu_status).setOnClickListener(this);
            itemView.findViewById(R.id.comment_status).setOnClickListener(this);
            likeButton.setOnClickListener(this);
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
                new MangaInCategoryAdapter.MangaInCategoryViewHolder(view, (v, position) -> mainActivity
                        .goToInformationMangaScreen(manga.getIdManga()));
        Glide.with(holder.getImgBook().getContext()).load(manga.getResourceId()).into(holder.getImgBook());
        holder.getTextLikeBook().setText(String.valueOf(manga.getLikeManga()));
        holder.getTextViewBook().setText(String.valueOf(manga.getViewManga()));
        holder.getTextNameBook().setText(manga.getNameManga());
        holder.getTextSummaryBook().setText(manga.getSummaryManga());
        holder.getTextAuthorBook().setText(manga.getMangaAuthor());
    }

    private void onClickButtonLike(TextView view, int position) {
        if (news.getItems().get(position).isMyLike()) {
            sendLike(view, position, 0);
            return;
        }
        sendLike(view, position, 1);
    }

    private void setColorAndDrawable(TextView view, int color, int drawable) {
        view.setTextColor(color);
        view.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
    }

    private void sendLike(TextView view, int position, int like) {
        APIClient.getAPICommunity().likePost("Bearer " + MainActivity.user.getToken(),
                news.getItems().get(position).getId(), like).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    if (like == 0) {
                        news.getItems().get(position).setMyLike(false);
                        long likes = news.getItems().get(position).getLikes();
                        news.getItems().get(position).setLikes(--likes);
                        setColorAndDrawable(view, BLACK, R.drawable.icon_like);
                        notifyDataSetChanged();
                    } else if (like == 1) {
                        news.getItems().get(position).setMyLike(true);
                        long likes = news.getItems().get(position).getLikes();
                        news.getItems().get(position).setLikes(++likes);
                        setColorAndDrawable(view, BLUE, R.drawable.icon_like_true);
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {

            }
        });
    }
}