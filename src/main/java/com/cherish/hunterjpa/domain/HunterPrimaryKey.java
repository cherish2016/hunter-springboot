package com.cherish.hunterjpa.domain;

import java.io.Serializable;

/**
 * 联合主键类
 * Created by Administrator on 2017/2/26.
 */
public class HunterPrimaryKey implements Serializable {
    private String name;
    private Integer phone;

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
