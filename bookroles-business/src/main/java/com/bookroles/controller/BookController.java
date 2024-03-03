package com.bookroles.controller;

import com.bookroles.Constant;
import com.bookroles.configurer.AesHttpServletRequestWrapper;
import com.bookroles.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;


//    @PostMapping("getById")
//    @ResponseBody
//    //这个接口被静态化页面替代 可以考虑去掉@RequestMapping注解
//    public Map getById(AesHttpServletRequestWrapper request) {
//        String ipAddr = Constant_0918.getRealIp(request);
//        String idStr = request.getRequestMap("id");
//        if(StringUtils.isEmpty(idStr)){//恶意攻击
//            return null;
//        }
//        int id = Integer.parseInt(idStr);
//        if(id <= 0 || id > 100000){//恶意攻击
//            return null;
//        }
//        return service.getById(id, ipAddr);
//    }

    @PostMapping("getDetailById")
    @ResponseBody
    public Map<String,String> getDetailById(AesHttpServletRequestWrapper request) {
        int id = Integer.parseInt(request.getRequestMap("id"));
        if(id <= 0 || id > 1000){//恶意攻击
            return null;
        }
        return service.getDetailById(id);
    }

    //todo 注意快速访问的 可以给予限制
    @PostMapping("getDownLoadUrl")
    @ResponseBody
    public Map<String,String> getDownLoadUrl(AesHttpServletRequestWrapper request) {
        String idStr = request.getRequestMap("id");
        if(StringUtils.isEmpty(idStr)) {//恶意攻击
            return null;
        }
        int id = Integer.parseInt(idStr);
        if(id <= 0 || id > 1000){//恶意攻击
            return null;
        }
        Map<String,String> map = new HashMap<>();
        map.put("address", service.getDownLoadUrl(id));
        return map;
    }

    @PostMapping("findByWord")
    @ResponseBody
    public List<Map<String,String>> findByWord(AesHttpServletRequestWrapper request) {
        String rowNumStr = request.getRequestMap("row");
        String word = request.getRequestMap("word");
        if(StringUtils.isEmpty(rowNumStr)){
            return null;
        }
        if(StringUtils.isEmpty(word) || word.length() > 100){//恶意攻击
            return null;
        }
        int rowNum = Integer.parseInt(rowNumStr);
        if(rowNum > 0){
            return service.findByWord(word,rowNum);
        }
        return service.findByWord(word,1);
    }

    @PostMapping("findByArchiveType")
    @ResponseBody
    public List<Map<String,String>> findByArchiveType(AesHttpServletRequestWrapper request) {
        String rowNumStr = request.getRequestMap("row");
        String archiveTypeIdStr = request.getRequestMap("archiveTypeId");
        if(StringUtils.isEmpty(archiveTypeIdStr)){
            return null;
        }
        int archiveTypeId = Integer.parseInt(archiveTypeIdStr);
        if(archiveTypeId > 30){//恶意攻击
            return null;
        }
        if(StringUtils.isNotEmpty(rowNumStr)){
            int rowNum = Integer.parseInt(rowNumStr);
            if(rowNum > 0 && archiveTypeId > 0){
                return service.findByArchiveType(archiveTypeId,rowNum);
            }
        }
        return service.findByArchiveType(archiveTypeId,1);
    }

    @PostMapping("findByAuthor")
    @ResponseBody
    public List<Map<String,String>> findByAuthor(AesHttpServletRequestWrapper request) {
        String rowNumStr = request.getRequestMap("row");
        String author = request.getRequestMap("author");
        if(StringUtils.isEmpty(author) || author.length() > 50){//恶意攻击
            return null;
        }
        if(StringUtils.isNotEmpty(rowNumStr)){
            int rowNum = Integer.parseInt(rowNumStr);
            if(rowNum > 0) {
                return service.findByAuthor(author, rowNum);
            }
        }
        return service.findByAuthor(author,1);
    }

    @PostMapping("findHot")
    @ResponseBody
    public List<Map<String,String>> findHot(AesHttpServletRequestWrapper request) {
        String rowNumStr = request.getRequestMap("row");
        if(StringUtils.isNotEmpty(rowNumStr)){
            int rowNum = Integer.parseInt(rowNumStr);
            if(rowNum > 0){
                return service.findHot(rowNum);
            }
        }
        return service.findHot(1);
    }


}
