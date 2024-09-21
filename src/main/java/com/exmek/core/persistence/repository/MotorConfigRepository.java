package com.exmek.core.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.MotorConfigEntity;

public interface MotorConfigRepository extends JpaRepository<MotorConfigEntity, Long>, JpaSpecificationExecutor<MotorConfigEntity> {

	Optional<MotorConfigEntity> findByModelRef(String modelRef);
}
