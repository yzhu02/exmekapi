package com.exmek.core.news;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.exmek.commons.utils.JsonMapperUtils;
import com.exmek.commons.utils.UrlUtils;
import com.exmek.core.model.News;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NewsRepo {

	public static final String NEWSREPO_NAME	= "newsrepo";
	
	public static final String NEWS_FILE_NAME	= "news.json";

	@Autowired
	private ResourceLoader resourceLoader;

	public List<News> loadAllNews() {
		return loadNewsBy(null, false);
	}
		
	public News loadNewsByIdOrTitle(String idOrTitle) {
		List<News> newsList = loadNewsBy(n -> Objects.equals(n.getId(), idOrTitle) || Objects.equals(n.getTitle(), idOrTitle), true);
		if (newsList != null && newsList.size() > 0) {
			return newsList.get(0);
		} else {
			return null;
		}
	}

	private List<News> loadNewsBy(Predicate<News> filter, boolean isSingle) {
		String userDir = System.getProperty("user.dir");
		String newsrepoLocation = userDir + File.separator + NEWSREPO_NAME;
		File newsrepoDir = new File(newsrepoLocation);
		log.info("Loading news from {} ", newsrepoLocation);
		if (!newsrepoDir.exists()) {
			log.warn("newsrepoLocation: {} doesn't exist. ", newsrepoLocation);
			Resource resource = resourceLoader.getResource("classpath:" + NEWSREPO_NAME);
			if (!resource.exists()) {
				return null;
			}
			try {
				newsrepoDir = resource.getFile();
			} catch (Exception e) {
				log.error("Unable to load news repo directory from newsrepo ", e);
			}
		}
		if (newsrepoDir == null) {
			return null;
		}
		List<News> newsList = new ArrayList<>();
		File[] subdirs = newsrepoDir.listFiles();
		for (File sub : subdirs) {
			if (!sub.isDirectory()) {
				continue;
			}
			File[] newsFiles = sub.listFiles((dir, filename) -> NEWS_FILE_NAME.equals(filename));
			if (newsFiles.length <= 0) {
				continue;
			}
			File newsFile = newsFiles[0];
			News aNews = null;
			try {
				String newsRecordStr = Files.readString(newsFile.toPath());
				aNews = JsonMapperUtils.readValue(newsRecordStr, new TypeReference<News>() {});
			} catch (IOException e) {
				log.error("Unable to read news file at {} ", newsFile.getPath(), e);
			}
			if (aNews == null) {
				continue;
			}
			aNews.setId(sub.getName());
			File[] picFiles = sub.listFiles((dir, filename) -> filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".png"));
			aNews.setPicturePaths(StreamSupport.stream(Arrays.spliterator(picFiles), false)
					.map(f -> f.getName())
					.sorted()
					.map(name -> UrlUtils.concatURL("/", NEWSREPO_NAME, sub.getName(), name))
					.collect(Collectors.toList()));
			if (filter == null || filter.test(aNews)) {
				newsList.add(aNews);
				if (isSingle) {
					break;
				}
			}
		}
		if (newsList.size() > 1) {
			Collections.sort(newsList, (n1, n2) -> {
				String pt1 = n1.getPublishTime();
				String pt2 = n2.getPublishTime();
				if (pt1 == null && pt2 == null) {
					return 0;
				}
				if (pt1 == null) {
					return 1;
				} else if (pt2 == null) {
					return -1;
				} else {
					return pt2.compareTo(pt1);
				}
			});
		}
		return newsList;
	}
}
