package com.zjy.myblog.service;

import com.zjy.myblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    /*Tag的增删改查*/

    Tag saveTag(Tag Tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id,Tag Tag);

    void deleteTag(Long id);


}
