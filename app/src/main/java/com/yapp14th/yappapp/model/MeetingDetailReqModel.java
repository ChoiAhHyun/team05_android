package com.yapp14th.yappapp.model;

public class MeetingDetailReqModel {
    String user_Id;
    Integer meet_Id;

    public MeetingDetailReqModel(String user_Id, Integer meet_Id) {
        this.user_Id = user_Id;
        this.meet_Id = meet_Id;
    }
}
