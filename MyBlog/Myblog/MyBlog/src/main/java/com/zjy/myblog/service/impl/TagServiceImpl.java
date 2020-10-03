package com.zjy.myblog.service.impl;

import com.zjy.myblog.dao.TagRepository;
import com.zjy.myblog.exception.NotFoundException;
import com.zjy.myblog.po.Tag;
import com.zjy.myblog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository TagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag Tag) {
        return TagRepository.save(Tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return TagRepository.getOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return TagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return TagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id,Tag Tag) {
        Tag t = TagRepository.getOne(id);
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(Tag,t);
        return TagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        TagRepository.deleteById(id);
    }
}
