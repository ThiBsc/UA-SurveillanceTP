-- MySQL dump 10.15  Distrib 10.0.33-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ua_surveillance
-- ------------------------------------------------------
-- Server version	10.0.33-MariaDB-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ENSEIGNANT`
--

DROP TABLE IF EXISTS `ENSEIGNANT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ENSEIGNANT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ENSEIGNANT`
--

LOCK TABLES `ENSEIGNANT` WRITE;
/*!40000 ALTER TABLE `ENSEIGNANT` DISABLE KEYS */;
INSERT INTO `ENSEIGNANT` VALUES (1,'Toto','Paul'),(2,'Titi','Jean'),(3,'Tutu','Marc');
/*!40000 ALTER TABLE `ENSEIGNANT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EVENEMENT`
--

DROP TABLE IF EXISTS `EVENEMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EVENEMENT` (
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='type:\n-screen\n-directory\n-usb\n-network';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EVENEMENT`
--

LOCK TABLES `EVENEMENT` WRITE;
/*!40000 ALTER TABLE `EVENEMENT` DISABLE KEYS */;
INSERT INTO `EVENEMENT` VALUES ('DIRECTORY'),('NETWORK'),('SCREEN'),('USB');
/*!40000 ALTER TABLE `EVENEMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EXAMEN`
--

DROP TABLE IF EXISTS `EXAMEN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EXAMEN` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `duree` varchar(10) NOT NULL,
  `ENSEIGNANT_id` int(11) NOT NULL,
  `MATIERE_id` int(11) NOT NULL,
  `PROMOTION_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`ENSEIGNANT_id`,`MATIERE_id`,`PROMOTION_id`),
  KEY `fk_EXAMEN_ENSEIGNANT_idx` (`ENSEIGNANT_id`),
  KEY `fk_EXAMEN_MATIERE1_idx` (`MATIERE_id`),
  KEY `fk_EXAMEN_PROMOTION1_idx` (`PROMOTION_id`),
  CONSTRAINT `fk_EXAMEN_ENSEIGNANT` FOREIGN KEY (`ENSEIGNANT_id`) REFERENCES `ENSEIGNANT` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_EXAMEN_MATIERE1` FOREIGN KEY (`MATIERE_id`) REFERENCES `MATIERE` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_EXAMEN_PROMOTION1` FOREIGN KEY (`PROMOTION_id`) REFERENCES `PROMOTION` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='Table regroupant les examens';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMEN`
--

LOCK TABLES `EXAMEN` WRITE;
/*!40000 ALTER TABLE `EXAMEN` DISABLE KEYS */;
INSERT INTO `EXAMEN` VALUES (1,'2017-12-02 00:00:00','2h',2,1,1),(2,'0000-00-00 00:00:00','4h',1,5,2),(5,'0000-00-00 00:00:00','8h',2,5,4),(6,'0000-00-00 00:00:00','87h',1,2,5),(26,'0000-00-00 00:00:00','kk',1,7,4),(28,'0000-00-00 00:00:00','45d',2,7,4),(29,'2018-02-08 00:00:00','1h22min',1,6,3);
/*!40000 ALTER TABLE `EXAMEN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EXAMEN_has_EVENEMENT`
--

DROP TABLE IF EXISTS `EXAMEN_has_EVENEMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EXAMEN_has_EVENEMENT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `EXAMEN_id` int(11) NOT NULL,
  `EVENEMENT_type` varchar(15) NOT NULL,
  `etu_nom` varchar(30) NOT NULL,
  `etu_prenom` varchar(30) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `other` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_EXAMEN_has_EVENEMENT_EVENEMENT1_idx` (`EVENEMENT_type`),
  KEY `fk_EXAMEN_has_EVENEMENT_EXAMEN1_idx` (`EXAMEN_id`),
  CONSTRAINT `fk_EXAMEN_has_EVENEMENT_EVENEMENT1` FOREIGN KEY (`EVENEMENT_type`) REFERENCES `EVENEMENT` (`type`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_EXAMEN_has_EVENEMENT_EXAMEN1` FOREIGN KEY (`EXAMEN_id`) REFERENCES `EXAMEN` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COMMENT='Table des évenements suspects\nexemple:\nexamen d''id 1, l''étudiant Prenom Nom à un évenement de type network.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMEN_has_EVENEMENT`
--

LOCK TABLES `EXAMEN_has_EVENEMENT` WRITE;
/*!40000 ALTER TABLE `EXAMEN_has_EVENEMENT` DISABLE KEYS */;
INSERT INTO `EXAMEN_has_EVENEMENT` VALUES (98,5,'USB','woop','jery','2018-02-08 14:35:57','CONNECTED'),(99,5,'USB','woop','jery','2018-02-08 14:36:09','DISCONNECTED'),(100,5,'NETWORK','woop','jery','2018-02-08 14:36:09','0.docs.google.com.'),(101,5,'NETWORK','woop','jery','2018-02-08 14:36:18','facebook.com.'),(102,28,'USB','poupou','titi','2018-02-08 14:42:37','CONNECTED'),(103,28,'USB','poupou','titi','2018-02-08 14:42:51','DISCONNECTED'),(104,2,'NETWORK','criquet','jimini','2018-02-08 15:04:51','0-edge-chat.facebook.com.'),(105,2,'NETWORK','criquet','jimini','2018-02-08 15:04:51','www.facebook.com.'),(106,2,'USB','Leblanc','Maxime','2018-02-08 15:05:05','CONNECTED'),(107,2,'NETWORK','Leblanc','Maxime','2018-02-08 15:05:07','pixel.facebook.com.'),(108,2,'NETWORK','Leblanc','Maxime','2018-02-08 15:05:07','pixel.facebook.com.'),(109,2,'USB','Leblanc','Maxime','2018-02-08 15:05:25','DISCONNECTED'),(110,29,'NETWORK','leblanc','juste','2018-02-08 15:30:07','www.facebook.com.'),(111,29,'NETWORK','leblanc','juste','2018-02-08 15:30:07','facebook.com.');
/*!40000 ALTER TABLE `EXAMEN_has_EVENEMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EXAMEN_has_VIDEO_PATH`
--

DROP TABLE IF EXISTS `EXAMEN_has_VIDEO_PATH`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EXAMEN_has_VIDEO_PATH` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `EXAMEN_id` int(11) NOT NULL,
  `VIDEO_PATH_nom` varchar(30) NOT NULL,
  `etu_nom` varchar(30) NOT NULL,
  `etu_prenom` varchar(30) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `etu_hostname` varchar(30) DEFAULT NULL,
  `etu_ip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_EXAMEN_has_VIDEO_PATH_EXAMEN1_idx` (`EXAMEN_id`),
  KEY `fk_EXAMEN_has_VIDEO_PATH_VIDEO_PATH1_idx` (`VIDEO_PATH_nom`),
  CONSTRAINT `fk_EXAMEN_has_VIDEO_PATH_EXAMEN1` FOREIGN KEY (`EXAMEN_id`) REFERENCES `EXAMEN` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_EXAMEN_has_VIDEO_PATH_VIDEO_PATH1` FOREIGN KEY (`VIDEO_PATH_nom`) REFERENCES `VIDEO_PATH` (`nom`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMEN_has_VIDEO_PATH`
--

LOCK TABLES `EXAMEN_has_VIDEO_PATH` WRITE;
/*!40000 ALTER TABLE `EXAMEN_has_VIDEO_PATH` DISABLE KEYS */;
INSERT INTO `EXAMEN_has_VIDEO_PATH` VALUES (14,5,'Test','woop','jery','2018-02-08 14:35:44','',''),(15,28,'Test','poupou','titi','2018-02-08 14:42:26','',''),(16,2,'Test','criquet','jimini','2018-02-08 15:04:18','',''),(17,2,'Test','Leblanc','Maxime','2018-02-08 15:05:05','',''),(18,29,'Test','leblanc','juste','2018-02-08 15:28:59','','');
/*!40000 ALTER TABLE `EXAMEN_has_VIDEO_PATH` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MATIERE`
--

DROP TABLE IF EXISTS `MATIERE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MATIERE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='Table regroupant les matières';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MATIERE`
--

LOCK TABLES `MATIERE` WRITE;
/*!40000 ALTER TABLE `MATIERE` DISABLE KEYS */;
INSERT INTO `MATIERE` VALUES (1,'IOT'),(2,'BDD'),(3,'Management de projet'),(4,'Android'),(5,'Programmation parallèle'),(6,'Fouille de données'),(7,'SECURITE');
/*!40000 ALTER TABLE `MATIERE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROMOTION`
--

DROP TABLE IF EXISTS `PROMOTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROMOTION` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROMOTION`
--

LOCK TABLES `PROMOTION` WRITE;
/*!40000 ALTER TABLE `PROMOTION` DISABLE KEYS */;
INSERT INTO `PROMOTION` VALUES (1,'M1 INFO'),(2,'LP LL'),(3,'M2 ID/ACDI'),(4,'L2 INFO'),(5,'L3 INFO');
/*!40000 ALTER TABLE `PROMOTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VIDEO_PATH`
--

DROP TABLE IF EXISTS `VIDEO_PATH`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VIDEO_PATH` (
  `nom` varchar(30) NOT NULL,
  `path` varchar(100) NOT NULL,
  PRIMARY KEY (`nom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Table ayant le chemin ou s''enregistre les vidéo\nEx: nom:default, path:/exam/video/';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VIDEO_PATH`
--

LOCK TABLES `VIDEO_PATH` WRITE;
/*!40000 ALTER TABLE `VIDEO_PATH` DISABLE KEYS */;
INSERT INTO `VIDEO_PATH` VALUES ('Default','/examen/video/'),('Test','/var/www/html/tmp/');
/*!40000 ALTER TABLE `VIDEO_PATH` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-08 16:34:19
