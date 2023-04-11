package com.axesplit.image.model.enums;

public enum PhotoSize {
    //    普通寸照
    ONE_INCH(295, 413, "一寸照"),
    TWO_INCH(413, 579, "二寸照"),
    LARGE_ONE_INCH(390, 567, "大一寸照"),
    SMALL_ONE_INCH(260, 378, "小一寸照"),
    LARGE_TWO_INCH(413, 626, "大二寸照"),
    SMALL_TWO_INCH(413, 531, "小二寸照"),
    FIVE_INCH(1050, 1499, "五寸照"),
    AVATAR(500, 500, "头像"),

    //    考试报名
    NORMAL(413, 513, "普通寸照"),
    CIVIL_SERVANT(413, 626, "公务员考试"),
    CET(240, 320, "四六级考试"),
    TEACHER(360, 480, "教师资格证"),
    CPA(178, 220, "注册会计师"),
    NURSE(295, 413, "护士资格证"),
    MANDARIN(390, 567, "普通话等级"),
    //     证书相关
    ID_CARD(358, 441, "身份证"),
    SOCIAL_SECURITY(358, 441, "社保照"),
    GRADUATION_CERTIFICATE(460, 540, "毕业证"),
    PASSPORT(354, 472, "护照"),
    DRIVING_LICENSE(520, 756, "交管驾驶证");
    private final int width;
    private final int height;
    private final String describe;

    PhotoSize(int width, int height, String describe) {
        this.width = width;
        this.height = height;
        this.describe = describe;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getDescribe() {
        return describe;
    }
}
