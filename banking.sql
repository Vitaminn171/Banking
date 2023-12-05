-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Dec 05, 2023 at 08:25 AM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `banking`
--

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transaction_id` int(5) NOT NULL,
  `sender` varchar(50) NOT NULL,
  `receiver` varchar(50) NOT NULL,
  `total` int(50) NOT NULL,
  `note` varchar(200) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transaction_id`, `sender`, `receiver`, `total`, `note`, `date`) VALUES
(1, 'abc@gmail.com', 'an@gmail.com', 1000000, 'abcxyzádfasdfasdfasdfasfasfasfasdfafafasdfafasdfasfasdfasdf', '2023-11-05 17:00:00'),
(2, 'an@gmail.com', 'an1@gmail.com', 1000, 'asdfasdf', '2023-10-31 17:00:00'),
(3, 'an1@gmail.com', 'an@gmail.com', 1000, 'asgdfgsdfhg', '2023-10-31 17:00:00'),
(4, 'ahha@gmail.com', 'an@gmail.com', 10000, 'dfgssdfg', '2023-11-04 17:00:00'),
(5, 'an@gmail.com', 'hehe@gmail.com', 300000, 'hehe', '2023-11-14 17:00:00'),
(6, 'an@gmail.com', 'hehe@gmail.com', 20000, 'hehehehe', '2023-11-14 17:00:00'),
(7, 'hehe@gmail.com', 'an@gmail.com', 200000, 'give back test', '2023-11-14 17:00:00'),
(8, 'an@gmail.com', 'hehe@gmail.com', 100000, 'he', '2023-11-14 17:00:00'),
(9, 'an@gmail.com', 'hehe@gmail.com', 50000, 'ok', '2023-11-20 17:00:00'),
(10, 'quocan@gmail.com', 'an@gmail.com', 50000, 'ok', '2023-11-20 17:00:00'),
(11, 'lyquocan@gmail.com', 'an@gmail.com', 500000, 'transfer money test', '2023-11-21 03:09:00'),
(12, 'an@gmail.com', 'an3@gmail.com', 55555, 'test time', '2023-12-05 07:10:38');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `money` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`email`, `password`, `status`, `money`) VALUES
('an2@gmail.com', '202cb962ac59075b964b07152d234b70', 1, 0),
('an3@gmail.com', '202cb962ac59075b964b07152d234b70', 1, 55555),
('an4@gmail.com', '202cb962ac59075b964b07152d234b70', 1, 0),
('an@gmail.com', '202cb962ac59075b964b07152d234b70', 1, 594445),
('hehe@gmail.com', '202cb962ac59075b964b07152d234b70', 1, 270000),
('lyquocan@gmail.com', '202cb962ac59075b964b07152d234b70', 0, 500000),
('quocan@gmail.com', '202cb962ac59075b964b07152d234b70', 1, 50000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transaction_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transaction_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
