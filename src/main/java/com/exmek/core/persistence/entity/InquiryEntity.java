package com.exmek.core.persistence.entity;

import com.exmek.core.commons.enums.InquiryStatus;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "INQUIRY")
@Access(AccessType.FIELD)
public class InquiryEntity extends AbstractManagableEntity {

	@Column(name = "CONTACT_NAME")
	private String contactName;
	
	@Column(name = "CONTACT_EMAIL")
	private String contactEmail;
	
	@Column(name = "CONTACT_PHONE")
	private String contactPhone;
	
	@Column(name = "REF_MODEL")
	private String refModel;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "REF_LINK")
	private String refLink;

	@Column(name = "CLIENT_IP_ADDRESS")
	private String clientIpAddress;
	
	@Column(name = "CLIENT_COUNTRY_OR_REGION")
	private String clientCountryOrRegion;
		
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private InquiryStatus status;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getRefModel() {
		return refModel;
	}

	public void setRefModel(String refModel) {
		this.refModel = refModel;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRefLink() {
		return refLink;
	}

	public void setRefLink(String refLink) {
		this.refLink = refLink;
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}

	public String getClientCountryOrRegion() {
		return clientCountryOrRegion;
	}

	public void setClientCountryOrRegion(String clientCountryOrRegion) {
		this.clientCountryOrRegion = clientCountryOrRegion;
	}

	public InquiryStatus getStatus() {
		return status;
	}

	public void setStatus(InquiryStatus status) {
		this.status = status;
	}

}
