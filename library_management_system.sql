-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 31, 2024 at 06:38 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_management_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `book_inventory`
--

CREATE TABLE `book_inventory` (
  `bookID` int(10) UNSIGNED NOT NULL,
  `bookTitle` varchar(255) NOT NULL,
  `bookCode` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `dateAdded` varchar(20) NOT NULL,
  `stocks` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `book_inventory`
--

INSERT INTO `book_inventory` (`bookID`, `bookTitle`, `bookCode`, `category`, `dateAdded`, `stocks`) VALUES
(1, 'Core Java Volume I - Fundamentals', 'JAVA000', 'Take Home', '2024-12-25', 2),
(2, 'Effective Java', 'JAVA001', 'Take Home', '2024-12-25', 2),
(3, 'OCA Java SE 8', 'JAVA002', 'Not Borrowable', '2023-11-13', 2),
(4, 'A JAVA FOR TODAY SE 21', 'JAVA003', 'Encyclopedia', '2024-12-04', 5),
(15, 'poqueng may tahi', 'nigga', 'Thesis', '2024-12-15', 30),
(16, 'testtt', 'test', 'Thesis', '2024-12-11', 4),
(17, 'gawkgawk', 'gawk', 'Not Borrowable', '2024-12-10', 8),
(18, 'makneega', 'makneega', 'Encyclopedia', '2024-09-09', 23),
(19, 'tipaklong', 'tipaklong', 'Stay In', '2024-12-02', 2),
(20, 'A reason to hawk tuah by spit on that thang', 'hawkTuah', 'Take Home', '2024-06-12', 33),
(21, 'Spring A JAVA Framework', 'JAVA004', 'Take Home', '2024-12-31', 3);

-- --------------------------------------------------------

--
-- Table structure for table `computer_users`
--

CREATE TABLE `computer_users` (
  `userID` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `department` varchar(255) NOT NULL,
  `logDate` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `computer_users`
--

INSERT INTO `computer_users` (`userID`, `name`, `department`, `logDate`) VALUES
(1, 'Carl Joshua Cadungog', 'BSCS', '2024-12-25'),
(2, 'John Doe', 'BSHM', '2024-11-16'),
(3, 'Daniel Cadungog', 'BSECE', '2024-12-27'),
(5, 'Zhean', 'BSIT', '2024-12-05'),
(6, 'Anthony ', 'BSThatwassohot', '2024-12-01'),
(7, 'Cedrick Toledana', 'BSCS', '2024-12-11'),
(8, 'Kent Raven', 'BSCS', '2024-11-01'),
(9, 'Maris', 'Ill touch myself nalang', '2024-08-14'),
(10, 'test', 'test', '2024-07-16'),
(11, 'test', 'test', '2024-10-21'),
(12, 'user5', 'bsit', '2024-12-05'),
(13, 'Dave Ulrich Santos', 'BSCS', '2024-08-12'),
(14, 'Ivan Joson', 'Kupal', '2024-01-01'),
(16, 'Carl Joshua Cadungog', 'BSCS', '2024-06-02'),
(17, 'Carl Joshua Cadungog', 'BSCS', '2024-09-19'),
(18, 'Carl Joshua Cadungog', 'BSCS', '2024-11-11'),
(19, 'lucy', 'bsnanay', '2024-08-04');

-- --------------------------------------------------------

--
-- Table structure for table `issue_book_table`
--

CREATE TABLE `issue_book_table` (
  `issueID` int(10) UNSIGNED NOT NULL,
  `bookID` int(11) NOT NULL,
  `bookTitle` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `studentNo` int(11) NOT NULL,
  `contact` varchar(50) NOT NULL,
  `issueDate` varchar(20) NOT NULL,
  `dueDate` varchar(20) NOT NULL,
  `remarks` varchar(255) NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `issue_book_table`
--

INSERT INTO `issue_book_table` (`issueID`, `bookID`, `bookTitle`, `name`, `studentNo`, `contact`, `issueDate`, `dueDate`, `remarks`, `status`) VALUES
(1, 2, 'Effective Java', 'Chessman', 202310253, '09455040518', '2024-12-17', '2024-12-20', '', 'Pending'),
(2, 1, 'Core Java Volume I - Fundamentals', 'Chessman', 202310253, '09455040518', '2024-12-17', '2024-12-20', 'kupal ung nanghiram', 'Pending'),
(3, 20, 'A reason to hawk tuah by spit on that thang', 'test', 13912743, '09456232921', '2024-12-02', '2024-12-05', 'test only', 'Pending'),
(4, 1, 'Core Java Volume I - Fundamentals', 'Viktor', 202310253, '09455040518', '2024-12-19', '2024-12-22', '', 'Pending'),
(5, 2, 'Effective Java', 'Viktor', 202312345, 'viktor is gone', '2024-12-01', '2024-12-04', 'VIKTOOOOOOOOROROORRORORRRRRRR', 'Pending'),
(6, 1, 'Core Java Volume I - Fundamentals', 'Test borrower', 123456789, '093455040518', '2024-12-17', '2024-12-20', 'the quick brown fox jumps over the lazy dog, test remarks test remarks, testttttttttttttttttttttttt', 'Pending'),
(7, 2, 'Effective Java', 'Dave Ulrich Upo Santos', 202372512, '09123456789', '2024-12-28', '2024-12-31', 'test not overdue', 'Returned'),
(8, 1, 'Core Java Volume I - Fundamentals', 'Defaulter user 1', 202345678, '09456789532', '2024-12-27', '2024-12-30', '', 'Returned'),
(9, 20, 'A reason to hawk tuah by spit on that thang', 'test without overdue record', 202356271, '09455040518', '2024-12-28', '2024-12-31', '', 'Returned'),
(10, 21, 'Spring A JAVA Framework', 'Cedrick Toledana', 202311111, 'cedrick\'semailsample@gmail.com', '2024-12-29', '2025-01-01', 'test for pie chart', 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `question` varchar(255) NOT NULL,
  `answer` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `question`, `answer`) VALUES
(1, 'Chessman', '123456', 'carljoshua.cadungog@cvsu.edu.ph', 'What is your childhood nickname?', 'kupal'),
(2, 'lucy', '1234567890', 'randomemail@gmail.com', 'Who is your favorite teacher?', 'wala'),
(3, 'namo', '123', 'wtfudge@gmail.com', 'Where is your birthplace?', 'pacific ocean'),
(4, 'Cheng-ge', '123456', 'carljoshuacadungog@gmail.com', 'What is your pet name?', 'hatdog'),
(5, 'test', 'test', 'test@gmail.com', 'Where is your birthplace?', 'test'),
(6, 'hatdoggy', '123', 'hatdog@gmail.com', 'Who is your favorite teacher?', 'sir budz'),
(7, 'goody', '123', 'bhjJF@GMAIL.COM', 'When is the month of your birthdate?', 'june'),
(8, 'zhean', '123', 'zhean@gmail.com', 'What is your childhood nickname?', 'tite');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book_inventory`
--
ALTER TABLE `book_inventory`
  ADD PRIMARY KEY (`bookID`),
  ADD UNIQUE KEY `bookCode` (`bookCode`);

--
-- Indexes for table `computer_users`
--
ALTER TABLE `computer_users`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `issue_book_table`
--
ALTER TABLE `issue_book_table`
  ADD PRIMARY KEY (`issueID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book_inventory`
--
ALTER TABLE `book_inventory`
  MODIFY `bookID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `computer_users`
--
ALTER TABLE `computer_users`
  MODIFY `userID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `issue_book_table`
--
ALTER TABLE `issue_book_table`
  MODIFY `issueID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
