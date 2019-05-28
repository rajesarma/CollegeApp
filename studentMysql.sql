-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: college
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `academic_year`
--

DROP TABLE IF EXISTS `academic_year`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `academic_year` (
  `year_id` int(11) NOT NULL AUTO_INCREMENT,
  `batch` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`year_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academic_year`
--

LOCK TABLES `academic_year` WRITE;
/*!40000 ALTER TABLE `academic_year` DISABLE KEYS */;
INSERT INTO `academic_year` VALUES (1,'2014-2015'),(2,'2015-2016'),(3,'2016-2017'),(4,'2017-2018'),(5,'2018-2019'),(6,'2019-2020');
/*!40000 ALTER TABLE `academic_year` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blood_group`
--

DROP TABLE IF EXISTS `blood_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `blood_group` (
  `bg_id` int(11) NOT NULL AUTO_INCREMENT,
  `bg_name` varchar(100) NOT NULL,
  PRIMARY KEY (`bg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blood_group`
--

LOCK TABLES `blood_group` WRITE;
/*!40000 ALTER TABLE `blood_group` DISABLE KEYS */;
INSERT INTO `blood_group` VALUES (1,'A Positive'),(2,'A Negative'),(3,'B Positive'),(4,'B Negative'),(5,'AB Positive'),(6,'AB Negative'),(7,'O Positive'),(8,'O Negative');
/*!40000 ALTER TABLE `blood_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `branch` (
  `branch_id` varchar(100) NOT NULL,
  `branch_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES ('01','B.Tech - CIVIL'),('02','B.Tech - EEE'),('03','B.Tech - MECH'),('04','B.Tech - ECE'),('05','B.Tech - CSE'),('12','B.Tech - IT');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_type`
--

DROP TABLE IF EXISTS `exam_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `exam_type` (
  `exam_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_type` varchar(100) DEFAULT NULL,
  `max_marks` int(11) DEFAULT NULL,
  PRIMARY KEY (`exam_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_type`
--

LOCK TABLES `exam_type` WRITE;
/*!40000 ALTER TABLE `exam_type` DISABLE KEYS */;
INSERT INTO `exam_type` VALUES (1,'Internal - 1',25),(2,'Internal - 2',25),(3,'Assignment',5),(4,'External Descriptive',70),(5,'External Lab',50);
/*!40000 ALTER TABLE `exam_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty_details`
--

DROP TABLE IF EXISTS `faculty_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `faculty_details` (
  `faculty_id` int(11) NOT NULL AUTO_INCREMENT,
  `faculty_name` varchar(100) DEFAULT NULL,
  `father_name` varchar(100) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `doj` date DEFAULT NULL,
  `photo` varchar(500) DEFAULT NULL,
  `aadhar` varchar(50) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  `blood_group_id` int(11) DEFAULT NULL,
  `is_active` char(1) DEFAULT NULL,
  `emp_no` varchar(25) DEFAULT NULL,
  `user_id` varchar(25) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `qly_id` int(11) DEFAULT NULL,
  `branch_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`faculty_id`),
  KEY `branch_id` (`branch_id`),
  KEY `faculty_details_ibfk_1_idx` (`blood_group_id`),
  KEY `faculty_details_ibfk_4_idx` (`qly_id`),
  KEY `faculty_details_ibfk_3_idx` (`subject_id`),
  CONSTRAINT `faculty_details_ibfk_1` FOREIGN KEY (`blood_group_id`) REFERENCES `blood_group` (`bg_id`),
  CONSTRAINT `faculty_details_ibfk_2` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`),
  CONSTRAINT `faculty_details_ibfk_3` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`subject_id`),
  CONSTRAINT `faculty_details_ibfk_4` FOREIGN KEY (`qly_id`) REFERENCES `qualifications` (`qly_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty_details`
--

LOCK TABLES `faculty_details` WRITE;
/*!40000 ALTER TABLE `faculty_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `faculty_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qualifications`
--

DROP TABLE IF EXISTS `qualifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qualifications` (
  `qly_id` int(11) NOT NULL AUTO_INCREMENT,
  `qly_name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`qly_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qualifications`
--

LOCK TABLES `qualifications` WRITE;
/*!40000 ALTER TABLE `qualifications` DISABLE KEYS */;
INSERT INTO `qualifications` VALUES (1,'M.Sc'),(2,'MBA'),(3,'B.Tech'),(4,'MCA'),(5,'PGDPA'),(6,'BA'),(7,'M Phil'),(8,'B.Com'),(9,'MA'),(10,'Pre Ph. D'),(11,'M.E/M.Tech'),(12,'M.Com'),(13,'Polytechnic '),(14,'B.E'),(15,'SCJP'),(16,'B.Sc'),(17,'PGDBM'),(18,'B.Plan'),(19,'SSC'),(20,'ML'),(21,'PGDHRM'),(22,'BCA'),(23,'CA'),(24,'ICWA'),(25,'CS'),(26,'Ph. D'),(28,'MS'),(29,'M.Pharm'),(30,'MDS'),(31,'BL/LLB'),(32,'BBA'),(33,'BS'),(34,'MBBS'),(35,'B.Pharm'),(36,'BDS'),(37,'DBA'),(38,'PGDIT '),(39,'PMP '),(40,'M.LI.Sc');
/*!40000 ALTER TABLE `qualifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_services`
--

DROP TABLE IF EXISTS `role_services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role_services` (
  `role_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  KEY `role_services_ibfk_1_idx` (`role_id`),
  KEY `role_services_ibfk_2_idx` (`service_id`),
  CONSTRAINT `role_services_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `role_services_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `services` (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_services`
--

LOCK TABLES `role_services` WRITE;
/*!40000 ALTER TABLE `role_services` DISABLE KEYS */;
INSERT INTO `role_services` VALUES (1,1),(1,10),(1,9),(1,8),(1,6),(1,5),(1,4),(1,3),(1,2),(1,11),(1,12),(1,13),(1,14),(1,7),(1,15),(1,16),(1,17),(1,18),(3,1),(3,14),(3,15),(3,18),(3,19),(3,20),(3,21),(2,21),(2,19),(2,14),(2,20),(2,10),(2,13),(2,18),(4,22),(4,23),(1,24);
/*!40000 ALTER TABLE `role_services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Admin'),(2,'FacultySubjects'),(3,'Student'),(4,'Management');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semester`
--

DROP TABLE IF EXISTS `semester`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `semester` (
  `semester_id` int(11) NOT NULL AUTO_INCREMENT,
  `semester_name` varchar(100) DEFAULT NULL,
  `year_id` int(11) NOT NULL,
  PRIMARY KEY (`semester_id`),
  KEY `semester_ibfk_1_idx` (`year_id`),
  CONSTRAINT `semester_ibfk_1` FOREIGN KEY (`year_id`) REFERENCES `years` (`year_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semester`
--

LOCK TABLES `semester` WRITE;
/*!40000 ALTER TABLE `semester` DISABLE KEYS */;
INSERT INTO `semester` VALUES (1,'1-1',1),(2,'1-2',1),(3,'2-1',2),(4,'2-2',2),(5,'3-1',3),(6,'3-2',3),(7,'4-1',4),(8,'4-2',4);
/*!40000 ALTER TABLE `semester` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `services` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_url` varchar(100) DEFAULT NULL,
  `service_name` varchar(25) DEFAULT NULL,
  `is_active` char(1) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `dispay_order` int(11) DEFAULT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,'/home','Home','Y',0,1),(2,'/test','Student List','Y',12,34),(3,'/studentAdd','Add Student','Y',12,31),(4,'searchStudent.do','Search Student','Y',14,52),(5,'addStudentAttendance.do','Add Student Attendance','Y',12,32),(6,'addStudentMarks.do','Add Student Marks','Y',12,33),(7,'studentAttendanceReport.do','Student Attendance Report','Y',14,51),(8,'studentProgressReport.do','Student Progress Report','Y',14,53),(9,'addFaculty.do','Add FacultySubjects','Y',11,21),(10,'facultyReport.do','FacultySubjects Report','Y',13,41),(11,'/home','FacultySubjects Related','Y',0,2),(12,'/home','Student Related','Y',0,4),(13,'/home','FacultySubjects Reports','Y',0,3),(14,'/home','Student Reports','Y',0,5),(15,'/logout','Log Out','Y',0,10),(16,'/home','Admin Services','Y',0,6),(17,'userCreation.do','User Creation','Y',16,1),(18,'changePassword.do','Change Password','Y',16,2),(19,'studentPerAttReport.do','Student Attendance Report','Y',14,54),(20,'studentPerMarksReport.do','Student Marks Report','Y',14,55),(21,'studentDisplay.do','Student Display','Y',14,56),(22,'managementReport.do','Management Report','Y',23,41),(23,'/home','Management Reports','Y',0,6),(24,'studentExcelUpload.do','Student Excel Upload','Y',12,34);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_attendance`
--

DROP TABLE IF EXISTS `student_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student_attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `year_id` int(11) DEFAULT NULL,
  `semester_id` int(11) DEFAULT NULL,
  `branch_id` varchar(25) DEFAULT NULL,
  `no_of_days` int(11) DEFAULT NULL,
  `days_present` int(11) DEFAULT NULL,
  `academic_year_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `branch_id` (`branch_id`),
  KEY `student_attendance_ibfk_6_idx` (`student_id`),
  KEY `student_attendance_ibfk_7_idx` (`academic_year_id`),
  KEY `student_attendance_ibfk_3_idx` (`semester_id`),
  KEY `student_attendance_ibfk_2_idx` (`year_id`),
  CONSTRAINT `student_attendance_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`year_no`),
  CONSTRAINT `student_attendance_ibfk_3` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`),
  CONSTRAINT `student_attendance_ibfk_4` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`),
  CONSTRAINT `student_attendance_ibfk_6` FOREIGN KEY (`student_id`) REFERENCES `student_details` (`student_id`),
  CONSTRAINT `student_attendance_ibfk_7` FOREIGN KEY (`academic_year_id`) REFERENCES `academic_year` (`year_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_attendance`
--

LOCK TABLES `student_attendance` WRITE;
/*!40000 ALTER TABLE `student_attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_details`
--

DROP TABLE IF EXISTS `student_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student_details` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_name` varchar(100) DEFAULT NULL,
  `father_name` varchar(100) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `doj` date DEFAULT NULL,
  `photo` varchar(500) DEFAULT NULL,
  `aadhar` varchar(50) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `guardian_mobile` varchar(10) DEFAULT NULL,
  `blood_group_id` int(11) DEFAULT NULL,
  `academic_year_id` int(11) DEFAULT NULL,
  `branch_id` varchar(50) DEFAULT NULL,
  `is_active` char(1) DEFAULT NULL,
  `roll_no` varchar(25) DEFAULT NULL,
  `mobile_no` varchar(10) DEFAULT NULL,
  `mother_name` varchar(100) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `mentor_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  KEY `branch_id` (`branch_id`),
  KEY `student_details_ibfk_2_idx` (`academic_year_id`),
  KEY `student_details_ibfk_1_idx` (`blood_group_id`),
  CONSTRAINT `student_details_ibfk_1` FOREIGN KEY (`blood_group_id`) REFERENCES `blood_group` (`bg_id`),
  CONSTRAINT `student_details_ibfk_2` FOREIGN KEY (`academic_year_id`) REFERENCES `academic_year` (`year_id`),
  CONSTRAINT `student_details_ibfk_3` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_details`
--

LOCK TABLES `student_details` WRITE;
/*!40000 ALTER TABLE `student_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_marks`
--

DROP TABLE IF EXISTS `student_marks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student_marks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `year_id` int(11) DEFAULT NULL,
  `semester_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `exam_type_id` int(11) DEFAULT NULL,
  `marks` int(11) DEFAULT NULL,
  `branch_id` varchar(25) DEFAULT NULL,
  `academic_year_id` int(11) DEFAULT NULL,
  `max_marks` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `branch_id` (`branch_id`),
  KEY `student_id_idx` (`student_id`),
  KEY `student_marks_ibfk_6_idx` (`academic_year_id`),
  KEY `student_marks_ibfk_5_idx` (`exam_type_id`),
  KEY `student_marks_ibfk_3_idx` (`semester_id`),
  KEY `student_marks_ibfk_4_idx` (`subject_id`),
  KEY `student_marks_ibfk_2_idx` (`year_id`),
  CONSTRAINT `student_id` FOREIGN KEY (`student_id`) REFERENCES `student_details` (`student_id`),
  CONSTRAINT `student_marks_ibfk_2` FOREIGN KEY (`year_id`) REFERENCES `years` (`year_no`),
  CONSTRAINT `student_marks_ibfk_3` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`),
  CONSTRAINT `student_marks_ibfk_4` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`subject_id`),
  CONSTRAINT `student_marks_ibfk_5` FOREIGN KEY (`exam_type_id`) REFERENCES `exam_type` (`exam_type_id`),
  CONSTRAINT `student_marks_ibfk_6` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`),
  CONSTRAINT `student_marks_ibfk_7` FOREIGN KEY (`academic_year_id`) REFERENCES `academic_year` (`year_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_marks`
--

LOCK TABLES `student_marks` WRITE;
/*!40000 ALTER TABLE `student_marks` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_marks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `subjects` (
  `subject_id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(100) DEFAULT NULL,
  `semester_id` int(11) DEFAULT NULL,
  `branch_id` varchar(100) DEFAULT NULL,
  `credits` int(11) DEFAULT NULL,
  PRIMARY KEY (`subject_id`),
  KEY `branch_id` (`branch_id`),
  KEY `subjects_ibfk_1_idx` (`semester_id`),
  CONSTRAINT `subjects_ibfk_1` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`),
  CONSTRAINT `subjects_ibfk_2` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=394 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES (1,'English',1,'12',3),(2,'Maths-1',1,'12',3),(3,'Engineering Chemistry',1,'12',3),(4,'Engineering Mechanics',1,'12',3),(5,'Computer Programming',1,'12',3),(6,'Environmental Studies',1,'12',2),(7,'Chemistry Lab',1,'12',3),(8,'English Comm. Lab',1,'12',2),(9,'Computer Programming lab',1,'12',2),(10,'English-2',2,'12',3),(11,'Mathematics-2',2,'12',3),(12,'Mathematics-3',2,'12',3),(13,'Engineering Physics',2,'12',3),(14,'Professional Ethics & Human Values',2,'12',3),(15,'Engineering Drawing',2,'12',3),(16,'English Communication Skills lab-2',2,'12',2),(17,'Engineering Physics lab',2,'12',2),(18,'Engg. Workshop & IT Workshop',2,'12',2),(19,'Managerial Economics & Financial Analysis',3,'12',3),(20,'Object Oriented Programming through C++',3,'12',3),(21,'Mathematical Foundations of Computer Science',3,'12',3),(22,'Digital Logic Design',3,'12',3),(23,'Data Structures',3,'12',3),(24,'Object Oriented Programming lab',3,'12',2),(25,'Data Structures lab',3,'12',2),(26,'Digital Logic Design Lab',3,'12',2),(27,'Seminar',3,'12',1),(28,'Probability and Statistics',4,'12',3),(29,'Java Programming',4,'12',3),(30,'Advanced Data Structures',4,'12',3),(31,'Computer Organization',4,'12',3),(32,'Language Processors',4,'12',3),(33,'Advanced Data Structures lab',4,'12',2),(34,'Java Programming lab',4,'12',2),(35,'Free Open Source Software lab',4,'12',2),(36,'Software Engineering',5,'12',3),(37,'Data Communication',5,'12',3),(38,'Advanced JAVA',5,'12',3),(39,'Database Management System',5,'12',3),(40,'Operating Systems',5,'12',3),(41,'Advanced JAVA lab',5,'12',3),(42,'Operating System lab',5,'12',2),(43,'Database Management System lab',5,'12',2),(44,'Linux Programming lab',5,'12',2),(45,'Seminar',5,'12',1),(46,'Computer Networks',6,'12',3),(47,'Data Ware housing and Mining',6,'12',3),(48,'Design and Analysis of Algorithms',6,'12',3),(49,'Software Testing',6,'12',3),(50,'Web Technologies',6,'12',3),(51,'Computer Networks lab',6,'12',2),(52,'Software Testing lab',6,'12',2),(53,'Web Technologies lab',6,'12',2),(54,'IPR and Patents',6,'12',3),(55,'Cryptography and Network Security',7,'12',3),(56,'UML & Design Patterns',7,'12',3),(57,'Mobile Compting',7,'12',3),(58,'Multimedia Computing(Elective-1)',7,'12',3),(59,'Hadoop & BigData (Elective-2)',7,'12',3),(60,'UML & Design Patterns lab',7,'12',2),(61,'Mobile Applicatin Development Lab',7,'12',2),(62,'Software Testing Lab',7,'12',2),(63,'Hadoop & BigData Lab',7,'12',2),(64,'Human Computer Interaction',8,'12',3),(65,'Distributed Systems(Elective-3)',8,'12',3),(66,'Mathematical Optimization',8,'12',3),(67,'Management Science',8,'12',3),(68,'Project',8,'12',9),(69,'English-1',1,'05',3),(70,'Mathematics-1',1,'05',3),(71,'Engineering Chemistry',1,'05',3),(72,'Engineering Mechanics',1,'05',3),(73,'Computer Programing',1,'05',3),(74,'Environmental Studies',1,'05',3),(75,'Engineering Chemistry laboratory',1,'05',2),(76,'English communcation skills lab-1',1,'05',2),(77,'C Programming lab',1,'05',2),(78,'English-2',2,'05',3),(79,'Mathematics-2',2,'05',3),(80,'Mathematics-3',2,'05',3),(81,'Engineering Physics',2,'05',3),(82,'Professional Ethics & Human Values',2,'05',3),(83,'Engineering Drawing',2,'05',3),(84,'English Communication Skills lab-2',2,'05',2),(85,'Engineering Physics lab',2,'05',2),(86,'Engg. Workshop & IT Workshop',2,'05',2),(87,'Managerial Economics & Financial Analysis',3,'05',3),(88,'Object Oriented Programming through C++',3,'05',3),(89,'Mathematical Foundations of Computer Science',3,'05',3),(90,'Digital Logic Design',3,'05',3),(91,'Data Structures',3,'05',3),(92,'Object Oriented Programming lab',3,'05',2),(93,'Data Structures lab',3,'05',2),(94,'Digital Logic Design Lab',3,'05',2),(95,'Seminar',3,'05',1),(96,'Probability and Statistics',4,'05',3),(97,'Java Programming',4,'05',3),(98,'Advanced Data Structures',4,'05',3),(99,'Computer Organization',4,'05',3),(100,'Formal Languages and Automata Theory',4,'05',3),(101,'Advanced Data Structures lab',4,'05',2),(102,'Java Programming lab',4,'05',2),(103,'Free Open Source Software lab',4,'05',2),(104,'Compiler Design',5,'05',3),(105,'Data Communication',5,'05',3),(106,'Principles of programming languages',5,'05',3),(107,'Database Management System',5,'05',3),(108,'Operating Systems',5,'05',3),(109,'Compiler Design lab',5,'05',3),(110,'Operating System lab',5,'05',2),(111,'Database Management System lab',5,'05',2),(112,'Linux Programming lab',5,'05',2),(113,'Seminar',5,'05',1),(114,'Computer Networks',6,'05',3),(115,'Data Ware housing and Mining',6,'05',3),(116,'Design and Analysis of Algorithms',6,'05',3),(117,'Software Engineering',6,'05',3),(118,'Web Technologies',6,'05',3),(119,'Computer Networks lab',6,'05',2),(120,'Software Engineering lab',6,'05',2),(121,'Web Technologies lab',6,'05',2),(122,'IPR and Patents',6,'05',3),(123,'Cryptography and Network Security',7,'05',3),(124,'UML & Design Patterns',7,'05',3),(125,'Mobile Compting',7,'05',3),(126,'Multimedia Computing(Elective-1)',7,'05',3),(127,'Hadoop & BigData (Elective-2)',7,'05',3),(128,'UML & Design Patterns lab',7,'05',2),(129,'Mobile Applicatin Development Lab',7,'05',2),(130,'Software Testing Lab',7,'05',2),(131,'Hadoop & BigData Lab',7,'05',2),(132,'Elective-3',8,'05',3),(133,'Elective-4',8,'05',3),(134,'Mathematical Optimization',8,'05',3),(135,'Management Science',8,'05',3),(136,'Project',8,'05',9),(137,'English-1',1,'04',0),(138,'Mathematics-1',1,'04',0),(139,'Mathematics-2',1,'04',0),(140,'Engineering physics',1,'04',0),(142,'Engineering Drawing',1,'04',0),(143,'English-Communication Skills lab-1',1,'04',0),(144,'Engg physics lab',1,'04',0),(145,'Engg Workshop & IT Workshop',1,'04',0),(146,'English-2',2,'04',0),(147,'Mathematics-2',2,'04',0),(148,'Engineering Chemistry',2,'04',0),(149,'Engineering Mechanics',2,'04',0),(150,'Computer Programming',2,'04',0),(151,'Network Analysis',2,'04',0),(152,'Engg Chemistry lab',2,'04',0),(153,'English Communication Skills lab-2',2,'04',0),(154,'Computer Programming Lab',2,'04',0),(155,'Managerial Economics & Financial Analysis',3,'04',0),(156,'Electronic Devices and Circuits',3,'04',0),(157,'Data Structures',3,'04',0),(158,'Environmental Studies',3,'04',0),(159,'Signals & Systems',3,'04',0),(160,'Electrical Technology',3,'04',0),(161,'Electronic Devices and Circuits lab',3,'04',0),(162,'Network & Electrical Technology lab',3,'04',0),(163,'Electronic Circuit Analysis',4,'04',0),(164,'Management Science',4,'04',0),(165,'Random Variables & Stochastic processes',4,'04',0),(166,'Switching Theory & Logic Design',4,'04',0),(167,'EM Waves and Transmission Lines',4,'04',0),(168,'Analog Communications',4,'04',0),(169,'Electronic Circuit Analysis lab',4,'04',0),(170,'Analog Communications lab',4,'04',0),(171,'Pulse and Digital Circuits',5,'04',0),(172,'Linear IC Applications',5,'04',0),(173,'Control Systems',5,'04',0),(174,'Digital System Design & Digital IC Applications',5,'04',0),(175,'Antennas and wave propagation',5,'04',0),(176,'pulse & Digital circuits lab',5,'04',0),(177,'LIC Applications lab',5,'04',0),(178,'Digital System Design & DICA lab',5,'04',0),(179,'IPR & patents',5,'04',0),(180,'Microprocessors and Microcontrollers',6,'04',0),(181,'Digital Signal Processing',6,'04',0),(182,'Digital Communications',6,'04',0),(183,'Microwave Engineering',6,'04',0),(184,'open elective',6,'04',0),(185,'Microprocessors and Microcontrollers lab',6,'04',0),(186,'Digital Communications lab',6,'04',0),(187,'Digital Signal Processing lab',6,'04',0),(188,'Seminar',6,'04',0),(189,'VLSI Design',7,'04',0),(190,'Computer Networks',7,'04',0),(191,'Digital Image Processing',7,'04',0),(192,'Computer Architecture & Organization',7,'04',0),(193,'elective-1',7,'04',0),(194,'elective-2',7,'04',0),(195,'VLSI lab',7,'04',0),(196,'Microwave Engg lab',7,'04',0),(197,'Cellular Mobile Communication',8,'04',0),(198,'Electronic Measurements and Instrumentation',8,'04',0),(199,'elective-3',8,'04',0),(200,'elective-4',8,'04',0),(201,'project',8,'04',0),(202,'English-1',1,'01',3),(203,'Mathematics-1',1,'01',3),(204,'Engineering Chemistry',1,'01',3),(205,'Engineering Mechanics',1,'01',3),(206,'Computer Programing',1,'01',3),(207,'Environmental Studies',1,'01',3),(208,'Engineering Chemistry laboratory',1,'01',2),(209,'English communcation skills lab-1',1,'01',2),(210,'C Programming lab',1,'01',2),(211,'English-2',2,'01',3),(212,'Mathematics-2',2,'01',3),(213,'Mathematics-3',2,'01',3),(214,'Engineering Physics',2,'01',3),(215,'Professional Ethics & Human Values',2,'01',3),(216,'Engineering Drawing',2,'01',3),(217,'English Communication Skills lab-2',2,'01',2),(218,'Engineering Physics lab',2,'01',2),(219,'Engg. Workshop & IT Workshop',2,'01',2),(220,'Electrical & Electronics Engineering',3,'01',3),(221,'Probability & Statistics',3,'01',3),(222,'Strength of Materials-1',3,'01',3),(223,'Building Materials and Construction',3,'01',3),(224,'Surveying',3,'01',3),(225,'Fluid Mechanics',3,'01',3),(226,'Surveying Field work-1',3,'01',2),(227,'Strength of Materials lab',3,'01',2),(228,'Building Planning & Drawing',4,'01',3),(229,'Managerial Economics & Financial Analysis',4,'01',3),(230,'Strength of Materials-2',4,'01',3),(231,'Hydraulics and Hydraulic Machinery',4,'01',3),(232,'Concrete Technology',4,'01',3),(233,'Structural Analysis-1',4,'01',3),(234,'Fluid Mechanics & Hydraulic Machinery lab',4,'01',2),(235,'Concrete Technology lab',4,'01',2),(236,'Sryveying Field work-2',4,'01',2),(237,'Engineering Geology',5,'01',0),(238,'Structural Analysis-2',5,'01',0),(239,'Design & Drawing of Reinforced Concrete Structures',5,'01',0),(240,'Geotechnical Engineering-1',5,'01',0),(241,'Transportation Engineering-1',5,'01',0),(242,'IPR & patents',5,'01',0),(243,'Geotechnical Engineering lab',5,'01',0),(244,'Engineering Geology lab',5,'01',0),(245,'Design & Drawing of steel structures ',6,'01',0),(246,'Geotechnical Engineering-2',6,'01',0),(247,'Water Resources Engineering',6,'01',0),(248,'EnvironmentalEngineering-1',6,'01',0),(249,'Transportation Engineering-2',6,'01',0),(250,'Open Elective',6,'01',0),(251,'Computer Aided Engineering Drawing',6,'01',0),(252,'Transporation Engineering lab',6,'01',0),(253,'Environmental Engineering-2',7,'01',0),(254,'Prestressed Concrete',7,'01',0),(255,'Contructive Technology and Management',7,'01',0),(256,'Water Resources Engg-2',7,'01',0),(257,'Remote Sensing and GIS Applications',7,'01',0),(258,'elective-1',7,'01',0),(259,'Environmental Engineering lab',7,'01',0),(260,'GIS & CAD lab',7,'01',0),(261,'Estimating,Specifications & contrcats',8,'01',3),(262,'elective-2',8,'01',3),(263,'elective-3',8,'01',3),(264,'elective-4',8,'01',3),(265,'Project work',8,'01',9),(266,'English-1',1,'02',0),(267,'Mathematics-1',1,'02',0),(268,'Mathematics-2',1,'02',0),(269,'Engineering physics',1,'02',0),(270,'Professional Ethics and Human Values',1,'02',0),(271,'Engineering Drawing',1,'02',0),(272,'English-Communication Skills lab-1',1,'02',0),(273,'Engg physics lab',1,'02',0),(274,'Engg Workshop & IT Workshop',1,'02',0),(275,'English-2',2,'02',0),(276,'Mathematics-2',2,'02',0),(277,'Engineering Chemistry',2,'02',0),(278,'Engineering Mechanics',2,'02',0),(279,'Computer Programming',2,'02',0),(280,'Network Analysis',2,'02',0),(281,'Engg Chemistry lab',2,'02',0),(282,'English Communication Skills lab-2',2,'02',0),(283,'Computer Programming Lab',2,'02',0),(284,'Electrical Circuit Analysis-2',3,'02',0),(285,'Thermal & Hydro Prime movers',3,'02',0),(286,'Basic Electronics and devices',3,'02',0),(287,'Complex variables and statistical methods',3,'02',0),(288,'Electro Magnetic Fields',3,'02',0),(289,'Electrical Machines-1',3,'02',0),(290,'Thermal and Hydro lab',3,'02',0),(291,'Electrical Circuits lab',3,'02',0),(292,'Environmental Studies',4,'02',0),(293,'Switching Theory and logic design',4,'02',0),(294,'Pulse & Digital Circuits',4,'02',0),(295,'Power Systems-1',4,'02',0),(296,'Electrical Machines-2',4,'02',0),(297,'Control Systems',4,'02',0),(298,'Electrical Machines-1 lab',4,'02',0),(299,'Electronic Devices & Circuit lab',4,'02',0),(300,'Managerial Economics and Financial Analysis',5,'02',0),(301,'Electrical Measurements',5,'02',0),(302,'power Systems-2',5,'02',0),(303,'Electrical Machines-3',5,'02',0),(304,'Power Electronics',5,'02',0),(305,'Linear & Digital IC Applications',5,'02',0),(306,'Electrical Machines-2 lab',5,'02',0),(307,'Control Systems lab',5,'02',0),(308,'IPR & Patents',5,'02',0),(309,'Switch gear and Protection',6,'02',0),(310,'Microprocessors & Microcontrollers',6,'02',0),(311,'Utilization of electrical energy',6,'02',0),(312,'power system analysis',6,'02',0),(313,'power semiconductor Drives',6,'02',0),(314,'Management Science',6,'02',0),(315,'Power Electronics lab',6,'02',0),(316,'Electrical Measurements lab',6,'02',0),(317,'Renewable Energy sourcesand systems',7,'02',0),(318,'HVAC & DC Transmission',7,'02',0),(319,'power system operation & control',7,'02',0),(320,'open elective',7,'02',0),(321,'elective-1',7,'02',0),(322,'Microprocessors & Microcontrollers lab',7,'02',0),(323,'Electrical Simulation lab',7,'02',0),(324,'Power systems lab',7,'02',0),(325,'Digital Control Systems',8,'02',0),(326,'elective-2',8,'02',0),(327,'elective-3',8,'02',0),(328,'elective-4',8,'02',0),(329,'Project',8,'02',0),(330,'English-1',1,'03',3),(331,'Mathematics-1',1,'03',3),(332,'Engineering Chemistry',1,'03',3),(333,'Engineering Mechanics',1,'03',3),(334,'Computer Programing',1,'03',3),(335,'Environmental Studies',1,'03',3),(336,'Engineering Chemistry laboratory',1,'03',2),(337,'English communcation skills lab-1',1,'03',2),(338,'C Programming lab',1,'03',2),(339,'English-2',2,'03',3),(340,'Mathematics-2',2,'03',3),(341,'Mathematics-3',2,'03',3),(342,'Engineering Physics',2,'03',3),(343,'Professional Ethics & Human Values',2,'03',3),(344,'Engineering Drawing',2,'03',3),(345,'English Communication Skills lab-2',2,'03',2),(346,'Engineering Physics lab',2,'03',2),(347,'Engg. Workshop & IT Workshop',2,'03',2),(348,'Metallurgy & Material Science',3,'03',3),(349,'Mechanics of solids',3,'03',3),(350,'Thermodynamics',3,'03',3),(351,'Managerial Economics & Financial Analysis',3,'03',3),(352,'Basic Electrical & Electronics Engg',3,'03',3),(353,'Computer aided Engg Drawing practice',3,'03',2),(354,'Basic Electrical & Electronics Engg lab',3,'03',2),(355,'Mechanics of solids & Metallurgy lab',3,'03',2),(356,'Kinematics of Machinery',4,'03',3),(357,'Thermal Engg-1',4,'03',3),(358,'Production Technology',4,'03',3),(359,'Fluid Mechanics & Hydraulic Machinery',4,'03',3),(360,'Machine Drawing',4,'03',3),(361,'Fluid Mechanics & Hydraulic Machinery lab',4,'03',2),(362,'Production Technology lab',4,'03',2),(363,'Thermal Engg lab',4,'03',2),(364,'Dynamics of manchinery',5,'03',3),(365,'Metal cutting & machine tools',5,'03',3),(366,'Design of machine members-1',5,'03',3),(367,'Instrumentation & Control systems',5,'03',3),(368,'Thermal engg-2',5,'03',3),(369,'Metrology',5,'03',3),(370,'Metrology & Instrumentation lab',5,'03',2),(371,'Machine tools lab',5,'03',2),(372,'IPR & Patents',5,'03',2),(373,'Operations Research',6,'03',3),(374,'Interactive Computer Graphics',6,'03',3),(375,'Design of machine Memebrs-2',6,'03',3),(376,'Robotics',6,'03',3),(377,'Heat Transfer',6,'03',3),(378,'Industrial Engg Management',6,'03',2),(379,'Departmental elective-1',6,'03',2),(380,'Heat Transfer lab',6,'03',2),(381,'Automobile Engineering',7,'03',3),(382,'CAD/CAM',7,'03',3),(383,'Finite Element Methods',7,'03',3),(384,'Unconventional Machining Processes',7,'03',3),(385,'open elective',7,'03',3),(386,'Departmental elective-2',7,'03',2),(387,'Simulation lab',7,'03',2),(388,'Design/Fabrication project',7,'03',2),(389,'Elective-3',8,'03',3),(390,'Elective-4',8,'03',3),(391,'Production planning and control',8,'03',3),(392,'Green Engineering systems',8,'03',3),(393,'Project',8,'03',9);
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `is_active` varchar(5) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `user_desc` varchar(100) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `faculty_id` int(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `last_login` timestamp NULL DEFAULT NULL,
  `failed_login_attempts` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `users_ibfk_1_idx` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','084e0343a0486ff05530df6c705c8bb4','Y',1,'Admin User',NULL,NULL,NULL,'2019-03-13 13:48:22',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `years`
--

DROP TABLE IF EXISTS `years`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `years` (
  `year_no` int(11) NOT NULL AUTO_INCREMENT,
  `year_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`year_no`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `years`
--

LOCK TABLES `years` WRITE;
/*!40000 ALTER TABLE `years` DISABLE KEYS */;
INSERT INTO `years` VALUES (1,'1 Year'),(2,'2 Year'),(3,'3 Year'),(4,'4 Year');
/*!40000 ALTER TABLE `years` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-15 12:29:37
