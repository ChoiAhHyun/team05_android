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

    public static String getValueOf(String value) {
        switch (value) {
            case "운동 / 스포츠":
                return "sports";
            case  "야외활동 / 액티비티":
                return "activity";
            case  "토론 / 글쓰기 / 독서":
                return "write";
            case "스터디":
                return "study";
            case "전시회 / 세미나":
                return "exhibition";
            case "음악 / 악기":
                return "music";
            case "만들기 / 수공예":
                return "diy";
            case "봉사활동":
                return "volunteer";
            case "사진 / 촬영":
                return "picture";
            case "게임":
                return "game";
            case "영화":
                return "movie";
            case "쿠킹 / 베이킹":
                return "cooking";
            case "커피 & 디저트":
                return "coffee";
            case "네일아트":
                return "nail";
            case "자동차":
                return "car";
            case "인테리어 / 가구 DIY":
                return "interior";
            case "콘서트 / 공연":
                return "concert";
            case "자유 주제":
                return "etc";

        }
        throw new IllegalArgumentException("Not Enum constant was found for value : " + value);
    }

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
