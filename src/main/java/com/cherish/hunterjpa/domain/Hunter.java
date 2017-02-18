package com.cherish.hunterjpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 求职者信息
 * Created by cherish on 2017/2/18.
 */
@Entity
public class Hunter {

    @Id
    private String name;
    private String phone;
    private String address;
    private String status;
    private String originalPosition;
    private String updateTime;
    private String reason;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOriginalPosition() {
        return originalPosition;
    }

    public void setOriginalPosition(String originalPosition) {
        this.originalPosition = originalPosition;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
