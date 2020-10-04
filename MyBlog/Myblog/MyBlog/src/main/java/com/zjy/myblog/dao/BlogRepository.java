package com.zjy.myblog.dao;

import com.zjy.myblog.po.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//JpaSpecificationExecutor可以实现复杂的条件查询
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommended = true")
    List<Blog> findTop(Pageable pageable);
}

