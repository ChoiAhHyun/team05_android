package com.yapp14th.yappapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoticeCommentResData {
    public int state;
    public ArrayList<CommentInfo> comment;

    public class CommentInfo extends ModelWithDate{

        public String fk_userId;
        public Integer commentId;
        public Integer fk_meetId;
        public String comment;
        public Date date;
        public String userImg;

    }

}