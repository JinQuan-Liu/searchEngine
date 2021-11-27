package com.stark.searchengine.controller;

import com.stark.searchengine.dto.ArticleDto;
import com.stark.searchengine.service.ArticleService;
import com.stark.searchengine.vo.ArticleSaveVo;
import com.stark.searchengine.vo.ArticleSearchVo;
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
	public String save(@RequestBody ArticleSaveVo articleSaveVo) {
		return articleService.save(articleSaveVo);
	}

	@RequestMapping(value = "fuzzySearch", method = RequestMethod.POST)
	public List<ArticleDto> fuzzySearch(@RequestBody ArticleSearchVo inputContent) throws IOException {
		List<ArticleDto> result = articleService.fuzzySearch(inputContent);
		return result;
	}

	@RequestMapping(value = "searchAll", method = RequestMethod.GET)
	public List<ArticleDto> searchAll() throws IOException {
		List<ArticleDto> result = articleService.searchAll();
		return result;
	}
}
