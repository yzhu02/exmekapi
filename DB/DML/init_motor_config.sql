-- DC Motor Config --
INSERT INTO MOTOR_CONFIG (
	MODEL_REF, CONFIG_NAME, CONFIG_VALUE
)
VALUES (
	'MB057GA*', 'curve.coordinates', '[{"name": "Tcont", "x": "Speed(Krpm)", "y": "Torque(oz-in)"}, {"name": "Tpeak", "x": "Speed(Krpm)", "y": "Torque(Ncm)"}]'
);


INSERT INTO MOTOR_CONFIG (
	MODEL_REF, CONFIG_NAME, CONFIG_VALUE
)
VALUES (
	'MB059AH*', 'curve.coordinates', '[{"name": "", "x": "Torque(Nm)", "y": "Current(A)"}, {"name": "", "x": "Torque(Nm)", "y": "Speed(rpm)"}]'
);


INSERT INTO MOTOR_CONFIG (
	MODEL_REF, CONFIG_NAME, CONFIG_VALUE
)
VALUES (
	'MB082GA*', 'curve.coordinates', '[{"name": "Torque(oz-in)", "x": "Speed(Krpm)", "y": "Torque(oz-in)"}, {"name": "Torque(Ncm)", "x": "Speed(Krpm)", "y": "Torque(Ncm)"}]'
);


-- Stepper Motor Config --
INSERT INTO MOTOR_CONFIG (
	MODEL_REF, CONFIG_NAME, CONFIG_VALUE
)
VALUES (
	'MP028NB*', 'curve.coordinates', '[{"x": "Frequency(Hz)", "y": "Torque(Ncm)"}]'
);
