package com.xx.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.xx.entity.Cook;
import com.xx.entity.User;
import com.xx.service.CookService;
import org.apache.commons.io.FilenameUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("cook")
public class CookController {

    private static Logger logger = LoggerFactory.getLogger(CookController.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private CookService cookService;

    @RequestMapping("selectIndex")
    public String selectIndex(String name,HttpServletRequest request) throws IOException {
        List<Cook> list = new ArrayList<>();
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建搜索对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //指定查询条件
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name").requireFieldMatch(false).preTags("<span style='color:red;'>").postTags("</font>");
        searchSourceBuilder
                .highlighter(highlightBuilder)
                .query(QueryBuilders.queryStringQuery(name).field("name"));
        searchRequest.indices("ems")
                     .types("emp")
                     .source(searchSourceBuilder);
        //返回搜索结果
        SearchResponse search = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        //获取hits对象
        SearchHits hits = search.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit doc:hits1) {
            //原始文档
            Map<String, Object> sourceAsMap = doc.getSourceAsMap();
            //高亮之后字段放入highlightFild
            Map<String, HighlightField> highlightFields = doc.getHighlightFields();
            Cook cook = new Cook();
            cook.setId(doc.getId());
            cook.setName((String) sourceAsMap.get("name"));
            cook.setImg((String) sourceAsMap.get("img"));
            if (highlightFields.containsKey("name")){
                cook.setName(String.valueOf(highlightFields.get("name").fragments()[0]));
            }
            cook.setBuzhou((String) sourceAsMap.get("buzhou"));
            cook.setZhaiyao((String) sourceAsMap.get("zhaiyao"));
            if (highlightFields.containsKey("zhaiyao")){
                cook.setZhaiyao(String.valueOf(highlightFields.get("zhaiyao").fragments()[0]));
            }
            list.add(cook);
        }
        request.setAttribute("cook",list);
        return "front/index";
    }

    @RequestMapping("query")
    public String query(HttpServletRequest request) throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //创建请求对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.indices("ems")
                     .types("emp")
                     .source(searchSourceBuilder);
        //返回搜索结果
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        SearchHit[] hits1 = hits.getHits();

        ArrayList<Cook> list = new ArrayList<>();

        for (SearchHit doc:hits1) {
            Map<String, Object> sourceAsMap = doc.getSourceAsMap();
            Cook cook = new Cook();
            cook.setImg(sourceAsMap.get("img").toString());
            cook.setName(sourceAsMap.get("name").toString());
            cook.setZhaiyao(sourceAsMap.get("zhaiyao").toString());
            list.add(cook);
        }
        request.setAttribute("cook",list);
        return "front/index";
    }


    @RequestMapping("createIndexAll")
    @ResponseBody
    public Map<String,String> createIndexAll() throws IOException {
        HashMap<String,String> hashMap = new HashMap<>();
        try {
            cookService.createIndexAll();
            hashMap.put("status","创建成功!");
        } catch (IOException e) {
            e.printStackTrace();
            hashMap.put("status","创建失败!");
        }
        return hashMap;
    }
    @RequestMapping("clearIndexAll")
    @ResponseBody
    public HashMap<String,String> clearIndexAll() throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            cookService.clearIndexAll();
            hashMap.put("status","清除成功!");
        } catch (IOException e) {
            e.printStackTrace();
            hashMap.put("status","清除失败!");
        }
        return hashMap;
    }
    @RequestMapping("findAll")
    public String findAll(HttpServletRequest request){
        List<Cook> all = cookService.findAll();
        request.setAttribute("cook",all);
        return "back/list";
    }

    @RequestMapping("insert")
    public String insert(MultipartFile aaa,Cook cook,HttpServletRequest request) throws IOException {
        //获取上传路径
        String realPath = request.getSession().getServletContext().getRealPath("/files");
        //获取文件名称
        String filename = aaa.getOriginalFilename();
        //文件后缀
        String extension = FilenameUtils.getExtension(filename);
        //新文件名
        String newFileName=UUID.randomUUID().toString().replace("-","")+ "."+extension;
        //当前上传文件以日期文件夹存放
        String dateDir = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //创建日期目录
        File file = new File(realPath,dateDir);
        //不存在创建目录
        if(!file.exists())file.mkdirs();
        //上传到服务器
        User user = (User) request.getSession().getAttribute("user");
        aaa.transferTo(new File(file,newFileName));
        cook.setId(UUID.randomUUID().toString());
        cook.setImg(dateDir+"/"+newFileName);
        cook.setTime(dateDir);
        cook.setPeople(user.getUsername());
        cookService.add(cook);
        IndexRequest indexRequest = new IndexRequest("ems", "emp", cook.getId());
        indexRequest.source(JSONObject.toJSONStringWithDateFormat(cook,"yyyy-mm-dd"), XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        logger.info("状态:"+index.status());
        return "redirect:/cook/findAll";
    }

    @RequestMapping("delete")
    public String delete(String id,HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("/files");
        cookService.delete(id);
        return "redirect:/cook/findAll";
    }

    @RequestMapping("update")
    public String update(Cook cook,MultipartFile bbb,HttpServletRequest request) throws IOException {
        if (bbb.getSize()!=0){
            //获取文件路径
            String realPath = request.getSession().getServletContext().getRealPath("/files");
            //文件名称
            String filename = bbb.getOriginalFilename();
            //获取文件后缀
            String extension = FilenameUtils.getExtension(filename);
            //新文件名字
            String newFileName =UUID.randomUUID().toString().replace("-","")+"."+extension;
            //当前文件目录以日期存放
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            //创建日期目录
            File file = new File(realPath,format);
            //不存在创建目录
            if(!file.exists())file.mkdirs();
            //上传到服务器
            bbb.transferTo(new File(file,newFileName));
            cook.setImg(format+"/"+newFileName);
            cook.setName(cook.getName());
            cook.setTime(format);
            cookService.update(cook);
        }
        UpdateRequest updateRequest = new UpdateRequest("ems","emp",cook.getId());
        updateRequest.doc(JSONObject.toJSONStringWithDateFormat(cook,"YYYY-mm-dd"),XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        logger.info("状态",update.status());
        cookService.update(cook);
        return "redirect:/cook/findAll";
    }
    @RequestMapping("selectOne")
    public String selectOne(String id,HttpServletRequest request){
        Cook cook = cookService.selectOne(id);
        request.setAttribute("cook",cook);
        return "back/update";
    }
}
