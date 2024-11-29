-- MySQL dump 10.13  Distrib 9.0.1, for Win64 (x86_64)
--
-- Host: localhost    Database: online_store
-- ------------------------------------------------------
-- Server version	9.0.1
-- drop database Burton;

CREATE DATABASE burton;
USE burton;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
  `subtotal` decimal(10,2) NOT NULL,  -- Campo subtotal sin generación automática
  `product_name` varchar(255) DEFAULT NULL,  -- Nombre del producto
  `product_image` varchar(255) DEFAULT NULL, -- Imagen del producto
  PRIMARY KEY (`cart_item_id`),
  KEY `cart_id` (`cart_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`cart_id`)
 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;

/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `order_items` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,  -- Modificada para ser NOT NULL
  PRIMARY KEY (`order_item_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
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
  `order_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `status` enum('Completed','Cancelled') DEFAULT 'Completed',
  `payment_method` enum('Credit Card','PayPal','Cash') DEFAULT 'Credit Card',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
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
  `description` varchar(512),
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


-- procedimiento para el momento en que la persona compre se guarde completada la compra y asi la proxima lo reinicie

-- SELECT * FROM orders;
-- SELECT * FROM order_items;

DELIMITER //

CREATE PROCEDURE get_orders_by_user(IN userId INT)
BEGIN
    SELECT 
        order_id, 
        order_date, 
        total_amount 
    FROM orders
    WHERE user_id = userId;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE get_order_items_by_order_id(IN orderId INT)
BEGIN
    SELECT 
        order_items.order_item_id,
        order_items.order_id,
        order_items.product_id,
        order_items.quantity,
        order_items.subtotal,
        products.title,
        products.images
    FROM 
        order_items
	INNER JOIN products
    ON order_items.product_id = products.id
    WHERE 
        order_id = orderId;
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE complete_cart(IN cartId INT)
BEGIN
  UPDATE carts
  SET status = 'Completed'
  WHERE cart_id = cartId;
END //
DELIMITER ;

-- Call get_active_cart_by_user(1);
-- para que me devuelva el carrito de la persona 
DELIMITER //

CREATE PROCEDURE get_active_cart_by_user(IN p_user_id INT)
BEGIN
    SELECT cart_id
    FROM carts
    WHERE user_id = p_user_id AND status = 'Active'
    ORDER BY created_at DESC
    LIMIT 1;
END //

DELIMITER ;

-- esto es para mantener el carrito o crear uno nuevo en caso de no tener
 -- Error al ejecutar el procedimiento: Cannot add or update a child row: a foreign key constraint fails (`burton`.`carts`, CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`))
DELIMITER //
CREATE PROCEDURE get_or_create_active_cart(IN userId INT)
BEGIN
  DECLARE cartId INT;

  -- Buscar un carrito activo del usuario
  SELECT cart_id INTO cartId
  FROM carts
  WHERE user_id = userId AND status = 'Active'
  LIMIT 1;

  -- Si no hay un carrito activo, crear uno nuevo
  IF cartId IS NULL THEN
    INSERT INTO carts (user_id, status)
    VALUES (userId, 'Active');
    SET cartId = LAST_INSERT_ID();
  END IF;

  -- Devolver el ID del carrito activo o recién creado
  SELECT cartId AS cart_id;
END //
DELIMITER ;


DELIMITER //


-- drop procedure get_products_in_cart;
DELIMITER //

CREATE PROCEDURE get_products_in_cart(
    IN p_cart_id INT
)
BEGIN
    -- Obtener los productos del carrito activo
    SELECT DISTINCT 
           ci.product_id, 
           ci.quantity, 
           ci.unit_price, 
           ci.subtotal, 
           ci.product_name,  
           ci.product_image  
    FROM cart_items ci
    WHERE ci.cart_id = p_cart_id;
END //

DELIMITER ;
-- ejemplo aqui jalo las cosas del carrito guardado
-- call get_products_in_cart(3);


DELIMITER //

CREATE PROCEDURE add_product_to_cart(
    IN p_cart_id INT, 
    IN p_product_id INT, 
    IN p_quantity INT, 
    IN p_unit_price DECIMAL(10, 2), 
    IN p_total DECIMAL(10, 2),
    IN p_nameProduct VARCHAR(255),
    IN p_URLimage VARCHAR(255)
)
BEGIN
    DECLARE existing_item INT;

    -- Verificar si el producto ya existe en el carrito
    SELECT COUNT(*) INTO existing_item
    FROM cart_items 
    WHERE cart_id = p_cart_id 
    AND product_id = p_product_id;

    -- Si el producto existe, actualizamos la cantidad y el subtotal
    IF existing_item > 0 THEN
        UPDATE cart_items
        SET quantity = p_quantity, 
            subtotal = p_total
        WHERE cart_id = p_cart_id 
        AND product_id = p_product_id;
    ELSE
        -- Si no existe, insertamos un nuevo producto
        INSERT INTO cart_items (
            cart_id, 
            product_id, 
            quantity, 
            unit_price, 
            subtotal,
            product_name,
            product_image
        )
        VALUES (
            p_cart_id, 
            p_product_id, 
            p_quantity, 
            p_unit_price, 
            p_total,
            p_nameProduct,
            p_URLimage
        );
    END IF;

END //

DELIMITER ;


DELIMITER //


CREATE PROCEDURE get_orders_by_Admin()
BEGIN
    SELECT 
        order_id, 
        order_date, 
        total_amount 
    FROM orders;
END //

DELIMITER ;

select * from carts;
select * from cart_items;
SELECT * FROM products;
SELECT * FROM orders;
SELECT * FROM order_items;
SELECT * FROM categories;

SELECT productos.title AS Product, SUM(orders.quantity) AS Quantity FROM  order_items orders
INNER JOIN products productos 
ON orders.product_id = productos.id
GROUP BY productos.title;


SELECT cate.name AS nombre,productos.title AS Product, SUM(orders.quantity) AS Quantity FROM  order_items orders
INNER JOIN products productos 
ON orders.product_id = productos.id
GROUP BY productos.title;

SELECT 
    cate.id AS id, 
    productos.title AS Product, 
    SUM(orders.quantity) AS Quantity
FROM order_items orders
INNER JOIN products productos 
    ON orders.product_id = productos.id
INNER JOIN categories cate
    ON productos.category_id = cate.id
WHERE cate.id = 5 
  AND EXISTS (
      SELECT 1 
      FROM order_items oi
      INNER JOIN products p 
          ON oi.product_id = p.id
      WHERE p.category_id = cate.id
  )
GROUP BY productos.title, cate.id;

SELECT DISTINCT 
    c.id, 
    c.name 
FROM categories c
INNER JOIN products p 
    ON c.id = p.category_id
WHERE EXISTS (
    SELECT 1 
    FROM order_items oi
    WHERE oi.product_id = p.id
);


-- Dump completed on 2024-10-26 16:29:14
