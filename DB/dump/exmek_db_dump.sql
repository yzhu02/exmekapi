-- MySQL dump 10.13  Distrib 8.4.2, for macos14 (arm64)
--
-- Host:     Database: exmek
-- ------------------------------------------------------
-- Server version	8.4.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `BRAKE`
--

DROP TABLE IF EXISTS `BRAKE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BRAKE` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `MODEL` varchar(64) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `FRAME_SIZE` decimal(10,2) DEFAULT NULL,
  `FRAME_SIZE_UNIT` varchar(16) DEFAULT NULL,
  `FRAME_SIZE_TYPE` varchar(16) DEFAULT NULL,
  `NEMA_SIZE` decimal(8,0) DEFAULT NULL,
  `LENGTH` decimal(10,2) DEFAULT NULL,
  `LENGTH_UNIT` varchar(16) DEFAULT NULL,
  `WEIGHT` decimal(10,2) DEFAULT NULL,
  `WEIGHT_UNIT` varchar(16) DEFAULT NULL,
  `RATED_VOLTAGE` decimal(10,2) DEFAULT NULL,
  `RATED_VOLTAGE_UNIT` varchar(16) DEFAULT NULL,
  `RESISTANCE` decimal(10,2) DEFAULT NULL,
  `RESISTANCE_UNIT` varchar(16) DEFAULT NULL,
  `CURRENT` decimal(10,4) DEFAULT NULL,
  `CURRENT_UNIT` varchar(16) DEFAULT NULL,
  `STATIC_TORQUE` decimal(10,4) DEFAULT NULL,
  `STATIC_TORQUE_UNIT` varchar(16) DEFAULT NULL,
  `RATED_POWER` decimal(10,2) DEFAULT NULL,
  `RATED_POWER_UNIT` varchar(16) DEFAULT NULL,
  `START_VOLTAGE` decimal(10,2) DEFAULT NULL,
  `START_VOLTAGE_UNIT` varchar(16) DEFAULT NULL,
  `SERIES` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_BRAKE_MODEL` (`MODEL`),
  KEY `IX_BRAKE_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_BRAKE_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`),
  KEY `IX_BRAKE_FRAME_SIZE` (`FRAME_SIZE`),
  KEY `IX_BRAKE_NEMA_SIZE` (`NEMA_SIZE`),
  KEY `IX_BRAKE_LENGTH` (`LENGTH`),
  KEY `IX_BRAKE_WEIGHT` (`WEIGHT`),
  KEY `IX_BRAKE_RATED_VOLTAGE` (`RATED_VOLTAGE`),
  KEY `IX_BRAKE_RESISTANCE` (`RESISTANCE`),
  KEY `IX_BRAKE_CURRENT` (`CURRENT`),
  KEY `IX_BRAKE_STATIC_TORQUE` (`STATIC_TORQUE`),
  KEY `IX_BRAKE_RATED_POWER` (`RATED_POWER`),
  KEY `IX_BRAKE_START_VOLTAGE` (`START_VOLTAGE`),
  KEY `FK_BRAKE_SERIES` (`SERIES`),
  CONSTRAINT `FK_BRAKE_SERIES` FOREIGN KEY (`SERIES`) REFERENCES `BRAKE_SERIES` (`SERIES`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BRAKE`
--

LOCK TABLES `BRAKE` WRITE;
/*!40000 ALTER TABLE `BRAKE` DISABLE KEYS */;
INSERT INTO `BRAKE` VALUES (1,0,'2024-08-16 23:18:51','2024-09-01 10:47:27','MAB23X-30','Brake',NULL,60.00,'mm','□',24,52.50,'mm',380.00,'g',24.00,'V',52.40,'Ω',0.4600,'A',2.0000,'Nm',11.00,'W',22.00,'V','MAB23X'),(2,0,'2024-08-16 23:23:07','2024-09-01 10:47:30','MPC023-','Brake',NULL,57.00,'mm','□',23,48.00,'mm',400.00,'g',24.00,'V',92.00,'Ω',0.2600,'A',0.5000,'Nm',6.20,'W',15.00,'V','MPC023'),(3,0,'2024-08-16 23:27:05','2024-09-01 10:47:33','MPC024-24-001','Brake',NULL,60.00,'mm','□',24,62.00,'mm',540.00,'g',24.00,'V',53.00,'Ω',0.4600,'A',1.5000,'Nm',11.00,'W',22.00,'V','MPC024'),(4,0,'2024-08-16 23:29:52','2024-09-01 10:47:36','MPC034-','Brake',NULL,86.00,'mm','□',34,63.00,'mm',500.00,'g',24.00,'V',58.00,'Ω',0.4100,'A',1.6800,'Nm',9.80,'W',15.00,'V','MPC034');
/*!40000 ALTER TABLE `BRAKE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BRAKE_SERIES`
--

DROP TABLE IF EXISTS `BRAKE_SERIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BRAKE_SERIES` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `SERIES` varchar(32) NOT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `TECHNICAL_DATA` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_BRAKE_SERIES_SERIES` (`SERIES`),
  KEY `IX_BRAKE_SERIES_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_BRAKE_SERIES_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BRAKE_SERIES`
--

LOCK TABLES `BRAKE_SERIES` WRITE;
/*!40000 ALTER TABLE `BRAKE_SERIES` DISABLE KEYS */;
INSERT INTO `BRAKE_SERIES` VALUES (1,0,'2024-09-01 10:42:26',NULL,'MAB23X','','{\"Rated Power\": \"11 W\", \"Rated Voltage\": \"24 VDC\", \"Start Voltage\": \"22 VDC\", \"Static Torque\": \"2 Nm\", \"Length\": \"52.5 mm\"}'),(2,0,'2024-09-01 10:42:26',NULL,'MPC023','','{\"Rated Power\": \"6.2 W\", \"Rated Voltage\": \"24 VDC\", \"Start Voltage\": \"15 VDC\", \"Static Torque\": \"0.5 Nm\", \"Length\": \"48 mm\"}'),(3,0,'2024-09-01 10:42:26',NULL,'MPC024','','{\"Rated Power\": \"11 W\", \"Rated Voltage\": \"24 VDC\", \"Start Voltage\": \"22 VDC\", \"Static Torque\": \"1.5 Nm\", \"Length\": \"62 mm\"}'),(4,0,'2024-09-01 10:42:26',NULL,'MPC034','','{\"Rated Power\": \"9.8 W\", \"Rated Voltage\": \"24 VDC\", \"Start Voltage\": \"15 VDC\", \"Static Torque\": \"1.68 Nm\", \"Length\": \"63 mm\"}'),(5,0,'2024-09-01 10:42:26',NULL,'MSRA061','','{ \"Rated Voltage\": \"12-48 VDC\", \"Static Torque\": \"2.26-3.95 Nm\"}');
/*!40000 ALTER TABLE `BRAKE_SERIES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CONFIG`
--

DROP TABLE IF EXISTS `CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CONFIG` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `NAME` varchar(64) NOT NULL,
  `VALUE` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_NAME` (`NAME`),
  KEY `IX_CONFIG_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_CONFIG_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CONFIG`
--

LOCK TABLES `CONFIG` WRITE;
/*!40000 ALTER TABLE `CONFIG` DISABLE KEYS */;
INSERT INTO `CONFIG` VALUES (11,0,'2024-11-20 17:09:23',NULL,'company.exmek','{\"name\": \"Wuxi Junhong Automation Technology Co.,Ltd.\", \"description\": \"Exmek Electric has more than 20 years’ experience designing and manufacturing fractional horsepower DC motor solutions for worldwide OEMs and industrial customers. With the most diversified products program, we could match your most unique and precise motion control needs. This includes solar tracking, material handling, medical, semiconductor, automobile, robot, office automation, textile, agriculture, etc. Our experience with the products and application history in the different industry enables us to understand and take care of your every single technical detail, right from design until volume production.\", \"mission\": \"Since establishment, Exmek strives to provide the best possible experience we can for our loyal customers and work alongside them to find the best solution to their individual situation.  We work together and grow together!\", \"phoneNumber\": \"0086 510 83079076\", \"email\": \"Lydia@junhongmotor.com\", \"address\": \"No. 28-94 Hui Bei Road, Liang Xi District, Wuxi, JiangSu, China\", \"youtubeLink\": \"https://www.youtube.com/user/jack973209\", \"facebookLink\": \"https://www.facebook.com/ExmekElectric\", \"linkedinLink\": \"https://www.linkedin.com/company/exmekelectric\", \"twitterLink\": \"https://twitter.com/ExmekElectric\"}'),(12,0,'2024-11-20 17:09:23',NULL,'smtp.exmeksys','{\"host\": \"smtp.gmail.com\", \"port\": 587, \"user\": \"exmeksys@gmail.com\", \"password\": \"mzuhdzrzhyeostbe\", \"properties\": {\"mail.transport.protocol\": \"smtp\", \"mail.smtp.auth\": \"true\", \"mail.smtp.starttls.enable\": \"true\", \"mail.debug\": \"true\"}}'),(13,0,'2024-11-20 17:09:23','2024-11-28 12:03:08','email.inquiryReceivers','{\"to\": [\"test4yz@gmail.com\"], \"cc\": [], \"bcc\": []}'),(14,0,'2024-11-20 23:05:38','2024-11-28 12:03:12','external.lookupCountryService','{\"baseEndpoint\": \"https://api.country.is/\", \"countryPropertyName\": \"country\"}'),(16,0,'2024-11-27 12:27:43',NULL,'consumers','[{\"id\": \"exmekweb@2411\"}, {\"id\": \"exmektest@2411\"}]'),(17,0,'2024-11-28 12:06:34',NULL,'cors.allowedOrigins','[\"http://localhost:3000\", \"http://localhost:80\", \"https://localhost:443\"]'),(18,0,'2024-11-29 12:23:12','2024-12-01 01:01:16','search.dcMotor.metaCriteria.fields','[\"frameSize\", \"length\", \"ratedVoltage\", \"ratedCurrent\", \"ratedPower\", \"ratedTorque\", \"ratedRotatingSpeed\", \"ratedLinearSpeed\", \"peakCurrent\", \"peakTorque\", \"maxSortingWeight\", \"weight\", \"model\"]'),(19,0,'2024-11-29 12:23:12','2024-12-01 01:01:33','search.stepperMotor.metaCriteria.fields','[\"frameSize\", \"length\", \"ratedVoltage\", \"phaseCurrent\", \"phaseResistance\", \"holdingTorque\", \"detentTorque\", \"maxThrust\", \"stepAngle\", \"weight\", \"model\"]'),(20,0,'2024-11-29 12:23:12','2024-12-01 01:02:20','search.planetaryGearbox.metaCriteria.fields','[\"frameSize\", \"length\", \"numOfStages\", \"efficiency\", \"ratedContinuousTorque\", \"maxMomentaryTorque\", \"weight\", \"model\"]'),(21,0,'2024-11-29 12:23:12','2024-12-01 01:02:31','search.brake.metaCriteria.fields','[\"frameSize\", \"length\", \"ratedVoltage\", \"resistance\", \"current\", \"staticTorque\", \"ratedPower\", \"startVoltage\", \"weight\", \"model\"]');
/*!40000 ALTER TABLE `CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DC_MOTOR`
--

DROP TABLE IF EXISTS `DC_MOTOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DC_MOTOR` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `CATEGORY` varchar(32) NOT NULL,
  `MODEL` varchar(64) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `FRAME_SIZE` decimal(10,2) DEFAULT NULL,
  `FRAME_SIZE_UNIT` varchar(16) DEFAULT NULL,
  `FRAME_SIZE_TYPE` varchar(16) DEFAULT NULL,
  `NEMA_SIZE` decimal(8,0) DEFAULT NULL,
  `LENGTH` decimal(10,2) DEFAULT NULL,
  `LENGTH_UNIT` varchar(16) DEFAULT NULL,
  `WEIGHT` decimal(10,2) DEFAULT NULL,
  `WEIGHT_UNIT` varchar(16) DEFAULT NULL,
  `RATED_VOLTAGE` decimal(10,2) DEFAULT NULL,
  `RATED_VOLTAGE_UNIT` varchar(16) DEFAULT NULL,
  `RATED_CURRENT` decimal(10,4) DEFAULT NULL,
  `RATED_CURRENT_UNIT` varchar(16) DEFAULT NULL,
  `RATED_POWER` decimal(10,2) DEFAULT NULL,
  `RATED_POWER_UNIT` varchar(16) DEFAULT NULL,
  `RATED_TORQUE` decimal(10,4) DEFAULT NULL,
  `RATED_TORQUE_UNIT` varchar(16) DEFAULT NULL,
  `RATED_ROTATING_SPEED` int DEFAULT NULL,
  `RATED_ROTATING_SPEED_UNIT` varchar(16) DEFAULT NULL,
  `RATED_LINEAR_SPEED` decimal(10,2) DEFAULT NULL,
  `RATED_LINEAR_SPEED_UNIT` varchar(16) DEFAULT NULL,
  `PEAK_CURRENT` decimal(10,4) DEFAULT NULL,
  `PEAK_CURRENT_UNIT` varchar(16) DEFAULT NULL,
  `PEAK_TORQUE` decimal(10,4) DEFAULT NULL,
  `PEAK_TORQUE_UNIT` varchar(16) DEFAULT NULL,
  `MAX_SORTING_WEIGHT` decimal(10,2) DEFAULT NULL,
  `MAX_SORTING_WEIGHT_UNIT` varchar(16) DEFAULT NULL,
  `NOLOAD_CURRENT` decimal(10,4) DEFAULT NULL,
  `NOLOAD_CURRENT_UNIT` varchar(16) DEFAULT NULL,
  `NOLOAD_ROTATING_SPEED` int DEFAULT NULL,
  `NOLOAD_ROTATING_SPEED_UNIT` varchar(16) DEFAULT NULL,
  `SERIES` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_MOTOR_MODEL` (`MODEL`),
  KEY `IX_MOTOR_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_MOTOR_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`),
  KEY `IX_MOTOR_NAME` (`NAME`),
  KEY `IX_MOTOR_TYPE` (`CATEGORY`),
  KEY `IX_MOTOR_FRAME_SIZE` (`FRAME_SIZE`),
  KEY `IX_MOTOR_NEMA_SIZE` (`NEMA_SIZE`),
  KEY `IX_MOTOR_LENGTH` (`LENGTH`),
  KEY `IX_MOTOR_WEIGHT` (`WEIGHT`),
  KEY `IX_MOTOR_RATED_VOLTAGE` (`RATED_VOLTAGE`),
  KEY `IX_MOTOR_RATED_CURRENT` (`RATED_CURRENT`),
  KEY `IX_MOTOR_RATED_POWER` (`RATED_POWER`),
  KEY `IX_MOTOR_RATED_TORQUE` (`RATED_TORQUE`),
  KEY `IX_MOTOR_RATED_ROTATING_SPEED` (`RATED_ROTATING_SPEED`),
  KEY `IX_MOTOR_RATED_LINEAR_SPEED` (`RATED_LINEAR_SPEED`),
  KEY `IX_MOTOR_PEAK_CURRENT` (`PEAK_CURRENT`),
  KEY `IX_MOTOR_PEAK_TORQUE` (`PEAK_TORQUE`),
  KEY `IX_MOTOR_MAX_SORTING_WEIGHT` (`MAX_SORTING_WEIGHT`),
  KEY `FK_DC_MOTOR_SERIES` (`SERIES`),
  CONSTRAINT `FK_DC_MOTOR_CATEGORY` FOREIGN KEY (`CATEGORY`) REFERENCES `MOTOR_CATEGORY` (`CATEGORY`),
  CONSTRAINT `FK_DC_MOTOR_SERIES` FOREIGN KEY (`SERIES`) REFERENCES `MOTOR_SERIES` (`SERIES`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DC_MOTOR`
--

LOCK TABLES `DC_MOTOR` WRITE;
/*!40000 ALTER TABLE `DC_MOTOR` DISABLE KEYS */;
INSERT INTO `DC_MOTOR` VALUES (1,0,'2024-08-10 01:04:13','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB057GA100','Internal Rotor BLDC Motor',NULL,57.00,'mm','φ',23,54.00,'mm',0.50,'kg',60.00,'V',1.2000,'A',55.00,'W',0.3300,'Nm',4750,'rpm',NULL,NULL,3.6000,'A',0.3300,'Nm',NULL,NULL,NULL,NULL,NULL,NULL,'MB057GA'),(2,0,'2024-08-10 01:18:31','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB057GA200','Internal Rotor BLDC Motor',NULL,57.00,'mm','φ',23,74.00,'mm',0.75,'kg',60.00,'V',2.3000,'A',105.00,'W',0.2000,'Nm',5000,'rpm',NULL,NULL,6.9000,'A',0.6000,'Nm',NULL,NULL,NULL,NULL,NULL,NULL,'MB057GA'),(3,0,'2024-08-10 01:21:31','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB057GA300','Internal Rotor BLDC Motor',NULL,57.00,'mm','φ',23,94.00,'mm',1.00,'kg',60.00,'V',3.6000,'A',162.00,'W',0.3100,'Nm',5000,'rpm',NULL,NULL,10.8000,'A',0.9300,'Nm',NULL,NULL,NULL,NULL,NULL,NULL,'MB057GA'),(4,0,'2024-08-10 01:24:24','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB057GA400','Internal Rotor BLDC Motor',NULL,57.00,'mm','φ',23,114.00,'mm',1.25,'kg',60.00,'V',4.6000,'A',209.00,'W',0.4000,'Nm',5000,'rpm',NULL,NULL,13.8000,'A',1.2000,'Nm',NULL,NULL,NULL,NULL,NULL,NULL,'MB057GA'),(5,0,'2024-08-10 01:29:09','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB059AH100','Internal Rotor BLDC Motor',NULL,59.00,'mm','φ',NULL,53.60,'mm',0.52,'kg',24.00,'V',4.7000,'A',84.00,'W',0.2300,'Nm',3500,'rpm',NULL,NULL,14.1000,'A',0.6900,'Nm',NULL,NULL,NULL,NULL,NULL,NULL,'MB059AH'),(6,0,'2024-08-10 01:32:27','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB059AH200','Internal Rotor BLDC Motor',NULL,59.00,'mm','φ',NULL,68.60,'mm',0.65,'kg',24.00,'V',7.5000,'A',136.00,'W',0.3700,'Nm',3500,'rpm',NULL,NULL,22.5000,'A',1.1100,'Nm',NULL,NULL,NULL,NULL,NULL,NULL,'MB059AH'),(7,0,'2024-08-10 01:35:58','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB059AH300','Internal Rotor BLDC Motor',NULL,59.00,'mm','φ',NULL,73.60,'mm',0.72,'kg',24.00,'V',9.6000,'A',172.00,'W',0.4700,'Nm',3500,'rpm',NULL,NULL,28.8000,'A',1.4100,'Nm',NULL,NULL,NULL,NULL,NULL,NULL,'MB059AH'),(8,0,'2024-08-10 01:39:00','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB059AH400','Internal Rotor BLDC Motor',NULL,59.00,'mm','φ',NULL,93.60,'mm',0.95,'kg',24.00,'V',12.2000,'A',220.00,'W',0.6000,'Nm',3500,'rpm',NULL,NULL,36.6000,'A',1.8000,'Nm',NULL,NULL,NULL,NULL,NULL,NULL,'MB059AH'),(9,0,'2024-08-10 01:40:38','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB070GA100','Internal Rotor BLDC Motor',NULL,70.00,'mm','φ',NULL,95.00,'mm',1.00,'kg',24.00,'V',4.3000,'A',85.00,'W',0.2150,'Nm',3800,'rpm',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0.7500,'A',4760,'rpm','MB070GA'),(10,0,'2024-08-10 01:43:38','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB070GA200','Internal Rotor BLDC Motor',NULL,70.00,'mm','φ',NULL,106.00,'mm',1.30,'kg',24.00,'V',5.0000,'A',95.00,'W',0.2850,'Nm',3200,'rpm',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0.6800,'A',3920,'rpm','MB070GA'),(11,0,'2024-08-10 01:45:14','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB070GA300','Internal Rotor BLDC Motor',NULL,70.00,'mm','φ',NULL,123.00,'mm',1.60,'kg',24.00,'V',5.5000,'A',110.00,'W',0.3550,'Nm',3000,'rpm',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0.6900,'A',3580,'rpm','MB070GA'),(12,0,'2024-08-10 01:48:12','2024-08-28 19:21:39','BLDC_INTERNAL_ROTOR','MB082GA100','Internal Rotor BLDC Motor',NULL,82.00,'mm','□',NULL,78.00,'mm',1.50,'kg',170.00,'V',3.0000,'A',314.00,'W',0.6000,'Nm',5000,'rpm',NULL,NULL,9.1000,'A',1.9100,'Nm',NULL,NULL,NULL,NULL,NULL,NULL,'MB082GA'),(13,0,'2024-12-09 14:42:50',NULL,'BLDC_SERVO','SE060AS100','BLDC Servo Motor',NULL,60.00,'mm','□',NULL,101.00,'mm',0.85,'kg',36.00,'V',3.7000,'A',100.00,'W',0.3200,'Nm',3000,'rpm',NULL,NULL,11.1000,'A',0.9600,'Nm',NULL,NULL,NULL,NULL,NULL,NULL,'SE060AS');
/*!40000 ALTER TABLE `DC_MOTOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DC_MOTOR_PERF_MEASUREMENT`
--

DROP TABLE IF EXISTS `DC_MOTOR_PERF_MEASUREMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DC_MOTOR_PERF_MEASUREMENT` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `MOTOR_ID` int unsigned NOT NULL,
  `TITLE` varchar(64) DEFAULT NULL,
  `VARIABLES` varchar(128) DEFAULT NULL,
  `VALUES` varchar(2048) DEFAULT NULL,
  `CONDITIONS` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_MOTOR_PERF_STAT_MOTOR_ID` (`MOTOR_ID`),
  CONSTRAINT `FK_DC_MOTOR_PERF_STAT_MOTOR_ID` FOREIGN KEY (`MOTOR_ID`) REFERENCES `DC_MOTOR` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DC_MOTOR_PERF_MEASUREMENT`
--

LOCK TABLES `DC_MOTOR_PERF_MEASUREMENT` WRITE;
/*!40000 ALTER TABLE `DC_MOTOR_PERF_MEASUREMENT` DISABLE KEYS */;
INSERT INTO `DC_MOTOR_PERF_MEASUREMENT` VALUES (2,1,'MB057GA100 @ 60 Vdc','Speed(Krpm), Torque(oz-in), Torque(Ncm)','0,21.876,41.768\n0.5,21.601,41.053\n0.75,21.450,40.297\n1,21.291,39.462\n1.5,20.945,37.500\n1.75,20.758,36.335\n2,20.561,35.013\n2.25,20.354,33.500\n2.75,19.909,29.693\n3,19.671,27.241\n3.5,19.158,21.490\n3.75,18.884,18.930\n4,18.596,16.600\n4.5,17.726,12.517\n4.75,15.182,10.721\n5,12.833,9.062\n5.5,8.642,6.102\n6,5.013,3.540',NULL),(3,2,'MB057GA200 @ 60 Vdc','Speed(Krpm), Torque(oz-in), Torque(Ncm)','0, 40.363, 79.593\n0.5, 39.895, 78.455\n0.75, 39.636, 77.255\n1, 39.360, 75.938\n1.5, 38.757, 72.872\n1.75, 38.428, 71.073\n2, 38.080, 69.052\n2.25, 37.714, 66.764\n2.75, 36.921, 61.140\n3, 36.494, 57.626\n3.25, 36.045, 53.467\n3.5, 35.573, 48.459\n3.75, 35.078, 42.700\n4.25, 34.014, 32.529\n4.75, 32.842, 24.040\n5, 28.753, 20.304\n5.5, 19.346, 13.661\n6, 11.239, 7.936',NULL),(4,3,'MB057GA300 @ 60 Vdc','Speed(Krpm), Torque(oz-in), Torque(Ncm)','0.000,59.560,115.27\n0.492,58.874,113.85\n0.738,58.486,112.36\n1.229,57.619,108.97\n1.475,57.137,107.02\n1.721,56.623,104.87\n2.213,55.493,99.81\n2.458,54.875,96.81\n2.704,54.220,93.41\n3.196,52.794,85.06\n3.442,52.020,79.85\n3.933,50.338,66.29\n4.179,49.427,57.99\n4.671,47.449,43.44\n4.917,46.377,37.09\n5.408,36.631,25.87\n5.900,23.044,16.27\n',NULL),(5,4,'MB057GA400 @ 60 Vdc','Speed(Krpm), Torque(oz-in), Torque(Ncm)','0.000,74.591,134.40\n0.417,73.886,133.07\n0.625,73.487,131.69\n1.042,72.593,128.64\n1.458,71.569,125.14\n1.875,70.408,121.08\n2.083,69.774,118.79\n2.292,69.103,116.30\n2.500,68.394,113.58\n2.708,67.646,110.60\n2.917,66.858,107.32\n3.125,66.028,103.69\n3.542,64.235,95.13\n3.750,63.268,90.03\n4.167,61.185,77.61\n4.375,60.063,70.02\n4.792,57.641,55.82\n5.000,56.334,49.51\n',NULL),(6,5,'MB059AH100 @ 24 Vdc','Torque(Nm), Current(A), Speed(rpm)','0.00,0.37,4764\n0.02,0.7,4662\n0.04,1.03,4557\n0.05,1.29,4473\n0.09,1.98,4258\n0.13,2.56,4074\n0.17,3.33,3834\n0.20,3.76,3698\n0.21,4.06,3603\n0.23,4.37,3504\n0.26,4.9,3339\n0.29,5.31,3210\n0.30,5.63,3110\n0.32,5.9,3025\n',NULL),(7,6,'MB059AH200 @ 24 Vdc','Torque(Nm), Current(A), Speed(rpm)','0,0.70,4700\n0.062,1.79,4499\n0.12,2.81,4311\n0.18,3.86,4116\n0.25,5.09,3889\n0.31,6.15,3695\n0.37,7.20,3500\n0.42,8.08,3338\n0.49,9.31,3111\n0.55,10.36,2916\n0.61,11.42,2722\n0.67,12.47,2527\n0.73,13.52,2333\n0.79,14.58,2138\n0.85,15.63,1943\n',NULL),(8,7,'MB059AH300 @ 24 Vdc','Torque(Nm), Current(A), Speed(rpm)','0.00,0.55,4560\n0.07,1.69,4399\n0.09,2.06,4345\n0.13,2.76,4246\n0.15,3.19,4186\n0.19,3.85,4092\n0.27,5.25,3894\n0.32,6.09,3774\n0.40,7.56,3566\n0.42,7.99,3505\n0.47,8.81,3389\n0.50,9.23,3328\n0.52,9.57,3281\n0.52,9.67,3266\n',NULL),(9,8,'MB059AH400 @ 24 Vdc','Torque(Nm), Current(A), Speed(rpm)','0.00,0.8,4360\n0.10,2.5,4179\n0.21,4.3,4002\n0.30,5.9,3838\n0.40,7.6,3662\n0.50,9.3,3488\n0.60,10.8,3316\n0.70,12.6,3137\n0.80,14.3,2968\n0.90,15.9,2794\n1.03,18,2577\n',NULL),(10,12,'MB082GA100 @ 170 Vdc','Speed(Krpm), Torque(oz-in), Torque(Ncm)','0,86.1,190.94\n0.1,86.1,190.94\n0.2,86.1,190.94\n0.3,86.1,190.94\n0.4,86.1,190.94\n0.5,86,190.94\n0.6,86,190.94\n0.7,86,190.94\n0.8,86,190.94\n0.9,86,190.94\n1,85.9,190.94\n1.5,85.8,190.94\n2,85.7,190.94\n2.5,85.6,177.10\n3,85.5,157.54\n3.5,85.4,138.05\n4,85.3,118.49\n4.5,85.2,99.00\n5,85.1,79.51\n5.5,84.9,59.95\n6,57.3,40.46\n',NULL),(11,13,'SE060AS100 @ 48 Vdc','Torque(Nm), Speed(rpm), Torque(Nm), Speed(rpm)','1.92,0,0.64,0\n1.92,2300,0.64,4200\n0.64,4200,,\n0,5000,,\n',NULL);
/*!40000 ALTER TABLE `DC_MOTOR_PERF_MEASUREMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DC_MOTOR_SPEC`
--

DROP TABLE IF EXISTS `DC_MOTOR_SPEC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DC_MOTOR_SPEC` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `MOTOR_ID` int unsigned NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `UNIT` varchar(16) DEFAULT NULL,
  `VALUE` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_MOTOR_SPEC_MOTOR_ID` (`MOTOR_ID`),
  KEY `IX_MOTOR_SPEC_NAME` (`NAME`),
  CONSTRAINT `FK_DC_MOTOR_SPEC_MOTOR_ID` FOREIGN KEY (`MOTOR_ID`) REFERENCES `DC_MOTOR` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DC_MOTOR_SPEC`
--

LOCK TABLES `DC_MOTOR_SPEC` WRITE;
/*!40000 ALTER TABLE `DC_MOTOR_SPEC` DISABLE KEYS */;
INSERT INTO `DC_MOTOR_SPEC` VALUES (1,1,'Torque Constant','Nm/A','0.084'),(2,1,'Ke(RMS)','V/krpm','6.2'),(3,1,'Rotor Inertia','Kg.cm²','0.09'),(4,1,'Line to Line Resistance','Ω','4.47'),(5,1,'Line to Line Inductance','mH','6.86'),(6,2,'Torque Constant','Nm/A','0.084'),(7,2,'Ke(RMS)','V/krpm','6.2'),(8,2,'Rotor Inertia','Kg.cm²','0.169'),(9,2,'Line to Line Resistance','Ω','1.79'),(10,2,'Line to Line Inductance','mH','3.16'),(11,3,'Torque Constant','Nm/A','0.083'),(12,3,'Ke(RMS)','V/krpm','6.2'),(13,3,'Rotor Inertia','Kg.cm²','0.249'),(14,3,'Line to Line Resistance','Ω','0.83'),(15,3,'Line to Line Inductance','mH','1.98'),(16,4,'Torque Constant','Nm/A','0.077'),(17,4,'Ke(RMS)','V/krpm','5.7'),(18,4,'Rotor Inertia','Kg.cm²','0.328'),(19,4,'Line to Line Resistance','Ω','0.49'),(20,4,'Line to Line Inductance','mH','1.6'),(21,5,'Torque Constant','Nm/A','0.048'),(22,5,'Ke(RMS)','V/krpm','3.5'),(23,5,'Rotor Inertia','Kg.cm²','0.073'),(24,5,'Line to Line Resistance','Ω','0.57'),(25,5,'Line to Line Inductance','mH','0.63'),(26,5,'Insulation Class',NULL,'B'),(27,6,'Torque Constant','Nm/A','0.048'),(28,6,'Ke(RMS)','V/krpm','3.5'),(29,6,'Rotor Inertia','Kg.cm²','0.105'),(30,6,'Line to Line Resistance','Ω','0.24'),(31,6,'Line to Line Inductance','mH','0.29'),(32,6,'Insulation Class',NULL,'B'),(33,7,'Torque Constant','Nm/A','0.051'),(34,7,'Ke(RMS)','V/krpm','3.8'),(35,7,'Rotor Inertia','Kg.cm²','0.119'),(36,7,'Line to Line Resistance','Ω','0.22'),(37,7,'Line to Line Inductance','mH','0.29'),(38,7,'Insulation Class',NULL,'B'),(39,8,'Torque Constant','Nm/A','0.050'),(40,8,'Ke(RMS)','V/krpm','3.7'),(41,8,'Rotor Inertia','Kg.cm²','0.173'),(42,8,'Line to Line Resistance','Ω','0.14'),(43,8,'Line to Line Inductance','mH','0.20'),(44,8,'Insulation Class',NULL,'B'),(45,9,'Max. Continuous Torque','Nm','0.3'),(46,9,'Max. Continuous Current','A','5.8'),(47,9,'Rotor Inertia','Kg.cm²','0.3'),(48,10,'Max. Continuous Torque','Nm','0.34'),(49,10,'Max. Continuous Current','A','5.8'),(50,10,'Rotor Inertia','Kg.cm²','0.4'),(51,11,'Max. Continuous Torque','Nm','0.38'),(52,11,'Max. Continuous Current','A','5.8'),(53,11,'Rotor Inertia','Kg.cm²','0.5'),(54,12,'Torque Constant','Nm/A','0.2280'),(55,12,'Ke(RMS)','V/krpm','16.90'),(56,12,'Line to Line Resistance','Ω','2.01'),(57,12,'Line to Line Inductance','mH','8.46'),(58,12,'Rotor Inertia','Kg.cm²','0.678'),(59,12,'Insulation Class',NULL,'F'),(62,13,'Torque Constant','Nm/A','0.057'),(63,13,'Ke(RMS)','V/krpm','4.2'),(64,13,'Line to Line Resistance','Ω','0.64'),(65,13,'Line to Line Inductance','mH','0.5'),(66,13,'Rotor Inertia','Kg.cm²','0.1'),(67,13,'No. of Poles',NULL,'8'),(68,13,'Feedback Device (Optical encoder)','PPR','2500');
/*!40000 ALTER TABLE `DC_MOTOR_SPEC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GEARBOX_SERIES`
--

DROP TABLE IF EXISTS `GEARBOX_SERIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GEARBOX_SERIES` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `SERIES` varchar(32) NOT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `TECHNICAL_DATA` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_GEARBOX_SERIES_SERIES` (`SERIES`),
  KEY `IX_GEARBOX_SERIES_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_GEARBOX_SERIES_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GEARBOX_SERIES`
--

LOCK TABLES `GEARBOX_SERIES` WRITE;
/*!40000 ALTER TABLE `GEARBOX_SERIES` DISABLE KEYS */;
INSERT INTO `GEARBOX_SERIES` VALUES (1,0,'2024-09-01 10:42:26',NULL,'PL030GE','','{\"Efficiency\": \"66-90%\", \"No. of Stages\": \"1-4\", \"Ratio\": \"1/3.7-1/939\", \"Continuous Torque\": \"0.3-3.0 Nm\", \"Peak Torque\": \"1.0-9.0 Nm\", \"Length\": \"29.0-49.9 mm\"}'),(2,0,'2024-09-01 10:42:26',NULL,'PL032GE','','{\"Efficiency\": \"66-90%\", \"No. of Stages\": \"1-4\", \"Ratio\": \"1/3.7-1/939\", \"Continuous Torque\": \"0.5-5.0 Nm\", \"Peak Torque\": \"1.5-12.0 Nm\", \"Length\": \"25.7-42.6 mm\"}'),(3,0,'2024-09-01 10:42:26',NULL,'PL036GF','','{\"Efficiency\": \"66-90%\", \"No. of Stages\": \"1-4\", \"Ratio\": \"1/3.7-1/596\", \"Continuous Torque\": \"0.5-5.0 Nm\", \"Peak Torque\": \"1.5-12.0 Nm\", \"Length\": \"29.0-53.0 mm\"}'),(4,0,'2024-09-01 10:42:26',NULL,'PL042GF','','{\"Efficiency\": \"66-90%\", \"No. of Stages\": \"1-4\", \"Ratio\": \"1/3.7-1/596\", \"Continuous Torque\": \"1.5-15.0 Nm\", \"Peak Torque\": \"3.0-38.0 Nm\", \"Length\": \"31.5-62.9 mm\"}'),(5,0,'2024-09-01 10:42:26',NULL,'PL042GE','','{\"Efficiency\": \"66-90%\", \"No. of Stages\": \"1-4\", \"Ratio\": \"1/3.7-1/515\", \"Continuous Torque\": \"2.0-20.0 Nm\", \"Peak Torque\": \"6.0-60.0 Nm\", \"Length\": \"37.5-68.6 mm\"}'),(6,0,'2024-09-01 10:42:26',NULL,'PL045GE','','{\"Efficiency\": \"66-90%\", \"No. of Stages\": \"1-4\", \"Ratio\": \"1/3.7-1/721\", \"Continuous Torque\": \"1.0-10.0 Nm\", \"Peak Torque\": \"3.0-30.0 Nm\", \"Length\": \"41.3-74.7 mm\"}'),(7,0,'2024-09-01 10:42:26',NULL,'PL052GE','','{\"Efficiency\": \"66-90%\", \"No. of Stages\": \"1-4\", \"Ratio\": \"1/3.6-1/307\", \"Continuous Torque\": \"2-20 Nm\", \"Peak Torque\": \"6-60 Nm\", \"Length\": \"45.7-79.8 mm\"}'),(8,0,'2024-09-01 10:42:26',NULL,'PL056GE','','{\"Efficiency\": \"66-90%\", \"No. of Stages\": \"1-4\", \"Ratio\": \"1/3.6-1/400\", \"Continuous Torque\": \"3-45 Nm\", \"Peak Torque\": \"6-110 Nm\", \"Length\": \"40.3-81.4 mm\"}'),(9,0,'2024-09-01 10:42:26',NULL,'PL062GE','','{\"Efficiency\": \"66-90%\", \"No. of Stages\": \"1-4\", \"Ratio\": \"1/3.6-1/307\", \"Continuous Torque\": \"4-45 Nm\", \"Peak Torque\": \"12-110 Nm\", \"Length\": \"42.3-82.8 mm\"}'),(10,0,'2024-09-01 10:42:26',NULL,'PL080GE','','{\"Efficiency\": \"73-90%\", \"No. of Stages\": \"1-4\", \"Ratio\": \"1/3.55-1/138\", \"Continuous Torque\": \"8-80 Nm\", \"Peak Torque\": \"12-120 Nm\", \"Length\": \"55.7-88.5 mm\"}');
/*!40000 ALTER TABLE `GEARBOX_SERIES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `INQUIRY`
--

DROP TABLE IF EXISTS `INQUIRY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `INQUIRY` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int unsigned DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `CONTACT_NAME` varchar(64) DEFAULT NULL,
  `CONTACT_EMAIL` varchar(64) DEFAULT NULL,
  `CONTACT_PHONE` varchar(64) DEFAULT NULL,
  `REF_MODEL` varchar(64) DEFAULT NULL,
  `QUANTITY` int unsigned DEFAULT NULL,
  `CONTENT` varchar(1024) DEFAULT NULL,
  `REF_LINK` varchar(256) DEFAULT NULL,
  `CLIENT_IP_ADDRESS` varchar(64) DEFAULT NULL,
  `CLIENT_COUNTRY_OR_REGION` varchar(64) DEFAULT NULL,
  `STATUS` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_INQUIRY_REF_MODEL` (`REF_MODEL`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `INQUIRY`
--

LOCK TABLES `INQUIRY` WRITE;
/*!40000 ALTER TABLE `INQUIRY` DISABLE KEYS */;
INSERT INTO `INQUIRY` VALUES (1,0,NULL,NULL,'Test Rahaul Choudhary','lc599024@gmail.com','7087422405','ME057AH200',2,'Electronic P/N ME057AH200 REV:P2 24VDC 8NM 6RPM','https://www.exmek.com/motors/ME057AH200','156.93.246.30','United States',NULL);
/*!40000 ALTER TABLE `INQUIRY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LEAD_DEF`
--

DROP TABLE IF EXISTS `LEAD_DEF`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LEAD_DEF` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `CODE` varchar(16) NOT NULL,
  `SCREW_DIAMETER_INCH` decimal(8,4) NOT NULL,
  `SCREW_DIAMETER_MM` decimal(8,4) NOT NULL,
  `LEAD_INCH` decimal(8,4) NOT NULL,
  `LEAD_MM` decimal(8,4) NOT NULL,
  `THREADS` int NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_LEAD_DEF_CODE` (`CODE`),
  KEY `IX_LEAD_DEF_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_LEAD_DEF_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LEAD_DEF`
--

LOCK TABLES `LEAD_DEF` WRITE;
/*!40000 ALTER TABLE `LEAD_DEF` DISABLE KEYS */;
INSERT INTO `LEAD_DEF` VALUES (1,0,'2024-08-11 16:16:26',NULL,'B0024',0.1380,3.5050,0.0240,0.6096,1),(2,0,'2024-08-11 16:16:26',NULL,'B0048',0.1380,3.5050,0.0480,1.2192,1),(3,0,'2024-08-11 16:16:26',NULL,'B0079',0.1380,3.5050,0.0790,2.0000,2),(4,0,'2024-08-11 16:16:26',NULL,'B0157',0.1380,3.5050,0.1570,4.0000,4),(5,0,'2024-08-11 16:16:26',NULL,'B0315',0.1380,3.5050,0.3150,8.0000,8),(6,0,'2024-08-11 16:16:26',NULL,'D0025',0.1880,4.7700,0.0250,0.6350,1),(7,0,'2024-08-11 16:16:26',NULL,'D0050',0.1880,4.7700,0.0500,1.2700,1),(8,0,'2024-08-11 16:16:26',NULL,'D0100',0.1880,4.7700,0.1000,2.5400,2),(9,0,'2024-08-11 16:16:26',NULL,'D0200',0.1880,4.7700,0.2000,5.0800,4),(10,0,'2024-08-11 16:16:26',NULL,'D0400',0.1880,4.7700,0.4000,10.1600,8),(11,0,'2024-08-11 16:16:26',NULL,'E0079',0.1970,5.0000,0.0790,2.0000,1),(12,0,'2024-08-11 16:16:26',NULL,'F0192',0.2190,5.5600,0.1920,4.8768,4),(13,0,'2024-08-11 16:16:26',NULL,'H0024',0.2500,6.3500,0.0240,0.6096,1),(14,0,'2024-08-11 16:16:26',NULL,'H0050',0.2500,6.3500,0.0500,1.2700,1),(15,0,'2024-08-11 16:16:26',NULL,'H0063',0.2500,6.3500,0.0630,1.5875,1),(16,0,'2024-08-11 16:16:26',NULL,'H0096',0.2500,6.3500,0.0960,2.4384,2),(17,0,'2024-08-11 16:16:26',NULL,'H0100',0.2500,6.3500,0.1000,2.5400,2),(18,0,'2024-08-11 16:16:26',NULL,'H0125',0.2500,6.3500,0.1250,3.1750,2),(19,0,'2024-08-11 16:16:26',NULL,'H0192',0.2500,6.3500,0.1920,4.8768,4),(20,0,'2024-08-11 16:16:26',NULL,'H0200',0.2500,6.3500,0.2000,5.0800,4),(21,0,'2024-08-11 16:16:26',NULL,'H0250',0.2500,6.3500,0.2500,6.3500,4),(22,0,'2024-08-11 16:16:26',NULL,'H0384',0.2500,6.3500,0.3840,9.7536,6),(23,0,'2024-08-11 16:16:26',NULL,'H0500',0.2500,6.3500,0.5000,12.7000,8),(24,0,'2024-08-11 16:16:26',NULL,'H1000',0.2500,6.3500,1.0000,25.4000,8),(25,0,'2024-08-11 16:16:26',NULL,'J0059',0.3150,8.0000,0.0590,1.5000,1),(26,0,'2024-08-11 16:16:26',NULL,'J0079',0.3150,8.0000,0.0790,2.0000,1),(27,0,'2024-08-11 16:16:26',NULL,'J0157',0.3150,8.0000,0.1570,4.0000,2),(28,0,'2024-08-11 16:16:26',NULL,'J0315',0.3150,8.0000,0.3150,8.0000,4),(29,0,'2024-08-11 16:16:26',NULL,'J0470',0.3150,8.0000,0.4700,12.0000,6),(30,0,'2024-08-11 16:16:26',NULL,'K0050',0.3750,9.5250,0.0500,1.2700,1),(31,0,'2024-08-11 16:16:26',NULL,'K0100',0.3750,9.5250,0.1000,2.5400,1),(32,0,'2024-08-11 16:16:26',NULL,'K0125',0.3750,9.5250,0.1250,3.1750,1),(33,0,'2024-08-11 16:16:26',NULL,'K0200',0.3750,9.5250,0.2000,5.0800,4),(34,0,'2024-08-11 16:16:26',NULL,'K0250',0.3750,9.5250,0.2500,6.3500,4),(35,0,'2024-08-11 16:16:26',NULL,'K0375',0.3750,9.5250,0.3750,9.5250,4),(36,0,'2024-08-11 16:16:26',NULL,'K0400',0.3750,9.5250,0.4000,10.1600,8),(37,0,'2024-08-11 16:16:26',NULL,'K0500',0.3750,9.5250,0.5000,12.7000,4),(38,0,'2024-08-11 16:16:26',NULL,'K1000',0.3750,9.5250,1.0000,25.4000,8),(39,0,'2024-08-11 16:16:26',NULL,'L0079',0.3940,10.0000,0.0790,2.0000,1),(40,0,'2024-08-11 16:16:26',NULL,'L0157',0.3940,10.0000,0.1570,4.0000,2),(41,0,'2024-08-11 16:16:26',NULL,'P0100',0.6250,15.8750,0.1000,2.5400,1),(42,0,'2024-08-11 16:16:26',NULL,'P0125',0.6250,15.8750,0.1250,3.1750,1),(43,0,'2024-08-11 16:16:26',NULL,'P0200',0.6250,15.8750,0.2000,5.0800,2),(44,0,'2024-08-11 16:16:26',NULL,'P0250',0.6250,15.8750,0.2500,6.3500,2),(45,0,'2024-08-11 16:16:26',NULL,'P0500',0.6250,15.8750,0.5000,12.7000,4),(46,0,'2024-08-11 16:16:26',NULL,'P1000',0.6250,15.8750,1.0000,25.4000,8);
/*!40000 ALTER TABLE `LEAD_DEF` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LINEAR_STEPPER_MOTOR_LEAD`
--

DROP TABLE IF EXISTS `LINEAR_STEPPER_MOTOR_LEAD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LINEAR_STEPPER_MOTOR_LEAD` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `MOTOR_ID` int unsigned NOT NULL,
  `LEAD_ID` int unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_MOTORLEAD_LEAD_ID` (`LEAD_ID`),
  KEY `IX_MOTORLEAD_MOTOR_ID` (`MOTOR_ID`),
  CONSTRAINT `FK_LS_MOTOR_LEAD_MOTOR_ID` FOREIGN KEY (`MOTOR_ID`) REFERENCES `STEPPER_MOTOR` (`ID`),
  CONSTRAINT `FK_MOTORLEAD_LEAD_ID` FOREIGN KEY (`LEAD_ID`) REFERENCES `LEAD_DEF` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LINEAR_STEPPER_MOTOR_LEAD`
--

LOCK TABLES `LINEAR_STEPPER_MOTOR_LEAD` WRITE;
/*!40000 ALTER TABLE `LINEAR_STEPPER_MOTOR_LEAD` DISABLE KEYS */;
INSERT INTO `LINEAR_STEPPER_MOTOR_LEAD` VALUES (1,3,1),(2,3,2),(3,3,3),(5,3,4),(6,3,5),(8,4,1),(9,4,2),(10,4,3),(11,4,4),(12,4,5);
/*!40000 ALTER TABLE `LINEAR_STEPPER_MOTOR_LEAD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MOTOR_CATEGORY`
--

DROP TABLE IF EXISTS `MOTOR_CATEGORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MOTOR_CATEGORY` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `CATEGORY` varchar(32) NOT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `TECHNICAL_DATA` varchar(256) DEFAULT NULL,
  `DISPLAY_NAME` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_MOTOR_CATEGORY_CATEGORY` (`CATEGORY`),
  KEY `IX_MOTOR_CATEGORY_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_MOTOR_CATEGORY_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MOTOR_CATEGORY`
--

LOCK TABLES `MOTOR_CATEGORY` WRITE;
/*!40000 ALTER TABLE `MOTOR_CATEGORY` DISABLE KEYS */;
INSERT INTO `MOTOR_CATEGORY` VALUES (71,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','','','Internal Rotor BLDC motor'),(72,0,'2024-08-31 20:48:15',NULL,'BLDC_EXTERNAL_ROTOR','','','External Rotor BLDC motor'),(73,0,'2024-08-31 20:48:15',NULL,'BLDC_FRAMELESS','','','Frameless BLDC motor'),(74,0,'2024-08-31 20:48:15',NULL,'BLDC_CORELESS','','','Coreless BLDC motor'),(75,0,'2024-08-31 20:48:15',NULL,'BLDC_SERVO','','','BLDC Servo motor'),(76,0,'2024-08-31 20:48:15',NULL,'BLDC_WITH_GEARBOX','','','BLDC motor with gearbox'),(77,0,'2024-08-31 20:48:15',NULL,'BLDC_DIRECT_DRIVE','','','Direct-drive Brushless motor'),(78,0,'2024-08-31 20:48:15',NULL,'PERMANENT_MAGNET_BRUSH','','','Permanent Magnet Brush motor'),(79,0,'2024-08-31 20:48:15',NULL,'BRUSH_WITH_GEARBOX','','','Brush motor with gearbox'),(80,0,'2024-08-31 20:48:15',NULL,'INTEGRATED','','','Integrated motor'),(81,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','','','Standard torque Stepper motor'),(82,0,'2024-08-31 20:48:15',NULL,'STEPPER_FLAT','','','Flat Stepper motor'),(83,0,'2024-08-31 20:48:15',NULL,'STEPPER_WITH_CONTROL','','','Stepper motor with control'),(84,0,'2024-08-31 20:48:15',NULL,'STEPPER_LINEAR','','','Linear Stepper motor'),(85,0,'2024-09-17 18:10:19',NULL,'SOLAR_TRACKING_APPLICATION','','','Solar Tracking Application'),(86,0,'2024-09-17 18:10:19',NULL,'MATERIAL_HANDLING_SOLUTION','','','Material Handling Solution');
/*!40000 ALTER TABLE `MOTOR_CATEGORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MOTOR_CONFIG`
--

DROP TABLE IF EXISTS `MOTOR_CONFIG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MOTOR_CONFIG` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int unsigned DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `MODEL_REFS` varchar(512) NOT NULL,
  `CONFIG_NAME` varchar(64) NOT NULL,
  `CONFIG_VALUE` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MOTOR_CONFIG`
--

LOCK TABLES `MOTOR_CONFIG` WRITE;
/*!40000 ALTER TABLE `MOTOR_CONFIG` DISABLE KEYS */;
INSERT INTO `MOTOR_CONFIG` VALUES (5,0,'2024-12-08 22:45:42',NULL,'MB057GA*','curve.coordinates','[{\"name\": \"Tcont\", \"x\": \"Speed(Krpm)\", \"y\": \"Torque(oz-in)\"}, {\"name\": \"Tpeak\", \"x\": \"Speed(Krpm)\", \"y\": \"Torque(Ncm)\"}]'),(6,0,'2024-12-08 22:45:42',NULL,'MB059AH*','curve.coordinates','[{\"name\": \"\", \"x\": \"Torque(Nm)\", \"y\": \"Speed(rpm)\"}, {\"name\": \"\", \"x\": \"Torque(Nm)\", \"y\": \"Current(A)\"}]'),(7,0,'2024-12-08 22:45:42',NULL,'MB082GA*','curve.coordinates','[{\"name\": \"Torque(oz-in)\", \"x\": \"Speed(Krpm)\", \"y\": \"Torque(oz-in)\"}, {\"name\": \"Torque(Ncm)\", \"x\": \"Speed(Krpm)\", \"y\": \"Torque(Ncm)\"}]'),(8,0,'2024-12-08 22:45:42',NULL,'MDB56GS*,ME042AS*,ME042GS*,ME042WS*,ME042YS*,ME043AS*,ME057AH*,ME060AS*,MEO70AS*,ME080AS*,ME080RS*D','curve.coordinates','[{\"name\": \"\", \"x\": \"Torque(Nm)\", \"y\": \"Speed(rpm)\"}, {\"name\": \"\", \"x\": \"Torque(Nm)\", \"y\": \"Current(A)\"}]'),(9,0,'2024-12-09 14:51:31',NULL,'SE060AS*','curve.coordinates','[{\"name\": \"Tpeak\", \"x\": \"Speed(rpm)[0]\", \"y\": \"Torque(Nm)[0]\"}, {\"name\": \"Tcont\", \"x\": \"Speed(rpm)[1]\", \"y\": \"Torque(Nm)[1]\"}]');
/*!40000 ALTER TABLE `MOTOR_CONFIG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MOTOR_SERIES`
--

DROP TABLE IF EXISTS `MOTOR_SERIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MOTOR_SERIES` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `CATEGORY` varchar(32) NOT NULL,
  `SERIES` varchar(32) NOT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `TECHNICAL_DATA` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_MOTOR_SERIES_SERIES` (`SERIES`),
  KEY `IX_MOTOR_SERIES_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_MOTOR_SERIES_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`),
  KEY `IX_MOTOR_SERIES_CATEGORY` (`CATEGORY`),
  CONSTRAINT `FK_MOTOR_SERIES_CATEGORY` FOREIGN KEY (`CATEGORY`) REFERENCES `MOTOR_CATEGORY` (`CATEGORY`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MOTOR_SERIES`
--

LOCK TABLES `MOTOR_SERIES` WRITE;
/*!40000 ALTER TABLE `MOTOR_SERIES` DISABLE KEYS */;
INSERT INTO `MOTOR_SERIES` VALUES (1,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME042WS','Wide speed range, flat torque\nExcellent speed stability\nCompact and high power\nLow temperature rise, low noise, low vibration','{\"Rated Power\": \"30-90 W\", \"Rated Voltage\": \"24-48 VDC\", \"Rated Torque\": \"0.072-0.215 Nm\", \"Rated Speed\": \"4000 rpm\", \"Length\": \"46-100 mm\"}'),(2,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME036GA','Long Life\nDirect Replacement of Toy Motor\nEconomic Design for Power Tool','{\"Rated Power\": \"7.5-33 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.015-0.07 Nm\", \"Rated Speed\": \"4500-4800 rpm\", \"Length\": \"30-57 mm\"}'),(3,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME042AS','Low cogging\nHigh Power Density\nHigh Efficiency','{\"Rated Power\": \"31-107 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.30-1.20 Nm\", \"Rated Speed\": \"3000 rpm\", \"Length\": \"52-90 mm\"}'),(4,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME042GS','Nema 17 Mounting Interface\nEconomic Design for Volume Production\nBonded Neo Magnet','{\"Rated Power\": \"27-112 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.064-0.268 Nm\", \"Rated Speed\": \"4000 rpm\", \"Length\": \"41-100 mm\"}'),(5,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME042YS','Compact Design\nEconomic Design\nBonded Neo Magnet','{\"Rated Power\": \"32-71 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.077-0.169 Nm\", \"Rated Speed\": \"4000 rpm\", \"Length\": \"60-85 mm\"}'),(6,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME043AS','Sealed Housing Design\n120/60 degree hall effect\nSmooth Operation','{\"Rated Power\": \"9.5-24.5 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.038-0.078 Nm\", \"Rated Speed\": \"2400-3000 rpm\", \"Length\": \"47.4-66.4 mm\"}'),(7,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','MB057GA','High Reliable\nBonded Neo Magnet\nNema 23 Flange Available','{\"Rated Power\": \"55-209 W\", \"Rated Voltage\": \"60 VDC\", \"Rated Torque\": \"0.11-0.40 Nm\", \"Rated Speed\": \"4750-5000 rpm\", \"Length\": \"54-114 mm\"}'),(8,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','MEL57AH','Multiple Poles Design for Low speed\nSintered Neo Magnet\nNEMA 23 Flange Available','{\"Rated Power\": \"24-72 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.23-0.70 Nm\", \"Rated Speed\": \"1000 rpm\", \"Length\": \"51-91 mm\"}'),(9,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','MDB56GS','Low Cogging\nNEMA 23 Mounting Interface\nEconomic Design for High Power','{\"Rated Power\": \"47-188 W\", \"Rated Voltage\": \"36 VDC\", \"Rated Torque\": \"0.15-0.60 Nm\", \"Rated Speed\": \"3000 rpm\", \"Length\": \"56-116 mm\"}'),(10,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME057AH','High Efficiency\nHigh Voltage Capability\nHigh Power Density','{\"Rated Power\": \"195-358 W\", \"Rated Voltage\": \"170 VDC\", \"Rated Torque\": \"0.31-0.70 Nm\", \"Rated Speed\": \"6000 rpm\", \"Length\": \"71.1-127.9 mm\"}'),(11,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','MB059AH','High Power Density\nNema 23 Mounting Available\nSintered Neo Magnet','{\"Rated Power\": \"84-220 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.23-0.60 Nm\", \"Rated Speed\": \"3500 rpm\", \"Length\": \"53.6-93.6 mm\"}'),(12,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME060AS','12 slots design for low cogging\nSintered Neo Magnet\nEconomic Design for Direct Drive','{\"Rated Power\": \"79-236 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.25-0.75 Nm\", \"Rated Speed\": \"3000 rpm\", \"Length\": \"78-120 mm\"}'),(13,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','MB070GA','Low cogging\nSpecial Deisgn for Easy Production\nHigh Reliable','{\"Rated Power\": \"85-110 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.215-0.355 Nm\", \"Rated Speed\": \"3000-3800 rpm\", \"Length\": \"95-123 mm\"}'),(14,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME070AS','12 slots design for low cogging\nSintered Neo Magnet\nEconomic Design for Direct Drive','{\"Rated Power\": \"157-471 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"0.50-1.50 Nm\", \"Rated Speed\": \"3000 rpm\", \"Length\": \"88-148 mm\"}'),(15,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME080AS','8 Poles with 3 Phases\nLow Cogging\nHigh Power Density','{\"Rated Power\": \"314-628 W\", \"Rated Voltage\": \"310 VDC\", \"Rated Torque\": \"1.00-2.00 Nm\", \"Rated Speed\": \"3000 rpm\", \"Length\": \"111-164 mm\"}'),(16,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME080RS','4 poles with 3 phases\nSintered Neo Magnet\nEconomic Design for Direct Drive','{\"Rated Power\": \"157-236 W\", \"Rated Voltage\": \"24-48 VDC\", \"Rated Torque\": \"0.50-1.50 Nm\", \"Rated Speed\": \"1500-3000 rpm\", \"Length\": \"80-120 mm\"}'),(17,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','MB082GA','Low Cogging\nNEMA 34 Mounting Interface\nBonded Neo Magnet\nHigh Voltage Available','{\"Rated Power\": \"314-838 W\", \"Rated Voltage\": \"170 VDC\", \"Rated Torque\": \"0.60-2.00 Nm\", \"Rated Speed\": \"4000-6000 rpm\", \"Length\": \"78-141 mm\"}'),(18,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME086AS','8 poles with 3 phases\nEconomic Design for Simple Servo\nHigh Efficiency','{\"Rated Power\": \"220-660 W\", \"Rated Voltage\": \"310 VDC\", \"Rated Torque\": \"0.70-2.10 Nm\", \"Rated Speed\": \"3000 rpm\", \"Length\": \"135-189 mm\"}'),(19,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME110AS','8 poles with 3 phases\n18 slots design for low cogging\nHigh Power Density','{\"Rated Power\": \"628-1885 W\", \"Rated Voltage\": \"310 VDC\", \"Rated Torque\": \"2.00-6.00 Nm\", \"Rated Speed\": \"3000 rpm\", \"Length\": \"128-180 mm\"}'),(20,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','ME130AS','8 poles with 3 phases\n24 slots design for low cogging\nDirect Replacement of IEC AC motor','{\"Rated Power\": \"314-942 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"2.00-6.00 Nm\", \"Rated Speed\": \"1500 rpm\", \"Length\": \"105-165 mm\"}'),(21,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','MB120GA','Economic Design with Bonded Neo\nVoltage Rating up to 325VDC\nOptional; NEMA 56 mounting','{\"Rated Power\": \"1275-3194 W\", \"Rated Voltage\": \"325 VDC\", \"Rated Torque\": \"2.03-6.10 Nm\", \"Rated Speed\": \"5000-6000 rpm\", \"Length\": \"144.3-251 mm\"}'),(22,0,'2024-08-31 20:48:15',NULL,'BLDC_INTERNAL_ROTOR','MBH057GA','','{\"Rated Power\": \"250-520 W\", \"Rated Voltage\": \"70 VDC\", \"Rated Torque\": \"0.2-0.4 Nm\", \"Rated Speed\": \"12500 rpm\", \"Length\": \"54-94 mm\"}'),(23,0,'2024-08-31 20:48:15',NULL,'BLDC_EXTERNAL_ROTOR','EF045AS','16 poles with 3 phases\nCompact Design\nSmooth Operation under Low Speed','{\"Rated Power\": \"17-30 W\", \"Rated Voltage\": \"12-24 VDC\", \"Rated Torque\": \"50-55.5 mNm\", \"Rated Speed\": \"2940-5000 RPM\", \"Length\": \"16.4 mm\"}'),(24,0,'2024-08-31 20:48:15',NULL,'BLDC_EXTERNAL_ROTOR','EF048GA','10 Poles with 3 Phases\nEconomic Solution with Driver\nSpecial for Fan Application','{\"Rated Power\": \"50 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"165 mNm\", \"Rated Speed\": \"2900 RPM\", \"Length\": \"25 mm\"}'),(25,0,'2024-08-31 20:48:15',NULL,'BLDC_EXTERNAL_ROTOR','EF058GA','10 Poles with 3 Phases\nEconomic Solution with Driver\nSpecial for Fan Application','{\"Rated Power\": \"50 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"149 mNm\", \"Rated Speed\": \"3200 RPM\", \"Length\": \"28.3 mm\"}'),(26,0,'2024-08-31 20:48:15',NULL,'BLDC_FRAMELESS','MF180AS','High Overload capability\nLow Speed Design Available\nEconomic Design','{\"Rated Power\": \"190-630 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"6-20 Nm\", \"Rated Speed\": \"300 RPM\"}'),(27,0,'2024-08-31 20:48:15',NULL,'BLDC_FRAMELESS','TF060','High Power Density\nLow Cogging Torque\nHall Board available','{\"Rated Power\": \"149-319 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"0.71-1.69 Nm\", \"Rated Speed\": \"1800-2000 RPM\"}'),(28,0,'2024-08-31 20:48:15',NULL,'BLDC_FRAMELESS','TF076','High Power Density\nLow Cogging Torque\nHall Board available','{\"Rated Power\": \"149-319 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"0.71-1.69 Nm\", \"Rated Speed\": \"1800-2000 RPM\"}'),(29,0,'2024-08-31 20:48:15',NULL,'BLDC_FRAMELESS','MF080AS','Multipolar and high torque density\nLow Speed and High Torque\nLow Rotor Inertia\nHigh overload capability\nHall Board or encoder available','{\"Rated Power\": \"188-377 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"2.4-4.8 Nm\", \"Rated Speed\": \"750 RPM\"}'),(30,0,'2024-08-31 20:48:15',NULL,'BLDC_FRAMELESS','MF090AS','Multipolar and high torque density\nLow Cogging Torque\nLow torque ripple\nHigh overload capability\nHall Board or encoder available','{\"Rated Power\": \"251-503 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"3.2-6.4 Nm\", \"Rated Speed\": \"750 RPM\"}'),(31,0,'2024-08-31 20:48:15',NULL,'BLDC_FRAMELESS','MF130AS','Multipolar and high torque density\nLow Cogging Torque\nLow torque ripple\nHigh overload capability\nHall Board or encoder available','{\"Rated Power\": \"141-377 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"4.5-12 Nm\", \"Rated Speed\": \"300 RPM\"}'),(32,0,'2024-08-31 20:48:15',NULL,'BLDC_CORELESS','SLS','As High As 40000RPM Speed\nLow Temperature Rise\nHall Sensor Available','{\"Rated Power\": \"80-180 W\", \"Rated Voltage\": \"15-32 VDC\", \"Rated Torque\": \"16-150 mNm\", \"Rated Speed\": \"8000-50000 RPM\", \"Length\": \"40-59.5 mm\"}'),(33,0,'2024-08-31 20:48:15',NULL,'BLDC_SERVO','SEP040','','{\"Rated Power\": \"50-100 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"0.16-0.32 Nm\", \"Rated Speed\": \"3000 RPM\", \"Length\": \"49.5-63.5 mm\"}'),(34,0,'2024-08-31 20:48:15',NULL,'BLDC_SERVO','SEP060','','{\"Rated Power\": \"200-400 W\", \"Rated Voltage\": \"24-48 VDC\", \"Rated Torque\": \"0.64-1.27 Nm\", \"Rated Speed\": \"3000 RPM\", \"Length\": \"64.5-84.5 mm\"}'),(35,0,'2024-08-31 20:48:15',NULL,'BLDC_SERVO','SEP080','','{\"Rated Power\": \"500-800 W\", \"Rated Voltage\": \"24-48 VDC\", \"Rated Torque\": \"1.53-2.55 Nm\", \"Rated Speed\": \"3000 RPM\", \"Length\": \"75-92 mm\"}'),(36,0,'2024-08-31 20:48:15',NULL,'BLDC_SERVO','SEP110','','{\"Rated Power\": \"1320 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"4.2 Nm\", \"Rated Speed\": \"3000 RPM\", \"Length\": \"109 mm\"}'),(37,0,'2024-08-31 20:48:15',NULL,'BLDC_SERVO','SEP130','','{\"Rated Power\": \"3000 W\", \"Rated Voltage\": \"538 VDC\", \"Rated Torque\": \"14.33 Nm\", \"Rated Speed\": \"2000 RPM\", \"Length\": \"150.5 mm\"}'),(38,0,'2024-08-31 20:48:15',NULL,'BLDC_SERVO','SE080AS REDUCER','All-in-one compact design\nHigh efficiency, low noise and high reliable gearbox\nBrake available\nDrive solution for mobile robot (AGV/Forklift)','{\"Rated Power\": \"400 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"18.3 Nm\", \"Rated Speed\": \"187.5 RPM\", \"Length\": \"130 mm\"}'),(39,0,'2024-08-31 20:48:15',NULL,'BLDC_SERVO','SE060AS','8 Poles with 3 Phases\nLow Cogging\nHigh Power Density','{\"Rated Power\": \"124-410 W\", \"Rated Voltage\": \"36-48 VDC\", \"Rated Torque\": \"0.3-1.3 Nm\", \"Rated Speed\": \"3000-4000 RPM\", \"Length\": \"101-141 mm\"}'),(40,0,'2024-08-31 20:48:15',NULL,'BLDC_SERVO','SE080AS','8 Poles with 3 Phases\nLow Cogging\nHigh Power Density','{\"Rated Power\": \"411-765 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"1.31-2.44 Nm\", \"Rated Speed\": \"3000 RPM\", \"Length\": \"132-153 mm\"}'),(41,0,'2024-08-31 20:48:15',NULL,'BLDC_WITH_GEARBOX','ME032RS100-SI0020','Direct Replacement of PMDC motor\nEconomic Solution\nLong Life','{\"Rated Power\": \"2.3 W\", \"Rated Voltage\": \"12 VDC\", \"Rated Torque\": \"9 Ncm\", \"Rated Speed\": \"250 RPM\", \"Length\": \"51.5 mm\"}'),(42,0,'2024-08-31 20:48:15',NULL,'BLDC_WITH_GEARBOX','ME036RS100-SI0010','Direct Replacement of PMDC motor\nEconomic Solution\nLong Life','{\"Rated Power\": \"6.3 W\", \"Rated Voltage\": \"12 VDC\", \"Rated Torque\": \"25 Ncm\", \"Rated Speed\": \"240 RPM\", \"Length\": \"80.5 mm\"}'),(43,0,'2024-08-31 20:48:15',NULL,'BLDC_WITH_GEARBOX','AGV BLDC Motor','8 poles with 3 phases\nLow Cogging','{\"Rated Power\": \"264 W\", \"Rated Voltage\": \"32 VDC\", \"Rated Torque\": \"1.2 Ncm\", \"Rated Speed\": \"2100 RPM\", \"Length\": \"106.6 mm\"}'),(44,0,'2024-08-31 20:48:15',NULL,'BLDC_DIRECT_DRIVE','MFP180AT100','Direct-drive brushless motor\nHigh Torque, low inertia, fast response,\nlow torque ripple\nIntegrated controller, speed & position-control\nRS232 and CANopen communication, and multi-turns absolute encoder optional\nSteering motor for autopilot agriculture machinery','{\"Rated Power\": \"68 W\", \"Rated Voltage\": \"12 VDC\", \"Rated Torque\": \"6.5 Nm\", \"Rated Speed\": \"100 RPM\"}'),(45,0,'2024-08-31 20:48:15',NULL,'PERMANENT_MAGNET_BRUSH','MB030JS','Ball Bearings\nLow Cogging\nReliable Brush Holder Design','{\"Rated Power\": \"6.1-15.5 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.0113-0.0184 Nm\", \"Rated Speed\": \"5180-8070 RPM\", \"Length\": \"52.6-62.1 mm\"}'),(46,0,'2024-08-31 20:48:15',NULL,'PERMANENT_MAGNET_BRUSH','MB040JS','Long life\nSkew Slot Design for Low cogging\nReliable Brush Holder Design','{\"Rated Power\": \"10.3-41.1 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.017-0.081 Nm\", \"Rated Speed\": \"4400-6000 RPM\", \"Length\": \"48-86 mm\"}'),(47,0,'2024-08-31 20:48:15',NULL,'PERMANENT_MAGNET_BRUSH','MB042DK','DIN Standard Mounting\nHigh Efficiency\nEMI filter available','{\"Rated Power\": \"14.3-28.3 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.04-0.09 Nm\", \"Rated Speed\": \"3000-3400 RPM\", \"Length\": \"75-95 mm\"}'),(48,0,'2024-08-31 20:48:15',NULL,'PERMANENT_MAGNET_BRUSH','MB054TP','Ceramic magnet\nReplacement Carbon Brush\nEconomic Design for Volume Production','{\"Rated Power\": \"23.5-124.4 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"0.07-0.36 Nm\", \"Rated Speed\": \"3200-3300 RPM\", \"Length\": \"75-145 mm\"}'),(49,0,'2024-08-31 20:48:15',NULL,'PERMANENT_MAGNET_BRUSH','MB063KG','Ceramic Magnets\nHigh Effiency\nLong Life Brush','{\"Rated Power\": \"50-100 W\", \"Rated Voltage\": \"12-24 VDC\", \"Rated Torque\": \"0.1-0.3 Nm\", \"Rated Speed\": \"3000-3350 RPM\", \"Length\": \"95-125 mm\"}'),(50,0,'2024-08-31 20:48:15',NULL,'PERMANENT_MAGNET_BRUSH','MB076HG','Stator with permanent rare earth magnet stator whichare glued and protected by a stainless steel sleeve','{\"Rated Power\": \"182-442 W\", \"Rated Voltage\": \"24-90 VDC\", \"Rated Torque\": \"0.75-1.39 Nm\", \"Rated Speed\": \"1200-3200 RPM\", \"Length\": \"114-157 mm\"}'),(51,0,'2024-08-31 20:48:15',NULL,'PERMANENT_MAGNET_BRUSH','MB100FG','IEC34-1 Standard Flange\nHard Ferrite magnet\nNumber of Poles: 4','{\"Rated Power\": \"292-682 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"0.90-2.10 Nm\", \"Rated Speed\": \"3100 RPM\", \"Length\": \"125-178 mm\"}'),(52,0,'2024-08-31 20:48:15',NULL,'BRUSH_WITH_GEARBOX','SG80','Low noise\nDouble output shaft available\nDoor Opener application','{\"Continuous Torque\": \"4 Nm\"}'),(53,0,'2024-08-31 20:48:15',NULL,'INTEGRATED','MDS040','','{\"Rated Power\": \"50-100 W\", \"Rated Voltage\": \"24-36 VDC\", \"Rated Torque\": \"0.16-0.32 Nm\", \"Rated Speed\": \"3000 RPM\", \"Length\": \"69-83 mm\"}'),(54,0,'2024-08-31 20:48:15',NULL,'INTEGRATED','MDS075','','{\"Rated Power\": \"47-188 W\", \"Rated Voltage\": \"36 VDC\", \"Rated Torque\": \"0.1-0.6 Nm\", \"Rated Speed\": \"3000 RPM\", \"Length\": \"88-148 mm\"}'),(55,0,'2024-08-31 20:48:15',NULL,'INTEGRATED','MDS060','','{\"Rated Power\": \"100-400 W\", \"Rated Voltage\": \"36 VDC\", \"Rated Torque\": \"0.32-1.27 Nm\", \"Rated Speed\": \"3000 RPM\", \"Length\": \"96.5-133.5 mm\"}'),(56,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP020NA','NEMA 8\n2 Phase Hybrid stepper motor','{\"Holding Torque\": \"1.8-2 Ncm\", \"Detent Torque\": \"0.2 Ncm\", \"Phase Current\": \"0.6 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"30-60 mm\"}'),(57,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP028NB','NEMA 11\n2 Phase Hybrid stepper motor','{\"Holding Torque\": \"4-9.5 Ncm\", \"Detent Torque\": \"0.8-1.5 Ncm\", \"Phase Current\": \"0.47-1.30 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"31.5-50.5 mm\"}'),(58,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP039NA','NEMA 16\n2 Phase Hybrid stepper motor','{\"Holding Torque\": \"4.6-30 Ncm\", \"Detent Torque\": \"0.8-2.5 Ncm\", \"Phase Current\": \"0.28-0.80 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"20-53 mm\"}'),(59,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP042NB','NEMA 17\n2 Phase Hybrid stepper motor','{\"Holding Torque\": \"12-80 Ncm\", \"Detent Torque\": \"1.42-6 Ncm\", \"Phase Current\": \"0.3-3.5 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"24-60 mm\"}'),(60,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP042SB','NEMA 17\n2 Phase Hybrid stepper motor','{\"Holding Torque\": \"14.5-49 Ncm\", \"Detent Torque\": \"1.5-2.74 Ncm\", \"Phase Current\": \"0.4-1.68 A\", \"Step Angle\": \"0.9 °\", \"Length\": \"22-68 mm\"}'),(61,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP057NB','NEMA 23\n2 Phase Hybrid stepper motor','{\"Holding Torque\": \"41-300 Ncm\", \"Detent Torque\": \"2.6-12 Ncm\", \"Phase Current\": \"1-6 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"41-115 mm\"}'),(62,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP057SB','NEMA 23\n2 Phase Hybrid stepper motor','{\"Holding Torque\": \"19.6-250 Ncm\", \"Detent Torque\": \"3.92-12 Ncm\", \"Phase Current\": \"1.0-3.0 A\", \"Step Angle\": \"0.9 °\", \"Length\": \"41-115 mm\"}'),(63,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP060NB','NEMA 24\n2 Phase Hybrid stepper motor','{\"Holding Torque\": \"110-310 Ncm\", \"Detent Torque\": \"6-17 Ncm\", \"Phase Current\": \"2.8 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"45-86 mm\"}'),(64,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP086NA','NEMA 34, Round housing\n2 Phases Hybrid Stepper Motor','{\"Holding Torque\": \"137.2-392 Ncm\", \"Detent Torque\": \"7.84-24.5 Ncm\", \"Phase Current\": \"1.7-7 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"62-127 mm\"}'),(65,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP086YG','NEMA 34\n2 Phases Hybrid Stepper Motor','{\"Holding Torque\": \"2.8-12.1 Nm\", \"Detent Torque\": \"0.2-0.38 Nm\", \"Phase Current\": \"6.1-10.0 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"65-156.5 mm\"}'),(66,0,'2024-08-31 20:48:15',NULL,'STEPPER_STANDARD_TORQUE','MP110YG','NEMA 43\n2 Phases Hybrid Stepper Motor','{\"Holding Torque\": \"11.68-30.81 Nm\", \"Detent Torque\": \"0.3-0.75 Nm\", \"Phase Current\": \"10.7-15.8 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"99-201 mm\"}'),(67,0,'2024-08-31 20:48:15',NULL,'STEPPER_FLAT','MPF028NB','','{\"Holding Torque\": \"0.98 Ncm\", \"Phase Current\": \"0.5 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"9.4 mm\"}'),(68,0,'2024-08-31 20:48:15',NULL,'STEPPER_FLAT','MPF068NB','','{\"Holding Torque\": \"6.4 Ncm\", \"Phase Current\": \"1 A\", \"Step Angle\": \"1.8 °\", \"Length\": \"9.6 mm\"}'),(69,0,'2024-08-31 20:48:15',NULL,'STEPPER_WITH_CONTROL','EMP42','','{\"Holding Torque\": \"33.3-80 Ncm\", \"Phase Current\": \"1-2.5 A\", \"Length\": \"40-60 mm\"}'),(70,0,'2024-08-31 20:48:15',NULL,'STEPPER_WITH_CONTROL','EMP57','','{\"Holding Torque\": \"55-300 Ncm\", \"Phase Current\": \"2-2.5 A\", \"Length\": \"41-115 mm\"}'),(71,0,'2024-08-31 20:48:15',NULL,'STEPPER_LINEAR','LS020NB','','{\"Phase Current\": \"0.5 A\", \"Length\": \"27.2-38.1 mm\"}'),(72,0,'2024-08-31 20:48:15',NULL,'STEPPER_LINEAR','LS028NB','','{\"Phase Current\": \"0.5-1.6 A\", \"Length\": \"32.6-45 mm\"}'),(73,0,'2024-08-31 20:48:15',NULL,'STEPPER_LINEAR','LS035NB','','{\"Phase Current\": \"0.5-1.5 A\", \"Length\": \"33.6-45.6 mm\"}'),(74,0,'2024-08-31 20:48:15',NULL,'STEPPER_LINEAR','LS042NB','','{\"Phase Current\": \"0.5-2.5 A\", \"Length\": \"34.1-48.1 mm\"}'),(75,0,'2024-08-31 20:48:15',NULL,'STEPPER_LINEAR','LS057NB','','{\"Phase Current\": \"1.0-4.0 A\", \"Length\": \"45-65 mm\"}'),(76,0,'2024-08-31 20:48:15',NULL,'STEPPER_LINEAR','LS086NB','','{\"Phase Current\": \"1.3-6.0 A\", \"Length\": \"78-100 mm\"}'),(77,0,'2024-09-17 18:10:19',NULL,'SOLAR_TRACKING_APPLICATION','66 FRAME PMDC/BLDC GEAR MOTOR','2 PPR or 4 PPR encoder available\nProtection class IP 65\nWith protective vent valve to reduce condensation','{\"Rated Power\": \"41-51 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"2.9-108.8 Nm\", \"Rated Speed\": \"4-167 rpm\", \"Length\": \"185-198 mm\", \"Ratio\": \"18-837\"}'),(78,0,'2024-09-17 18:10:19',NULL,'SOLAR_TRACKING_APPLICATION','82 FRAME PMDC/BLDC GEAR MOTOR','2 PPR or 4 PPR encoder available\nProtection class IP 65\nWith protective vent valve to reduce condensation','{\"Rated Power\": \"82-102 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"5.8-217.4 Nm\", \"Rated Speed\": \"4-167 rpm\", \"Length\": \"215-260 mm\", \"Ratio\": \"18-836\"}'),(79,0,'2024-09-17 18:10:19',NULL,'SOLAR_TRACKING_APPLICATION','94 FRAME PMDC/BLDC GEAR MOTOR','2 PPR or 4 PPR encoder available\nProtection class IP 65\nWith protective vent valve to reduce condensation\nUL Certified','{\"Rated Power\": \"35-115 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"320-550 Nm\", \"Rated Speed\": \"0.6-2.2 rpm\", \"Length\": \"308-330 mm\", \"Ratio\": \"745-2023.5\"}'),(80,0,'2024-09-17 18:10:19',NULL,'SOLAR_TRACKING_APPLICATION','128 FRAME PMDC/BLDC GEAR MOTOR','2 PPR or 4 PPR encoder available\nEpoxy coating on the surface \nWith protective vent valve to reduce condensation','{\"Rated Power\": \"272-339 W\", \"Rated Voltage\": \"24 VDC\", \"Rated Torque\": \"37.3-2024.1 Nm\", \"Rated Speed\": \"1-87 rpm\", \"Length\": \"310-355 mm\", \"Ratio\": \"23-1557\"}'),(81,0,'2024-09-17 18:10:19',NULL,'MATERIAL_HANDLING_SOLUTION','Sorter BLDC Roller','Direct drive\nWide speed range\nFree of maintenance\nSurface knurling available','{\"Rated Power\": \"150-400 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"4.5-15 Nm\", \"Rated Speed\": \"300-800 rpm\"}'),(82,0,'2024-09-17 18:10:19',NULL,'MATERIAL_HANDLING_SOLUTION','Sorter Servo Roller','Direct drive\nServo control\nInductive encoder','{\"Rated Power\": \"150-400 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"4.5-15 Nm\", \"Rated Speed\": \"300-800 rpm\"}'),(83,0,'2024-09-17 18:10:19',NULL,'MATERIAL_HANDLING_SOLUTION','Smart Roller','Multiple color rubber available\nCoating Thickness 2mm or customized','{\"Rated Power\": \"50-100 W\", \"Rated Voltage\": \"48 VDC\", \"Rated Torque\": \"1.5-2 Nm\", \"Rated Speed\": \"3500-1000 rpm\"}');
/*!40000 ALTER TABLE `MOTOR_SERIES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NEWS`
--

DROP TABLE IF EXISTS `NEWS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `NEWS` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `TITLE` varchar(200) NOT NULL,
  `CONTENT` varchar(2048) DEFAULT NULL,
  `NEWS_DATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_NEWS_TITLE` (`TITLE`),
  KEY `IX_NEWS_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_NEWS_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`),
  KEY `IX_NEWS_DATETIME` (`NEWS_DATETIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NEWS`
--

LOCK TABLES `NEWS` WRITE;
/*!40000 ALTER TABLE `NEWS` DISABLE KEYS */;
/*!40000 ALTER TABLE `NEWS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PLANETARY_GEARBOX`
--

DROP TABLE IF EXISTS `PLANETARY_GEARBOX`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PLANETARY_GEARBOX` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `MODEL` varchar(64) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `FRAME_SIZE` decimal(10,2) DEFAULT NULL,
  `FRAME_SIZE_UNIT` varchar(16) DEFAULT NULL,
  `FRAME_SIZE_TYPE` varchar(16) DEFAULT NULL,
  `LENGTH` decimal(10,2) DEFAULT NULL,
  `LENGTH_UNIT` varchar(16) DEFAULT NULL,
  `WEIGHT` decimal(10,2) DEFAULT NULL,
  `WEIGHT_UNIT` varchar(16) DEFAULT NULL,
  `NUM_OF_STAGES` int NOT NULL,
  `REDUCTION_RATIOS` varchar(64) DEFAULT NULL,
  `EFFICIENCY` decimal(10,2) DEFAULT NULL,
  `EFFICIENCY_UNIT` varchar(16) DEFAULT NULL,
  `RATED_CONTINUOUS_TORQUE` decimal(10,4) DEFAULT NULL,
  `RATED_CONTINUOUS_TORQUE_UNIT` varchar(16) DEFAULT NULL,
  `MAX_MOMENTARY_TORQUE` decimal(10,4) DEFAULT NULL,
  `MAX_MOMENTARY_TORQUE_UNIT` varchar(16) DEFAULT NULL,
  `MAX_RADIAL_LOAD` decimal(10,2) DEFAULT NULL,
  `MAX_RADIAL_LOAD_UNIT` varchar(16) DEFAULT NULL,
  `MAX_AXIAL_LOAD` decimal(10,2) DEFAULT NULL,
  `MAX_AXIAL_LOAD_UNIT` varchar(16) DEFAULT NULL,
  `MAX_SHAFT_PRESS` decimal(10,2) DEFAULT NULL,
  `MAX_SHAFT_PRESS_UNIT` varchar(16) DEFAULT NULL,
  `MAX_BACKLASH_NOLOAD` decimal(10,2) DEFAULT NULL,
  `MAX_BACKLASH_NOLOAD_UNIT` varchar(16) DEFAULT NULL,
  `OPERATING_TEMPERATURE` varchar(32) DEFAULT NULL,
  `RECOMMEND_INPUT_SPEED` varchar(32) DEFAULT NULL,
  `NEMA_SIZE` decimal(8,0) DEFAULT NULL,
  `SERIES` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_PLANETARY_GEARBOX_MODEL` (`MODEL`),
  KEY `IX_PLGB_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_PLGB_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`),
  KEY `IX_PLGB_LENGTH` (`LENGTH`),
  KEY `IX_PLGB_WEIGHT` (`WEIGHT`),
  KEY `IX_PLGB_NUM_OF_STAGES` (`NUM_OF_STAGES`),
  KEY `IX_PLGB_EFFICIENCY` (`EFFICIENCY`),
  KEY `IX_PLGB_RATED_CONTINUOUS_TORQUE` (`RATED_CONTINUOUS_TORQUE`),
  KEY `IX_PLGB_MAX_MOMENTARY_TORQUE` (`MAX_MOMENTARY_TORQUE`),
  KEY `FK_PLANETARY_GEARBOX_SERIES` (`SERIES`),
  CONSTRAINT `FK_PLANETARY_GEARBOX_SERIES` FOREIGN KEY (`SERIES`) REFERENCES `GEARBOX_SERIES` (`SERIES`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PLANETARY_GEARBOX`
--

LOCK TABLES `PLANETARY_GEARBOX` WRITE;
/*!40000 ALTER TABLE `PLANETARY_GEARBOX` DISABLE KEYS */;
INSERT INTO `PLANETARY_GEARBOX` VALUES (1,0,'2024-08-14 19:30:05','2024-09-02 16:55:57','PL030GE30K-1S','Planetary Gearbox',NULL,30.00,'mm','φ',29.00,'mm',72.00,'g',1,'1/3.7, 1/5.2',90.00,'%',0.3000,'Nm',1.0000,'Nm',100.00,'N',50.00,'N',100.00,'N',2.50,'°','-15°C ～ +80°C','＜ 10000rpm',NULL,'PL030GE'),(2,0,'2024-08-14 19:39:34','2024-09-02 16:55:57','PL030GE30K-2S','Planetary Gearbox',NULL,30.00,'mm','φ',36.10,'mm',80.00,'g',2,'1/14, 1/19,1/27, 1/35',81.00,'%',1.2000,'Nm',3.5000,'Nm',100.00,'N',50.00,'N',100.00,'N',2.50,'°','-15°C ～ +80°C','＜ 10000rpm',NULL,'PL030GE'),(3,0,'2024-08-14 19:42:21','2024-09-02 16:55:57','PL030GE30K-3S','Planetary Gearbox',NULL,30.00,'mm','φ',43.00,'mm',108.00,'g',3,'1/51, 1/71, 1/100, 1/139',73.00,'%',2.5000,'Nm',7.5000,'Nm',100.00,'N',50.00,'N',100.00,'N',2.50,'°','-15°C ～ +80°C','＜ 10000rpm',NULL,'PL030GE'),(4,0,'2024-08-14 19:45:13','2024-09-02 16:55:57','PL030GE30K-4S','Planetary Gearbox',NULL,30.00,'mm','φ',49.90,'mm',126.00,'g',4,'1/189, 1/264, 1/369, 1/516, 1/721, 1/939',66.00,'%',3.0000,'Nm',9.0000,'Nm',100.00,'N',50.00,'N',100.00,'N',2.50,'°','-15°C ～ +80°C','＜ 10000rpm',NULL,'PL030GE');
/*!40000 ALTER TABLE `PLANETARY_GEARBOX` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `STEPPER_MOTOR`
--

DROP TABLE IF EXISTS `STEPPER_MOTOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `STEPPER_MOTOR` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `CATEGORY` varchar(32) NOT NULL,
  `MODEL` varchar(64) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `FRAME_SIZE` decimal(10,2) DEFAULT NULL,
  `FRAME_SIZE_UNIT` varchar(16) DEFAULT NULL,
  `FRAME_SIZE_TYPE` varchar(16) DEFAULT NULL,
  `NEMA_SIZE` decimal(8,0) DEFAULT NULL,
  `LENGTH` decimal(10,2) DEFAULT NULL,
  `LENGTH_UNIT` varchar(16) DEFAULT NULL,
  `WEIGHT` decimal(10,2) DEFAULT NULL,
  `WEIGHT_UNIT` varchar(16) DEFAULT NULL,
  `RATED_VOLTAGE` decimal(10,2) DEFAULT NULL,
  `RATED_VOLTAGE_UNIT` varchar(16) DEFAULT NULL,
  `PHASE_CURRENT` decimal(10,4) DEFAULT NULL,
  `PHASE_CURRENT_UNIT` varchar(16) DEFAULT NULL,
  `PHASE_RESISTANCE` decimal(10,2) DEFAULT NULL,
  `PHASE_RESISTANCE_UNIT` varchar(16) DEFAULT NULL,
  `PHASE_INDUCTANCE` decimal(10,2) DEFAULT NULL,
  `PHASE_INDUCTANCE_UNIT` varchar(16) DEFAULT NULL,
  `HOLDING_TORQUE` decimal(10,4) DEFAULT NULL,
  `HOLDING_TORQUE_UNIT` varchar(16) DEFAULT NULL,
  `DETENT_TORQUE` decimal(10,4) DEFAULT NULL,
  `DETENT_TORQUE_UNIT` varchar(16) DEFAULT NULL,
  `STEP_ANGLE` decimal(10,2) DEFAULT NULL,
  `STEP_ANGLE_UNIT` varchar(16) DEFAULT NULL,
  `MAX_THRUST` decimal(10,2) DEFAULT NULL,
  `MAX_THRUST_UNIT` varchar(16) DEFAULT NULL,
  `SERIES` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_STEPPER_MOTOR_MODEL` (`MODEL`),
  KEY `IX_STEPPER_MOTOR_CREATED_TIMESTAMP` (`CREATED_TIMESTAMP`),
  KEY `IX_STEPPER_MOTOR_UPDATED_TIMESTAMP` (`UPDATED_TIMESTAMP`),
  KEY `IX_STEPPER_MOTOR_NAME` (`NAME`),
  KEY `IX_STEPPER_MOTOR_TYPE` (`CATEGORY`),
  KEY `IX_STEPPER_MOTOR_FRAME_SIZE` (`FRAME_SIZE`),
  KEY `IX_STEPPER_MOTOR_NEMA_SIZE` (`NEMA_SIZE`),
  KEY `IX_STEPPER_MOTOR_LENGTH` (`LENGTH`),
  KEY `IX_STEPPER_MOTOR_WEIGHT` (`WEIGHT`),
  KEY `IX_STEPPER_MOTOR_RATED_VOLTAGE` (`RATED_VOLTAGE`),
  KEY `IX_STEPPER_MOTOR_PHASE_CURRENT` (`PHASE_CURRENT`),
  KEY `IX_STEPPER_MOTOR_HOLDING_TORQUE` (`HOLDING_TORQUE`),
  KEY `IX_STEPPER_MOTOR_DETENT_TORQUE` (`DETENT_TORQUE`),
  KEY `IX_STEPPER_MOTOR_STEP_ANGLE` (`STEP_ANGLE`),
  KEY `IX_STEPPER_MOTOR_MAX_THRUST` (`MAX_THRUST`),
  KEY `FK_STEPPER_MOTOR_SERIES` (`SERIES`),
  CONSTRAINT `FK_STEPPER_MOTOR_CATEGORY` FOREIGN KEY (`CATEGORY`) REFERENCES `MOTOR_CATEGORY` (`CATEGORY`),
  CONSTRAINT `FK_STEPPER_MOTOR_SERIES` FOREIGN KEY (`SERIES`) REFERENCES `MOTOR_SERIES` (`SERIES`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `STEPPER_MOTOR`
--

LOCK TABLES `STEPPER_MOTOR` WRITE;
/*!40000 ALTER TABLE `STEPPER_MOTOR` DISABLE KEYS */;
INSERT INTO `STEPPER_MOTOR` VALUES (1,0,'2024-08-11 01:24:50','2024-08-31 20:57:38','STEPPER_FLAT','MPF028NB001','Flat Stepper Motor',NULL,28.00,'mm','□',NULL,NULL,NULL,28.00,'g',NULL,NULL,0.5000,'A',3.70,'Ω',0.88,'mH',0.9800,'Ncm',NULL,NULL,NULL,NULL,NULL,NULL,'MPF028NB'),(2,0,'2024-08-11 12:37:42','2024-08-31 20:57:50','STEPPER_FLAT','MPF068NB001','Flat Stepper Motor',NULL,68.00,'mm','φ',NULL,NULL,NULL,95.00,'g',NULL,NULL,1.0000,'A',3.80,'Ω',2.00,'mH',6.4000,'Ncm',0.5000,'Ncm',NULL,NULL,NULL,NULL,'MPF068NB'),(3,0,'2024-08-11 16:37:18','2024-08-28 19:26:38','STEPPER_LINEAR','LS020NB101','Linear Stepper Motor',NULL,20.00,'mm','□',8,27.20,'mm',NULL,NULL,2.50,'V',0.5000,'A',5.10,'Ω',1.50,'mH',NULL,NULL,NULL,NULL,NULL,NULL,65.00,'N','LS020NB'),(4,0,'2024-08-11 16:51:06','2024-08-28 19:26:38','STEPPER_LINEAR','LS020NB201','Linear Stepper Motor',NULL,20.00,'mm','□',8,38.10,'mm',NULL,NULL,4.40,'V',0.5000,'A',8.80,'Ω',2.70,'mH',NULL,NULL,NULL,NULL,NULL,NULL,65.00,'N','LS020NB'),(5,0,'2024-08-11 19:13:39','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP020NA101','Standard Stepper Motor',NULL,20.00,'mm','□',8,30.00,'mm',60.00,'g',NULL,NULL,0.6000,'A',6.50,'Ω',1.70,'mH',1.8000,'Ncm',0.2000,'Ncm',1.80,'°',NULL,NULL,'MP020NA'),(6,0,'2024-08-11 19:16:28','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP020NA102','Standard Stepper Motor',NULL,20.00,'mm','□',8,30.00,'mm',60.00,'g',NULL,NULL,0.6000,'A',6.50,'Ω',1.70,'mH',1.8000,'Ncm',0.2000,'Ncm',1.80,'°',NULL,NULL,'MP020NA'),(7,0,'2024-08-11 19:18:49','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP020NA300','Standard Stepper Motor',NULL,20.00,'mm','□',8,33.00,'mm',60.00,'g',NULL,NULL,0.6000,'A',6.50,'Ω',0.80,'mH',2.0000,'Ncm',0.2000,'Ncm',1.80,'°',NULL,NULL,'MP020NA'),(8,0,'2024-08-12 19:08:50','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP028NB101','Standard Stepper Motor',NULL,28.00,'mm','□',11,31.50,'mm',100.00,'g',NULL,NULL,0.6700,'A',5.60,'Ω',4.20,'mH',6.0000,'Ncm',0.8000,'Ncm',1.80,'°',NULL,NULL,'MP028NB'),(9,0,'2024-08-13 01:08:04','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP028NB102','Standard Stepper Motor',NULL,28.00,'mm','□',11,31.50,'mm',100.00,'g',NULL,NULL,0.4700,'A',2.80,'Ω',1.00,'mH',4.0000,'Ncm',0.8000,'Ncm',1.80,'°',NULL,NULL,'MP028NB'),(10,0,'2024-08-13 16:53:32','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP028NB103','Standard Stepper Motor',NULL,28.00,'mm','□',11,31.50,'mm',100.00,'g',NULL,NULL,1.3000,'A',1.40,'Ω',1.10,'mH',6.5000,'Ncm',0.8000,'Ncm',1.80,'°',NULL,NULL,'MP028NB'),(11,0,'2024-08-13 16:55:58','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP028NB201','Standard Stepper Motor',NULL,28.00,'mm','□',11,44.50,'mm',180.00,'g',NULL,NULL,0.6700,'A',6.80,'Ω',4.90,'mH',9.5000,'Ncm',1.2000,'Ncm',1.80,'°',NULL,NULL,'MP028NB'),(12,0,'2024-08-13 17:02:35','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP028NB202','Standard Stepper Motor',NULL,28.00,'mm','□',11,44.50,'mm',180.00,'g',NULL,NULL,1.0000,'A',3.40,'Ω',1.20,'mH',7.5000,'Ncm',1.2000,'Ncm',1.80,'°',NULL,NULL,'MP028NB'),(13,0,'2024-08-13 17:06:01','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP028NB301','Standard Stepper Motor',NULL,28.00,'mm','□',11,50.50,'mm',210.00,'g',NULL,NULL,0.6700,'A',9.20,'Ω',5.70,'mH',12.0000,'Ncm',1.5000,'Ncm',1.80,'°',NULL,NULL,'MP028NB'),(14,0,'2024-08-13 17:13:21','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP028NB302','Standard Stepper Motor',NULL,28.00,'mm','□',11,50.50,'mm',210.00,'g',NULL,NULL,0.9600,'A',4.60,'Ω',1.80,'mH',9.0000,'Ncm',1.5000,'Ncm',1.80,'°',NULL,NULL,'MP028NB'),(15,0,'2024-08-13 17:15:03','2024-08-31 18:04:22','STEPPER_STANDARD_TORQUE','MP028NB303','Standard Stepper Motor',NULL,28.00,'mm','□',11,50.50,'mm',210.00,'g',NULL,NULL,1.0000,'A',4.30,'Ω',2.00,'mH',9.5000,'Ncm',1.5000,'Ncm',1.80,'°',NULL,NULL,'MP028NB');
/*!40000 ALTER TABLE `STEPPER_MOTOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `STEPPER_MOTOR_PERF_MEASUREMENT`
--

DROP TABLE IF EXISTS `STEPPER_MOTOR_PERF_MEASUREMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `STEPPER_MOTOR_PERF_MEASUREMENT` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `MOTOR_ID` int unsigned NOT NULL,
  `TITLE` varchar(64) DEFAULT NULL,
  `VARIABLES` varchar(256) DEFAULT NULL,
  `VALUES` varchar(2048) DEFAULT NULL,
  `CONDITIONS` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_STEPPER_MOTOR_PERF_STAT_MOTOR_ID` (`MOTOR_ID`),
  CONSTRAINT `FK_STEPPER_MOTOR_PERF_STAT_MOTOR_ID` FOREIGN KEY (`MOTOR_ID`) REFERENCES `STEPPER_MOTOR` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `STEPPER_MOTOR_PERF_MEASUREMENT`
--

LOCK TABLES `STEPPER_MOTOR_PERF_MEASUREMENT` WRITE;
/*!40000 ALTER TABLE `STEPPER_MOTOR_PERF_MEASUREMENT` DISABLE KEYS */;
INSERT INTO `STEPPER_MOTOR_PERF_MEASUREMENT` VALUES (1,9,'MP028NB102 Torque-Frequency Curve','Frequency(Hz), Torque(Ncm)','50,5.9\n100,5.9\n200,6.4\n300,6.6\n400,6.9\n500,7.1\n600,7.2\n700,7.2\n800,7.2\n900,7\n1000,6.8\n1100,6.7\n1200,6.6\n1300,6.4\n1500,6\n1700,6\n','18.9V 0.67A'),(2,11,'MP028NB201 Torque-Frequency Curve','Frequency(Hz), Torque(Ncm)','50,7.87\n100,8.2\n200,7.85\n300,7.88\n400,8.1\n500,8.5\n600,8.7\n700,8.7\n800,8.7\n900,8.7\n1000,8.6\n1100,8.5\n1200,8.4\n1300,8.3\n1400,8.2\n1500,8.1\n1600,7.9\n1700,7.6\n1800,7.3\n1900,6.9\n2000,6.6\n2100,6.3\n2200,5.9\n2300,5.3\n2400,3\n2500,2.5\n2600,2\n','24V 0.67A'),(3,13,'MP028NB301 Torque-Frequency Curve','Frequency(Hz), Torque(Ncm)','50,7.7\n100,7.8\n200,8.3\n300,8.6\n400,9\n500,9.3\n600,9.5\n700,9.5\n800,9.5\n900,9.5\n1000,9.5\n1100,9.5\n1200,9.5\n1300,9.4\n1400,9.3\n1500,9.1\n1600,8.9\n1700,8.7\n1800,8.6\n1900,8.4\n2000,8.1\n2100,7.5\n2200,7\n2300,6.5\n2400,5.8\n2500,4\n','24V 0.67A'),(4,3,'LS020NB101 Speed-Thrust Curves','pps, r/min, LinearSpeed[0.3048:AF, 0.6096:AA, 1.2192:B, 2:G, 4:M, 8:T](mm/s), Thrust[0.3048:AF, 0.6096:AA, 1.2192:B, 2:G, 4:M, 8:T](kg)','200,60,0.3,0.6,1.2,2.0,4.0,8.0,3.2,2.7,2.2,2.1,1.1,0.5 \n600,180,0.9,1.8,3.7,6.0,12.0,24.0,3.1,2.5,2.1,2.0,0.9,0.4 \n1000,300,1.5,3.0,6.1,10.0,20.0,40.0,2.8,2.4,2.0,1.9,0.9,0.4 \n1500,450,2.3,4.6,9.1,15.0,30.0,60.0,2.7,2.4,1.9,1.8,0.6,0.3 \n2000,600,3.0,6.1,12.2,20.0,40.0,80.0,2.6,2.2,1.8,1.7,0.6,0.2 \n2500,750,3.8,7.6,15.2,25.0,50.0,100.0,2.5,1.9,1.7,1.5,0.5,0.2  \n',''),(6,4,'LS020NB201 Speed-Thrust Curves','pps, r/min, LinearSpeed[0.3048:AF, 0.6096:AA, 1.2192:B, 2:G, 4:M, 8:T](mm/s), Thrust[0.3048:AF, 0.6096:AA, 1.2192:B, 2:G, 4:M, 8:T](kg)','200,60,0.3,0.6096,1.2192,2,4,8,7.0,6.8,4.6,3.5,3.1,2.0 \n600,180,0.9,1.8288,3.6576,6,12,24,6.9,6.7,4.5,3.4,3.0,1.8 \n1000,300,1.5,3.048,6.096,10,20,40,6.8,6.5,4.4,3.3,2.9,1.5 \n1500,450,2.25,4.572,9.144,15,30,60,6.6,6.4,4.3,3.1,2.8,1.5 \n2000,600,3,6.096,12.192,20,40,80,6.5,6.2,4.1,3.1,2.8,1.3 \n2500,750,3.75,7.62,15.24,25,50,100,6.4,5.7,4.0,2.9,2.4,1.2   \n','');
/*!40000 ALTER TABLE `STEPPER_MOTOR_PERF_MEASUREMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `STEPPER_MOTOR_SPEC`
--

DROP TABLE IF EXISTS `STEPPER_MOTOR_SPEC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `STEPPER_MOTOR_SPEC` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `MOTOR_ID` int unsigned NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `UNIT` varchar(16) DEFAULT NULL,
  `VALUE` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_STEPPER_MOTOR_SPEC_MOTOR_ID` (`MOTOR_ID`),
  KEY `IX_STEPPER_MOTOR_SPEC_NAME` (`NAME`),
  CONSTRAINT `FK_STEPPER_MOTOR_SPEC_MOTOR_ID` FOREIGN KEY (`MOTOR_ID`) REFERENCES `STEPPER_MOTOR` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `STEPPER_MOTOR_SPEC`
--

LOCK TABLES `STEPPER_MOTOR_SPEC` WRITE;
/*!40000 ALTER TABLE `STEPPER_MOTOR_SPEC` DISABLE KEYS */;
INSERT INTO `STEPPER_MOTOR_SPEC` VALUES (1,1,'No. of Wires',NULL,'4'),(2,1,'Rotor Inertia','g.cm²','1.7'),(3,2,'Rotor Inertia','g.cm²','0.16'),(4,5,'No. of Wires',NULL,'4'),(5,5,'Rotor Inertia','g.cm²','2'),(6,6,'No. of Wires',NULL,'4'),(7,6,'Rotor Inertia','g.cm²','2'),(8,7,'No. of Wires',NULL,'6'),(9,7,'Rotor Inertia','g.cm²','40'),(10,8,'No. of Wires',NULL,'4'),(11,8,'Rotor Inertia','g.cm²','9.5'),(12,9,'No. of Wires',NULL,'6'),(13,9,'Rotor Inertia','g.cm²','9.5'),(14,10,'No. of Wires',NULL,'4'),(15,10,'Rotor Inertia','g.cm²','9.5'),(16,11,'No. of Wires',NULL,'4'),(17,11,'Rotor Inertia','g.cm²','12'),(18,12,'No. of Wires',NULL,'6'),(19,12,'Rotor Inertia','g.cm²','12'),(20,13,'No. of Wires',NULL,'4'),(21,13,'Rotor Inertia','g.cm²','18'),(22,14,'No. of Wires',NULL,'6'),(23,14,'Rotor Inertia','g.cm²','18'),(24,15,'No. of Wires',NULL,'6'),(25,15,'Rotor Inertia','g.cm²','18');
/*!40000 ALTER TABLE `STEPPER_MOTOR_SPEC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `VERSION` int unsigned DEFAULT '0',
  `CREATED_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIMESTAMP` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `USERNAME` varchar(64) NOT NULL,
  `EMAIL` varchar(128) DEFAULT NULL,
  `PASSWORD` varchar(16) DEFAULT NULL,
  `ROLE` varchar(16) DEFAULT NULL,
  `FIRSTNAME` varchar(64) DEFAULT NULL,
  `LASTNAME` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_USERNAME` (`USERNAME`),
  UNIQUE KEY `UK_EMAIL` (`EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (3,0,'2024-12-06 14:06:09',NULL,'admin',NULL,'adminp','ADMIN',NULL,NULL),(4,0,'2024-12-06 14:06:09',NULL,'exmekweb',NULL,'exmekweb@2411','USER',NULL,NULL);
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'exmek'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-10  0:08:32
