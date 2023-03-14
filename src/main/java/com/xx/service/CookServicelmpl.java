package com.xx.service;

import com.alibaba.fastjson.JSONObject;
import com.xx.dao.CookDao;
import com.xx.entity.Cook;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CookServicelmpl implements CookService {

    @Autowired
    private CookDao cookDao;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Override
    public List<Cook> findAll() {
        return cookDao.findAll();
    }
    @Override
    public void add(Cook cook) {
        cookDao.add(cook);
    }
    @Override
    public void update(Cook cook) {
        cookDao.update(cook);
    }
    @Override
    public Cook selectOne(String id) {
        return cookDao.selectOne(id);
    }
    @Override
    public void delete(String id) {
        cookDao.delete(id);
    }

    @Override
    public void createIndexAll() throws IOException {
        List<Cook> all = cookDao.findAll();
        for (Cook cook:all) {
            IndexRequest indexRequest = new IndexRequest("ems", "emp", cook.getId());
            String s = JSONObject.toJSONStringWithDateFormat(cook, "yyyy-mm-dd");
            indexRequest.source(s, XContentType.JSON);
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        }
    }

    @Override
    public void clearIndexAll() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建搜索对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //指定查询条件
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.indices("ems")
                     .types("emp")
                     .source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        //给list赋值
        ArrayList<Cook> list = new ArrayList<>();
        SearchHit[] dochits = hits.getHits();

        for (SearchHit all:dochits) {
            Cook cook = new Cook();
            cook.setId(all.getId());
            list.add(cook);
        }
        for (Cook cook:list) {
            DeleteRequest deleteRequest = new DeleteRequest("ems", "emp", cook.getId());
            restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
        }

    }


}
