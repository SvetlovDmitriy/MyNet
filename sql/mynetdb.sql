-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mynetdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mynetdb`;
CREATE SCHEMA IF NOT EXISTS `mynetdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `mynetdb`;

DROP TABLE IF EXISTS `timeT`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `status`;
DROP TABLE IF EXISTS `service`;
DROP TRIGGER IF EXISTS `service_after_update` ;
DROP TRIGGER IF EXISTS `service_after_insert` ;
USE `mynetdb`;
-- -----------------------------------------------------
-- Table `mynetdb`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mynetdb`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(1024) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE `name_UNIQUE` (`name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mynetdb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mynetdb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL  DEFAULT 2,
  `cash` DOUBLE NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  CONSTRAINT `fk_account_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `mynetdb`.`role` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mynetdb`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mynetdb`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(1024) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mynetdb`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mynetdb`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DOUBLE UNSIGNED NOT NULL,
  `description` VARCHAR(1024) NULL DEFAULT "",
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  CONSTRAINT `fk_product_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `mynetdb`.`category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mynetdb`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mynetdb`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mynetdb`.`service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mynetdb`.`service` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  `status_id` INT NOT NULL,
  UNIQUE (`user_id`, `category_id`, `product_id`),
  PRIMARY KEY (`id`),
  UNIQUE user_id_category_id_UNIQUE (`user_id`, `category_id`),
  UNIQUE user_id_product_id_UNIQUE (`user_id`, `product_id`),
  CONSTRAINT `fk_service_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `mynetdb`.`status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_service_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `mynetdb`.`product` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_service_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `mynetdb`.`category` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_service_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mynetdb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mynetdb`.`timeT`
-- -----------------------------------------------------
CREATE TABLE `timeT` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`service_id` INT NOT NULL,
	`time` timestamp, 
    `total` INT DEFAULT 0,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_timeT_service_id` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Trigger `mynetdb`.`service_after_update_status`
-- -----------------------------------------------------
DELIMITER $$
CREATE
	TRIGGER `service_after_update_status` AFTER UPDATE 
	ON `service`
	FOR EACH ROW BEGIN
    
		SET @timeNew=CURRENT_TIMESTAMP;
	
       IF OLD.status_id != NEW.status_id THEN
		IF NEW.status_id=1 THEN
            UPDATE timeT SET timeT.`time` = @timeNew WHERE timeT.service_id=NEW.id;
		ELSE
			SET @totalOld=(SELECT total FROM timeT WHERE `timeT`.service_id=NEW.id);
			SET @totalDif=TIMESTAMPDIFF(SECOND, (SELECT `time` FROM timeT WHERE `timeT`.service_id LIKE NEW.id), CURRENT_TIMESTAMP);
			UPDATE timeT SET `time`= @timeNew, total=@totalOld+@totalDif WHERE timeT.service_id=NEW.id;
		END IF;
        END IF;
       
		
    END$$
    
-- -----------------------------------------------------
-- Trigger `service_after_insert`
-- -----------------------------------------------------
CREATE
	TRIGGER `service_after_insert` AFTER INSERT 
	ON `service` 
	FOR EACH ROW BEGIN
		INSERT INTO `timeT` (service_id, `time`) VALUES (NEW.id, CURRENT_TIMESTAMP);
		
    END$$
    
-- -----------------------------------------------------
-- Trigger `user_before_apdate_cash`
-- -----------------------------------------------------
CREATE
	TRIGGER `user_before_apdate_cash` BEFORE UPDATE
    ON `user` 
    FOR EACH ROW BEGIN
		IF NEW.cash <= 0 THEN
			UPDATE service SET service.status_id=2 WHERE service.user_id=NEW.id;
		END IF;
        
        IF NEW.cash > 0 AND OLD.cash <= 0 THEN
			UPDATE service SET service.status_id=1 WHERE service.user_id=NEW.id;
		END IF;
   
END$$    
DELIMITER ;

INSERT INTO role (id, name) VALUES(DEFAULT, "admin");
INSERT INTO role (id, name) VALUES(DEFAULT, "customer");

INSERT INTO status (id, name) VALUES(DEFAULT, "Run");
INSERT INTO status (id, name) VALUES(DEFAULT, "Stop");

SET @text = "admin";
INSERT INTO user (id, login, password, role_id) VALUES(DEFAULT, @text, @text, (SELECT id FROM role WHERE name = @text));
SET @text = "customer";
INSERT INTO user (id, login, password, role_id) VALUES(DEFAULT, "user1", "user1", (SELECT id FROM role WHERE name = @text));
INSERT INTO user (id, login, password, role_id) VALUES(DEFAULT, "user2", "user2", (SELECT id FROM role WHERE name = @text));
INSERT INTO user (id, login, password, role_id) VALUES(DEFAULT, "user3", "user3", (SELECT id FROM role WHERE name = @text));
INSERT INTO user (id, login, password, role_id) VALUES(DEFAULT, "user4", "user4", (SELECT id FROM role WHERE name = @text));

INSERT INTO category (id, name, description) VALUES (DEFAULT, "TV", DEFAULT);
INSERT INTO category (id, name, description) VALUES (DEFAULT, "Internet", DEFAULT);
INSERT INTO category (id, name, description) VALUES (DEFAULT, "IP-TV", DEFAULT);

SET @text = "TV";
INSERT INTO product (id, category_id, name, price, description) VALUES(DEFAULT, (SELECT id FROM category WHERE name = @text), "Base", 10.00, DEFAULT);
INSERT INTO product (id, category_id, name, price, description) VALUES(DEFAULT, (SELECT id FROM category WHERE name = @text), "Base+", 15.00, DEFAULT);
INSERT INTO product (id, category_id, name, price, description) VALUES(DEFAULT, (SELECT id FROM category WHERE name = @text), "SuperTV", 20.00, DEFAULT);

SET @text = "Internet";
INSERT INTO product (id, category_id, name, price, description) VALUES(DEFAULT, (SELECT id FROM category WHERE name = @text), "Inet 10 Mb", 20.00, DEFAULT);
INSERT INTO product (id, category_id, name, price, description) VALUES(DEFAULT, (SELECT id FROM category WHERE name = @text), "Inet 20 Mb", 30.00, DEFAULT);
INSERT INTO product (id, category_id, name, price, description) VALUES(DEFAULT, (SELECT id FROM category WHERE name = @text), "Inet 100 Mb", 40.00, DEFAULT);

SET @text = "IP-TV";
INSERT INTO product (id, category_id, name, price, description) VALUES(DEFAULT, (SELECT id FROM category WHERE name = @text), "TV Base", 15.00, DEFAULT);
INSERT INTO product (id, category_id, name, price, description) VALUES(DEFAULT, (SELECT id FROM category WHERE name = @text), "TV Base+", 20.00, DEFAULT);
INSERT INTO product (id, category_id, name, price, description) VALUES(DEFAULT, (SELECT id FROM category WHERE name = @text), "All World", 30.00, DEFAULT);

SET @s_id = 1;
SET @p_id = 2;
INSERT INTO service (user_id, product_id, category_id, status_id) VALUES(2, @p_id, (SELECT category_id FROM product WHERE @p_id = id), (SELECT id FROM status WHERE id = @s_id));
SET @p_id = 5;
INSERT INTO service (user_id, product_id, category_id, status_id) VALUES(2, @p_id, (SELECT category_id FROM product WHERE @p_id = id), (SELECT id FROM status WHERE id = @s_id));

SET @p_id = 1;
INSERT INTO service (user_id, product_id, category_id, status_id) VALUES(3, @p_id, (SELECT category_id FROM product WHERE @p_id = id), (SELECT id FROM status WHERE id = @s_id));
SET @p_id = 4;
INSERT INTO service (user_id, product_id, category_id, status_id) VALUES(3, @p_id, (SELECT category_id FROM product WHERE @p_id = id), (SELECT id FROM status WHERE id = @s_id));
SET @p_id = 8;
INSERT INTO service (user_id, product_id, category_id, status_id) VALUES(3, @p_id, (SELECT category_id FROM product WHERE @p_id = id), (SELECT id FROM status WHERE id = @s_id));

SET @p_id = 7;
INSERT INTO service (user_id, product_id, category_id, status_id) VALUES(4, @p_id, (SELECT category_id FROM product WHERE @p_id = id), (SELECT id FROM status WHERE id = @s_id));
SET @p_id = 1;
INSERT INTO service (user_id, product_id, category_id, status_id) VALUES(4, @p_id, (SELECT category_id FROM product WHERE @p_id = id), (SELECT id FROM status WHERE id = @s_id));

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

