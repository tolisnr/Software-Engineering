-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 19, 2024 at 01:13 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `budgetbuddy`
--

-- --------------------------------------------------------

--
-- Table structure for table `balance`
--

CREATE TABLE `balance` (
  `balanceID` varchar(100) NOT NULL,
  `teamID` varchar(6) NOT NULL,
  `expenseID` int(100) NOT NULL,
  `win` int(11) NOT NULL,
  `loss` int(11) NOT NULL,
  `amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `balance`
--

INSERT INTO `balance` (`balanceID`, `teamID`, `expenseID`, `win`, `loss`, `amount`) VALUES
('b137fff8-2dc5-11ef-8e49-ac198eff75f2', 'TJI4kr', 17, 1, 2, -30),
('b138e5a5-2dc5-11ef-8e49-ac198eff75f2', 'TJI4kr', 17, 1, 3, -30);

-- --------------------------------------------------------

--
-- Table structure for table `expense`
--

CREATE TABLE `expense` (
  `expenseID` int(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `amount` double NOT NULL,
  `date` date NOT NULL,
  `payer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `expense`
--

INSERT INTO `expense` (`expenseID`, `title`, `amount`, `date`, `payer`) VALUES
(1, 'test1', 200, '2024-06-19', 1),
(2, 'test2', 200, '2024-06-19', 1),
(3, 'test3', 200, '2024-06-19', 1),
(4, 'test4', 200, '2024-06-19', 1),
(5, 'test6', 200, '2024-06-19', 1),
(6, 'test', 200, '2024-06-19', 2),
(7, 'test8', 200, '2024-06-19', 2),
(8, 'test9', 90, '2024-06-19', 1),
(13, 'test14', 100, '2024-06-19', 2),
(14, 'test100', 80, '2024-06-19', 1),
(15, 'test101', 60, '2024-06-19', 1),
(16, 'test103', 60, '2024-06-19', 1),
(17, 'test105', 60, '2024-06-19', 1);

-- --------------------------------------------------------

--
-- Table structure for table `expense_users_paidfor`
--

CREATE TABLE `expense_users_paidfor` (
  `userID` int(11) NOT NULL,
  `expenseID` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `expense_users_paidfor`
--

INSERT INTO `expense_users_paidfor` (`userID`, `expenseID`) VALUES
(1, 1),
(2, 1),
(1, 2),
(2, 2),
(1, 3),
(2, 3),
(1, 4),
(2, 4),
(1, 5),
(2, 5),
(1, 6),
(2, 6),
(1, 7),
(2, 7),
(1, 8),
(2, 8),
(3, 9),
(2, 9),
(1, 9),
(1, 10),
(2, 10),
(1, 11),
(1, 12),
(3, 12),
(1, 13),
(2, 13),
(1, 14),
(3, 14),
(3, 15),
(1, 15),
(2, 15),
(1, 16),
(3, 16),
(2, 16),
(1, 17),
(2, 17),
(3, 17);

-- --------------------------------------------------------

--
-- Table structure for table `owes`
--

CREATE TABLE `owes` (
  `teamID` varchar(6) NOT NULL,
  `lossID` int(11) NOT NULL,
  `winID` int(11) NOT NULL,
  `Amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `owes`
--

INSERT INTO `owes` (`teamID`, `lossID`, `winID`, `Amount`) VALUES
('0giRvk', 1, 2, -63.333333333333336),
('0giRvk', 2, 1, 200),
('5J7rl4', 1, 2, 3.3333333333333357),
('5J7rl4', 1, 3, 0),
('5J7rl4', 2, 1, -50),
('5J7rl4', 2, 3, 0.000000000000007105427357601002),
('5J7rl4', 3, 1, 0),
('5J7rl4', 3, 2, -46.666666666666664),
('fC003j', 1, 2, 66.66666666666666),
('fC003j', 1, 3, 0),
('fC003j', 2, 1, 0),
('fC003j', 2, 3, 0),
('fC003j', 3, 1, 0),
('fC003j', 3, 2, -33.333333333333336),
('gLZVLH', 1, 2, 36.66666666666666),
('gLZVLH', 1, 3, 0),
('gLZVLH', 2, 1, 25),
('gLZVLH', 2, 3, 0),
('gLZVLH', 3, 1, 30),
('gLZVLH', 3, 2, -63.333333333333336),
('IATV1O', 1, 2, 0),
('IATV1O', 1, 3, 0),
('IATV1O', 2, 1, 0),
('IATV1O', 2, 3, 0),
('IATV1O', 3, 1, 50),
('IATV1O', 3, 2, 0),
('jVJrGB', 1, 2, -30),
('jVJrGB', 2, 1, 0),
('otnZ6i', 1, 2, -63.333333333333336),
('otnZ6i', 2, 1, 100),
('pkBjJx', 1, 2, 30),
('pkBjJx', 1, 3, 0),
('pkBjJx', 2, 1, 10),
('pkBjJx', 2, 3, 0),
('pkBjJx', 3, 1, 10),
('pkBjJx', 3, 2, 30),
('Sxw5jO', 1, 2, 0),
('Sxw5jO', 1, 3, 30),
('Sxw5jO', 2, 1, 10),
('Sxw5jO', 2, 3, 30),
('Sxw5jO', 3, 1, 10),
('Sxw5jO', 3, 2, 0),
('TJI4kr', 1, 2, 30),
('TJI4kr', 1, 3, 0),
('TJI4kr', 2, 1, 10),
('TJI4kr', 2, 3, 0),
('TJI4kr', 3, 1, 10),
('TJI4kr', 3, 2, 30),
('vSPcZW', 1, 2, -63.333333333333336),
('vSPcZW', 2, 1, -50),
('YInbcU', 1, 2, -63.333333333333336),
('YInbcU', 2, 1, 100);

-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE `team` (
  `teamID` varchar(6) NOT NULL,
  `title` varchar(100) NOT NULL,
  `category` varchar(100) NOT NULL,
  `admin` int(11) NOT NULL,
  `total` double NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `team`
--

INSERT INTO `team` (`teamID`, `title`, `category`, `admin`, `total`) VALUES
('TJI4kr', 'dokimi', '', 1, 60);

-- --------------------------------------------------------

--
-- Table structure for table `teams_users`
--

CREATE TABLE `teams_users` (
  `userID` int(11) NOT NULL,
  `teamID` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teams_users`
--

INSERT INTO `teams_users` (`userID`, `teamID`) VALUES
(1, 'TJI4kr'),
(2, 'TJI4kr'),
(3, 'TJI4kr');

-- --------------------------------------------------------

--
-- Table structure for table `team_expenses`
--

CREATE TABLE `team_expenses` (
  `teamID` varchar(6) NOT NULL,
  `expenseID` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `team_expenses`
--

INSERT INTO `team_expenses` (`teamID`, `expenseID`) VALUES
('TJI4kr', 17);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `username`, `password`) VALUES
(1, 'eleni', 'tsotsou'),
(2, 'athina', 'papa'),
(3, 'zoi', 'douka');

-- --------------------------------------------------------

--
-- Table structure for table `user_team_totals`
--

CREATE TABLE `user_team_totals` (
  `userID` int(11) NOT NULL,
  `teamID` varchar(6) NOT NULL,
  `mytotal` double NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_team_totals`
--

INSERT INTO `user_team_totals` (`userID`, `teamID`, `mytotal`) VALUES
(1, 'TJI4kr', 20),
(2, 'TJI4kr', 20),
(3, 'TJI4kr', 20);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `expense`
--
ALTER TABLE `expense`
  ADD PRIMARY KEY (`expenseID`);

--
-- Indexes for table `owes`
--
ALTER TABLE `owes`
  ADD PRIMARY KEY (`teamID`,`lossID`,`winID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `expense`
--
ALTER TABLE `expense`
  MODIFY `expenseID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
