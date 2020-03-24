CREATE DATABASE  IF NOT EXISTS `HTCC_Log` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
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
-- Table structure for table `ApiLog202003`
--

DROP TABLE IF EXISTS `ApiLog202003`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202003` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202003`
--

LOCK TABLES `ApiLog202003` WRITE;
/*!40000 ALTER TABLE `ApiLog202003` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202003` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202003`
--

LOCK TABLES `CheckInLog202003` WRITE;
/*!40000 ALTER TABLE `CheckInLog202003` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202004`
--

LOCK TABLES `CheckInLog202004` WRITE;
/*!40000 ALTER TABLE `CheckInLog202004` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202003`
--

LOCK TABLES `CheckOutLog202003` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202003` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202004`
--

LOCK TABLES `CheckOutLog202004` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202004` DISABLE KEYS */;
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
  `images` varchar(2048) DEFAULT '',
  `response` varchar(4096) DEFAULT '',
  `status` int NOT NULL DEFAULT '2',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ComplaintLog202003`
--

LOCK TABLES `ComplaintLog202003` WRITE;
/*!40000 ALTER TABLE `ComplaintLog202003` DISABLE KEYS */;
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
  `images` varchar(2048) DEFAULT '',
  `response` varchar(4096) DEFAULT '',
  `status` int NOT NULL DEFAULT '2',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ComplaintLog202004`
--

LOCK TABLES `ComplaintLog202004` WRITE;
/*!40000 ALTER TABLE `ComplaintLog202004` DISABLE KEYS */;
/*!40000 ALTER TABLE `ComplaintLog202004` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-24 10:03:50
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
-- Table structure for table `CompanyUser`
--

DROP TABLE IF EXISTS `CompanyUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CompanyUser` (
  `companyId` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(256) NOT NULL,
  `email` varchar(128) NOT NULL,
  `role` int NOT NULL DEFAULT '0',
  `status` int NOT NULL DEFAULT '1',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CompanyUser`
--

LOCK TABLES `CompanyUser` WRITE;
/*!40000 ALTER TABLE `CompanyUser` DISABLE KEYS */;
INSERT INTO `CompanyUser` VALUES ('HCMUS','autth','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','haiau762@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','datnq','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','nguyenquocdat2511998@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','duydc','$2a$10$6klYF5wFHCbGKGBBkn2Ocu4FS3Thqt1HSi62b3irSLv5h0Ev4HeZG','dinhcongduy125@gmail.com',0,1,'2020-03-21 03:12:53'),('HCMUS','duyna','$2a$10$ABFyaFNiF0nLpmeMBpKKE.jbLTWz7wdcQEPuz4MDfaj2ytcyN6dgW','naduy.hcmus@gmail.com',0,1,'2020-03-21 02:03:48'),('HCMUS','duytv','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','duytv2907@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','huylh','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','hoanghuyit98@gmail.com',0,1,'2020-03-18 04:34:24'),('VNG','admin','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com',0,1,'2020-03-16 08:07:19'),('VNG','admin1','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com',0,1,'2020-03-16 14:43:08'),('VNG','duytv','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','duytv2907@gmail.com',0,1,'2020-03-18 04:34:24');
/*!40000 ALTER TABLE `CompanyUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmployeeInfo`
--

DROP TABLE IF EXISTS `EmployeeInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EmployeeInfo` (
  `companyId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `employeeId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `officeId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `department` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `title` varchar(128) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `fullName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `birthDate` date NOT NULL,
  `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `identityCardNo` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `phoneNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `address` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`username`),
  UNIQUE KEY `employeeId_UNIQUE` (`employeeId`),
  KEY `IDX_employeeId` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeInfo`
--

LOCK TABLES `EmployeeInfo` WRITE;
/*!40000 ALTER TABLE `EmployeeInfo` DISABLE KEYS */;
INSERT INTO `EmployeeInfo` VALUES ('HCMUS','autth','HCMUS-002','NVC','React','Fresher Frontend Developer','Trương Thị Hải Âu','1998-01-01','haiau762@gmail.com','123456789','0342012299','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('HCMUS','datnq','HCMUS-00001','NVC','Vue','Fresher Frontend Developer','Nguyễn Quốc Đạt','1998-01-01','nguyenquocdat2511998@gmail.com','123456789','0343244644','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-21 00:58:48'),('HCMUS','duydc','HCMUS-004','NVC','Mobile','Junior Mobile Developer','Đinh Công Duy142','2000-06-01','dinhcongduy125@gmail.com','025728760','0335406888','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-23 13:07:23'),('HCMUS','duyna','HCMUS-003','NVC','Backend','Team Leader','Nguyễn Anh Duy','1998-09-26','naduy.hcmus@gmail.com','272683901','0948202709','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-21 02:02:32'),('HCMUS','duytv','HCMUS-005','NVC','Mobile','Junior Mobile Developer','Võ Tấn Duy','1998-01-01','duytv2907@gmail.com','123456789','0931852460','Gò Vấp, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('HCMUS','huylh','HCMUS-006','NVC','Backend','Junior Backend Developer','Lê Hoàng Huy','1998-01-01','hoanghuyit98@gmail.com','123456789','0325236999','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('VNG','admin','VNG-00001','CAMPUS','PMA','Team Leader','Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 05:47:37'),('VNG','admin1','VNG-002','CAMPUS','ZPI','Junior Mobile Developer','Võ Tấn Duy','1998-07-29','Duytv.2907@gmail.com','123456789','0948202709','Gò Vấp, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:48'),('VNG','duytv','VNG-003','CAMPUS','Mobile','Junior Mobile Developer','Võ Tấn Duy','1998-01-01','duytv2907@gmail.com','123456789','0931852460','Gò Vấp, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47');
/*!40000 ALTER TABLE `EmployeeInfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-24 10:03:52
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
INSERT INTO `AdminUser` VALUES ('aaaaaaaaa','$2a$10$TM2OBBvWZXoi6fYIWo/13.pkkKJRjUgLaG8G09jGz2IwxusaSRqgm','aaaaaaaaaaa','03333333333333','nguyen@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-03-23 15:10:37'),('admin','$2a$10$81hQMXOlnFufwl.jYXCVveQVcC2JOhCHeBtS4Vng5/EoHw46oUN4i','Nguyễn Anh Duy 123','0948202701','naduy.hcmus@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',0,1,'2020-03-22 15:59:02'),('admin1','$2a$10$9OxClkoaV9bbNLc1g3MXC.3R5FyLnhdSYSsejQ6YPASk5UcRjGfD2','Nguyễn Quốc Đạt','0912345678','admin@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-03-18 15:10:16');
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

-- Dump completed on 2020-03-24 10:03:54
