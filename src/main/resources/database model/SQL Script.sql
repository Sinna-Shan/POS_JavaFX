create database pos;
use pos;

CREATE TABLE IF NOT EXISTS `pos`.`user` (
  `user_id` VARCHAR(10) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `type` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `password_UNIQUE` (`password` ASC) VISIBLE,
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE);

CREATE TABLE IF NOT EXISTS `pos`.`customer` (
  `contact` VARCHAR(10) NOT NULL,
  `email` VARCHAR(100) NULL,
  `name` VARCHAR(100) NULL,
  PRIMARY KEY (`contact`));

CREATE TABLE IF NOT EXISTS `pos`.`item` (
  `item_code` VARCHAR(50) NOT NULL,
  `sup_id` VARCHAR(50) NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `qty` INT NOT NULL,
  `buyPrice` DECIMAL(10,2) NOT NULL,
  `sellPrice` DECIMAL(10,2) NOT NULL,
  `type` VARCHAR(10) NOT NULL,
  `size` CHAR(5) NOT NULL,
  `profit` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`item_code`),
  INDEX `id_idx` (`sup_id` ASC) VISIBLE,
  CONSTRAINT `sup_id`
    FOREIGN KEY (`sup_id`)
    REFERENCES `pos`.`supplier` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

  
CREATE TABLE IF NOT EXISTS `pos`.`supplier` (
  `id` VARCHAR(50) NOT NULL,
  `title` VARCHAR(5) NULL DEFAULT NULL,
  `name` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(10) NOT NULL,
  `company` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`));

    
CREATE TABLE IF NOT EXISTS `pos`.`orderDetails` (
  `item_code` VARCHAR(25) NOT NULL,
  `order_id` VARCHAR(50) NOT NULL,
  `qty` INT NOT NULL,
  `unit_price` DECIMAL(10,2) NOT NULL,
  `discount` DECIMAL(10,2) NULL,
  `total` DECIMAL(10,2) NOT NULL,
  INDEX `order_id_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `pos`.`order` (`order_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `pos`.`orders` (
  `order_id` VARCHAR(50) NOT NULL,
  `date` VARCHAR(25) NOT NULL,
  `user_id` VARCHAR(45) NOT NULL,
  `cust_contact` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `pos`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `cust_contact`
    FOREIGN KEY (`cust_contact`)
    REFERENCES `pos`.`customer` (`contact`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);



INSERT INTO order VALUES ("0763236693","asdf@gmail.com","kumara");
INSERT INTO customer VALUES ("0763236693","asdf@gmail.com","kumara");
INSERT INTO supplier VALUES ("sup_0001","Mr.","Gunadasa","0763236693","Kuwait");

INSERT INTO item VALUES ("itm_0001","sup_0001","denim",10,1000,1500,"gents","M",500);
INSERT INTO item VALUES ('itm_0002','sup_0001','Denim',5,500.0,1000.0,'Ladies','M',500.0);

INSERT INTO user VALUES("emp_0001","Noah","Luth595@gmail.com","Noah","Noah@123","admin");
INSERT INTO user VALUES("emp_0002","Janani","lakshana.al@gmail.com","Janani","Janani@123","user");

drop database pos;


