package com.yapp14th.yappapp.model;

import java.io.Serializable;

public class InterestModel implements Serializable {

    private String title;
    private Integer isChecked;

    public InterestModel(String title, Integer isChecked) {
        this.title = title;
        this.isChecked = isChecked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }
}