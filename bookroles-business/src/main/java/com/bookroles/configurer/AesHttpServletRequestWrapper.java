package com.bookroles.configurer;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AesHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final String requestBody;
    HttpServletRequest req;
    private Map<String,String> requestMap = new HashMap<>();

    public AesHttpServletRequestWrapper(HttpServletRequest request, String requestBody) {
        super(request);
        this.requestBody = requestBody;
        this.req = request;
        if(requestBody != null && !"".equals(requestBody.trim())){
            this.requestMap = JSONObject.parseObject(requestBody, new TypeReference<HashMap<String,String>>(){});
        }

    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new StringReader(requestBody));
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStream() {

            //            private final InputStream in = new ByteArrayInputStream(requestBody.getBytes(req.getCharacterEncoding()));
            private final InputStream in = new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8));

//            @Override
//            public boolean isFinished() {
//                return true;
//            }
//
//            @Override
//            public boolean isReady() {
//                return true;
//            }
//
//            @Override
//            public void setReadListener(ReadListener readListener) {
//
//            }

            @Override
            public int read() throws IOException {
                return in.read();
            }

        };
    }

    public String getRequestMap(String name) {
        return requestMap.get(name);
    }


}
