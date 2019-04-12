package com.yapp14th.yappapp.model;

public enum Category {

    sport("운동 / 스포츠"),
    activity("야외활동 / 액티비티"),
    reading("토론/ 글쓰기 / 독서"),
    study("스터디"),
    consert("공연 / 전시회 / 세미나"),
    music("음악 / 영화"),
    make("만들기 / 수공예"),
    volunteer("봉사활동"),
    picture("사진 / 촬영"),
    qame("게임"),
    cooking("쿠킹 / 베이킹");


    private String name;

    private static final int size = Category.values().length;

    Category(String name) {
        this.name = name;
    }

    public static int size() {
        return size;
    }

    public String getName() {
        return name;
    }

}
