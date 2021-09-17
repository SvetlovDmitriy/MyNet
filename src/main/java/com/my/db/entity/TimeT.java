package com.my.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class TimeT implements Serializable {
    private int id;
    private int serviceId;
    private Timestamp time;
    private int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "TmeT{" +
                "id=" + id +
                ", serviceId=" + serviceId +
                ", time=" + time +
                ", total=" + total +
                '}';
    }
}
