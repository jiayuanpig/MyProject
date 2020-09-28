package com.zjy.myblog.dao;

import com.zjy.myblog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type,Long> {
        Type findByName(String name);
}
