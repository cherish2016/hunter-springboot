package com.cherish.hunterjpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.annotation.Documented;

/**
 * 求职者信息
 * Created by cherish on 2017/2/18.
 */
@Entity

public class Hunter {

    @Id
    private String name;
    private int phone;
    private String email;
    private String address;
    private String status;
    private String originalCompany;
    private String originalPosition;
    private String desiredPosition;
    private String updateTime;
    private String reason;
    private String comments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getOriginalCompany() {
        return originalCompany;
    }

    public void setOriginalCompany(String originalCompany) {
        this.originalCompany = originalCompany;
    }

    public String getOriginalPosition() {
        return originalPosition;
    }

    public void setOriginalPosition(String originalPosition) {
        this.originalPosition = originalPosition;
    }

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public void setDesiredPosition(String desiredPosition) {
        this.desiredPosition = desiredPosition;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
