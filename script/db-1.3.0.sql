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
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `userIP` varchar(32) DEFAULT '',
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
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `companyId` varchar(32) NOT NULL DEFAULT '',
  `username` varchar(32) NOT NULL DEFAULT '',
  `clientTime` bigint NOT NULL DEFAULT '0',
  `serverTime` bigint NOT NULL DEFAULT '0',
  `validTime` varchar(8) NOT NULL DEFAULT '00:00',
  `isOnTime` tinyint(1) NOT NULL DEFAULT '1',
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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
  `validLatitude` float NOT NULL DEFAULT '0',
  `validLongitude` float NOT NULL DEFAULT '0',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `maxAllowDistance` int NOT NULL DEFAULT '0',
  `usedWifi` tinyint(1) NOT NULL DEFAULT '0',
  `ip` varchar(32) NOT NULL DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`logId`)
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

-- Dump completed on 2020-03-19 23:21:19
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
INSERT INTO `CompanyUser` VALUES ('HCMUS','autth','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','haiau762@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','datnq','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','nguyenquocdat2511998@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','duydc','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','dinhcongduy125@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','duyna','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','duytv','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','duytv2907@gmail.com',0,1,'2020-03-18 04:34:24'),('HCMUS','huylh','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','hoanghuyit98@gmail.com',0,1,'2020-03-18 04:34:24'),('VNG','admin','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com',0,1,'2020-03-16 08:07:19'),('VNG','admin1','$2a$10$FpjbmtPQbpQWNN.MgKsDeuquTGz8/wrDM5m2kPeS1AX00HnJY35dK','naduy.hcmus@gmail.com',0,1,'2020-03-16 14:43:08');
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
INSERT INTO `EmployeeInfo` VALUES ('HCMUS','autth','HCMUS-002','NVC','React','Fresher Frontend Developer','Trương Thị Hải Âu','1998-01-01','haiau762@gmail.com','123456789','0342012299','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('HCMUS','datnq','HCMUS-00001','NVC','Vue','Fresher Frontend Developer','Nguyễn Quốc Đạt','1998-01-01','nguyenquocdat2511998@gmail.com','123456789','0343244644','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('HCMUS','duydc','HCMUS-004','NVC','Mobile','Junior Mobile Developer','Đinh Công Duy1','1998-05-01','dinhcongduy125@gmail.com','025728760','0335406888','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-19 15:12:51'),('HCMUS','duyna','HCMUS-003','NVC','Backend','Team Leader','Nguyễn Anh Duy','1998-09-27','naduy.hcmus@gmail.com','123456789','0948202709','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('HCMUS','duytv','HCMUS-005','NVC','Mobile','Junior Mobile Developer','Võ Tấn Duy','1998-01-01','duytv2907@gmail.com','123456789','0931852460','Gò Vấp, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('HCMUS','huylh','HCMUS-006','NVC','Backend','Junior Backend Developer','Lê Hoàng Huy','1998-01-01','hoanghuyit98@gmail.com','123456789','0325236999','TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:47'),('VNG','admin','VNG-00001','CAMPUS','PMA','Team Leader','Đinh Công Duy','2020-05-18','naduy.hcmus@gmail.com','025728760','0948202709','Quận 9, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 05:47:37'),('VNG','admin1','VNG-002','CAMPUS','ZPI','Junior Mobile Developer','Võ Tấn Duy','1998-07-29','Duytv.2907@gmail.com','123456789','0948202709','Gò Vấp, TPHCM','https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg','2020-03-18 04:59:48');
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

-- Dump completed on 2020-03-19 23:21:21
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

-- Dump completed on 2020-03-19 23:21:23
