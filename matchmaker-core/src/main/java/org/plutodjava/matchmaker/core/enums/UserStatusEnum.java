package org.plutodjava.matchmaker.core.enums;

/**
 * @Classname CarTypeEnum
 * @Description 汽车类型枚举
 * @Author lw
 * @Date 2020-01-08 11:32
 */
public enum UserStatusEnum {

    AVAILABLE(0, "可用"),
    FORBIDDEN(1, "禁用"),
    REMOVAL(2, "注销");

    private Integer status;

    private String desc;

    private UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(Integer status) {
        UserStatusEnum[] userStatusEnums = values();
        for (UserStatusEnum userStatusEnum : userStatusEnums) {
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