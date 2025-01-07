-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: forohub
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `documento_identidad` varchar(14) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `calle` varchar(100) NOT NULL,
  `distrito` varchar(100) NOT NULL,
  `complemento` varchar(100) DEFAULT NULL,
  `numero` varchar(20) DEFAULT NULL,
  `ciudad` varchar(100) NOT NULL,
  `activo` tinyint NOT NULL,
  `perfil` varchar(100) NOT NULL,
  `login` varchar(100) NOT NULL,
  `clave` varchar(300) NOT NULL,
  `foto_Url` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `documento_identidad` (`documento_identidad`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Diego Martínez','diegoa.martinez@forohub.com','79838301','1451123420','Avenida 3','distrito 3','i','13','bogota',1,'ADMINISTRADOR','diegoa.martinez','$2a$10$2/XKk9.xksWN2EtupoV.VO6TE/XQG1iXUBCH4ZBOvUfIb9hBvBGXy','https://media.vogue.mx/photos/66b3de4d6f2542a25da55a86/2:3/w_1600,c_limit/daenerys-targaryen.jpg'),(4,'Laura Lopez','laura@forohub.com','79118301','2612123420','Avenida 7 ','distrito 4','a','4','bogota',0,'ALUMNO','Laura.Lopez','$2a$10$7U1EB8zcojDE4.JdTnKIQ.lrZ/uXT.II/9IR0hUmLLaJKngulHvda','https://media.vogue.mx/photos/66b3de4d6f2542a25da55a86/2:3/w_1600,c_limit/daenerys-targaryen.jpg'),(6,'Dayana Bernal','dayana@forohub.com','78118301','2912123420','Avenida 6 ','distrito 5','a','4','bogota',1,'ALUMNO','Dayana.Bernal','$2a$10$mOE.ypLQNjN1fTJvcODVwOJTQ0csWwCUdrKaXj.Da.d1q3K52/sBa','https://media.vogue.mx/photos/66b3de4d6f2542a25da55a86/2:3/w_1600,c_limit/daenerys-targaryen.jpg'),(7,'Juan Tapias','juan@forohub.com','77218301','3012123420','Avenida 5 ','distrito 4','a','4','bogota',1,'ALUMNO','Juan.Tapias','$2a$10$jW/q8WLsmYvSki10vyX5XuSp4FNc3oylD4zX0vow6iTbkKKEQDbUG','https://media.vogue.mx/photos/66b3de4d6f2542a25da55a86/2:3/w_1600,c_limit/daenerys-targaryen.jpg'),(8,'Bruno Dario','bruno@forohub.com','72218301','3312123420','Avenida 2 ','distrito 2','a','4','bogota',1,'INSTRUCTOR','Bruno.Dario','$2a$10$qTNgzcj5nO1jgztekMsBNeuqyl.68hXsro8rD6FQywB8PR7qkSNVi','https://media.vogue.mx/photos/66b3de4d6f2542a25da55a86/2:3/w_1600,c_limit/daenerys-targaryen.jpg'),(9,'Génesys Róndon','genesys@forohub.com','75218301','3212123420','Avenida 3 ','distrito 3','a','4','bogota',1,'INSTRUCTOR','Genesys.Rondon','$2a$10$me/iWZ100o0cMWUe.7YDeerqzHiAff.NEWEyk8CmSajEEpMHKR70u','https://media.vogue.mx/photos/66b3de4d6f2542a25da55a86/2:3/w_1600,c_limit/daenerys-targaryen.jpg');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-07  7:59:26
