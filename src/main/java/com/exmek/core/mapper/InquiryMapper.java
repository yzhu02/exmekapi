package com.exmek.core.mapper;

import org.springframework.stereotype.Component;

import com.exmek.core.model.Inquiry;
import com.exmek.core.persistence.entity.InquiryEntity;
import com.exmek.core.rest.InquiryRequest;

@Component
public class InquiryMapper {


	public InquiryEntity mapInquiryToEntity(InquiryRequest.Inquiry inquiry) {
		InquiryEntity entity = new InquiryEntity();
		entity.setContactName(inquiry.getContactName());
		entity.setContactEmail(inquiry.getContactEmail());
		entity.setContactPhone(inquiry.getContactPhone());
		entity.setRefModel(inquiry.getRefModel());
		entity.setQuantity(inquiry.getQuantity());
		entity.setMessage(inquiry.getMessage());
		entity.setRefLink(inquiry.getRefLink());
		return entity;
	}

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
		model.setMessage(entity.getMessage());
		model.setRefLink(entity.getRefLink());
		model.setClientIpAddress(entity.getClientIpAddress());
		model.setClientCountryOrRegion(entity.getClientCountryOrRegion());
		model.setStatus(entity.getStatus());
		return model;
	}
}
