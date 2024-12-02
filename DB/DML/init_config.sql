-- Config --
INSERT INTO CONFIG (NAME, VALUE)
VALUES
(
	'company.exmek', '{"name": "Wuxi Junhong Automation Technology Co.,Ltd.", "description": "Exmek Electric has more than 20 years’ experience designing and manufacturing fractional horsepower DC motor solutions for worldwide OEMs and industrial customers. With the most diversified products program, we could match your most unique and precise motion control needs. This includes solar tracking, material handling, medical, semiconductor, automobile, robot, office automation, textile, agriculture, etc. Our experience with the products and application history in the different industry enables us to understand and take care of your every single technical detail, right from design until volume production.", "mission": "Since establishment, Exmek strives to provide the best possible experience we can for our loyal customers and work alongside them to find the best solution to their individual situation.  We work together and grow together!", "phoneNumber": "0086 510 83079076", "email": "Lydia@junhongmotor.com", "address": "No. 28-94 Hui Bei Road, Liang Xi District, Wuxi, JiangSu, China", "youtubeLink": "https://www.youtube.com/user/jack973209", "facebookLink": "https://www.facebook.com/ExmekElectric", "linkedinLink": "https://www.linkedin.com/company/exmekelectric", "twitterLink": "https://twitter.com/ExmekElectric"}'
),
(
	'smtp.exmeksys', '{"host": "smtp.gmail.com", "port": 587, "user": "exmeksys@gmail.com", "password": "mzuhdzrzhyeostbe", "properties": {"mail.transport.protocol": "smtp", "mail.smtp.auth": "true", "mail.smtp.starttls.enable": "true", "mail.debug": "true"}}'
),
(
	'email.inquiryReceivers', '{"to": ["lydia@junhongmotor.com"], "cc": ["grace.zhang@junhongmotor.com"], "bcc": []}' 
),
(
	'external.lookupCountryService', '{"baseEndpoint": "https://api.country.is/", "countryPropertyName": "country"}' 
),
(
	'consumers', '[{"id": "exmekweb@2411"}, {"id": "exmektest@2411"}]' 
),
(
	'cors.allowedOrigins', '["http://localhost:3000", "http://localhost:80", "https://localhost:443"]' 
),
(
	'search.dcMotor.metaCriteria.fields', '["frameSize", "length", "ratedVoltage", "ratedCurrent", "ratedPower", "ratedTorque", "ratedRotatingSpeed", "ratedLinearSpeed", "peakCurrent", "peakTorque", "maxSortingWeight", "weight", "model"]'
),
(
	'search.stepperMotor.metaCriteria.fields', '["frameSize", "length", "ratedVoltage", "phaseCurrent", "phaseResistance", "holdingTorque", "detentTorque", "maxThrust", "stepAngle", "weight", "model"]'
),
(
	'search.planetaryGearbox.metaCriteria.fields', '["frameSize", "length", "numOfStages", "efficiency", "ratedContinuousTorque", "maxMomentaryTorque", "weight", "model"]'
),
(
	'search.brake.metaCriteria.fields', '["frameSize", "length", "ratedVoltage", "resistance", "current", "staticTorque", "ratedPower", "startVoltage", "weight", "model"]'
)
;


-- UPDATE  CONFIG set VALUE = '{"to": ["test4yz@gmail.com"], "cc": [], "bcc": []}' WHERE NAME = 'email.inquiry_receivers';

