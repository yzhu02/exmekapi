package com.exmek.core.persistence.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.exmek.core.persistence.projection.TimestampOfSeries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.exmek.core.persistence.entity.AbstractProductEntity;

public interface BaseProductRepository<T extends AbstractProductEntity> {

	Optional<T> findById(Long id);

	Optional<T> findByModel(String model);

	List<T> findAll(Specification<T> spec, Sort sort);
	
	Page<T> findAll(Specification<T> spec, Pageable pageable);


	List<T> findByModelContaining(String keyword);
	
	List<T> findByDescriptionContaining(String keyword);

  default List<TimestampOfSeries> findLastCreatedPerSeries() {
    // Should be implemented by the sub interface.
    // Have the default implementation to avoid generating queries for LightweightXXXRepository
    return null;
  }

  Date findLastCreatedBySeries(String series);
}
