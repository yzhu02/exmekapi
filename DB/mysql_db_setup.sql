CREATE DATABASE exmek CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'sa'@'localhost' IDENTIFIED WITH caching_sha2_password BY 'sa';
GRANT ALL ON *.* TO 'sa'@'localhost' WITH GRANT OPTION;

CREATE USER 'sa'@'%' IDENTIFIED WITH caching_sha2_password BY 'sa';
GRANT ALL ON exmek.* TO 'sa'@'%' WITH GRANT OPTION;