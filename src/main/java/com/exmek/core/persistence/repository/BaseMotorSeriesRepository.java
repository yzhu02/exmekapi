package com.exmek.core.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import com.exmek.core.persistence.entity.AbstractMotorSeriesEntity;

@NoRepositoryBean
public interface BaseMotorSeriesRepository<T extends AbstractMotorSeriesEntity> extends BaseSeriesRepository<T> {

	List<T> findAllByCategory(String category, Sort sort);
	
	Page<T> findAllByCategory(String category, Pageable pageable);
}
