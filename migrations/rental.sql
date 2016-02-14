SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `car_rental` ;
CREATE SCHEMA IF NOT EXISTS `car_rental` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `car_rental` ;

-- -----------------------------------------------------
-- Table `car_rental`.`brand`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `car_rental`.`brand` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rental`.`model`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `car_rental`.`model` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `brand_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `brand_id_INDEX` (`brand_id` ASC),
  CONSTRAINT `brand_id_FOREIGN`
    FOREIGN KEY (`brand_id`)
    REFERENCES `car_rental`.`brand` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rental`.`car_description`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `car_rental`.`car_description` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `model_id` INT NOT NULL,
  `doors` INT NULL DEFAULT 2,
  `seats` INT NULL DEFAULT 2,
  `consumption` INT NOT NULL,
  `air_condition` TINYINT(1) NULL DEFAULT 0,
  `air_bags` TINYINT(1) NULL DEFAULT 0,
  `automatic` TINYINT(1) NULL DEFAULT 0,
  `description` TEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `model_id_INDEX` (`model_id` ASC),
  CONSTRAINT `model_id`
    FOREIGN KEY (`model_id`)
    REFERENCES `car_rental`.`model` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rental`.`car`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `car_rental`.`car` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `description_id` INT(11) NOT NULL,
  `is_available` TINYINT(1) NOT NULL,
  `description` TINYTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `description_id_INDEX` (`description_id` ASC),
  CONSTRAINT `description_id_FOREIGN`
    FOREIGN KEY (`description_id`)
    REFERENCES `car_rental`.`car_description` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rental`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `car_rental`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` INT NOT NULL,
  `is_admin` TINYINT(1) NOT NULL,
  `name` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NULL,
  `passport_number` VARCHAR(45) NULL,
  `address` VARCHAR(128) NULL,
  `phone` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `mail_UNIQUE` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rental`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `car_rental`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TINYTEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rental`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `car_rental`.`order` (
  `id` INT(11) NOT NULL,
  `car_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `status_id` INT NOT NULL,
  `from` DATE NOT NULL,
  `to` DATE NOT NULL,
  `total` INT NOT NULL,
  `info` TEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `car_id_INDEX` (`car_id` ASC),
  INDEX `user_id_INDEX` (`user_id` ASC),
  INDEX `status_id_INDEX` (`status_id` ASC),
  CONSTRAINT `car_id_FOREIGN`
    FOREIGN KEY (`car_id`)
    REFERENCES `car_rental`.`car` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_id_FOREIGN`
    FOREIGN KEY (`user_id`)
    REFERENCES `car_rental`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `status_id_FOREIGN`
    FOREIGN KEY (`status_id`)
    REFERENCES `car_rental`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `car_rental`.`order_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `car_rental`.`order_detail` (
  `id` INT(11) NOT NULL,
  `order_id` INT NULL,
  `price` INT NOT NULL DEFAULT 0,
  `description` TINYTEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `oredr_id_INDEX` (`order_id` ASC),
  CONSTRAINT `oredr_id_FOREIGN`
    FOREIGN KEY (`order_id`)
    REFERENCES `car_rental`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
