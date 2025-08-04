package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.DownloadTrackingEntity;

public interface DownloadTrackingRepository extends JpaRepository<DownloadTrackingEntity, Long>, JpaSpecificationExecutor<DownloadTrackingEntity> {

}
