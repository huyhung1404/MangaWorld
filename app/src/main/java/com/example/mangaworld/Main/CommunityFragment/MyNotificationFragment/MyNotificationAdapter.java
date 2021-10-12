package com.example.mangaworld.Main.CommunityFragment.MyNotificationFragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Main.CommunityFragment.CommunityFragment;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.Notification;
import com.example.mangaworld.Model.Message;
import com.example.mangaworld.R;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNotificationAdapter extends RecyclerView.Adapter<MyNotificationAdapter.NotificationViewHolder> {
    private final CommunityFragment.ISeenNotification m_ISeenNotification;
    private final ColorStateList SEEN_COLOR;
    private final ColorStateList UNSEEN_COLOR;
    private List<Notification> m_Notifications;
    private final Date dateNow = new Date();


    public MyNotificationAdapter(Context _context, CommunityFragment.ISeenNotification _iSeenNotification) {
        this.m_ISeenNotification = _iSeenNotification;
        SEEN_COLOR = ContextCompat.getColorStateList(_context, R.color.white);
        UNSEEN_COLOR = ContextCompat.getColorStateList(_context, R.color.notification_unseen);
    }

    public void SetData(List<Notification> _notifications) {
        this.m_Notifications = _notifications;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view, (v, position) -> {
            if (m_Notifications.get(position).isSeen()){
                return;
            }
            APIClient.getAPICommunity().seenNotification("Bearer " + MainActivity.user.getToken(),m_Notifications.get(position).getId()).enqueue(new Callback<Message>() {
                @Override
                public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                    if (response.isSuccessful()){
                        m_Notifications.get(position).setSeen(true);
                        notifyItemChanged(position);
                        m_ISeenNotification.seen();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {

                }
            });
        });
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = m_Notifications.get(position);
        if(notification == null){
            return;
        }
        Glide.with(holder.avatar.getContext())
                .load(notification.getAvatar())
                .into(holder.avatar);
        holder.content.setText(notification.getTitle());
        holder.time.setText(timeHandling(notification.getCreatedDate()));
        if(notification.isSeen()){
            holder.card.setCardBackgroundColor(SEEN_COLOR);
            return;
        }
        holder.card.setCardBackgroundColor(UNSEEN_COLOR);
    }

    @Override
    public int getItemCount() {
        return (m_Notifications == null) ? 0 : m_Notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CardView card;
        private final CircleImageView avatar;
        private final TextView content;
        private final TextView time;
        private final OnClickListenerRecyclerView onClickListener;

        public NotificationViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListener) {
            super(itemView);
            card = itemView.findViewById(R.id.card_view_notification);
            avatar = itemView.findViewById(R.id.avatar_notification);
            content = itemView.findViewById(R.id.text_notification);
            time = itemView.findViewById(R.id.text_time_notification);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
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
}