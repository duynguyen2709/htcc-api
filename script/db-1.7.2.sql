CREATE DATABASE  IF NOT EXISTS `HTCC_Log` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `HTCC_Log`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: 167.179.80.90    Database: HTCC_Log
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ApiLog202004`
--

DROP TABLE IF EXISTS `ApiLog202004`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202004` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `serviceId` int NOT NULL DEFAULT '0',
  `requestURL` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `method` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `path` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `params` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `body` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `returnCode` int NOT NULL DEFAULT '1',
  `requestTime` bigint NOT NULL DEFAULT '0',
  `responseTime` bigint NOT NULL DEFAULT '0',
  `userIP` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB AUTO_INCREMENT=15502 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202004`
--

LOCK TABLES `ApiLog202004` WRITE;
/*!40000 ALTER TABLE `ApiLog202004` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ApiLog202005`
--

DROP TABLE IF EXISTS `ApiLog202005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202005` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `serviceId` int NOT NULL DEFAULT '0',
  `requestURL` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `method` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `path` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `params` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `body` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `returnCode` int NOT NULL DEFAULT '1',
  `requestTime` bigint NOT NULL DEFAULT '0',
  `responseTime` bigint NOT NULL DEFAULT '0',
  `userIP` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202005`
--

LOCK TABLES `ApiLog202005` WRITE;
/*!40000 ALTER TABLE `ApiLog202005` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202005` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckInLog202004`
--

DROP TABLE IF EXISTS `CheckInLog202004`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202004` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `subType` int NOT NULL DEFAULT '1',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `officeId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `serverTime` bigint NOT NULL DEFAULT '0',
  `validTime` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '00:00',
  `isOnTime` tinyint(1) NOT NULL DEFAULT '1',
  `validLatitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `validLongitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `latitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `longitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `image` varchar(512) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202004`
--

LOCK TABLES `CheckInLog202004` WRITE;
/*!40000 ALTER TABLE `CheckInLog202004` DISABLE KEYS */;
INSERT INTO `CheckInLog202004` VALUES (7,20200428,'4ffbfaa',1,'HCMUS','NVC','duytv',1588061152511,1588073536694,'08:30',0,10.762462,106.682755,10.762462,106.682755,100000,0,'127.0.0.1','','2020-04-28 11:32:18'),(9,20200429,'be49d90',1,'HCMUS','NVC','duytv',1588125246302,1588125247391,'08:30',0,10.762462,106.682755,10.839958,106.659401,100000,1,'192.168.1.249','','2020-04-29 01:54:09'),(15,20200429,'9e480bd',1,'HCMUS','NVC','autth',1588137648573,1588137666222,'08:30',0,10.762462,106.682755,10.762462,106.682755,100000,0,'127.0.0.1','','2020-04-29 05:21:06');
/*!40000 ALTER TABLE `CheckInLog202004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckInLog202005`
--

DROP TABLE IF EXISTS `CheckInLog202005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202005` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `subType` int NOT NULL DEFAULT '1',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `officeId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `serverTime` bigint NOT NULL DEFAULT '0',
  `validTime` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '00:00',
  `isOnTime` tinyint(1) NOT NULL DEFAULT '1',
  `validLatitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `validLongitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `latitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `longitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `image` varchar(512) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202005`
--

LOCK TABLES `CheckInLog202005` WRITE;
/*!40000 ALTER TABLE `CheckInLog202005` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckInLog202005` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckOutLog202004`
--

DROP TABLE IF EXISTS `CheckOutLog202004`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202004` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `subType` int NOT NULL DEFAULT '1',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `officeId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `serverTime` bigint NOT NULL DEFAULT '0',
  `validTime` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '00:00',
  `isOnTime` tinyint(1) NOT NULL DEFAULT '1',
  `validLatitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `validLongitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `latitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `longitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `image` varchar(512) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202004`
--

LOCK TABLES `CheckOutLog202004` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202004` DISABLE KEYS */;
INSERT INTO `CheckOutLog202004` VALUES (4,20200428,'4da4c73',1,'HCMUS','NVC','duytv',1588071152511,1588073592308,'17:30',1,10.762462,106.682755,10.762462,106.682755,100000,0,'127.0.0.1','','2020-04-28 11:33:13'),(5,20200429,'8b8cff9',1,'HCMUS','NVC','duytv',1588125331021,1588125332893,'17:30',0,10.762462,106.682755,10.839978,106.659309,100000,1,'192.168.1.249','','2020-04-29 01:55:33'),(10,20200429,'4b5c091',1,'HCMUS','NVC','autth',1588137708352,1588137710851,'17:30',0,10.762462,106.682755,10.757957,106.745277,100000,1,'172.25.35.242','','2020-04-29 05:21:51');
/*!40000 ALTER TABLE `CheckOutLog202004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckOutLog202005`
--

DROP TABLE IF EXISTS `CheckOutLog202005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202005` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `subType` int NOT NULL DEFAULT '1',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `officeId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `serverTime` bigint NOT NULL DEFAULT '0',
  `validTime` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '00:00',
  `isOnTime` tinyint(1) NOT NULL DEFAULT '1',
  `validLatitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `validLongitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `latitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `longitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `image` varchar(512) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202005`
--

LOCK TABLES `CheckOutLog202005` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202005` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckOutLog202005` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ComplaintLog202003`
--

DROP TABLE IF EXISTS `ComplaintLog202003`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ComplaintLog202003` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `complaintId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `receiverType` int NOT NULL DEFAULT '2',
  `isAnonymous` int NOT NULL DEFAULT '0',
  `category` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `content` varchar(8192) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `images` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `response` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '[]',
  `status` int NOT NULL DEFAULT '2',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `complaintId_UNIQUE` (`complaintId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`),
  KEY `receiverType_INDEX` (`receiverType`),
  KEY `complaintId_INDEX` (`complaintId`),
  KEY `status_INDEX` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ComplaintLog202003`
--

LOCK TABLES `ComplaintLog202003` WRITE;
/*!40000 ALTER TABLE `ComplaintLog202003` DISABLE KEYS */;
INSERT INTO `ComplaintLog202003` VALUES (15,20200326,'3e58054','#VNG-3e58054','VNG','duytv',1585210791055,1,0,'Transport','[\"test\"]','[]','[\"123\"]',1,'2020-04-11 05:16:32'),(16,20200326,'96bbe36','#VNG-96bbe36','VNG','duytv',1585212038686,2,0,'Transport','[\"test\"]','[]','[\"Từ chối xử lý khiếu nại\"]',0,'2020-04-11 05:16:32'),(17,20200326,'16b72ca','#VNG-16b72ca','VNG','duytv',1585212561772,1,0,'Transport','[\"test\"]','[\"https://drive.google.com/uc?export=view&id=1VhLEl-BKU2TAmMgKGqB0J8EL-38MfFjh\"]','[\"123\"]',1,'2020-04-11 05:16:32'),(18,20200326,'09bda25','#VNG-09bda25','VNG','duytv',1585213693416,2,0,'Transport','[\"abc\"]','[\"https://drive.google.com/uc?export=view&id=14HjTZzBIwuQbjsnSrFewakPpOI5tQ2yA\",\"https://drive.google.com/uc?export=view&id=1U_dMZWCYJ6dVtXVYT4LTqyUZAnwz6glZ\"]','[\"Đã xử lý khiếu nại\"]',1,'2020-04-11 05:16:32'),(19,20200326,'e982b20','#VNG-e982b20','VNG','duytv',1585213742776,1,0,'Transport','[\"abc\"]','[\"https://drive.google.com/uc?export=view&id=1Eg3whx9PJyMcUEY6_uLrjv_GQ7F0RT93\"]','[\"123\"]',1,'2020-04-11 05:16:32'),(20,20200326,'050c461','#VNG-050c461','VNG','duytv',1585214317413,2,0,'Shopping','[\"testing \"]','[\"https://drive.google.com/uc?export=view&id=1JdbcUgLfHAq9zjn5PYn4wD5IZprncNAp\"]','[\"Đã xử lý\"]',1,'2020-04-11 05:16:32'),(21,20200326,'aa75a49','#VNG-aa75a49','VNG','duytv',1585217865234,1,0,'Transport','[\"bxnzksmsmjsjsnsnsnnsnsnsnznznxnxnxnxnxnxnnxnxnxnxnxnznxnxnxnnxnxxbdnsnznxnxjxjxnxjjxjxjxjxjxnxnxmxnxmmzjxnxjxjxjxjxjjxjxjxjxjzjjzxjzjxjxjjzjzjxjjxjxjx\"]','[]','[\"123\"]',1,'2020-04-11 05:16:32'),(22,20200326,'30e838c','#VNG-30e838c','VNG','duytv',1585231620779,1,0,'Movie','[\"test again\"]','[\"https://drive.google.com/uc?export=view&id=1DQE3XEu4kG9n2issC5pPgxp80rsI8ixy\"]','[\"123\"]',1,'2020-04-11 05:16:32'),(23,20200326,'28a74c5','#HCMUS-28a74c5','HCMUS','duyna',1585234162711,2,0,'Salary','[\"test\"]','[\"https://drive.google.com/uc?export=view&id=1Nw1Gc2GW0Sld3hxLHK7E3ExvVuz9pKhs\",\"https://drive.google.com/uc?export=view&id=10DuzxnRtL4n2Ud8igp8WXqWcHuMWI665\"]','[\"Đã xử lý khiếu nại\"]',1,'2020-04-11 05:16:32'),(24,20200326,'517bd94','#HCMUS-517bd94','HCMUS','duyna',1585234433580,2,0,'Salary','[\"ttttdjdjdnndnndejjekdkdkkdkdkd djjdjdjdjjdjd jejejeme djekekr\ndjkrjeje \neieieiie\"]','[\"https://drive.google.com/uc?export=view&id=1qw7-fejztwdlnISudMfflgvmLZp4L3tG\"]','[\"Từ chối xử lý khiếu nại\"]',0,'2020-04-11 05:16:32'),(25,20200326,'f4e0ce4','#HCMUS-f4e0ce4','HCMUS','duyna',1585234522810,1,0,'Transport','[\"asd\"]','[]','[\"123\"]',1,'2020-04-11 05:33:54'),(26,20200326,'d4e1ff6','#HCMUS-d4e1ff6','HCMUS','',1585234810660,2,1,'Transport','[\"test 2\"]','[\"https://drive.google.com/uc?export=view&id=1DL8UoliwXgA8Z9JjxsfJheDVUeybNMaf\"]','[\"ok\"]',1,'2020-04-18 09:25:15'),(29,20200327,'92fb2a9','#VNG-92fb2a9','VNG','duytv',1585273344113,1,0,'Transport','[\"test 1\"]','[]','[\"123\"]',1,'2020-04-11 05:16:32'),(30,20200327,'45ba10f','#VNG-45ba10f','VNG','duytv',1585273457940,1,0,'Transport','[\"hạ caa djjd\"]','[]','[\"123\"]',0,'2020-04-11 05:16:32'),(31,20200327,'8473c6e','#VNG-8473c6e','VNG','duytv',1585273843235,1,0,'Transport','[\"aghb\"]','[]','[\"1\"]',1,'2020-04-11 05:16:32'),(32,20200327,'01076de','#VNG-01076de','VNG','duytv',1585274004782,1,0,'Transport','[\"1\"]','[]','[\"ok\"]',1,'2020-04-11 05:16:32'),(33,20200327,'04371a9','#VNG-04371a9','VNG','duytv',1585274774820,1,0,'Transport','[\"2\"]','[]','[]',1,'2020-04-11 05:18:32'),(34,20200327,'26faa21','#VNG-26faa21','VNG','duytv',1585275234771,1,0,'Transport','[\"3\"]','[]','[]',2,'2020-04-11 05:18:32'),(35,20200327,'d4ceb4f','#VNG-d4ceb4f','VNG','duytv',1585276358805,1,0,'Transport','[\"4\"]','[]','[]',2,'2020-04-11 05:18:32'),(36,20200327,'346f522','#VNG-346f522','VNG','duytv',1585276566459,1,0,'Transport','[\"5\"]','[]','[]',2,'2020-04-11 05:18:32'),(37,20200327,'29e4cc7','#VNG-29e4cc7','VNG','duytv',1585276685191,1,0,'Transport','[\"6\"]','[]','[]',2,'2020-04-11 05:18:33'),(38,20200327,'536a460','#VNG-536a460','VNG','duytv',1585276814359,1,0,'Transport','[\"7\"]','[]','[]',2,'2020-04-11 05:18:33'),(39,20200327,'3609465','#VNG-3609465','VNG','duytv',1585277024227,1,0,'Transport','[\"8\"]','[]','[]',2,'2020-04-11 05:18:33'),(40,20200327,'0a03aa9','#VNG-0a03aa9','VNG','duytv',1585277042576,1,0,'Transport','[\"9\"]','[]','[]',2,'2020-04-11 05:18:33'),(41,20200327,'a4a5a51','#VNG-a4a5a51','VNG','duytv',1585316835240,1,0,'Transport','[\"test image view\"]','[\"https://drive.google.com/uc?export=view&id=1euiuh5RfsXBsP_aDbydNaZd96Cgtqds8\",\"https://drive.google.com/uc?export=view&id=141QHnCgAETKwJLpC2P5Wg1XUB_hnH1e-\"]','[]',2,'2020-04-11 05:18:33'),(42,20200327,'8f17d4b','#VNG-8f17d4b','VNG','duytv',1585319398747,1,0,'Transport','[\"abc\"]','[\"https://drive.google.com/uc?export=view&id=1eQNMm_f4jPaRKUdiFbjM9dJFYxjv2q_0\",\"https://drive.google.com/uc?export=view&id=1sxdKAoh36cPC_oVPd4YpMQsl8jaZa5nN\",\"https://drive.google.com/uc?export=view&id=1qejTU5SCcKglXPiea-0AvN8muQb3HGPu\"]','[]',2,'2020-04-11 05:18:33'),(43,20200327,'3122f14','#VNG-3122f14','VNG','duytv',1585319631109,1,0,'Transport','[\"test\"]','[]','[]',2,'2020-04-11 05:18:33'),(44,20200327,'169eff6','#VNG-169eff6','VNG','duytv',1585319771147,1,0,'Transport','[\"asdf\"]','[]','[]',2,'2020-04-11 05:18:33'),(45,20200327,'8b38223','#VNG-8b38223','VNG','duytv',1585319930415,1,0,'Transport','[\"aa\"]','[]','[]',2,'2020-04-11 05:18:34'),(46,20200327,'d0e84ca','#VNG-d0e84ca','VNG','duytv',1585320034126,1,0,'Transport','[\"d\"]','[]','[]',2,'2020-04-11 05:18:34'),(51,20200327,'f906ddd','#HCMUS-f906ddd','HCMUS','duyna',1585324767057,2,0,'Personal','[\"y2\"]','[\"https://drive.google.com/uc?export=view&id=1kTrWbSOcQebMn8VUHB36-ubzf-rp3KJ-\",\"https://drive.google.com/uc?export=view&id=1f4D0lH7WjdbGS-LF3Ht7oOhxw8XVWlxM\"]','[]',2,'2020-04-11 05:18:34'),(52,20200327,'7ce9069','#HCMUS-7ce9069','HCMUS','duyna',1585325509848,2,0,'Shopping','[\"final\"]','[\"https://drive.google.com/uc?export=view&id=1hTmDbHSMO-EjiIVpiONDAOqheO2UyfAi\",\"https://drive.google.com/uc?export=view&id=1-wf9Xs4BrRXNYqfHLJXJkNhh3rXArFuI\"]','[]',2,'2020-04-11 05:18:34'),(53,20200328,'d6f0b49','#HCMUS-d6f0b49','HCMUS','duyna',1585370705772,1,0,'Transport','[\"ios\"]','[\"https://drive.google.com/uc?export=view&id=1m8J8iqCjC0c7n5PzWGHZvy6Z38jte_sz\"]','[]',2,'2020-04-11 05:18:34'),(54,20200328,'2a12885','#HCMUS-2a12885','HCMUS','duyna',1585392877579,2,0,'Food','[\"h\"]','[\"https://drive.google.com/uc?export=view&id=1j48S6GlhrYHVdgkdPbn1UHDxUc0aspVq\"]','[]',2,'2020-04-11 05:18:34'),(55,20200328,'767bc3e','#HCMUS-767bc3e','HCMUS','duyna',1585396031939,1,0,'Transport','[\"large image\"]','[\"https://drive.google.com/uc?export=view&id=1YiFWvouyoR_5lzCXrWft_0Pa2A7fGLSO\"]','[]',2,'2020-04-11 05:18:34'),(56,20200328,'1a7344c','#HCMUS-1a7344c','HCMUS','duyna',1585397025560,2,0,'Transport','[\"nginximagesize\"]','[\"https://drive.google.com/uc?export=view&id=1o5aaAue8mSfa4fmBM2-hwQYHhcwDp-VK\",\"https://drive.google.com/uc?export=view&id=1L2hzGjXpOnVkVIfjsWDGufP1i4S7rBYs\"]','[]',2,'2020-04-11 05:18:35'),(57,20200328,'b7eee54','#HCMUS-b7eee54','HCMUS','duyna',1585397417362,1,0,'Transport','[\"2 large\"]','[\"https://drive.google.com/uc?export=view&id=1lcSTCCnI8WwKgg57LaDwHlCvaAp95V-0\",\"https://drive.google.com/uc?export=view&id=1cP87TxafIhHCR5tp48iOzlfZ6FS4Df08\"]','[\"123\"]',1,'2020-04-11 05:16:32'),(58,20200328,'f0addc3','#VNG-f0addc3','VNG','duytv',1585399912755,1,0,'Transport','[\"t\"]','[\"https://drive.google.com/uc?export=view&id=1F9wfaD-q7t34ZdbkglAPb5CFl-yHvT_b\"]','[]',2,'2020-04-11 05:18:35'),(59,20200328,'85e991f','#HCMUS-85e991f','HCMUS','duyna',1585407143973,1,0,'Transport','[\"u\"]','[\"https://drive.google.com/uc?export=view&id=1yRNAiyNJ_spT5eCIRJyvAqp1ANpIhzz_\"]','[]',2,'2020-04-11 05:18:35'),(60,20200328,'0801c03','#HCMUS-0801c03','HCMUS','duyna',1585407689876,1,0,'Transport','[\"iwiw\"]','[\"https://drive.google.com/uc?export=view&id=12Y9v5-CVQU2HLQdw4I908_3CB65WgY7j\",\"https://drive.google.com/uc?export=view&id=1n6hLmtUs_LjjtoVuQ7k4g0djpslpHkBq\",\"https://drive.google.com/uc?export=view&id=1zAgVf5Kjd8kgiHV29D4tsE_Ler3UvX7s\"]','[\"test tool\"]',0,'2020-04-11 05:16:32'),(61,20200331,'4837873','#-4837873','','',1585656306177,1,1,'Transport','[\"n\"]','[\"https://drive.google.com/uc?export=view&id=1ztY-IYCKX77BchRhF9-Xc-h4AAGuEoGs\"]','[]',2,'2020-04-11 05:33:54'),(62,20200331,'1234576','#VNG-1234576','VNG','admin1',1585656306177,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"test reject\"]',0,'2020-04-11 05:16:32'),(63,20200331,'1234577','#VNG-1234577','VNG','admin1',1585656306177,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"Ok\"]',1,'2020-04-11 05:16:32'),(64,20200331,'1234578','#VNG-1234578','VNG','admin1',1585656306177,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[]',2,'2020-04-11 05:18:35'),(65,20200331,'1234579','#VNG-1234579','VNG','admin1',1585656306177,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"Đã xử lý oke =,=\"]',1,'2020-04-11 05:16:32'),(66,20200331,'1234580','#VNG-1234580','VNG','admin1',1585656306177,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[]',2,'2020-04-11 05:18:35'),(67,20200331,'1234581','#VNG-1234581','VNG','admin1',1585656306177,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[]',2,'2020-04-11 05:18:35'),(68,20200331,'1234582','#VNG-1234582','VNG','admin1',1585656306177,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[]',2,'2020-04-11 05:18:36'),(69,20200331,'1234583','#VNG-1234583','VNG','admin1',1585656306177,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[]',2,'2020-04-11 05:18:36'),(70,20200331,'1234584','#VNG-1234584','VNG','admin1',1585656306177,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[]',2,'2020-04-11 05:18:36'),(81,20200331,'1234585','#VNG-1234585','VNG','admin1',1585656306177,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[]',2,'2020-04-11 05:18:36');
/*!40000 ALTER TABLE `ComplaintLog202003` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ComplaintLog202004`
--

DROP TABLE IF EXISTS `ComplaintLog202004`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ComplaintLog202004` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `complaintId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `receiverType` int NOT NULL DEFAULT '2',
  `isAnonymous` int NOT NULL DEFAULT '0',
  `category` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `content` varchar(8192) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `images` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `response` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '[]',
  `status` int NOT NULL DEFAULT '2',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `complaintId_UNIQUE` (`complaintId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`),
  KEY `receiverType_INDEX` (`receiverType`),
  KEY `complaintId_INDEX` (`complaintId`),
  KEY `status_INDEX` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ComplaintLog202004`
--

LOCK TABLES `ComplaintLog202004` WRITE;
/*!40000 ALTER TABLE `ComplaintLog202004` DISABLE KEYS */;
INSERT INTO `ComplaintLog202004` VALUES (1,20200407,'076cfeb','#HCMUS-076cfeb','HCMUS','duyna',1586265215300,2,0,'Salary','[\"tết image\"]','[\"https://drive.google.com/uc?export=view&id=1V6oyNBh3YL-JylyUfx-B_gB97kYxDvya\",\"https://drive.google.com/uc?export=view&id=10VB3pTujq0poKIkF0kWxq5R50j0tCI1L\",\"https://drive.google.com/uc?export=view&id=1SdDcjROMtUG3E3mpfCGaB2ZqYamVW9m5\"]','[\"asd\"]',1,'2020-04-11 05:16:35'),(2,20200407,'85ccd6b','#HCMUS-85ccd6b','HCMUS','duyna',1586265215406,2,0,'Personal','[\"qqqq\"]','[\"https://drive.google.com/uc?export=view&id=1NII07_CozrihxU7eSUBnPTskkSP3Znb8\",\"https://drive.google.com/uc?export=view&id=1rfxzK1NKgbBGVDXSqbJsAqK0M5HJSrec\",\"https://drive.google.com/uc?export=view&id=16pq-mj5nN_Xk00XJLxO-X9zhDFr8eObZ\"]','[]',2,'2020-04-11 05:17:10'),(3,20200401,'96d0505','#VNG-96d0505','VNG','admin1',1585717644953,2,0,'Food','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"ssss\"]',0,'2020-04-11 05:16:35'),(4,20200401,'36318fb','#VNG-36318fb','VNG','admin1',1585717696236,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"Đã xử lý\"]',1,'2020-04-11 05:16:35'),(5,20200401,'1234567','#VNG-1234567','VNG','admin1',1585717696236,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"test udpate\"]',1,'2020-04-11 05:16:35'),(6,20200401,'1234568','#VNG-1234568','VNG','admin1',1585717696236,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"test noti\"]',1,'2020-04-11 05:16:35'),(7,20200401,'1234569','#VNG-1234569','VNG','admin1',1585717696236,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\" noti again\"]',0,'2020-04-11 05:16:35'),(8,20200401,'1234570','#VNG-1234570','VNG','admin1',1585717696236,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"2\"]',1,'2020-04-11 05:16:35'),(9,20200401,'1234571','#VNG-1234571','VNG','admin1',1585717696236,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"aaaaa\"]',1,'2020-04-11 05:16:35'),(10,20200401,'1234572','#VNG-1234572','VNG','admin1',1585717696236,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"oke\"]',1,'2020-04-14 14:21:57'),(11,20200401,'1234573','#VNG-1234573','VNG','admin1',1585717696236,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[]',2,'2020-04-11 05:17:10'),(12,20200401,'1234574','#VNG-1234574','VNG','admin1',1585717696236,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[\"test\"]',1,'2020-04-14 14:22:24'),(13,20200401,'1234575','#VNG-1234575','VNG','admin1',1585717696236,2,0,'Transport','[\"qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd\"]','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','[]',2,'2020-04-11 05:17:10'),(14,20200402,'78c029f','#HCMUS-78c029f','HCMUS','autth',1585818519902,2,0,'Salary','[\"111\"]','[\"https://drive.google.com/uc?export=view&id=163x89eZdhfO1WdPh8y1e3aFqXJABKwDW\",\"https://drive.google.com/uc?export=view&id=1IG96ZzQ5tgVOfKwFGRc1ojnR4U7d_ilS\",\"https://drive.google.com/uc?export=view&id=1C0pTouKjnCAUKvWPNhh8yHDPcFQQrACO\"]','[]',2,'2020-04-11 05:17:11'),(15,20200402,'9f96908','#HCMUS-9f96908','HCMUS','autth',1585818669606,1,0,'Salary','[\"2222\"]','[\"https://drive.google.com/uc?export=view&id=1o9VsIir6PdMBF8m5AlPBzcLh9pUTskqa\",\"https://drive.google.com/uc?export=view&id=1Oq2wMfTs7CxopYZZTo4XTv8nt6UA2mrz\",\"https://drive.google.com/uc?export=view&id=1-iRR3eyIlLtct1AqGOLLJcQCrWdN2_Y1\"]','[]',1,'2020-04-11 05:17:11'),(16,20200407,'c172946','#VNG-c172946','VNG','duytv',1586266466058,1,0,'Medical','[\"fgh\"]','[]','[\"asdasd\"]',1,'2020-04-11 05:45:37'),(17,20200407,'89911c0','#VNG-89911c0','VNG','duytv',1586266538304,2,0,'Transport','[\"uj\"]','[]','[]',2,'2020-04-18 09:32:17'),(18,20200411,'802f499','#HCMUS-802f499','HCMUS','duydc',1586586272985,2,0,'Transport','[\"asd\",\"123\"]','[\"https://drive.google.com/uc?export=view&id=1CJeZwvGg22zxZqKHNCnpDthEtUwLXQne\",\"https://drive.google.com/uc?export=view&id=11l-8p8jMhni4jNrAKT_KJxXKN9bkSG-t\"]','[\"qiwueiwqueowqueiwquewqiueiwqueoiwqueiwqeuoiwqueoiwqueqwieuqwiewquoeiuwqoei\",\"qwe\"]',0,'2020-04-18 09:41:59'),(19,20200411,'0647a40','#-0647a40','','',1586599336374,2,1,'Transport','[\"test\"]','[]','[]',2,'2020-04-18 09:32:17'),(20,20200411,'53eb818','#HCMUS-53eb818','HCMUS','duydc',1586608189333,2,0,'Transport','[\"bsbs\",\"asdasdsad sadsadsa\",\"qweqeqw\",\"123456456789\"]','[\"https://drive.google.com/uc?export=view&id=1k4WWQ4C44FimhVCPSP5cVfoYI_uSh6ar\"]','[\"bqưeqweokqowesbs\",\"asdasdsad sadsadsa\",\"qweqeqw\",\"qwe\"]',1,'2020-04-18 04:06:33'),(21,20200411,'91201e8','#HCMUS-91201e8','HCMUS','duydc',1586620089890,1,0,'Transport','[\"df\",\"dddas\",\"asd\",\"1\"]','[\"https://drive.google.com/uc?export=view&id=1jhLQab65NrgmR5PQLwT31eE_fGX43bqQ\"]','[\"asádiajsidjasidjasidjdqơkeoqkweowqasd\",\"asq qiwejqwiej\", \"oqweq\", \"asq qiwejqwiejasq qiwejqwiejasq qiwejqwiej\",\"dasda6s51d6as4 1d56a1 sd651 as61d 6a5s1d6 as1 61sad65 a1s6d5 1as65d1 a6s51d 6a5s1 d56a1s 6d1as 65d1 6as51d 65a1sd\"]',1,'2020-04-12 06:10:58'),(22,20200412,'7544e51','#HCMUS-7544e51','HCMUS','duydc',1586654937041,1,0,'Transport','[\"asdas\",\"asd\"]','[]','[\"asq qiwejqwiejasq qiwejqwiej asq qiwejqwiejasq qiwejqwiej\"]',2,'2020-04-22 05:43:35'),(23,20200412,'e694716','#HCMUS-e694716','HCMUS','duydc',1586660807537,2,0,'Transport','[\"sdas\",\"123456789\"]','[]','[\"dasda6s51d6as41d56a1 sd651 as61d 6a5s1d6 as1 61sad65 a1s6d5 1as65d1 a6s51d6a5s1d56a1s6d1asasdasdsadkoqwjeoqjeoeqw65d1 6as51d 65a1sd\"]',2,'2020-04-18 09:32:17'),(24,20200412,'64521f3','#HCMUS-64521f3','HCMUS','duydc',1586670444456,2,0,'Transport','[\"dasda6s51d6as4 1d56a1 sd651 as61d 6a5s1d6 as1 61sad65 a1s6d5 1as65d1 a6s51d 6a5s1 d56a1s 6d1as 65d1 6as51d 65a1sd\",\"456123\"]','[\"https://drive.google.com/uc?export=view&id=1W1Z-yWXQ8uslCWR7M5gz5MhTr4DXfF3U\"]','[\"dasda6s51d6as4 1d56a1 sd651 as61d 6a5s1d6 as1 61sad65 a1s6d5 1as65d1 a6s51d 6a5s1 d56a1s 6d1as 65d1 6as51d 65a1sd\"]',2,'2020-04-18 09:32:17'),(25,20200412,'901ef2a','#HCMUS-901ef2a','HCMUS','autth',1586672975986,2,0,'Food','[\"mmm\"]','[\"https://drive.google.com/uc?export=view&id=1T2wsVgrjIoHeWem5JT8rOpFfMY-fiTyT\",\"https://drive.google.com/uc?export=view&id=1mPXUivZvvA_os5zPaw_PlcJfPPNj1tjd\"]','[\"asq qiwejqwiejasq qiwejqwiej asq qiwejqwiejasq qiwejqwiej\"]',0,'2020-04-12 06:38:53'),(26,20200412,'eefc3d4','#HCMUS-eefc3d4','HCMUS','autth',1586673311103,2,0,'Shopping','[\"real\",\"uuuu\"]','[\"https://drive.google.com/uc?export=view&id=12Z5pjuronEUuRxneFmduMFN39Qr3GZQe\",\"https://drive.google.com/uc?export=view&id=1UgIWCAKpXHQCycwxF8pULiQwgSjGQmBc\"]','[\"asq qiwejqwiejasq qiwejqwiej asq qiwejqwiejasq qiwejqwiej\",\"test\"]',1,'2020-04-18 09:32:49'),(27,20200412,'20f7f12','#HCMUS-20f7f12','HCMUS','duydc',1586679207633,2,0,'Medical','[\"123456\"]','[\"https://drive.google.com/uc?export=view&id=1JMmJALEqTMbj3yefljPnpCVbRF3jLjbF\"]','[]',2,'2020-04-12 08:13:32'),(28,20200412,'8dd218d','#HCMUS-8dd218d','HCMUS','duydc',1586679606280,2,0,'Transport','[\"dhhd\"]','[\"https://drive.google.com/uc?export=view&id=1xHIq8d6wb2ag2P4Njwjd33aVZUdxVVQZ\",\"https://drive.google.com/uc?export=view&id=1dFzxwXf3Y4M-Y4kAfJyTtIeVzV6QQ3pH\"]','[]',2,'2020-04-18 09:32:17'),(29,20200413,'1e7c568','#VNG-1e7c568','VNG','duytv',1586745512561,2,0,'Transport','[\"vdxgfd\"]','[]','[]',2,'2020-04-18 09:32:17'),(30,20200421,'908972c','#HCMUS-908972c','HCMUS','duyna',1587465297374,2,0,'Personal','[\"êw\"]','[\"https://drive.google.com/uc?export=view&id=1tHBKkqPDINxmpHhrYdENgp7HzYdM8Pf_\"]','[]',2,'2020-04-21 10:35:05');
/*!40000 ALTER TABLE `ComplaintLog202004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ComplaintLog202005`
--

DROP TABLE IF EXISTS `ComplaintLog202005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ComplaintLog202005` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `complaintId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `receiverType` int NOT NULL DEFAULT '2',
  `isAnonymous` int NOT NULL DEFAULT '0',
  `category` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `content` varchar(8192) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `images` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `response` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '[]',
  `status` int NOT NULL DEFAULT '2',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `complaintId_UNIQUE` (`complaintId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`),
  KEY `receiverType_INDEX` (`receiverType`),
  KEY `complaintId_INDEX` (`complaintId`),
  KEY `status_INDEX` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ComplaintLog202005`
--

LOCK TABLES `ComplaintLog202005` WRITE;
/*!40000 ALTER TABLE `ComplaintLog202005` DISABLE KEYS */;
/*!40000 ALTER TABLE `ComplaintLog202005` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LeavingRequestLog202004`
--

DROP TABLE IF EXISTS `LeavingRequestLog202004`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LeavingRequestLog202004` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `leavingRequestId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `useDayOff` tinyint(1) NOT NULL DEFAULT '1',
  `hasSalary` tinyint(1) NOT NULL DEFAULT '0',
  `category` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `reason` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `detail` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `response` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `approver` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `status` int NOT NULL DEFAULT '2',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `complaintId_UNIQUE` (`leavingRequestId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`),
  KEY `status_INDEX` (`status`),
  KEY `leavingRequestId_INDEX` (`leavingRequestId`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LeavingRequestLog202004`
--

LOCK TABLES `LeavingRequestLog202004` WRITE;
/*!40000 ALTER TABLE `LeavingRequestLog202004` DISABLE KEYS */;
INSERT INTO `LeavingRequestLog202004` VALUES (8,20200406,'a43146a','#HCMUS-LR-a43146a','HCMUS','duydc',1586165998355,1,0,'Nghỉ phép năm','asdasd','[{\"date\":\"20200412\",\"session\":1},{\"date\":\"20200413\",\"session\":0},{\"date\":\"20200414\",\"session\":2}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:50:24'),(9,20200406,'23c9ef5','#HCMUS-LR-23c9ef5','HCMUS','duydc',1586169633170,1,0,'Nghỉ phép năm','dasd','[{\"date\":\"20200421\",\"session\":0},{\"date\":\"20200422\",\"session\":0},{\"date\":\"20200423\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:19:04'),(10,20200406,'3075b6b','#HCMUS-LR-3075b6b','HCMUS','duydc',1586174196253,1,0,'Nghỉ phép năm','fasddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd','[{\"date\":\"20200406\",\"session\":0}]','fasdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd','duyna',0,'2020-04-08 13:28:13'),(11,20200407,'2561a55','#HCMUS-LR-2561a55','HCMUS','duydc',1586260579985,1,0,'Nghỉ phép năm','dasd','[{\"date\":\"20200513\",\"session\":0},{\"date\":\"20200520\",\"session\":1}]','21312098310928321938192381923m21k3moi12j3o1i2j3o123j','duyna',1,'2020-04-08 13:28:13'),(12,20200407,'e997334','#HCMUS-LR-e997334','HCMUS','duydc',1586260607208,1,0,'Nghỉ thai sản','dasd','[{\"date\":\"20200529\",\"session\":2}]','ok','duyna',1,'2020-04-12 15:44:53'),(13,20200407,'574e989','#HCMUS-LR-574e989','HCMUS','duydc',1586260965193,1,0,'Nghỉ bệnh','asdas','[{\"date\":\"20200609\",\"session\":1},{\"date\":\"20200610\",\"session\":1},{\"date\":\"20200611\",\"session\":0},{\"date\":\"20200612\",\"session\":0}]','','',2,'2020-04-07 12:02:49'),(14,20200407,'dad304e','#HCMUS-LR-dad304e','HCMUS','duydc',1586262025656,1,0,'Nghỉ bù do làm thêm giờ','qwq112','[{\"date\":\"20200522\",\"session\":2},{\"date\":\"20200529\",\"session\":1}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:48:35'),(15,20200407,'cfd27ce','#HCMUS-LR-cfd27ce','HCMUS','duydc',1586262228744,1,0,'Nghỉ phép năm','dasd','[{\"date\":\"20200606\",\"session\":0},{\"date\":\"20200613\",\"session\":0}]','123','duyna',1,'2020-04-08 05:39:07'),(16,20200407,'ba60a52','#HCMUS-LR-ba60a52','HCMUS','duydc',1586262577392,1,0,'Nghỉ phép năm','asd','[{\"date\":\"20200813\",\"session\":0}]','abc','duyna',1,'2020-04-08 05:39:07'),(17,20200407,'e25efd1','#HCMUS-LR-e25efd1','HCMUS','duydc',1586267104391,1,0,'Nghỉ phép không hưởng lương','Do hoc','[{\"date\":\"20200408\",\"session\":1},{\"date\":\"20200409\",\"session\":0},{\"date\":\"20200410\",\"session\":0}]','1','duyna',0,'2020-04-08 05:39:06'),(18,20200409,'8dcff74','#HCMUS-LR-8dcff74','HCMUS','duydc',1586427613618,0,0,'Nghỉ bù do làm thêm giờ','uyy','[{\"date\":\"20200411\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:51:26'),(19,20200409,'ca55cbc','#VNG-LR-ca55cbc','VNG','test',1586427943592,1,0,'Nghỉ phép năm','test','[{\"date\":\"20200409\",\"session\":0},{\"date\":\"20200410\",\"session\":0},{\"date\":\"20200411\",\"session\":0}]','test update','admin1',1,'2020-04-12 04:51:12'),(20,20200409,'1f43b9b','#HCMUS-LR-1f43b9b','HCMUS','duytv',1586428238347,1,0,'Nghỉ phép năm','test','[{\"date\":\"20200409\",\"session\":0},{\"date\":\"20200410\",\"session\":0},{\"date\":\"20200411\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-14 03:51:06'),(21,20200409,'6bbc71c','#HCMUS-LR-6bbc71c','HCMUS','duytv',1586435923271,0,0,'Nghỉ bù do làm thêm giờ','test','[{\"date\":\"20200413\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:55:57'),(22,20200409,'bcbaa39','#HCMUS-LR-bcbaa39','HCMUS','duytv',1586436657251,0,0,'Nghỉ phép không hưởng lương','abc','[{\"date\":\"20200414\",\"session\":1}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:56:04'),(23,20200409,'55f1b71','#HCMUS-LR-55f1b71','HCMUS','duytv',1586437089553,0,0,'Nghỉ phép không hưởng lương','qwer','[{\"date\":\"20200415\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-13 12:26:28'),(24,20200409,'ae75308','#HCMUS-LR-ae75308','HCMUS','duytv',1586437250277,1,0,'Nghỉ phép năm','ttest','[{\"date\":\"20200416\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-13 12:33:02'),(25,20200409,'ee90c92','#HCMUS-LR-ee90c92','HCMUS','duytv',1586437341968,1,0,'Nghỉ phép năm','jkl','[{\"date\":\"20200417\",\"session\":1}]','Nhân viên tự hủy đơn','',0,'2020-04-13 12:57:57'),(26,20200409,'b006c1b','#HCMUS-LR-b006c1b','HCMUS','duytv',1586438296806,0,0,'Nghỉ phép không hưởng lương','yyyyy','[{\"date\":\"20200418\",\"session\":1}]','Nhân viên tự hủy đơn','',0,'2020-04-13 13:33:36'),(27,20200410,'45ce501','#HCMUS-LR-45ce501','HCMUS','duydc',1586522541620,0,0,'Nghỉ bù do làm thêm giờ','ii','[{\"date\":\"20200420\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:19:28'),(28,20200410,'c3f6fb1','#HCMUS-LR-c3f6fb1','HCMUS','duydc',1586533198785,1,0,'Nghỉ phép năm','test','[{\"date\":\"20200424\",\"session\":0},{\"date\":\"20200425\",\"session\":0},{\"date\":\"20200426\",\"session\":0},{\"date\":\"20200427\",\"session\":0},{\"date\":\"20200428\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:35:04'),(29,20200412,'baddf82','#HCMUS-LR-baddf82','HCMUS','duydc',1586689469330,0,0,'Nghỉ thai sản','abc','[{\"date\":\"20200418\",\"session\":0},{\"date\":\"20200419\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:35:24'),(30,20200412,'99a3ff8','#VNG-LR-99a3ff8','VNG','duytv',1586700862355,0,0,'Nghỉ phép không hưởng lương','abc','[{\"date\":\"20200412\",\"session\":1}]','','',2,'2020-04-12 14:14:24'),(31,20200413,'76a54fb','#VNG-LR-76a54fb','VNG','duytv',1586743639740,0,0,'Nghỉ bù do làm thêm giờ','qwert','[{\"date\":\"20200413\",\"session\":0}]','','',2,'2020-04-13 02:07:21'),(32,20200413,'2dc4368','#VNG-LR-2dc4368','VNG','duytv',1586743777288,1,0,'Nghỉ bệnh','qwert','[{\"date\":\"20200414\",\"session\":0}]','','',2,'2020-04-13 02:10:06'),(33,20200413,'1e18c1e','#VNG-LR-1e18c1e','VNG','duytv',1586744070544,0,0,'Nghỉ bù do làm thêm giờ','qqqqq','[{\"date\":\"20200415\",\"session\":0}]','','',2,'2020-04-13 02:14:32'),(34,20200413,'5433e98','#VNG-LR-5433e98','VNG','duytv',1586744189126,0,0,'Nghỉ bù do làm thêm giờ','qqqqq','[{\"date\":\"20200416\",\"session\":0}]','','',2,'2020-04-13 02:16:30'),(35,20200413,'fbabeda','#VNG-LR-fbabeda','VNG','duytv',1586744235822,0,0,'Nghỉ thai sản','dffsd','[{\"date\":\"20200417\",\"session\":0}]','','',2,'2020-04-13 02:17:17'),(36,20200413,'a986c87','#VNG-LR-a986c87','VNG','duytv',1586744363271,0,0,'Nghỉ thai sản','rrrrrr','[{\"date\":\"20200418\",\"session\":1}]','','',2,'2020-04-13 02:19:24'),(37,20200413,'176140a','#VNG-LR-176140a','VNG','duytv',1586745098361,0,0,'Nghỉ bù do làm thêm giờ','fdfdsf','[{\"date\":\"20200418\",\"session\":2}]','','',2,'2020-04-13 02:31:39'),(38,20200413,'a6ee7f5','#VNG-LR-a6ee7f5','VNG','duytv',1586745479918,0,0,'Nghỉ bù do làm thêm giờ','bxcvxv','[{\"date\":\"20200419\",\"session\":0}]','','',2,'2020-04-13 02:38:01'),(39,20200413,'bb1636d','#VNG-LR-bb1636d','VNG','duytv',1586745750914,0,0,'Nghỉ bù do làm thêm giờ','dgdfs','[{\"date\":\"20200420\",\"session\":1}]','','',2,'2020-04-13 02:42:32'),(40,20200413,'eb34494','#VNG-LR-eb34494','VNG','duytv',1586746574931,0,0,'Nghỉ phép không hưởng lương','dsdfdsf','[{\"date\":\"20200421\",\"session\":1}]','','',2,'2020-04-13 02:56:16'),(41,20200413,'e8160d9','#VNG-LR-e8160d9','VNG','duytv',1586746840145,0,0,'Nghỉ phép không hưởng lương','dfsdfd','[{\"date\":\"20200421\",\"session\":2}]','','',2,'2020-04-13 03:00:41'),(42,20200413,'1130e9b','#VNG-LR-1130e9b','VNG','duytv',1586746888100,0,0,'Nghỉ thai sản','cvxcxc','[{\"date\":\"20200422\",\"session\":0}]','','',2,'2020-04-13 03:01:29'),(43,20200413,'14ff0cc','#VNG-LR-14ff0cc','VNG','duytv',1586747036767,0,0,'Nghỉ phép không hưởng lương','nbvnv','[{\"date\":\"20200423\",\"session\":1}]','','',2,'2020-04-13 03:03:58'),(44,20200413,'11e5a0e','#VNG-LR-11e5a0e','VNG','duytv',1586747894994,0,0,'Nghỉ thai sản','thjj','[{\"date\":\"20200423\",\"session\":2}]','','',2,'2020-04-13 03:18:16'),(45,20200413,'620bf56','#HCMUS-LR-620bf56','HCMUS','duytv',1586785022384,0,0,'Nghỉ thai sản','test','[{\"date\":\"20200419\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-13 13:37:42'),(46,20200413,'4bc0725','#VNG-LR-4bc0725','VNG','admin1',1586791268741,0,0,'Nghỉ thai sản','ggg','[{\"date\":\"20200415\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-13 15:21:54'),(47,20200413,'b4091ae','#VNG-LR-b4091ae','VNG','admin1',1586791364275,1,0,'Nghỉ phép năm','gggyyyyy','[{\"date\":\"20200414\",\"session\":0}]','','',2,'2020-04-13 15:22:47'),(48,20200414,'bb1aeb6','#HCMUS-LR-bb1aeb6','HCMUS','duytv',1586838401337,0,0,'Nghỉ bù do làm thêm giờ','sadadasd','[{\"date\":\"20200422\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:55:27'),(49,20200414,'7d41777','#VNG-LR-7d41777','VNG','admin1',1586846918408,0,0,'Nghỉ phép không hưởng lương','yyu','[{\"date\":\"20200416\",\"session\":1}]','','',2,'2020-04-14 06:48:40'),(50,20200414,'3f46bca','#VNG-LR-3f46bca','VNG','admin1',1586847001367,0,0,'Nghỉ thai sản','uu','[{\"date\":\"20200415\",\"session\":0}]','','',2,'2020-04-14 06:50:04'),(51,20200414,'096218a','#HCMUS-LR-096218a','HCMUS','duytv',1586848144375,1,0,'Nghỉ phép năm','test','[{\"date\":\"20200420\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:56:17'),(52,20200414,'dc5b87b','#HCMUS-LR-dc5b87b','HCMUS','duytv',1586849098648,1,0,'Nghỉ phép năm','fdsfsd','[{\"date\":\"20200424\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:55:43'),(53,20200414,'b5b5771','#HCMUS-LR-b5b5771','HCMUS','duytv',1586849200904,0,0,'Nghỉ bù do làm thêm giờ','gjggk','[{\"date\":\"20200423\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:55:35'),(54,20200414,'d42c13f','#HCMUS-LR-d42c13f','HCMUS','duytv',1586849377692,0,0,'Nghỉ phép không hưởng lương','test','[{\"date\":\"20200421\",\"session\":1}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:55:19'),(55,20200414,'1cd99cc','#HCMUS-LR-1cd99cc','HCMUS','duytv',1586849444890,0,0,'Nghỉ phép không hưởng lương','test','[{\"date\":\"20200425\",\"session\":1}]','','',2,'2020-04-14 07:30:47'),(56,20200415,'b5f4bf2','#HCMUS-LR-b5f4bf2','HCMUS','duyna',1586918577865,1,0,'Nghỉ phép năm','test simulator','[{\"date\":\"20200415\",\"session\":0}]','','',2,'2020-04-15 02:43:00'),(57,20200415,'332aaa9','#HCMUS-LR-332aaa9','HCMUS','duyna',1586918603570,1,0,'Nghỉ bệnh','test 2','[{\"date\":\"20200416\",\"session\":0},{\"date\":\"20200418\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:12:05'),(58,20200415,'6020605','#HCMUS-LR-6020605','HCMUS','duyna',1586918857383,0,0,'Nghỉ bù do làm thêm giờ','m','[{\"date\":\"20200419\",\"session\":1}]','Nhân viên tự hủy đơn','',0,'2020-04-15 02:47:48'),(59,20200415,'aa96a56','#HCMUS-LR-aa96a56','HCMUS','duyna',1586919833105,0,0,'Nghỉ thai sản','test real','[{\"date\":\"20200420\",\"session\":1}]','Nhân viên tự hủy đơn','',0,'2020-04-15 03:04:01'),(60,20200415,'4ee217b','#HCMUS-LR-4ee217b','HCMUS','duyna',1586919880036,1,0,'Nghỉ bệnh','ưuuw','[{\"date\":\"20200421\",\"session\":0},{\"date\":\"20200423\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:12:15'),(61,20200415,'7bd1d15','#HCMUS-LR-7bd1d15','HCMUS','duyna',1586920291724,1,0,'Nghỉ bệnh','jj','[{\"date\":\"20200426\",\"session\":0},{\"date\":\"20200429\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 07:06:31'),(62,20200415,'d716b90','#HCMUS-LR-d716b90','HCMUS','duytv',1586923639249,0,0,'Nghỉ phép không hưởng lương','test','[{\"date\":\"20200427\",\"session\":0},{\"date\":\"20200429\",\"session\":0}]','asd','duyna',1,'2020-04-18 09:22:55'),(63,20200415,'474bb68','#HCMUS-LR-474bb68','HCMUS','duytv',1586924014253,0,0,'Nghỉ thai sản','fdsfsf','[{\"date\":\"20200426\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 05:09:09'),(64,20200415,'7b2549d','#VNG-LR-7b2549d','VNG','admin1',1586933458723,1,0,'Nghỉ bệnh','android','[{\"date\":\"20200419\",\"session\":0},{\"date\":\"20200422\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 06:51:11'),(65,20200415,'6d2af03','#HCMUS-LR-6d2af03','HCMUS','duyna',1586934604878,1,0,'Nghỉ phép năm','auqo','[{\"date\":\"20200501\",\"session\":0},{\"date\":\"20200504\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 15:12:35'),(66,20200415,'8db3298','#HCMUS-LR-8db3298','HCMUS','duyna',1586966948477,1,1,'Nghỉ phép năm','qqq','[{\"date\":\"20200427\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 16:09:32'),(67,20200415,'b9f5084','#HCMUS-LR-b9f5084','HCMUS','duyna',1586966957279,1,1,'Nghỉ phép năm','iii','[{\"date\":\"20200428\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 16:09:35'),(68,20200415,'f39820e','#HCMUS-LR-f39820e','HCMUS','duyna',1586967296621,1,0,'Nghỉ phép không hưởng lương','yy','[{\"date\":\"20200422\",\"session\":0}]','Nhân viên tự hủy đơn','',0,'2020-04-15 16:15:07'),(69,20200421,'ebcb054','#HCMUS-LR-ebcb054','HCMUS','duyna',1587476119830,1,1,'Nghỉ phép năm','uuuui','[{\"date\":\"20200422\",\"session\":0},{\"date\":\"20200424\",\"session\":0}]','','',2,'2020-04-21 13:35:22'),(70,20200422,'5e8888a','#HCMUS-LR-5e8888a','HCMUS','duydc',1587530941759,1,0,'Nghỉ bệnh','hhkhjk','[{\"date\":\"20200422\",\"session\":0}]','','',2,'2020-04-22 04:49:05'),(71,20200422,'d49ff47','#HCMUS-LR-d49ff47','HCMUS','duydc',1587530998880,1,0,'Nghỉ bệnh','asdqwe','[{\"date\":\"20200722\",\"session\":0},{\"date\":\"20200723\",\"session\":0},{\"date\":\"20200724\",\"session\":2}]','','',2,'2020-04-22 04:50:02'),(72,20200425,'efc5175','#HCMUS-LR-efc5175','HCMUS','duyna',1587787882280,1,1,'Nghỉ phép năm','yyy','[{\"date\":\"20200502\",\"session\":0}]','','',2,'2020-04-25 04:11:25'),(73,20200425,'aa36958','#HCMUS-LR-aa36958','HCMUS','duyna',1587791340233,1,1,'Nghỉ phép năm','test','[{\"date\":\"20200506\",\"session\":2}]','','',2,'2020-04-25 05:09:05'),(74,20200425,'9b6d16e','#HCMUS-LR-9b6d16e','HCMUS','duyna',1587791397679,1,1,'Nghỉ phép năm','ytt','[{\"date\":\"20200509\",\"session\":1}]','','',2,'2020-04-25 05:09:59');
/*!40000 ALTER TABLE `LeavingRequestLog202004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LeavingRequestLog202005`
--

DROP TABLE IF EXISTS `LeavingRequestLog202005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LeavingRequestLog202005` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `leavingRequestId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `useDayOff` tinyint(1) NOT NULL DEFAULT '1',
  `hasSalary` tinyint(1) NOT NULL DEFAULT '0',
  `category` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `reason` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `detail` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `response` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `approver` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `status` int NOT NULL DEFAULT '2',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `complaintId_UNIQUE` (`leavingRequestId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`),
  KEY `status_INDEX` (`status`),
  KEY `leavingRequestId_INDEX` (`leavingRequestId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LeavingRequestLog202005`
--

LOCK TABLES `LeavingRequestLog202005` WRITE;
/*!40000 ALTER TABLE `LeavingRequestLog202005` DISABLE KEYS */;
/*!40000 ALTER TABLE `LeavingRequestLog202005` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LogCounter`
--

DROP TABLE IF EXISTS `LogCounter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LogCounter` (
  `logType` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `yyyyMM` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `params` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `count` int NOT NULL DEFAULT '0',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logType`,`yyyyMM`,`params`),
  KEY `logType_IDX` (`logType`),
  KEY `params_IDX` (`params`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LogCounter`
--

LOCK TABLES `LogCounter` WRITE;
/*!40000 ALTER TABLE `LogCounter` DISABLE KEYS */;
INSERT INTO `LogCounter` VALUES ('ComplaintLog','202003','1',18,'2020-04-04 13:59:57'),('ComplaintLog','202003','2-HCMUS',4,'2020-04-18 09:25:15'),('ComplaintLog','202003','2-VNG',7,'2020-04-08 05:49:42'),('ComplaintLog','202004','1',16,'2020-04-22 05:43:35'),('ComplaintLog','202004','2-HCMUS',4,'2020-04-21 10:35:05'),('ComplaintLog','202004','2-VNG',2,'2020-04-14 14:22:24'),('LeavingRequestLog','202004','HCMUS',12,'2020-04-25 05:09:58'),('LeavingRequestLog','202004','VNG',18,'2020-04-15 06:51:11'),('NonReadNotificationLog','','1-HCMUS-duydc',30,'2020-04-28 13:15:52'),('NotificationLog','202003','1-HCMUS-duydc',15,'2020-04-28 13:13:27'),('NotificationLog','202004','1-HCMUS-duydc',15,'2020-04-28 13:13:27');
/*!40000 ALTER TABLE `LogCounter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NotificationLog202003`
--

DROP TABLE IF EXISTS `NotificationLog202003`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NotificationLog202003` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `notiId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `screenId` int NOT NULL DEFAULT '0',
  `clientId` int NOT NULL DEFAULT '1',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `sendTime` bigint NOT NULL DEFAULT '0',
  `retryTime` bigint NOT NULL DEFAULT '0',
  `title` varchar(512) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `content` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `iconId` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `iconURL` varchar(512) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `tokenPush` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `status` int NOT NULL DEFAULT '2',
  `hasRead` tinyint(1) NOT NULL DEFAULT '0',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`),
  KEY `status_INDEX` (`status`),
  KEY `clientId_INDEX` (`clientId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NotificationLog202003`
--

LOCK TABLES `NotificationLog202003` WRITE;
/*!40000 ALTER TABLE `NotificationLog202003` DISABLE KEYS */;
INSERT INTO `NotificationLog202003` VALUES (1,20200329,'abcxyz','20200329-00016',1,1,'HCMUS','duydc',1585500460670,1585500460670,'abc','asd','checkin','https://drive.google.com/uc?export=view&id=13VkeHpPGGQPIqSPylQHX1FBGaLpJ6kM3','[]',2,0,'2020-04-28 13:53:02'),(2,20200329,'t3','20200329-00017',7,1,'HCMUS','duydc',1585500460670,1585500460670,'qweqqqwqq','sadsadsadwqewqeqqeqwwqeweacxdweqesadadwqe','complaint','https://drive.google.com/uc?export=view&id=1bAZ6NAfVxFb1jPWqT8wIkPGaZ9ZObi3a','[]',2,0,'2020-04-28 13:53:02'),(3,20200329,'zzzz','20200329-00018',0,1,'HCMUS','duydc',1585500460670,1585500460670,'abc','asd','history','https://drive.google.com/uc?export=view&id=1vQ3HCWVyg1rX7P1-aYo1MZvICrR3BbEf','[]',2,0,'2020-04-28 06:39:44'),(4,20200329,'raa','20200329-00019',0,1,'HCMUS','duydc',1585500460670,1585500460670,'qweqqqwqq','qweqqqwqq','home','https://drive.google.com/uc?export=view&id=12zhYAfrJ9OsX2_rdQohtFpD0rLDgjnT2','[]',2,0,'2020-04-28 06:39:44'),(5,20200329,'mmm','20200329-00020',2,1,'HCMUS','duydc',1585500460670,1585500460670,'abc','asd','leaving','https://drive.google.com/uc?export=view&id=181Xuew9SGy17KJ2x6wHeLJaRTBFkNHZg','[]',2,0,'2020-04-28 13:53:03'),(6,20200327,'abcxyz','20200327-00021',0,1,'HCMUS','duydc',1585300460670,1585300460670,'asd asdwq wqeq axzzcx wqeqwe sadasd asdq','qweqqqwqq','noti','https://drive.google.com/uc?export=view&id=1lwV3OFqdTDH3cFHt-cAruMVGN4SV6yTi','[]',2,0,'2020-04-28 06:39:44'),(7,20200327,'t3','20200327-00022',6,1,'HCMUS','duydc',1585300460670,1585300460670,'qweqqqwqq','asd','payroll','https://drive.google.com/uc?export=view&id=14zSFk6qYhBHLINARDzWbv8IWG8_q8KAU','[]',2,0,'2020-04-28 13:53:03'),(8,20200327,'zzzz','20200327-00023',4,1,'HCMUS','duydc',1585300460670,1585300460670,'abc','aaaa','personal','https://drive.google.com/uc?export=view&id=15q22GGIOOPY6wFgKumH-M88VDQ7sKUru','[]',2,0,'2020-04-28 13:53:03'),(9,20200327,'raa','20200327-00024',3,1,'HCMUS','duydc',1585300460670,1585300460670,'cxzxzc','aaaa','statistics','https://drive.google.com/uc?export=view&id=1ASCBAWzpW2Gxr74Y2dNvkocaxKkxJ_iv','[]',2,0,'2020-04-28 13:53:03'),(10,20200327,'mmm','20200327-00025',1,1,'HCMUS','duydc',1584200360670,1584200360670,'abc','sadsadsadwqewqeqqeqwwqeweacxdweqesadadwqe','checkin','https://drive.google.com/uc?export=view&id=13VkeHpPGGQPIqSPylQHX1FBGaLpJ6kM3','[]',2,0,'2020-04-28 13:53:02'),(11,20200314,'abcxyz','20200314-00026',7,1,'HCMUS','duydc',1584200360670,1584200360670,'asd asdwq wqeq axzzcx wqeqwe sadasd asdq','qweqqqwqq','complaint','https://drive.google.com/uc?export=view&id=1bAZ6NAfVxFb1jPWqT8wIkPGaZ9ZObi3a','[]',2,0,'2020-04-28 13:53:02'),(12,20200314,'t3','20200314-00027',0,1,'HCMUS','duydc',1584200360670,1584200360670,'qweqqqwqq','asd','history','https://drive.google.com/uc?export=view&id=1vQ3HCWVyg1rX7P1-aYo1MZvICrR3BbEf','[]',2,0,'2020-04-28 06:39:44'),(13,20200314,'zzzz','20200314-00028',0,1,'HCMUS','duydc',1584200360670,1584200360670,'abc','aaaa','home','https://drive.google.com/uc?export=view&id=12zhYAfrJ9OsX2_rdQohtFpD0rLDgjnT2','[]',2,0,'2020-04-28 06:39:44'),(14,20200314,'raa','20200314-00029',0,1,'HCMUS','duydc',1584200360670,1584200360670,'cxzxzc','aaaa','leaving','https://drive.google.com/uc?export=view&id=181Xuew9SGy17KJ2x6wHeLJaRTBFkNHZg','[]',2,0,'2020-04-28 06:39:44'),(15,20200314,'mmm','20200314-00030',2,1,'HCMUS','duydc',1584200360670,1584200360670,'abc','sadsadsadwqewqeqqeqwwqeweacxdweqesadadwqe','noti','https://drive.google.com/uc?export=view&id=1lwV3OFqdTDH3cFHt-cAruMVGN4SV6yTi','[]',2,0,'2020-04-28 13:53:03');
/*!40000 ALTER TABLE `NotificationLog202003` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NotificationLog202004`
--

DROP TABLE IF EXISTS `NotificationLog202004`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NotificationLog202004` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `notiId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `screenId` int NOT NULL DEFAULT '0',
  `clientId` int NOT NULL DEFAULT '1',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `sendTime` bigint NOT NULL DEFAULT '0',
  `retryTime` bigint NOT NULL DEFAULT '0',
  `title` varchar(512) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `content` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `iconId` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `iconURL` varchar(512) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `tokenPush` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `status` int NOT NULL DEFAULT '2',
  `hasRead` tinyint(1) NOT NULL DEFAULT '0',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`),
  KEY `status_INDEX` (`status`),
  KEY `clientId_INDEX` (`clientId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NotificationLog202004`
--

LOCK TABLES `NotificationLog202004` WRITE;
/*!40000 ALTER TABLE `NotificationLog202004` DISABLE KEYS */;
INSERT INTO `NotificationLog202004` VALUES (1,20200427,'abcxyz','20200427-0001',1,1,'HCMUS','duydc',1588000460670,1588000460670,'random title','asd asdwq wqeq axzzcx wqeqwe sadasd asdq','checkin','https://drive.google.com/uc?export=view&id=13VkeHpPGGQPIqSPylQHX1FBGaLpJ6kM3','[]',2,0,'2020-04-28 13:53:20'),(2,20200426,'abcxyz','20200426-0002',7,1,'HCMUS','duydc',1587900340670,1587900340670,'a very long long long long long long long title','qwqe qweewq qwewqe','complaint','https://drive.google.com/uc?export=view&id=1bAZ6NAfVxFb1jPWqT8wIkPGaZ9ZObi3a','[]',2,0,'2020-04-28 13:53:21'),(3,20200426,'xyz','20200426-0003',0,1,'HCMUS','duydc',1587900140670,1587900140670,'sadsadsadwqewqeqqeqwwqeweacxdweqesadadwqe','asd asda asdad aaa zxzxc','history','https://drive.google.com/uc?export=view&id=1vQ3HCWVyg1rX7P1-aYo1MZvICrR3BbEf','[]',2,0,'2020-04-28 06:39:44'),(4,20200425,'yyyy','20200425-0004',0,1,'HCMUS','duydc',1587820140670,1587820140670,'abc','asd','home','https://drive.google.com/uc?export=view&id=12zhYAfrJ9OsX2_rdQohtFpD0rLDgjnT2','[]',2,0,'2020-04-28 06:39:44'),(5,20200425,'xxxx','20200425-0005',2,1,'HCMUS','duydc',1587820140670,1587820140670,'cxzxzc','aaaa','leaving','https://drive.google.com/uc?export=view&id=181Xuew9SGy17KJ2x6wHeLJaRTBFkNHZg','[]',2,0,'2020-04-28 13:53:21'),(6,20200423,'raaa','20200423-0006',0,1,'HCMUS','duydc',1587620140670,1587620140670,'abc','qwewqe','noti','https://drive.google.com/uc?export=view&id=1lwV3OFqdTDH3cFHt-cAruMVGN4SV6yTi','[]',2,0,'2020-04-28 06:39:44'),(7,20200423,'qkew','20200423-0007',6,1,'HCMUS','duydc',1587620140370,1587620140370,'qweqqqwqq','cxzxzc','payroll','https://drive.google.com/uc?export=view&id=14zSFk6qYhBHLINARDzWbv8IWG8_q8KAU','[]',2,0,'2020-04-28 13:53:21'),(8,20200420,'qkeq','20200420-0008',4,1,'HCMUS','duydc',1587380140670,1587380140670,'abc','sadsadsadwqewqeqqeqwwqeweacxdweqesadadwqe','personal','https://drive.google.com/uc?export=view&id=15q22GGIOOPY6wFgKumH-M88VDQ7sKUru','[]',2,0,'2020-04-28 13:53:21'),(9,20200420,'zzzzz','20200420-0009',3,1,'HCMUS','duydc',1587380140370,1587380140370,'asd asdwq wqeq axzzcx wqeqwe sadasd asdq','asd asda asdad aaa zxzxc','statistics','https://drive.google.com/uc?export=view&id=1ASCBAWzpW2Gxr74Y2dNvkocaxKkxJ_iv','[]',2,0,'2020-04-28 13:53:21'),(10,20200420,'q1112i','20200420-0010',1,1,'HCMUS','duydc',1587380135370,1587380135370,'abc','qweqqqwqq','checkin','https://drive.google.com/uc?export=view&id=13VkeHpPGGQPIqSPylQHX1FBGaLpJ6kM3','[]',2,0,'2020-04-28 13:53:21'),(11,20200418,'qwe111','20200418-0011',7,1,'HCMUS','duydc',1587175140670,1587175140670,'abc','qweqqqwqq aaaa','complaint','https://drive.google.com/uc?export=view&id=1bAZ6NAfVxFb1jPWqT8wIkPGaZ9ZObi3a','[]',2,0,'2020-04-28 13:53:21'),(12,20200418,'qwe112','20200418-0012',0,1,'HCMUS','duydc',1587175130670,1587175130670,'qweqqqwqq','qqq111','history','https://drive.google.com/uc?export=view&id=1vQ3HCWVyg1rX7P1-aYo1MZvICrR3BbEf','[]',2,0,'2020-04-28 06:39:44'),(13,20200416,'qwe113','20200416-0013',0,1,'HCMUS','duydc',1587055140670,1587055140670,'qweqqqwqq','qweqqqwqq aaaa','home','https://drive.google.com/uc?export=view&id=12zhYAfrJ9OsX2_rdQohtFpD0rLDgjnT2','[]',2,0,'2020-04-28 06:39:44'),(14,20200416,'qwe114','20200416-0014',0,1,'HCMUS','duydc',1587175120670,1587175120670,'asd asdwq wqeq axzzcx wqeqwe sadasd asdq','qqq111','leaving','https://drive.google.com/uc?export=view&id=181Xuew9SGy17KJ2x6wHeLJaRTBFkNHZg','[]',2,0,'2020-04-28 06:39:44'),(15,20200416,'qwe115','20200416-0015',2,1,'HCMUS','duydc',1587175110670,1587175110670,'qweqqqwqq','sadsadsadwqewqeqqeqwwqeweacxdweqesadadwqe','noti','https://drive.google.com/uc?export=view&id=1lwV3OFqdTDH3cFHt-cAruMVGN4SV6yTi','[]',2,0,'2020-04-28 13:53:21');
/*!40000 ALTER TABLE `NotificationLog202004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NotificationLog202005`
--

DROP TABLE IF EXISTS `NotificationLog202005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NotificationLog202005` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `notiId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `screenId` int NOT NULL DEFAULT '0',
  `clientId` int NOT NULL DEFAULT '1',
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `sendTime` bigint NOT NULL DEFAULT '0',
  `retryTime` bigint NOT NULL DEFAULT '0',
  `title` varchar(512) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `content` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `iconId` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `iconURL` varchar(512) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `tokenPush` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '[]',
  `status` int NOT NULL DEFAULT '2',
  `hasRead` tinyint(1) NOT NULL DEFAULT '0',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`),
  KEY `status_INDEX` (`status`),
  KEY `clientId_INDEX` (`clientId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NotificationLog202005`
--

LOCK TABLES `NotificationLog202005` WRITE;
/*!40000 ALTER TABLE `NotificationLog202005` DISABLE KEYS */;
/*!40000 ALTER TABLE `NotificationLog202005` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-29 12:51:15
CREATE DATABASE  IF NOT EXISTS `HTCC_Company` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `HTCC_Company`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: 167.179.80.90    Database: HTCC_Company
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `BuzConfig`
--

DROP TABLE IF EXISTS `BuzConfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BuzConfig` (
  `companyId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `section` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `value` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`section`,`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BuzConfig`
--

LOCK TABLES `BuzConfig` WRITE;
/*!40000 ALTER TABLE `BuzConfig` DISABLE KEYS */;
INSERT INTO `BuzConfig` VALUES ('HCMUS','DAY_OFF_INFO','allowCancelRequest','true','2020-04-15 10:06:05'),('HCMUS','DAY_OFF_INFO','categoryList','[{\"category\":\"Nghỉ phép năm\",\"useDayOff\":true,\"hasSalary\":true},{\"category\":\"Nghỉ bệnh\",\"useDayOff\":true,\"hasSalary\":false},{\"category\":\"Nghỉ thai sản\",\"useDayOff\":false,\"hasSalary\":true},{\"category\":\"Nghỉ phép không hưởng lương\",\"useDayOff\":true,\"hasSalary\":false},{\"category\":\"Nghỉ bù do làm thêm giờ\",\"useDayOff\":false,\"hasSalary\":true}]','2020-04-15 10:06:05'),('HCMUS','DAY_OFF_INFO','dayOffByLevel','[{\"level\":0.0,\"totalDayOff\":10.0},{\"level\":1.0,\"totalDayOff\":12.0},{\"level\":1.5,\"totalDayOff\":15.0},{\"level\":2.1,\"totalDayOff\":20.0}]','2020-04-19 03:28:29'),('HCMUS','DAY_OFF_INFO','maxDayAllowCancel','0','2020-04-15 10:06:05'),('VNG','DAY_OFF_INFO','allowCancelRequest','false','2020-04-15 14:37:56'),('VNG','DAY_OFF_INFO','categoryList','[{\"category\":\"Nghỉ phép năm\",\"useDayOff\":true,\"hasSalary\":true},{\"category\":\"Nghỉ bệnh\",\"useDayOff\":true,\"hasSalary\":false},{\"category\":\"Nghỉ phép không hưởng lương\",\"useDayOff\":true,\"hasSalary\":false},{\"category\":\"Nghỉ bù do làm thêm giờ\",\"useDayOff\":false,\"hasSalary\":true},{\"category\":\"Nghỉ thai sản\",\"useDayOff\":false,\"hasSalary\":true},{\"category\":\"aaa\",\"useDayOff\":false,\"hasSalary\":false}]','2020-04-21 15:54:32'),('VNG','DAY_OFF_INFO','dayOffByLevel','[{\"level\":11.0,\"totalDayOff\":1.0},{\"level\":2.1,\"totalDayOff\":20.0},{\"level\":1.5,\"totalDayOff\":15.0},{\"level\":1.0,\"totalDayOff\":12.0},{\"level\":0.0,\"totalDayOff\":10.0}]','2020-04-21 16:12:51'),('VNG','DAY_OFF_INFO','maxDayAllowCancel','1','2020-04-15 14:38:00');
/*!40000 ALTER TABLE `BuzConfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Company`
--

DROP TABLE IF EXISTS `Company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Company` (
  `companyId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `companyName` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `phoneNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `address` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `status` int NOT NULL DEFAULT '1',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Company`
--

LOCK TABLES `Company` WRITE;
/*!40000 ALTER TABLE `Company` DISABLE KEYS */;
INSERT INTO `Company` VALUES ('HCMUS','Trường Đại học Khoa Học Tự Nhiên','02862884499','bantin@hcmus.edu.vn','Trường Đại học Khoa Học Tự Nhiên',1,'2020-04-18 06:27:11'),('VNG','Công Ty Cổ Phần VNG','1900561558','campus@vng.com.vn','Lô 03b-04-05-06-07, đường số 13, khu Công Nghiệp, Khu Chế Xuất Tân Thuận, phường Tân Thuận Đông, Quận 7, TP.HCM.',1,'2020-04-12 09:07:17');
/*!40000 ALTER TABLE `Company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CompanyUser`
--

DROP TABLE IF EXISTS `CompanyUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CompanyUser` (
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(256) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(256) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `phoneNumber` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `role` int NOT NULL DEFAULT '0',
  `status` int NOT NULL DEFAULT '1',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CompanyUser`
--

LOCK TABLES `CompanyUser` WRITE;
/*!40000 ALTER TABLE `CompanyUser` DISABLE KEYS */;
INSERT INTO `CompanyUser` VALUES ('HCMUS','autth','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','haiau762@gmail.com','0342012299',0,1,'2020-04-11 12:03:29'),('HCMUS','datnq','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','nguyenquocdat2511998@gmail.com','0343244644',0,1,'2020-04-04 05:44:47'),('HCMUS','duydc','$2a$10$6klYF5wFHCbGKGBBkn2Ocu4FS3Thqt1HSi62b3irSLv5h0Ev4HeZG','dinhcongduy125@gmail.com','0335406888',0,1,'2020-04-04 05:44:47'),('HCMUS','duyna','$2a$10$MRgcb9OCyUGpfvD3yCTtreptfxeM3gklBkbQFiahfFV9CPViQsaVW','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-04 05:44:48'),('HCMUS','duytv','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','duytv2907@gmail.com','0931852460',0,1,'2020-04-04 05:44:48'),('HCMUS','hcmus','$2a$10$YMs9JcjDsWDwT.Cx.19R3Olp1FJ7ZZo9n0V/WDDDSvzd0/qzRsmr2','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-07 03:12:44'),('HCMUS','huylh','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','hoanghuyit98@gmail.com','0325236999',0,1,'2020-04-04 05:44:48'),('HCMUS','teacher','$2a$10$lF9Z6jZJ1FSxEmA7dqSQx.oBQi7Y2AQFDqfVlBB.aHHppbLOKXCP.','trphloc1@gmail.com','0333333333',0,0,'2020-04-16 06:56:32'),('VNG','admin','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','09482027091111',0,0,'2020-04-11 13:16:50'),('VNG','admin1','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin10','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin11','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin12','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin13','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin14','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin15','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin2','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin3','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin4','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin5','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin6','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin7','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin8','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','admin9','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','dat','$2a$10$H0ZLKqN.yvz.LXwKcLJeK.9nh.7RK69WArlnP.0Mjbs34lheElVR6','nguyenquocdat2511998@gmail.com','0948202709',0,1,'2020-04-11 13:16:49'),('VNG','duytv','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','duytv2907@gmail.com','0931852460',0,1,'2020-04-11 13:16:50'),('VNG','test','$2a$10$jecu94DCGVHQRLOgZMNNcekbDHYzFGRDVMg6MTi2eaEIgnv47CKyK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-11 13:16:50');
/*!40000 ALTER TABLE `CompanyUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Department`
--

DROP TABLE IF EXISTS `Department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Department` (
  `companyId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `department` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `departmentName` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `headManager` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`department`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Department`
--

LOCK TABLES `Department` WRITE;
/*!40000 ALTER TABLE `Department` DISABLE KEYS */;
INSERT INTO `Department` VALUES ('HCMUS','Backend','BackEnd - API','duyna','2020-04-16 14:19:46'),('HCMUS','Mobile','Mobile - Flutter','duytv','2020-04-16 14:19:46'),('HCMUS','React','FrontEnd - ReactJS','autth','2020-04-16 14:19:46'),('HCMUS','Vue','FrontEnd - VueJS','datnq','2020-04-16 14:19:46'),('VNG','PMA','Payment App - ZaloPay','admin','2020-04-16 14:21:19'),('VNG','ZA','Zalo','admin3','2020-04-16 14:21:19'),('VNG','ZPI','Payment App - ZaloPay Integration','admin3','2020-04-16 14:29:07');
/*!40000 ALTER TABLE `Department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmployeeInfo`
--

DROP TABLE IF EXISTS `EmployeeInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EmployeeInfo` (
  `companyId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `employeeId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `officeId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `department` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `title` varchar(128) NOT NULL DEFAULT '',
  `level` decimal(10,1) NOT NULL DEFAULT '0.0',
  `fullName` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `birthDate` date NOT NULL,
  `email` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `identityCardNo` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `phoneNumber` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `address` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `avatar` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`username`),
  UNIQUE KEY `employeeId_UNIQUE` (`employeeId`),
  KEY `IDX_employeeId` (`employeeId`),
  FULLTEXT KEY `employeeId` (`employeeId`,`fullName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeInfo`
--

LOCK TABLES `EmployeeInfo` WRITE;
/*!40000 ALTER TABLE `EmployeeInfo` DISABLE KEYS */;
INSERT INTO `EmployeeInfo` VALUES ('HCMUS','autth','HCMUS-00002','LT','React','Fresher Frontend Developer',2.1,'Trương Thị Hải Âu','1998-01-01','haiau762@gmail.com','123456789','0342012299','TPHCM','https://drive.google.com/uc?export=view&id=1qaP45dWOz3cTplv4Nz7hk69cHdUAFFTu','2020-04-21 10:40:54'),('HCMUS','datnq','HCMUS-00001','LT','Vue','Fresher Frontend Developer',2.1,'Nguyễn Quốc Đạt','1998-01-01','nguyenquocdat2511998@gmail.com','123456789','0343244644','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-21 10:40:54'),('HCMUS','duydc','HCMUS-00004','NVC','Mobile','Junior Mobile Developer',2.1,'Đinh Công duy','2000-07-12','dinhcongduy125@gmail.com','025728760','0335406888','HCM','https://drive.google.com/uc?export=view&id=1XRDa1pXRlHGFOb8MssrCYvqy6nrKZRIt','2020-04-21 10:40:55'),('HCMUS','duyna','HCMUS-00003','NVC','Backend','Team Leader',2.1,'NGUYỄN ANH DUY','1998-09-27','naduy.hcmus@gmail.com','272683901','0948202709','Q7, TPHCM','https://drive.google.com/uc?export=view&id=1wB4Ir5yH5wlJS1mtCfrFo2LvjeOvQFAC','2020-04-21 10:40:55'),('HCMUS','duytv','HCMUS-00005','NVC','Mobile','Junior Mobile Developer',2.1,'Võ Tấn Duy','1998-01-01','duytv2907@gmail.com','123456789','0931852460','Gò Vấp, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-21 10:40:55'),('HCMUS','huylh','HCMUS-00006','LT','Backend','Junior Backend Developer',2.1,'Lê Hoàng Huy','1998-01-01','hoanghuyit98@gmail.com','123456789','0325236999','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-21 10:40:55'),('VNG','admin','VNG-00001','CAMPUS','PMA','Team Leader',1.5,'Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','09482027091111','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','admin1','VNG-00002','CAMPUS','ZPI','Junior Mobile Developer',1.5,'Võ Tấn Duy','1998-07-29','Duytv.2907@gmail.com','123456789','0948202709','Gò Vấp, TPHCM','https://drive.google.com/uc?export=view&id=1aN0kY8wa-FIjJKkMV9N0ctJtrQTZktEn','2020-04-15 14:43:34'),('VNG','admin10','VNG-00012','CAMPUS','PMA','Team Leader',1.5,'Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0335406888','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','admin11','VNG-00013','CAMPUS','ZPI','Team Leader',1.5,'NGUYỄN ANH DUY','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','admin12','VNG-00014','FV','PMA','Team Leader',1.5,'Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0335406888','Quận 9, TPHCM','https://drive.google.com/uc?export=view&id=1wB4Ir5yH5wlJS1mtCfrFo2LvjeOvQFAC','2020-04-15 14:43:34'),('VNG','admin13','VNG-00015','CAMPUS','ZA','Team Leader',1.5,'Trương Thị Hải Âu','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','admin14','VNG-00016','CAMPUS','ZPI','Team Leader',1.5,'Trương Thị Hải Âu','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','admin15','VNG-00017','CAMPUS','PMA','Team Leader',1.5,'Lê Hoàng Huy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','admin2','VNG-00004','CAMPUS','PMA','Team Leader',1.5,'Lê Hoàng Huy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://drive.google.com/uc?export=view&id=1wB4Ir5yH5wlJS1mtCfrFo2LvjeOvQFAC','2020-04-15 14:43:34'),('VNG','admin3','VNG-00005','FV','ZA','Team Leader',1.5,'Nguyễn Quốc Đạt','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','admin4','VNG-00006','CAMPUS','PMA','Team Leader',1.5,'Nguyễn Quốc Đạt','2020-05-18','naduy.hcmus@gmail.com','025728760','0931852460','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','admin5','VNG-00007','CAMPUS','PMA','Team Leader',1.5,'Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://drive.google.com/uc?export=view&id=1wB4Ir5yH5wlJS1mtCfrFo2LvjeOvQFAC','2020-04-15 14:43:34'),('VNG','admin6','VNG-00008','CAMPUS','ZA','Team Leader',1.5,'Võ Tấn Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0931852460','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','admin7','VNG-00009','CAMPUS','PMA','Team Leader',1.5,'Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','admin8','VNG-00010','CAMPUS','PMA','Team Leader',1.5,'Võ Tấn Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0931852460','Quận 9, TPHCM','https://drive.google.com/uc?export=view&id=1wB4Ir5yH5wlJS1mtCfrFo2LvjeOvQFAC','2020-04-15 14:43:34'),('VNG','admin9','VNG-00011','FV','PMA','Team Leader',1.5,'Nguyễn Anh Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-15 14:43:34'),('VNG','duytv','VNG-00003','CAMPUS','ZPI','Junior Mobile Developer',1.5,'Võ Tấn Duy','1998-01-01','duytv2907@gmail.com','123456789','0931852460','Gò Vấp, TPHCM','https://drive.google.com/uc?export=view&id=1B73ONk5Mksssci8v2NMxL8MDU3jYc1NQ','2020-04-15 14:43:34');
/*!40000 ALTER TABLE `EmployeeInfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Office`
--

DROP TABLE IF EXISTS `Office`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Office` (
  `companyId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `officeId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `officeName` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `address` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `latitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `longitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `phoneNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `forceUseWifi` tinyint(1) NOT NULL DEFAULT '0',
  `allowWifiIP` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `isHeadquarter` tinyint(1) NOT NULL DEFAULT '0',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`officeId`),
  KEY `companyId_IND` (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Office`
--

LOCK TABLES `Office` WRITE;
/*!40000 ALTER TABLE `Office` DISABLE KEYS */;
INSERT INTO `Office` VALUES ('HCMUS','LT','Linh Trung','Đường số 5A, Q. Thủ Đức, Tp. Hồ Chí Minh',10.875701,106.799117,50,'02838962823','bantin@hcmus.edu.vn',0,'',0,'2020-04-07 14:42:43'),('HCMUS','NVC','Nguyễn Văn Cừ','227 Nguyễn Văn Cừ, phường 4, Q5, Tp. Hồ Chí Minh',10.762462,106.682755,100000,'02862884499','bantin@hcmus.edu.vn',0,'',1,'2020-04-28 11:30:10'),('HCMUS','VNGHN','VNG Ha Noi','asd asd1',16.064947,108.201640,10,'0123456789','vng@vng.com.vn',0,'',0,'2020-04-19 04:45:19'),('VNG','CAMPUS','Trụ sở Campus','Lô 03b-04-05-06-07, đường số 13, khu Công Nghiệp, Khu Chế Xuất Tân Thuận, phường Tân Thuận Đông, Quận 7, TP.HCM.',10.758033,106.746140,20,'1900561558','campus@vng.com.vn',0,'',1,'2020-04-14 14:27:47'),('VNG','HCM','VĂN PHÒNG HCM','Tầng 13, 14, Toà nhà Sài Gòn Giải Phóng, 432 Nguyễn Thị Minh Khai, Phường 5, Quận 3, TP.HCM',10.769619,106.685524,10,'1900561558','hcmhcm@vng.com.vn',0,'',0,'2020-04-12 14:42:00');
/*!40000 ALTER TABLE `Office` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShiftTime`
--

DROP TABLE IF EXISTS `ShiftTime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShiftTime` (
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `officeId` varchar(45) COLLATE utf8mb4_general_ci NOT NULL,
  `shiftId` int NOT NULL,
  `startTime` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '00:00',
  `endTime` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '00:00',
  `allowLateMinutes` int NOT NULL DEFAULT '0',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`officeId`,`shiftId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShiftTime`
--

LOCK TABLES `ShiftTime` WRITE;
/*!40000 ALTER TABLE `ShiftTime` DISABLE KEYS */;
INSERT INTO `ShiftTime` VALUES ('HCMUS','LT',1,'06:30','17:30',10,'2020-04-24 14:38:41'),('HCMUS','LT',2,'06:30','12:00',10,'2020-04-24 14:38:41'),('HCMUS','LT',3,'12:30','17:30',10,'2020-04-24 14:38:41'),('HCMUS','NVC',1,'06:30','17:30',10,'2020-04-24 14:38:50'),('HCMUS','NVC',2,'06:30','12:00',10,'2020-04-24 14:38:50'),('HCMUS','NVC',3,'12:30','17:30',10,'2020-04-24 14:38:50'),('VNG','CAMPUS',1,'08:30','17:30',30,'2020-04-24 14:36:45'),('VNG','CAMPUS',2,'08:30','12:00',5,'2020-04-29 00:57:39'),('VNG','CAMPUS',3,'07:00','11:30',10,'2020-04-29 00:59:44'),('VNG','CAMPUS',4,'08:00','17:00',10,'2020-04-29 01:00:14'),('VNG','CAMPUS',5,'07:05','12:00',5,'2020-04-29 00:33:06'),('VNG','HCM',1,'08:30','17:30',30,'2020-04-28 16:15:37'),('VNG','HCM',2,'08:30','12:00',10,'2020-04-28 16:15:37'),('VNG','HCM',3,'13:30','17:30',10,'2020-04-28 16:15:37');
/*!40000 ALTER TABLE `ShiftTime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WorkingDay`
--

DROP TABLE IF EXISTS `WorkingDay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WorkingDay` (
  `id` int NOT NULL AUTO_INCREMENT,
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `officeId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(45) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1',
  `weekDay` int NOT NULL,
  `date` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `session` int NOT NULL DEFAULT '0',
  `isWorking` tinyint(1) NOT NULL DEFAULT '1',
  `extraInfo` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WorkingDay`
--

LOCK TABLES `WorkingDay` WRITE;
/*!40000 ALTER TABLE `WorkingDay` DISABLE KEYS */;
INSERT INTO `WorkingDay` VALUES (10,'VNG','CAMPUS','1',1,'',0,0,'','2020-04-24 14:32:32'),(11,'VNG','CAMPUS','1',2,'',0,1,'','2020-04-24 14:32:32'),(12,'VNG','CAMPUS','1',3,'',0,1,'','2020-04-24 14:32:32'),(13,'VNG','CAMPUS','1',4,'',0,1,'','2020-04-24 14:32:32'),(14,'VNG','CAMPUS','1',5,'',0,1,'','2020-04-24 14:32:32'),(15,'VNG','CAMPUS','1',6,'',0,1,'','2020-04-24 14:32:33'),(16,'VNG','CAMPUS','1',7,'',0,0,'','2020-04-24 14:32:33'),(17,'VNG','CAMPUS','2',0,'20200430',0,0,'Nghỉ lễ 30/4','2020-04-24 14:32:34'),(18,'VNG','CAMPUS','2',0,'20200501',0,0,'Nghỉ lễ 1/5','2020-04-24 14:32:34'),(19,'VNG','HCM','1',1,'',0,0,'','2020-04-24 14:33:18'),(20,'VNG','HCM','1',2,'',0,1,'','2020-04-24 14:33:18'),(21,'VNG','HCM','1',3,'',0,1,'','2020-04-24 14:33:18'),(22,'VNG','HCM','1',4,'',0,1,'','2020-04-24 14:33:19'),(23,'VNG','HCM','1',5,'',0,1,'','2020-04-24 14:33:19'),(24,'VNG','HCM','1',6,'',0,1,'','2020-04-24 14:33:19'),(25,'VNG','HCM','1',7,'',0,0,'','2020-04-24 14:33:19'),(26,'VNG','HCM','2',0,'20200430',0,0,'Nghỉ lễ 30/4','2020-04-24 14:33:19'),(27,'VNG','HCM','2',0,'20200501',0,0,'Nghỉ lễ 1/5','2020-04-24 14:33:19'),(28,'HCMUS','LT','1',1,'',0,0,'','2020-04-24 14:34:52'),(29,'HCMUS','LT','1',2,'',0,1,'','2020-04-24 14:34:52'),(30,'HCMUS','LT','1',3,'',0,1,'','2020-04-24 14:34:52'),(31,'HCMUS','LT','1',4,'',0,1,'','2020-04-24 14:34:53'),(32,'HCMUS','LT','1',5,'',0,1,'','2020-04-24 14:34:53'),(33,'HCMUS','LT','1',6,'',0,1,'','2020-04-24 14:34:53'),(34,'HCMUS','LT','1',7,'',1,1,'','2020-04-24 14:34:53'),(35,'HCMUS','LT','2',0,'20200430',0,0,'Nghỉ lễ 30/4','2020-04-24 14:34:53'),(36,'HCMUS','LT','2',0,'20200501',0,0,'Nghỉ lễ 1/5','2020-04-24 14:34:53'),(37,'HCMUS','NVC','1',1,'',0,0,'','2020-04-24 14:35:02'),(38,'HCMUS','NVC','1',2,'',0,1,'','2020-04-24 14:35:02'),(39,'HCMUS','NVC','1',3,'',0,1,'','2020-04-24 14:35:02'),(40,'HCMUS','NVC','1',4,'',0,1,'','2020-04-24 14:35:02'),(41,'HCMUS','NVC','1',5,'',0,1,'','2020-04-24 14:35:02'),(42,'HCMUS','NVC','1',6,'',0,1,'','2020-04-24 14:35:03'),(43,'HCMUS','NVC','1',7,'',1,1,'','2020-04-24 14:35:03'),(44,'HCMUS','NVC','2',0,'20200430',0,0,'Nghỉ lễ 30/4','2020-04-24 14:35:03'),(45,'HCMUS','NVC','2',0,'20200501',0,0,'Nghỉ lễ 1/5','2020-04-24 14:35:03'),(46,'HCMUS','NVC','2',0,'20200506',1,0,'Nghỉ','2020-04-25 04:59:54'),(47,'HCMUS','VNGHN','1',1,'',0,0,'','2020-04-28 11:35:06'),(48,'HCMUS','VNGHN','1',2,'',0,1,'','2020-04-28 11:35:06'),(49,'HCMUS','VNGHN','1',3,'',0,1,'','2020-04-28 11:35:06'),(50,'HCMUS','VNGHN','1',4,'',0,1,'','2020-04-28 11:35:06'),(51,'HCMUS','VNGHN','1',5,'',0,1,'','2020-04-28 11:35:06'),(52,'HCMUS','VNGHN','1',6,'',0,1,'','2020-04-28 11:35:06'),(53,'HCMUS','VNGHN','1',7,'',1,1,'','2020-04-28 11:35:06'),(54,'HCMUS','VNGHN','2',0,'20200430',0,0,'Nghỉ lễ 30/4','2020-04-28 11:35:07'),(55,'HCMUS','VNGHN','2',0,'20200501',0,0,'Nghỉ lễ 1/5','2020-04-28 11:35:07'),(56,'HCMUS','VNGHN','2',0,'20200506',1,0,'Nghỉ','2020-04-28 11:35:07'),(57,'HCMUS','VNGHN','2',0,'20200424',0,0,'','2020-04-28 11:35:46'),(58,'HCMUS','VNGHN','2',0,'20200428',0,0,'','2020-04-28 11:35:59'),(59,'HCMUS','VNGHN','2',0,'20200429',0,0,'','2020-04-28 11:36:03');
/*!40000 ALTER TABLE `WorkingDay` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-29 12:51:20
CREATE DATABASE  IF NOT EXISTS `HTCC_Admin` /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `HTCC_Admin`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: 167.179.80.90    Database: HTCC_Admin
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `AdminUser`
--

DROP TABLE IF EXISTS `AdminUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AdminUser` (
  `username` varchar(32) NOT NULL,
  `password` varchar(256) NOT NULL DEFAULT '',
  `fullName` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phoneNumber` varchar(20) NOT NULL DEFAULT '',
  `email` varchar(128) NOT NULL DEFAULT '',
  `avatar` varchar(256) NOT NULL DEFAULT 'https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',
  `role` int NOT NULL DEFAULT '1',
  `status` int NOT NULL DEFAULT '1',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AdminUser`
--

LOCK TABLES `AdminUser` WRITE;
/*!40000 ALTER TABLE `AdminUser` DISABLE KEYS */;
INSERT INTO `AdminUser` VALUES ('aaaaaaa','$2a$10$Q0k4WvDnd/0fieeGaDG6u./N6kX2QVAKwcPxk.XhXCsniuWYbl46q','aaa','0333333333333333','aaaa@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-03-26 14:48:09'),('admin','$2a$10$JjuoahY1k4mhsk87lEWzN.FUi8I1lsDaZ0HoTxYaolZ7/ZhMcQTby','Nguyễn Anh Duy 11111','0948202701','naduy.hcmus@gmail.com','https://drive.google.com/uc?export=view&id=1hrhFAOqV2jiFfc38UF6-okd2LZ2yoPqZ',0,1,'2020-04-09 14:11:05'),('admin1','$2a$10$9OxClkoaV9bbNLc1g3MXC.3R5FyLnhdSYSsejQ6YPASk5UcRjGfD2','Nguyễn Quốc Đạt 123 456 1 111','0912345678','admin@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-03-26 14:59:50'),('dat','$2a$10$wLiomueoC9RxqSE8IfPFiOdLTTfI6hBKPENVv5obi4cncR0OtOmqm','a','033333333333','aaa@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-04-07 08:43:34'),('duyna','$2a$10$jAkp0g.MDz3TdmDgBJTj..VNpfCrNUHxRGY5ZN9ljRFEv9MOnHs/a','NGUYEN ANH DUY','0948202709','naduy.hcmus@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-04-06 15:28:10');
/*!40000 ALTER TABLE `AdminUser` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-29 12:51:22
