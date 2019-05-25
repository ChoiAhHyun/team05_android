package com.yapp14th.yappapp.model;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {

    private String userId;

    private String userPw;

    private Integer userGen;

    private String userBirth;

    private String userNick;

    private List<InterestModel> interest;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public Integer getUserGen() {
        return userGen;
    }

    public void setUserGen(Integer userGen) {
        this.userGen = userGen;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public List<InterestModel> getInterest() {
        return interest;
    }

    public void setInterest(List<InterestModel> interest) {
        this.interest = interest;
    }

}
