package com.exmek.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class IntegratedStepperMotor extends StepperMotor {

  // Additional fields are derived from Specs

  public static final String FIELD_NAME_FIELDBUS = "fieldbus";
  public static final String SPEC_NAME_FIELDBUS = "Fieldbus";

  public static final String FIELD_NAME_OPERATING_VOLTAGE = "operatingVoltage";
  public static final String SPEC_NAME_OPERATING_VOLTAGE = "Operating Voltage";

  public static final String FIELD_NAME_LOGIC_VOLTAGE = "logicVoltage";
  public static final String SPEC_NAME_LOGIC_VOLTAGE = "Logic Voltage";

  public static final String FIELD_NAME_ENCODER_RESOLUTION = "encoderResolution";
  public static final String SPEC_NAME_ENCODER_RESOLUTION = "Encoder Resolution";

  private String fieldbus;
  private String operatingVoltage;
  private String logicVoltage;
  private String encoderResolution;

}
