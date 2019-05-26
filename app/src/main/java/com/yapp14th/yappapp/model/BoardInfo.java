package com.yapp14th.yappapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BoardInfo implements Parcelable {

    private String imgPath;
    private String name;
    private String date;
    private String content;


    public BoardInfo(String imgPath, String name, String date, String content) {
        this.imgPath = imgPath;
        this.name = name;
        this.date = date;
        this.content = content;

    }

    public String getImgPath() {
        return imgPath;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    protected BoardInfo(Parcel in) {
        imgPath = in.readString();
        name = in.readString();
        date = in.readString();
        content = in.readString();
    }

    public static final Creator<BoardInfo> CREATOR = new Creator<BoardInfo>() {
        @Override
        public BoardInfo createFromParcel(Parcel in) {
            return new BoardInfo(in);
        }

        @Override
        public BoardInfo[] newArray(int size) {
            return new BoardInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgPath);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(content);
    }
}
