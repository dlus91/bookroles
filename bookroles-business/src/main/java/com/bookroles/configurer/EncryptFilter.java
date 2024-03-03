package com.bookroles.configurer;

import com.bookroles.Constant;
import com.bookroles.exception.AssertUtil;
import com.bookroles.tool.AESpkcs7paddingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EncryptFilter implements Filter{

    final static Logger logger = LoggerFactory.getLogger(EncryptFilter.class);

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println(filterConfig.toString());
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        System.out.println("javax.net.ssl :" + System.getProperty("javax.net.ssl .trustStore"));
//        System.setProperty("javax.net.ssl .trustStore","H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\keystore\\tomcat_keystore.jks");
//        System.setProperty("javax.net.ssl .trustStorePassword","bookroles");
        System.out.println("request.getRequestURI(): "+request.getRequestURI());
        if("".equals(request.getRequestURI()) || "/bookroles/".equals(request.getRequestURI())){
            try {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } catch (IOException | ServletException e) {
                logger.error("拦截首页异常:" + e.getMessage());
                writeErrorResponse(response,"首页异常");
            }
        }

//        String path = request.getRequestURL().substring(request.getContextPath().length()).replaceAll("/+$", "");
        String path = request.getRequestURL().toString();
        logger.info("进入加解密过滤器,URL:" + path);
        long beginTime = System.currentTimeMillis();
        logger.info("进入加解密过滤器,时间:" + beginTime);
        List<String> allowpaths = new ArrayList<>();
        setWhiteList(allowpaths);
        long currentMs = System.currentTimeMillis();
        request.getSession().setAttribute("currentMs", currentMs);
        /**
         * todo: 做安全验证，具体做法，
         * todo: 在这里将请求数据的接口和其他接口 区分出来，请求数据接口延迟一会50ms 自己实现一个map，去跟踪它的请求链，看看有没有请求页面所需的其他必须的 请求，如果有则放行数据接口，如果没有则给予警告。只请求数据接口没有其他静态数据的请求是不合理的，此为判断依据/思路，可以有多套策略替换
         * todo: 同时强制页面参数与解码参数需一致 并且做限制（也就是说一个数据请求接口，需要多个参数字段校验）
         * todo: 比如5秒内最多有两次数据请求，map验证后可以手动将ip为key的用户数据销毁（密钥管理），反之则可以进入候选(短期)黑名单map中，还可以维护一个长期黑名单（手动校验）
         * todo: 这里可以有个统一异常处理，将不合理请求和黑名单请求 做异常管理，返回base64的数据
         */
        try {
            if (validFilterFlag(allowpaths, request.getRequestURL().toString())) {
                filterChain.doFilter(request, response);
                return;
            }
            String requestBody = getRequestBody(request,response);
            Map<String, String> keyMap = Constant.getKeystoreMap(currentMs);
            String requestBodyMw = new AESpkcs7paddingUtil().decrypt(requestBody,keyMap);
            logger.info("解密请求数据:" + requestBodyMw);
            request.setAttribute("requestBody", requestBodyMw);
            AesHttpServletRequestWrapper wrapRequest = new AesHttpServletRequestWrapper(request, requestBodyMw);
            AesHttpServletResponseWrapper wrapResponse = new AesHttpServletResponseWrapper(response);
            filterChain.doFilter(wrapRequest, wrapResponse);
            byte[] data = wrapResponse.getResponseData();
            String responseStr = new String(data, StandardCharsets.UTF_8);
            logger.info("原始返回数据:" + responseStr);
            // 加密返回报文
            String responseBodyMw = new AESpkcs7paddingUtil().encrypt(responseStr,keyMap);
            logger.info("加密返回数据:" + responseStr);
            writeResponse(servletResponse, responseBodyMw);
            logger.info("加密返回数据,运行时间:" + (System.currentTimeMillis() - beginTime));
        } catch (Exception e) {
            logger.error("加解密过程失败:" + e.getMessage());
            logger.info("加解密过程失败,运行时间:" + (System.currentTimeMillis() - beginTime));
            writeErrorResponse(response,"加解密过程失败");
        }
    }

    private void setWhiteList(List<String> allowpaths){
        //这里为了安全尽量可以写全 如：js,css文件较少直接全上
        allowpaths.add("/home.jsp");
        allowpaths.add("/detail.html");
        allowpaths.add("/header.html");
        allowpaths.add("/footer.html");
        allowpaths.add("/aaa.jsp");
        allowpaths.add("/css/bookroles1.ttf");
        allowpaths.add("/css/bootstrap.min.css");
        allowpaths.add("/css/monokai_sublime.min.css");
        allowpaths.add("/js/arttemplate.js");
        allowpaths.add("/js/base64.js");
        allowpaths.add("/js/bootstrap.min.js");
        allowpaths.add("/js/crypto-js.js");
        allowpaths.add("/js/crypto-js2.min.js");
        allowpaths.add("/js/jquery-3.7.1.min.js");
        //只允许以下三个只匹配到文件夹 这种做法比较危险
        allowpaths.add("/detail/");
        allowpaths.add("/image/");
        allowpaths.add("/bootstrap-icons/");
        allowpaths.add("/a/get");
    }

    private boolean validFilterFlag(List<String> allowpaths, String url){
        AssertUtil.isTrue(url.length() > 90, "请求地址格式不正确");
        for(String str : allowpaths){
            if(!Constant.systemDebug){
                if(url.contains(("www.bookroles.com" + str))){
                    return true;
                }
            }else {
                if(
                        url.contains(("localhost:8080/bookroles" + str)) ||
                        url.contains(("localhost:8090/bookroles" + str)) ||
                        url.contains(("192.168.1.100:8080/bookroles" + str)) ||
                        url.contains(("192.168.1.100:8090/bookroles" + str)) ||
                        url.contains(("localhost:8443/bookroles" + str)) ||
                        url.contains(("192.168.1.100:8443/bookroles" + str))
                ){
                    return true;
                }
            }
        }
        return false;
    }

    private String getRequestBody(HttpServletRequest request,HttpServletResponse response) {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            logger.error("请求体读取失败:" + e.getMessage());
            writeErrorResponse(response,"请求体读取失败");
        }
        return "";
    }

    private void writeErrorResponse(HttpServletResponse response,String exceptionString) {
        try {
            response.setStatus(500);
            response.setContentType("text/plain");
            response.getWriter().write(exceptionString);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponse(ServletResponse response, String responseString) {
        try {

            response.setContentType("text/plain");
            response.getWriter().write(responseString);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
