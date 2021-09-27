package com.my.db.entity;

import java.io.Serializable;

public class Service implements Serializable {
    private int id;
    private int userId;
    private int productId;
    private int categoryId;
    private int statusId;

    public Service() {
    }

    public Service(int id, int userId, int productId, int categoryId, int statusId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.categoryId = categoryId;
        this.statusId = statusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", categoryId=" + categoryId +
                ", statusId=" + statusId +
                '}';
    }
}
