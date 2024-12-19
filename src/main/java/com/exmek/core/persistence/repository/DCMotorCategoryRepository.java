package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.DCMotorCategoryEntity;

public interface DCMotorCategoryRepository 
extends BaseMotorCategoryRepository<DCMotorCategoryEntity>, JpaRepository<DCMotorCategoryEntity, Long>, JpaSpecificationExecutor<DCMotorCategoryEntity> {

}
