-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 12, 2025 at 03:48 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sms`
--

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `attendanceID` int(11) NOT NULL,
  `userID` varchar(20) NOT NULL,
  `clockIn` datetime DEFAULT NULL,
  `clockOut` datetime DEFAULT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `attendance`
--

INSERT INTO `attendance` (`attendanceID`, `userID`, `clockIn`, `clockOut`, `date`) VALUES
(1, 'S001', '2025-05-21 00:52:40', '2025-05-21 01:18:05', '2025-05-21'),
(9, 'S001', '2025-05-23 16:54:20', '2025-05-23 16:56:26', '2025-05-23'),
(10, 'S001', '2025-05-24 01:37:11', '2025-05-24 01:37:25', '2025-05-24'),
(11, 'S001', '2025-05-27 17:19:23', '2025-05-27 17:27:49', '2025-05-27'),
(12, 'S002', '2025-05-27 17:23:33', '2025-05-27 17:25:10', '2025-05-27'),
(13, 'S001', '2025-05-29 06:38:39', '2025-05-29 12:33:45', '2025-05-29'),
(14, 'S002', '2025-05-29 14:05:57', '2025-05-29 14:07:07', '2025-05-29'),
(16, 'S002', '2025-05-31 17:11:32', '2025-05-31 17:35:25', '2025-05-31'),
(17, 'S001', '2025-06-12 09:38:39', '2025-06-12 09:39:23', '2025-06-12');

-- --------------------------------------------------------

--
-- Table structure for table `deduction`
--

CREATE TABLE `deduction` (
  `deductionID` int(11) NOT NULL,
  `staffID` int(11) NOT NULL,
  `deductionAmount` decimal(9,2) DEFAULT NULL,
  `deductionReason` varchar(225) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `fo`
--

CREATE TABLE `fo` (
  `userID` varchar(20) NOT NULL,
  `position` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fo`
--

INSERT INTO `fo` (`userID`, `position`) VALUES
('F001', 'Payroll Officer'),
('F002', 'Finance'),
('F003', 'Finance');

-- --------------------------------------------------------

--
-- Table structure for table `hr`
--

CREATE TABLE `hr` (
  `userID` varchar(20) NOT NULL,
  `position` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hr`
--

INSERT INTO `hr` (`userID`, `position`) VALUES
('H001', 'Hannah Fashihah binti Mohd Rashid');

-- --------------------------------------------------------

--
-- Table structure for table `leaverequest`
--

CREATE TABLE `leaverequest` (
  `requestID` int(11) NOT NULL,
  `userID` varchar(20) NOT NULL,
  `leaveType` varchar(20) DEFAULT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `reason` text DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Pending',
  `requestDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `durationType` varchar(10) DEFAULT NULL,
  `fromTime` time DEFAULT NULL,
  `toTime` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `leaverequest`
--

INSERT INTO `leaverequest` (`requestID`, `userID`, `leaveType`, `startDate`, `endDate`, `reason`, `status`, `requestDate`, `durationType`, `fromTime`, `toTime`) VALUES
(1, 'S001', 'Annual Leave', '2025-06-05', '2025-06-07', 'Holiday', 'Pending', '2025-05-28 16:00:00', NULL, NULL, NULL),
(3, 'S002', 'Annual Leave', '2025-06-20', '2025-06-22', 'Wedding', 'Pending', '2025-05-30 16:00:00', 'full', NULL, NULL),
(4, 'S002', 'Medical Leave', '2025-07-02', '2025-07-02', 'Check up', 'Pending', '2025-06-02 16:00:00', 'partial', '10:26:00', '12:26:00');

-- --------------------------------------------------------

--
-- Table structure for table `manager`
--

CREATE TABLE `manager` (
  `userID` varchar(20) NOT NULL,
  `position` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `manager`
--

INSERT INTO `manager` (`userID`, `position`) VALUES
('M001', 'Nur Sharina binti Shalathim'),
('M002', 'Sub Manager');

-- --------------------------------------------------------

--
-- Table structure for table `overtimerequest`
--

CREATE TABLE `overtimerequest` (
  `overtimeID` int(11) NOT NULL,
  `userID` varchar(20) NOT NULL,
  `otDate` date NOT NULL,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `reason` text DEFAULT NULL,
  `status` varchar(10) DEFAULT 'Pending',
  `reviewedBy` varchar(20) DEFAULT NULL,
  `reviewedDate` datetime DEFAULT NULL,
  `remarks` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `overtimerequest`
--

INSERT INTO `overtimerequest` (`overtimeID`, `userID`, `otDate`, `startTime`, `endTime`, `reason`, `status`, `reviewedBy`, `reviewedDate`, `remarks`) VALUES
(1, 'S002', '2025-06-05', '17:21:00', '18:21:00', 'To finish extra task', 'Approved', 'H001', '2025-06-05 15:23:34', 'Approved by HR'),
(2, 'S001', '2025-06-12', '17:30:00', '19:00:00', 'Needed extra time to submit task', 'Pending', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `payrun`
--

CREATE TABLE `payrun` (
  `payrunID` int(11) NOT NULL,
  `month` year(4) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(50) DEFAULT NULL,
  `totalSalary` decimal(10,2) DEFAULT NULL,
  `createdBy` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payrun`
--

INSERT INTO `payrun` (`payrunID`, `month`, `year`, `createdDate`, `status`, `totalSalary`, `createdBy`) VALUES
(1, '2001', 2023, '2025-05-19 17:51:10', 'Pending', 0.00, 'F001'),
(2, '2001', 2023, '2025-05-19 17:52:14', 'Pending', 0.00, 'F001'),
(3, '2001', 2023, '2025-05-19 17:52:39', 'Pending', 0.00, 'F001'),
(4, '2001', 2023, '2025-05-19 18:32:30', 'Pending', 0.00, 'F001'),
(5, '2001', 2023, '2025-05-19 18:49:35', 'Pending', 0.00, 'F001'),
(6, '2005', 2025, '2025-06-12 01:45:58', 'Pending', 0.00, 'Yasmin');

-- --------------------------------------------------------

--
-- Table structure for table `payrundetail`
--

CREATE TABLE `payrundetail` (
  `detailID` int(11) NOT NULL,
  `payrunID` int(11) DEFAULT NULL,
  `userID` varchar(20) DEFAULT NULL,
  `basicSalary` decimal(10,2) DEFAULT NULL,
  `overtimeHours` decimal(5,2) DEFAULT NULL,
  `overtimePay` decimal(10,2) DEFAULT NULL,
  `deductions` decimal(10,2) DEFAULT NULL,
  `netSalary` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payslip`
--

CREATE TABLE `payslip` (
  `payslipID` int(11) NOT NULL,
  `staffID` int(11) NOT NULL,
  `payslipSalaryAmount` decimal(10,2) NOT NULL,
  `payslipDeductionAmount` decimal(10,2) NOT NULL,
  `payslipOvertimePay` decimal(10,2) NOT NULL,
  `payslipNetPay` decimal(10,2) NOT NULL,
  `payslipMonth` date NOT NULL,
  `payslipCreatedAt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `salarycomponent`
--

CREATE TABLE `salarycomponent` (
  `componentID` int(11) NOT NULL,
  `detailID` int(11) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `userID` varchar(20) NOT NULL,
  `staffPosition` varchar(100) DEFAULT NULL,
  `staffPhoneno` varchar(20) DEFAULT NULL,
  `staffAddress` text DEFAULT NULL,
  `staffJoinedDate` date DEFAULT NULL,
  `staffGender` varchar(10) DEFAULT NULL,
  `staffDOB` date DEFAULT NULL,
  `staffMaritalStatus` varchar(20) DEFAULT NULL,
  `staffEmpType` varchar(50) DEFAULT NULL,
  `staffBank` varchar(100) DEFAULT NULL,
  `staffAccNo` varchar(30) DEFAULT NULL,
  `basicSalary` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`userID`, `staffPosition`, `staffPhoneno`, `staffAddress`, `staffJoinedDate`, `staffGender`, `staffDOB`, `staffMaritalStatus`, `staffEmpType`, `staffBank`, `staffAccNo`, `basicSalary`) VALUES
('S001', 'Senior IT', '011-23456789', 'Lot 53163, Taman Sri Putra 2, Kampung Tok Jembal, 2130, Kuala Nerus, Kuala Terengganu', '2020-04-15', 'Female', '2003-01-21', 'Single', 'Full-time', 'Bank Rakyat', '1234567890123', 3500.00),
('S002', 'Sales Representative', '01123346105', 'No.25, Jalan Batu Permata 12, Taman Permata, 75250, Melaka', '2025-05-14', 'Male', '2025-04-27', 'Married', 'Full-time', 'Bank Islam', '04015021761812', 2300.00);

-- --------------------------------------------------------

--
-- Table structure for table `staff_old`
--

CREATE TABLE `staff_old` (
  `staffID` int(11) NOT NULL,
  `staffPassword` varchar(20) NOT NULL,
  `staffRole` enum('General Staff','Finance Officer','Manager') NOT NULL,
  `staffName` varchar(50) NOT NULL,
  `staffFullname` varchar(255) NOT NULL,
  `staffEmail` varchar(50) NOT NULL,
  `staffPosition` varchar(50) NOT NULL,
  `staffPhoneno` varchar(15) NOT NULL,
  `staffAddress` varchar(255) NOT NULL,
  `staffJoinedDate` date DEFAULT NULL,
  `staffGender` varchar(10) DEFAULT NULL,
  `staffDOB` date DEFAULT NULL,
  `staffMaritalStatus` varchar(20) DEFAULT NULL,
  `staffEmpType` varchar(20) DEFAULT NULL,
  `staffBank` varchar(50) DEFAULT NULL,
  `staffAccNo` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff_old`
--

INSERT INTO `staff_old` (`staffID`, `staffPassword`, `staffRole`, `staffName`, `staffFullname`, `staffEmail`, `staffPosition`, `staffPhoneno`, `staffAddress`, `staffJoinedDate`, `staffGender`, `staffDOB`, `staffMaritalStatus`, `staffEmpType`, `staffBank`, `staffAccNo`) VALUES
(1, 'y45m1n', 'Finance Officer', 'Yasmin', 'Nurul Yasmin binti Nor Asri', 'yasminasri@gmail.com', 'Finance Officer', '01175245250', '11/7, Jalan Panglima Muda 21, Taman Datuk Panglima, 52300, Johor', '2020-05-04', 'Female', '1995-04-24', 'Female', 'Full-Time', 'RHB', '1267893456'),
(2, 'rosnaliza03', 'General Staff', 'Ros', 'Rosnaliza binti Rosman', 'ros03@gmail.com', 'General Staff', '0129876543', 'No.34, Blok A25, Jalan Periwira 17, Ipoh, Perak', '2024-08-01', 'Female', '2003-09-14', 'Single', 'Contract', 'CIMB', '764125789'),
(3, 'alhamdulillah308', 'Manager', 'Sharina', 'Nur Sharina binti Shalathim', 'sharina308@gmail.com', 'Manager', '0178956386', 'No. 13, Jalan Ukay Perdana, Taman Ukay Perdana, 68000, Selangor ', '2021-07-21', 'Female', '1994-08-30', 'Single', 'Full-Time', 'Affin Bank', '1567245987'),
(7, 'kamalhensem', 'General Staff', 'Kamal', 'Kamal Adli bin Ahmad', 'kamal177@gmail.com', 'Marketing', '0127357392', '17/1, Jalan Mutiara Indah, Taman Mutiara, 6800, Selangor', '2025-01-17', 'Male', '2025-01-01', 'Single', 'Full-Time', 'Maybank', '1678943261'),
(8, 'Hann25', 'General Staff', 'Hannah', 'Hannah Fashihah binti Mohd Rashid', 'hannahmohd973@gmail.com', 'IT Staff', '01158536105', 'NO.38, JALAN POKOK MANGGA 25\r\nTAMAN POKOK MANGGA', '2024-09-17', 'Female', '2003-07-25', 'Single', 'Full-Time', 'Bank Islam', '04015021761812'),
(9, 'salam', 'General Staff', 'Salam', 'Ahmad Salam bin Hamid', 'salam@gmail.com', 'Sales Representative', '01158536105', 'no.12', '2025-01-12', 'Male', '2025-01-07', 'Married', 'Full-Time', 'Bank Islam', '04015672453319'),
(10, 'aisyah', 'General Staff', 'Yasmin', 'Aisyah binti Abdul', 'aisyah@gmail.com', 'Staff', '012-3456789', 'no.1', '2025-01-01', 'Female', '2025-01-01', 'Married', 'Contract', 'Bank Islam', '04015672453319'),
(11, 'aliabu', 'General Staff', 'Ali', 'Ali bin Abu', 'aliabu@gmail.com', 'Intern', '01172425450', 'Jalan Melati Felda Ulu Dengar 86000 Kluang Johor', '2025-01-08', 'Male', '2025-01-07', 'Single', 'Contract', 'Maybank', '1678942456');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userID` varchar(20) NOT NULL,
  `role` enum('Finance Officer','Staff','Human Resource','Manager') NOT NULL,
  `username` varchar(20) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `role`, `username`, `fullname`, `password`, `email`) VALUES
('F001', 'Finance Officer', 'Yasmin', 'Nurul Yasmin Nor Asri', '$2a$10$Pg4qMjA2UNb90hx2IkI7.uEhbSCzHWfzs0z3pS6F9hdyw3uE.bPZW', 'yasmin@gmail.com'),
('F002', 'Finance Officer', 'Alia', 'Nur Alia Arshad', '$2a$10$DSPrIi2i6sRdyrDybdlLS.YYfvkifiKG8UHVqOY3TOb5Hd3JAihEa', 'alia01@gmail.com'),
('F003', 'Finance Officer', 'Syafiq', 'Syafiq Kyle', '$2a$10$zxBHWyvD3sEj7JWZiSk8k.A44fXiU6RpbbIQSMX7KjaPmozFEalFu', 'syafiq99@gmail.com'),
('H001', 'Human Resource', 'Hannah', 'Hannah Fashihah Mohd Rashid', '$2a$10$tP2xW8qgGFFe69pDxob0wuoSFrnsaTE0DV7Hq54NRT996KTfbMFHO', 'hannah@gmail.com'),
('M001', 'Manager', 'Sharina', 'Nur Sharina Shalathim', '$2a$10$nCXTK0GYzsCqGHCGZ910Ee56RRY1BYBCRKXNA4.kb3YBqNSBgkzGO', 'sharina@gmail.com'),
('M002', 'Manager', 'Hamid', 'Hamid Anuar', '$2a$10$gfqcnBwRILsQNiyhbN.QMeCtO23KRaTV/x7G31OQ.q5QL3O8v4ksi', 'hamid@gmail.com'),
('S001', 'Staff', 'Ros', 'Rosnaliza Rosman', '$2a$10$BC6dkKZp6wGNLwdnyuoSueUfuKRk1HQInWVvCHS6qWy0kCIUOOOsO', 'rosnaliza03@gmail.com'),
('S002', 'Staff', 'Ali', 'Muhammad Ali Rahman', '$2a$10$fgsSOpd6LFqlqKLdWMU8eOA/uq7hR1G1xBCvrKVdnXomqLSLv09HO', 'muhdali@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`attendanceID`),
  ADD UNIQUE KEY `userID` (`userID`,`date`);

--
-- Indexes for table `deduction`
--
ALTER TABLE `deduction`
  ADD PRIMARY KEY (`deductionID`),
  ADD UNIQUE KEY `staffID` (`staffID`);

--
-- Indexes for table `fo`
--
ALTER TABLE `fo`
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `hr`
--
ALTER TABLE `hr`
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `leaverequest`
--
ALTER TABLE `leaverequest`
  ADD PRIMARY KEY (`requestID`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `manager`
--
ALTER TABLE `manager`
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `overtimerequest`
--
ALTER TABLE `overtimerequest`
  ADD PRIMARY KEY (`overtimeID`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `payrun`
--
ALTER TABLE `payrun`
  ADD PRIMARY KEY (`payrunID`);

--
-- Indexes for table `payrundetail`
--
ALTER TABLE `payrundetail`
  ADD PRIMARY KEY (`detailID`),
  ADD KEY `payrunID` (`payrunID`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `payslip`
--
ALTER TABLE `payslip`
  ADD PRIMARY KEY (`payslipID`),
  ADD UNIQUE KEY `staffID` (`staffID`);

--
-- Indexes for table `salarycomponent`
--
ALTER TABLE `salarycomponent`
  ADD PRIMARY KEY (`componentID`),
  ADD KEY `detailID` (`detailID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `staff_old`
--
ALTER TABLE `staff_old`
  ADD PRIMARY KEY (`staffID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`),
  ADD KEY `idx_username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `attendanceID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `deduction`
--
ALTER TABLE `deduction`
  MODIFY `deductionID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `leaverequest`
--
ALTER TABLE `leaverequest`
  MODIFY `requestID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `overtimerequest`
--
ALTER TABLE `overtimerequest`
  MODIFY `overtimeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `payrun`
--
ALTER TABLE `payrun`
  MODIFY `payrunID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `payrundetail`
--
ALTER TABLE `payrundetail`
  MODIFY `detailID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payslip`
--
ALTER TABLE `payslip`
  MODIFY `payslipID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `salarycomponent`
--
ALTER TABLE `salarycomponent`
  MODIFY `componentID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `staff_old`
--
ALTER TABLE `staff_old`
  MODIFY `staffID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `attendance`
--
ALTER TABLE `attendance`
  ADD CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Constraints for table `deduction`
--
ALTER TABLE `deduction`
  ADD CONSTRAINT `deduction_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff_old` (`staffID`);

--
-- Constraints for table `fo`
--
ALTER TABLE `fo`
  ADD CONSTRAINT `fo_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Constraints for table `hr`
--
ALTER TABLE `hr`
  ADD CONSTRAINT `hr_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Constraints for table `leaverequest`
--
ALTER TABLE `leaverequest`
  ADD CONSTRAINT `leaverequest_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Constraints for table `manager`
--
ALTER TABLE `manager`
  ADD CONSTRAINT `manager_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Constraints for table `overtimerequest`
--
ALTER TABLE `overtimerequest`
  ADD CONSTRAINT `overtimerequest_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Constraints for table `payrundetail`
--
ALTER TABLE `payrundetail`
  ADD CONSTRAINT `payrundetail_ibfk_1` FOREIGN KEY (`payrunID`) REFERENCES `payrun` (`payrunID`),
  ADD CONSTRAINT `payrundetail_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);

--
-- Constraints for table `salarycomponent`
--
ALTER TABLE `salarycomponent`
  ADD CONSTRAINT `salarycomponent_ibfk_1` FOREIGN KEY (`detailID`) REFERENCES `payrundetail` (`detailID`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
