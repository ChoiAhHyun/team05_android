package com.yapp14th.yappapp.model;

public enum Category {

    sports("운동 / 스포츠"),
    activity("야외활동 / 액티비티"),
    write("토론 / 글쓰기 / 독서"),
    study("스터디"),
    exhibition("전시회 / 세미나"),
    music("음악 / 악기"),
    diy("만들기 / 수공예"),
    volunteer("봉사활동"),
    picture("사진 / 촬영"),
    game("게임"),
    movie("영화"),
    cooking("쿠킹 / 베이킹"),
    coffee("커피 & 디저트"),
    nail("네일아트"),
    car("자동차"),
    interior("인테리어 / 가구 DIY"),
    concert("콘서트 / 공연"),
    etc("자유 주제");


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
