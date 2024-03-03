package com.bookroles;

import com.bookroles.exception.AssertUtil;
import com.bookroles.tool.CsvPropertiesEnum;
import com.bookroles.tool.CsvUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.*;

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

        //todo 功能开关 维护中页面 更新时暂停服务
        //todo 可以考虑更新逻辑怎么存放 下面的代码放在那里
        //点击记录- 快照表
        CsvUtil bookClickTotalCsv = new CsvUtil().build(CsvPropertiesEnum.BOOK_CLICK_TOTAL.getName(), CsvPropertiesEnum.BOOK_CLICK_TOTAL.getHeaderString()).readList();
        List<Map<String,String>> bookClickTotalDataList = bookClickTotalCsv.getDataList();
        for(Map<String,String> map : bookClickTotalDataList){
            String id = map.get("id");
            String count = map.get("count");
            String timestamp = map.get("timestamp");
            Map<String,String> singMap = new HashMap<>();
            singMap.put("count",count);
            singMap.put("timestamp", timestamp);
            Constant.BOOK_CLICK_LAST_RECORD.put(Integer.valueOf(id), singMap);
        }
        logger.info("初始化 BOOK_CLICK_TOTAL 成功");

        //下载记录- 快照表
        CsvUtil bookDownloadTotalCsv = new CsvUtil().build(CsvPropertiesEnum.BOOK_DOWNLOAD_TOTAL.getName(), CsvPropertiesEnum.BOOK_DOWNLOAD_TOTAL.getHeaderString()).readList();
        List<Map<String,String>> bookDownloadTotalDataList = bookDownloadTotalCsv.getDataList();
        for(Map<String,String> map : bookDownloadTotalDataList){
            String id = map.get("id");
            String count = map.get("count");
            String timestamp = map.get("timestamp");
            Map<String,String> singMap = new HashMap<>();
            singMap.put("count",count);
            singMap.put("timestamp",timestamp);
            Constant.BOOK_CLICK_LAST_RECORD.put(Integer.valueOf(id), singMap);
        }
        logger.info("初始化 BOOK_DOWNLOAD_TOTAL 成功");

        //它只读 不用在销毁时候写，在生成html时才写入 -- 它是最早加载的一张表，因为其他表需要用它初始化
        CsvUtil bookHtmlRecordCsv = new CsvUtil().build(CsvPropertiesEnum.BOOK_HTML_RECORD.getName(),CsvPropertiesEnum.BOOK_HTML_RECORD.getHeaderString()).readList();
        if(Constant.getBookHtmlRecordIndex() == null){
            AssertUtil.isFalse(Constant.setBookHtmlRecordIndex(bookHtmlRecordCsv.getDataList()),"book html record暂无详情页，请先生成");
            logger.info("book html record索引设置成功");
        }else{
            logger.warn("book html record索引设置失败...");
        }

        CsvUtil bookCsv = new CsvUtil().build(CsvPropertiesEnum.BOOK.getName(),CsvPropertiesEnum.BOOK.getHeaderString()).readList();
        if(Constant.getBookIndex() == null){
            AssertUtil.isFalse(Constant.setBookIndex(bookCsv.getDataList()),"book 索引设置失败");
            logger.info("book 索引设置成功");
        }else{
            logger.warn("book 索引设置失败...");
        }

        if(Constant.getBookDownloadUrlIndex() == null){
            AssertUtil.isFalse(Constant.setBookDownloadUrlIndex(bookCsv.getDataList()),"book download_url索引设置失败");
            logger.info("book download_url索引设置成功");
        }else{
            logger.warn("book download_url索引设置失败...");
        }

        CsvUtil bookKeywordsCsv = new CsvUtil().build(CsvPropertiesEnum.BOOK_KEYWORDS_RECORD.getName(), CsvPropertiesEnum.BOOK_KEYWORDS_RECORD.getHeaderString()).readList();
        if(bookKeywordsCsv.getDataList() == null || bookKeywordsCsv.getDataList().isEmpty()){
            List<Map<String, String>> extendBookKeyWordsAndBooksFromBookIndex = Constant.getExtendBookKeyWordsAndBooksFromBookIndex();
            //全量逻辑
            AssertUtil.isFalse(Constant.setBookKeywordsIndex(extendBookKeyWordsAndBooksFromBookIndex),"book keywords全量索引设置失败");
            logger.info("book keywords全量索引设置成功");
        }else{
            //增量逻辑
            List<Map<String,String>> nList = new ArrayList<>();
            List<Map<String,String>> bookList = bookCsv.getDataList();
            List<Map<String,String>> bookKeyWordsList = bookKeywordsCsv.getDataList();
            for (Map<String,String> bookMap : bookList){
                String bookId = String.valueOf(bookMap.get("id"));
                int bookKeyWordsExist = 0;
                for(Map<String,String> bookKeyWordsMap : bookKeyWordsList){
                    String bookKeyWordsId = String.valueOf(bookKeyWordsMap.get("id"));
                    if(bookId.equals(bookKeyWordsId)){
                        bookKeyWordsExist = 1;
                        break;
                    }
                }
                if(bookKeyWordsExist == 0){
                    Map<String,String> nMap = new HashMap<>();
                    nMap.put("id", bookMap.get("id"));
                    nMap.put("name", bookMap.get("name"));
                    nMap.put("key_words", bookMap.get("key_words"));
                    nList.add(nMap);
                }
            }
            if(!nList.isEmpty()){
                List<Map<String,String>> extendBookKeyWordsAndBooksFromDataList = Constant.getExtendBookKeyWordsAndBooksFromData(nList);
                AssertUtil.isFalse(Constant.setBookKeywordsIndex(extendBookKeyWordsAndBooksFromDataList),"book keywords交叉增量索引设置失败");
                logger.info("book keywords交叉增量索引设置成功");
            }else{
                AssertUtil.isFalse(Constant.setBookKeywordsIndex(bookKeywordsCsv.getDataList()),"book keywords本表增量索引设置失败");
                logger.info("book keywords本表增量索引设置成功");
            }
        }

        logger.info("=========初始化成功，耗时:"+ (System.currentTimeMillis() - beginTime) +"ms==========");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        List<Map<String,String>> bookClickTotalList = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, String>> entry : Constant.BOOK_CLICK_LAST_RECORD.entrySet()) {
            Map<String,String> valueMap = entry.getValue();
            Map<String,String> nMap = new HashMap<>();
            nMap.put("id", String.valueOf(entry.getKey()));
            if (StringUtils.isEmpty(valueMap.get("count"))) {
                nMap.put("count", "0");
                nMap.put("timestamp", "0");
            } else {
                nMap.put("count", valueMap.get("count"));
                nMap.put("timestamp", valueMap.get("timestamp"));
            }
            bookClickTotalList.add(nMap);
        }
        new CsvUtil().build(CsvPropertiesEnum.BOOK_CLICK_TOTAL.getName(), CsvPropertiesEnum.BOOK_CLICK_TOTAL.getHeaderString()).writeForData(bookClickTotalList);
        logger.info("===book_click_total数据重新记录完成===");

        List<Map<String,String>> bookDownLoadTotalList = new ArrayList<>();
        for (Map.Entry<Integer, Map<String, String>> entry : Constant.BOOK_DOWNLOAD_LAST_RECORD.entrySet()) {
            Map<String,String> valueMap = entry.getValue();
            Map<String,String> nMap = new HashMap<>();
            nMap.put("id", String.valueOf(entry.getKey()));
            if (StringUtils.isEmpty(valueMap.get("count"))) {
                nMap.put("count", "0");
                nMap.put("timestamp", "0");
            } else {
                nMap.put("count", valueMap.get("count"));
                nMap.put("timestamp", valueMap.get("timestamp"));
            }
            bookDownLoadTotalList.add(nMap);
        }
        new CsvUtil().build(CsvPropertiesEnum.BOOK_DOWNLOAD_TOTAL.getName(), CsvPropertiesEnum.BOOK_DOWNLOAD_TOTAL.getHeaderString()).writeForData(bookDownLoadTotalList);
        logger.info("===book_download_total数据重新记录完成===");

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
