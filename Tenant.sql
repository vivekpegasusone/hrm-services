-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: drishti
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user_seq`
--

DROP TABLE IF EXISTS `user_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Dumping data for table `user_seq`
--

LOCK TABLES `user_seq` WRITE;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` VALUES (1);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Table structure for table `code_lookup`
--

DROP TABLE IF EXISTS `code_lookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `code_lookup` (  
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `value` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CL_PARENT_ID` (`parent_id`),
  CONSTRAINT `FK_CL_PARENT_ID` FOREIGN KEY (`parent_id`) REFERENCES `code_lookup` (`id`),
  UNIQUE KEY `UK_CL_CODE_PARENT_ID` (`code`,`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `skill_set`
--

DROP TABLE IF EXISTS `skill_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill_set` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `skill_category_id` bigint NOT NULL,
  `name` varchar(25) NOT NULL,
  `version` varchar(255) NOT NULL,
  `description` varchar(100) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_SS_NAME_VERSION` (`name`,`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` bigint NOT NULL,
  `user_code` varchar(20) NOT NULL,
  `login_id` varchar(30) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `title` varchar(5) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `middle_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `nick_name` varchar(15) NOT NULL,
  `email` varchar(60) NOT NULL,
  `joining_date` datetime(6) NOT NULL,
  `isd_code` varchar(4) NOT NULL,
  `contact_number` varchar(10) NOT NULL, 
  `is_active` bit(1) NOT NULL,
  `is_locked` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_EM_USER_CODE` (`user_code`),
  UNIQUE KEY `UK_EM_LOGIN_ID` (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_account`
--

DROP TABLE IF EXISTS `employee_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_account` (  
  `employee_id` bigint NOT NULL,
  `account_type` varchar(10) NOT NULL, 
  `bank_name` varchar(60) NOT NULL,
  `branch_name` varchar(60) NOT NULL,
  `ifsc_code` varchar(15) NOT NULL,
  `account_number` varchar(25) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,
  PRIMARY KEY (`employee_id`),
  CONSTRAINT `FK_EA_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `employee_detail`
--

DROP TABLE IF EXISTS `employee_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_detail` (
  `employee_id` bigint NOT NULL,
  `nationality` varchar(20) NOT NULL,
  `date_of_birth` datetime(6) NOT NULL,
  `city_of_birth` varchar(30) NOT NULL,
  `country_of_birth` varchar(30) NOT NULL,
  `marital_status` varchar(10) NOT NULL,
  `date_of_marriage` datetime(6),
  `date_of_resignation` datetime(6),
  `date_of_relieving` datetime(6),  
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,
  PRIMARY KEY (`employee_id`),
  CONSTRAINT `FK_ED_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `employee_education`
--

DROP TABLE IF EXISTS `employee_education`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_education` (
  `employee_id` bigint NOT NULL,
  `education_code_id` int NOT NULL,
  `institution` varchar(60) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `end_date` datetime(6) NOT NULL,  
  `grade` varchar(10) NOT NULL, 
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,  
  PRIMARY KEY (`employee_id`),
  CONSTRAINT `FK_EE_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_emergency_contact`
--

DROP TABLE IF EXISTS `employee_emergency_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_emergency_contact` (
  `employee_id` bigint NOT NULL,  
  `name` varchar(60) NOT NULL,
  `relation` varchar(20) NOT NULL,
  `mobile_number` varchar(15) NOT NULL,
  `notes` varchar(255) NOT NULL,
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,  
  PRIMARY KEY (`employee_id`),
  CONSTRAINT `FK_EEC_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_family`
--

DROP TABLE IF EXISTS `employee_family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_family` (  
  `employee_id` bigint NOT NULL,
  `name` varchar(60) NOT NULL,
  `relation` varchar(20) NOT NULL,
  `date_of_birth` datetime(6) NOT NULL,
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,  
  PRIMARY KEY (`employee_id`),
  CONSTRAINT `FK_EF_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_identity_doc`
--

DROP TABLE IF EXISTS `employee_identity_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_identity_doc` (
  `employee_id` bigint NOT NULL,
  `document_type` varchar(15) NOT NULL,
  `document_number` varchar(25) NOT NULL,
  `issue_date` datetime(6) NOT NULL,
  `expiry_date` datetime(6) NOT NULL,
  `city_of_issue` varchar(30),
  `country_of_issue` varchar(30) NOT NULL,
  `state_of_issue` varchar(30),
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,  
  PRIMARY KEY (`employee_id`),
  CONSTRAINT `FK_EED_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_job_history`
--

DROP TABLE IF EXISTS `employee_job_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_job_history` (
  `employee_id` bigint NOT NULL,
  `employer_name` varchar(100) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `start_date` datetime(6) NOT NULL,  
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,  
  PRIMARY KEY (`employee_id`),
  CONSTRAINT `FK_EJH_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_picture`
--

DROP TABLE IF EXISTS `employee_picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_picture` (
  `employee_id` bigint NOT NULL,
  `profile_picture` blob,
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,
  PRIMARY KEY (`employee_id`),
  CONSTRAINT `FK_EP_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_skill_set`
--

DROP TABLE IF EXISTS `employee_skill_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_skill_set` (
  `employee_id` bigint NOT NULL,
  `skill_set_id` bigint NOT NULL,
  `experience_in_months` int NOT NULL,  
  `proficiency` varchar(12) NOT NULL,
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `UK_ESS_EMPLOYEE_ID_` (`skill_set_id`, `employee_id`),
  CONSTRAINT `FK_ESS_SKILL_SET_ID` FOREIGN KEY (`skill_set_id`) REFERENCES `skill_set` (`id`),
  CONSTRAINT `FK_ESS_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee_communication`
--

DROP TABLE IF EXISTS `employee_communication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_communication` ( 
  `employee_id` bigint NOT NULL,  
  `personal_email` varchar(60) NOT NULL,
  `curr_address` varchar(60) NOT NULL,
  `curr_street` varchar(60) DEFAULT NULL,
  `curr_city` varchar(30) NOT NULL,
  `curr_state` varchar(30) NOT NULL,
  `curr_country` varchar(30) NOT NULL,
  `curr_zip_code` varchar(10) NOT NULL,
  `perm_address` varchar(60) NOT NULL,
  `perm_street` varchar(60) DEFAULT NULL,
  `perm_city` varchar(30) NOT NULL,
  `perm_state` varchar(30) NOT NULL,
  `perm_country` varchar(30) NOT NULL,
  `perm_zip_code` varchar(10) NOT NULL,
  `created_by` int NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `updated_by` int NOT NULL,
  `updated_on` datetime(6) NOT NULL,
  PRIMARY KEY (`employee_id`),
  CONSTRAINT `FK_ECOM_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-05 20:21:44