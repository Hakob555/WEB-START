package com.smartcode.web.repository.cooment;

import com.smartcode.web.model.Comment;

import java.util.List;

public interface CommentRepository {
    void create(Comment comment);
    void update(Integer commentId,Comment comment);
    List<Comment> getAll(Integer userID);
    Comment getById(Integer commentId);
    void delete(Integer commentId);



}
