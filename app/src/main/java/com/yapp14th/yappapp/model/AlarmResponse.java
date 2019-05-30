package com.yapp14th.yappapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class AlarmResponse {

    public int state;

    public ArrayList<AlarmInfo> list;


    public static class AlarmInfo extends ModelWithDate {

        public Integer isEnded;
        public Integer flag;
        public Integer meetId;
        public String profileImage;
        public String senderId;
        public String title;
        public Date date;
        public String content;

    }
}