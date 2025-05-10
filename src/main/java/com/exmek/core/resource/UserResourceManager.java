package com.exmek.core.resource;

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

import org.springframework.stereotype.Component;

import com.exmek.commons.utils.JsonMapperUtils;
import com.exmek.commons.utils.UrlUtils;
import com.exmek.core.model.News;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserResourceManager {

	public static final String SYS_PROP_NAME_USER_RESOURCES_LOCATION = "user.resources.location";

	private static final String NEWS_FILE_NAME				= "news.json";
	
	public static final String NEWSREPO						= "newsrepo";
	public static final String COMMON_TECHDOCS				= "techdocs";
	
	public static final String NEWSREPO_BASE_PATH			= "/" + NEWSREPO;
	public static final String COMMON_TECHDOCS_BASE_PATH	= "/" + COMMON_TECHDOCS;

	public static final String RESOURCE_BASE_LOCATION		= System.getProperty(SYS_PROP_NAME_USER_RESOURCES_LOCATION);
	
	public static final String NEWSREPO_LOCATION			= RESOURCE_BASE_LOCATION + File.separator + NEWSREPO + File.separator;
	public static final String COMMON_TECHDOCS_LOCATION		= RESOURCE_BASE_LOCATION + File.separator + COMMON_TECHDOCS + File.separator;

	public List<News> getAllNews() {
		return findNewsBy(null, false);
	}

	private List<News> findNewsBy(Predicate<News> filter, boolean isSingle) {
		File newsRepoDir = new File(NEWSREPO_LOCATION);
		log.info("Loading news from {} ...", NEWSREPO_LOCATION);
		if (!newsRepoDir.exists()) {
			log.warn("Can't load news as the newsRepo location {} doesn't exist. ", newsRepoDir);
			return null;
		}
		List<News> newsList = new ArrayList<>();
		File[] subdirs = newsRepoDir.listFiles();
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
					.map(name -> UrlUtils.concatURL(NEWSREPO_BASE_PATH, sub.getName(), name))
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

	public News getNewsByIdOrTitle(String idOrTitle) {
		List<News> newsList = findNewsBy(n -> Objects.equals(n.getId(), idOrTitle) || Objects.equals(n.getTitle(), idOrTitle), true);
		if (newsList != null && newsList.size() > 0) {
			return newsList.get(0);
		} else {
			return null;
		}
	}

	public List<ResourceInfo> getCommonTechDocInfos() {
		File commonTechDocsDir = new File(COMMON_TECHDOCS_LOCATION);
		log.info("Loading common techdocs from {} ...", COMMON_TECHDOCS_LOCATION);
		if (!commonTechDocsDir.exists()) {
			log.warn("Can't load common techDocs as the techDocs location {} doesn't exist. ", commonTechDocsDir);
			return null;
		}
		File[] commonTechDocPdfFiles = commonTechDocsDir.listFiles((dir, filename) -> filename.toLowerCase().endsWith(".pdf"));
		return Arrays.stream(commonTechDocPdfFiles)
				.map(f -> ResourceInfo.builder().name(f.getName()).path(COMMON_TECHDOCS_BASE_PATH + "/" + f.getName()).size(f.length()).build())
				.toList();
	}

}
