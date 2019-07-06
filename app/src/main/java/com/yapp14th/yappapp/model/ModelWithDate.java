package com.yapp14th.yappapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelWithDate {

    public String getStringFormatDate(Date dateType) {

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.mm.dd");
        return transFormat.format(dateType);

    }

    public String getStringFormatTime(Date dateType){

        SimpleDateFormat transFormat = new SimpleDateFormat("h:mm a");
        return transFormat.format(dateType).replace("AM", "am").replace("PM","pm");

    }
}
