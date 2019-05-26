package com.yapp14th.yappapp.model;

import java.io.Serializable;
import java.util.List;

public class SearchModel implements Serializable {

    public double distance;
    public int meetId;
    public String meetName;
    public String meetDateTime;
    public String meetlocation;
    public int meet_personNum;
    public String meet_Img;
    public int participantNum;
    public List<String> participantImg;

}
