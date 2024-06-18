-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 18, 2024 at 12:06 PM
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
-- Database: `budgetbuddies`
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
(1, 'f', 10, '2024-06-14', 2),
(2, 'HOTEL', 500, '2024-06-15', 2),
(3, 'HOTEL', 500, '2024-06-15', 2),
(4, 'sf', 2424, '2024-06-15', 2),
(5, 'csc', 400, '2024-06-15', 2),
(6, 'bj', 6966, '2024-06-15', 2),
(7, 'bbi', 86, '2024-06-15', 2),
(8, 'fsdef', 332, '2024-06-15', 2),
(10, 'sagapo', 34, '2024-06-15', 2),
(11, 'dc', 323, '2024-06-15', 2),
(12, 'fca', 32, '2024-06-15', 2),
(14, 'cfs', 323, '2024-06-15', 2),
(15, 'travel', 800, '2024-06-15', 2),
(18, 'bb', 86, '2024-06-15', 2),
(19, 'drinks', 80, '2024-06-16', 2),
(21, 'Hotel', 90, '2024-06-16', 2),
(22, 'hotel', 200, '2024-06-16', 2),
(23, 'flight', 300, '2024-06-16', 8),
(24, 'taxi', 20, '2024-06-16', 2),
(25, 'food', 40, '2024-06-16', 8),
(26, 'HOTEL', 2000, '2024-06-16', 2),
(27, 'hotel', 2000, '2024-06-16', 9),
(28, 'flight', 500, '2024-06-16', 9),
(29, 'hotel', 1000, '2024-06-16', 2),
(30, 'flight', 300, '2024-06-16', 9),
(31, 'mpanio', 300, '2024-06-16', 2),
(32, 'tsai', 900, '2024-06-16', 9),
(33, 'tower', 30, '2024-06-16', 2),
(34, 'hotel', 300, '2024-06-16', 2),
(35, 'hotel', 300, '2024-06-16', 9),
(36, 'eleni', 300, '2024-06-16', 9),
(40, 'fbwei', 23723, '2024-06-16', 2),
(41, '1', 21, '2024-06-16', 2),
(42, '2', 20, '2024-06-16', 2),
(43, '1', 10, '2024-06-16', 2),
(45, 'cs', 10, '2024-06-16', 2),
(47, '1', 10, '2024-06-16', 2),
(49, 'fai', 0, '2024-06-16', 2),
(50, 'test', 20, '2024-06-17', 2),
(51, 'kroketes', 50, '2024-06-17', 11),
(52, 'ko', 0, '2024-06-17', 10),
(53, 'mpam', 0, '2024-06-17', 10),
(54, 'nextt', 100, '2024-06-17', 2),
(57, 'd', 0, '2024-06-17', 9),
(58, 'sd', 0, '2024-06-17', 12),
(59, 'cac', 0, '2024-06-17', 12),
(60, 'sdcs', 10, '2024-06-17', 12),
(61, 'asdc', 100, '2024-06-17', 12),
(62, 'kopa', 10, '2024-06-17', 12),
(64, 'cwds', 100, '2024-06-18', 12),
(66, 'dscvd', 100, '2024-06-18', 12),
(67, 'vrefv', 40, '2024-06-18', 9);

-- --------------------------------------------------------

--
-- Table structure for table `expense_users(paidfor)`
--

CREATE TABLE `expense_users(paidfor)` (
  `userID` int(11) NOT NULL,
  `expenseID` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
('2ih690', 'fuck', '', 2, 0),
('49I2nw', 'eleni', '', 2, 0),
('4dXUN1', 'maroko', '', 2, 0),
('7eDjW5', 'dd', '', 12, 0),
('7KIXeb', 'dcs', '', 12, 0),
('8F49Ln', 'eleni', '', 2, 0),
('a86Zl6', 'oust', '', 10, 0),
('aL9DKE', 'new', '', 8, 0),
('blZt9U', 'lefta', '', 12, 110),
('CgRvO5', 'papia', '', 9, 0),
('D2WEY5', 'ts', '', 2, 0),
('d8zp5R', 'NEW YORK', '', 2, 0),
('gltCOI', 'mmmm', 'nnn', 5, 0),
('GpVNGJ', 'fccswdcf', '', 12, 140),
('guUwMY', 'dokimi', 'fagita', 2, 0),
('gY6hHQ', 'fuckyou', '', 2, 0),
('hfA71B', 'zouzounia', '', 2, 100),
('hMNtgm', 'dc', '', 12, 0),
('hqfZxq', 'mpp', '', 10, 0),
('hY4F1F', 'test', '', 2, 0),
('iBO4QM', 'dd', '', 12, 0),
('kuK37y', 'mpa', '', 1, 0),
('kuwH7E', 'sgfayd', '', 6, 0),
('N40Kv9', 'sangai', '', 9, 0),
('NLRn8x', 'DEN EXOYME CONTENT', 'BARIEMAI', 7, 0),
('pOn4DM', 'zom', '', 2, -100),
('Q0XfSf', 'paris', '', 8, 0),
('RhIPMy', 'test', '', 2, 0),
('rZB77T', 'cdascsad', '', 12, 300),
('Tii5jd', 'paris', '', 2, 0),
('tkI6i9', 'pp', '', 12, 0),
('zN14nf', 'xqx', '', 12, 10),
('ZtEY2o', 'delete', '', 2, 0),
('ZXFY4Q', 'panel', '', 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `teams_users`
--

CREATE TABLE `teams_users` (
  `userID` int(11) NOT NULL,
  `teamID` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `team_expenses`
--

CREATE TABLE `team_expenses` (
  `teamID` varchar(6) NOT NULL,
  `expenseID` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(1, 'eleni', 'test'),
(2, 'eleni', 'tsotsou'),
(3, 'maria', 'mentaki'),
(4, 'maria', 'mentaki'),
(5, 'm', 'k'),
(6, 'evagelia', '12345'),
(7, 'PAOK', '1926'),
(8, 'el', 'ts'),
(9, 'ma', 'me'),
(10, 'tsoko', 'eleni'),
(11, 'elena', 'kris'),
(12, 'zoi', 'douka');

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
-- Indexes for dumped tables
--

--
-- Indexes for table `balance`
--
ALTER TABLE `balance`
  ADD UNIQUE KEY `unique_balance` (`teamID`,`win`,`loss`),
  ADD KEY `team` (`teamID`),
  ADD KEY `balance_win_fk` (`win`),
  ADD KEY `balance_loss_fk` (`loss`),
  ADD KEY `exid` (`expenseID`);

--
-- Indexes for table `expense`
--
ALTER TABLE `expense`
  ADD PRIMARY KEY (`expenseID`),
  ADD KEY `paying` (`payer`);

--
-- Indexes for table `expense_users(paidfor)`
--
ALTER TABLE `expense_users(paidfor)`
  ADD KEY `us_ex` (`userID`),
  ADD KEY `ex_us` (`expenseID`);

--
-- Indexes for table `owes`
--
ALTER TABLE `owes`
  ADD PRIMARY KEY (`teamID`,`winID`,`lossID`);

--
-- Indexes for table `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`teamID`),
  ADD KEY `group_admin` (`admin`);

--
-- Indexes for table `teams_users`
--
ALTER TABLE `teams_users`
  ADD KEY `members` (`teamID`),
  ADD KEY `member` (`userID`);

--
-- Indexes for table `team_expenses`
--
ALTER TABLE `team_expenses`
  ADD KEY `team_ex` (`teamID`),
  ADD KEY `expanse` (`expenseID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `user_team_totals`
--
ALTER TABLE `user_team_totals`
  ADD PRIMARY KEY (`userID`,`teamID`),
  ADD KEY `user_team_totals_fk_team` (`teamID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `expense`
--
ALTER TABLE `expense`
  MODIFY `expenseID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `balance`
--
ALTER TABLE `balance`
  ADD CONSTRAINT `balance_loss_fk` FOREIGN KEY (`loss`) REFERENCES `users` (`userID`),
  ADD CONSTRAINT `balance_team_fk` FOREIGN KEY (`teamID`) REFERENCES `team` (`teamID`),
  ADD CONSTRAINT `balance_win_fk` FOREIGN KEY (`win`) REFERENCES `users` (`userID`),
  ADD CONSTRAINT `exid` FOREIGN KEY (`expenseID`) REFERENCES `expense` (`expenseID`) ON DELETE CASCADE,
  ADD CONSTRAINT `team` FOREIGN KEY (`teamID`) REFERENCES `team` (`teamID`);

--
-- Constraints for table `expense`
--
ALTER TABLE `expense`
  ADD CONSTRAINT `paying` FOREIGN KEY (`payer`) REFERENCES `users` (`userID`);

--
-- Constraints for table `expense_users(paidfor)`
--
ALTER TABLE `expense_users(paidfor)`
  ADD CONSTRAINT `ex_us` FOREIGN KEY (`expenseID`) REFERENCES `expense` (`expenseID`) ON DELETE CASCADE,
  ADD CONSTRAINT `us_ex` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`);

--
-- Constraints for table `team`
--
ALTER TABLE `team`
  ADD CONSTRAINT `group_admin` FOREIGN KEY (`admin`) REFERENCES `users` (`userID`);

--
-- Constraints for table `teams_users`
--
ALTER TABLE `teams_users`
  ADD CONSTRAINT `member` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`),
  ADD CONSTRAINT `members` FOREIGN KEY (`teamID`) REFERENCES `team` (`teamID`);

--
-- Constraints for table `team_expenses`
--
ALTER TABLE `team_expenses`
  ADD CONSTRAINT `expanse` FOREIGN KEY (`expenseID`) REFERENCES `expense` (`expenseID`) ON DELETE CASCADE,
  ADD CONSTRAINT `team_ex` FOREIGN KEY (`teamID`) REFERENCES `team` (`teamID`);

--
-- Constraints for table `user_team_totals`
--
ALTER TABLE `user_team_totals`
  ADD CONSTRAINT `user_team_totals_fk_team` FOREIGN KEY (`teamID`) REFERENCES `team` (`teamID`),
  ADD CONSTRAINT `user_team_totals_fk_user` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
