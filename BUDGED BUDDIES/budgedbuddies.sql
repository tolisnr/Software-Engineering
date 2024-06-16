-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 16, 2024 at 06:05 PM
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
-- Database: `budgedbuddies`
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
('a2161ac9-2bf9-11ef-a93a-ac198eff75f2', 'd8zp5R', 37, 2, 8, 3943.5),
('3037cfdb-2be7-11ef-a93a-ac198eff75f2', 'Tii5jd', 36, 2, 8, 100),
('3039cfc4-2be7-11ef-a93a-ac198eff75f2', 'Tii5jd', 36, 2, 9, 100);

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
(37, 'mpa', 7887, '2024-06-16', 2);

-- --------------------------------------------------------

--
-- Table structure for table `expense_users(paidfor)`
--

CREATE TABLE `expense_users(paidfor)` (
  `userID` int(11) NOT NULL,
  `expenseID` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `expense_users(paidfor)`
--

INSERT INTO `expense_users(paidfor)` (`userID`, `expenseID`) VALUES
(8, 10),
(2, 11),
(8, 11),
(8, 12),
(8, 14),
(2, 15),
(8, 15),
(8, 18),
(2, 19),
(8, 19),
(8, 21),
(2, 21),
(2, 22),
(8, 22),
(2, 23),
(8, 23),
(2, 24),
(8, 24),
(2, 25),
(8, 25),
(8, 26),
(2, 26),
(8, 27),
(9, 27),
(8, 28),
(9, 28),
(2, 29),
(8, 29),
(9, 29),
(2, 30),
(8, 30),
(9, 30),
(2, 31),
(8, 31),
(9, 31),
(2, 32),
(8, 32),
(2, 33),
(9, 33),
(2, 34),
(8, 34),
(2, 35),
(9, 35),
(8, 36),
(9, 36),
(2, 37),
(8, 37);

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
('3pMF1o', 8, 2, 433.3333333333333),
('3pMF1o', 9, 2, 433.3333333333333),
('3pMF1o', 2, 8, 0),
('3pMF1o', 9, 8, 0),
('3pMF1o', 2, 9, 450),
('3pMF1o', 8, 9, 450),
('d8zp5R', 8, 2, 3943.5),
('d8zp5R', 2, 8, 0),
('EmSJ2a', 8, 2, 0),
('EmSJ2a', 2, 8, -2000),
('IdXjwu', 8, 2, 10),
('IdXjwu', 9, 2, 15),
('IdXjwu', 2, 8, 0),
('IdXjwu', 9, 8, 0),
('IdXjwu', 2, 9, 0),
('IdXjwu', 8, 9, 0),
('j2SzY7', 9, 8, 0),
('j2SzY7', 8, 9, 0),
('le2E7E', 8, 2, 0),
('le2E7E', 9, 2, 150),
('le2E7E', 2, 8, 0),
('le2E7E', 9, 8, 0),
('le2E7E', 2, 9, 150),
('le2E7E', 8, 9, 0),
('SYJx0C', 8, 2, 150),
('SYJx0C', 9, 2, 100),
('SYJx0C', 2, 8, 0),
('SYJx0C', 9, 8, 0),
('SYJx0C', 2, 9, 0),
('SYJx0C', 8, 9, 0),
('Tii5jd', 8, 2, 100),
('Tii5jd', 9, 2, 100),
('Tii5jd', 2, 8, 0),
('Tii5jd', 9, 8, 0),
('Tii5jd', 2, 9, 0),
('Tii5jd', 8, 9, 150),
('UVHuOg', 8, 2, -20),
('UVHuOg', 2, 8, 0);

-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE `team` (
  `teamID` varchar(6) NOT NULL,
  `title` varchar(100) NOT NULL,
  `category` varchar(100) NOT NULL,
  `admin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `team`
--

INSERT INTO `team` (`teamID`, `title`, `category`, `admin`) VALUES
('8F49Ln', 'eleni', '', 2),
('D2WEY5', 'ts', '', 2),
('d8zp5R', 'NEW YORK', '', 2),
('gltCOI', 'mmmm', 'nnn', 5),
('guUwMY', 'dokimi', 'fagita', 2),
('hY4F1F', 'test', '', 2),
('kuK37y', 'mpa', '', 1),
('kuwH7E', 'sgfayd', '', 6),
('NLRn8x', 'DEN EXOYME CONTENT', 'BARIEMAI', 7),
('Q0XfSf', 'paris', '', 8),
('Tii5jd', 'paris', '', 2);

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
(1, 'kuK37y'),
(5, 'gltCOI'),
(6, 'kuwH7E'),
(7, 'NLRn8x'),
(2, '8F49Ln'),
(2, 'D2WEY5'),
(2, 'guUwMY'),
(8, 'Q0XfSf'),
(2, 'Tii5jd'),
(8, 'Tii5jd'),
(9, 'Tii5jd'),
(2, 'd8zp5R'),
(8, 'd8zp5R');

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
('Tii5jd', 36),
('d8zp5R', 37);

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
(9, 'ma', 'me');

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `expense`
--
ALTER TABLE `expense`
  MODIFY `expenseID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
