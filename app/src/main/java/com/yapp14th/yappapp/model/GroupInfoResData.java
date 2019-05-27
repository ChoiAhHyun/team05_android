package com.yapp14th.yappapp.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.dynamic.ObjectWrapper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GroupInfoResData implements Serializable {

    private int state;

    private ArrayList<GroupInfo> list;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<GroupInfo> getList() {
        return list;
    }

    public void setList(ArrayList<GroupInfo> list) {
        this.list = list;
    }


    public static class GroupInfo extends ModelWithDate implements Parcelable {

        public Double distance;
        public Integer meetId;
        public String meetName;
        public Date meetDateTime;
        public String meetlocation;
        public String meet_Img;
        public String meet_personNum;
        public int participantNum;
        public String[] participantImg;

        protected GroupInfo(Parcel in) {
            if (in.readByte() == 0) {
                distance = null;
            } else {
                distance = in.readDouble();
            }
            if (in.readByte() == 0) {
                meetId = null;
            } else {
                meetId = in.readInt();
            }
            meetName = in.readString();
            long tmpDate = in.readLong();
            meetDateTime = tmpDate == -1 ? null : new Date(tmpDate);
            meetlocation = in.readString();
            meet_Img = in.readString();
            meet_personNum = in.readString();
            participantNum = in.readInt();
            participantImg = in.createStringArray();

        }

        public static final Creator<GroupInfo> CREATOR = new Creator<GroupInfo>() {
            @Override
            public GroupInfo createFromParcel(Parcel in) {
                return new GroupInfo(in);
            }

            @Override
            public GroupInfo[] newArray(int size) {
                return new GroupInfo[size];
            }
        };



        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (distance == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(distance);
            }
            if (meetId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(meetId);
            }
            dest.writeString(meetName);
            dest.writeLong(meetDateTime != null ? meetDateTime.getTime() : -1);
            dest.writeString(meetlocation);
            dest.writeString(meet_Img);
            dest.writeString(meet_personNum);
            dest.writeInt(participantNum);
            dest.writeStringArray(participantImg);
        }

    }
}