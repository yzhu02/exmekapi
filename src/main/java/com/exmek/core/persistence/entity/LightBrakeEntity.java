package com.exmek.core.persistence.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "BRAKE")
@Access(AccessType.FIELD)
public class LightBrakeEntity extends AbstractBrakeEntity {

}
