package com.yapp14th.yappapp.model;

import android.os.Parcel;
import android.os.Parcelable;

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


     public static class GroupInfo implements Parcelable {

          private String meet_name;
          private String meet_datetime;
          private String meed_Id;
          private String fk_meetcaptain;
          private String meet_personNumMax;
          private String meet_location;


          protected GroupInfo(Parcel in) {
               meet_name = in.readString();
               meet_datetime = in.readString();
               meed_Id = in.readString();
               fk_meetcaptain = in.readString();
               meet_personNumMax = in.readString();
               meet_location = in.readString();
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

          public String getFull(){return this.meet_name+" "+this.meet_datetime+ " "+this.meed_Id +" "+this.fk_meetcaptain+" "+this.meet_personNumMax+ " "+this.meet_location;}

          @Override
          public int describeContents() {
               return 0;
          }

          @Override
          public void writeToParcel(Parcel dest, int flags) {
               dest.writeString(meet_name);
               dest.writeString(meet_datetime);
               dest.writeString(meed_Id);
               dest.writeString(fk_meetcaptain);
               dest.writeString(meet_personNumMax);
               dest.writeString(meet_location);
          }
     }
}
