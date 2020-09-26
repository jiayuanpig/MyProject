package com.zjy.myblog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_blog")
/*博客类*/
public class Blog {
    @Id
    @GeneratedValue
    private Long id;

    private String title;           //标题
    private String content;         //内容
    private String firstPicture;    //首图
    private String flag;            //标记
    private String views;           //浏览次数
    private boolean appreciation;   //开启赞赏
    private boolean shareStatement; //开启版权
    private boolean commentabled;   //开启评论
    private boolean published;      //开启发布
    private boolean recommended;    //开启推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;        //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;        //更新时间

    /*在多对一的情况下，需要用多的那一方维护一的关系*/
    @ManyToOne
    private Type type;              //一篇博客对应一种类型

    /*在多对多的情况下，任选一方来维护关系，这里选Blog来维护*/
    /*设置级联新增：在博客设置标签时，自动将数据库没有的标签添加到数据库中*/
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>(); //一篇博客对应多个标签

    @ManyToOne
    private User user;              //一篇博客对应一个用户

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>(); //一个博客有多个评论


    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views='" + views + '\'' +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommended=" + recommended +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
