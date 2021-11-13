package com.stark.searchengine.service;

import com.stark.searchengine.connect.ElasticSearchClient;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {
	Logger log = LoggerFactory.getLogger(ElasticSearchService.class);

	@Autowired
	private ElasticSearchClient elasticSearchClient;

	public String query() {
		TransportClient client = elasticSearchClient.getClientInstance();
		GetResponse response = client.prepareGet("nba", "nba", "1").execute().actionGet();
		log.info(response.toString());
		return response.toString();
	}
}
