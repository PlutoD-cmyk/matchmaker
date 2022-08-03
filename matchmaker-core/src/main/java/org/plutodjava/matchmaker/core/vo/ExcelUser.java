package org.plutodjava.matchmaker.core.vo;
/*
 *@Author: ljy
 *@Date:2021/11/6 10:45
 *
 */

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;
@Data
@ExcelTarget("users")
public class ExcelUser {
    @Excel(name="编号")
    private Integer id;
    @Excel(name="姓名")
    private String username;
    @Excel(name="手机号")
    private String mobile;
    @Excel(name="年龄")
    private Integer age;
    @Excel(name="身份证号",width=20.0)
    private String certificate;
    @Excel(name="性别")
    private String gender;
    @Excel(name="婚姻情况")
    private String marriage;
    @Excel(name="头像")
    private String avatar;
    @Excel(name="可用状态")
    private String statusDesc;
}
