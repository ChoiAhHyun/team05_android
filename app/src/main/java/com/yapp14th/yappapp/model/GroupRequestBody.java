package com.yapp14th.yappapp.model;

public class GroupRequestBody {

    int meetPage;
    String userId;
    Double myLongitude;
    Double myLatitude;


    public GroupRequestBody(String userId, Double myLongitude, Double myLatitude, int meetPage){

        this.userId = userId;
        this.myLongitude= myLongitude;
        this.myLatitude = myLatitude;
        this.meetPage = meetPage;

    }

    public GroupRequestBody(String userId, Double myLongitude, Double myLatitude){

        this.userId = userId;
        this.myLongitude= myLongitude;
        this.myLatitude = myLatitude;

    }
}
