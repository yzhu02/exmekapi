-- DC_MOTOR_CATEGORY --
INSERT INTO DC_MOTOR_CATEGORY (CATEGORY, TYPE, DISPLAY_NAME, DESCRIPTION, TECHNICAL_DATA)
VALUES 
('BLDC_INTERNAL_ROTOR', 'BLDC', 'Internal Rotor BLDC motor', '', ''),
('BLDC_EXTERNAL_ROTOR', 'BLDC', 'External Rotor BLDC motor', '', ''),
('BLDC_FRAMELESS', 'BLDC', 'Frameless BLDC motor', '', ''),
('BLDC_CORELESS', 'BLDC', 'Coreless BLDC motor', '', ''),
('BLDC_SERVO', 'BLDC', 'BLDC Servo motor', '', ''),
('BLDC_WITH_GEARBOX', 'BLDC', 'BLDC motor with gearbox', '', ''),
('BLDC_DIRECT_DRIVE', 'BLDC', 'Direct-drive Brushless motor', '', ''),

('PERMANENT_MAGNET_BRUSH', 'BRUSH', 'Permanent Magnet Brush motor', '', ''),
('BRUSH_WITH_GEARBOX', 'BRUSH', 'Brush motor with gearbox', '', ''),

('INTEGRATED', NULL, 'Integrated motor', '', ''),

('SOLAR_TRACKING_APPLICATION', NULL, 'Solar Tracking Application', '', ''),
('MATERIAL_HANDLING_SOLUTION', NULL, 'Material Handling Solution', '', '')
;


-- DC_ MOTOR_SERIES --
INSERT INTO DC_MOTOR_SERIES (CATEGORY, SERIES, DISPLAY_NAME, DESCRIPTION, TECHNICAL_DATA)
VALUES
-- BLDC_INTERNAL_ROTOR Begin --
(
'BLDC_INTERNAL_ROTOR',
'ME042WS',
'ME042WS',
'Wide speed range, flat torque
Excellent speed stability
Compact and high power
Low temperature rise, low noise, low vibration',
'{"Rated Power": "30-90 W", "Rated Voltage": "24-48 VDC", "Rated Torque": "0.072-0.215 Nm", "Rated Speed": "4000 rpm", "Length": "46-100 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME036GA',
'ME036GA',
'Long Life
Direct Replacement of Toy Motor
Economic Design for Power Tool',
'{"Rated Power": "7.5-33 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.015-0.07 Nm", "Rated Speed": "4500-4800 rpm", "Length": "30-57 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME042AS',
'ME042AS',
'Low cogging
High Power Density
High Efficiency',
'{"Rated Power": "31-107 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.30-1.20 Nm", "Rated Speed": "3000 rpm", "Length": "52-90 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME042GS',
'ME042GS',
'Nema 17 Mounting Interface
Economic Design for Volume Production
Bonded Neo Magnet',
'{"Rated Power": "27-112 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.064-0.268 Nm", "Rated Speed": "4000 rpm", "Length": "41-100 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME042YS',
'ME042YS',
'Compact Design
Economic Design
Bonded Neo Magnet',
'{"Rated Power": "32-71 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.077-0.169 Nm", "Rated Speed": "4000 rpm", "Length": "60-85 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME043AS',
'ME043AS',
'Sealed Housing Design
120/60 degree hall effect
Smooth Operation',
'{"Rated Power": "9.5-24.5 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.038-0.078 Nm", "Rated Speed": "2400-3000 rpm", "Length": "47.4-66.4 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'MB057GA',
'MB057GA',
'High Reliable
Bonded Neo Magnet
Nema 23 Flange Available',
'{"Rated Power": "55-209 W", "Rated Voltage": "60 VDC", "Rated Torque": "0.11-0.40 Nm", "Rated Speed": "4750-5000 rpm", "Length": "54-114 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'MEL57AH',
'MEL57AH',
'Multiple Poles Design for Low speed
Sintered Neo Magnet
NEMA 23 Flange Available',
'{"Rated Power": "24-72 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.23-0.70 Nm", "Rated Speed": "1000 rpm", "Length": "51-91 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'MDB56GS',
'MDB56GS',
'Low Cogging
NEMA 23 Mounting Interface
Economic Design for High Power',
'{"Rated Power": "47-188 W", "Rated Voltage": "36 VDC", "Rated Torque": "0.15-0.60 Nm", "Rated Speed": "3000 rpm", "Length": "56-116 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME057AH',
'ME057AH',
'High Efficiency
High Voltage Capability
High Power Density',
'{"Rated Power": "195-358 W", "Rated Voltage": "170 VDC", "Rated Torque": "0.31-0.70 Nm", "Rated Speed": "6000 rpm", "Length": "71.1-127.9 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'MB059AH',
'MB059AH',
'High Power Density
Nema 23 Mounting Available
Sintered Neo Magnet',
'{"Rated Power": "84-220 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.23-0.60 Nm", "Rated Speed": "3500 rpm", "Length": "53.6-93.6 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME060AS',
'ME060AS',
'12 slots design for low cogging
Sintered Neo Magnet
Economic Design for Direct Drive',
'{"Rated Power": "79-236 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.25-0.75 Nm", "Rated Speed": "3000 rpm", "Length": "78-120 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'MB070GA',
'MB070GA',
'Low cogging
Special Deisgn for Easy Production
High Reliable',
'{"Rated Power": "85-110 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.215-0.355 Nm", "Rated Speed": "3000-3800 rpm", "Length": "95-123 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME070AS',
'ME070AS',
'12 slots design for low cogging
Sintered Neo Magnet
Economic Design for Direct Drive',
'{"Rated Power": "157-471 W", "Rated Voltage": "48 VDC", "Rated Torque": "0.50-1.50 Nm", "Rated Speed": "3000 rpm", "Length": "88-148 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME080AS',
'ME080AS',
'8 Poles with 3 Phases
Low Cogging
High Power Density',
'{"Rated Power": "314-628 W", "Rated Voltage": "310 VDC", "Rated Torque": "1.00-2.00 Nm", "Rated Speed": "3000 rpm", "Length": "111-164 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME080RS',
'ME080RS',
'4 poles with 3 phases
Sintered Neo Magnet
Economic Design for Direct Drive',
'{"Rated Power": "157-236 W", "Rated Voltage": "24-48 VDC", "Rated Torque": "0.50-1.50 Nm", "Rated Speed": "1500-3000 rpm", "Length": "80-120 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'MB082GA',
'MB082GA',
'Low Cogging
NEMA 34 Mounting Interface
Bonded Neo Magnet
High Voltage Available',
'{"Rated Power": "314-838 W", "Rated Voltage": "170 VDC", "Rated Torque": "0.60-2.00 Nm", "Rated Speed": "4000-6000 rpm", "Length": "78-141 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME086AS',
'ME086AS',
'8 poles with 3 phases
Economic Design for Simple Servo
High Efficiency',
'{"Rated Power": "220-660 W", "Rated Voltage": "310 VDC", "Rated Torque": "0.70-2.10 Nm", "Rated Speed": "3000 rpm", "Length": "135-189 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME110AS',
'ME110AS',
'8 poles with 3 phases
18 slots design for low cogging
High Power Density',
'{"Rated Power": "628-1885 W", "Rated Voltage": "310 VDC", "Rated Torque": "2.00-6.00 Nm", "Rated Speed": "3000 rpm", "Length": "128-180 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'ME130AS',
'ME130AS',
'8 poles with 3 phases
24 slots design for low cogging
Direct Replacement of IEC AC motor',
'{"Rated Power": "314-942 W", "Rated Voltage": "48 VDC", "Rated Torque": "2.00-6.00 Nm", "Rated Speed": "1500 rpm", "Length": "105-165 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'MB120GA',
'MB120GA',
'Economic Design with Bonded Neo
Voltage Rating up to 325VDC
Optional; NEMA 56 mounting',
'{"Rated Power": "1275-3194 W", "Rated Voltage": "325 VDC", "Rated Torque": "2.03-6.10 Nm", "Rated Speed": "5000-6000 rpm", "Length": "144.3-251 mm"}'
),

(
'BLDC_INTERNAL_ROTOR',
'MBH057GA',
'MBH057GA',
'',
'{"Rated Power": "250-520 W", "Rated Voltage": "70 VDC", "Rated Torque": "0.2-0.4 Nm", "Rated Speed": "12500 rpm", "Length": "54-94 mm"}'
),
-- BLDC_INTERNAL_ROTOR End --

-- BLDC_EXTERNAL_ROTOR Begin --
(
'BLDC_EXTERNAL_ROTOR',
'EF045AS',
'EF045AS',
'16 poles with 3 phases
Compact Design
Smooth Operation under Low Speed',
'{"Rated Power": "17-30 W", "Rated Voltage": "12-24 VDC", "Rated Torque": "50-55.5 mNm", "Rated Speed": "2940-5000 rpm", "Length": "16.4 mm"}'
),

(
'BLDC_EXTERNAL_ROTOR',
'EF048GA',
'EF048GA',
'10 Poles with 3 Phases
Economic Solution with Driver
Special for Fan Application',
'{"Rated Power": "50 W", "Rated Voltage": "24 VDC", "Rated Torque": "165 mNm", "Rated Speed": "2900 rpm", "Length": "25 mm"}'
),

(
'BLDC_EXTERNAL_ROTOR',
'EF058GA',
'EF058GA',
'10 Poles with 3 Phases
Economic Solution with Driver
Special for Fan Application',
'{"Rated Power": "50 W", "Rated Voltage": "24 VDC", "Rated Torque": "149 mNm", "Rated Speed": "3200 rpm", "Length": "28.3 mm"}'
),
-- BLDC_EXTERNAL_ROTOR End --

-- BLDC_FRAMELESS Begin --
(
'BLDC_FRAMELESS',
'MF180AS',
'MF180AS',
'High Overload capability
Low Speed Design Available
Economic Design',
'{"Rated Power": "190-630 W", "Rated Voltage": "48 VDC", "Rated Torque": "6-20 Nm", "Rated Speed": "300 rpm"}'
),

(
'BLDC_FRAMELESS',
'TF060',
'TF060',
'High Power Density
Low Cogging Torque
Hall Board available',
'{"Rated Power": "149-319 W", "Rated Voltage": "48 VDC", "Rated Torque": "0.71-1.69 Nm", "Rated Speed": "1800-2000 rpm"}'
),

(
'BLDC_FRAMELESS',
'TF076',
'TF076',
'High Power Density
Low Cogging Torque
Hall Board available',
'{"Rated Power": "149-319 W", "Rated Voltage": "48 VDC", "Rated Torque": "0.71-1.69 Nm", "Rated Speed": "1800-2000 rpm"}'
),

(
'BLDC_FRAMELESS',
'MF080AS',
'MF080AS',
'Multipolar and high torque density
Low Speed and High Torque
Low Rotor Inertia
High overload capability
Hall Board or encoder available',
'{"Rated Power": "188-377 W", "Rated Voltage": "48 VDC", "Rated Torque": "2.4-4.8 Nm", "Rated Speed": "750 rpm"}'
),

(
'BLDC_FRAMELESS',
'MF090AS',
'MF090AS',
'Multipolar and high torque density
Low Cogging Torque
Low torque ripple
High overload capability
Hall Board or encoder available',
'{"Rated Power": "251-503 W", "Rated Voltage": "48 VDC", "Rated Torque": "3.2-6.4 Nm", "Rated Speed": "750 rpm"}'
),

(
'BLDC_FRAMELESS',
'MF130AS',
'MF130AS',
'Multipolar and high torque density
Low Cogging Torque
Low torque ripple
High overload capability
Hall Board or encoder available',
'{"Rated Power": "141-377 W", "Rated Voltage": "48 VDC", "Rated Torque": "4.5-12 Nm", "Rated Speed": "300 rpm"}'
),
-- BLDC_FRAMELESS End --

-- BLDC_CORELESS Begin --
(
'BLDC_CORELESS',
'SLS',
'SLS',
'As High As 40000rpm Speed
Low Temperature Rise
Hall Sensor Available',
'{"Rated Power": "80-180 W", "Rated Voltage": "15-32 VDC", "Rated Torque": "16-150 mNm", "Rated Speed": "8000-50000 rpm", "Length": "40-59.5 mm"}'
),
-- BLDC_CORELESS End --

-- BLDC_SERVO Begin --
(
'BLDC_SERVO',
'SEP040',
'SEP040',
'',
'{"Rated Power": "50-100 W", "Rated Voltage": "48 VDC", "Rated Torque": "0.16-0.32 Nm", "Rated Speed": "3000 rpm", "Length": "49.5-63.5 mm"}'
),

(
'BLDC_SERVO',
'SEP060',
'SEP060',
'',
'{"Rated Power": "200-400 W", "Rated Voltage": "24-48 VDC", "Rated Torque": "0.64-1.27 Nm", "Rated Speed": "3000 rpm", "Length": "64.5-84.5 mm"}'
),

(
'BLDC_SERVO',
'SEP080',
'SEP080',
'',
'{"Rated Power": "500-800 W", "Rated Voltage": "24-48 VDC", "Rated Torque": "1.53-2.55 Nm", "Rated Speed": "3000 rpm", "Length": "75-92 mm"}'
),

(
'BLDC_SERVO',
'SEP110',
'SEP110',
'',
'{"Rated Power": "1320 W", "Rated Voltage": "48 VDC", "Rated Torque": "4.2 Nm", "Rated Speed": "3000 rpm", "Length": "109 mm"}'
),

(
'BLDC_SERVO',
'SEP130',
'SEP130',
'',
'{"Rated Power": "3000 W", "Rated Voltage": "538 VDC", "Rated Torque": "14.33 Nm", "Rated Speed": "2000 rpm", "Length": "150.5 mm"}'
),

(
'BLDC_SERVO',
'SE080AS-REDUCER',
'SE080AS REDUCER',
'All-in-one compact design
High efficiency, low noise and high reliable gearbox
Brake available
Drive solution for mobile robot (AGV/Forklift)',
'{"Rated Power": "400 W", "Rated Voltage": "48 VDC", "Rated Torque": "18.3 Nm", "Rated Speed": "187.5 rpm", "Length": "130 mm"}'
),

(
'BLDC_SERVO',
'SE060AS',
'SE060AS',
'8 Poles with 3 Phases
Low Cogging
High Power Density',
'{"Rated Power": "124-410 W", "Rated Voltage": "36-48 VDC", "Rated Torque": "0.3-1.3 Nm", "Rated Speed": "3000-4000 rpm", "Length": "101-141 mm"}'
),

(
'BLDC_SERVO',
'SE080AS',
'SE080AS',
'8 Poles with 3 Phases
Low Cogging
High Power Density',
'{"Rated Power": "411-765 W", "Rated Voltage": "48 VDC", "Rated Torque": "1.31-2.44 Nm", "Rated Speed": "3000 rpm", "Length": "132-153 mm"}'
),
-- BLDC_SERVO End --

-- BLDC_WITH_GEARBOX Begin --
(
'BLDC_WITH_GEARBOX',
'ME032RS100-SI0020',
'ME032RS100-SI0020',
'Direct Replacement of PMDC motor
Economic Solution
Long Life',
'{"Rated Power": "2.3 W", "Rated Voltage": "12 VDC", "Rated Torque": "9 Ncm", "Rated Speed": "250 rpm", "Length": "51.5 mm"}'
),

(
'BLDC_WITH_GEARBOX',
'ME036RS100-SI0010',
'ME036RS100-SI0010',
'Direct Replacement of PMDC motor
Economic Solution
Long Life',
'{"Rated Power": "6.3 W", "Rated Voltage": "12 VDC", "Rated Torque": "25 Ncm", "Rated Speed": "240 rpm", "Length": "80.5 mm"}'
),

(
'BLDC_WITH_GEARBOX',
'AGV_BLDC_Motor',
'AGV BLDC Motor',
'8 poles with 3 phases
Low Cogging',
'{"Rated Power": "264 W", "Rated Voltage": "32 VDC", "Rated Torque": "1.2 Ncm", "Rated Speed": "2100 rpm", "Length": "106.6 mm"}'
),
-- BLDC_WITH_GEARBOX End --

-- BLDC_DIRECT_DRIVE Begin --
(
'BLDC_DIRECT_DRIVE',
'MFP180AT100',
'MFP180AT100',
'Direct-drive brushless motor
High Torque, low inertia, fast response,
low torque ripple
Integrated controller, speed & position-control
RS232 and CANopen communication, and multi-turns absolute encoder optional
Steering motor for autopilot agriculture machinery',
'{"Rated Power": "68 W", "Rated Voltage": "12 VDC", "Rated Torque": "6.5 Nm", "Rated Speed": "100 rpm"}'
),
-- BLDC_DIRECT_DRIVE End --



-- PERMANENT_MAGNET_BRUSH Begin --
(
'PERMANENT_MAGNET_BRUSH',
'MB030JS',
'MB030JS',
'Ball Bearings
Low Cogging
Reliable Brush Holder Design',
'{"Rated Power": "6.1-15.5 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.0113-0.0184 Nm", "Rated Speed": "5180-8070 rpm", "Length": "52.6-62.1 mm"}'
),

(
'PERMANENT_MAGNET_BRUSH',
'MB040JS',
'MB040JS',
'Long life
Skew Slot Design for Low cogging
Reliable Brush Holder Design',
'{"Rated Power": "10.3-41.1 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.017-0.081 Nm", "Rated Speed": "4400-6000 rpm", "Length": "48-86 mm"}'
),

(
'PERMANENT_MAGNET_BRUSH',
'MB042DK',
'MB042DK',
'DIN Standard Mounting
High Efficiency
EMI filter available',
'{"Rated Power": "14.3-28.3 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.04-0.09 Nm", "Rated Speed": "3000-3400 rpm", "Length": "75-95 mm"}'
),

(
'PERMANENT_MAGNET_BRUSH',
'MB054TP',
'MB054TP',
'Ceramic magnet
Replacement Carbon Brush
Economic Design for Volume Production',
'{"Rated Power": "23.5-124.4 W", "Rated Voltage": "24 VDC", "Rated Torque": "0.07-0.36 Nm", "Rated Speed": "3200-3300 rpm", "Length": "75-145 mm"}'
),

(
'PERMANENT_MAGNET_BRUSH',
'MB063KG',
'MB063KG',
'Ceramic Magnets
High Effiency
Long Life Brush',
'{"Rated Power": "50-100 W", "Rated Voltage": "12-24 VDC", "Rated Torque": "0.1-0.3 Nm", "Rated Speed": "3000-3350 rpm", "Length": "95-125 mm"}'
),

(
'PERMANENT_MAGNET_BRUSH',
'MB076HG',
'MB076HG',
'Stator with permanent rare earth magnet stator whichare glued and protected by a stainless steel sleeve',
'{"Rated Power": "182-442 W", "Rated Voltage": "24-90 VDC", "Rated Torque": "0.75-1.39 Nm", "Rated Speed": "1200-3200 rpm", "Length": "114-157 mm"}'
),

(
'PERMANENT_MAGNET_BRUSH',
'MB100FG',
'MB100FG',
'IEC34-1 Standard Flange
Hard Ferrite magnet
Number of Poles: 4',
'{"Rated Power": "292-682 W", "Rated Voltage": "48 VDC", "Rated Torque": "0.90-2.10 Nm", "Rated Speed": "3100 rpm", "Length": "125-178 mm"}'
),
-- PERMANENT_MAGNET_BRUSH End --



-- BRUSH_WITH_GEARBOX Begin --
(
'BRUSH_WITH_GEARBOX',
'SG80',
'SG80',
'Low noise
Double output shaft available
Door Opener application',
'{"Continuous Torque": "4 Nm"}'
),
-- BRUSH_WITH_GEARBOX End --



-- INTEGRATED Begin --
(
'INTEGRATED',
'MDS040',
'MDS040',
'',
'{"Rated Power": "50-100 W", "Rated Voltage": "24-36 VDC", "Rated Torque": "0.16-0.32 Nm", "Rated Speed": "3000 rpm", "Length": "69-83 mm"}'
),

(
'INTEGRATED',
'MDS057',
'MDS057',
'',
'{"Rated Power": "47-188 W", "Rated Voltage": "36 VDC", "Rated Torque": "0.1-0.6 Nm", "Rated Speed": "3000 rpm", "Length": "88-148 mm"}'
),

(
'INTEGRATED',
'MDS060',
'MDS060',
'',
'{"Rated Power": "100-400 W", "Rated Voltage": "36 VDC", "Rated Torque": "0.32-1.27 Nm", "Rated Speed": "3000 rpm", "Length": "96.5-133.5 mm"}'
),

-- INTEGRATED End --


-- SOLAR_TRACKING_APPLICATION Begin --
(
'SOLAR_TRACKING_APPLICATION',
'66-FRAME-PMDC_BLDC-GEAR-MOTOR',
'66 FRAME PMDC/BLDC GEAR MOTOR',
'2 PPR or 4 PPR encoder available
Protection class IP 65
With protective vent valve to reduce condensation',
'{"Rated Power": "41-51 W", "Rated Voltage": "24 VDC", "Rated Torque": "2.9-108.8 Nm", "Rated Speed": "4-167 rpm", "Length": "185-198 mm", "Ratio": "18-837"}'
),

(
'SOLAR_TRACKING_APPLICATION',
'82-FRAME-PMDC_BLDC-GEAR-MOTOR',
'82 FRAME PMDC/BLDC GEAR MOTOR',
'2 PPR or 4 PPR encoder available
Protection class IP 65
With protective vent valve to reduce condensation',
'{"Rated Power": "82-102 W", "Rated Voltage": "24 VDC", "Rated Torque": "5.8-217.4 Nm", "Rated Speed": "4-167 rpm", "Length": "215-260 mm", "Ratio": "18-836"}'
),

(
'SOLAR_TRACKING_APPLICATION',
'94-FRAME-PMDC_BLDC-GEAR-MOTOR',
'94 FRAME PMDC/BLDC GEAR MOTOR',
'2 PPR or 4 PPR encoder available
Protection class IP 65
With protective vent valve to reduce condensation
UL Certified',
'{"Rated Power": "35-115 W", "Rated Voltage": "24 VDC", "Rated Torque": "320-550 Nm", "Rated Speed": "0.6-2.2 rpm", "Length": "308-330 mm", "Ratio": "745-2023.5"}'
),

(
'SOLAR_TRACKING_APPLICATION',
'128-FRAME-PMDC_BLDC-GEAR-MOTOR',
'128 FRAME PMDC/BLDC GEAR MOTOR',
'2 PPR or 4 PPR encoder available
Epoxy coating on the surface 
With protective vent valve to reduce condensation',
'{"Rated Power": "272-339 W", "Rated Voltage": "24 VDC", "Rated Torque": "37.3-2024.1 Nm", "Rated Speed": "1-87 rpm", "Length": "310-355 mm", "Ratio": "23-1557"}'
),
-- SOLAR_TRACKING_APPLICATION End --

-- MATERIAL_HANDLING_SOLUTION Begin --
(
'MATERIAL_HANDLING_SOLUTION',
'Sorter-BLDC-Roller',
'Sorter BLDC Roller',
'Direct drive
Wide speed range
Free of maintenance
Surface knurling available',
'{"Rated Power": "150-400 W", "Rated Voltage": "48 VDC", "Rated Torque": "4.5-15 Nm", "Rated Speed": "300-800 rpm"}'
),

(
'MATERIAL_HANDLING_SOLUTION',
'Sorter-Servo-Roller',
'Sorter Servo Roller',
'Direct drive
Servo control
Inductive encoder',
'{"Rated Power": "150-400 W", "Rated Voltage": "48 VDC", "Rated Torque": "4.5-15 Nm", "Rated Speed": "300-800 rpm"}'
),

(
'MATERIAL_HANDLING_SOLUTION',
'Smart-Roller',
'Smart Roller',
'Multiple color rubber available
Coating Thickness 2mm or customized',
'{"Rated Power": "50-100 W", "Rated Voltage": "48 VDC", "Rated Torque": "1.5-2 Nm", "Rated Speed": "3500-1000 rpm"}'
)
-- MATERIAL_HANDLING_SOLUTION End --

;


