package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.DCMotorEntity;

public interface LightDCMotorRepository extends BaseProductRepository<DCMotorEntity.Light>, JpaRepository<DCMotorEntity.Light, Long>, JpaSpecificationExecutor<DCMotorEntity.Light> {
}
