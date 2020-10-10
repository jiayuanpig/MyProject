package com.zjy.myblog.service;

import com.zjy.myblog.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);


    Comment saveComment(Comment comment);
}
