package com.example.mangaworld.object;

public class Comment {
    private String resourceAvatar;
    private String nameComment;
    private String commentContent;

    public Comment(String resourceAvatar, String name, String commentContent) {
        this.resourceAvatar = resourceAvatar;
        this.nameComment = name;
        this.commentContent = commentContent;
    }

    public String getResourceAvatar() {
        return resourceAvatar;
    }

    public String getNameComment() {
        return nameComment;
    }

    public String getCommentContent() {
        return commentContent;
    }
}
