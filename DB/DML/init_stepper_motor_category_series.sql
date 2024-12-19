-- STEPPER_MOTOR_CATEGORY --
INSERT INTO STEPPER_MOTOR_CATEGORY (CATEGORY, TYPE, DISPLAY_NAME, DESCRIPTION, TECHNICAL_DATA)
VALUES 
('STEPPER_STANDARD_TORQUE', NULL, 'Standard torque Stepper motor', '', ''),
('STEPPER_FLAT', NULL, 'Flat Stepper motor', '', ''),
('STEPPER_WITH_CONTROL', NULL, 'Stepper motor with control', '', ''),
('STEPPER_LINEAR', NULL, 'Linear Stepper motor', '', '')
;


-- STEPPER_MOTOR_SERIES --
INSERT INTO STEPPER_MOTOR_SERIES (CATEGORY, SERIES, DISPLAY_NAME, DESCRIPTION, TECHNICAL_DATA)
VALUES
-- STEPPER_STANDARD_TORQUE Begin --
(
'STEPPER_STANDARD_TORQUE',
'MP020NA',
'MP020NA',
'NEMA 8
2 Phase Hybrid stepper motor',
'{"Holding Torque": "1.8-2 Ncm", "Detent Torque": "0.2 Ncm", "Phase Current": "0.6 A", "Step Angle": "1.8 °", "Length": "30-60 mm"}'
),

(
'STEPPER_STANDARD_TORQUE',
'MP028NB',
'MP028NB',
'NEMA 11
2 Phase Hybrid stepper motor',
'{"Holding Torque": "4-9.5 Ncm", "Detent Torque": "0.8-1.5 Ncm", "Phase Current": "0.47-1.30 A", "Step Angle": "1.8 °", "Length": "31.5-50.5 mm"}'
),

(
'STEPPER_STANDARD_TORQUE',
'MP039NA',
'MP039NA',
'NEMA 16
2 Phase Hybrid stepper motor',
'{"Holding Torque": "4.6-30 Ncm", "Detent Torque": "0.8-2.5 Ncm", "Phase Current": "0.28-0.80 A", "Step Angle": "1.8 °", "Length": "20-53 mm"}'
),

(
'STEPPER_STANDARD_TORQUE',
'MP042NB',
'MP042NB',
'NEMA 17
2 Phase Hybrid stepper motor',
'{"Holding Torque": "12-80 Ncm", "Detent Torque": "1.42-6 Ncm", "Phase Current": "0.3-3.5 A", "Step Angle": "1.8 °", "Length": "24-60 mm"}'
),

(
'STEPPER_STANDARD_TORQUE',
'MP042SB',
'MP042SB',
'NEMA 17
2 Phase Hybrid stepper motor',
'{"Holding Torque": "14.5-49 Ncm", "Detent Torque": "1.5-2.74 Ncm", "Phase Current": "0.4-1.68 A", "Step Angle": "0.9 °", "Length": "22-68 mm"}'
),

(
'STEPPER_STANDARD_TORQUE',
'MP057NB',
'MP057NB',
'NEMA 23
2 Phase Hybrid stepper motor',
'{"Holding Torque": "41-300 Ncm", "Detent Torque": "2.6-12 Ncm", "Phase Current": "1-6 A", "Step Angle": "1.8 °", "Length": "41-115 mm"}'
),

(
'STEPPER_STANDARD_TORQUE',
'MP057SB',
'MP057SB',
'NEMA 23
2 Phase Hybrid stepper motor',
'{"Holding Torque": "19.6-250 Ncm", "Detent Torque": "3.92-12 Ncm", "Phase Current": "1.0-3.0 A", "Step Angle": "0.9 °", "Length": "41-115 mm"}'
),

(
'STEPPER_STANDARD_TORQUE',
'MP060NB',
'MP060NB',
'NEMA 24
2 Phase Hybrid stepper motor',
'{"Holding Torque": "110-310 Ncm", "Detent Torque": "6-17 Ncm", "Phase Current": "2.8 A", "Step Angle": "1.8 °", "Length": "45-86 mm"}'
),

(
'STEPPER_STANDARD_TORQUE',
'MP086NA',
'MP086NA',
'NEMA 34, Round housing
2 Phases Hybrid Stepper Motor',
'{"Holding Torque": "137.2-392 Ncm", "Detent Torque": "7.84-24.5 Ncm", "Phase Current": "1.7-7 A", "Step Angle": "1.8 °", "Length": "62-127 mm"}'
),

(
'STEPPER_STANDARD_TORQUE',
'MP086YG',
'MP086YG',
'NEMA 34
2 Phases Hybrid Stepper Motor',
'{"Holding Torque": "2.8-12.1 Nm", "Detent Torque": "0.2-0.38 Nm", "Phase Current": "6.1-10.0 A", "Step Angle": "1.8 °", "Length": "65-156.5 mm"}'
),

(
'STEPPER_STANDARD_TORQUE',
'MP110YG',
'MP110YG',
'NEMA 43
2 Phases Hybrid Stepper Motor',
'{"Holding Torque": "11.68-30.81 Nm", "Detent Torque": "0.3-0.75 Nm", "Phase Current": "10.7-15.8 A", "Step Angle": "1.8 °", "Length": "99-201 mm"}'
),
-- STEPPER_STANDARD_TORQUE End --



-- STEPPER_FLAT Begin --
(
'STEPPER_FLAT',
'MPF028NB',
'MPF028NB',
'',
'{"Holding Torque": "0.98 Ncm", "Phase Current": "0.5 A", "Step Angle": "1.8 °", "Length": "9.4 mm"}'
),

(
'STEPPER_FLAT',
'MPF068NB',
'MPF068NB',
'',
'{"Holding Torque": "6.4 Ncm", "Phase Current": "1 A", "Step Angle": "1.8 °", "Length": "9.6 mm"}'
),
-- STEPPER_FLAT End --



-- STEPPER_WITH_CONTROL Begin --
(
'STEPPER_WITH_CONTROL',
'EMP42',
'EMP42',
'',
'{"Holding Torque": "33.3-80 Ncm", "Phase Current": "1-2.5 A", "Length": "40-60 mm"}'
),

(
'STEPPER_WITH_CONTROL',
'EMP57',
'EMP57',
'',
'{"Holding Torque": "55-300 Ncm", "Phase Current": "2-2.5 A", "Length": "41-115 mm"}'
),
-- STEPPER_WITH_CONTROL End --



-- STEPPER_LINEAR Begin --
(
'STEPPER_LINEAR',
'LS020NB',
'LS020NB',
'',
'{"Phase Current": "0.5 A", "Length": "27.2-38.1 mm"}'
),

(
'STEPPER_LINEAR',
'LS028NB',
'LS028NB',
'',
'{"Phase Current": "0.5-1.6 A", "Length": "32.6-45 mm"}'
),

(
'STEPPER_LINEAR',
'LS035NB',
'LS035NB',
'',
'{"Phase Current": "0.5-1.5 A", "Length": "33.6-45.6 mm"}'
),

(
'STEPPER_LINEAR',
'LS042NB',
'LS042NB',
'',
'{"Phase Current": "0.5-2.5 A", "Length": "34.1-48.1 mm"}'
),

(
'STEPPER_LINEAR',
'LS057NB',
'LS057NB',
'',
'{"Phase Current": "1.0-4.0 A", "Length": "45-65 mm"}'
),

(
'STEPPER_LINEAR',
'LS086NB',
'LS086NB',
'',
'{"Phase Current": "1.3-6.0 A", "Length": "78-100 mm"}'
)
-- STEPPER_LINEAR End --
;


