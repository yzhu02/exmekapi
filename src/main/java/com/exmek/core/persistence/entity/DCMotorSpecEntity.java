package com.exmek.core.persistence.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "DC_MOTOR_SPEC")
@Access(AccessType.FIELD)
public class DCMotorSpecEntity extends AbstractMotorSpecEntity {
}
