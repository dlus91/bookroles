package com.bookroles.service;

import com.bookroles.service.model.Dictionary;

import java.util.List;
import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2023/9/27 22:15
 */
public interface IDictionaryService {

    List<String> search();

    List<Dictionary> search(Dictionary dictionary);

    void refresh();

    boolean validAuthor(Dictionary dictionary);

    boolean validPublishHouse(Dictionary dictionary);

    boolean validCacheName(int id,String type);

    boolean validCacheName(String name,String type);

    List<Dictionary> getCacheName(Dictionary dictionary);

    int addName(Dictionary dictionary);

    int modifyName(Dictionary dictionary);


}
