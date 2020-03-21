CREATE DATABASE  IF NOT EXISTS `HTCC_Log` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
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
  `userIP` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202003`
--

LOCK TABLES `ApiLog202003` WRITE;
/*!40000 ALTER TABLE `ApiLog202003` DISABLE KEYS */;
INSERT INTO `ApiLog202003` VALUES (1,20200321,'1bfc862',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"HCMUS\",\"password\":\"1234\",\"username\":\"duyna\"}',-1,1584768495749,1584768499372,'113.185.72.232',NULL),(2,20200321,'e7b2e20',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"HCMUS\",\"password\":\"123\",\"username\":\"duyna\"}',1,1584768505259,1584768506847,'113.185.72.232',NULL),(3,20200321,'1006814',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"HCMUS\",\"password\":\"123\",\"username\":\"duyna\"}',1,1584768534938,1584768535305,'113.185.72.232',NULL),(4,20200321,'d541de7',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duyna','GET','/api/employee/checkin/HCMUS/duyna','{}','',0,1584768535617,1584768537167,'113.185.72.232',NULL),(5,20200321,'37d0d65',1,'https://1612145.online:80/api/gateway/private/logout','POST','/api/gateway/private/logout','{}','',1,1584768581298,1584768581384,'113.185.72.232',NULL),(6,20200321,'23ceec6',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"HCMUS\",\"password\":\"123\",\"username\":\"duyna\"}',1,1584768602634,1584768606807,'113.185.72.232',NULL),(7,20200321,'19016c4',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"HCMUS\",\"password\":\"123\",\"username\":\"duyna\"}',1,1584768617393,1584768617726,'113.185.72.232',NULL),(8,20200321,'8e15c61',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duyna','GET','/api/employee/checkin/HCMUS/duyna','{}','',1,1584768618037,1584768618225,'113.185.72.232',NULL),(9,20200321,'3f0a250',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duyna','GET','/api/employee/checkin/HCMUS/duyna','{}','',1,1584768636124,1584768636213,'113.185.72.232',NULL),(10,20200321,'be5b97b',2,'https://1612145.online:80/api/employee/checkin/','POST','/api/employee/checkin/','{}','{\"clientTime\":1584768634245,\"companyId\":\"HCMUS\",\"latitude\":10.9617634,\"longitude\":106.8373791,\"type\":1,\"usedWifi\":false,\"username\":\"duyna\"}',1,1584768636122,1584768636530,'113.185.72.232',NULL),(11,20200321,'d60e33b',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duyna','GET','/api/employee/checkin/HCMUS/duyna','{}','',1,1584768636972,1584768637050,'113.185.72.232',NULL),(12,20200321,'8b8dd07',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duyna','GET','/api/employee/checkin/HCMUS/duyna','{}','',1,1584768836944,1584768837064,'113.185.72.232',NULL),(13,20200321,'8e5caa0',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duyna','GET','/api/employee/checkin/HCMUS/duyna','{}','',1,1584768843813,1584768843910,'113.185.72.232',NULL),(14,20200321,'9facc73',2,'https://1612145.online:80/api/employee/checkin/','POST','/api/employee/checkin/','{}','{\"clientTime\":1584768841920,\"companyId\":\"HCMUS\",\"latitude\":10.962893,\"longitude\":106.8385506,\"type\":1,\"usedWifi\":false,\"username\":\"duyna\"}',1,1584768843818,1584768843886,'113.185.72.232',NULL),(15,20200321,'eed8b7d',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duyna','GET','/api/employee/checkin/HCMUS/duyna','{}','',1,1584768844122,1584768844218,'113.185.72.232',NULL),(16,20200321,'4b0113d',1,'https://1612145.online:80/api/gateway/private/logout','POST','/api/gateway/private/logout','{}','',1,1584769015066,1584769015409,'113.185.72.232',NULL),(17,20200321,'7873ab7',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"VNG\",\"password\":\"1234\",\"username\":\"admin\"}',1,1584769026712,1584769027029,'113.185.72.232',NULL),(18,20200321,'e1e075f',2,'https://1612145.online:80/api/employee/checkin/VNG/admin','GET','/api/employee/checkin/VNG/admin','{}','',1,1584769027359,1584769027485,'113.185.72.232',NULL),(19,20200321,'3a18d9d',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"HCMUS\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769350785,1584769351137,'42.118.20.0',NULL),(20,20200321,'1566f5d',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duytv','GET','/api/employee/checkin/HCMUS/duytv','{}','',1,1584769351505,1584769351608,'42.118.20.0',NULL),(21,20200321,'9063bf7',1,'https://1612145.online:80/api/gateway/private/logout','POST','/api/gateway/private/logout','{}','',1,1584769354593,1584769354691,'42.118.20.0',NULL),(22,20200321,'6270062',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"hcmus\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769365606,1584769365898,'42.118.20.0',NULL),(23,20200321,'e46dc4e',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"hcmus\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769367457,1584769367796,'42.118.20.0',NULL),(24,20200321,'624bf81',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"hcmus\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769376047,1584769376297,'42.118.20.0',NULL),(25,20200321,'6ce3c96',1,'https://1612145.online:80/api/gateway/private/logout','POST','/api/gateway/private/logout','{}','',1,1584769423241,1584769423355,'113.185.72.232',NULL),(26,20200321,'086b719',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"hcmus\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769423244,1584769423667,'42.118.20.0',NULL),(27,20200321,'d92e8b1',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"hcmus\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769425890,1584769426116,'42.118.20.0',NULL),(28,20200321,'0bc4011',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"VNG\",\"password\":\"1234\",\"username\":\"admin1\"}',1,1584769434457,1584769434764,'113.185.72.232',NULL),(29,20200321,'787f7c5',2,'https://1612145.online:80/api/employee/checkin/VNG/admin1','GET','/api/employee/checkin/VNG/admin1','{}','',1,1584769435122,1584769435172,'113.185.72.232',NULL),(30,20200321,'f722d72',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"hcmus\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769443803,1584769444064,'42.118.20.0',NULL),(31,20200321,'701b682',1,'https://1612145.online:80/api/gateway/private/logout','POST','/api/gateway/private/logout','{}','',1,1584769478357,1584769478455,'113.185.72.232',NULL),(32,20200321,'acbe686',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"HCMUS\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769492951,1584769493330,'113.185.72.232',NULL),(33,20200321,'cb17db3',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duytv','GET','/api/employee/checkin/HCMUS/duytv','{}','',1,1584769493783,1584769493897,'113.185.72.232',NULL),(34,20200321,'b934c4a',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"hcmus\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769508004,1584769508400,'42.118.20.0',NULL),(35,20200321,'7277706',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"hcmus\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769510148,1584769510359,'42.118.20.0',NULL),(36,20200321,'f6a8dcc',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"HCMUS\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769512759,1584769512997,'42.118.20.0',NULL),(37,20200321,'8d39b12',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duytv','GET','/api/employee/checkin/HCMUS/duytv','{}','',1,1584769513269,1584769513342,'42.118.20.0',NULL),(38,20200321,'5a39478',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duytv','GET','/api/employee/checkin/HCMUS/duytv','{}','',1,1584769517717,1584769517765,'42.118.20.0',NULL),(39,20200321,'fca1d5e',2,'https://1612145.online:80/api/employee/checkin/','POST','/api/employee/checkin/','{}','{\"clientTime\":1584769516387,\"companyId\":\"HCMUS\",\"latitude\":10.8399831,\"longitude\":106.6592643,\"type\":1,\"usedWifi\":false,\"username\":\"duytv\"}',1,1584769517716,1584769518200,'42.118.20.0',NULL),(40,20200321,'d8bb777',2,'https://1612145.online:80/api/employee/checkin/HCMUS/duytv','GET','/api/employee/checkin/HCMUS/duytv','{}','',1,1584769518407,1584769518464,'42.118.20.0',NULL),(41,20200321,'952130f',1,'https://1612145.online:80/api/gateway/private/logout','POST','/api/gateway/private/logout','{}','',1,1584769523000,1584769523058,'42.118.20.0',NULL),(42,20200321,'fcc2f7f',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"hcmus\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769655913,1584769656184,'42.118.20.0',NULL),(43,20200321,'120c6a9',1,'https://1612145.online:80/api/gateway/public/login','POST','/api/gateway/public/login','{}','{\"clientId\":\"1\",\"companyId\":\"hcmus\",\"password\":\"1234\",\"username\":\"duytv\"}',1,1584769672097,1584769672402,'42.118.20.0',NULL);
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
  `userIP` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
-- Table structure for table `ApiLog202005`
--

DROP TABLE IF EXISTS `ApiLog202005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApiLog202005` (
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
  `userIP` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `userIP` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `userIP` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `userIP` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `userIP` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `userIP` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `userIP` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `userIP` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApiLog202012`
--

LOCK TABLES `ApiLog202012` WRITE;
/*!40000 ALTER TABLE `ApiLog202012` DISABLE KEYS */;
/*!40000 ALTER TABLE `ApiLog202012` ENABLE KEYS */;
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202003`
--

LOCK TABLES `CheckInLog202003` WRITE;
/*!40000 ALTER TABLE `CheckInLog202003` DISABLE KEYS */;
INSERT INTO `CheckInLog202003` VALUES (4,20200321,'9facc73','HCMUS','duyna',1584768841920,1584768843842,'08:30',0,10.762462,106.682755,10.962893,106.838554,10,0,NULL,NULL),(5,20200321,'fca1d5e','HCMUS','duytv',1584769516387,1584769518006,'08:30',0,10.762462,106.682755,10.839983,106.659264,10,0,NULL,NULL);
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
-- Table structure for table `CheckInLog202005`
--

DROP TABLE IF EXISTS `CheckInLog202005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202005` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202005`
--

LOCK TABLES `CheckInLog202005` WRITE;
/*!40000 ALTER TABLE `CheckInLog202005` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckInLog202005` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckInLog202006`
--

DROP TABLE IF EXISTS `CheckInLog202006`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202006` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202006`
--

LOCK TABLES `CheckInLog202006` WRITE;
/*!40000 ALTER TABLE `CheckInLog202006` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckInLog202006` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckInLog202007`
--

DROP TABLE IF EXISTS `CheckInLog202007`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202007` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202007`
--

LOCK TABLES `CheckInLog202007` WRITE;
/*!40000 ALTER TABLE `CheckInLog202007` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckInLog202007` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckInLog202008`
--

DROP TABLE IF EXISTS `CheckInLog202008`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202008` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202008`
--

LOCK TABLES `CheckInLog202008` WRITE;
/*!40000 ALTER TABLE `CheckInLog202008` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckInLog202008` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckInLog202009`
--

DROP TABLE IF EXISTS `CheckInLog202009`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202009` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202009`
--

LOCK TABLES `CheckInLog202009` WRITE;
/*!40000 ALTER TABLE `CheckInLog202009` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckInLog202009` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckInLog202010`
--

DROP TABLE IF EXISTS `CheckInLog202010`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202010` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202010`
--

LOCK TABLES `CheckInLog202010` WRITE;
/*!40000 ALTER TABLE `CheckInLog202010` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckInLog202010` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckInLog202011`
--

DROP TABLE IF EXISTS `CheckInLog202011`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202011` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202011`
--

LOCK TABLES `CheckInLog202011` WRITE;
/*!40000 ALTER TABLE `CheckInLog202011` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckInLog202011` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckInLog202012`
--

DROP TABLE IF EXISTS `CheckInLog202012`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckInLog202012` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckInLog202012`
--

LOCK TABLES `CheckInLog202012` WRITE;
/*!40000 ALTER TABLE `CheckInLog202012` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckInLog202012` ENABLE KEYS */;
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
-- Table structure for table `CheckOutLog202005`
--

DROP TABLE IF EXISTS `CheckOutLog202005`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202005` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202005`
--

LOCK TABLES `CheckOutLog202005` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202005` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckOutLog202005` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckOutLog202006`
--

DROP TABLE IF EXISTS `CheckOutLog202006`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202006` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202006`
--

LOCK TABLES `CheckOutLog202006` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202006` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckOutLog202006` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckOutLog202007`
--

DROP TABLE IF EXISTS `CheckOutLog202007`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202007` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202007`
--

LOCK TABLES `CheckOutLog202007` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202007` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckOutLog202007` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckOutLog202008`
--

DROP TABLE IF EXISTS `CheckOutLog202008`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202008` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202008`
--

LOCK TABLES `CheckOutLog202008` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202008` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckOutLog202008` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckOutLog202009`
--

DROP TABLE IF EXISTS `CheckOutLog202009`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202009` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202009`
--

LOCK TABLES `CheckOutLog202009` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202009` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckOutLog202009` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckOutLog202010`
--

DROP TABLE IF EXISTS `CheckOutLog202010`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202010` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202010`
--

LOCK TABLES `CheckOutLog202010` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202010` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckOutLog202010` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckOutLog202011`
--

DROP TABLE IF EXISTS `CheckOutLog202011`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202011` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202011`
--

LOCK TABLES `CheckOutLog202011` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202011` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckOutLog202011` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CheckOutLog202012`
--

DROP TABLE IF EXISTS `CheckOutLog202012`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CheckOutLog202012` (
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
  `ip` varchar(32) DEFAULT '',
  `updDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`),
  UNIQUE KEY `ymd_UNIQUE` (`ymd`,`companyId`,`username`),
  KEY `ymd_INDEX` (`ymd`),
  KEY `companyId_INDEX` (`companyId`),
  KEY `username_INDEX` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CheckOutLog202012`
--

LOCK TABLES `CheckOutLog202012` WRITE;
/*!40000 ALTER TABLE `CheckOutLog202012` DISABLE KEYS */;
/*!40000 ALTER TABLE `CheckOutLog202012` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-21 13:14:12
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
INSERT INTO `CompanyUser` VALUES ('HCMUS','autth','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','haiau762@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','datnq','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','nguyenquocdat2511998@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','duydc','$2a$10$6klYF5wFHCbGKGBBkn2Ocu4FS3Thqt1HSi62b3irSLv5h0Ev4HeZG','dinhcongduy125@gmail.com',0,1,'2020-03-21 03:12:53'),('HCMUS','duyna','$2a$10$ABFyaFNiF0nLpmeMBpKKE.jbLTWz7wdcQEPuz4MDfaj2ytcyN6dgW','naduy.hcmus@gmail.com',0,1,'2020-03-21 02:03:48'),('HCMUS','duytv','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','duytv2907@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','huylh','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','hoanghuyit98@gmail.com',0,1,'2020-03-18 04:34:24'),('VNG','admin','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com',0,1,'2020-03-16 08:07:19'),('VNG','admin1','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com',0,1,'2020-03-16 14:43:08');
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
INSERT INTO `EmployeeInfo` VALUES ('HCMUS','autth','HCMUS-002','NVC','React','Fresher Frontend Developer','Trương Thị Hải Âu','1998-01-01','haiau762@gmail.com','123456789','0342012299','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('HCMUS','datnq','HCMUS-00001','NVC','Vue','Fresher Frontend Developer','Nguyễn Quốc Đạt','1998-01-01','nguyenquocdat2511998@gmail.com','123456789','0343244644','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-21 00:58:48'),('HCMUS','duydc','HCMUS-004','NVC','Mobile','Junior Mobile Developer','Đinh Công Duy','2000-06-01','dinhcongduy125@gmail.com','025728760','0335406888','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-21 03:11:33'),('HCMUS','duyna','HCMUS-003','NVC','Backend','Team Leader','Nguyễn Anh Duy','1998-09-26','naduy.hcmus@gmail.com','272683901','0948202709','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-21 02:02:32'),('HCMUS','duytv','HCMUS-005','NVC','Mobile','Junior Mobile Developer','Võ Tấn Duy','1998-01-01','duytv2907@gmail.com','123456789','0931852460','Gò Vấp, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('HCMUS','huylh','HCMUS-006','NVC','Backend','Junior Backend Developer','Lê Hoàng Huy','1998-01-01','hoanghuyit98@gmail.com','123456789','0325236999','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('VNG','admin','VNG-00001','CAMPUS','PMA','Team Leader','Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 05:47:37'),('VNG','admin1','VNG-002','CAMPUS','ZPI','Junior Mobile Developer','Võ Tấn Duy','1998-07-29','Duytv.2907@gmail.com','123456789','0948202709','Gò Vấp, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:48');
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

-- Dump completed on 2020-03-21 13:14:15
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
INSERT INTO `AdminUser` VALUES ('admin','$2a$10$E33ia.3uF2eloKbZXoG1ZuMfQGZu0x8GjEhQhEqrb9yg7MUlQl132','Nguyễn Anh Duy 123','0948202701','naduy.hcmus1@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',0,1,'2020-03-18 05:15:39'),('admin1','$2a$10$9OxClkoaV9bbNLc1g3MXC.3R5FyLnhdSYSsejQ6YPASk5UcRjGfD2','Nguyễn Quốc Đạt','0912345678','admin@gmail.com','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',1,1,'2020-03-18 15:10:16');
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

-- Dump completed on 2020-03-21 13:14:17
