package com.my.db.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private int roleId;
    private String login;
    private transient String password;
    private double cash;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return roleId;
    }

    public void setRole(int role) {
        this.roleId = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + roleId +
                ", login='" + login + '\'' +
                ", cash=" + cash +
                '}';
    }
}
