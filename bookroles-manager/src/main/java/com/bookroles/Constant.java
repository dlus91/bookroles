package com.bookroles;

import com.bookroles.service.IDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant {

    final static Logger logger = LoggerFactory.getLogger(Constant.class);

    public static ApplicationContext applicationContent;
    public static String PRO_CTX_VALUE;
    private static ServletContext servletContext;
    public static boolean systemDebug = true;
    public final static int pageSize = 8;

    public static ServletContext getInstanceServletContext(){
        return servletContext;
    }

    public static void setInstanceServletContext(ServletContext context){
        if(getInstanceServletContext() == null){
            servletContext = context;
        }
    }

    //todo 以下三个方法 暂时不知道放在哪里，暂时先放在这里，以后再移动
    public static String getRealIp(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("X-FORWARDED-FOR ");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr ();
            }
            if (ip != null && ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        } catch (Throwable e) {
            logger.error(e.getMessage());

        }
        return ip;
    }

    public static String unicodeEncode(String string) {
        char[] utfBytes = string.toCharArray();
        StringBuilder unicodeBytes = new StringBuilder();
        for (char utfByte : utfBytes) {
            String hexB = Integer.toHexString(utfByte);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes.append("\\u").append(hexB);
        }
        return unicodeBytes.toString();
    }

    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }


}
