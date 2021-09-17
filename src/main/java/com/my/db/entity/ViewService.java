package com.my.db.entity;

import java.io.Serializable;

public class ViewService implements Serializable {
    private int serviceID;
    private String categoryName;
    private String productName;
    private Double productPrice;
    private String productDescription;
    private String statusName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String tatusName) {
        this.statusName = tatusName;
    }

    @Override
    public String toString() {
        return "ViewService{" +
                "serviceID=" + serviceID +
                ", serviceName='" + categoryName + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDescription='" + productDescription + '\'' +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
