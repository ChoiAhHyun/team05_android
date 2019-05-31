package com.yapp14th.yappapp.model;

public class MeetingDeleteBody {
    String cancelreason;
    Integer meetId;

    public MeetingDeleteBody(String cancelreason, Integer meetId) {
        this.cancelreason = cancelreason;
        this.meetId = meetId;
    }
}
