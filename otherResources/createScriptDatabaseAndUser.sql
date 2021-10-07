DROP SCHEMA IF EXISTS `cato`;
DROP USER IF EXISTS 'userCato'@'localhost';

CREATE SCHEMA `cato` DEFAULT CHARACTER SET utf8 ;

CREATE USER 'userCato'@'localhost' IDENTIFIED BY 'pwCato';
GRANT ALL PRIVILEGES ON cato.* TO 'userCato'@'localhost';
