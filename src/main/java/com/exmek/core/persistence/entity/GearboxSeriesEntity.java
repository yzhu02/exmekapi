package com.exmek.core.persistence.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "GEARBOX_SERIES")
@Access(AccessType.FIELD)
public class GearboxSeriesEntity extends AbstractSeriesEntity {

}
