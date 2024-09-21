package com.exmek.core.persistence.entity;

import java.math.BigDecimal;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "LEAD_DEF")
@Access(AccessType.FIELD)
public class LeadDefEntity extends AbstractManagableEntity {

	@Column(name = "CODE")
	private String code;
	
	@Column(name = "SCREW_DIAMETER_INCH")
	private BigDecimal screwDiameterInch;
		
	@Column(name = "SCREW_DIAMETER_MM")
	private BigDecimal screwDiameterMM;

	@Column(name = "LEAD_INCH")
	private BigDecimal leadInch;
		
	@Column(name = "LEAD_MM")
	private BigDecimal leadMM;
	
	@Column(name = "THREADS")
	private Integer threads;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getScrewDiameterInch() {
		return screwDiameterInch;
	}

	public void setScrewDiameterInch(BigDecimal screwDiameterInch) {
		this.screwDiameterInch = screwDiameterInch;
	}

	public BigDecimal getScrewDiameterMM() {
		return screwDiameterMM;
	}

	public void setScrewDiameterMM(BigDecimal screwDiameterMM) {
		this.screwDiameterMM = screwDiameterMM;
	}

	public BigDecimal getLeadInch() {
		return leadInch;
	}

	public void setLeadInch(BigDecimal leadInch) {
		this.leadInch = leadInch;
	}

	public BigDecimal getLeadMM() {
		return leadMM;
	}

	public void setLeadMM(BigDecimal leadMM) {
		this.leadMM = leadMM;
	}

	public Integer getThreads() {
		return threads;
	}

	public void setThreads(Integer threads) {
		this.threads = threads;
	}

}
