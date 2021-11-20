package com.stark.searchengine.bean;

import java.util.Date;

public class News {

    private String title;
    private String tag;
    private Date publishTime;


    public News() {
        super();
    }
    public News(String title, String tag, Date publishTime) {
        super();
        this.title = title;
        this.tag = tag;
        this.publishTime = publishTime;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public Date getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}