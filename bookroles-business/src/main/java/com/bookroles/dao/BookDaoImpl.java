package com.bookroles.dao;

import com.bookroles.Constant;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("bookDaoImpl")
public class BookDaoImpl implements IBookDao{

    @Override
    public Map<String,String> getDetailById(int id) {
        Map<String,String> dataSingle = new HashMap<>();
        Map<String,String> oldBookClickMap = Constant.getBookClickLastRecord(id);
        int bookClickCount = Integer.parseInt(oldBookClickMap.get("count"));
        long bookClickTimestamp = Long.parseLong(oldBookClickMap.get("timestamp"));
        dataSingle.put("search_count", String.valueOf(++bookClickCount));
        dataSingle.put("search_time", String.valueOf(bookClickTimestamp));

        Map<String,String> bookDownloadLastRecord = Constant.getBookDownloadLastRecord(id);
        int bookDownloadCount = Integer.parseInt(bookDownloadLastRecord.get("count"));
        long bookDownloadTimestamp = Long.parseLong(bookDownloadLastRecord.get("timestamp"));
        dataSingle.put("download_count", String.valueOf(bookDownloadCount));
        dataSingle.put("download_time", String.valueOf(bookDownloadTimestamp));
        Constant.updateBookCLickLastRecord(id);
        return dataSingle;
    }

    @Override
    public String getDownLoadUrl(int id) {
        Constant.updateBookDownloadLastRecord(id);
        return String.valueOf(Constant.getBookDownloadUrlIndex().get(String.valueOf(id)));
    }

    @Override
    public List<Map<String,String>> findByWord(String name, int pageNum) {
        return Constant.selectBookKeyWordsIndexData(name,pageNum);
    }

    @Override
    public List<Map<String,String>> findByArchiveType(int archiveTypeId, int pageNum) {
        return Constant.selectBookArchiveTypeIndexData(archiveTypeId,pageNum);
    }

    @Override
    public List<Map<String,String>> findByAuthor(String author, int pageNum) {
        return Constant.selectBookAuthorIndexData(author,pageNum);
    }

    @Override
    public List<Map<String,String>> findHot(int pageNum) {
        return Constant.selectBookIndexData(pageNum);
    }
}
