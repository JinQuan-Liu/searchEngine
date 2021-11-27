package com.stark.searchengine.service;

import com.alibaba.fastjson.JSON;
import com.stark.searchengine.bean.Article;
import com.stark.searchengine.constants.IndexInfo;
import com.stark.searchengine.dto.ArticleDto;
import com.stark.searchengine.vo.ArticleSaveVo;
import com.stark.searchengine.vo.ArticleSearchVo;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {
	Logger log = LoggerFactory.getLogger(ArticleService.class);

	@Qualifier("restHighLevelClient")
	@Autowired
	private RestHighLevelClient client;

	public String save(ArticleSaveVo articleSaveVo) {
		Article article = new Article();
		article.setTitle(articleSaveVo.getTitle());
		article.setContent(articleSaveVo.getContent());
		article.setPublishTime(new Date());
		IndexRequest indexRequest = new IndexRequest(IndexInfo.ARTICLE_INDEX_NAME);
		String source = JSON.toJSONString(article);
		indexRequest.source(source, XContentType.JSON);
		IndexResponse response = null;
		try {
			response = client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";

	}

	public List<ArticleDto> searchAll() throws IOException {
		SearchRequest searchRequest = new SearchRequest().indices("article");
		SearchSourceBuilder builder = new SearchSourceBuilder();
		// 设置最大查询数量为10
		builder.from(0);
		builder.size(10);
		searchRequest.source(builder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		List<ArticleDto> articleDtoList = new ArrayList<>();
		for (SearchHit searchHit : searchResponse.getHits().getHits()) {
			ArticleDto articleDto = JSON.parseObject(searchHit.getSourceAsString(), ArticleDto.class);
			if (articleDto.getContent().length() > 200) {
				articleDto.setContent(articleDto.getContent().substring(0,200) + "...");
			}
			articleDtoList.add(articleDto);
		}
		return articleDtoList;
	}

	public List<ArticleDto> fuzzySearch(ArticleSearchVo inputContent) throws IOException {
		SearchSourceBuilder builder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest();
		builder.query(QueryBuilders.boolQuery()
				.should(QueryBuilders.matchQuery("content", inputContent.getInputContent()))
				.should(QueryBuilders.matchQuery("title", inputContent.getInputContent())));
		//设置高亮
		String preTags = "<span style='color:red;'>";
		String postTags = "</span>";
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.preTags(preTags);//设置前缀
		highlightBuilder.postTags(postTags);//设置后缀
		highlightBuilder.field("title").field("content").numOfFragments(3).noMatchSize(150);//设置高亮字段
		builder.highlighter(highlightBuilder);
//		builder.size(10);
		searchRequest.source(builder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		List<ArticleDto> articleDtoList = new ArrayList<>();
		for (SearchHit searchHit : searchResponse.getHits().getHits()) {
			Map<String, HighlightField> map = searchHit.getHighlightFields();
			ArticleDto articleDto = JSON.parseObject(searchHit.getSourceAsString(), ArticleDto.class);
			// 将高亮内容设置到返回结果
			if (null != map.get("title")) {
				Text[] texts =  map.get("title").getFragments();
				System.out.println(texts);
				String titleResult ="";
				for(Text text:texts){
					titleResult += text;
				}
				articleDto.setTitle(titleResult);
			}
			if (null != map.get("content")) {
				Text[] texts =  map.get("content").getFragments();
				System.out.println(texts);
				String contentResult ="";
				for(Text text:texts){
					contentResult += text;
				}
				articleDto.setContent(contentResult);
			}
			articleDtoList.add(articleDto);
		}
		return articleDtoList;
	}
}
