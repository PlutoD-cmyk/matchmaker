package org.plutodjava.matchmaker.core.enums;

/**
 * @Classname EducationEnum
 * @Description 学历枚举
 * @Author lw
 * @Date 2020-01-08 11:32
 */
public enum EducationEnum {

    juniorCollege(0, "专科"),
    undergraduate (1, "本科"),
    doctor (2, "博士"),
    graduate(3, "研究生");

    private Integer status;

    private String desc;

    private EducationEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(Integer status) {
        EducationEnum[] userStatusEnums = values();
        for (EducationEnum userStatusEnum : userStatusEnums) {
            if (userStatusEnum.getStatus().equals(status)) {
                return userStatusEnum.getDesc();
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}