-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 18, 2020 at 07:32 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `HTCC_Admin`
--
CREATE DATABASE IF NOT EXISTS `HTCC_Admin` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `HTCC_Admin`;

-- --------------------------------------------------------

--
-- Table structure for table `AdminUser`
--

CREATE TABLE `AdminUser` (
  `username` varchar(32) NOT NULL,
  `password` varchar(256) NOT NULL DEFAULT '',
  `fullName` varchar(256) CHARACTER SET utf8 NOT NULL,
  `phoneNumber` varchar(20) NOT NULL DEFAULT '',
  `email` varchar(128) NOT NULL DEFAULT '',
  `avatar` varchar(256) NOT NULL DEFAULT 'https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg',
  `role` int(11) NOT NULL DEFAULT 1,
  `status` int(11) NOT NULL DEFAULT 1,
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `AdminUser`
--

INSERT INTO `AdminUser` (`username`, `password`, `fullName`, `phoneNumber`, `email`, `avatar`, `role`, `status`, `updDate`) VALUES
('admin', '$2a$10$9OxClkoaV9bbNLc1g3MXC.3R5FyLnhdSYSsejQ6YPASk5UcRjGfD2', 'Nguyễn Anh Duy', '0948202709', 'naduy.hcmus@gmail.com', 'https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg', 0, 1, '2020-03-15 15:54:13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `AdminUser`
--
ALTER TABLE `AdminUser`
  ADD PRIMARY KEY (`username`);
--
-- Database: `HTCC_Company`
--
CREATE DATABASE IF NOT EXISTS `HTCC_Company` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
USE `HTCC_Company`;

-- --------------------------------------------------------

--
-- Table structure for table `CompanyUser`
--

CREATE TABLE `CompanyUser` (
  `companyId` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(256) NOT NULL,
  `email` varchar(128) NOT NULL,
  `role` int(11) NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 1,
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `CompanyUser`
--

INSERT INTO `CompanyUser` (`companyId`, `username`, `password`, `email`, `role`, `status`, `updDate`) VALUES
('VNG', 'admin', '$2a$10$SXqCEoTNNrRcY8bSfGDEfumjJw32t9N5WgI47g.dDJkSSnxePzDPO', 'naduy.hcmus@gmail.com', 0, 1, '2020-03-14 14:33:48');

-- --------------------------------------------------------

--
-- Table structure for table `EmployeeInfo`
--

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
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `EmployeeInfo`
--

INSERT INTO `EmployeeInfo` (`companyId`, `username`, `employeeId`, `officeId`, `department`, `fullName`, `birthDate`, `email`, `identityCardNo`, `phoneNumber`, `address`, `avatar`, `updDate`) VALUES
('VNG', 'admin', 'VNG-00001', 'CAMPUS', 'PMA', 'Nguyễn Anh Duy', '1998-09-28', 'naduy.hcmus@gmail.com', '272683901', '0948202709', 'Quận 9, TPHCM', 'https://i.pinimg.com/originals/0d/36/e7/0d36e7a476b06333d9fe9960572b66b9.jpg', '2020-03-13 13:56:53');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `CompanyUser`
--
ALTER TABLE `CompanyUser`
  ADD PRIMARY KEY (`companyId`,`username`);

--
-- Indexes for table `EmployeeInfo`
--
ALTER TABLE `EmployeeInfo`
  ADD PRIMARY KEY (`companyId`,`username`),
  ADD UNIQUE KEY `employeeId_UNIQUE` (`employeeId`),
  ADD KEY `IDX_employeeId` (`employeeId`);
--
-- Database: `HTCC_Log`
--
CREATE DATABASE IF NOT EXISTS `HTCC_Log` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `HTCC_Log`;

-- --------------------------------------------------------

--
-- Table structure for table `ApiLog202003`
--

CREATE TABLE `ApiLog202003` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ApiLog202004`
--

CREATE TABLE `ApiLog202004` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ApiLog202005`
--

CREATE TABLE `ApiLog202005` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ApiLog202006`
--

CREATE TABLE `ApiLog202006` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ApiLog202007`
--

CREATE TABLE `ApiLog202007` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ApiLog202008`
--

CREATE TABLE `ApiLog202008` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ApiLog202009`
--

CREATE TABLE `ApiLog202009` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ApiLog202010`
--

CREATE TABLE `ApiLog202010` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ApiLog202011`
--

CREATE TABLE `ApiLog202011` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ApiLog202012`
--

CREATE TABLE `ApiLog202012` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `RequestLog202003`
--

CREATE TABLE `RequestLog202003` (
  `logId` int(11) NOT NULL,
  `ymd` bigint(20) NOT NULL DEFAULT 0,
  `serviceId` int(11) NOT NULL DEFAULT 0,
  `requestURL` varchar(256) NOT NULL DEFAULT '',
  `method` varchar(8) NOT NULL DEFAULT '',
  `path` varchar(256) NOT NULL DEFAULT '',
  `params` varchar(256) NOT NULL DEFAULT '',
  `body` varchar(4096) NOT NULL DEFAULT '',
  `response` text NOT NULL,
  `returnCode` int(11) NOT NULL DEFAULT 1,
  `requestTime` bigint(20) NOT NULL DEFAULT 0,
  `responseTime` bigint(20) NOT NULL DEFAULT 0,
  `userIP` varchar(32) DEFAULT '',
  `updDate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `RequestLog202003`
--

INSERT INTO `RequestLog202003` (`logId`, `ymd`, `serviceId`, `requestURL`, `method`, `path`, `params`, `body`, `response`, `returnCode`, `requestTime`, `responseTime`, `userIP`, `updDate`) VALUES
(1, 20200319, 1, '1', 'POST', '/api/gateway/public/login', '{}', '{clientId=1.0, companyId=VNG, password=123.0, username=admin}', '{\"returnCode\":0,\"returnMessage\":\"Hệ thống đang có lỗi. Vui lòng thử lại sau\",\"data\":\"{returnCode=0, returnMessage=Hệ thống đang có lỗi. Vui lòng thử lại sau, data=Internal Server Error}\"}', 0, 1584556312153, 1584556312153, '0:0:0:0:0:0:0:1', '2020-03-18 18:31:52');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ApiLog202003`
--
ALTER TABLE `ApiLog202003`
  ADD PRIMARY KEY (`logId`);

--
-- Indexes for table `ApiLog202004`
--
ALTER TABLE `ApiLog202004`
  ADD PRIMARY KEY (`logId`);

--
-- Indexes for table `ApiLog202005`
--
ALTER TABLE `ApiLog202005`
  ADD PRIMARY KEY (`logId`);

--
-- Indexes for table `ApiLog202006`
--
ALTER TABLE `ApiLog202006`
  ADD PRIMARY KEY (`logId`);

--
-- Indexes for table `ApiLog202007`
--
ALTER TABLE `ApiLog202007`
  ADD PRIMARY KEY (`logId`);

--
-- Indexes for table `ApiLog202008`
--
ALTER TABLE `ApiLog202008`
  ADD PRIMARY KEY (`logId`);

--
-- Indexes for table `ApiLog202009`
--
ALTER TABLE `ApiLog202009`
  ADD PRIMARY KEY (`logId`);

--
-- Indexes for table `ApiLog202010`
--
ALTER TABLE `ApiLog202010`
  ADD PRIMARY KEY (`logId`);

--
-- Indexes for table `ApiLog202011`
--
ALTER TABLE `ApiLog202011`
  ADD PRIMARY KEY (`logId`);

--
-- Indexes for table `ApiLog202012`
--
ALTER TABLE `ApiLog202012`
  ADD PRIMARY KEY (`logId`);

--
-- Indexes for table `RequestLog202003`
--
ALTER TABLE `RequestLog202003`
  ADD PRIMARY KEY (`logId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ApiLog202003`
--
ALTER TABLE `ApiLog202003`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ApiLog202004`
--
ALTER TABLE `ApiLog202004`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ApiLog202005`
--
ALTER TABLE `ApiLog202005`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ApiLog202006`
--
ALTER TABLE `ApiLog202006`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ApiLog202007`
--
ALTER TABLE `ApiLog202007`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ApiLog202008`
--
ALTER TABLE `ApiLog202008`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ApiLog202009`
--
ALTER TABLE `ApiLog202009`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ApiLog202010`
--
ALTER TABLE `ApiLog202010`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ApiLog202011`
--
ALTER TABLE `ApiLog202011`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ApiLog202012`
--
ALTER TABLE `ApiLog202012`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `RequestLog202003`
--
ALTER TABLE `RequestLog202003`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
