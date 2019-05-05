package com.yapp14th.yappapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserModel implements Serializable {

    private String userId;

    private String userPw;

    private Boolean userGen;

    private Date userBirth;

    private String userNick;

    private byte[] userImg;

    private List<InterestModel> interest;

    private Integer gps_lat;

    private Integer gps_lan;

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

    public Boolean getUserGen() {
        return userGen;
    }

    public void setUserGen(Boolean userGen) {
        this.userGen = userGen;
    }

    public Date getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(Date userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public byte[] getUserImg() {
        return userImg;
    }

    public void setUserImg(byte[] userImg) {
        this.userImg = userImg;
    }

    public List<InterestModel> getInterest() {
        return interest;
    }

    public void setInterest(List<InterestModel> interest) {
        this.interest = interest;
    }

    public Integer getGps_lat() {
        return gps_lat;
    }

    public void setGps_lat(Integer gps_lat) {
        this.gps_lat = gps_lat;
    }

    public Integer getGps_lan() {
        return gps_lan;
    }

    public void setGps_lan(Integer gps_lan) {
        this.gps_lan = gps_lan;
    }
}
