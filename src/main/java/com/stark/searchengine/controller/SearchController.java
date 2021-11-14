package com.stark.searchengine.controller;

import com.stark.searchengine.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("search")
@RestController
public class SearchController {

	@Autowired
	private ElasticSearchService esSerivce;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String test(@PathVariable String id) throws IOException {
		return esSerivce.searchById(id);
	}
}
