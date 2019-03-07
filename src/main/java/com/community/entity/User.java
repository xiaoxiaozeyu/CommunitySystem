package com.community.entity;

import java.io.Serializable;

/**
 * 实体类(entity)User 对应数据库中的 t_users表
 */
public class User implements Serializable {

    private Integer id;//对象标识符
    private String username;//用户名
    private String password;//密码
    private String imgpath;//头像路径

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
}
