package com.example.mangaworld.Main.CommunityFragment.MyProfileFragment.MyGroupsFragment.MyGroupCreated.GroupsManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mangaworld.API.APIClient;
import com.example.mangaworld.Interface.OnClickListenerRecyclerView;
import com.example.mangaworld.Main.MainActivity;
import com.example.mangaworld.Model.Community.UserForum;
import com.example.mangaworld.Model.Message;
import com.example.mangaworld.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private List<UserForum> userForums;
    private final long idGroup;
    private final Context context;

    public MemberAdapter(long idGroup, Context context) {
        this.idGroup = idGroup;
        this.context = context;
    }

    public void setData(List<UserForum> userForums) {
        this.userForums = userForums;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_manager, parent, false);
        return new MemberViewHolder(view, (v, position) -> {
            if (v.getId() == R.id.button_member_accept) {
                acceptButtonClick(userForums.get(position).getId(), position);
                return;
            }
            refuseButtonClick(userForums.get(position).getId(), position);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        if (userForums.get(position) == null) {
            return;
        }
        Glide.with(holder.avatar.getContext())
                .load(userForums.get(position).getLinkImage())
                .into(holder.avatar);
        holder.name.setText(userForums.get(position).getFullName());
    }

    @Override
    public int getItemCount() {
        return (userForums == null) ? 0 : userForums.size();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CircleImageView avatar;
        private final TextView name;
        private final OnClickListenerRecyclerView onClickListener;

        public MemberViewHolder(@NonNull View itemView, OnClickListenerRecyclerView onClickListener) {
            super(itemView);

            avatar = itemView.findViewById(R.id.avatar_member_manager);
            name = itemView.findViewById(R.id.name_member_manager);
            this.onClickListener = onClickListener;
            itemView.findViewById(R.id.button_member_accept).setOnClickListener(this);
            itemView.findViewById(R.id.button_member_refuse).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(v, getAdapterPosition());
        }
    }

    private void acceptButtonClick(long idUser, int position) {
        APIClient.getAPICommunity().acceptUserJoinGroup("Bearer " + MainActivity.user.getToken(), idGroup, idUser).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    userForums.remove(position);
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(context, "Gửi yêu cầu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refuseButtonClick(long idUser, int position) {
        APIClient.getAPICommunity().refuseUserJoinGroup("Bearer " + MainActivity.user.getToken(), idGroup, idUser).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    userForums.remove(position);
                    notifyItemRemoved(position);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                Toast.makeText(context, "Gửi yêu cầu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
