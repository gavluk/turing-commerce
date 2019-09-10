DROP TABLE `shipping` IF EXISTS;
CREATE TABLE `shipping` (
  `shipping_id` int(11) NOT NULL AUTO_INCREMENT,
  `shipping_type` varchar(100) NOT NULL,
  `shipping_cost` decimal(10,2) NOT NULL,
  `shipping_region_id` int(11) NOT NULL,
  PRIMARY KEY (`shipping_id`)
);
