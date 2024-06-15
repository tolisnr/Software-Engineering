-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 15, 2024 at 01:32 PM
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
  `amountOwed` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`amountOwed`))
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
(1, 'f', 10, '2024-06-14', 2);

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
('52pGci', 'bb', '', 2),
('8F49Ln', 'eleni', '', 2),
('D2WEY5', 'ts', '', 2),
('gltCOI', 'mmmm', 'nnn', 5),
('hY4F1F', 'test', '', 2),
('kuK37y', 'mpa', '', 1),
('kuwH7E', 'sgfayd', '', 6),
('NB9AV9', 'kapa', '', 2),
('NLRn8x', 'DEN EXOYME CONTENT', 'BARIEMAI', 7),
('VleUrx', 'aa', '', 2);

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
(2, 'hY4F1F'),
(2, 'hY4F1F'),
(2, '52pGci'),
(2, '52pGci'),
(2, 'VleUrx'),
(2, 'VleUrx'),
(2, 'VleUrx'),
(1, 'VleUrx'),
(1, 'kuK37y'),
(5, 'gltCOI'),
(6, 'kuwH7E'),
(7, 'NLRn8x'),
(2, '8F49Ln'),
(2, 'D2WEY5'),
(1, 'hY4F1F'),
(2, 'NB9AV9');

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
(7, 'PAOK', '1926');

-- --------------------------------------------------------

--
-- Table structure for table `user_expense`
--

CREATE TABLE `user_expense` (
  `userID` int(11) NOT NULL,
  `expenseID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `balance`
--
ALTER TABLE `balance`
  ADD KEY `team` (`teamID`);

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
  ADD KEY `ex_us` (`expenseID`),
  ADD KEY `us_ex` (`userID`);

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
-- Indexes for table `user_expense`
--
ALTER TABLE `user_expense`
  ADD KEY `user_expense` (`userID`),
  ADD KEY `expense` (`expenseID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `expense`
--
ALTER TABLE `expense`
  MODIFY `expenseID` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `balance`
--
ALTER TABLE `balance`
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
  ADD CONSTRAINT `ex_us` FOREIGN KEY (`expenseID`) REFERENCES `expense` (`expenseID`),
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
  ADD CONSTRAINT `expanse` FOREIGN KEY (`expenseID`) REFERENCES `expense` (`expenseID`),
  ADD CONSTRAINT `team_ex` FOREIGN KEY (`teamID`) REFERENCES `team` (`teamID`);

--
-- Constraints for table `user_expense`
--
ALTER TABLE `user_expense`
  ADD CONSTRAINT `expense` FOREIGN KEY (`expenseID`) REFERENCES `expense` (`expenseID`),
  ADD CONSTRAINT `user_expense` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
