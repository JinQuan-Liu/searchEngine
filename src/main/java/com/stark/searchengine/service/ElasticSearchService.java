package com.stark.searchengine.service;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
		return String.valueOf(response);
	}
}
