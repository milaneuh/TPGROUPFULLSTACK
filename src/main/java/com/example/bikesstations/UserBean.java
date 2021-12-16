package com.example.bikesstations;

import javax.persistence.*;

@Entity
@Table(name="users_connect")
public class UserBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String user_login;
    private String user_pwd;
    private String user_api_key;

    public UserBean() {
    }

    public String getUser_api_key() {
        return user_api_key;
    }

    public void setUser_api_key(String user_api_key) {
        this.user_api_key = user_api_key;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }
}
