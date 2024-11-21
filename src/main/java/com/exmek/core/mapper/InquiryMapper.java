package com.exmek.core.mapper;

import org.springframework.stereotype.Component;

import com.exmek.core.model.Inquiry;
import com.exmek.core.persistence.entity.InquiryEntity;

@Component
public class InquiryMapper {

	public Inquiry mapInquiryToModel(InquiryEntity entity) {
		if (entity == null) {
			return null;
		}
		Inquiry model = new Inquiry();
//		model.setId(entity.getId());
		model.setContactName(entity.getContactName());
		model.setContactEmail(entity.getContactEmail());
		model.setContactPhone(entity.getContactPhone());
		model.setRefModel(entity.getRefModel());
		model.setQuantity(entity.getQuantity());
		model.setContent(entity.getContent());
		model.setRefLink(entity.getRefLink());
		model.setClientIpAddress(entity.getClientIpAddress());
		model.setClientHost(entity.getClientHost());
		model.setClientCountryOrRegion(entity.getClientCountryOrRegion());
		model.setStatus(entity.getStatus());
		return model;
	}
}
