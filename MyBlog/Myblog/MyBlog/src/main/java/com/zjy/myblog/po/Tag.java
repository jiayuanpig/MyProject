package com.zjy.myblog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_tag")
/*标签类*/
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    private String name;    //标签名

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>(); //一个标签对应多个博客


    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
