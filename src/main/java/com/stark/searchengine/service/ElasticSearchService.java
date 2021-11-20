package com.stark.searchengine.service;

import com.alibaba.fastjson.JSON;
import com.stark.searchengine.bean.News;
import com.stark.searchengine.constants.IndexInfo;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class ElasticSearchService {
	Logger log = LoggerFactory.getLogger(ElasticSearchService.class);

	// elasticSearch客户端
	@Qualifier("restHighLevelClient")
	@Autowired
	private RestHighLevelClient client;

	public String searchById(String id) throws IOException {
		log.info("queryById start id:{}", id);
		GetRequest request = new GetRequest("nba", "nba",id);
		GetResponse response = client.get(request, RequestOptions.DEFAULT);
		log.info("queryById end, query result:{}", response);
		if (null != response) {
			return response.toString();
		} else {
			return "查无结果";
		}
	}

	public IndexResponse save(String title, String tag, Date time) {
		IndexRequest indexRequest = new IndexRequest(IndexInfo.NEWS_INDEX_NAME, IndexInfo.NEWS_INDEX_TYPE);
		News news = new News();
		news.setTitle(title);
		news.setTag(tag);
		news.setPublishTime(time);
		String source = JSON.toJSONString(news);
		indexRequest.source(source, XContentType.JSON);
		IndexResponse response = null;
		try {
			response = client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != response) {
			return response;
		}
		return null;
	}

	public String fuzzySearch() throws IOException {
		SearchSourceBuilder builder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest();
//		searchRequest.indices("nba");
//		builder.query(QueryBuilders.matchQuery("gymnasium", "球馆"));
//		builder.query(QueryBuilders.matchPhrasePrefixQuery("gymnasium", "球馆"));
		builder.query(QueryBuilders.boolQuery()
			.should(QueryBuilders.matchQuery("name_en", "Cleveland"))
			.should(QueryBuilders.matchQuery("publishTime", "1636873402352")));
		searchRequest.source(builder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		return searchResponse.toString();
	}
}
