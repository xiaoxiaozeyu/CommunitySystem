package com.community.entity;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private Integer id;
    private String message;
    private Date mdate;
    private String messageperson;
    private Integer articleid;
    private String imgpath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMdate() {
        return mdate;
    }

    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    public String getMessageperson() {
        return messageperson;
    }

    public void setMessageperson(String messageperson) {
        this.messageperson = messageperson;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
}
