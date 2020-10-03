package com.zjy.myblog.dao;

import com.zjy.myblog.po.Blog;
import com.zjy.myblog.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagRepository extends JpaRepository<Tag,Long>{
        Tag findByName(String name);
}
