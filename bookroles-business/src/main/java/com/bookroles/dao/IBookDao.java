package com.bookroles.dao;

import java.util.List;
import java.util.Map;

public interface IBookDao {

    Map<String,String> getDetailById(int id);

    String getDownLoadUrl(int id);

    List<Map<String,String>> findByWord(String name, int pageNum);

    List<Map<String,String>> findByArchiveType(int archiveTypeId, int pageNum);

    List<Map<String,String>> findByAuthor(String author, int pageNum);

    List<Map<String,String>> findHot(int pageNum);

}
