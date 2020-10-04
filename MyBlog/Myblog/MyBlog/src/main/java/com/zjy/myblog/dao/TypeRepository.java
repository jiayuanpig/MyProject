package com.zjy.myblog.dao;

import com.zjy.myblog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
        Type findByName(String name);

        //将分类对应的博客数前N名的分类返回
        @Query("select t from Type t")
        List<Type> findTop(Pageable pageable);
}
