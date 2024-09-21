SET @series := 'MPC';
SET @model  := 'MPC023-';

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

	57,							-- FRAME_SIZE,
	'mm',						-- FRAME_SIZE_UNIT,			-- mm, cm
	'□',						-- FRAME_SIZE_TYPE,			-- DIAMETER (φ), SIDE (□)
	23,							-- NEMA_SIZE,
		
	48,							-- LENGTH,
	'mm',						-- LENGTH_UNIT,					-- mm, cm
	
	400,						-- WEIGHT,
	'g',						-- WEIGHT_UNIT,				-- g
	
	24,							-- RATED_VOLTAGE,
	'V',						-- RATED_VOLTAGE_UNIT,			-- V
	
	92,						-- RESISTANCE,
	'Ω',						-- RESISTANCE_UNIT,			-- ohm (Ω)
	
	0.26,						-- CURRENT,
	'A',						-- CURRENT_UNIT,				-- A

	0.5,							-- STATIC_TORQUE,
	'Nm',						-- STATIC_TORQUE_UNIT,			-- Nm
	
	6.2,							-- RATED_POWER,
	'W',						-- RATED_POWER_UNIT,			-- W
	
	15,							-- START_VOLTAGE,
	'V'							-- START_VOLTAGE_UNIT			-- V
);
