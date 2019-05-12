package com.yapp14th.yappapp.model;

import java.io.Serializable;
import java.util.ArrayList;

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


     public class GroupInfo{

          private String meet_name;
          private String meet_datetime;
          private String meed_Id;
          private String fk_meetcaptain;
          private String meet_personNumMax;
          private String meet_location;


          public String getMeet_name() {
               return meet_name;
          }

          public void setMeet_name(String meet_name) {
               this.meet_name = meet_name;
          }

          public String getMeet_datetime() {
               return meet_datetime;
          }

          public void setMeet_datetime(String meet_datetime) {
               this.meet_datetime = meet_datetime;
          }

          public String getMeed_Id() {
               return meed_Id;
          }

          public void setMeed_Id(String meed_Id) {
               this.meed_Id = meed_Id;
          }

          public String getFk_meetcaptain() {
               return fk_meetcaptain;
          }

          public void setFk_meetcaptain(String fk_meetcaptain) {
               this.fk_meetcaptain = fk_meetcaptain;
          }

          public String getMeet_personNumMax() {
               return meet_personNumMax;
          }

          public void setMeet_personNumMax(String meet_personNumMax) {
               this.meet_personNumMax = meet_personNumMax;
          }

          public String getMeet_location() {
               return meet_location;
          }

          public void setMeet_location(String meet_location) {
               this.meet_location = meet_location;
          }
     }
}
