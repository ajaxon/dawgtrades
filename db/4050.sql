
SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `attribute`;
SET foreign_key_checks = 1;


CREATE TABLE `attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(128) NOT NULL,
  `attribute_type_id` int(11) unsigned NOT NULL,
  `item_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `attribute_type_id_idx` (`attribute_type_id`),
  KEY `item_id_idx` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `attribute_type`;
SET foreign_key_checks = 1;

CREATE TABLE `attribute_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `category_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `category_id_idx` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `auction`;
SET foreign_key_checks = 1;

CREATE TABLE `auction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `minPrice` float NOT NULL,
  `expiration` date NOT NULL,
  `item_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `bid`;
SET foreign_key_checks = 1;

CREATE TABLE `bid` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `auction_id` int(11) unsigned NOT NULL,
  `date` date NOT NULL,
  `amount` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_id` (`user_id`),
  KEY `auction_id` (`auction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `category`;
SET foreign_key_checks = 1;

CREATE TABLE `category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `parent_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `parent_id_idx` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `experience_report`;
SET foreign_key_checks = 1;

CREATE TABLE `experience_report` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `rating` int(11) NOT NULL,
  `report` varchar(128) DEFAULT NULL,
  `reviewer_id` int(11) unsigned NOT NULL,
  `reviewed_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `reviewer_id_idx` (`reviewer_id`),
  KEY `reviewed_id_idx` (`reviewed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `item`;
SET foreign_key_checks = 1;

CREATE TABLE `item` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `identifier` varchar(45) DEFAULT NULL,
  `description` varchar(128) DEFAULT NULL,
  `owner_id` int(11) unsigned NOT NULL,
  `category_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `category_id_idx` (`category_id`),
  KEY `user_id_idx` (`owner_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `membership`;
SET foreign_key_checks = 1;

CREATE TABLE `membership` (
  `price` float DEFAULT NULL,
  `date` date DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `user`;
SET foreign_key_checks = 1;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `canText` tinyint(1) DEFAULT 0,
  `isAdmin` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE attribute
  ADD CONSTRAINT fk_attribute_type_id FOREIGN KEY (attribute_type_id) REFERENCES attribute_type(id) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES item(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE attribute_type
  ADD CONSTRAINT category_id FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE auction
  ADD CONSTRAINT item_id_FK FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE CASCADE ON UPDATE CASCADE;

 ALTER TABLE bid
  ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE  ON UPDATE  CASCADE,
  ADD CONSTRAINT auction_id FOREIGN KEY (auction_id) REFERENCES  auction (id) ON DELETE CASCADE  ON UPDATE  CASCADE;

ALTER TABLE category
    ADD CONSTRAINT parent_id FOREIGN KEY (parent_id) REFERENCES category (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE experience_report
    ADD   CONSTRAINT reviewed_id FOREIGN KEY (reviewed_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT reviewer_id FOREIGN KEY (reviewer_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE item
    ADD  CONSTRAINT owner_id FOREIGN KEY (owner_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD  CONSTRAINT category_id_FK_item FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE ON UPDATE CASCADE;
