package com.bookroles;

import com.bookroles.tool.KeyGenerateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * todo
 */
public class Constant {

    final static Logger logger = LoggerFactory.getLogger(Constant.class);

    //书籍点击记录
    public static Map<Integer, Map<String, String>> BOOK_CLICK_LAST_RECORD = new HashMap<>();
    //书籍下载记录
    public static Map<Integer, Map<String, String>> BOOK_DOWNLOAD_LAST_RECORD = new HashMap<>();
    //全局索引
    private static final Map<String, Object> _indexs = new HashMap<>();
    public static ApplicationContext applicationContent;
    public static String PRO_CTX_VALUE;
    private static ServletContext servletContext;
    private static Map<Integer,Map<String,String>> keystoreMap;
    public static boolean systemDebug = true;
    public final static int pageSize = 4;

    public static ServletContext getInstanceServletContext(){
        return servletContext;
    }

    static {
        //todo 先注释掉 只用固定一个密钥  等搞定了https 再回头来解决这个问题
//        keystoreMap = KeyGenerateUtil.generateData();
    }

    public static void setInstanceServletContext(ServletContext context){
        if(getInstanceServletContext() == null){
            servletContext = context;
        }
    }

    //慎用，很耗时
    public static void generateKeystore(){
        KeyGenerateUtil.generateKeystoreData();
    }

    public static Map<String,String> getKeystoreMap(long currentMs){
        if(keystoreMap == null || keystoreMap.isEmpty()){
            return KeyGenerateUtil.getDefaultMap();
        }
        return KeyGenerateUtil.getKeyMap(keystoreMap,currentMs);
    }

    public static Map<String,String> getBookClickLastRecord(int id){
        if(!BOOK_CLICK_LAST_RECORD.containsKey(id)){
            Map<String,String> map = new HashMap<>();
            map.put("count", "0");
            map.put("timestamp", "0");
            BOOK_CLICK_LAST_RECORD.put(id, map);
            return map;
        }
        return BOOK_CLICK_LAST_RECORD.get(id);
    }

    public static void updateBookCLickLastRecord(int id){
        Map<String,String> map = getBookClickLastRecord(id);
        int count = Integer.parseInt(String.valueOf(map.get("count")));
        map.put("count", String.valueOf(++count));
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
    }

    public static void updateBookCLickLastRecord(int id,int count,long timestamp){
        Map<String,String> map = getBookClickLastRecord(id);
        map.put("count", String.valueOf(count));
        map.put("timestamp", String.valueOf(timestamp));
    }

    public static Map<String,String> getBookDownloadLastRecord(int id){
        if(!BOOK_DOWNLOAD_LAST_RECORD.containsKey(id)){
            Map<String,String> map = new HashMap<>();
            map.put("count", "0");
            map.put("timestamp", "0");
            BOOK_DOWNLOAD_LAST_RECORD.put(id, map);
            return map;
        }
        return BOOK_DOWNLOAD_LAST_RECORD.get(id);
    }

    /**
     * @param id  保证id是存在的
     */
    public static void updateBookDownloadLastRecord(int id){
        Map<String,String> map = getBookDownloadLastRecord(id);
        int count = Integer.parseInt(String.valueOf(map.get("count")));
        map.put("count", String.valueOf(++count));
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
    }

    public static void updateBookDownloadLastRecord(int id,int count,long timestamp){
        Map<String,String> map = getBookDownloadLastRecord(id);
        map.put("count", String.valueOf(count));
        map.put("timestamp", String.valueOf(timestamp));
    }

    //book全部索引
    public static List<Map<String,String>> getBookIndex(){
        return (List<Map<String, String>>) _indexs.get("book");
    }

    //book全部索引
    /**
     * @param list 数据
     */
    public static boolean setBookIndex(List<Map<String,String>> list){
        if(list.isEmpty()){
            return false;
        }
        List<Map<String,String>> destList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String,String> bookMap = list.get(i);
            Map<String, String> map = new HashMap<>();
            map.put("rowNum", String.valueOf(i+1));
            map.put("id", bookMap.get("id"));
            map.put("name", bookMap.get("name"));
            map.put("author", bookMap.get("author"));
            map.put("image", bookMap.get("image"));
            map.put("publish_house", bookMap.get("publish_house"));
            map.put("archive_type_id", bookMap.get("archive_type_id"));
            map.put("archive_type_name", bookMap.get("archive_type_name"));
            map.put("key_words", bookMap.get("key_words"));
            map.put("file", getBookHtmlRecordIndex().get(map.get("id")));
            destList.add(map);
        }
        _indexs.put("book", destList);
        return true;
    }

    //book全部索引
    public static void flushBookNameIndex(){
        _indexs.remove("book");
    }

    //获取book by id索引
    public static Map<String,String> selectBookIndexById(String id){
        List<Map<String,String>> list = getBookIndex();
        for(Map<String,String> map : list){
            if(id.equals(map.get("id"))){
                return map;
            }
        }
        return null;
    }

    //获取book by ids索引
    public static List<Map<String,String>> selectBookIndexByIdArray(String[] ids){
        List<Map<String,String>> nList = new ArrayList<>();
        List<Map<String,String>> list = getBookIndex();
        for(Map<String,String> map : list){
            for(String id : ids){
                if(id.equals(map.get("id"))) {
                    nList.add(map);
                }
            }
        }
        return nList;
    }

    //获取book by ids索引
    public static List<Map<String,String>> selectBookIndexByIds(String... id){
        return selectBookIndexByIdArray(id);
    }

    //索引book
    public static List<Map<String,String>> selectBookIndexData(int pageNum){
        return selectBookIndexData(pageNum,pageSize);
    }

    //索引book
    public static List<Map<String,String>> selectBookIndexData(int pageNum, int pageSize){
        List<Map<String,String>> list = getBookIndex();
        if(list.isEmpty()){
            return null;
        }
        List<Map<String,String>> destList = new ArrayList<>();
        //分页模式
        int beginNum = (pageNum - 1) * pageSize;
        int endNum = pageNum * pageSize;
        int rowNum = 0;
        for(Map<String,String> map : list){
            if(rowNum < endNum && rowNum >= beginNum) {
                destList.add(map);
            }else if(rowNum == endNum){
                break;
            }
            rowNum++;
        }
        return destList;
    }

    //索引book word
    public static List<Map<String,String>> selectBookKeyWordsIndexData(String word,int pageNum){
        return selectBookKeyWordsIndexData(word,pageNum,pageSize);
    }

    //undo 持续改进 搜索功能
    //todo 根据热门来排序索引出来的书籍 判断其是搜索书籍还是出版社 来给出不同的排序 与补全
    //索引book name
    public static List<Map<String,String>> selectBookKeyWordsIndexData(String word,int pageNum, int pageSize){
        if(StringUtils.isEmpty(word)){
            return null;
        }
        List<Map<String,String>> list = getBookIndex();
        if(list.isEmpty()){
            return null;
        }
        List<Map<String,String>> destList = new ArrayList<>();
        //分页模式
        int beginNum = (pageNum - 1) * pageSize;
        int endNum = pageNum * pageSize;
        int rowNum = 0;
        for(Map<String,String> map : list){
            String bookId = map.get("id");
            String bookName = map.get("name");
            String publishHouse = map.get("publish_house");
            //搜索书名及相关联的书籍 先深度搜索后广度搜索
            if (bookName.contains(word) || word.contains(bookName)) {
                Map<String, String> extendBookKeywordsMap = selectBookKeywordsIndexById(bookId);
                if(extendBookKeywordsMap != null && !extendBookKeywordsMap.isEmpty()){
                    String extendIds = extendBookKeywordsMap.get("extend_ids");
                    List<Map<String,String>> extendList = selectBookIndexByIdArray(extendIds.split(" "));
                    for(Map<String,String> extendMap : extendList){
                        if(rowNum < endNum && rowNum >= beginNum) {
                            destList.add(extendMap);
                            rowNum++;
                            continue;
                        }else if(rowNum == endNum) {
                            break;
                        }
                        rowNum++;
                    }
                }
            }
            if(publishHouse.contains(word) || word.contains(publishHouse)){
                if(rowNum < endNum && rowNum >= beginNum) {
                    destList.add(map);
                    rowNum++;
                    continue;
                }else if(rowNum == endNum) {
                    break;
                }
                rowNum++;
            }
            if(rowNum == endNum) {
                break;
            }
        }
        return destList;
    }

    //索引book author
    public static List<Map<String,String>> selectBookAuthorIndexData(String author,int pageNum){
        return selectBookAuthorIndexData(author,pageNum,pageSize);
    }

    //索引book author
    public static List<Map<String,String>> selectBookAuthorIndexData(String author,int pageNum, int pageSize){
        if(StringUtils.isEmpty(author)){
            return null;
        }
        List<Map<String,String>> list = getBookIndex();
        if(list.isEmpty()){
            return null;
        }
        List<Map<String,String>> destList = new ArrayList<>();
        //分页模式
        int beginNum = (pageNum - 1) * pageSize;
        int endNum = pageNum * pageSize;
        int rowNum = 0;
        for(Map<String,String> map : list){
            String bookAuthor = map.get("author");
            if (bookAuthor.contains(author)) {
                if(rowNum < endNum && rowNum >= beginNum) {
                    destList.add(map);
                }else if(rowNum == endNum){
                    break;
                }
                rowNum++;
            }
        }
        return destList;
    }

    //索引book archive_type
    public static List<Map<String,String>> selectBookArchiveTypeIndexData(int archiveTypeId,int pageNum){
        return selectBookArchiveTypeIndexData(archiveTypeId,pageNum,pageSize);
    }

    //索引book archive_type
    public static List<Map<String,String>> selectBookArchiveTypeIndexData(int archiveTypeId,int pageNum, int pageSize){
        if(archiveTypeId == 0){
            return null;
        }
        List<Map<String,String>> list = getBookIndex();
        if(list.isEmpty()){
            return null;
        }
        List<Map<String,String>> destList = new ArrayList<>();
        //分页模式
        int beginNum = (pageNum - 1) * pageSize;
        int endNum = pageNum * pageSize;
        int rowNum = 0;
        for(Map<String,String> map : list){
            int id = Integer.parseInt(map.get("archive_type_id"));
            if (archiveTypeId == id) {
                if(rowNum < endNum && rowNum >= beginNum) {
                    destList.add(map);
                }else if(rowNum == endNum){
                    break;
                }
                rowNum++;
            }

        }
        return destList;
    }

    //book download_url 索引
    public static Map<String,String> getBookDownloadUrlIndex(){
        return (Map<String,String>) _indexs.get("book_download_url");
    }

    //book download_url 索引
    /**
     * @param list 数据
     */
    public static boolean setBookDownloadUrlIndex(List<Map<String,String>> list){
        if(list.isEmpty()){
            return false;
        }
        Map<String,String> destMap = new HashMap<>();
        for(Map<String,String> bookMap : list){
            destMap.put(bookMap.get("id"), bookMap.get("download_url"));
        }
        _indexs.put("book_download_url", destMap);
        return true;
    }

    //book download_url 索引
    public static void flushBookDownloadUrlIndex(){
        _indexs.remove("book_download_url");
    }

    //book 扩展关键字 索引
    public static List<Map<String,String>> getBookKeywordsIndex(){
        return (List<Map<String,String>>) _indexs.get("book_keywords");
    }

    //book 扩展关键字 索引
    public static Map<String,String> selectBookKeywordsIndexById(String id){
        List<Map<String,String>> list = (List<Map<String,String>>) _indexs.get("book_keywords");
        for(Map<String,String> map : list){
            String extendId = map.get("id");
            if(id.equals(extendId)){
                return map;
            }
        }
        return null;
    }

    //book 扩展关键字 索引
    /**
     * @param list 数据
     */
    public static boolean setBookKeywordsIndex(List<Map<String,String>> list){
        if(list.isEmpty()){
            return false;
        }
        List<Map<String,String>> extendList = new ArrayList<>();
        for(Map<String,String> map : list){
            Map<String,String> nMap = new HashMap<>();
            nMap.put("id", map.get("id"));
            nMap.put("name", map.get("name"));
            nMap.put("key_words", map.get("key_words"));
            nMap.put("extend_ids", map.get("extend_ids"));
            nMap.put("extend_key_words", map.get("extend_key_words"));
            extendList.add(nMap);
        }
        _indexs.put("book_keywords", extendList);
        return true;
    }

    //索引根据book key_words扩展出 extend_key_words和entend_books集合
    //增量更新逻辑
    public static List<Map<String,String>> getExtendBookKeyWordsAndBooksFromData(List<Map<String,String>> list){
//		List<Map> list = getBookIndex();
        //一般来说 书籍领域的关键字是很少改变的 所以一次运算后 就会持久化下来了
        //唯一需要注意的就是更新的时候要分 增量更新与存量迭代
        //这个逻辑适合做增量数据 读取新增书籍  这就不用担心效率问题了
        List<Map<String,String>> globalList = getBookIndex();
        for(Map<String,String> map : list){
            String keyWords = map.get("key_words");
            Set<Object> extendIdSet = new HashSet<>();
            Set<Object> extendKeywordsSet = new HashSet<>();
            map.put("extend_ids", "");
            map.put("extend_key_words", "");
            //存量数据 读取当下所有
            for(Map<String, String> inMap : globalList){
                String inBookIds = inMap.get("id");
                String inKeyWords = inMap.get("key_words");
                String[] inKeyWordArray = inKeyWords.split(" ");
                for(String word : inKeyWordArray){
                    if(keyWords.contains(word)){
                        extendIdSet.add(inBookIds);
                        extendKeywordsSet.addAll(Arrays.asList(inKeyWordArray));
                        break;
                    }
                }
            }
            if(!extendIdSet.isEmpty()){
                String idStr = StringUtils.join(extendIdSet.toString().split(","), "");
                idStr = StringUtils.stripStart(idStr, "[");
                idStr = StringUtils.stripEnd(idStr, "]");
                map.put("extend_ids", idStr);
            }
            if(!extendKeywordsSet.isEmpty()){
                String keywordStr = StringUtils.join(extendKeywordsSet.toString().split(","), "");
                keywordStr = StringUtils.stripStart(keywordStr, "[");
                keywordStr = StringUtils.stripEnd(keywordStr, "]");
                map.put("extend_key_words", keywordStr);
            }

        }
        return list;
    }

    //索引根据book key_words扩展出 extend_key_words和entend_books集合
    //存量更新逻辑 谨慎运用
    public static List<Map<String,String>> getExtendBookKeyWordsAndBooksFromBookIndex(){
        List<Map<String,String>> list = getBookIndex();
        List<Map<String,String>> extendList = new ArrayList<>();
        for(Map<String,String> map : list){
            Map<String,String> nMap = new HashMap<>();
            nMap.put("id", map.get("id"));
            nMap.put("name", map.get("name"));
            nMap.put("key_words", map.get("key_words"));
            extendList.add(nMap);
        }
        return getExtendBookKeyWordsAndBooksFromData(extendList);
    }

    //book 扩展关键字 索引
    public static void flushBookKeywordsIndex(){
        _indexs.remove("book_keywords");
    }

    //book html record
    public static Map<String,String> getBookHtmlRecordIndex(){
        return (Map<String,String>) _indexs.get("book_html_record");
    }

    //book html record
    /**
     * @param list 数据
     */
    public static boolean setBookHtmlRecordIndex(List<Map<String,String>> list){
        if(list.isEmpty()){
            return false;
        }
        Map<String,String> destMap = new HashMap<>();
        for (Map<String,String> bookMap : list){
            destMap.put(bookMap.get("book_id"), bookMap.get("file_name"));
        }
        _indexs.put("book_html_record", destMap);
        return true;
    }

    //book html record
    public static void flushBookHtmlRecordIndex(){
        _indexs.remove("book_html_record");
    }


    //todo 以下三个方法 暂时不知道放在哪里，暂时先放在这里，以后再移动
    public static String getRealIp(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("X-FORWARDED-FOR ");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr ();
            }
            if (ip != null && ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        } catch (Throwable e) {
            logger.error(e.getMessage());

        }
        return ip;
    }

    public static String unicodeEncode(String string) {
        char[] utfBytes = string.toCharArray();
        StringBuilder unicodeBytes = new StringBuilder();
        for (char utfByte : utfBytes) {
            String hexB = Integer.toHexString(utfByte);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes.append("\\u").append(hexB);
        }
        return unicodeBytes.toString();
    }

    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }


//    public static void main(String[] args) {
//        long beginTime = System.currentTimeMillis();
//        setBookIndex(getData());
//        List list = getExtendBookKeyWordsAndBooksFromBookIndex();
//        System.out.println(list);
//		CsvUtil csvUtil = new CsvUtil().build(CsvPropertiesEnum.BOOK_KEYWORDS_RECORD.getName(), CsvPropertiesEnum.BOOK_KEYWORDS_RECORD.getHeaderString()).add(list);
//		Map extendMap = new HashMap();
//		String[] extendStr = csvUtil.getDataStr().split("\r\n");
//		for(String str : extendStr){
//			String[] fieldArray = str.split(",");
//			Map nMap = new HashMap();
//			nMap.put("id", fieldArray[0].replaceAll("\"", ""));
//			nMap.put("name", fieldArray[1].replaceAll("\"", ""));
//			nMap.put("key_words", fieldArray[2].replaceAll("\"", ""));
//			nMap.put("extend_ids", fieldArray[3].replaceAll("\"", ""));
//			nMap.put("extend_key_words", fieldArray[4].replaceAll("\"", ""));
//			System.out.println(nMap);
//			extendMap.put(nMap.get("id"), nMap);
//		}
//		CsvUtil csvUtil = new CsvUtil().build(CsvPropertiesEnum.BOOK_KEYWORDS_RECORD.getName(), CsvPropertiesEnum.BOOK_KEYWORDS_RECORD.getHeaderString()).readList();
//		System.out.println(csvUtil.getDataList());
//        System.out.println("耗时：" + (System.currentTimeMillis() - beginTime) + "ms");
//    }

//    public static List getData(){
//        List list = new ArrayList();
//        HashMap map1 = new HashMap();
//        map1.put("id", 1);
//        map1.put("name", "技术陷阱");
//        map1.put("key_words","技术 陷阱 工业革命 创造性创新 替代性创新 失业 陷阱1");
//        list.add(map1);
//        return list;
//        return null;
//    }


}
