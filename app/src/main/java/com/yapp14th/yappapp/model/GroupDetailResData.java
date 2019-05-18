package com.yapp14th.yappapp.model;

import java.util.ArrayList;

public class GroupDetailResData {

    public int state;
    public ArrayList<GroupDetailInfo> list;

    public GroupDetailResData(int state, ArrayList<GroupDetailInfo> modelList){

        this.state = state;
        this.list = modelList;

    }

    public ArrayList<GroupDetailInfo> getGroupDetailInfo(){return this.list;}

    public class GroupDetailInfo{

        public int meed_Id;
        public String meet_name;
        public String fk_meetcaptain;
        public String meet_datetime;
        public String meet_location;
        public Double meet_latitude;
        public Double meet_longitude;
        public String meet_explanation;
        public String meet_personNum;

        public GroupDetailInfo(int meed_Id, String meet_name, String fk_meetcaptain,  String meet_datetime, String meet_location, Double meet_latitude, Double meet_longitude,  String meet_explanation, String meet_personNum){

            this.meed_Id = meed_Id;
            this.fk_meetcaptain = fk_meetcaptain;
            this.meet_name = meet_name;
            this.meet_datetime = meet_datetime;
            this.meet_latitude = meet_latitude;
            this.meet_longitude = meet_longitude;
            this.meet_location = meet_location;
            this.meet_explanation = meet_explanation;
            this.meet_personNum = meet_personNum;

        }

    }

}
