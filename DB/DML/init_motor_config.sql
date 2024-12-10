---- BLDC Motor config begin ----

-- BLDC_INTERNAL_ROTOR config begin --
INSERT INTO MOTOR_CONFIG (
	MODEL_REFS, CONFIG_NAME, CONFIG_VALUE
)
VALUES 
(
	'MB057GA*', 'curve.coordinates', '[{"name": "Tcont", "x": "Speed(Krpm)", "y": "Torque(oz-in)"}, {"name": "Tpeak", "x": "Speed(Krpm)", "y": "Torque(Ncm)"}]'
),
(
	'MB059AH*', 'curve.coordinates', '[{"name": "", "x": "Torque(Nm)", "y": "Speed(rpm)"}, {"name": "", "x": "Torque(Nm)", "y": "Current(A)"}]'
),
(
	'MB082GA*', 'curve.coordinates', '[{"name": "Torque(oz-in)", "x": "Speed(Krpm)", "y": "Torque(oz-in)"}, {"name": "Torque(Ncm)", "x": "Speed(Krpm)", "y": "Torque(Ncm)"}]'
),
(
	'MDB56GS*,ME042AS*,ME042GS*,ME042WS*,ME042YS*,ME043AS*,ME057AH*,ME060AS*,MEO70AS*,ME080AS*,ME080RS*D,ME080AS*,ME110AS*,MEL57AH*', 'curve.coordinates', '[{"name": "", "x": "Torque(Nm)", "y": "Speed(rpm)"}, {"name": "", "x": "Torque(Nm)", "y": "Current(A)"}]'
)
;
-- BLDC_INTERNAL_ROTOR config end --


-- BLDC_EXTERNAL_ROTOR config begin --
INSERT INTO MOTOR_CONFIG (
	MODEL_REFS, CONFIG_NAME, CONFIG_VALUE
)
VALUES
(
	'EF045AS*', 'curve.coordinates', '[{"name": "", "x": "Torque(Nm)", "y": "Speed(rpm)"}, {"name": "", "x": "Torque(Nm)", "y": "Current(A)"}]'
)
;
-- BLDC_EXTERNAL_ROTOR config end --


-- BLDC_SERVO config begin --
INSERT INTO MOTOR_CONFIG (
	MODEL_REFS, CONFIG_NAME, CONFIG_VALUE
)
VALUES
(
	'SE060AS*,SE080AS*', 'curve.coordinates', '[{"name": "Tpeak", "x": "Speed(rpm)[0]", "y": "Torque(Nm)[0]"}, {"name": "Tcont", "x": "Speed(rpm)[1]", "y": "Torque(Nm)[1]"}]'
),
VALUES
(
	'SEP040A(B)Q*,SEP060A(B)M*,SEP080A(B)M*,SEP110A(B)13H*,SEP130A(B)R30H*', 'curve.coordinates', '[{"name": "", "x": "Speed(rpm)[0]", "y": "Torque(Nm)[0]"}, {"name": "", "x": "Speed(rpm)[1]", "y": "Torque(Nm)[1]"}]'
)
;
-- BLDC_SERVO config end --
---- BLDC Motor config end ----

---- PERMANENT_MAGNET_BRUSH config begin ----
INSERT INTO MOTOR_CONFIG (
	MODEL_REFS, CONFIG_NAME, CONFIG_VALUE
)
VALUES
(
	'MB030JS*,MB040JS*,MB042DK*,MB063KG*', 'curve.coordinates', '[{"name": "Speed(rpm)", "x": "Torque(mNm)", "y": "Speed(rpm)"}, {"name": "Current(A)", "x": "Torque(mNm)", "y": "Current(A)"}]'
),
VALUES
(
	'MB054TP*,MB076HG*,MB100FG*', 'curve.coordinates', '[{"name": "Speed(rpm)", "x": "Torque(Nm)", "y": "Speed(rpm)"}, {"name": "Current(A)", "x": "Torque(Nm)", "y": "Current(A)"}]'
)
---- PERMANENT_MAGNET_BRUSH config end ----



---- Stepper Motor config begin ----
-- STEPPER_STANDARD_TORQUE config begin --
INSERT INTO MOTOR_CONFIG (
	MODEL_REFS, CONFIG_NAME, CONFIG_VALUE
)
VALUES (
	'MP028NB*,MP039NA*,MP042NB*,MP057NB*', 'curve.coordinates', '[{"x": "Frequency(Hz)", "y": "Torque(Ncm)"}]'
);
-- STEPPER_STANDARD_TORQUE config end --
---- Stepper Motor config end ----