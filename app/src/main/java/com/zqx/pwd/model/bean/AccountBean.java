package com.zqx.pwd.model.bean;

/**
 * Created by ZhangQixiang on 2017/2/13.
 */
public class AccountBean {
    public String server;
    public String name;
    public String pwd;
    public int    id;

    public AccountBean() {
    }

    public AccountBean(int id, String server, String name, String pwd) {
        this(server, name, pwd);
        this.id = id;
    }

    public AccountBean(String server, String name, String pwd) {
        this.server = server;
        this.name = name;
        this.pwd = pwd;
    }
}
