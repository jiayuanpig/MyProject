package com.zjy.myblog.dao;

import com.zjy.myblog.po.Blog;
import com.zjy.myblog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long>{
        Tag findByName(String name);

        @Query("select t from Tag t")
        List<Tag> findTop(Pageable pageable);
}
