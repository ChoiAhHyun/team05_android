package com.yapp14th.yappapp.model;

import java.io.Serializable;
import java.util.List;

public class MypageInterestModel implements Serializable {
    private String userId;
    private List<String> list;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
