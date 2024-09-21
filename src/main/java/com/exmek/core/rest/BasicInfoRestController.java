package com.exmek.core.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exmek.core.config.Configuration;
import com.exmek.core.model.Company;
import com.exmek.core.model.News;
import com.exmek.core.news.NewsRepo;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class BasicInfoRestController {

//	private static final Logger logger = LoggerFactory.getLogger(BasicInfoRestController.class);
		
	@Autowired
	private Configuration configuration; 

	@Autowired
	private NewsRepo newsRepo;
	
	@GetMapping("/company/exmek")
	public Company getExmekCompany() {
		return configuration.getExmekCompany();
	}
		
	@GetMapping("/news/{idOrTitle}")
	public News getNews(@NotNull @PathVariable("idOrTitle") String idOrTitle) {
		if (idOrTitle == null || idOrTitle.isBlank()) {
			return null;
		}
		return newsRepo.loadNewsByIdOrTitle(idOrTitle);
	}
	
	@GetMapping("/news")
	public List<News> getAllNews() {
		return newsRepo.loadAllNews();
	}
}
