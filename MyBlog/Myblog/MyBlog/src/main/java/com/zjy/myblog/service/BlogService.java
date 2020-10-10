package com.zjy.myblog.service;

import com.zjy.myblog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Pageable pageable, Blog blog);

    Page<Blog> listBlog(String query, Pageable pageable);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    List<Blog> listRecommendBlogTop(Integer size);

    /*自动将md文本内容转换为html内容*/
    Blog getAndConvert(Long id);

    /*获取博客所有年份*/
    /*获取对应年份的所有博客*/
    Map<String, List<Blog>> archiveBlog();

    /*获取博客总数*/
    Long countBlog();

}
