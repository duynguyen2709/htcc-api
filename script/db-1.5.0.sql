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
  `requestId` varchar(8) NOT NULL DEFAULT '',
  `serviceId` int NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `returnCode` int NOT NULL DEFAULT '1',
  `requestTime` bigint NOT NULL DEFAULT '0',
  `responseTime` bigint NOT NULL DEFAULT '0',
  `userIP` varchar(64) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB AUTO_INCREMENT=4816 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202004`
--

LOCK TABLES `ApiLog202004` WRITE;
/*!40000 ALTER TABLE `ApiLog202004` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckInLog202003`
--

DROP TABLE IF EXISTS `CheckInLog202003`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202003` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) NOT NULL DEFAULT '',
  `companyId` varchar(32) NOT NULL DEFAULT '',
  `username` varchar(32) NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `serverTime` bigint NOT NULL DEFAULT '0',
  `validTime` varchar(8) NOT NULL DEFAULT '00:00',
  `isOnTime` tinyint(1) NOT NULL DEFAULT '1',
  `validLatitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `validLongitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `latitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `longitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(64) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202003`
--

LOCK TABLES `CheckInLog202003` WRITE;
/*!40000 ALTER TABLE `CheckInLog202003` DISABLE KEYS */;
INSERT INTO `CheckInLog202003` VALUES (1,20200326,'f69ceb0','VNG','duytv',1585231911326,1585231913797,'08:30',0,10.762462,106.682755,10.839979,106.659355,10,0,NULL,'2020-03-26 14:11:56'),(2,20200327,'1226857','VNG','duytv',1585273301647,1585273304252,'08:30',0,10.762462,106.682755,10.839954,106.659317,10,0,NULL,'2020-03-27 01:41:44'),(3,20200327,'4f0a51d','HCMUS','duyna',1585316622168,1585316626140,'08:30',0,10.762462,106.682755,10.962877,106.838562,10,0,NULL,'2020-03-27 13:43:50'),(4,20200328,'86f966c','HCMUS','duyna',1585364324032,1585364326024,'08:30',0,10.762462,106.682755,10.962917,106.838463,10,0,NULL,'2020-03-28 02:58:46'),(5,20200329,'46c5822','HCMUS','duyna',1585446034259,1585446036958,'08:30',0,10.762462,106.682755,10.959263,106.846855,10,0,NULL,'2020-03-29 01:40:37'),(6,20200330,'4c70b4a','VNG','duytv',1585564507668,1585564514925,'08:30',0,10.762462,106.682755,10.839926,106.659271,10,0,'','2020-03-30 10:35:15'),(7,20200331,'b733d7a','HCMUS','duyna',1585663022698,1585663026798,'08:30',0,10.762462,106.682755,10.961472,106.836281,10,0,NULL,'2020-03-31 13:57:07');
/*!40000 ALTER TABLE `CheckInLog202003` ENABLE KEYS */;
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
  `requestId` varchar(8) NOT NULL DEFAULT '',
  `companyId` varchar(32) NOT NULL DEFAULT '',
  `username` varchar(32) NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `serverTime` bigint NOT NULL DEFAULT '0',
  `validTime` varchar(8) NOT NULL DEFAULT '00:00',
  `isOnTime` tinyint(1) NOT NULL DEFAULT '1',
  `validLatitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `validLongitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `latitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `longitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(64) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202004`
--

LOCK TABLES `CheckInLog202004` WRITE;
/*!40000 ALTER TABLE `CheckInLog202004` DISABLE KEYS */;
INSERT INTO `CheckInLog202004` VALUES (1,20200405,'123b11d','VNG','duytv',1586088900141,1586088908150,'08:30',0,10.762462,106.682755,10.811074,106.699951,10,1,'192.168.1.107','2020-04-05 12:15:09'),(2,20200405,'1584bf2','HCMUS','duydc',1586088946702,1586088950962,'08:30',0,10.762462,106.682755,10.811069,106.699936,10,1,'192.168.1.107','2020-04-05 12:15:51'),(3,20200407,'63edbf8','VNG','admin1',1586267295579,1586267299480,'08:30',0,10.762462,106.682755,10.962917,106.838554,10,0,'','2020-04-07 13:48:20');
/*!40000 ALTER TABLE `CheckInLog202004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckOutLog202003`
--

DROP TABLE IF EXISTS `CheckOutLog202003`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202003` (
  `logId` int NOT NULL AUTO_INCREMENT,
  `ymd` bigint NOT NULL DEFAULT '0',
  `requestId` varchar(8) NOT NULL DEFAULT '',
  `companyId` varchar(32) NOT NULL DEFAULT '',
  `username` varchar(32) NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `serverTime` bigint NOT NULL DEFAULT '0',
  `validTime` varchar(8) NOT NULL DEFAULT '00:00',
  `isOnTime` tinyint(1) NOT NULL DEFAULT '1',
  `validLatitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `validLongitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `latitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `longitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(64) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202003`
--

LOCK TABLES `CheckOutLog202003` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202003` DISABLE KEYS */;
INSERT INTO `CheckOutLog202003` VALUES (1,20200326,'b1ecf38','HCMUS','duyna',1585234096989,1585234097737,'17:30',1,10.762462,106.682755,10.720408,106.700951,10,0,NULL,'2020-03-26 14:48:18'),(2,20200327,'d3f80ec','HCMUS','duyna',1585322687987,1585322689663,'17:30',1,10.762462,106.682755,10.962036,106.839775,10,0,NULL,'2020-03-27 15:24:50'),(3,20200328,'20acbcf','HCMUS','duyna',1585367930166,1585367932906,'17:30',0,10.762462,106.682755,10.962858,106.838470,10,0,NULL,'2020-03-28 03:58:53'),(4,20200330,'3a63e3f','VNG','duytv',1585564651243,1585564654038,'17:30',1,10.762462,106.682755,10.840089,106.659325,10,1,'192.168.1.249','2020-03-30 10:37:34'),(5,20200331,'8c7e5d6','HCMUS','duyna',1585663032591,1585663035672,'17:30',1,10.762462,106.682755,10.961472,106.836281,10,0,NULL,'2020-03-31 13:57:16');
/*!40000 ALTER TABLE `CheckOutLog202003` ENABLE KEYS */;
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
  `requestId` varchar(8) NOT NULL DEFAULT '',
  `companyId` varchar(32) NOT NULL DEFAULT '',
  `username` varchar(32) NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `serverTime` bigint NOT NULL DEFAULT '0',
  `validTime` varchar(8) NOT NULL DEFAULT '00:00',
  `isOnTime` tinyint(1) NOT NULL DEFAULT '1',
  `validLatitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `validLongitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `latitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `longitude` decimal(10,6) NOT NULL DEFAULT '0.000000',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(64) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202004`
--

LOCK TABLES `CheckOutLog202004` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202004` DISABLE KEYS */;
INSERT INTO `CheckOutLog202004` VALUES (1,20200405,'aa921f4','VNG','duytv',1586088908774,1586088911163,'17:30',1,10.762462,106.682755,10.811069,106.699959,10,1,'192.168.1.107','2020-04-05 12:15:12');
/*!40000 ALTER TABLE `CheckOutLog202004` ENABLE KEYS */;
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
  `requestId` varchar(8) NOT NULL DEFAULT '',
  `complaintId` varchar(32) NOT NULL DEFAULT '',
  `companyId` varchar(32) NOT NULL DEFAULT '',
  `username` varchar(32) NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `receiverType` int NOT NULL DEFAULT '2',
  `isAnonymous` int NOT NULL DEFAULT '0',
  `category` varchar(256) NOT NULL DEFAULT '',
  `content` varchar(8192) NOT NULL DEFAULT '',
  `images` varchar(2048) NOT NULL DEFAULT '[]',
  `response` varchar(4096) DEFAULT '',
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
INSERT INTO `ComplaintLog202003` VALUES (15,20200326,'3e58054','#VNG-3e58054','VNG','duytv',1585210791055,1,0,'Transport','test','[]','123',1,'2020-03-31 09:36:26'),(16,20200326,'96bbe36','#VNG-96bbe36','VNG','duytv',1585212038686,2,0,'Transport','test','[]','Từ chối xử lý khiếu nại',0,'2020-03-26 13:28:16'),(17,20200326,'16b72ca','#VNG-16b72ca','VNG','duytv',1585212561772,1,0,'Transport','test','[\"https://drive.google.com/uc?export=view&id=1VhLEl-BKU2TAmMgKGqB0J8EL-38MfFjh\"]','123',1,'2020-03-31 09:37:11'),(18,20200326,'09bda25','#VNG-09bda25','VNG','duytv',1585213693416,2,0,'Transport','abc','[\"https://drive.google.com/uc?export=view&id=14HjTZzBIwuQbjsnSrFewakPpOI5tQ2yA\",\"https://drive.google.com/uc?export=view&id=1U_dMZWCYJ6dVtXVYT4LTqyUZAnwz6glZ\"]','Đã xử lý khiếu nại',1,'2020-03-26 13:27:33'),(19,20200326,'e982b20','#VNG-e982b20','VNG','duytv',1585213742776,1,0,'Transport','abc','[\"https://drive.google.com/uc?export=view&id=1Eg3whx9PJyMcUEY6_uLrjv_GQ7F0RT93\"]','123',1,'2020-03-31 09:39:11'),(20,20200326,'050c461','#VNG-050c461','VNG','duytv',1585214317413,2,0,'Shopping','testing ','[\"https://drive.google.com/uc?export=view&id=1JdbcUgLfHAq9zjn5PYn4wD5IZprncNAp\"]','Đã xử lý',1,'2020-04-01 05:01:54'),(21,20200326,'aa75a49','#VNG-aa75a49','VNG','duytv',1585217865234,1,0,'Transport','bxnzksmsmjsjsnsnsnnsnsnsnznznxnxnxnxnxnxnnxnxnxnxnxnznxnxnxnnxnxxbdnsnznxnxjxjxnxjjxjxjxjxjxnxnxmxnxmmzjxnxjxjxjxjxjjxjxjxjxjzjjzxjzjxjxjjzjzjxjjxjxjx','[]','123',1,'2020-03-31 09:58:21'),(22,20200326,'30e838c','#VNG-30e838c','VNG','duytv',1585231620779,1,0,'Movie','test again','[\"https://drive.google.com/uc?export=view&id=1DQE3XEu4kG9n2issC5pPgxp80rsI8ixy\"]','123',1,'2020-03-31 09:59:33'),(23,20200326,'28a74c5','#HCMUS-28a74c5','HCMUS','duyna',1585234162711,2,0,'Salary','test','[\"https://drive.google.com/uc?export=view&id=1Nw1Gc2GW0Sld3hxLHK7E3ExvVuz9pKhs\",\"https://drive.google.com/uc?export=view&id=10DuzxnRtL4n2Ud8igp8WXqWcHuMWI665\"]','Đã xử lý khiếu nại',1,'2020-03-26 15:07:55'),(24,20200326,'517bd94','#HCMUS-517bd94','HCMUS','duyna',1585234433580,2,0,'Salary','ttttdjdjdnndnndejjekdkdkkdkdkd djjdjdjdjjdjd jejejeme djekekr\ndjkrjeje \neieieiie','[\"https://drive.google.com/uc?export=view&id=1qw7-fejztwdlnISudMfflgvmLZp4L3tG\"]','Từ chối xử lý khiếu nại',0,'2020-03-26 15:07:55'),(25,20200326,'f4e0ce4','#HCMUS-f4e0ce4','HCMUS','duyna',1585234522810,1,0,'Transport','','[]','123',1,'2020-03-31 10:04:29'),(26,20200326,'d4e1ff6','#HCMUS-d4e1ff6','HCMUS','',1585234810660,2,1,'Transport','test 2','[\"https://drive.google.com/uc?export=view&id=1DL8UoliwXgA8Z9JjxsfJheDVUeybNMaf\"]','',2,'2020-03-26 15:00:19'),(29,20200327,'92fb2a9','#VNG-92fb2a9','VNG','duytv',1585273344113,1,0,'Transport','test 1','[]','123',1,'2020-03-31 10:42:45'),(30,20200327,'45ba10f','#VNG-45ba10f','VNG','duytv',1585273457940,1,0,'Transport','hạ caa djjd','[]','123',0,'2020-03-31 10:43:36'),(31,20200327,'8473c6e','#VNG-8473c6e','VNG','duytv',1585273843235,1,0,'Transport','aghb','[]','1',1,'2020-04-02 02:50:50'),(32,20200327,'01076de','#VNG-01076de','VNG','duytv',1585274004782,1,0,'Transport','1','[]','ok',1,'2020-03-31 12:03:42'),(33,20200327,'04371a9','#VNG-04371a9','VNG','duytv',1585274774820,1,0,'Transport','2','[]','',1,'2020-04-02 10:41:49'),(34,20200327,'26faa21','#VNG-26faa21','VNG','duytv',1585275234771,1,0,'Transport','3','[]','',2,'2020-03-27 02:14:02'),(35,20200327,'d4ceb4f','#VNG-d4ceb4f','VNG','duytv',1585276358805,1,0,'Transport','4','[]','',2,'2020-03-27 02:32:40'),(36,20200327,'346f522','#VNG-346f522','VNG','duytv',1585276566459,1,0,'Transport','5','[]','',2,'2020-03-27 02:36:08'),(37,20200327,'29e4cc7','#VNG-29e4cc7','VNG','duytv',1585276685191,1,0,'Transport','6','[]','',2,'2020-03-27 02:38:07'),(38,20200327,'536a460','#VNG-536a460','VNG','duytv',1585276814359,1,0,'Transport','7','[]','',2,'2020-03-27 02:40:16'),(39,20200327,'3609465','#VNG-3609465','VNG','duytv',1585277024227,1,0,'Transport','8','[]','',2,'2020-03-27 02:43:46'),(40,20200327,'0a03aa9','#VNG-0a03aa9','VNG','duytv',1585277042576,1,0,'Transport','9','[]','',2,'2020-03-27 02:44:04'),(41,20200327,'a4a5a51','#VNG-a4a5a51','VNG','duytv',1585316835240,1,0,'Transport','test image view','[\"https://drive.google.com/uc?export=view&id=1euiuh5RfsXBsP_aDbydNaZd96Cgtqds8\",\"https://drive.google.com/uc?export=view&id=141QHnCgAETKwJLpC2P5Wg1XUB_hnH1e-\"]','',2,'2020-03-27 13:47:24'),(42,20200327,'8f17d4b','#VNG-8f17d4b','VNG','duytv',1585319398747,1,0,'Transport','abc','[\"https://drive.google.com/uc?export=view&id=1eQNMm_f4jPaRKUdiFbjM9dJFYxjv2q_0\",\"https://drive.google.com/uc?export=view&id=1sxdKAoh36cPC_oVPd4YpMQsl8jaZa5nN\",\"https://drive.google.com/uc?export=view&id=1qejTU5SCcKglXPiea-0AvN8muQb3HGPu\"]','',2,'2020-03-27 14:30:07'),(43,20200327,'3122f14','#VNG-3122f14','VNG','duytv',1585319631109,1,0,'Transport','test','[]','',2,'2020-03-27 14:33:53'),(44,20200327,'169eff6','#VNG-169eff6','VNG','duytv',1585319771147,1,0,'Transport','asdf','[]','',2,'2020-03-27 14:36:32'),(45,20200327,'8b38223','#VNG-8b38223','VNG','duytv',1585319930415,1,0,'Transport','aa','[]','',2,'2020-03-27 14:38:53'),(46,20200327,'d0e84ca','#VNG-d0e84ca','VNG','duytv',1585320034126,1,0,'Transport','d','[]','',2,'2020-03-27 14:40:36'),(51,20200327,'f906ddd','#HCMUS-f906ddd','HCMUS','duyna',1585324767057,2,0,'Personal','y2','[\"https://drive.google.com/uc?export=view&id=1kTrWbSOcQebMn8VUHB36-ubzf-rp3KJ-\",\"https://drive.google.com/uc?export=view&id=1f4D0lH7WjdbGS-LF3Ht7oOhxw8XVWlxM\"]','',2,'2020-03-27 15:59:41'),(52,20200327,'7ce9069','#HCMUS-7ce9069','HCMUS','duyna',1585325509848,2,0,'Shopping','final','[\"https://drive.google.com/uc?export=view&id=1hTmDbHSMO-EjiIVpiONDAOqheO2UyfAi\",\"https://drive.google.com/uc?export=view&id=1-wf9Xs4BrRXNYqfHLJXJkNhh3rXArFuI\"]','',2,'2020-03-27 16:12:14'),(53,20200328,'d6f0b49','#HCMUS-d6f0b49','HCMUS','duyna',1585370705772,1,0,'Transport','ios','[\"https://drive.google.com/uc?export=view&id=1m8J8iqCjC0c7n5PzWGHZvy6Z38jte_sz\"]','',2,'2020-03-28 04:45:11'),(54,20200328,'2a12885','#HCMUS-2a12885','HCMUS','duyna',1585392877579,2,0,'Food','h','[\"https://drive.google.com/uc?export=view&id=1j48S6GlhrYHVdgkdPbn1UHDxUc0aspVq\"]','',2,'2020-03-28 10:54:44'),(55,20200328,'767bc3e','#HCMUS-767bc3e','HCMUS','duyna',1585396031939,1,0,'Transport','large image','[\"https://drive.google.com/uc?export=view&id=1YiFWvouyoR_5lzCXrWft_0Pa2A7fGLSO\"]','',2,'2020-03-28 11:47:17'),(56,20200328,'1a7344c','#HCMUS-1a7344c','HCMUS','duyna',1585397025560,2,0,'Transport','nginximagesize','[\"https://drive.google.com/uc?export=view&id=1o5aaAue8mSfa4fmBM2-hwQYHhcwDp-VK\",\"https://drive.google.com/uc?export=view&id=1L2hzGjXpOnVkVIfjsWDGufP1i4S7rBYs\"]','',2,'2020-03-28 12:03:51'),(57,20200328,'b7eee54','#HCMUS-b7eee54','HCMUS','duyna',1585397417362,1,0,'Transport','2 large','[\"https://drive.google.com/uc?export=view&id=1lcSTCCnI8WwKgg57LaDwHlCvaAp95V-0\",\"https://drive.google.com/uc?export=view&id=1cP87TxafIhHCR5tp48iOzlfZ6FS4Df08\"]','123',1,'2020-04-02 02:47:15'),(58,20200328,'f0addc3','#VNG-f0addc3','VNG','duytv',1585399912755,1,0,'Transport','t','[\"https://drive.google.com/uc?export=view&id=1F9wfaD-q7t34ZdbkglAPb5CFl-yHvT_b\"]','',2,'2020-03-28 12:52:01'),(59,20200328,'85e991f','#HCMUS-85e991f','HCMUS','duyna',1585407143973,1,0,'Transport','u','[\"https://drive.google.com/uc?export=view&id=1yRNAiyNJ_spT5eCIRJyvAqp1ANpIhzz_\"]','',2,'2020-03-28 14:52:34'),(60,20200328,'0801c03','#HCMUS-0801c03','HCMUS','duyna',1585407689876,1,0,'Transport','iwiw','[\"https://drive.google.com/uc?export=view&id=12Y9v5-CVQU2HLQdw4I908_3CB65WgY7j\",\"https://drive.google.com/uc?export=view&id=1n6hLmtUs_LjjtoVuQ7k4g0djpslpHkBq\",\"https://drive.google.com/uc?export=view&id=1zAgVf5Kjd8kgiHV29D4tsE_Ler3UvX7s\"]','test tool',0,'2020-03-31 12:06:40'),(61,20200331,'4837873','#-4837873','','',1585656306177,1,1,'Transport','n\n','[\"https://drive.google.com/uc?export=view&id=1ztY-IYCKX77BchRhF9-Xc-h4AAGuEoGs\"]','',2,'2020-03-31 12:05:14'),(62,20200331,'1234576','#VNG-1234576','VNG','admin1',1585656306177,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','test reject',0,'2020-04-07 12:40:26'),(63,20200331,'1234577','#VNG-1234577','VNG','admin1',1585656306177,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(64,20200331,'1234578','#VNG-1234578','VNG','admin1',1585656306177,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(65,20200331,'1234579','#VNG-1234579','VNG','admin1',1585656306177,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','Đã xử lý oke =,=',1,'2020-04-04 10:32:01'),(66,20200331,'1234580','#VNG-1234580','VNG','admin1',1585656306177,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(67,20200331,'1234581','#VNG-1234581','VNG','admin1',1585656306177,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(68,20200331,'1234582','#VNG-1234582','VNG','admin1',1585656306177,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(69,20200331,'1234583','#VNG-1234583','VNG','admin1',1585656306177,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(70,20200331,'1234584','#VNG-1234584','VNG','admin1',1585656306177,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(81,20200331,'1234585','#VNG-1234585','VNG','admin1',1585656306177,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22');
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
  `requestId` varchar(8) NOT NULL DEFAULT '',
  `complaintId` varchar(32) NOT NULL DEFAULT '',
  `companyId` varchar(32) NOT NULL DEFAULT '',
  `username` varchar(32) NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `receiverType` int NOT NULL DEFAULT '2',
  `isAnonymous` int NOT NULL DEFAULT '0',
  `category` varchar(256) NOT NULL DEFAULT '',
  `content` varchar(8192) NOT NULL DEFAULT '',
  `images` varchar(2048) NOT NULL DEFAULT '[]',
  `response` varchar(4096) DEFAULT '',
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ComplaintLog202004`
--

LOCK TABLES `ComplaintLog202004` WRITE;
/*!40000 ALTER TABLE `ComplaintLog202004` DISABLE KEYS */;
INSERT INTO `ComplaintLog202004` VALUES (1,20200407,'076cfeb','#HCMUS-076cfeb','HCMUS','duyna',1586265215300,2,0,'Salary','tết image','[\"https://drive.google.com/uc?export=view&id=1V6oyNBh3YL-JylyUfx-B_gB97kYxDvya\",\"https://drive.google.com/uc?export=view&id=10VB3pTujq0poKIkF0kWxq5R50j0tCI1L\",\"https://drive.google.com/uc?export=view&id=1SdDcjROMtUG3E3mpfCGaB2ZqYamVW9m5\"]','asd',1,'2020-03-26 15:07:55'),(2,20200407,'85ccd6b','#HCMUS-85ccd6b','HCMUS','duyna',1586265215406,2,0,'Personal','qqqq','[\"https://drive.google.com/uc?export=view&id=1NII07_CozrihxU7eSUBnPTskkSP3Znb8\",\"https://drive.google.com/uc?export=view&id=1rfxzK1NKgbBGVDXSqbJsAqK0M5HJSrec\",\"https://drive.google.com/uc?export=view&id=16pq-mj5nN_Xk00XJLxO-X9zhDFr8eObZ\"]','',2,'2020-03-26 15:07:55'),(3,20200401,'96d0505','#VNG-96d0505','VNG','admin1',1585717644953,2,0,'Food','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','ssss',0,'2020-04-04 10:24:57'),(4,20200401,'36318fb','#VNG-36318fb','VNG','admin1',1585717696236,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','Đã xử lý',1,'2020-04-04 10:27:36'),(5,20200401,'1234567','#VNG-1234567','VNG','admin1',1585717696236,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','test udpate',1,'2020-04-07 01:00:30'),(6,20200401,'1234568','#VNG-1234568','VNG','admin1',1585717696236,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','test noti',1,'2020-04-07 14:30:33'),(7,20200401,'1234569','#VNG-1234569','VNG','admin1',1585717696236,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]',' noti again',0,'2020-04-07 14:31:35'),(8,20200401,'1234570','#VNG-1234570','VNG','admin1',1585717696236,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','2',1,'2020-04-07 14:39:26'),(9,20200401,'1234571','#VNG-1234571','VNG','admin1',1585717696236,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','aaaaa',1,'2020-04-07 14:42:45'),(10,20200401,'1234572','#VNG-1234572','VNG','admin1',1585717696236,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(11,20200401,'1234573','#VNG-1234573','VNG','admin1',1585717696236,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(12,20200401,'1234574','#VNG-1234574','VNG','admin1',1585717696236,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(13,20200401,'1234575','#VNG-1234575','VNG','admin1',1585717696236,2,0,'Transport','qjqjjqieiiwiwkssjjsskskksksksksksksksksksksksksksjsjsjsnsnsjsjsjsjsjsjsjsjsjdjjsjsjsjsjsjeieiekekjsjsjsjdjdjdjdjjd','[\"https://drive.google.com/uc?export=view&id=1IEujDYINJTA4DvRAD-rvNRtHTHdDKL_Z\"]','',2,'2020-04-01 05:08:22'),(14,20200402,'78c029f','#HCMUS-78c029f','HCMUS','autth',1585818519902,2,0,'Salary','111','[\"https://drive.google.com/uc?export=view&id=163x89eZdhfO1WdPh8y1e3aFqXJABKwDW\",\"https://drive.google.com/uc?export=view&id=1IG96ZzQ5tgVOfKwFGRc1ojnR4U7d_ilS\",\"https://drive.google.com/uc?export=view&id=1C0pTouKjnCAUKvWPNhh8yHDPcFQQrACO\"]','',2,'2020-04-02 09:08:53'),(15,20200402,'9f96908','#HCMUS-9f96908','HCMUS','autth',1585818669606,1,0,'Salary','2222','[\"https://drive.google.com/uc?export=view&id=1o9VsIir6PdMBF8m5AlPBzcLh9pUTskqa\",\"https://drive.google.com/uc?export=view&id=1Oq2wMfTs7CxopYZZTo4XTv8nt6UA2mrz\",\"https://drive.google.com/uc?export=view&id=1-iRR3eyIlLtct1AqGOLLJcQCrWdN2_Y1\"]','',1,'2020-04-02 10:41:39'),(16,20200407,'c172946','#VNG-c172946','VNG','duytv',1586266466058,1,0,'Medical','fgh','[]','',2,'2020-04-07 13:34:27'),(17,20200407,'89911c0','#VNG-89911c0','VNG','duytv',1586266538304,1,0,'Transport','uj','[]','',2,'2020-04-07 13:35:39');
/*!40000 ALTER TABLE `ComplaintLog202004` ENABLE KEYS */;
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
  `requestId` varchar(8) NOT NULL DEFAULT '',
  `leavingRequestId` varchar(32) NOT NULL DEFAULT '',
  `companyId` varchar(32) NOT NULL DEFAULT '',
  `username` varchar(32) NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `category` varchar(256) NOT NULL DEFAULT '',
  `reason` varchar(4096) NOT NULL DEFAULT '',
  `detail` varchar(4096) NOT NULL DEFAULT '[]',
  `response` varchar(2048) NOT NULL DEFAULT '',
  `approver` varchar(256) NOT NULL DEFAULT '',
  `status` int NOT NULL DEFAULT '2',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `complaintId_UNIQUE` (`leavingRequestId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`),
  KEY `status_INDEX` (`status`),
  KEY `leavingRequestId_INDEX` (`leavingRequestId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LeavingRequestLog202004`
--

LOCK TABLES `LeavingRequestLog202004` WRITE;
/*!40000 ALTER TABLE `LeavingRequestLog202004` DISABLE KEYS */;
INSERT INTO `LeavingRequestLog202004` VALUES (8,20200406,'a43146a','#HCMUS-LR-a43146a','HCMUS','duydc',1586165998355,'Nghỉ phép năm','asdasd','[{\"date\":\"20200412\",\"session\":1},{\"date\":\"20200413\",\"session\":0},{\"date\":\"20200414\",\"session\":2}]','','',2,'2020-04-06 09:40:02'),(9,20200406,'23c9ef5','#HCMUS-LR-23c9ef5','HCMUS','duydc',1586169633170,'Nghỉ phép năm','dasd','[{\"date\":\"20200421\",\"session\":0},{\"date\":\"20200422\",\"session\":0},{\"date\":\"20200423\",\"session\":0}]','','',2,'2020-04-06 10:40:37'),(10,20200406,'3075b6b','#HCMUS-LR-3075b6b','HCMUS','duydc',1586174196253,'Nghỉ phép năm','fasddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd','[{\"date\":\"20200406\",\"session\":0}]','','',2,'2020-04-06 11:56:39'),(11,20200407,'2561a55','#HCMUS-LR-2561a55','HCMUS','duydc',1586260579985,'Nghỉ phép năm','dasd','[{\"date\":\"20200513\",\"session\":0},{\"date\":\"20200520\",\"session\":1}]','','',2,'2020-04-07 11:56:22'),(12,20200407,'e997334','#HCMUS-LR-e997334','HCMUS','duydc',1586260607208,'Nghỉ thai sản','dasd','[{\"date\":\"20200529\",\"session\":2}]','','',2,'2020-04-07 11:56:49'),(13,20200407,'574e989','#HCMUS-LR-574e989','HCMUS','duydc',1586260965193,'Nghỉ bệnh','asdas','[{\"date\":\"20200609\",\"session\":1},{\"date\":\"20200610\",\"session\":1},{\"date\":\"20200611\",\"session\":0},{\"date\":\"20200612\",\"session\":0}]','','',2,'2020-04-07 12:02:49'),(14,20200407,'dad304e','#HCMUS-LR-dad304e','HCMUS','duydc',1586262025656,'Nghỉ bù do làm thêm giờ','qwq112','[{\"date\":\"20200522\",\"session\":2},{\"date\":\"20200529\",\"session\":1}]','','',2,'2020-04-07 12:20:29'),(15,20200407,'cfd27ce','#HCMUS-LR-cfd27ce','HCMUS','duydc',1586262228744,'Nghỉ phép năm','dasd','[{\"date\":\"20200606\",\"session\":0},{\"date\":\"20200613\",\"session\":0}]','','',2,'2020-04-07 12:23:51'),(16,20200407,'ba60a52','#HCMUS-LR-ba60a52','HCMUS','duydc',1586262577392,'Nghỉ phép năm','asd','[{\"date\":\"20200813\",\"session\":0}]','','',2,'2020-04-07 12:29:40'),(17,20200407,'e25efd1','#HCMUS-LR-e25efd1','HCMUS','duydc',1586267104391,'Nghỉ phép không hưởng lương','Do hoc','[{\"date\":\"20200408\",\"session\":1},{\"date\":\"20200409\",\"session\":0},{\"date\":\"20200410\",\"session\":0}]','','',2,'2020-04-07 13:45:07');
/*!40000 ALTER TABLE `LeavingRequestLog202004` ENABLE KEYS */;
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
  `count` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
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
INSERT INTO `LogCounter` VALUES ('ComplaintLog','202003','1','18','2020-04-04 13:59:57'),('ComplaintLog','202003','2-HCMUS','5','2020-04-04 14:01:29'),('ComplaintLog','202003','2-VNG','8','2020-04-07 12:40:26'),('ComplaintLog','202004','1','2','2020-04-07 13:35:39'),('ComplaintLog','202004','2-HCMUS','2','2020-04-04 14:01:52'),('ComplaintLog','202004','2-VNG','4','2020-04-07 14:42:45'),('LeavingRequestLog','202004','HCMUS','8','2020-04-07 13:45:07');
/*!40000 ALTER TABLE `LogCounter` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-08  8:42:55
CREATE DATABASE  IF NOT EXISTS `HTCC_Company` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
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
INSERT INTO `Company` VALUES ('HCMUS','Trường Đại học Khoa Học Tự Nhiên','028 6288 4499','bantin@hcmus.edu.vn','Trường Đại học Khoa Học Tự Nhiên',1,'2020-04-07 14:39:37'),('VNG','Công Ty Cổ Phần VNG','1900561558','campus@vng.com.vn','Lô 03b-04-05-06-07, đường số 13, khu Công Nghiệp, Khu Chế Xuất Tân Thuận, phường Tân Thuận Đông, Quận 7, TP.HCM.',1,'2020-04-07 15:42:34');
/*!40000 ALTER TABLE `Company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CompanyUser`
--

DROP TABLE IF EXISTS `CompanyUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CompanyUser` (
  `companyId` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL DEFAULT '',
  `phoneNumber` varchar(20) NOT NULL DEFAULT '',
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
INSERT INTO `CompanyUser` VALUES ('a','a','$2a$10$Emhax71DZHAPEC6PonfDjuslfh2v1SRcFhn1rwuD6gWlOhqze..IS','naduy.hcmus@gmail.com','0948202701',0,0,'2020-04-07 16:06:36'),('HCMUS','autth','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','haiau762@gmail.com','0342012299',0,1,'2020-04-04 05:44:47'),('HCMUS','datnq','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','nguyenquocdat2511998@gmail.com','0343244644',0,1,'2020-04-04 05:44:47'),('HCMUS','duydc','$2a$10$6klYF5wFHCbGKGBBkn2Ocu4FS3Thqt1HSi62b3irSLv5h0Ev4HeZG','dinhcongduy125@gmail.com','0335406888',0,1,'2020-04-04 05:44:47'),('HCMUS','duyna','$2a$10$MRgcb9OCyUGpfvD3yCTtreptfxeM3gklBkbQFiahfFV9CPViQsaVW','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-04 05:44:48'),('HCMUS','duytv','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','duytv2907@gmail.com','0931852460',0,1,'2020-04-04 05:44:48'),('HCMUS','hcmus','$2a$10$YMs9JcjDsWDwT.Cx.19R3Olp1FJ7ZZo9n0V/WDDDSvzd0/qzRsmr2','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-07 03:12:44'),('HCMUS','huylh','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','hoanghuyit98@gmail.com','0325236999',0,1,'2020-04-04 05:44:48'),('VNG','admin','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','09482027091111',0,0,'2020-04-07 15:18:05'),('VNG','admin1','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-04-04 05:44:48'),('VNG','admin10','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 08:07:19'),('VNG','admin11','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 14:43:08'),('VNG','admin12','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 08:07:19'),('VNG','admin13','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 14:43:08'),('VNG','admin14','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 08:07:19'),('VNG','admin15','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 14:43:08'),('VNG','admin2','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 08:07:19'),('VNG','admin3','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 14:43:08'),('VNG','admin4','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 08:07:19'),('VNG','admin5','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 14:43:08'),('VNG','admin6','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 08:07:19'),('VNG','admin7','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 14:43:08'),('VNG','admin8','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 08:07:19'),('VNG','admin9','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com','0948202709',0,1,'2020-03-16 14:43:08'),('VNG','dat','$2a$10$H0ZLKqN.yvz.LXwKcLJeK.9nh.7RK69WArlnP.0Mjbs34lheElVR6','nguyenquocdat2511998@gmail.com','0948202709',0,1,'2020-04-07 15:36:56'),('vng','dat123','$2a$10$RJKmDR4MOmQYrbVOdiW4r.udLlxlMBKbWpSMxx79OH0xb80hDGs5K','nguyenquocdat2511998@gmail.com','0948202709',0,1,'2020-04-07 15:38:41'),('vng','datt','$2a$10$zvim5cJCdv7ag2o6t5yUleG0mYufKRE22gFiGhHL9fqzRqih3SNwy','nguyenquocdat2511998@gmail.com','0333333333333',0,1,'2020-04-07 15:40:10'),('VNG','duytv','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','duytv2907@gmail.com','0931852460',0,1,'2020-04-04 05:44:48');
/*!40000 ALTER TABLE `CompanyUser` ENABLE KEYS */;
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
INSERT INTO `EmployeeInfo` VALUES ('a','a','a-00001','','','','','1970-01-01','naduy.hcmus@gmail.com','','0948202701','','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-07 16:06:30'),('HCMUS','autth','HCMUS-00002','NVC','React','Fresher Frontend Developer','Trương Thị Hải Âu','1998-01-01','haiau762@gmail.com','123456789','0342012299','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-06 15:31:55'),('HCMUS','datnq','HCMUS-00001','NVC','Vue','Fresher Frontend Developer','Nguyễn Quốc Đạt','1998-01-01','nguyenquocdat2511998@gmail.com','123456789','0343244644','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-21 00:58:48'),('HCMUS','duydc','HCMUS-00004','NVC','Mobile','Junior Mobile Developer','Đinh Công duy','2000-07-12','dinhcongduy125@gmail.com','025728760','0335406888','HCM','https://drive.google.com/uc?export=view&id=1QD9IzdGeQlvf0VlF110BrCp4cbSRm0z7','2020-04-06 15:31:55'),('HCMUS','duyna','HCMUS-00003','NVC','Backend','Team Leader','NGUYỄN ANH DUY','1998-09-27','naduy.hcmus@gmail.com','272683901','0948202709','Q7, TPHCM','https://drive.google.com/uc?export=view&id=1wB4Ir5yH5wlJS1mtCfrFo2LvjeOvQFAC','2020-04-06 15:31:56'),('HCMUS','duytv','HCMUS-00005','NVC','Mobile','Junior Mobile Developer','Võ Tấn Duy','1998-01-01','duytv2907@gmail.com','123456789','0931852460','Gò Vấp, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-06 15:31:56'),('HCMUS','hcmus','HCMUS-00007','','','','','1970-01-01','naduy.hcmus@gmail.com','','0948202709','','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-07 03:12:45'),('HCMUS','huylh','HCMUS-00006','NVC','Backend','Junior Backend Developer','Lê Hoàng Huy','1998-01-01','hoanghuyit98@gmail.com','123456789','0325236999','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-06 15:31:56'),('VNG','admin','VNG-00001','CAMPUS','PMA','Team Leader','Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','09482027091111','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-07 15:17:54'),('VNG','admin1','VNG-00002','CAMPUS','ZPI','Junior Mobile Developer','Võ Tấn Duy','1998-07-29','Duytv.2907@gmail.com','123456789','0948202709','Gò Vấp, TPHCM','https://drive.google.com/uc?export=view&id=1aN0kY8wa-FIjJKkMV9N0ctJtrQTZktEn','2020-04-07 13:46:58'),('VNG','admin10','VNG-00012','CAMPUS','PMA','Team Leader','Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0335406888','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-04 05:51:28'),('VNG','admin11','VNG-00013','CAMPUS','ZPI','Team Leader','NGUYỄN ANH DUY','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-04 05:51:28'),('VNG','admin12','VNG-00014','FV','PMA','Team Leader','Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0335406888','Quận 9, TPHCM','https://drive.google.com/uc?export=view&id=1wB4Ir5yH5wlJS1mtCfrFo2LvjeOvQFAC','2020-04-04 05:51:27'),('VNG','admin13','VNG-00015','CAMPUS','ZA','Team Leader','Trương Thị Hải Âu','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-05 13:03:42'),('VNG','admin14','VNG-00016','CAMPUS','ZPI','Team Leader','Trương Thị Hải Âu','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-05 13:03:42'),('VNG','admin15','VNG-00017','CAMPUS','PMA','Team Leader','Lê Hoàng Huy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-05 13:03:43'),('VNG','admin2','VNG-00004','CAMPUS','PMA','Team Leader','Lê Hoàng Huy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://drive.google.com/uc?export=view&id=1wB4Ir5yH5wlJS1mtCfrFo2LvjeOvQFAC','2020-04-05 13:03:43'),('VNG','admin3','VNG-00005','FV','ZA','Team Leader','Nguyễn Quốc Đạt','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-05 13:03:43'),('VNG','admin4','VNG-00006','CAMPUS','PMA','Team Leader','Nguyễn Quốc Đạt','2020-05-18','naduy.hcmus@gmail.com','025728760','0931852460','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-05 13:03:43'),('VNG','admin5','VNG-00007','CAMPUS','PMA','Team Leader','Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://drive.google.com/uc?export=view&id=1wB4Ir5yH5wlJS1mtCfrFo2LvjeOvQFAC','2020-04-04 05:51:27'),('VNG','admin6','VNG-00008','CAMPUS','ZA','Team Leader','Võ Tấn Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0931852460','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-04 05:51:27'),('VNG','admin7','VNG-00009','CAMPUS','PMA','Team Leader','Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 05:47:37'),('VNG','admin8','VNG-00010','CAMPUS','PMA','Team Leader','Võ Tấn Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0931852460','Quận 9, TPHCM','https://drive.google.com/uc?export=view&id=1wB4Ir5yH5wlJS1mtCfrFo2LvjeOvQFAC','2020-04-04 05:51:27'),('VNG','admin9','VNG-00011','FV','PMA','Team Leader','Nguyễn Anh Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-05 13:03:43'),('VNG','dat','VNG-00018','','','','','1970-01-01','nguyenquocdat2511998@gmail.com','','0948202709','','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-07 15:36:56'),('vng','dat123','vng-00019','','','','','1970-01-01','nguyenquocdat2511998@gmail.com','','0948202709','','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-07 15:38:41'),('vng','datt','vng-00020','','','','','1970-01-01','nguyenquocdat2511998@gmail.com','','0333333333333','','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-07 15:40:10'),('VNG','duytv','VNG-00003','CAMPUS','ZPI','Junior Mobile Developer','Võ Tấn Duy','1998-01-01','duytv2907@gmail.com','123456789','0931852460','Gò Vấp, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-04-04 05:51:27');
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
INSERT INTO `Office` VALUES ('HCMUS','LT','Linh Trung','Đường số 5A, Q. Thủ Đức, Tp. Hồ Chí Minh',10.875701,106.799117,50,'02838962823','bantin@hcmus.edu.vn',0,'',0,'2020-04-07 14:42:43'),('HCMUS','NVC','Nguyễn Văn Cừ','227 Đường Nguyễn Văn Cừ, Phường 4, Quận 5, Hồ Chí Minh',10.762462,106.682752,20,'02862884499','bantin@hcmus.edu.vn',0,'',1,'2020-04-07 14:42:43'),('VNG','CAMPUS','Trụ sở Campus','Lô 03b-04-05-06-07, đường số 13, khu Công Nghiệp, Khu Chế Xuất Tân Thuận, phường Tân Thuận Đông, Quận 7, TP.HCM.',10.758033,106.746136,20,'1900561558','campus@vng.com.vn',0,'',1,'2020-04-07 14:38:42'),('VNG','HCM','VĂN PHÒNG HCM','Tầng 13, 14, Toà nhà Sài Gòn Giải Phóng, 432 Nguyễn Thị Minh Khai, Phường 5, Quận 3, TP.HCM',10.769619,106.685524,10,'1900561558','hcm@vng.com.vn',0,'',0,'2020-04-07 14:35:38');
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
  `shiftId` int(11) NOT NULL,
  `timeFrom` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '00:00',
  `timeTo` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '00:00',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`shiftId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `WorkingDay`
--

DROP TABLE IF EXISTS `WorkingDay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WorkingDay` (
  `companyId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `officeId` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `weekDay` int(11) NOT NULL,
  `isWorking` tinyint(1) NOT NULL DEFAULT '1',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`officeId`,`weekDay`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-08  8:42:59
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
INSERT INTO `AdminUser` VALUES ('aaaaaaa','$2a$10$Q0k4WvDnd/0fieeGaDG6u./N6kX2QVAKwcPxk.XhXCsniuWYbl46q','aaa','0333333333333333','aaaa@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-03-26 14:48:09'),('admin','$2a$10$JjuoahY1k4mhsk87lEWzN.FUi8I1lsDaZ0HoTxYaolZ7/ZhMcQTby','Nguyễn Anh Duy','0948202701','naduy.hcmus@gmail.com','https://drive.google.com/uc?export=view&id=1mN0pR8SWVQSvJIOBNiABC3pQTmRfTa6o',0,1,'2020-04-07 16:12:01'),('admin1','$2a$10$9OxClkoaV9bbNLc1g3MXC.3R5FyLnhdSYSsejQ6YPASk5UcRjGfD2','Nguyễn Quốc Đạt 123 456 1 111','0912345678','admin@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-03-26 14:59:50'),('dat','$2a$10$wLiomueoC9RxqSE8IfPFiOdLTTfI6hBKPENVv5obi4cncR0OtOmqm','a','033333333333','aaa@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-04-07 08:43:34'),('duyna','$2a$10$jAkp0g.MDz3TdmDgBJTj..VNpfCrNUHxRGY5ZN9ljRFEv9MOnHs/a','NGUYEN ANH DUY','0948202709','naduy.hcmus@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-04-06 15:28:10');
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

-- Dump completed on 2020-04-08  8:43:01
