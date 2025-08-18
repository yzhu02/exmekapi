package com.exmek.core.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.exmek.core.persistence.entity.AbstractSeriesEntity;

@NoRepositoryBean
public interface BaseSeriesRepository<T extends AbstractSeriesEntity> extends PagingAndSortingRepository<T, Long> {
	
	Optional<T> findBySeries(String series);
	
	@Override
	List<T> findAll(Sort sort);
}
