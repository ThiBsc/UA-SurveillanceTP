-- MySQL dump 10.15  Distrib 10.0.31-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ua_surveillance
-- ------------------------------------------------------
-- Server version	10.0.31-MariaDB-0ubuntu0.16.04.2

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
INSERT INTO `ENSEIGNANT` VALUES (1,'Barichard','Vincent'),(2,'Genest','David'),(3,'Richer','Jean-Michel');
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='Table regroupant les examens';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMEN`
--

LOCK TABLES `EXAMEN` WRITE;
/*!40000 ALTER TABLE `EXAMEN` DISABLE KEYS */;
INSERT INTO `EXAMEN` VALUES (7,'2017-12-13 00:00:00','1h',1,1,1),(8,'2017-12-13 00:00:00','1h',1,1,1),(9,'2017-12-13 00:00:00','1h',1,1,1),(10,'2017-12-13 00:00:00','1h',1,1,1),(11,'2017-12-13 00:00:00','1h',1,1,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=1220 DEFAULT CHARSET=utf8mb4 COMMENT='Table des évenements suspects\nexemple:\nexamen d''id 1, l''étudiant Prenom Nom à un évenement de type network.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMEN_has_EVENEMENT`
--

LOCK TABLES `EXAMEN_has_EVENEMENT` WRITE;
/*!40000 ALTER TABLE `EXAMEN_has_EVENEMENT` DISABLE KEYS */;
INSERT INTO `EXAMEN_has_EVENEMENT` VALUES (813,7,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:16:37','ENTRY_CREATE;/home/etudiant/fichier;0'),(814,7,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:16:37','ENTRY_MODIFY;/home/etudiant/fichier;0'),(815,7,'USB','Gourdon','Charly','2017-12-12 16:17:03','DISCONNECTED'),(816,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','zimbra.univ-angers.fr.'),(817,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','zimbra.univ-angers.fr.'),(818,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','zimbra.univ-angers.fr.'),(819,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','1.144.49.193.in-addr.arpa.'),(820,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','192.43.168.192.in-addr.arpa.'),(821,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','0/0/0'),(822,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','192.43.168.192.in-addr.arpa.'),(823,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','0/0/0'),(824,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','8.8.8.8.in-addr.arpa.'),(825,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','0/0/0'),(826,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','8.8.8.8.in-addr.arpa.'),(827,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','251.0.0.224.in-addr.arpa.'),(828,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','0/0/0'),(829,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','251.0.0.224.in-addr.arpa.'),(830,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','0/1/0'),(831,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','33.43.168.192.in-addr.arpa.'),(832,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','0/0/0'),(833,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','33.43.168.192.in-addr.arpa.'),(834,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:03','0/0/0'),(835,7,'USB','Gourdon','Charly','2017-12-12 16:17:03','DISCONNECTED'),(836,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','length'),(837,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','length'),(838,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','199.89.189.91.in-addr.arpa.'),(839,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','33.43.168.192.in-addr.arpa.'),(840,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','1.43.168.192.in-addr.arpa.'),(841,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','0/1/0'),(842,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','251.0.0.224.in-addr.arpa.'),(843,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','0/1/0'),(844,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(845,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','0/1/0'),(846,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','8.4.3.b.b.2.1.d.e.c.a.2.e.d.2.e.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(847,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','0/1/0'),(848,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:04','255.43.168.192.in-addr.arpa.'),(849,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(850,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','0/0/0'),(851,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(852,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','0/1/0'),(853,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(854,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','0/0/0'),(855,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(856,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','0/1/0'),(857,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','251.0.0.224.in-addr.arpa.'),(858,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','0/0/0'),(859,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','251.0.0.224.in-addr.arpa.'),(860,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:06','0/1/0'),(861,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:07','192.43.168.192.in-addr.arpa.'),(862,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:07','0/0/0'),(863,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:07','192.43.168.192.in-addr.arpa.'),(864,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:07','0/0/0'),(865,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:07','1.144.49.193.in-addr.arpa.'),(866,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:07','8.8.8.8.in-addr.arpa.'),(867,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:07','0/0/0'),(868,7,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:17:08','ENTRY_MODIFY;/home/etudiant/fichier;6'),(869,7,'USB','Gourdon','Charly','2017-12-12 16:17:09','CONNECTED'),(870,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:09','255.255.255.255.in-addr.arpa.'),(871,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:09','192.43.168.192.in-addr.arpa.'),(872,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:10','length'),(873,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:10','length'),(874,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:10','1.43.168.192.in-addr.arpa.'),(875,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:10','0/0/0'),(876,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:10','33.43.168.192.in-addr.arpa.'),(877,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:10','199.89.189.91.in-addr.arpa.'),(878,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:10','255.43.168.192.in-addr.arpa.'),(879,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:10','0/0/0'),(880,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:10','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(881,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','255.43.168.192.in-addr.arpa.'),(882,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','0/0/0'),(883,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','255.43.168.192.in-addr.arpa.'),(884,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','0/0/0'),(885,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','192.43.168.192.in-addr.arpa.'),(886,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','0/0/0'),(887,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','192.43.168.192.in-addr.arpa.'),(888,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','0/0/0'),(889,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(890,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','0/0/0'),(891,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(892,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','0/1/0'),(893,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','1.144.49.193.in-addr.arpa.'),(894,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:11','8.8.8.8.in-addr.arpa.'),(895,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','255.255.255.255.in-addr.arpa.'),(896,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','0/0/0'),(897,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','255.255.255.255.in-addr.arpa.'),(898,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','0/1/0'),(899,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','192.43.168.192.in-addr.arpa.'),(900,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','0/0/0'),(901,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','192.43.168.192.in-addr.arpa.'),(902,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','db5-client-s.gateway.messenger.live.com.'),(903,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','db5-client-s.gateway.messenger.live.com.'),(904,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','0/0/0'),(905,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','251.0.0.224.in-addr.arpa.'),(906,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','0/0/0'),(907,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','0/0/0'),(908,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','db5-client-s.gateway.messenger.live.com.'),(909,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','db5-client-s.gateway.messenger.live.com.'),(910,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','0/0/0'),(911,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','251.0.0.224.in-addr.arpa.'),(912,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','0/1/0'),(913,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:15','1.144.49.193.in-addr.arpa.'),(914,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:27','ntp.ubuntu.com.'),(915,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:27','ntp.ubuntu.com.'),(916,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:27','0/0/0'),(917,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:27','0/0/0'),(918,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:27','ntp.ubuntu.com.'),(919,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','ntp.ubuntu.com.'),(920,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','length'),(921,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','length'),(922,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','1.144.49.193.in-addr.arpa.'),(923,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','192.43.168.192.in-addr.arpa.'),(924,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','0/0/0'),(925,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','192.43.168.192.in-addr.arpa.'),(926,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','0/0/0'),(927,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','8.8.8.8.in-addr.arpa.'),(928,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','0/0/0'),(929,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','8.8.8.8.in-addr.arpa.'),(930,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','4.94.189.91.in-addr.arpa.'),(931,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','0/0/0'),(932,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','4.94.189.91.in-addr.arpa.'),(933,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:28','www.google.fr.'),(934,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:35','https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html'),(935,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','ssl.gstatic.com.'),(936,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','ssl.gstatic.com.'),(937,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','lh3.googleusercontent.com.'),(938,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','lh3.googleusercontent.com.'),(939,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','1.144.49.193.in-addr.arpa.'),(940,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','0/0/0'),(941,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','0/0/0'),(942,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','lh3.googleusercontent.com.'),(943,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','lh3.googleusercontent.com.'),(944,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','www.google.com.'),(945,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','www.google.com.'),(946,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','0/0/0'),(947,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','0/0/0'),(948,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','ssl.gstatic.com.'),(949,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','ssl.gstatic.com.'),(950,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','192.43.168.192.in-addr.arpa.'),(951,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','www.gstatic.com.'),(952,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','www.gstatic.com.'),(953,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','0/0/0'),(954,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:36','0/0/0'),(955,7,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:17:40','ENTRY_DELETE;/home/etudiant/fichier;0'),(956,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:41','https://docs.oracle.com/javase/7/docs/api/java/util/AbstractCollection.html'),(957,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(958,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','0/0/0'),(959,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(960,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','0/0/0'),(961,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','251.0.0.224.in-addr.arpa.'),(962,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','0/0/0'),(963,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','192.43.168.192.in-addr.arpa.'),(964,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','1.43.168.192.in-addr.arpa.'),(965,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','0/0/0'),(966,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','33.43.168.192.in-addr.arpa.'),(967,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','255.255.255.255.in-addr.arpa.'),(968,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','length'),(969,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','length'),(970,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','199.89.189.91.in-addr.arpa.'),(971,7,'NETWORK','Deramaix','Jonathan','2017-12-12 16:17:51','www.wikipedia.org.'),(972,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:55','ocsp.digicert.com.'),(973,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:55','1.144.49.193.in-addr.arpa.'),(974,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:55','0/0/0'),(975,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:55','ocsp.digicert.com.'),(976,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:55','192.43.168.192.in-addr.arpa.'),(977,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:55','0/0/0'),(978,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:55','192.43.168.192.in-addr.arpa.'),(979,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','0/0/0'),(980,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','8.8.8.8.in-addr.arpa.'),(981,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','0/0/0'),(982,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','8.8.8.8.in-addr.arpa.'),(983,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','outlook.live.com.'),(984,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','outlook.live.com.'),(985,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','0/0/0'),(986,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','0/0/0'),(987,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','outlook.live.com.'),(988,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','outlook.live.com.'),(989,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','browser.pipe.aria.microsoft.com.'),(990,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','browser.pipe.aria.microsoft.com.'),(991,7,'NETWORK','Gourdon','Charly','2017-12-12 16:17:56','browser.pipe.aria.microsoft.com.'),(992,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','browser.pipe.aria.microsoft.com.'),(993,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','browser.pipe.aria.microsoft.com.'),(994,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','browser.pipe.aria.microsoft.com.'),(995,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','8.8.8.8.in-addr.arpa.'),(996,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','0/0/0'),(997,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','8.8.8.8.in-addr.arpa.'),(998,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','192.43.168.192.in-addr.arpa.'),(999,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','0/0/0'),(1000,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','192.43.168.192.in-addr.arpa.'),(1001,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','0/0/0'),(1002,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','1.144.49.193.in-addr.arpa.'),(1003,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','250.255.255.239.in-addr.arpa.'),(1004,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','0/0/0'),(1005,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','250.255.255.239.in-addr.arpa.'),(1006,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','0/1/0'),(1007,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','64.43.168.192.in-addr.arpa.'),(1008,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','0/0/0'),(1009,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','64.43.168.192.in-addr.arpa.'),(1010,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:03','0/0/0'),(1011,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','250.255.255.239.in-addr.arpa.'),(1012,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','0/0/0'),(1013,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','250.255.255.239.in-addr.arpa.'),(1014,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','0/1/0'),(1015,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','64.43.168.192.in-addr.arpa.'),(1016,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','0/0/0'),(1017,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','64.43.168.192.in-addr.arpa.'),(1018,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','0/0/0'),(1019,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','1.144.49.193.in-addr.arpa.'),(1020,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','192.43.168.192.in-addr.arpa.'),(1021,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','0/0/0'),(1022,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','192.43.168.192.in-addr.arpa.'),(1023,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','0/0/0'),(1024,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','8.8.8.8.in-addr.arpa.'),(1025,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','0/0/0'),(1026,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','8.8.8.8.in-addr.arpa.'),(1027,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','db5-client-s.gateway.messenger.live.com.'),(1028,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','db5-client-s.gateway.messenger.live.com.'),(1029,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','0/0/0'),(1030,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','0/0/0'),(1031,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','db5-client-s.gateway.messenger.live.com.'),(1032,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:18','db5-client-s.gateway.messenger.live.com.'),(1033,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','play.google.com.'),(1034,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','play.google.com.'),(1035,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','play.google.com.'),(1036,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','0/0/0'),(1037,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','0/0/0'),(1038,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','0/0/0'),(1039,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','play.google.com.'),(1040,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','play.google.com.'),(1041,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','play.google.com.'),(1042,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','1.144.49.193.in-addr.arpa.'),(1043,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','192.43.168.192.in-addr.arpa.'),(1044,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','0/0/0'),(1045,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','192.43.168.192.in-addr.arpa.'),(1046,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','0/0/0'),(1047,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','8.8.8.8.in-addr.arpa.'),(1048,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','0/0/0'),(1049,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','8.8.8.8.in-addr.arpa.'),(1050,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','safebrowsing.googleapis.com.'),(1051,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','0/0/0'),(1052,7,'NETWORK','Gourdon','Charly','2017-12-12 16:18:30','safebrowsing.googleapis.com.'),(1053,7,'DIRECTORY','Gourdon','Charly','2017-12-12 16:18:42','ENTRY_CREATE;/home/etudiant/Documents/test.txt;0'),(1054,7,'DIRECTORY','Gourdon','Charly','2017-12-12 16:18:42','ENTRY_MODIFY;/home/etudiant/Documents/test.txt;0'),(1055,7,'DIRECTORY','Gourdon','Charly','2017-12-12 16:19:04','ENTRY_DELETE;/home/etudiant/Documents/test.txt;0'),(1056,7,'USB','gourdon','charly','2017-12-12 16:26:04','DISCONNECTED'),(1057,7,'USB','gourdon','charly','2017-12-12 16:26:08','CONNECTED'),(1058,7,'NETWORK','gourdon','charly','2017-12-12 16:26:32','https://docs.oracle.com/javase/7/docs/api/java/util/AbstractQueue.html'),(1059,8,'USB','Gourdon','Charly','2017-12-12 16:30:36','DISCONNECTED'),(1060,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','255.43.168.192.in-addr.arpa.'),(1061,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','0/0/0'),(1062,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','192.43.168.192.in-addr.arpa.'),(1063,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(1064,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','0/0/0'),(1065,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','1.43.168.192.in-addr.arpa.'),(1066,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','0/0/0'),(1067,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','33.43.168.192.in-addr.arpa.'),(1068,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(1069,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','0/1/0'),(1070,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','251.0.0.224.in-addr.arpa.'),(1071,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:38','0/0/0'),(1072,8,'USB','Gourdon','Charly','2017-12-12 16:30:41','CONNECTED'),(1073,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','255.255.255.255.in-addr.arpa.'),(1074,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','192.43.168.192.in-addr.arpa.'),(1075,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','1.43.168.192.in-addr.arpa.'),(1076,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','0/0/0'),(1077,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','33.43.168.192.in-addr.arpa.'),(1078,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','255.43.168.192.in-addr.arpa.'),(1079,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','0/0/0'),(1080,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(1081,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','0/0/0'),(1082,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(1083,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:30:43','0/0/0'),(1084,8,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:30:46','ENTRY_CREATE;/home/etudiant/fichier;0'),(1085,8,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:30:46','ENTRY_MODIFY;/home/etudiant/fichier;0'),(1086,8,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:30:54','ENTRY_MODIFY;/home/etudiant/fichier;7'),(1087,8,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:30:57','ENTRY_DELETE;/home/etudiant/fichier;0'),(1088,8,'NETWORK','Gourdon','Charly','2017-12-12 16:31:25','https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html'),(1089,8,'NETWORK','Gourdon','Charly','2017-12-12 16:31:31','https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html'),(1090,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','255.255.255.255.in-addr.arpa.'),(1091,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','192.43.168.192.in-addr.arpa.'),(1092,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','1.43.168.192.in-addr.arpa.'),(1093,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','0/0/0'),(1094,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','33.43.168.192.in-addr.arpa.'),(1095,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','www.wikipedia.org.'),(1096,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','www.wikipedia.org.'),(1097,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','upload.wikimedia.org.'),(1098,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','upload.wikimedia.org.'),(1099,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','fr.wikipedia.org.'),(1100,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','fr.wikipedia.org.'),(1101,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','login.wikimedia.org.'),(1102,8,'NETWORK','Deramaix','Jonathan','2017-12-12 16:31:53','login.wikimedia.org.'),(1103,9,'USB','Gourdon','Charly','2017-12-12 16:58:40','DISCONNECTED'),(1104,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','255.43.168.192.in-addr.arpa.'),(1105,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','0/1/0'),(1106,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','192.43.168.192.in-addr.arpa.'),(1107,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(1108,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','0/1/0'),(1109,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','1.43.168.192.in-addr.arpa.'),(1110,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','0/1/0'),(1111,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','33.43.168.192.in-addr.arpa.'),(1112,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(1113,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','0/1/0'),(1114,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:43','251.0.0.224.in-addr.arpa.'),(1115,9,'USB','Gourdon','Charly','2017-12-12 16:58:49','CONNECTED'),(1116,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','255.255.255.255.in-addr.arpa.'),(1117,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','192.43.168.192.in-addr.arpa.'),(1118,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','1.43.168.192.in-addr.arpa.'),(1119,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','0/0/0'),(1120,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','33.43.168.192.in-addr.arpa.'),(1121,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','255.43.168.192.in-addr.arpa.'),(1122,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','0/0/0'),(1123,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(1124,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','0/0/0'),(1125,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(1126,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:58:51','0/0/0'),(1127,9,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:59:04','ENTRY_CREATE;/home/etudiant/fichier;0'),(1128,9,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:59:04','ENTRY_MODIFY;/home/etudiant/fichier;0'),(1129,9,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:59:12','ENTRY_MODIFY;/home/etudiant/fichier;6'),(1130,9,'DIRECTORY','Deramaix','Jonathan','2017-12-12 16:59:16','ENTRY_DELETE;/home/etudiant/fichier;0'),(1131,9,'NETWORK','Gourdon','Charly','2017-12-12 16:59:33','https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html'),(1132,9,'NETWORK','Gourdon','Charly','2017-12-12 16:59:38','https://docs.oracle.com/javase/7/docs/technotes/guides/collections/index.html'),(1133,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','255.255.255.255.in-addr.arpa.'),(1134,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','192.43.168.192.in-addr.arpa.'),(1135,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','1.43.168.192.in-addr.arpa.'),(1136,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','0/0/0'),(1137,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','33.43.168.192.in-addr.arpa.'),(1138,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','250.255.255.239.in-addr.arpa.'),(1139,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','0/1/0'),(1140,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','64.43.168.192.in-addr.arpa.'),(1141,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','www.wikipedia.org.'),(1142,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','www.wikipedia.org.'),(1143,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','upload.wikimedia.org.'),(1144,9,'NETWORK','Deramaix','Jonathan','2017-12-12 16:59:47','upload.wikimedia.org.'),(1145,10,'USB','Gourdon','Charly','2017-12-13 12:43:22','DISCONNECTED'),(1146,10,'USB','Gourdon','Charly','2017-12-13 12:43:23','DISCONNECTED'),(1147,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','255.43.168.192.in-addr.arpa.'),(1148,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','0/1/0'),(1149,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','192.43.168.192.in-addr.arpa.'),(1150,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(1151,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','0/1/0'),(1152,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(1153,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','0/1/0'),(1154,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','251.0.0.224.in-addr.arpa.'),(1155,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','0/1/0'),(1156,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','1.43.168.192.in-addr.arpa.'),(1157,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','0/1/0'),(1158,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:43:25','33.43.168.192.in-addr.arpa.'),(1159,10,'USB','Gourdon','Charly','2017-12-13 12:43:26','CONNECTED'),(1160,10,'DIRECTORY','Deramaix','Jonathan','2017-12-13 12:43:35','ENTRY_CREATE;/home/etudiant/fichier;0'),(1161,10,'DIRECTORY','Deramaix','Jonathan','2017-12-13 12:43:35','ENTRY_MODIFY;/home/etudiant/fichier;0'),(1162,10,'DIRECTORY','Deramaix','Jonathan','2017-12-13 12:43:45','ENTRY_MODIFY;/home/etudiant/fichier;4'),(1163,10,'DIRECTORY','Deramaix','Jonathan','2017-12-13 12:43:50','ENTRY_DELETE;/home/etudiant/fichier;0'),(1164,10,'NETWORK','Gourdon','Charly','2017-12-13 12:44:19','https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html'),(1165,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','255.255.255.255.in-addr.arpa.'),(1166,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','192.43.168.192.in-addr.arpa.'),(1167,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','1.43.168.192.in-addr.arpa.'),(1168,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','0/0/0'),(1169,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','33.43.168.192.in-addr.arpa.'),(1170,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','www.wikipedia.org.'),(1171,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','www.wikipedia.org.'),(1172,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','upload.wikimedia.org.'),(1173,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','upload.wikimedia.org.'),(1174,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','fr.wikipedia.org.'),(1175,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','fr.wikipedia.org.'),(1176,10,'NETWORK','Deramaix','Jonathan','2017-12-13 12:44:54','250.255.255.239.in-addr.arpa.'),(1177,11,'USB','Gourdon','Charly','2017-12-13 15:05:08','DISCONNECTED'),(1178,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','255.43.168.192.in-addr.arpa.'),(1179,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','0/1/0'),(1180,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','192.43.168.192.in-addr.arpa.'),(1181,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(1182,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','0/1/0'),(1183,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','1.43.168.192.in-addr.arpa.'),(1184,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','0/1/0'),(1185,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','33.43.168.192.in-addr.arpa.'),(1186,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(1187,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','0/1/0'),(1188,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','251.0.0.224.in-addr.arpa.'),(1189,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:10','0/1/0'),(1190,11,'USB','Gourdon','Charly','2017-12-13 15:05:14','CONNECTED'),(1191,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','255.255.255.255.in-addr.arpa.'),(1192,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','192.43.168.192.in-addr.arpa.'),(1193,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','1.43.168.192.in-addr.arpa.'),(1194,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','0/0/0'),(1195,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','33.43.168.192.in-addr.arpa.'),(1196,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','255.43.168.192.in-addr.arpa.'),(1197,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','0/0/0'),(1198,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','f.5.3.6.0.7.2.5.5.8.5.2.5.e.5.2.0.0.0.0.0.0.0.0.0.0.0.0.0.8.e.f.ip6.arpa.'),(1199,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','0/0/0'),(1200,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','b.f.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.2.0.f.f.ip6.arpa.'),(1201,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:05:16','0/0/0'),(1202,11,'DIRECTORY','Deramaix','Jonathan','2017-12-13 15:05:31','ENTRY_CREATE;/home/etudiant/fichier;0'),(1203,11,'DIRECTORY','Deramaix','Jonathan','2017-12-13 15:05:31','ENTRY_MODIFY;/home/etudiant/fichier;0'),(1204,11,'DIRECTORY','Deramaix','Jonathan','2017-12-13 15:05:37','ENTRY_MODIFY;/home/etudiant/fichier;8'),(1205,11,'DIRECTORY','Deramaix','Jonathan','2017-12-13 15:05:40','ENTRY_DELETE;/home/etudiant/fichier;0'),(1206,11,'NETWORK','Gourdon','Charly','2017-12-13 15:05:58','https://www.google.fr/search?source=hp&ei=XyMxWs2tFIP-aMXokLAC&btnG=Rechercher&q=arraylist'),(1207,11,'NETWORK','Gourdon','Charly','2017-12-13 15:06:01','https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html'),(1208,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','255.255.255.255.in-addr.arpa.'),(1209,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','192.43.168.192.in-addr.arpa.'),(1210,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','1.43.168.192.in-addr.arpa.'),(1211,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','0/0/0'),(1212,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','33.43.168.192.in-addr.arpa.'),(1213,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','250.255.255.239.in-addr.arpa.'),(1214,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','0/1/0'),(1215,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','64.43.168.192.in-addr.arpa.'),(1216,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','www.wikipedia.org.'),(1217,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','www.wikipedia.org.'),(1218,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','www.wikipedia.org.'),(1219,11,'NETWORK','Deramaix','Jonathan','2017-12-13 15:06:14','upload.wikimedia.org.');
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EXAMEN_has_VIDEO_PATH`
--

LOCK TABLES `EXAMEN_has_VIDEO_PATH` WRITE;
/*!40000 ALTER TABLE `EXAMEN_has_VIDEO_PATH` DISABLE KEYS */;
INSERT INTO `EXAMEN_has_VIDEO_PATH` VALUES (21,9,'Test','Deramaix','Jonathan','2017-12-12 16:58:24','',''),(22,9,'Test','Gourdon','Charly','2017-12-12 16:58:25','',''),(23,10,'Test','Deramaix','Jonathan','2017-12-13 12:43:07','',''),(24,10,'Test','Gourdon','Charly','2017-12-13 12:43:08','',''),(25,11,'Test','Gourdon','Charly','2017-12-13 15:04:49','',''),(26,11,'Test','Deramaix','Jonathan','2017-12-13 15:04:52','','');
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='Table regroupant les matières';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MATIERE`
--

LOCK TABLES `MATIERE` WRITE;
/*!40000 ALTER TABLE `MATIERE` DISABLE KEYS */;
INSERT INTO `MATIERE` VALUES (1,'IOT'),(2,'BDD'),(3,'Management de projet'),(4,'Android'),(5,'Programmation parallèle'),(6,'Fouille de données');
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
INSERT INTO `VIDEO_PATH` VALUES ('Default','/examen/video/'),('Test','/tmp/');
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

-- Dump completed on 2017-12-18 18:21:51
