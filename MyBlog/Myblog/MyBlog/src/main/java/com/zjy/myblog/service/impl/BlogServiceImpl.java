package com.zjy.myblog.service.impl;

import com.zjy.myblog.dao.BlogRepository;
import com.zjy.myblog.exception.NotFoundException;
import com.zjy.myblog.po.Blog;
import com.zjy.myblog.service.BlogService;
import com.zjy.myblog.util.MarkdownUtils;
import com.zjy.myblog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;


    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    /*根据条件查询对应的Blog，查询条件封装在Blog对象中*/
    @Override
    public Page<Blog> listBlog(Pageable pageable, Blog blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                //根据标题、分类、标签进行条件查询
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getType() != null && !"".equals(blog.getType()) && blog.getType().getId() != null && !"".equals(blog.getType().getId())) {
                    predicates.add(criteriaBuilder.equal(root.<String>get("type").get("id"), blog.getType().getId()));
                }
                if (blog.isRecommended()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommended"), blog.isRecommended()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogRepository.getOne(id);
        if (blog1 == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return blogRepository.save(blog1);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        if(blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog newBlog = new Blog();
        BeanUtils.copyProperties(blog,newBlog);
        String content = newBlog.getContent();
        newBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blogRepository.updateViews(id);

        return newBlog;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYears();
        Map<String, List<Blog>> map = new HashMap<>();
        for(String year:years){
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}
