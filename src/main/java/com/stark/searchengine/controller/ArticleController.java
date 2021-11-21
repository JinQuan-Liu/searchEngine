package com.stark.searchengine.controller;

import com.stark.searchengine.dto.ArticleDto;
import com.stark.searchengine.service.ArticleService;
import com.stark.searchengine.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("article")
@RestController
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(@RequestParam("title") String title, @RequestParam("content") String content) {
		return;
	}

	@RequestMapping(value = "fuzzySearch", method = RequestMethod.POST)
	public List<ArticleDto> fuzzySearch(@RequestBody ArticleVo inputContent) throws IOException {
		List<ArticleDto> result = articleService.fuzzySearch(inputContent);
		return result;
	}
}
