package com.example.mangaworld.fragment.readBookFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.mangaworld.R;
import com.example.mangaworld.fragment.readBookFragment.readBookAdapter.CommentAdapter;
import com.example.mangaworld.object.Comment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentFragment extends Fragment {
    private String idBook;


    public CommentFragment(String idBook){
        this.idBook = idBook;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_comment, container, false);
        RecyclerView rcvComment = mView.findViewById(R.id.rcv_comment);
        CircleImageView circleImageView = mView.findViewById(R.id.img_my_avatar);
        Glide.with(getContext()).load("https://scontent-hkg4-1.xx.fbcdn.net/v/t1.6435-9/179204046_1811297539029978_8785896082035732471_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=e3f864&_nc_ohc=QLDBnvQGal0AX_tU1ON&_nc_ht=scontent-hkg4-1.xx&oh=39960f558ef1cc4c4a7a0a0ada526ee7&oe=60D41CD7").into(circleImageView);
        CommentAdapter commentAdapter = new CommentAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvComment.setLayoutManager(linearLayoutManager);
        commentAdapter.setData(setDataComment());
        rcvComment.setAdapter(commentAdapter);
        return mView;
    }

    private List<Comment> setDataComment() {
        List<Comment> listComment = new ArrayList<>();
        listComment.add(new Comment("https://scontent-hkg4-1.xx.fbcdn.net/v/t1.6435-9/179204046_1811297539029978_8785896082035732471_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=e3f864&_nc_ohc=QLDBnvQGal0AX_tU1ON&_nc_ht=scontent-hkg4-1.xx&oh=39960f558ef1cc4c4a7a0a0ada526ee7&oe=60D41CD7","Hùng Bá", "BỘ truyện rất hay"));
        listComment.add(new Comment("https://scontent-hkg4-1.xx.fbcdn.net/v/t1.6435-9/179204046_1811297539029978_8785896082035732471_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=e3f864&_nc_ohc=QLDBnvQGal0AX_tU1ON&_nc_ht=scontent-hkg4-1.xx&oh=39960f558ef1cc4c4a7a0a0ada526ee7&oe=60D41CD7","Hùng Bá", "BỘ truyện rất hay BỘ truyện rất hay BỘ truyện rất hay BỘ truyện rất hay BỘ truyện rất hay vBỘ truyện rất hayvBỘ truyện rất hay BỘ truyện rất hayvBỘ truyện rất hay BỘ truyện rất hay BỘ truyện rất hayBỘ truyện rất hayBỘ truyện rất hay BỘ truyện rất hay BỘ truyện rất hayBỘ truyện rất hay"));
        listComment.add(new Comment("https://scontent-hkg4-1.xx.fbcdn.net/v/t1.6435-9/179204046_1811297539029978_8785896082035732471_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=e3f864&_nc_ohc=QLDBnvQGal0AX_tU1ON&_nc_ht=scontent-hkg4-1.xx&oh=39960f558ef1cc4c4a7a0a0ada526ee7&oe=60D41CD7","Hùng Bá", "BỘ truyện rất hay"));
        listComment.add(new Comment("https://scontent-hkg4-1.xx.fbcdn.net/v/t1.6435-9/179204046_1811297539029978_8785896082035732471_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=e3f864&_nc_ohc=QLDBnvQGal0AX_tU1ON&_nc_ht=scontent-hkg4-1.xx&oh=39960f558ef1cc4c4a7a0a0ada526ee7&oe=60D41CD7","Hùng Bá", "BỘ truyện rất hay"));
        listComment.add(new Comment("https://scontent-hkg4-1.xx.fbcdn.net/v/t1.6435-9/179204046_1811297539029978_8785896082035732471_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=e3f864&_nc_ohc=QLDBnvQGal0AX_tU1ON&_nc_ht=scontent-hkg4-1.xx&oh=39960f558ef1cc4c4a7a0a0ada526ee7&oe=60D41CD7","Hùng Bá", "BỘ truyện rất hay"));
        listComment.add(new Comment("https://scontent-hkg4-1.xx.fbcdn.net/v/t1.6435-9/179204046_1811297539029978_8785896082035732471_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=e3f864&_nc_ohc=QLDBnvQGal0AX_tU1ON&_nc_ht=scontent-hkg4-1.xx&oh=39960f558ef1cc4c4a7a0a0ada526ee7&oe=60D41CD7","Hùng Bá", "BỘ truyện rất hay"));
        listComment.add(new Comment("https://scontent-hkg4-1.xx.fbcdn.net/v/t1.6435-9/179204046_1811297539029978_8785896082035732471_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=e3f864&_nc_ohc=QLDBnvQGal0AX_tU1ON&_nc_ht=scontent-hkg4-1.xx&oh=39960f558ef1cc4c4a7a0a0ada526ee7&oe=60D41CD7","Hùng Bá", "BỘ truyện rất hay"));
        listComment.add(new Comment("https://scontent-hkg4-1.xx.fbcdn.net/v/t1.6435-9/179204046_1811297539029978_8785896082035732471_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=e3f864&_nc_ohc=QLDBnvQGal0AX_tU1ON&_nc_ht=scontent-hkg4-1.xx&oh=39960f558ef1cc4c4a7a0a0ada526ee7&oe=60D41CD7","Hùng Bá", "BỘ truyện rất hay"));
        listComment.add(new Comment("https://scontent-hkg4-1.xx.fbcdn.net/v/t1.6435-9/179204046_1811297539029978_8785896082035732471_n.jpg?_nc_cat=107&ccb=1-3&_nc_sid=e3f864&_nc_ohc=QLDBnvQGal0AX_tU1ON&_nc_ht=scontent-hkg4-1.xx&oh=39960f558ef1cc4c4a7a0a0ada526ee7&oe=60D41CD7","Hùng Bá", "BỘ truyện rất hay"));
        return listComment;
    }
}