package com.bookroles.tool;

/**
 * @Author: dlus91
 * @Date: 2020/11/13 23:30
 */
public enum CsvPropertiesEnum {
    BOOK("book","id,author,name,archive_type_id,archive_type_name,image,score,download_format,upload_time,publish_house,publish_month,content_intro,download_url,key_words"),
    BOOK_CLICK_TOTAL("book_click_total","id,count,timestamp"),
    BOOK_CLICK_RECORD("book_click_record","book_id,author,archive_type_id,ip_addr,user_id,timestamp"),
    BOOK_DOWNLOAD_TOTAL("book_download_total","id,count,timestamp"),
    BOOK_DOWNLOAD_RECORD("book_download_record","book_id,ip_addr,user_id,timestamp"),
    BOOK_SEARCH_RECORD("book_search_record","word,ip_addr,user_id,timestamp"),
    BOOK_KEYWORDS_RECORD("book_keywords_record","id,name,key_words,extend_ids,extend_key_words"),
    BOOK_HTML_RECORD("book_html_record","book_id,file_name,timestamp");


    private final String name;
    private final String headerString;

    CsvPropertiesEnum(String name, String headerString) {
        this.name = name;
        this.headerString = headerString;
    }

    public String getName() {
        return name;
    }

    public String getHeaderString(){return headerString; }



    public static void main(String[] args) {
        System.out.println(CsvPropertiesEnum.BOOK.getName());
        System.out.println(CsvPropertiesEnum.BOOK.getHeaderString());
        System.out.println(CsvPropertiesEnum.BOOK_CLICK_TOTAL.getName());
        System.out.println(CsvPropertiesEnum.BOOK_CLICK_TOTAL.getHeaderString());
    }
}
