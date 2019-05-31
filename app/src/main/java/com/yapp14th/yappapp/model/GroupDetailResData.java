package com.yapp14th.yappapp.model;
public class GroupDetailResData {

    public int state;
    public GroupDetailInfo result;

    public GroupDetailResData(int state, GroupDetailInfo result){

        this.state = state;
        this.result = result;

    }

    public class GroupDetailInfo{

        public String captain_id;
        public Double latitude;
        public Double longitude;
        public Integer person_num;
        public String meet_explanation;
        public String user_nick;
        public Integer endingflag;
        public String[] participants_img;
        public String[] participants_Id;
        public String captain_img;
        public Integer participants_num;


    }



}
