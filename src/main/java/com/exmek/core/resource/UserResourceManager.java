package com.exmek.core.resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.exmek.commons.utils.JsonMapperUtils;
import com.exmek.commons.utils.UrlUtils;
import com.exmek.core.model.News;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserResourceManager extends AbstractResourceManager {

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
					.map(name -> UrlUtils.concatURL(NEWSREPO_BASE_PATH, sub.getName(), UrlUtils.encodeBrackets(name)))
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
		File[] commonTechDocPdfFiles = commonTechDocsDir.listFiles((dir, filename) -> isPdfOrZip(filename));
		return Arrays.stream(commonTechDocPdfFiles)
				.map(f -> ResourceInfo.builder()
						.name(f.getName())
						.path(UrlUtils.concatURL(COMMON_TECHDOCS_BASE_PATH, UrlUtils.encodeBrackets(f.getName())))
						.size(f.length())
						.build()
				)
				.toList();
	}

	@Override
	public List<ResourceInfo> getTechDocInfos() {
		List<ResourceInfo> totalTechDocInfos = new ArrayList<>();
		List<ResourceInfo> motorTechDocInfos = getTechDocInfosOfProductDir(DIR_NAME_MOTOR);
		if (CollectionUtils.isNotEmpty(motorTechDocInfos)) {
			totalTechDocInfos.addAll(motorTechDocInfos);
		}
		List<ResourceInfo> gearboxTechDocInfos = getTechDocInfosOfProductDir(DIR_NAME_GEARBOX);
		if (CollectionUtils.isNotEmpty(gearboxTechDocInfos)) {
			totalTechDocInfos.addAll(gearboxTechDocInfos);
		}
		List<ResourceInfo> brakeTechDocInfos = getTechDocInfosOfProductDir(DIR_NAME_BRAKE);
		if (CollectionUtils.isNotEmpty(brakeTechDocInfos)) {
			totalTechDocInfos.addAll(brakeTechDocInfos);
		}
		return totalTechDocInfos;
    }
	
	private List<ResourceInfo> getTechDocInfosOfProductDir(String productDirName) {
		String relResPath = UrlUtils.concatURL("/", DIR_NAME_MATERIALS, productDirName, DIR_NAME_TECHDOC);
		String resLocation = RESOURCE_BASE_LOCATION + 
				File.separator + DIR_NAME_MATERIALS + 
				File.separator + productDirName + 
				File.separator + DIR_NAME_TECHDOC;
		File resDir = new File(resLocation);
		if (resDir.exists()) {
			log.info("Loading techdoc resources from {} ...", resLocation);
			File[] resFiles = resDir.listFiles(f -> isPdfOrZip(f.getName()));
			if (resFiles != null && resFiles.length > 0) {
				return Arrays.stream(resFiles)
				.map(f -> ResourceInfo.builder()
						.name(f.getName())
						.path(UrlUtils.concatURL(relResPath, UrlUtils.encodeBrackets(f.getName())))
						.size(f.length())
						.build()
				)
				.collect(Collectors.toList());
			}
		}
		return null;
	}

	private boolean isPdfOrZip(String filename) {
		if (filename == null) {
			return false;
		}
		filename = filename.toLowerCase();
		return filename.endsWith(".pdf") || filename.endsWith(".zip");
	}

	private List<String> getResourcePaths(String model, String baseDirName, String productDirName, String resSubCatDirName, 
			String series) {

		List<String> resPaths = getResourcePaths(model, baseDirName, productDirName, resSubCatDirName);
		if (series == null) {
			return resPaths;
		}
		List<String> bySeriesResPaths = getResourcePaths(series, baseDirName, productDirName, resSubCatDirName);
		if (CollectionUtils.isEmpty(resPaths)) {
			return bySeriesResPaths;
		}
		if (CollectionUtils.isEmpty(bySeriesResPaths)) {
			return resPaths;
		}
		Set<String> resExtensionSet = resPaths.stream()
				.map(p -> getFileNameExtension(p))
				.collect(Collectors.toSet());
		for (String bySeriesResPath : bySeriesResPaths) {
			if (!resExtensionSet.contains(getFileNameExtension(bySeriesResPath))) {
				// If it contains model specific file like MB057GA100.stl and 
				// series specific file MB057GA.stl then only model specific file will be returned.
				// Only if the series specific file has different extension like MB057GA.zip then both will be returned.
				resPaths.add(bySeriesResPath);
			}
		}
		return resPaths;
	}

	private List<String> getResourcePaths(String modelOrSeries, String baseDirName, String productDirName, String resSubCatDirName) {

		File[] resFiles = null;

		String relResPath = UrlUtils.concatURL("/", baseDirName, productDirName, modelOrSeries, resSubCatDirName);
		String resLocation = RESOURCE_BASE_LOCATION + 
				File.separator + baseDirName + 
				File.separator + productDirName + 
				File.separator + modelOrSeries + 
				File.separator + resSubCatDirName;
		File resDir = new File(resLocation);
		if (resDir.exists()) {
			log.info("Loading resource for {} from {} ...", modelOrSeries, resLocation);
			resFiles = resDir.listFiles();
		} else {
			relResPath = UrlUtils.concatURL("/", baseDirName, productDirName, resSubCatDirName);
			resLocation = RESOURCE_BASE_LOCATION + 
					File.separator + baseDirName + 
					File.separator + productDirName + 
					File.separator + resSubCatDirName;
			resDir = new File(resLocation);
			if (resDir.exists()) {
				log.info("Loading resource for {} from {} ...", modelOrSeries, resLocation);
				resFiles = resDir.listFiles(f -> isMatchingResourceFile(f, modelOrSeries));
			} else {
				log.warn("Can't load resources as the location {} doesn't exist. ", resLocation);
				return null;
			}
		}
		
		if (resFiles == null || resFiles.length == 0) {
			log.warn("No resource loaded for {} from {} ", modelOrSeries, resLocation);
			return null;
		}
		if (resFiles.length > 1) {
			Arrays.sort(resFiles, (f1, f2) -> {
				return f1.getName().compareTo(f2.getName());
			});
		}
		String resParentPath = relResPath;
		return Arrays.stream(resFiles)
				.map(f -> UrlUtils.concatURL(resParentPath, UrlUtils.encodeBrackets(f.getName())))
				.collect(Collectors.toList());
	}
	
	private Map<String, List<String>> getIndexedResourcePaths(String model, String baseDirName, String productDirName, String resSubCatDirName, 
			String series) {

		Map<String, List<String>> indexedResPaths = getIndexedResourcePaths(model, baseDirName, productDirName, resSubCatDirName);
		if (series == null) {
			return indexedResPaths;
		}
		Map<String, List<String>> bySeriesIndexedResPaths = getIndexedResourcePaths(series, baseDirName, productDirName, resSubCatDirName);
		if (ObjectUtils.isEmpty(indexedResPaths)) {
			return bySeriesIndexedResPaths;
		}
		if (ObjectUtils.isEmpty(bySeriesIndexedResPaths)) {
			return indexedResPaths;
		}
		for (Map.Entry<String, List<String>> entry : indexedResPaths.entrySet()) {
			List<String> resPaths = entry.getValue();
			Set<String> resExtensionSet = resPaths.stream()
					.map(p -> getFileNameExtension(p))
					.collect(Collectors.toSet());
			List<String> bySeriesResPaths = bySeriesIndexedResPaths.get(entry.getKey());
			for (String bySeriesResPath : bySeriesResPaths) {
				if (!resExtensionSet.contains(getFileNameExtension(bySeriesResPath))) {
					// If it contains model specific file like MPC023-[Implication For Name].jpg and 
					// series specific file MPC023[Implication For Name].jpg then only model specific file will be returned.
					// Only if the series specific file has different extension like MPC023[Implication For Name].png then both will be returned.
					resPaths.add(bySeriesResPath);
				}
			}
		}
		return indexedResPaths;
	}

	private Map<String, List<String>> getIndexedResourcePaths(String modelOrSeries, String baseDirName, String productDirName, String resSubCatDirName) {

		File[] resFiles = null;

		String relResPath = UrlUtils.concatURL("/", baseDirName, productDirName, modelOrSeries, resSubCatDirName);
		String resLocation = RESOURCE_BASE_LOCATION + 
				File.separator + baseDirName + 
				File.separator + productDirName + 
				File.separator + modelOrSeries + 
				File.separator + resSubCatDirName;
		File resDir = new File(resLocation);
		if (resDir.exists()) {
			log.info("Loading indexed resource for {} from {} ...", modelOrSeries, resLocation);
			resFiles = resDir.listFiles();
		} else {
			relResPath = UrlUtils.concatURL("/", baseDirName, productDirName, resSubCatDirName);
			resLocation = RESOURCE_BASE_LOCATION + 
					File.separator + baseDirName + 
					File.separator + productDirName + 
					File.separator + resSubCatDirName;
			resDir = new File(resLocation);
			if (resDir.exists()) {
				log.info("Loading indexed resource for {} from {} ...", modelOrSeries, resLocation);
				resFiles = resDir.listFiles(f -> isMatchingResourceFile(f, modelOrSeries));
			} else {
				log.warn("Can't load indexed resources as the location {} doesn't exist. ", resLocation);
				return null;
			}
		}
		
		if (resFiles == null || resFiles.length == 0) {
			log.warn("No indexed resource loaded for {} from {} ", modelOrSeries, resLocation);
			return null;
		}
		if (resFiles.length > 1) {
			Arrays.sort(resFiles, (f1, f2) -> {
				return f1.getName().compareTo(f2.getName());
			});
		}
		Map<String, List<String>> indexedResPaths = new HashMap<>();
		String resParentPath = relResPath;
		for (File file : resFiles) {
			String filename = file.getName();
			int dotInx = filename.lastIndexOf('.');
			String indexName = "";
			if (dotInx > 0) {
				String resName = filename.substring(0, dotInx);
				Pattern p = Pattern.compile(INDEXED_RESOURCE_FILENAME_REGEX);
				Matcher m = p.matcher(resName);
				if (m.matches()) {
					indexName = m.group(3);
				}
			}
			List<String> resPaths = indexedResPaths.get(indexName);
			if (resPaths == null) {
				resPaths = new ArrayList<>();
				indexedResPaths.put(indexName, resPaths);
			}
			resPaths.add(UrlUtils.concatURL(resParentPath, UrlUtils.encodeBrackets(file.getName())));
		}
		return indexedResPaths;
	}
	
	private String getFileNameExtension(String filePath) {
		if (filePath == null) {
			return null;
		}
		int dotInx = filePath.lastIndexOf('.');
		if (dotInx >= 0) {
			return filePath.substring(dotInx + 1);
		} else {
			return "";
		}
	}

	private boolean isMatchingResourceFile(File file, String modelOrSeries) {
		String filename = file.getName();
		String resName = filename;
		int dotInx = resName.indexOf('.');
		if (dotInx >= 0) {
			resName = resName.substring(0, dotInx);
			int bracketInx = resName.indexOf('[');
			if (bracketInx >= 0) {
				resName = resName.substring(0, bracketInx);
			}
		}
		return resName.equals(modelOrSeries);
	}

	@Override
	public List<String> getMotorMechanicalImagePaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_MOTOR, DIR_NAME_MICHANICAL, series);
	}

	@Override
	public List<String> getGearboxMechanicalImagePaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_GEARBOX, DIR_NAME_MICHANICAL, series);
	}

	@Override
	public List<String> getBrakeMechanicalImagePaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_BRAKE, DIR_NAME_MICHANICAL, series);
	}
	
	@Override
	public List<String> getMotor3DDrawingPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_MOTOR, DIR_NAME_3D, series);
	}

	@Override
	public List<String> getGearbox3DDrawingPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_GEARBOX, DIR_NAME_3D, series);
	}
	
	@Override
	public List<String> getBrake3DDrawingPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_BRAKE, DIR_NAME_3D, series);
	}

	@Override
	public List<String> getMotorTechDocPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_MOTOR, DIR_NAME_TECHDOC, series);
	}
	
	@Override
	public List<String> getGearboxTechDocPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_GEARBOX, DIR_NAME_TECHDOC, series);
	}
	
	@Override
	public List<String> getBrakeTechDocPaths(String model, String series) {
		return getResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_BRAKE, DIR_NAME_TECHDOC, series);
	}

	@Override
	public Map<String, List<String>> getMotorAdditionalImagePaths(String model, String series) {
		return getIndexedResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_MOTOR, DIR_NAME_ADDITIONAL_IMAGES, series);
	}
	
	@Override
	public Map<String, List<String>> getGearboxAdditionalImagePaths(String model, String series) {
		return getIndexedResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_GEARBOX, DIR_NAME_ADDITIONAL_IMAGES, series);
	}
	
	@Override
	public Map<String, List<String>> getBrakeAdditionalImagePaths(String model, String series) {
		return getIndexedResourcePaths(model, DIR_NAME_MATERIALS, DIR_NAME_BRAKE, DIR_NAME_ADDITIONAL_IMAGES, series);
	}
}
