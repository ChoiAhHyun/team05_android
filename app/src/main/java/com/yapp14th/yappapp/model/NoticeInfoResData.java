package com.yapp14th.yappapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeInfoResData extends ModelWithDate implements Parcelable {
    public int state;
    public Integer meetId;
    public String notice;
    public Date date;
    public Integer commentNum;

    protected NoticeInfoResData(Parcel in) {
        state = in.readInt();
        if (in.readByte() == 0) {
            meetId = null;
        } else {
            meetId = in.readInt();
        }
        notice = in.readString();
        if (in.readByte() == 0) {
            commentNum = null;
        } else {
            commentNum = in.readInt();
        }
        long tmpDate = in.readLong();
        date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<NoticeInfoResData> CREATOR = new Creator<NoticeInfoResData>() {
        @Override
        public NoticeInfoResData createFromParcel(Parcel in) {
            return new NoticeInfoResData(in);
        }

        @Override
        public NoticeInfoResData[] newArray(int size) {
            return new NoticeInfoResData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(state);
        if (meetId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(meetId);
        }
        dest.writeString(notice);
        if (commentNum == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(commentNum);
        }
        dest.writeLong(date != null ? date.getTime() : -1);
    }
}
