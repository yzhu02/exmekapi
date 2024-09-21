package com.exmek.core.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.ConfigEntity;

public interface ConfigRepository extends JpaRepository<ConfigEntity, Long>, JpaSpecificationExecutor<ConfigEntity> {

	Optional<ConfigEntity> findByName(String name);
}
