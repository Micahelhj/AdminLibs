package com.adminlib.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * 类描述：ABLogUtil 字符串中的数字处理
 * 创建人：Michael
 * 创建时间：2015年5月12日 11:22
 * 修改人：Michael
 * 修改时间：2015/12/15 9:40
 * 修改备注：
 */
public class ABNumberUtil {
    /**
     * 判断number参数是否是整型数或者浮点数表示方式
     *
     * @param number the number
     * @return boolean
     */
    public static boolean isIntegerOrFloatNumber(String number) {
        if (TextUtils.isEmpty(number))
            return false;
        if (isIntegerNumber(number) || isFloatPointNumber(number))
            return true;
        else
            return false;
    }

    /**
     * 判断number参数是否是整型数表示方式
     *
     * @param number the number
     * @return boolean
     */
    public static boolean isIntegerNumber(String number) {
        if (TextUtils.isEmpty(number))
            return false;
        number = number.trim();
        String intNumRegex = "\\-{0,1}\\d+";//整数的正则表达式
        if (number.matches(intNumRegex))
            return true;
        else
            return false;
    }

    /**
     * 判断number参数是否是浮点数表示方式
     *
     * @param number the number
     * @return boolean
     */
    public static boolean isFloatPointNumber(String number) {
        if (TextUtils.isEmpty(number))
            return false;
        number = number.trim();
        String pointPrefix = "(\\-|\\+){0,1}\\d*\\.\\d+";//浮点数的正则表达式-小数点在中间与前面
        String pointSuffix = "(\\-|\\+){0,1}\\d+\\.";//浮点数的正则表达式-小数点在后面
        if (number.matches(pointPrefix) || number.matches(pointSuffix))
            return true;
        else
            return false;
    }

    /**
     * 修剪浮点类型  规则(如:0.00保留2位小数)
     *
     * @param value the value
     * @param rules the rules
     * @return String
     */
    public static String getTrim(String value, String rules) {
        if (value == null || value.length() == 0 || rules == null || rules.length() == 0) {
            return "";
        }
        try {
            return getTrim(Double.parseDouble(value), rules);
        } catch (Exception e) {
            return value;
        }
    }

    /**
     * 修剪浮点类型 规则(如:0.00保留2位小数)
     *
     * @param value the value
     * @param rules the rules
     * @return Trim
     */
    public static String getTrim(double value, String rules) {
        DecimalFormat df = new DecimalFormat(rules);
        return df.format(value);
    }

    /**
     * 判断奇、偶数
     *
     * @param number the number
     * @return  boolean
     */
    public static boolean isOdd(int number) {
        if ((number & 1) != 0) {
            return false;
        }
        return true;
    }

    /**
     * 数据分页--获取总页数
     *
     * @param count    总条数
     * @param pageSize 总页数
     * @return : totalPage
     */
    public static int getTotalPage(int count, int pageSize) {
        int totalPage = 0;
        if (count <= pageSize)
            totalPage = 1;
        else {
            totalPage = count / pageSize;//赋值
            if (pageSize != 0 && count % pageSize > 0)
                totalPage++;//实际为totalPage=totalPage+1
        }
        return totalPage;
    }

}
