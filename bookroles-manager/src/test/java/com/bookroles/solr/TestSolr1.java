package com.bookroles.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: dlus91
 * @Date: 2023/10/30 16:53
 */
public class TestSolr1 {

    Long beginTime;
    SolrClient client;

    @Before
    public void init(){
        beginTime = System.currentTimeMillis();

        //solr定义的标准中，新增的文档类型都是SolrinputDocument
        //新增/更新用add，查询用query，删除有delete
        String basePath = "http://192.168.1.102:8983/solr/book-roles";
        client = new HttpSolrClient.Builder(basePath).build();
    }

    @After
    public void destory(){
        System.out.printf("耗时为：%dms\n",System.currentTimeMillis() - beginTime);
    }

    @Test
    public void testQuery(){
        SolrQuery query = new SolrQuery();
        query.setQuery("zh_all:价值");
//        query.setQuery("open:1");
        //排序
        query.setSort("id",SolrQuery.ORDER.desc);
        //分页
        query.setStart(0);
        query.setRows(10);
        //高亮
        query.setHighlight(true);
        query.addHighlightField("zh_all");
        query.setHighlightSimplePre("<span style='color:red'>");
        query.setHighlightSimplePost("</span>");

        QueryResponse rep = null;
        try {
            rep = client.query(query);
            //响应头
            NamedList<Object> headList = rep.getHeader();
            System.out.println("======响应头=======");
            for (Map.Entry<String, Object> entry : headList) {
                System.out.println(entry);
            }
            System.out.println("======响应体=======");
            System.out.println("======响应体 头部数据=======");
            System.out.println(rep.getResults().getStart());
            System.out.println(rep.getResults().getNumFound());
            System.out.println(rep.getResults().getMaxScore());
            System.out.println(rep.getResults().getNumFoundExact());
            System.out.println("======响应体 数据=======");
            Map<String, Map<String, List<String>>> highlighting = rep.getHighlighting();
            for (SolrDocument result : rep.getResults()) {
                Map<String, List<String>> hlrow = highlighting.get(result.getFieldValue("id"));
                System.out.println(hlrow);
                System.out.println(result);
            }
            System.out.println("======响应体 高亮数据=======");
            System.out.println(highlighting);
        } catch (SolrServerException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("======all=======");
        System.out.println(rep);

    }

    //包括新增和更新，主键一致-更新，主键不存在-新增
    @Test
    public void testUpdate() {
        //SolrInputDocument，就是用于描述一个文档的对象，通过field+value描述一个文档
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "7");
        document.addField("zh_all", "测试solrj22新增数据");
        UpdateResponse rep = null;
        try {
            //在solr服务中，数据的写操作，也是有事务的。web管理平台，默认一次操作一次提交，solrJ执行回滚。
            rep = client.add(document);
            System.out.println(rep);
            client.commit();
            //现实使用中，没必要每次都close
            client.close();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            try {
                client.rollback();
            } catch (SolrServerException | IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Test
    public void testDelete() {
        List<String> idList = List.of("7", "001");
        UpdateResponse rep = null;
        try {
            rep = client.deleteById(idList);
            System.out.println(rep);
            client.commit();
            client.close();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            try {
                client.rollback();
            } catch (SolrServerException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }


}
