package com.wzx.service;

import com.wzx.po.Comment;

import java.util.List;

public interface CommentService {
    void save(Comment comment);

    List<Comment> findCommentByNewsId(Long newsId);
}
