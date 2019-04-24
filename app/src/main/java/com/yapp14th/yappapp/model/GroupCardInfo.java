package com.yapp14th.yappapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GroupCardInfo implements Parcelable {
    private String title;
    private String date;
    private String time;
    private String locName;
    private String distance;
    private ArrayList<String> imgSrcPath;

    public GroupCardInfo(String title, String date,String time, String locName, String distance, ArrayList<String> imgSrcPath){

        this.title = title;
        this.time = time;
        this.date = date;
        this.locName = locName;
        this.distance = distance;
        this.imgSrcPath = imgSrcPath;

    }

    protected GroupCardInfo(Parcel in) {
        title = in.readString();
        date = in.readString();
        time = in.readString();
        locName = in.readString();
        distance = in.readString();
        imgSrcPath = in.createStringArrayList();
    }

    public static final Creator<GroupCardInfo> CREATOR = new Creator<GroupCardInfo>() {
        @Override
        public GroupCardInfo createFromParcel(Parcel in) {
            return new GroupCardInfo(in);
        }

        @Override
        public GroupCardInfo[] newArray(int size) {
            return new GroupCardInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(locName);
        dest.writeString(distance);
        dest.writeStringList(imgSrcPath);
    }
}
