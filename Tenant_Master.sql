CREATE DATABASE `tenant_master`;
use tenant_master;

CREATE TABLE `tenant_data_source` (
  `id` int NOT NULL AUTO_INCREMENT,  
  `tenant` varchar(255) NOT NULL,
  `module` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `driver_class_name` varchar(255) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `maximum_pool_size` int NOT NULL,
  `minimum_idle` int NOT NULL,
  `max_lifetime` int NOT NULL,
  `keep_alive_time` int NOT NULL,
  `connection_timeout` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_TENANT_DATA_SOURCE_NAME_MODULE` (`tenant`,`module`)
) 
-- root encrypted to VB9vUergflhaSpEDhEXsRw==
INSERT INTO `tenant_master`.`tenant_data_source` (`id`, `tenant`, `module`, `url`, `user_name`, `password`, `driver_class_name`,
`is_active`, `maximum_pool_size`, `minimum_idle`, `max_lifetime`, `keep_alive_time`, `connection_timeout`)
VALUES(1, 'drishti', 'user', 'jdbc:mysql://localhost:3306/anpl_user?useSSL=false','root', 'VB9vUergflhaSpEDhEXsRw==',
'com.mysql.cj.jdbc.Driver', 1, 10, 10, 1800000, 120000, 30000);