DROP TABLE `tax` IF EXISTS;
CREATE TABLE `tax` (
  `tax_id` int(11) NOT NULL AUTO_INCREMENT,
  `tax_type` varchar(100) NOT NULL,
  `tax_percentage` decimal(10,2) NOT NULL,
  PRIMARY KEY (`tax_id`)
);
