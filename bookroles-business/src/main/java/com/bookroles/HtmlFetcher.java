package com.bookroles;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.bookroles.tool.CsvPropertiesEnum;
import com.bookroles.tool.CsvUtil;
import com.bookroles.tool.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HtmlFetcher {

    final static Logger logger = LoggerFactory.getLogger(HtmlFetcher.class);

    public static String htmlUrl;
    public static String dataCenterId;
    public static String machineId;

    static {
        try {
            htmlUrl = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/system.properties")).get("system.htmlFileURL").toString();
            dataCenterId = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/system.properties")).get("system.dataCenterId").toString();
            machineId = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/system.properties")).get("system.machineId").toString();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static List<Map<String,String>> findBookAndBookHtmlRecordDiffRecord(List<Map<String,String>> bookList){
        CsvUtil bookHtmlUtil = new CsvUtil().build(CsvPropertiesEnum.BOOK_HTML_RECORD.getName(), CsvPropertiesEnum.BOOK_HTML_RECORD.getHeaderString()).readList();
        List<Map<String,String>> bookHtmlList = bookHtmlUtil.getDataList();
        List<Map<String,String>> list = new ArrayList<>();
        for(Map<String,String> bookMap : bookList){
            String bookId = bookMap.get("id");
            int bookHtmlRecordExist = 0;
            for(Map<String,String> bookHtmlMap : bookHtmlList){
                String bookHtmlId = bookHtmlMap.get("book_id");
                if(bookId.equals(bookHtmlId)){
                    bookHtmlRecordExist = 1;
                    break;
                }
            }
            if(bookHtmlRecordExist == 0){
                list.add(bookMap);
            }
        }
        return list;
    }

    private static List<Map<String,String>> findBookClickAndBookDownloadAndFillRecord(List<Map<String,String>> list){
        CsvUtil bookClickTotalCsv = new CsvUtil().build(CsvPropertiesEnum.BOOK_CLICK_TOTAL.getName(), CsvPropertiesEnum.BOOK_CLICK_TOTAL.getHeaderString()).readList();
        CsvUtil bookDownloadTotalCsv = new CsvUtil().build(CsvPropertiesEnum.BOOK_DOWNLOAD_TOTAL.getName(), CsvPropertiesEnum.BOOK_DOWNLOAD_TOTAL.getHeaderString()).readList();
        for(Map<String,String> bookClickAndBookDownloadMap : list){
            String idStr = bookClickAndBookDownloadMap.get("id");
            List<Map<String,String>> bookClickTotalDataList = bookClickTotalCsv.getDataList();
            for(Map<String,String> map : bookClickTotalDataList){
                String bookClickCountIdStr = map.get("id");
                if(idStr.equals(bookClickCountIdStr)) {
                    if(map.get("count") != null) {
                        bookClickAndBookDownloadMap.put("search_count", map.get("count"));
                        bookClickAndBookDownloadMap.put("search_time", map.get("timestamp"));
                    }else{
                        bookClickAndBookDownloadMap.put("search_count", "0");
                        bookClickAndBookDownloadMap.put("search_time", "0");
                    }
                }
            }


            List<Map<String,String>> bookDownloadTotalDataList = bookDownloadTotalCsv.getDataList();
            for(Map<String,String> map : bookDownloadTotalDataList){
                String bookDownloadCountIdStr = map.get("id");
                if(idStr.equals(bookDownloadCountIdStr)) {
                    if(map.get("count") != null) {
                        bookClickAndBookDownloadMap.put("download_count", map.get("count"));
                        bookClickAndBookDownloadMap.put("download_timestamp", map.get("timestamp"));
                    }else{
                        bookClickAndBookDownloadMap.put("download_count", "0");
                        bookClickAndBookDownloadMap.put("download_time", "0");
                    }
                }
            }
        }

        return list;
    }

    private static List<Map<String,String>> findBookKeywordsBook(List<Map<String,String>> list,List<Map<String,String>> bookList){
        CsvUtil bookKeyWordsRecordUtil = new CsvUtil().build(CsvPropertiesEnum.BOOK_KEYWORDS_RECORD.getName(), CsvPropertiesEnum.BOOK_KEYWORDS_RECORD.getHeaderString()).readList();
        List<Map<String,String>> bookKeyWordsRecordList = bookKeyWordsRecordUtil.getDataList();
        for(Map<String,String> bookFillMap : list){
            String idStr = bookFillMap.get("id");
            List<String> extendIdList = new ArrayList<>();
            List<Map<String,String>> extendBooks = new ArrayList<>();
            for(Map<String,String> bookKeyWordsRecordMap : bookKeyWordsRecordList){
                String bookKeyWordsIdStr = bookKeyWordsRecordMap.get("id");
                if(idStr.equals(bookKeyWordsIdStr)){
                    extendIdList = List.of(bookKeyWordsRecordMap.get("extend_ids").split(" "));
                    break;
                }
            }
            if(!extendIdList.isEmpty()) {
                int num = 0;
                for(String extendId : extendIdList){
                    Map<String, String> nmap = new HashMap<>();
                    if(num < 4) {
                        for (Map<String,String> bookMap : bookList) {
                            String bookIdStr = bookMap.get("id");
                            if (extendId.equals(bookIdStr)) {
                                nmap.put("id", bookIdStr);
                                nmap.put("name", bookMap.get("name"));
                                nmap.put("author", bookMap.get("author"));
                                nmap.put("image", bookMap.get("image"));
                                extendBooks.add(nmap);
                                ++num;
                                break;
                            }
                        }
                    }else{
                        break;
                    }
                }
            }
            bookFillMap.put("extend_books",JSONArray.from(extendBooks).toJSONString());
        }
        return list;
    }

    private static String getProtertiesHtml(){
        //jsoup主要用于爬虫 非常好用 切记
//        String url = "http://localhost:8080/bookroles/detail/prototype.html";
//        Document doc = Jsoup.connect(url).get();
//        String propertiesPageHtml = doc.html();
        String filePath = System.getProperty("user.dir") + "\\src\\main\\webapp\\detail\\prototype.html";
        StringBuilder html = new StringBuilder();
        try {
            InputStreamReader fReader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(fReader);
            String line;
            while ((line = reader.readLine()) != null) {
                html.append(line).append("\r\n");
            }
            reader.close();
            fReader.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return html.toString();
    }

    //这里必须生成页面
    private static List<Map<String,String>> productionHtml(List<Map<String,String>> detailList, String propertiesPageHtml){
        List<Map<String,String>> nhtmlList = new ArrayList<>();
        for (Map<String,String> map : detailList) {
            //这里逻辑有点奇怪，主要是因为 extend_books数据未经处理不希望直接输出到页面上
            String extendBookJson = map.get("extend_books");
            map.remove("extend_books");
            String detailBookJson = JSONObject.from(map).toJSONString();
            //移除是为了序列化时不存在，这里需加回去保证数据完整
            map.put("extend_books",extendBookJson);
            String html = propertiesPageHtml.replace("const bookDetailData = {}", "const bookDetailData = " + detailBookJson);
            String fileName = String.valueOf(new SnowFlake(Long.parseLong(dataCenterId),Long.parseLong(machineId)).nextId());
            map.put("file", fileName);
            map.put("file_content", html);
            String fileUrl = htmlUrl + fileName + ".html";
            File file = new File(fileUrl);
            if (file.exists()) {
                logger.warn("===存在，跳过" + fileUrl + "页面");
            } else {
                try {
                    FileWriter fileWriter = new FileWriter(fileUrl, true);
                    fileWriter.append(html);
                    fileWriter.flush();
                    fileWriter.close();
                    Map<String,String> nHtmlMap = new HashMap<>();
                    nHtmlMap.put("book_id", map.get("id"));
                    nHtmlMap.put("file_name", map.get("file") +".html");
                    nHtmlMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
                    nhtmlList.add(nHtmlMap);
                    logger.info("===第一次生成 " + fileUrl + " 页面，主要是为了填充主数据和生成文件名称");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
        new CsvUtil().build(CsvPropertiesEnum.BOOK_HTML_RECORD.getName(),CsvPropertiesEnum.BOOK_HTML_RECORD.getHeaderString())
                .addList(nhtmlList);
        return detailList;
    }

    private static void secondProductionHtml(List<Map<String,String>> productionList){
        for (Map<String,String> productionMap : productionList) {
            String extendBookJson = productionMap.get("extend_books");
            List<Map> extendBookList = JSONObject.parseObject(extendBookJson, new TypeReference<ArrayList<Map>>(){});
            for(Map<String,String> extendBookMap : extendBookList){
                String extendIdStr = extendBookMap.get("id");
                for (Map<String,String> fileMap : productionList) {
                    String idStr = fileMap.get("id");
                    if (idStr.equals(extendIdStr)) {
                        extendBookMap.put("file", fileMap.get("file") + ".html");
                        break;
                    }
                }
            }
            String secondExtendBookJson = JSONArray.from(extendBookList).toJSONString();
            String html = productionMap.get("file_content");
            html = html.replace("const bookExtendData = []", "const bookExtendData = " + secondExtendBookJson);
            String fileName = productionMap.get("file");
            String fileUrl = htmlUrl + fileName + ".html";
            File file = new File(fileUrl);
            if (file.exists()) {
                try {
                    FileWriter fileWriter = new FileWriter(fileUrl);
                    fileWriter.append(html);
                    fileWriter.flush();
                    fileWriter.close();
                    logger.info("===第二次渲染 " + fileUrl + " 页面，主要是为了填充其他次要数据，如：最新书籍，热门书籍，相关书籍等...");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            } else {
                logger.error("===二次渲染页面不允许页面不存在,book_id:" + productionMap.get("id"));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        long beginTime = System.currentTimeMillis();
        CsvUtil bookUtil = new CsvUtil().build(CsvPropertiesEnum.BOOK.getName(), CsvPropertiesEnum.BOOK.getHeaderString()).readList().excludeHeaders("download_url", "key_words");
        List<Map<String,String>> bookList = bookUtil.getDataList();
        //类似 book left join book_html_record on id != id 差集 语句
        List<Map<String,String>> bookAndBookHtmlRecordDiffRecordList =findBookAndBookHtmlRecordDiffRecord(bookList);
        if(bookAndBookHtmlRecordDiffRecordList.isEmpty()){
            logger.warn("暂无新书籍上架，请检查 book_html_record.csv和 /detail/文件架下的页面");
            return;
        }
        //填充 查询次数 查询时间 下载次数 下载时间
        List<Map<String,String>> bookClickAndBookDownloadAndFillRecordList = findBookClickAndBookDownloadAndFillRecord(bookAndBookHtmlRecordDiffRecordList);
        //填充 相关书籍 页面刚生成时不存在关联URL 有关联书籍但没有地址，地址要在详情页上自己补充
        List<Map<String,String>> bookKeywordsBookList = findBookKeywordsBook(bookClickAndBookDownloadAndFillRecordList,bookList);
        //获取原型页面 字符串
        String propertiesPageHtml = getProtertiesHtml();
        //生产页面并返回记录-只插入主要/必要的数据和文件名称
        List<Map<String,String>> productionList = productionHtml(bookKeywordsBookList, propertiesPageHtml);
        //二次生成html
        secondProductionHtml(productionList);
        logger.info("===book_html_record表记录成功，总耗时："+(System.currentTimeMillis() - beginTime)+"ms===");
    }

}
