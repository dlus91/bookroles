package com.bookroles.tool;

/**
 * @Author: dlus91
 * @Date: 2023/10/3 0:48
 */
public class SecurityUtil {

    /**
     * 判断参数是否含有攻击串
     * @param value
     * @return
     */
    public static boolean judgeSQLInject(String value){
        if(value == null || "".equals(value)){
            return false;
        }
        String xssStr = "select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|drop|execute|show|%20|=|--|!=|javascript|script|eval|password|invoke";
        String[] xssArr = xssStr.split("\\|");
        for(int i = 0; i < xssArr.length; i++){
            if(value.contains(xssArr[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * 处理跨站xss字符转义
     *
     * @param value
     * @return
     */
    private static String clearXss(String value) {
        if (value == null || "".equals(value)) {
            return value;
        }
        value = value.replaceAll("<", "<").replaceAll(">", ">");
        value = value.replaceAll("\\(", "(").replace("\\)", ")");
        value = value.replaceAll("'", "'");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
                "\"\"");
        value = value.replace("script", "");

        //为了用户密码安全，禁止列表查询展示用户密码----------
        value = value.replace(",password","").replace("password","");
        return value;
    }

}
