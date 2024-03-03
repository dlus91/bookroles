package com.bookroles.service;

import com.bookroles.Constant;
import com.bookroles.dao.BookDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("bookService")
public class BookService {

    @Autowired
    private BookDaoImpl bookDaoImpl;

    public Map<String,String> getDetailById(int id) {
        return bookDaoImpl.getDetailById(id);
    }

    public String getDownLoadUrl(int id){
        return bookDaoImpl.getDownLoadUrl(id);
    }

    //todo 书籍相关性 -- 与书籍同个类型的其他书籍或作者在该领域声望较高 -- 浅作者相关性 浅类型相关性 深度解析本书包括书籍中出现的其他书籍和作者
    public List<Map<String,String>> findByWord(String word, int pageNum) {
        List<Map<String,String>> list = bookDaoImpl.findByWord(word,pageNum);
        return list;
    }

    //todo 弃用 管理后台起来后 综合考虑更新问题后 在考虑是否删除代码
//    public List<Map<String,String>> findByWord2(String word, String ipAddr, int pageNum) {
//        //直接搜索业务
//        //todo 这里的代码逻辑是错的 需先加载所有关键字 不然第一次搜索会跳过很多本该被所搜到的书籍
//        List<Map<String,String>> list = bookDaoImpl.findByWord(word,ipAddr,pageNum);
//        String inputWord = keyWords.getInputWord();
//        int preid = 0;
//        Set<String> nset = new HashSet();
//        for(Map map : list){
//            preid = Integer.parseInt(String.valueOf(map.get("id")));
//            String[] wordArray = String.valueOf(map.get("key_words")).split(" ");
//            for(String str : wordArray){
//                nset.add(str);
//            }
//            keyWords.setPreid(preid);
//        }
//        if(inputWord == null || !inputWord.equals(word)){
//            keyWords.setData(nset);
//        }else{
//            keyWords.addAll(nset);
//        }
//
//        List<Map> extentList = new ArrayList();
//        //扩展性搜索业务 -- 如果没有直接搜索 则不存在扩展性搜索
//        if(list == null || list.size() < Constant.pageSize){
//            if(keyWords.getData() == null && keyWords.getData().size() == 0){
//                return new ArrayList();
//            }
//            extentList = bookDaoImpl.findByKeyWords(keyWords.getData().toArray(new String[]{}),keyWords.getPreid(),ipAddr, Constant.pageSize - list.size());
//
//            for(Map map : extentList){
//                preid = Integer.parseInt(String.valueOf(map.get("id")));
//                keyWords.setPreid(preid);
//            }
//        }
//
//        keyWords.setInputWord(word);
//        list.addAll(extentList);
//        System.out.print("ipaddr="+ipAddr+ " serssion keyWords =");
//        System.out.println(keyWords.getData());
//
//        return list;
//    }

    //todo 类型相关性 -- 某本书 属于多个类型 但只能一个主类型 做一个相关性 -- 汇总本领域书籍相关性 并尝试跨领域关联（需验证并得到认可） 并排序
    public List<Map<String,String>> findByArchiveType(int archiveTypeId, int pageNum) {
        List<Map<String,String>> list = bookDaoImpl.findByArchiveType(archiveTypeId,pageNum);
        return list;
    }

    //todo 作者相关性 -- 合著过的 或者 同类型经常出现的 -- 汇总作者关系网 或者说价值观系统 包括提到的他人和被提到的书籍  或者 潜在竞争对手的书籍
    public List<Map<String,String>> findByAuthor(String author, int pageNum) {
        List<Map<String,String>> list = bookDaoImpl.findByAuthor(author,pageNum);
        return list;
    }

    public List<Map<String,String>> findHot(int pageNum) {
        List<Map<String,String>> list = bookDaoImpl.findHot(pageNum);
        return list;
    }

}
