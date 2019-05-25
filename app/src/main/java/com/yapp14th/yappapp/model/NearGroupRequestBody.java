package com.yapp14th.yappapp.model;

public class NearGroupRequestBody {
    String userId;
    Double myLongitude;
    Double myLatitude;


    public NearGroupRequestBody(String userId, Double myLongitude, Double myLatitude){

        this.userId = userId;
        this.myLongitude= myLongitude;
        this.myLatitude = myLatitude;

    }
}
