SET @series := 'MPC';
SET @model  := 'MPC024-24-001';

INSERT INTO BRAKE (
	SERIES,
	MODEL,
	NAME,
	DESCRIPTION,

	FRAME_SIZE,
	FRAME_SIZE_UNIT,			-- mm, cm
	FRAME_SIZE_TYPE,			-- DIAMETER (φ), SIDE (□)
	NEMA_SIZE,
		
	LENGTH,
	LENGTH_UNIT,					-- mm, cm
	
	WEIGHT,
	WEIGHT_UNIT,				-- g
	
	RATED_VOLTAGE,
	RATED_VOLTAGE_UNIT,			-- V
	
	RESISTANCE,
	RESISTANCE_UNIT,			-- ohm (Ω)
	
	CURRENT,
	CURRENT_UNIT,				-- A

	STATIC_TORQUE,
	STATIC_TORQUE_UNIT,			-- Nm
	
	RATED_POWER,
	RATED_POWER_UNIT,			-- W
	
	START_VOLTAGE,
	START_VOLTAGE_UNIT			-- V
)
VALUES (
	@series,					-- SERIES,
	@model,						-- MODEL,
	'Brake',					-- NAME,
	NULL,						-- DESCRIPTION,

	60,							-- FRAME_SIZE,
	'mm',						-- FRAME_SIZE_UNIT,			-- mm, cm
	'□',						-- FRAME_SIZE_TYPE,			-- DIAMETER (φ), SIDE (□)
	24,							-- NEMA_SIZE,
		
	62,							-- LENGTH,
	'mm',						-- LENGTH_UNIT,					-- mm, cm
	
	540,						-- WEIGHT,
	'g',						-- WEIGHT_UNIT,				-- g
	
	24,							-- RATED_VOLTAGE,
	'V',						-- RATED_VOLTAGE_UNIT,			-- V
	
	53,							-- RESISTANCE,
	'Ω',						-- RESISTANCE_UNIT,			-- ohm (Ω)
	
	0.46,						-- CURRENT,
	'A',						-- CURRENT_UNIT,				-- A

	1.5,						-- STATIC_TORQUE,
	'Nm',						-- STATIC_TORQUE_UNIT,			-- Nm
	
	11,							-- RATED_POWER,
	'W',						-- RATED_POWER_UNIT,			-- W
	
	22,							-- START_VOLTAGE,
	'V'							-- START_VOLTAGE_UNIT			-- V
);
