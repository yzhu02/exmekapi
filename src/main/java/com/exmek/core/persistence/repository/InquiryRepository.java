package com.exmek.core.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.exmek.core.persistence.entity.InquiryEntity;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Long>, JpaSpecificationExecutor<InquiryEntity> {

}
