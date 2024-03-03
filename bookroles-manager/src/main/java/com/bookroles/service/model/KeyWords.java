package com.bookroles.service.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

/**
 * @Author: dlus91
 * @Date: 2023/9/10 16:09
 */
//@Component("keyWords")
//@SessionScope
public class KeyWords {

    private String inputWord;

    private int preid;

    private Set<String> data = new HashSet();

    public boolean add(String word){
        return this.data.add(word);
    }

    public boolean addAll(Collection c){
        return this.data.addAll(c);
    }

    public String getInputWord() {
        return inputWord;
    }

    public void setInputWord(String inputWord) {
        this.inputWord = inputWord;
    }

    public int getPreid() {
        return preid;
    }

    public void setPreid(int preid) {
        this.preid = preid;
    }

    public Set<String> getData() {
        return data;
    }

    public void setData(Set<String> data) {
        this.data = data;
    }

    public static void main(String[] args) {
        Set<String> data = new HashSet<String>();
        data.add("aaa");
        data.add("bbb");
        data.add("ccc");
        data.add("ddd");
        data.add("eee");
        data.add("fff");
        data.add("ggg");
        String[] aa = data.toArray(new String[]{});

//        Arrays.asList(aa).stream().forEach(System.out::println);
    }
}
