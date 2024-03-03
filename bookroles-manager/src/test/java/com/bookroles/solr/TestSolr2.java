package com.bookroles.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.CollectionUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 * @Author: dlus91
 * @Date: 2023/11/7 9:24
 */
public class TestSolr2 {

    Long beginTime;
    SolrClient solrClient;

    @Before
    public void init(){
        beginTime = System.currentTimeMillis();

        //solr定义的标准中，新增的文档类型都是SolrinputDocument
        //新增/更新用add，查询用query，删除有delete
        String basePath = "http://192.168.1.102:8983/solr/book-roles";
        solrClient = new HttpSolrClient.Builder(basePath).build();
    }

    @After
    public void destory(){
        System.out.printf("耗时为：%dms\n",System.currentTimeMillis() - beginTime);
    }

    private static String GetCurrentDate(){
        Date dt = new Date();
        //最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day =sdf.format(dt);
        return day;
    }


    @Test
    public void test01(){
        String fileName = "敏捷思维训练法.pdf";
        String solrId = fileName;
        String filePath = "H:\\book\\Solr\\"+fileName;
        Path path = Path.of(filePath);
        try {
            String contentType = Files.probeContentType(path);
            System.out.println(contentType);
            ContentStreamUpdateRequest contentStreamUpdateRequest = new ContentStreamUpdateRequest("/update/extract");
            contentStreamUpdateRequest.addFile(path.toFile(), contentType);

            contentStreamUpdateRequest.setParam("literal.id", solrId);
            contentStreamUpdateRequest.setParam("literal.path", filePath);
            contentStreamUpdateRequest.setParam("literal.pathuploaddate", GetCurrentDate());
            contentStreamUpdateRequest.setParam("literal.pathtype", contentType);
            contentStreamUpdateRequest.setParam("zh_smart_all", "attr_content");
            contentStreamUpdateRequest.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            solrClient.request(contentStreamUpdateRequest);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SolrServerException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void test2(){
        HashMap<Object, Object> h1 = CollectionUtil.newHashMap(16);
    }




}
