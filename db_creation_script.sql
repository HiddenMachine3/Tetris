CREATE DATABASE IF NOT EXISTS tetris CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE tetris;  -- Replace  \connect tetris;

CREATE TABLE `scores` (
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `score` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;