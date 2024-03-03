package com.bookroles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

//todo 传输加解密 前端页面压缩加静态化
public class FirstListener implements ServletContextListener {

    final static Logger logger = LoggerFactory.getLogger(FirstListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("===ServletContext初始化===");
        long beginTime = System.currentTimeMillis();
        Constant.applicationContent = new ClassPathXmlApplicationContext(new String[]{"classpath:/spring-mvc.xml"});
        Constant.setInstanceServletContext(sce.getServletContext());
        Constant.PRO_CTX_VALUE = Constant.getInstanceServletContext().getRealPath("");
        Constant.systemDebug = getDebugValue();
        logger.info("=========初始化成功，耗时:"+ (System.currentTimeMillis() - beginTime) +"ms==========");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        logger.info("===servletContext销毁===");
    }

    private boolean getDebugValue(){
        String debugStr = "true";
        try {
            debugStr = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/system.properties")).get("system.debug").toString();
        } catch (IOException e) {
            logger.error(e.getMessage());

        }
        return "true".equalsIgnoreCase(debugStr);
    }

}
