package com.exmek.core.mapper;

import org.springframework.stereotype.Component;

import com.exmek.core.persistence.entity.DownloadTrackingEntity;
import com.exmek.core.rest.DownloadTrackingRequest;

@Component
public class DownloadTrackingMapper {


	public DownloadTrackingEntity mapDownloadTrackingRequestToEntity(DownloadTrackingRequest request) {
		DownloadTrackingEntity entity = new DownloadTrackingEntity();
		entity.setDownloadLink(request.getDownloadLink());
		entity.setContactEmail(request.getContactEmail());
		entity.setContactName(request.getContactName());
		entity.setContactPhone(request.getContactPhone());
		entity.setCompany(request.getCompany());
		return entity;
	}
}
