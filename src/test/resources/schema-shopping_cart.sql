DROP TABLE `shopping_cart` IF EXISTS;
CREATE TABLE `shopping_cart` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `cart_id` char(36) NOT NULL,
  `product_id` int(11) NOT NULL,
  `attributes` varchar(1000) NOT NULL,
  `quantity` int(11) NOT NULL,
  `buy_now` tinyint(1) NOT NULL DEFAULT '1',
  `added_on` datetime NOT NULL,
  PRIMARY KEY (`item_id`)
);
