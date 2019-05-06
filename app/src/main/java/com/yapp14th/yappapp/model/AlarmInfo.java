package com.yapp14th.yappapp.model;

public class AlarmInfo {

    private String imgPath;

    private int alarmType, timeState;

    private String title, date, content;

    private Boolean isFull;


    public AlarmInfo(String imgPath, int alarmType, int timeState, String title, String date, String content, Boolean isFull) {
        this.imgPath = imgPath;
        this.alarmType = alarmType;
        this.timeState = timeState;
        this.title = title;
        this.date = date;
        this.content = content;
        this.isFull = isFull;

    }


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public int getTimeState() {
        return timeState;
    }

    public void setTimeState(int timeState) {
        this.timeState = timeState;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }
}
