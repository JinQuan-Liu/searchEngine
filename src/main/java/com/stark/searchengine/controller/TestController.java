package com.stark.searchengine.controller;

import com.stark.searchengine.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class TestController {
	@Autowired
	private ElasticSearchService elasticSearchService;

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		return elasticSearchService.query();
	}
}
