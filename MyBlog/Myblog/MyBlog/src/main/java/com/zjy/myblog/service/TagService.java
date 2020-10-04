package com.zjy.myblog.service;

import com.zjy.myblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    /*Tag的增删改查*/

    Tag saveTag(Tag Tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);
    /*获取所有标签*/
    List<Tag> listTag();
    /*根据一组ID(String类型，通过逗号隔开)获取一组标签*/
    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);

    Tag updateTag(Long id,Tag Tag);

    void deleteTag(Long id);


}
