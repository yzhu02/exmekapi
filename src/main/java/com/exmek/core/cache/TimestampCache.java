package com.exmek.core.cache;

import com.exmek.core.persistence.entity.AbstractMotorEntity;
import com.exmek.core.persistence.entity.AbstractProductEntity;
import com.exmek.core.persistence.projection.TimestampOfCategory;
import com.exmek.core.persistence.projection.TimestampOfSeries;
import com.exmek.core.persistence.repository.BaseMotorRepository;
import com.exmek.core.persistence.repository.BaseProductRepository;
import com.exmek.core.scheduler.Scheduleable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TimestampCache implements Scheduleable  {

  private Map<Class<? extends AbstractMotorEntity>, Map<String, Date>> lastCreatedPerCategoryCache = new ConcurrentHashMap<>();

  private Map<Class<? extends AbstractMotorEntity>, Map<String, Map<String, Date>>> lastCreatedPerSeriesByCategoryCache = new ConcurrentHashMap<>();

  private Map<Class<? extends AbstractProductEntity>, Map<String, Date>> lastCreatedPerSeriesCache = new ConcurrentHashMap<>();

  @Override
  public void onSchedule() {
    log.info("TimestampCache is triggered...");
    lastCreatedPerCategoryCache.clear();
    lastCreatedPerSeriesByCategoryCache.clear();
    lastCreatedPerSeriesCache.clear();
    log.info("TimestampCache is cleared...");
  }

  public Map<String, Date> getLastCreatedPerCategoryMap(
      Class<? extends AbstractMotorEntity> entityClass, BaseMotorRepository<? extends AbstractMotorEntity> repository) {

    return lastCreatedPerCategoryCache.computeIfAbsent(entityClass, key -> initializeLastCreatedPerCategoryCache(repository));
  }

  private Map<String, Date> initializeLastCreatedPerCategoryCache(BaseMotorRepository<? extends AbstractMotorEntity> repository) {
    List<TimestampOfCategory> lastCreatedPerCategoryList = repository.findLastCreatedPerCategory();
    return Optional.ofNullable(lastCreatedPerCategoryList).stream()
        .flatMap(List::stream)
        .filter(toc -> toc.getTimestamp() != null)
        .collect(Collectors.toMap(TimestampOfCategory::getCategory, TimestampOfCategory::getTimestamp));
  }


  public Map<String, Date> getLastCreatedPerSeriesByCategoryMap(
      Class<? extends AbstractMotorEntity> entityClass, BaseMotorRepository<? extends AbstractMotorEntity> repository, String category) {

    Map<String, Map<String, Date>> lastCreatedPerSeriesByCategoryMap =
        lastCreatedPerSeriesByCategoryCache.computeIfAbsent(entityClass, key -> new HashMap<>());
    return lastCreatedPerSeriesByCategoryMap.computeIfAbsent(category, key -> initializeLastCreatedPerSeriesByCategoryCache(repository, category));
  }

  private Map<String, Date> initializeLastCreatedPerSeriesByCategoryCache(BaseMotorRepository<? extends AbstractMotorEntity> repository, String category) {
    List<TimestampOfSeries> lastCreatedPerSeriesList = repository.findLastCreatedPerSeriesByCategory(category);
    return Optional.ofNullable(lastCreatedPerSeriesList).stream()
        .flatMap(List::stream)
        .filter(tos -> tos.getTimestamp() != null)
        .collect(Collectors.toMap(TimestampOfSeries::getSeries, TimestampOfSeries::getTimestamp));
  }


  public Map<String, Date> getLastCreatedPerSeriesMap(
      Class<? extends AbstractProductEntity> entityClass, BaseProductRepository<? extends AbstractProductEntity> repository) {

    return lastCreatedPerSeriesCache.computeIfAbsent(entityClass, key -> initializeLastCreatedPerSeriesCache(repository));
  }

  private Map<String, Date> initializeLastCreatedPerSeriesCache(BaseProductRepository<? extends AbstractProductEntity> repository) {
    List<TimestampOfSeries> lastCreatedPerSeriesList = repository.findLastCreatedPerSeries();
    return Optional.ofNullable(lastCreatedPerSeriesList).stream()
        .flatMap(List::stream)
        .filter(tos -> tos.getTimestamp() != null)
        .collect(Collectors.toMap(TimestampOfSeries::getSeries, TimestampOfSeries::getTimestamp));
  }

}
