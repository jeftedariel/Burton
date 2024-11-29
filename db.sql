-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: burton
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
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `cart_item_id` int NOT NULL AUTO_INCREMENT,
  `cart_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cart_item_id`),
  KEY `cart_id` (`cart_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`cart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (1,2,3,3,69.00,207.00,'Classic Heather Gray Hoodie','https://i.imgur.com/cHddUCu.jpeg'),(2,2,4,1,90.00,90.00,'Classic Grey Hooded Sweatshirt','https://i.imgur.com/R2PN9Wq.jpeg'),(3,2,7,2,79.00,158.00,'Classic Comfort Drawstring Joggers','https://i.imgur.com/mp3rUty.jpeg'),(4,2,8,4,98.00,392.00,'Classic Red Jogger Sweatpants','https://i.imgur.com/9LFjwpI.jpeg'),(5,2,9,8,61.00,488.00,'Classic Navy Blue Baseball Cap','https://i.imgur.com/R3iobJA.jpeg'),(6,2,39,6,84.00,504.00,'Vibrant Pink Classic Sneakers','https://i.imgur.com/mcW42Gi.jpeg'),(7,2,42,2,53.00,106.00,'Elegant Patent Leather Peep-Toe Pumps with Gold-Tone Heel','https://i.imgur.com/AzAY4Ed.jpeg'),(8,2,34,1,71.00,71.00,'Modern Ergonomic Office Chair','https://i.imgur.com/3dU0m72.jpeg'),(9,3,23,1,43.00,43.00,'Sleek Modern Laptop with Ambient Lighting','https://i.imgur.com/OKn1KFI.jpeg'),(10,4,14,1,43.00,43.00,'Classic High-Waisted Athletic Shorts','https://i.imgur.com/eGOUveI.jpeg'),(11,5,25,2,39.00,78.00,'Stylish Red & Silver Over-Ear Headphones','https://i.imgur.com/YaSqa06.jpeg'),(12,6,15,1,39.00,39.00,'Classic White Crew Neck T-Shirt','https://i.imgur.com/axsyGpD.jpeg'),(13,6,17,2,35.00,70.00,'Classic Black T-Shirt','https://i.imgur.com/9DqEOV5.jpeg'),(14,7,26,1,27.00,27.00,'Sleek Mirror Finish Phone Case','https://i.imgur.com/yb9UQKL.jpeg'),(15,8,47,1,73.00,73.00,'Radiant Citrus Eau de Parfum','https://i.imgur.com/xPDwUb3.jpg');
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `status` enum('Active','Completed') DEFAULT 'Active',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cart_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (1,0,'Active','2024-11-29 05:51:39','2024-11-29 05:51:39'),(2,3,'Completed','2024-11-29 05:51:59','2024-11-29 05:53:09'),(3,2097367643,'Completed','2024-11-29 15:04:12','2024-11-29 15:04:17'),(4,3,'Completed','2024-11-29 15:30:09','2024-11-29 15:30:19'),(5,3,'Completed','2024-11-29 15:46:06','2024-11-29 15:46:15'),(6,3,'Completed','2024-11-29 16:15:28','2024-11-29 16:15:56'),(7,3,'Completed','2024-11-29 16:16:18','2024-11-29 16:16:23'),(8,1,'Completed','2024-11-29 16:17:28','2024-11-29 16:17:34');
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `image` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Clothes','https://i.imgur.com/QkIa5tT.jpeg'),(2,'Electronics','https://i.imgur.com/ZANVnHE.jpeg'),(3,'Change title','https://i.imgur.com/Qphac99.jpeg'),(4,'Shoes','https://i.imgur.com/qNOjJje.jpeg'),(5,'Miscellaneous','https://i.imgur.com/BG8J0Fj.jpg'),(6,'New Category','https://placeimg.com/640/480/any'),(7,'New Category','https://placeimg.com/640/480/any'),(8,'New Category','https://placeimg.com/640/480/any'),(9,'New Category','https://placeimg.com/640/480/any'),(10,'New Category','https://placeimg.com/640/480/any'),(11,'New Category','https://placeimg.com/640/480/any'),(12,'New Category','https://placeimg.com/640/480/any'),(13,'New Category','https://placeimg.com/640/480/any'),(14,'New Category','https://placeimg.com/640/480/any'),(15,'New Category','https://placeimg.com/640/480/any'),(16,'New Category','https://placeimg.com/640/480/any'),(17,'New Category','https://placeimg.com/640/480/any'),(18,'New Category','https://placeimg.com/640/480/any'),(19,'New Category','https://placeimg.com/640/480/any'),(20,'New Category','https://placeimg.com/640/480/any'),(21,'New Category','https://placeimg.com/640/480/any'),(22,'New Category','https://placeimg.com/640/480/any'),(23,'New Category','https://placeimg.com/640/480/any'),(25,'New Category','https://placeimg.com/640/480/any'),(26,'New Category','https://placeimg.com/640/480/any'),(27,'New Category','https://placeimg.com/640/480/any'),(28,'New Category','https://placeimg.com/640/480/any'),(29,'New Category','https://placeimg.com/640/480/any'),(32,'New Category','https://placeimg.com/640/480/any'),(33,'New Category','https://placeimg.com/640/480/any'),(34,'New Category','https://placeimg.com/640/480/any'),(35,'New Category','https://placeimg.com/640/480/any'),(37,'New Category','https://placeimg.com/640/480/any'),(39,'New Category','https://placeimg.com/640/480/any'),(40,'New Category','https://placeimg.com/640/480/any'),(41,'New Category','https://placeimg.com/640/480/any'),(42,'Grosery','https://placeimg.com/640/480/any'),(43,'Computer Category','https://placeimg.com/640/480/any'),(44,'New Category','https://placeimg.com/640/480/any'),(45,'New Category','https://placeimg.com/640/480/any'),(46,'New Category','https://placeimg.com/640/480/any'),(47,'New Category','https://placeimg.com/640/480/any'),(48,'New Category','https://placeimg.com/640/480/any'),(49,'New Category','https://placeimg.com/640/480/any'),(50,'New Category','https://placeimg.com/640/480/any'),(51,'New Category','https://placeimg.com/640/480/any'),(52,'Books','https://api.lorem.space/image/book?w=150&h=220'),(53,'New Category','https://placeimg.com/640/480/any'),(54,'New Category','https://placeimg.com/640/480/any'),(55,'New Category','https://placeimg.com/640/480/any'),(56,'New Category','https://placeimg.com/640/480/any'),(57,'New Category','https://placeimg.com/640/480/any'),(58,'New Category','https://placeimg.com/640/480/any'),(59,'New Category','https://placeimg.com/640/480/any'),(62,'New Category','https://placeimg.com/640/480/any'),(63,'New Category','https://placeimg.com/640/480/any'),(64,'New Category','https://placeimg.com/640/480/any'),(65,'New Category','https://placeimg.com/640/480/any'),(66,'qr','https://api.escuelajs.co/api/v1/files/948a.jpg'),(67,'New Category','https://placeimg.com/640/480/any'),(68,'New Category','https://placeimg.com/640/480/any'),(69,'New Category','https://placeimg.com/640/480/any'),(70,'New Category','https://placeimg.com/640/480/any'),(71,'New Category','https://placeimg.com/640/480/any'),(72,'New Category','https://placeimg.com/640/480/any'),(73,'New Category','https://placeimg.com/640/480/any'),(74,'New Category','https://placeimg.com/640/480/any'),(75,'New Category','https://placeimg.com/640/480/any'),(76,'New Category','https://placeimg.com/640/480/any'),(77,'New Category','https://placeimg.com/640/480/any'),(78,'New Category','https://placeimg.com/640/480/any'),(80,'New Category','https://placeimg.com/640/480/any'),(81,'asd','https://api.escuelajs.co/api/v1/files/65fb.jpg'),(82,'qwe','https://api.escuelajs.co/api/v1/files/5fb9.jpg'),(83,'New Category','https://placeimg.com/640/480/any'),(84,'ghh','https://api.escuelajs.co/api/v1/files/d4e2.jpg'),(85,'New Category','https://placeimg.com/640/480/any'),(86,'New Category','https://placeimg.com/640/480/any'),(87,'New Category','https://placeimg.com/640/480/any'),(88,'New Category','https://placeimg.com/640/480/any'),(89,'MAngwas','https://www.google.com/imgres?q=mangas&imgurl=https%3A%2F%2Fi0.wp.com%2Fcodigoespagueti.com%2Fwp-content%2Fuploads%2F2018%2F12%2Fmangas.png&imgrefurl=https%3A%2F%2Fcodigoespagueti.com%2Fnoticias%2Fcultura%2Festos-fueron-los-mangas-mejor-vendidos-del-2018%2F&docid=ERIas0987WOovM&tbnid=KCgpVn2BcmsPlM&vet=12ahUKEwir8OD5joKKAxUXK0QIHfy0KoUQM3oECBYQAA..i&w=1080&h=608&hcb=2&ved=2ahUKEwir8OD5joKKAxUXK0QIHfy0KoUQM3oECBYQAA'),(90,'New Category','https://placeimg.com/640/480/any'),(91,'New Category','https://placeimg.com/640/480/any'),(92,'New Category','https://placeimg.com/640/480/any'),(93,'New Category','https://placeimg.com/640/480/any');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,3,3,207.00),(2,1,4,1,90.00),(3,1,7,2,158.00),(4,1,8,4,392.00),(5,1,9,8,488.00),(6,1,39,6,504.00),(7,1,42,2,106.00),(8,1,34,1,71.00),(9,2,23,1,43.00),(10,3,14,1,43.00),(11,4,25,2,78.00),(12,5,17,2,70.00),(13,6,26,1,27.00),(14,7,47,1,73.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `total_amount` decimal(10,2) NOT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('Completed','Cancelled') DEFAULT 'Completed',
  `payment_method` enum('Credit Card','PayPal','Cash') DEFAULT 'Credit Card',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,3,2016.00,'2023-11-29 05:53:09','Completed','Credit Card'),(2,2097367643,43.00,'2023-11-29 15:04:17','Completed','Credit Card'),(3,2,43.00,'2023-05-29 15:30:19','Completed','Credit Card'),(4,2,78.00,'2024-01-03 15:46:15','Completed','Credit Card'),(5,1,70.00,'2024-02-14 16:15:56','Completed','Credit Card'),(6,1,27.00,'2024-11-29 16:16:23','Completed','Credit Card'),(7,1,73.00,'2024-11-29 16:17:34','Completed','Credit Card');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `images` text,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (2,'Classic Red Pullover Hoodie',15.00,'Elevate your casual wardrobe with our Classic Red Pullover Hoodie. Crafted with a soft cotton blend for ultimate comfort, this vibrant red hoodie features a kangaroo pocket, adjustable drawstring hood, and ribbed cuffs for a snug fit. The timeless design ensures easy pairing with jeans or joggers for a relaxed yet stylish look, making it a versatile addition to your everyday attire.','https://i.imgur.com/1twoaDy.jpeg,https://i.imgur.com/FDwQgLy.jpeg,https://i.imgur.com/kg1ZhhH.jpeg',1),(3,'Classic Heather Gray Hoodie',69.00,'Stay cozy and stylish with our Classic Heather Gray Hoodie. Crafted from soft, durable fabric, it features a kangaroo pocket, adjustable drawstring hood, and ribbed cuffs. Perfect for a casual day out or a relaxing evening in, this hoodie is a versatile addition to any wardrobe.','https://i.imgur.com/cHddUCu.jpeg,https://i.imgur.com/CFOjAgK.jpeg,https://i.imgur.com/wbIMMme.jpeg',1),(4,'Classic Grey Hooded Sweatshirt',90.00,'Elevate your casual wear with our Classic Grey Hooded Sweatshirt. Made from a soft cotton blend, this hoodie features a front kangaroo pocket, an adjustable drawstring hood, and ribbed cuffs for a snug fit. Perfect for those chilly evenings or lazy weekends, it pairs effortlessly with your favorite jeans or joggers.','https://i.imgur.com/R2PN9Wq.jpeg,https://i.imgur.com/IvxMPFr.jpeg,https://i.imgur.com/7eW9nXP.jpeg',1),(5,'Classic Black Hooded Sweatshirt',79.00,'Elevate your casual wardrobe with our Classic Black Hooded Sweatshirt. Made from high-quality, soft fabric that ensures comfort and durability, this hoodie features a spacious kangaroo pocket and an adjustable drawstring hood. Its versatile design makes it perfect for a relaxed day at home or a casual outing.','https://i.imgur.com/cSytoSD.jpeg,https://i.imgur.com/WwKucXb.jpeg,https://i.imgur.com/cE2Dxh9.jpeg',1),(6,'Classic Comfort Fit Joggers',25.00,'Discover the perfect blend of style and comfort with our Classic Comfort Fit Joggers. These versatile black joggers feature a soft elastic waistband with an adjustable drawstring, two side pockets, and ribbed ankle cuffs for a secure fit. Made from a lightweight and durable fabric, they are ideal for both active days and relaxed lounging.','https://i.imgur.com/ZKGofuB.jpeg,https://i.imgur.com/GJi73H0.jpeg,https://i.imgur.com/633Fqrz.jpeg',1),(7,'Classic Comfort Drawstring Joggers',79.00,'Experience the perfect blend of comfort and style with our Classic Comfort Drawstring Joggers. Designed for a relaxed fit, these joggers feature a soft, stretchable fabric, convenient side pockets, and an adjustable drawstring waist with elegant gold-tipped detailing. Ideal for lounging or running errands, these pants will quickly become your go-to for effortless, casual wear.','https://i.imgur.com/mp3rUty.jpeg,https://i.imgur.com/JQRGIc2.jpeg',1),(8,'Classic Red Jogger Sweatpants',98.00,'Experience ultimate comfort with our red jogger sweatpants, perfect for both workout sessions and lounging around the house. Made with soft, durable fabric, these joggers feature a snug waistband, adjustable drawstring, and practical side pockets for functionality. Their tapered design and elastic cuffs offer a modern fit that keeps you looking stylish on the go.','https://i.imgur.com/9LFjwpI.jpeg,https://i.imgur.com/vzrTgUR.jpeg,https://i.imgur.com/p5NdI6n.jpeg',1),(9,'Classic Navy Blue Baseball Cap',61.00,'Step out in style with this sleek navy blue baseball cap. Crafted from durable material, it features a smooth, structured design and an adjustable strap for the perfect fit. Protect your eyes from the sun and complement your casual looks with this versatile and timeless accessory.','https://i.imgur.com/R3iobJA.jpeg,https://i.imgur.com/Wv2KTsf.jpeg,https://i.imgur.com/76HAxcA.jpeg',1),(10,'Classic Blue Baseball Cap',86.00,'Top off your casual look with our Classic Blue Baseball Cap, made from high-quality materials for lasting comfort. Featuring a timeless six-panel design with a pre-curved visor, this adjustable cap offers both style and practicality for everyday wear.','https://i.imgur.com/wXuQ7bm.jpeg,https://i.imgur.com/BZrIEmb.jpeg,https://i.imgur.com/KcT6BE0.jpeg',1),(11,'Classic Red Baseball Cap',35.00,'Elevate your casual wardrobe with this timeless red baseball cap. Crafted from durable fabric, it features a comfortable fit with an adjustable strap at the back, ensuring one size fits all. Perfect for sunny days or adding a sporty touch to your outfit.','https://i.imgur.com/cBuLvBi.jpeg,https://i.imgur.com/N1GkCIR.jpeg,https://i.imgur.com/kKc9A5p.jpeg',1),(12,'Classic Black Baseball Cap',58.00,'Elevate your casual wear with this timeless black baseball cap. Made with high-quality, breathable fabric, it features an adjustable strap for the perfect fit. Whether you’re out for a jog or just running errands, this cap adds a touch of style to any outfit.','https://i.imgur.com/KeqG6r4.jpeg,https://i.imgur.com/xGQOw3p.jpeg,https://i.imgur.com/oO5OUjb.jpeg',1),(13,'Classic Olive Chino Shorts',84.00,'Elevate your casual wardrobe with these classic olive chino shorts. Designed for comfort and versatility, they feature a smooth waistband, practical pockets, and a tailored fit that makes them perfect for both relaxed weekends and smart-casual occasions. The durable fabric ensures they hold up throughout your daily activities while maintaining a stylish look.','https://i.imgur.com/UsFIvYs.jpeg,https://i.imgur.com/YIq57b6.jpeg',1),(14,'Classic High-Waisted Athletic Shorts',43.00,'Stay comfortable and stylish with our Classic High-Waisted Athletic Shorts. Designed for optimal movement and versatility, these shorts are a must-have for your workout wardrobe. Featuring a figure-flattering high waist, breathable fabric, and a secure fit that ensures they stay in place during any activity, these shorts are perfect for the gym, running, or even just casual wear.','https://i.imgur.com/eGOUveI.jpeg,https://i.imgur.com/UcsGO7E.jpeg,https://i.imgur.com/NLn4e7S.jpeg',1),(15,'Classic White Crew Neck T-Shirt',39.00,'Elevate your basics with this versatile white crew neck tee. Made from a soft, breathable cotton blend, it offers both comfort and durability. Its sleek, timeless design ensures it pairs well with virtually any outfit. Ideal for layering or wearing on its own, this t-shirt is a must-have staple for every wardrobe.','https://i.imgur.com/axsyGpD.jpeg,https://i.imgur.com/T8oq9X2.jpeg,https://i.imgur.com/J6MinJn.jpeg',1),(16,'Classic White Tee - Timeless Style and Comfort',73.00,'Elevate your everyday wardrobe with our Classic White Tee. Crafted from premium soft cotton material, this versatile t-shirt combines comfort with durability, perfect for daily wear. Featuring a relaxed, unisex fit that flatters every body type, it\'s a staple piece for any casual ensemble. Easy to care for and machine washable, this white tee retains its shape and softness wash after wash. Pair it with your favorite jeans or layer it under a jacket for a smart look.','https://i.imgur.com/Y54Bt8J.jpeg,https://i.imgur.com/SZPDSgy.jpeg,https://i.imgur.com/sJv4Xx0.jpeg',1),(17,'Classic Black T-Shirt',35.00,'Elevate your everyday style with our Classic Black T-Shirt. This staple piece is crafted from soft, breathable cotton for all-day comfort. Its versatile design features a classic crew neck and short sleeves, making it perfect for layering or wearing on its own. Durable and easy to care for, it\'s sure to become a favorite in your wardrobe.','https://i.imgur.com/9DqEOV5.jpeg,https://i.imgur.com/ae0AEYn.jpeg,https://i.imgur.com/mZ4rUjj.jpeg',1),(18,'Sleek White & Orange Wireless Gaming Controller',69.00,'Elevate your gaming experience with this state-of-the-art wireless controller, featuring a crisp white base with vibrant orange accents. Designed for precision play, the ergonomic shape and responsive buttons provide maximum comfort and control for endless hours of gameplay. Compatible with multiple gaming platforms, this controller is a must-have for any serious gamer looking to enhance their setup.','https://i.imgur.com/ZANVnHE.jpeg,https://i.imgur.com/Ro5z6Tn.jpeg,https://i.imgur.com/woA93Li.jpeg',2),(19,'Sleek Wireless Headphone & Inked Earbud Set',44.00,'Experience the fusion of style and sound with this sophisticated audio set featuring a pair of sleek, white wireless headphones offering crystal-clear sound quality and over-ear comfort. The set also includes a set of durable earbuds, perfect for an on-the-go lifestyle. Elevate your music enjoyment with this versatile duo, designed to cater to all your listening needs.','https://i.imgur.com/yVeIeDa.jpeg,https://i.imgur.com/jByJ4ih.jpeg,https://i.imgur.com/KXj6Tpb.jpeg',2),(20,'Sleek Comfort-Fit Over-Ear Headphones',28.00,'Experience superior sound quality with our Sleek Comfort-Fit Over-Ear Headphones, designed for prolonged use with cushioned ear cups and an adjustable, padded headband. Ideal for immersive listening, whether you\'re at home, in the office, or on the move. Their durable construction and timeless design provide both aesthetically pleasing looks and long-lasting performance.','https://i.imgur.com/SolkFEB.jpeg,https://i.imgur.com/KIGW49u.jpeg,https://i.imgur.com/mWwek7p.jpeg',2),(21,'Efficient 2-Slice Toaster',48.00,'Enhance your morning routine with our sleek 2-slice toaster, featuring adjustable browning controls and a removable crumb tray for easy cleaning. This compact and stylish appliance is perfect for any kitchen, ensuring your toast is always golden brown and delicious.','https://i.imgur.com/keVCVIa.jpeg,https://i.imgur.com/afHY7v2.jpeg,https://i.imgur.com/yAOihUe.jpeg',2),(22,'Sleek Wireless Computer Mouse',10.00,'Experience smooth and precise navigation with this modern wireless mouse, featuring a glossy finish and a comfortable ergonomic design. Its responsive tracking and easy-to-use interface make it the perfect accessory for any desktop or laptop setup. The stylish blue hue adds a splash of color to your workspace, while its compact size ensures it fits neatly in your bag for on-the-go productivity.','https://i.imgur.com/w3Y8NwQ.jpeg,https://i.imgur.com/WJFOGIC.jpeg,https://i.imgur.com/dV4Nklf.jpeg',2),(23,'Sleek Modern Laptop with Ambient Lighting',43.00,'Experience next-level computing with our ultra-slim laptop, featuring a stunning display illuminated by ambient lighting. This high-performance machine is perfect for both work and play, delivering powerful processing in a sleek, portable design. The vibrant colors add a touch of personality to your tech collection, making it as stylish as it is functional.','https://i.imgur.com/OKn1KFI.jpeg,https://i.imgur.com/G4f21Ai.jpeg,https://i.imgur.com/Z9oKRVJ.jpeg',2),(24,'Sleek Modern Laptop for Professionals',97.00,'Experience cutting-edge technology and elegant design with our latest laptop model. Perfect for professionals on-the-go, this high-performance laptop boasts a powerful processor, ample storage, and a long-lasting battery life, all encased in a lightweight, slim frame for ultimate portability. Shop now to elevate your work and play.','https://i.imgur.com/ItHcq7o.jpeg,https://i.imgur.com/55GM3XZ.jpeg,https://i.imgur.com/tcNJxoW.jpeg',2),(25,'Stylish Red & Silver Over-Ear Headphones',39.00,'Immerse yourself in superior sound quality with these sleek red and silver over-ear headphones. Designed for comfort and style, the headphones feature cushioned ear cups, an adjustable padded headband, and a detachable red cable for easy storage and portability. Perfect for music lovers and audiophiles who value both appearance and audio fidelity.','https://i.imgur.com/YaSqa06.jpeg,https://i.imgur.com/isQAliJ.jpeg,https://i.imgur.com/5B8UQfh.jpeg',2),(26,'Sleek Mirror Finish Phone Case',27.00,'Enhance your smartphone\'s look with this ultra-sleek mirror finish phone case. Designed to offer style with protection, the case features a reflective surface that adds a touch of elegance while keeping your device safe from scratches and impacts. Perfect for those who love a minimalist and modern aesthetic.','https://i.imgur.com/yb9UQKL.jpeg,https://i.imgur.com/m2owtQG.jpeg,https://i.imgur.com/bNiORct.jpeg',2),(27,'Sleek Smartwatch with Vibrant Display',16.00,'Experience modern timekeeping with our high-tech smartwatch, featuring a vivid touch screen display, customizable watch faces, and a comfortable blue silicone strap. This smartwatch keeps you connected with notifications and fitness tracking while showcasing exceptional style and versatility.','https://i.imgur.com/LGk9Jn2.jpeg,https://i.imgur.com/1ttYWaI.jpeg,https://i.imgur.com/sPRWnJH.jpeg',2),(28,'Sleek Modern Leather Sofa',53.00,'Enhance the elegance of your living space with our Sleek Modern Leather Sofa. Designed with a minimalist aesthetic, it features clean lines and a luxurious leather finish. The robust metal legs provide stability and support, while the plush cushions ensure comfort. Perfect for contemporary homes or office waiting areas, this sofa is a statement piece that combines style with practicality.','https://i.imgur.com/Qphac99.jpeg,https://i.imgur.com/dJjpEgG.jpeg,https://i.imgur.com/MxJyADq.jpeg',3),(29,'Mid-Century Modern Wooden Dining Table',24.00,'Elevate your dining room with this sleek Mid-Century Modern dining table, featuring an elegant walnut finish and tapered legs for a timeless aesthetic. Its sturdy wood construction and minimalist design make it a versatile piece that fits with a variety of decor styles. Perfect for intimate dinners or as a stylish spot for your morning coffee.','https://i.imgur.com/DMQHGA0.jpeg,https://i.imgur.com/qrs9QBg.jpeg,https://i.imgur.com/XVp8T1I.jpeg',3),(31,'Modern Elegance Teal Armchair',25.00,'Elevate your living space with this beautifully crafted armchair, featuring a sleek wooden frame that complements its vibrant teal upholstery. Ideal for adding a pop of color and contemporary style to any room, this chair provides both superb comfort and sophisticated design. Perfect for reading, relaxing, or creating a cozy conversation nook.','https://i.imgur.com/6wkyyIN.jpeg,https://i.imgur.com/Ald3Rec.jpeg,https://i.imgur.com/dIqo03c.jpeg',3),(32,'Elegant Solid Wood Dining Table',67.00,'Enhance your dining space with this sleek, contemporary dining table, crafted from high-quality solid wood with a warm finish. Its sturdy construction and minimalist design make it a perfect addition for any home looking for a touch of elegance. Accommodates up to six guests comfortably and includes a striking fruit bowl centerpiece. The overhead lighting is not included.','https://i.imgur.com/4lTaHfF.jpeg,https://i.imgur.com/JktHE1C.jpeg,https://i.imgur.com/cQeXQMi.jpeg',3),(33,'Modern Minimalist Workstation Setup',49.00,'Elevate your home office with our Modern Minimalist Workstation Setup, featuring a sleek wooden desk topped with an elegant computer, stylish adjustable wooden desk lamp, and complimentary accessories for a clean, productive workspace. This setup is perfect for professionals seeking a contemporary look that combines functionality with design.','https://i.imgur.com/3oXNBst.jpeg,https://i.imgur.com/ErYYZnT.jpeg,https://i.imgur.com/boBPwYW.jpeg',3),(34,'Modern Ergonomic Office Chair',71.00,'Elevate your office space with this sleek and comfortable Modern Ergonomic Office Chair. Designed to provide optimal support throughout the workday, it features an adjustable height mechanism, smooth-rolling casters for easy mobility, and a cushioned seat for extended comfort. The clean lines and minimalist white design make it a versatile addition to any contemporary workspace.','https://i.imgur.com/3dU0m72.jpeg,https://i.imgur.com/zPU3EVa.jpeg',3),(35,'Futuristic Holographic Soccer Cleats',39.00,'Step onto the field and stand out from the crowd with these eye-catching holographic soccer cleats. Designed for the modern player, these cleats feature a sleek silhouette, lightweight construction for maximum agility, and durable studs for optimal traction. The shimmering holographic finish reflects a rainbow of colors as you move, ensuring that you\'ll be noticed for both your skills and style. Perfect for the fashion-forward athlete who wants to make a statement.','https://i.imgur.com/qNOjJje.jpeg,https://i.imgur.com/NjfCFnu.jpeg,https://i.imgur.com/eYtvXS1.jpeg',4),(36,'Rainbow Glitter High Heels',39.00,'Step into the spotlight with these eye-catching rainbow glitter high heels. Designed to dazzle, each shoe boasts a kaleidoscope of shimmering colors that catch and reflect light with every step. Perfect for special occasions or a night out, these stunners are sure to turn heads and elevate any ensemble.','https://i.imgur.com/62gGzeF.jpeg,https://i.imgur.com/5MoPuFM.jpeg,https://i.imgur.com/sUVj7pK.jpeg',4),(37,'Chic Summer Denim Espadrille Sandals',33.00,'Step into summer with style in our denim espadrille sandals. Featuring a braided jute sole for a classic touch and adjustable denim straps for a snug fit, these sandals offer both comfort and a fashionable edge. The easy slip-on design ensures convenience for beach days or casual outings.','https://i.imgur.com/9qrmE1b.jpeg,https://i.imgur.com/wqKxBVH.jpeg,https://i.imgur.com/sWSV6DK.jpeg',4),(38,'Vibrant Runners: Bold Orange & Blue Sneakers',27.00,'Step into style with these eye-catching sneakers featuring a striking combination of orange and blue hues. Designed for both comfort and fashion, these shoes come with flexible soles and cushioned insoles, perfect for active individuals who don\'t compromise on style. The reflective silver accents add a touch of modernity, making them a standout accessory for your workout or casual wear.','https://i.imgur.com/hKcMNJs.jpeg,https://i.imgur.com/NYToymX.jpeg,https://i.imgur.com/HiiapCt.jpeg',4),(39,'Vibrant Pink Classic Sneakers',84.00,'Step into style with our Vibrant Pink Classic Sneakers! These eye-catching shoes feature a bold pink hue with iconic white detailing, offering a sleek, timeless design. Constructed with durable materials and a comfortable fit, they are perfect for those seeking a pop of color in their everyday footwear. Grab a pair today and add some vibrancy to your step!','https://i.imgur.com/mcW42Gi.jpeg,https://i.imgur.com/mhn7qsF.jpeg,https://i.imgur.com/F8vhnFJ.jpeg',4),(40,'Futuristic Silver and Gold High-Top Sneaker',68.00,'Step into the future with this eye-catching high-top sneaker, designed for those who dare to stand out. The sneaker features a sleek silver body with striking gold accents, offering a modern twist on classic footwear. Its high-top design provides support and style, making it the perfect addition to any avant-garde fashion collection. Grab a pair today and elevate your shoe game!','https://i.imgur.com/npLfCGq.jpeg,https://i.imgur.com/vYim3gj.jpeg,https://i.imgur.com/HxuHwBO.jpeg',4),(41,'Futuristic Chic High-Heel Boots',36.00,'Elevate your style with our cutting-edge high-heel boots that blend bold design with avant-garde aesthetics. These boots feature a unique color-block heel, a sleek silhouette, and a versatile light grey finish that pairs easily with any cutting-edge outfit. Crafted for the fashion-forward individual, these boots are sure to make a statement.','https://i.imgur.com/HqYqLnW.jpeg,https://i.imgur.com/RlDGnZw.jpeg,https://i.imgur.com/qa0O6fg.jpeg',4),(42,'Elegant Patent Leather Peep-Toe Pumps with Gold-Tone Heel',53.00,'Step into sophistication with these chic peep-toe pumps, showcasing a lustrous patent leather finish and an eye-catching gold-tone block heel. The ornate buckle detail adds a touch of glamour, perfect for elevating your evening attire or complementing a polished daytime look.','https://i.imgur.com/AzAY4Ed.jpeg,https://i.imgur.com/umfnS9P.jpeg,https://i.imgur.com/uFyuvLg.jpeg',4),(43,'Elegant Purple Leather Loafers',17.00,'Step into sophistication with our Elegant Purple Leather Loafers, perfect for making a bold statement. Crafted from high-quality leather with a vibrant purple finish, these shoes feature a classic loafer silhouette that\'s been updated with a contemporary twist. The comfortable slip-on design and durable soles ensure both style and functionality for the modern man.','https://i.imgur.com/Au8J9sX.jpeg,https://i.imgur.com/gdr8BW2.jpeg,https://i.imgur.com/KDCZxnJ.jpeg',4),(44,'Classic Blue Suede Casual Shoes',39.00,'Step into comfort with our Classic Blue Suede Casual Shoes, perfect for everyday wear. These shoes feature a stylish blue suede upper, durable rubber soles for superior traction, and classic lace-up fronts for a snug fit. The sleek design pairs well with both jeans and chinos, making them a versatile addition to any wardrobe.','https://i.imgur.com/sC0ztOB.jpeg,https://i.imgur.com/Jf9DL9R.jpeg,https://i.imgur.com/R1IN95T.jpeg',4),(45,'Sleek Futuristic Electric Bicycle',22.00,'This modern electric bicycle combines style and efficiency with its unique design and top-notch performance features. Equipped with a durable frame, enhanced battery life, and integrated tech capabilities, it\'s perfect for the eco-conscious commuter looking to navigate the city with ease.','https://i.imgur.com/BG8J0Fj.jpg,https://i.imgur.com/ujHBpCX.jpg,https://i.imgur.com/WHeVL9H.jpg',5),(46,'Sleek All-Terrain Go-Kart',37.00,'Experience the thrill of outdoor adventures with our Sleek All-Terrain Go-Kart, featuring a durable frame, comfortable racing seat, and robust, large-tread tires perfect for handling a variety of terrains. Designed for fun-seekers of all ages, this go-kart is an ideal choice for backyard racing or exploring local trails.','https://i.imgur.com/Ex5x3IU.jpg,https://i.imgur.com/z7wAQwe.jpg,https://i.imgur.com/kc0Dj9S.jpg',5),(47,'Radiant Citrus Eau de Parfum',73.00,'Indulge in the essence of summer with this vibrant citrus-scented Eau de Parfum. Encased in a sleek glass bottle with a bold orange cap, this fragrance embodies freshness and elegance. Perfect for daily wear, it\'s an olfactory delight that leaves a lasting, zesty impression.','https://i.imgur.com/xPDwUb3.jpg,https://i.imgur.com/3rfp691.jpg,https://i.imgur.com/kG05a29.jpg',5),(48,'Sleek Olive Green Hardshell Carry-On Luggage',48.00,'Travel in style with our durable hardshell carry-on, perfect for weekend getaways and business trips. This sleek olive green suitcase features smooth gliding wheels for easy airport navigation, a sturdy telescopic handle, and a secure zippered compartment to keep your belongings safe. Its compact size meets most airline overhead bin requirements, ensuring a hassle-free flying experience.','https://i.imgur.com/jVfoZnP.jpg,https://i.imgur.com/Tnl15XK.jpg,https://i.imgur.com/7OqTPO6.jpg',5),(49,'Chic Transparent Fashion Handbag',61.00,'Elevate your style with our Chic Transparent Fashion Handbag, perfect for showcasing your essentials with a clear, modern edge. This trendy accessory features durable acrylic construction, luxe gold-tone hardware, and an elegant chain strap. Its compact size ensures you can carry your day-to-day items with ease and sophistication.','https://i.imgur.com/Lqaqz59.jpg,https://i.imgur.com/uSqWK0m.jpg,https://i.imgur.com/atWACf1.jpg',5),(50,'Trendy Pink-Tinted Sunglasses',38.00,'Step up your style game with these fashionable black-framed, pink-tinted sunglasses. Perfect for making a statement while protecting your eyes from the glare. Their bold color and contemporary design make these shades a must-have accessory for any trendsetter looking to add a pop of color to their ensemble.','https://i.imgur.com/0qQBkxX.jpg,https://i.imgur.com/I5g1DoE.jpg,https://i.imgur.com/myfFQBW.jpg',5),(51,'Elegant Glass Tumbler Set',50.00,'Enhance your drinkware collection with our sophisticated set of glass tumblers, perfect for serving your favorite beverages. This versatile set includes both clear and subtly tinted glasses, lending a modern touch to any table setting. Crafted with quality materials, these durable tumblers are designed to withstand daily use while maintaining their elegant appeal.','https://i.imgur.com/TF0pXdL.jpg,https://i.imgur.com/BLDByXP.jpg,https://i.imgur.com/b7trwCv.jpg',5),(52,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(54,'Jose ',23.00,'Producto sin descripción','[\"https://i.imgur.com/QkIa5tT.jpeg\"]',1),(55,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(56,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(57,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(58,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(59,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(60,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(61,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(62,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(63,'New Product',10.00,'2','[\"https://placeimg.com/640/480/any\"]',1),(64,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(65,'Iphone',300.00,'Best iphone till now','[\"https://images.app.goo.gl/veA8JgBNQqNL7LiD7\"]',1),(66,'iphone',400.00,'Best iphone','[\"https://images.app.goo.gl/veA8JgBNQqNL7LiD7\"]',1),(67,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(68,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(69,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(70,'iphone 13 pro',600.00,'Best Phone Overall','[\"https://images.app.goo.gl/veA8JgBNQqNL7LiD7\"]',1),(71,'iphone 13 pro',800.00,'best','[\"https://images.app.goo.gl/veA8JgBNQqNL7LiD7\"]',1),(72,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(73,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(74,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(75,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(76,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(77,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(78,'New Product 1',123.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(79,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(80,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(81,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(84,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(85,'Eniola',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(86,'iphone',500.00,'best','[\"https://frontend.blocksto.ai/_next/image?url=https%3A%2F%2Fminio.blocksto.ai%2Fblocksto-bucket%2Fimages%2F2024-11-11T14%3A07%3A31.787Z_599eacbfbff1e8bd4e685b569b45a41e-cc_ft_1536.webp&w=1080&q=75\"]',1),(87,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(88,'hgh',7777.00,'chjgx','[\"https://imgur.com/1twoaDy\"]',1),(89,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(90,'ghhdd',6666.00,'nhjhdj','[\"https://i.imgur.com/sC0ztOB.jpeg\"]',1),(91,'xfzgf',888.00,'ncjghcj','[\"https://i.imgur.com/sC0ztOB.jpeg\"]',1),(92,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(93,'dgdydy',333.00,'dfgdfhbh','[\"https://imgur.com/1twoaDy\"]',1),(94,'jyfuyf',33.00,'ewf','[\"https://imgur.com/1twoaDy\"]',1),(95,'hshts',454.00,'gxhg','[\"https://imgur.com/1twoaDy\"]',1),(96,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(97,'uyfhjv',6565.00,'khjghfgdf','[\"https://i.imgur.com/1twoaDy.jpeg\"]',1),(98,'jkhjgfxvc',5559.00,'hjgfdsdghfjkljkjg','[\"https://i.imgur.com/1twoaDy.jpeg\"]',1),(99,'hkjhdfhgj',65.00,'khjghgf','[\"https://i.imgur.com/1twoaDy.jpeg\"]',2),(100,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(103,'mnbvc',66.00,'khjghfgf','[\"https://i.imgur.com/1twoaDy.jpeg\"]',2),(104,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(105,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(106,'New Product',10.00,'A description','[\"https://frontend.blocksto.ai/_next/image?url=https%3A%2F%2Fminio.blocksto.ai%2Fblocksto-bucket%2Fimages%2F2024-11-11T14%3A07%3A31.787Z_599eacbfbff1e8bd4e685b569b45a41e-cc_ft_1536.webp&w=1080&q=75\"]',1),(107,'New Product',10.00,'A description','[\"https://frontend.blocksto.ai/_next/image?url=https%3A%2F%2Fminio.blocksto.ai%2Fblocksto-bucket%2Fimages%2F2024-11-11T14%3A07%3A31.787Z_599eacbfbff1e8bd4e685b569b45a41e-cc_ft_1536.webp&w=1080&q=75\"]',3),(108,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(109,'wee',5.00,'drff','[\"https://placeimg.com/640/480/any\"]',1),(110,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(111,'New Cotton Shirt',1100.00,'Very nice and attractive','[\"https://placeimg.com/640/480/cotton\"]',1),(112,'Mobile Phones',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(113,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(114,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(115,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(116,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(117,'fruity',10.00,'food','[\"https://placeimg.com/640/480/any\"]',1),(118,'fruity',10.00,'food','[\"https://placeimg.com/640/480/any\"]',1),(119,'fruity',10.00,'food','[\"https://placeimg.com/640/480/any\"]',1),(120,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(121,'fruity',10.00,'food','[\"https://placeimg.com/640/480/any\"]',1),(122,'fruity',10.00,'food','[\"https://placeimg.com/640/480/any\"]',1),(123,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(124,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(125,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\",\"https://i.imgur.com/LGk9Jn2.jpeg\"]',2),(126,'fruity',10.00,'food','[\"https://placeimg.com/640/480/any\"]',1),(127,'fruity',10.00,'food','[\"https://placeimg.com/640/480/any\"]',1),(128,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(129,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(130,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(131,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(132,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(133,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(134,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(135,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(136,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(137,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(138,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(139,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(140,'crude oil',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(142,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(143,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(144,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(145,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(146,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(147,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(148,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(149,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(150,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(151,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(152,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(153,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(156,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(157,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(158,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(159,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(160,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(161,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(162,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(163,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(164,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(165,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1),(166,'New Product',10.00,'A description','[\"https://placeimg.com/640/480/any\"]',1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `registered_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-29 13:12:47
