CREATE DATABASE  IF NOT EXISTS `HTCC_Log` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `HTCC_Log`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: HTCC_Log
-- ------------------------------------------------------
-- Server version	5.7.27

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
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `ymd` bigint(20) NOT NULL DEFAULT '0',
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT '1',
  `requestTime` bigint(20) NOT NULL DEFAULT '0',
  `responseTime` bigint(20) NOT NULL DEFAULT '0',
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
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
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `ymd` bigint(20) NOT NULL DEFAULT '0',
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT '1',
  `requestTime` bigint(20) NOT NULL DEFAULT '0',
  `responseTime` bigint(20) NOT NULL DEFAULT '0',
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
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
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `ymd` bigint(20) NOT NULL DEFAULT '0',
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT '1',
  `requestTime` bigint(20) NOT NULL DEFAULT '0',
  `responseTime` bigint(20) NOT NULL DEFAULT '0',
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202005`
--

LOCK TABLES `ApiLog202005` WRITE;
/*!40000 ALTER TABLE `ApiLog202005` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202005` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ApiLog202006`
--

DROP TABLE IF EXISTS `ApiLog202006`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202006` (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `ymd` bigint(20) NOT NULL DEFAULT '0',
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT '1',
  `requestTime` bigint(20) NOT NULL DEFAULT '0',
  `responseTime` bigint(20) NOT NULL DEFAULT '0',
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202006`
--

LOCK TABLES `ApiLog202006` WRITE;
/*!40000 ALTER TABLE `ApiLog202006` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202006` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ApiLog202007`
--

DROP TABLE IF EXISTS `ApiLog202007`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202007` (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `ymd` bigint(20) NOT NULL DEFAULT '0',
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT '1',
  `requestTime` bigint(20) NOT NULL DEFAULT '0',
  `responseTime` bigint(20) NOT NULL DEFAULT '0',
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202007`
--

LOCK TABLES `ApiLog202007` WRITE;
/*!40000 ALTER TABLE `ApiLog202007` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202007` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ApiLog202008`
--

DROP TABLE IF EXISTS `ApiLog202008`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202008` (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `ymd` bigint(20) NOT NULL DEFAULT '0',
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT '1',
  `requestTime` bigint(20) NOT NULL DEFAULT '0',
  `responseTime` bigint(20) NOT NULL DEFAULT '0',
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202008`
--

LOCK TABLES `ApiLog202008` WRITE;
/*!40000 ALTER TABLE `ApiLog202008` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202008` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ApiLog202009`
--

DROP TABLE IF EXISTS `ApiLog202009`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202009` (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `ymd` bigint(20) NOT NULL DEFAULT '0',
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT '1',
  `requestTime` bigint(20) NOT NULL DEFAULT '0',
  `responseTime` bigint(20) NOT NULL DEFAULT '0',
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202009`
--

LOCK TABLES `ApiLog202009` WRITE;
/*!40000 ALTER TABLE `ApiLog202009` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202009` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ApiLog202010`
--

DROP TABLE IF EXISTS `ApiLog202010`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202010` (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `ymd` bigint(20) NOT NULL DEFAULT '0',
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT '1',
  `requestTime` bigint(20) NOT NULL DEFAULT '0',
  `responseTime` bigint(20) NOT NULL DEFAULT '0',
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202010`
--

LOCK TABLES `ApiLog202010` WRITE;
/*!40000 ALTER TABLE `ApiLog202010` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202010` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ApiLog202011`
--

DROP TABLE IF EXISTS `ApiLog202011`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202011` (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `ymd` bigint(20) NOT NULL DEFAULT '0',
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT '1',
  `requestTime` bigint(20) NOT NULL DEFAULT '0',
  `responseTime` bigint(20) NOT NULL DEFAULT '0',
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202011`
--

LOCK TABLES `ApiLog202011` WRITE;
/*!40000 ALTER TABLE `ApiLog202011` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202011` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ApiLog202012`
--

DROP TABLE IF EXISTS `ApiLog202012`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202012` (
  `logId` int(11) NOT NULL AUTO_INCREMENT,
  `ymd` bigint(20) NOT NULL DEFAULT '0',
  `serviceId` int(11) NOT NULL DEFAULT '0',
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT '1',
  `requestTime` bigint(20) NOT NULL DEFAULT '0',
  `responseTime` bigint(20) NOT NULL DEFAULT '0',
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202012`
--

LOCK TABLES `ApiLog202012` WRITE;
/*!40000 ALTER TABLE `ApiLog202012` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202012` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-15 22:54:47
CREATE DATABASE  IF NOT EXISTS `HTCC_Company` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `HTCC_Company`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: HTCC_Company
-- ------------------------------------------------------
-- Server version	5.7.27

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
  `role` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '1',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`companyId`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CompanyUser`
--

LOCK TABLES `CompanyUser` WRITE;
/*!40000 ALTER TABLE `CompanyUser` DISABLE KEYS */;
INSERT INTO `CompanyUser` VALUES ('VNG','admin','$2a$10$SXqCEoTNNrRcY8bSfGDEfumjJw32t9N5WgI47g.dDJkSSnxePzDPO','naduy.hcmus@gmail.com',0,1,'2020-03-14 14:33:48');
/*!40000 ALTER TABLE `CompanyUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmployeeInfo`
--

DROP TABLE IF EXISTS `EmployeeInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EmployeeInfo` (
  `companyId` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `username` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `employeeId` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `officeId` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `department` varchar(128) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `fullName` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `birthDate` date NOT NULL,
  `email` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `identityCardNo` varchar(12) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `phoneNumber` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `address` varchar(512) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  `avatar` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT 'https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',
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
INSERT INTO `EmployeeInfo` VALUES ('VNG','admin','VNG-00001','CAMPUS','PMA','Nguyễn Anh Duy','1998-09-28','naduy.hcmus@gmail.com','272683901','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-13 13:56:53');
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

-- Dump completed on 2020-03-15 22:54:47
CREATE DATABASE  IF NOT EXISTS `HTCC_Admin` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `HTCC_Admin`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: HTCC_Admin
-- ------------------------------------------------------
-- Server version	5.7.27

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
  `fullName` varchar(256) CHARACTER SET utf8 NOT NULL,
  `phoneNumber` varchar(20) NOT NULL DEFAULT '',
  `email` varchar(128) NOT NULL DEFAULT '',
  `avatar` varchar(256) NOT NULL DEFAULT 'https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',
  `role` int(11) NOT NULL DEFAULT '1',
  `status` int(11) NOT NULL DEFAULT '1',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AdminUser`
--

LOCK TABLES `AdminUser` WRITE;
/*!40000 ALTER TABLE `AdminUser` DISABLE KEYS */;
INSERT INTO `AdminUser` VALUES ('admin','$2a$10$9OxClkoaV9bbNLc1g3MXC.3R5FyLnhdSYSsejQ6YPASk5UcRjGfD2','Nguyễn Anh Duy','0948202709','naduy.hcmus@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',0,1,'2020-03-15 15:54:13');
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

-- Dump completed on 2020-03-15 22:54:47
