package com.stark.searchengine.service;

import com.alibaba.fastjson.JSON;
import com.stark.searchengine.bean.News;
import com.stark.searchengine.constants.IndexInfo;
import com.stark.searchengine.dto.ArticleDto;
import com.stark.searchengine.vo.ArticleVo;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {
	Logger log = LoggerFactory.getLogger(ElasticSearchService.class);

	@Qualifier("restHighLevelClient")
	@Autowired
	private RestHighLevelClient client;

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

	public List<ArticleDto> fuzzySearch(ArticleVo inputContent) throws IOException {
		SearchSourceBuilder builder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest();
		builder.query(QueryBuilders.boolQuery()
				.should(QueryBuilders.matchQuery("content", inputContent.getInputContent()))
				.should(QueryBuilders.matchQuery("title", inputContent.getInputContent())));
		searchRequest.source(builder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		List<ArticleDto> articleDtoList = new ArrayList<>();
		for (SearchHit searchHit : searchResponse.getHits().getHits()) {
			System.out.println(searchHit.getSourceAsString());
			ArticleDto articleDto = JSON.parseObject(searchHit.getSourceAsString(), ArticleDto.class);
			articleDtoList.add(articleDto);
		}
		return articleDtoList;
	}
}
