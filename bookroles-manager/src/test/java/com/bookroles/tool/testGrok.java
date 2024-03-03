package com.bookroles.tool;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2023/10/8 21:19
 */
public class testGrok {

    @Test
    public void test1() {
//        String filePath = "H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\log\\bookroles_tomcat_access_log.2023-09-25.txt";
//
//        ClassPathResource classPathResource = new ClassPathResource("grok-pattern");
//
//        System.out.println(classPathResource.getPath());
//
//
//        String GROK_PATTERN_PATH = classPathResource.getPath();
//        String logMsg = "<165>1 2003-08-24T05:14:15.000003-07:00 192.0.2.1 myproc 8710 - - %% It's time to make the do-nuts.";
//        String pattern = "%{SYSLOG5424LINE}";
//        Grok grok = new Grok();
//        grok.addPatternFromFile(GROK_PATTERN_PATH);
//
//        grok.compile(pattern);
//        Match grokMatch = grok.match(logMsg);
//        grokMatch.captures();
//        if(!grokMatch.isNull()){
//            System.out.println(grokMatch.toMap().toString());
//            System.out.println(grokMatch.toJson().toString());
//        }else{
//            System.out.println("not match");
//        }
    }

    @Test
    public void test2()  {
//        URL pattern_url = getClass().getClassLoader().getResource(
//                "pattarns/sourcefire");
//        Grok grok = new Grok();
//        grok = Grok.create(pattern_url.getFile());
//        grok.compile("%{SOURCEFIRE}");
    }


}
