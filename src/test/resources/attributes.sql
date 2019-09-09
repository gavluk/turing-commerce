drop table `attribute_value` if exists;
CREATE TABLE `attribute_value` (
  `attribute_value_id` int(11) NOT NULL AUTO_INCREMENT,
  `attribute_id` int(11) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (`attribute_value_id`),
);


drop table `attribute` if exists;
CREATE TABLE `attribute` (
  `attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`attribute_id`)
);

drop table `product_attribute` if exists;
CREATE TABLE `product_attribute` (
  `product_id` int(11) NOT NULL,
  `attribute_value_id` int(11) NOT NULL,
  PRIMARY KEY (`product_id`,`attribute_value_id`)
);


INSERT INTO `attribute`
(attribute_id, name)
VALUES(1, 'Size');
INSERT INTO `attribute`
(attribute_id, name)
VALUES(2, 'Color');

INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(1, 1, 'S');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(2, 1, 'M');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(3, 1, 'L');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(4, 1, 'XL');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(5, 1, 'XXL');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(6, 2, 'White');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(7, 2, 'Black');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(8, 2, 'Red');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(9, 2, 'Orange');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(10, 2, 'Yellow');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(11, 2, 'Green');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(12, 2, 'Blue');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(13, 2, 'Indigo');
INSERT INTO attribute_value
(attribute_value_id, attribute_id, value)
VALUES(14, 2, 'Purple');

INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 1);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 2);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 3);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 4);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 5);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 6);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 7);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 8);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 9);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 10);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 11);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 12);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 13);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(1, 14);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 1);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 2);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 3);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 4);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 5);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 6);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 7);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 8);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 9);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 10);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 11);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 12);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 13);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(2, 14);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 1);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 2);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 3);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 4);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 5);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 6);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 7);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 8);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 9);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 10);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 11);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 12);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 13);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(3, 14);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 1);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 2);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 3);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 4);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 5);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 6);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 7);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 8);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 9);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 10);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 11);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 12);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 13);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(4, 14);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 1);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 2);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 3);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 4);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 5);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 6);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 7);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 8);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 9);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 10);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 11);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 12);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 13);
INSERT INTO product_attribute
(product_id, attribute_value_id)
VALUES(5, 14);
