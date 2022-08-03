package org.plutodjava.matchmaker.core.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class IdCardNumberMethod {
    /**
     * 根据身份证的号码算出当前身份证持有者的年龄
     *
     * @param
     * @throws Exception
     * @return  -1(表示异常) 0 (身份证号码为空)
     */
    public static int getAgeForIdcard(String idcard) {
        try {
            int age = 0;
            if (StringUtils.isEmpty(idcard)) {
                return age;
            }

            String birth = "";
            if (idcard.length() == 18) {
                birth = idcard.substring(6, 14);
            } else if (idcard.length() == 15) {
                birth = "19" + idcard.substring(6, 12);
            }

            int year = Integer.valueOf(birth.substring(0, 4));
            int month = Integer.valueOf(birth.substring(4, 6));
            int day = Integer.valueOf(birth.substring(6));
            Calendar cal = Calendar.getInstance();
            age = cal.get(Calendar.YEAR) - year;
            //周岁计算
            if (cal.get(Calendar.MONTH) < (month - 1) || (cal.get(Calendar.MONTH) == (month - 1) && cal.get(Calendar.DATE) < day)) {
                age--;
            }
            return age;
        } catch (Exception e) {
            e.getMessage();
        }
        return -1;
    }

}
