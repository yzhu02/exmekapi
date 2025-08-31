package com.exmek.core.persistence.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PLANETARY_GEARBOX")
@Access(AccessType.FIELD)
public class PlanetaryGearboxEntity extends AbstractPlanetaryGearboxEntity {
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERIES", referencedColumnName = "SERIES", nullable = false, insertable = false, updatable = false)
    private GearboxSeriesEntity productSeries;
	
	public GearboxSeriesEntity getProductSeries() {
		return productSeries;
	}

	public void setProductSeries(GearboxSeriesEntity productSeries) {
		this.productSeries = productSeries;
	}

}
