SET @series := 'MP028NB';
SET @model := 'MP028NB103';

INSERT INTO STEPPER_MOTOR  (
	CATEGORY,						-- STEPPER_STANDARD, STEPPER_FLAT, STEPPER_WITH_CONTROL
	SERIES,
	MODEL,						-- In case of STEPPER_LINEAR, it represents MODEL instead 
	NAME,
	DESCRIPTION, 
	
	FRAME_SIZE,
	FRAME_SIZE_UNIT,			-- mm, cm
	FRAME_SIZE_TYPE,			-- DIAMETER (φ), SIDE (□)
	NEMA_SIZE,
	
	LENGTH,
	LENGTH_UNIT,				-- mm, cm
	
	WEIGHT,
	WEIGHT_UNIT,				-- g
	
	RATED_VOLTAGE,
	RATED_VOLTAGE_UNIT,			-- V
	
	PHASE_CURRENT,
	PHASE_CURRENT_UNIT,			-- A
	
	PHASE_RESISTANCE,
	PHASE_RESISTANCE_UNIT,		-- ohm (Ω)
	
	PHASE_INDUCTANCE,
	PHASE_INDUCTANCE_UNIT,		-- mH
	
	HOLDING_TORQUE,
	HOLDING_TORQUE_UNIT,		-- Nm, Ncm
	
	DETENT_TORQUE,
	DETENT_TORQUE_UNIT,			-- Nm, Ncm
	
	STEP_ANGLE,
	STEP_ANGLE_UNIT,			-- DEG (°)
	
	MAX_THRUST,
	MAX_THRUST_UNIT				-- N
)
VALUES (
	'STEPPER_STANDARD',			-- CATEGORY						-- STEPPER_STANDARD, STEPPER_FLAT, STEPPER_WITH_CONTROL
	@series,					-- SERIES,
	@model,						-- MODEL					-- In case of STEPPER_LINEAR, it represents MODEL instead 
	'Standard Stepper Motor',		-- NAME
	NULL,						-- DESCRIPTION 
	
	28,							-- FRAME_SIZE
	'mm',						-- FRAME_SIZE_UNIT			-- mm, cm
	'□',						-- FRAME_SIZE_TYPE			-- DIAMETER (φ), SIDE (□)
	11,						-- NEMA_SIZE
	
	31.5,						-- LENGTH
	'mm',						-- LENGTH_UNIT				-- mm, cm
	
	100,							-- WEIGHT
	'g',						-- WEIGHT_UNIT				-- g
	
	NULL,						-- RATED_VOLTAGE
	NULL,						-- RATED_VOLTAGE_UNIT		-- V
	
	1.3,							-- PHASE_CURRENT
	'A',						-- PHASE_CURRENT_UNIT		-- A
	
	1.4,						-- PHASE_RESISTANCE
	'Ω',						-- PHASE_RESISTANCE_UNIT	-- ohm (Ω)
	
	1.1,						-- PHASE_INDUCTANCE
	'mH',						-- PHASE_INDUCTANCE_UNIT	-- mH
	
	6.5,						-- HOLDING_TORQUE
	'Ncm',						-- HOLDING_TORQUE_UNIT		-- Nm, Ncm
	
	0.8,						-- DETENT_TORQUE
	'Ncm',						-- DETENT_TORQUE_UNIT		-- Nm, Ncm
	
	1.8,						-- STEP_ANGLE
	'°',						-- STEP_ANGLE_UNIT			-- DEG (°)
	
	NULL,						-- MAX_THRUST
	NULL						-- MAX_THRUST_UNIT			-- N
);


INSERT INTO STEPPER_MOTOR_SPEC (MOTOR_ID, NAME, UNIT, VALUE)
SELECT ID, 'No. of Wires', NULL, '4' FROM STEPPER_MOTOR WHERE MODEL = @model
;

INSERT INTO STEPPER_MOTOR_SPEC (MOTOR_ID, NAME, UNIT, VALUE)
SELECT ID, 'Rotor Inertia', 'g.cm²', '9.5' FROM STEPPER_MOTOR WHERE MODEL = @model
;
