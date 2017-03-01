package com.cherish.hunterjpa.model;

/**
 * 搜索bean结构
 * Created by Administrator on 2017/3/1.
 */
public class SearchBean {

    private String field;
    private String value;

    public SearchBean(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
