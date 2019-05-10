-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bookstore
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bookstore
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bookstore` DEFAULT CHARACTER SET utf8 ;
USE `bookstore` ;

-- -----------------------------------------------------
-- Table `bookstore`.`authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`authors` (
  `authorId` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`authorId`))
ENGINE = InnoDB
AUTO_INCREMENT = 601
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`category` (
  `categoryID` INT(11) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`categoryID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore`.`publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`publisher` (
  `PublisherID` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PublisherID`))
ENGINE = InnoDB
AUTO_INCREMENT = 601
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`book` (
  `ISBN` INT(11) NOT NULL,
  `Title` VARCHAR(200) NOT NULL,
  `PubID` INT(11) NOT NULL,
  `PublishYear` INT(11) NOT NULL,
  `price` INT(11) NULL DEFAULT '0',
  `categoryID` INT(11) NOT NULL,
  `threshold` INT(11) NOT NULL,
  `quantity` INT(11) NULL DEFAULT '0',
  PRIMARY KEY (`ISBN`),
  INDEX `categoryId_idx` (`categoryID` ASC),
  INDEX `publisherfk2_idx` (`PubID` ASC),
  CONSTRAINT `categoryId`
    FOREIGN KEY (`categoryID`)
    REFERENCES `bookstore`.`category` (`categoryID`),
  CONSTRAINT `publisherfk2`
    FOREIGN KEY (`PubID`)
    REFERENCES `bookstore`.`publisher` (`PublisherID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore`.`book_authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`book_authors` (
  `ISBN` INT(11) NOT NULL,
  `AuthorID` INT(11) NOT NULL,
  PRIMARY KEY (`ISBN`, `AuthorID`),
  INDEX `authorName_idx` (`AuthorID` ASC),
  CONSTRAINT `BookToAuthor`
    FOREIGN KEY (`ISBN`)
    REFERENCES `bookstore`.`book` (`ISBN`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore`.`ordertable`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`ordertable` (
  `OrderID` INT(11) NOT NULL AUTO_INCREMENT,
  `PublisherId` INT(11) NOT NULL,
  `ISBN` INT(11) NOT NULL,
  `QUANTITY` INT(11) NULL DEFAULT NULL,
  `confirmed` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`OrderID`, `PublisherId`, `ISBN`),
  INDEX `ISBNinOrders` (`ISBN` ASC),
  INDEX `PublisherId_idx` (`PublisherId` ASC),
  CONSTRAINT `ISBNinOrders`
    FOREIGN KEY (`ISBN`)
    REFERENCES `bookstore`.`book` (`ISBN`),
  CONSTRAINT `PublisherIdfk`
    FOREIGN KEY (`PublisherId`)
    REFERENCES `bookstore`.`publisher` (`PublisherID`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore`.`publisheraddresses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`publisheraddresses` (
  `pubId` INT(11) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`pubId`, `address`),
  CONSTRAINT `fk12`
    FOREIGN KEY (`pubId`)
    REFERENCES `bookstore`.`publisher` (`PublisherID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore`.`publishernumbers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`publishernumbers` (
  `PubID` INT(11) NOT NULL,
  `number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PubID`, `number`),
  CONSTRAINT `fk13`
    FOREIGN KEY (`PubID`)
    REFERENCES `bookstore`.`publisher` (`PublisherID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`users` (
  `userID` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `pasword` VARCHAR(45) NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phonenumber` VARCHAR(45) NOT NULL,
  `shippingAddress` VARCHAR(100) NOT NULL,
  `user_type` TINYINT(4) NOT NULL,
  PRIMARY KEY (`userID`, `email`, `username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1002
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore`.`topsales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore`.`topsales` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `ISBN` INT(11) NULL DEFAULT NULL,
  `quantity` INT(11) NULL DEFAULT NULL,
  `userID` INT(11) NULL DEFAULT NULL,
  `shippingMonth` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `BOOK_idx` (`ISBN` ASC),
  INDEX `USER_idx` (`userID` ASC),
  CONSTRAINT `BOOK`
    FOREIGN KEY (`ISBN`)
    REFERENCES `bookstore`.`book` (`ISBN`),
  CONSTRAINT `USER`
    FOREIGN KEY (`userID`)
    REFERENCES `bookstore`.`users` (`userID`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

USE `bookstore` ;

-- -----------------------------------------------------
-- procedure Add_book_authors
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Add_book_authors`(In authorName varchar(45), in ISBN int)
BEGIN
set @authorid = (select authorId from authors where authors.Name = authorName);
insert into book_authors values (ISBN,authorsID);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure Add_new_authors
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Add_new_authors`(in authorname varchar(45))
BEGIN
insert into authors (Name) values (authorname);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure Add_new_book
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Add_new_book`(in ISBN int,in title varchar(45) ,in publishername varchar(45),in pubYear int ,in price int,in threshold int,in quantity int,in bookCategory varchar(45))
BEGIN
select category.categoryID into @ctid from category where category.Name = bookCategory;
select PublisherID into @pubid from publisher where publisher.Name = publishername;
insert into book values ( ISBN,title,@pubid,pubYear,price,@ctid,threshold,quantity);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure Add_new_publishers
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Add_new_publishers`(in pubName varchar(45))
BEGIN
insert into publisher (Name) values (pubName);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure Retreive_user_info
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Retreive_user_info`(in usrName varchar(45))
BEGIN
select * from users  where users.username = usrName;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure Search_for_book
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Search_for_book`( IN booktitle varchar(45), IN authorname varchar(45), IN publishername varchar(45), IN categoryy varchar(45), In pubYear int , In priceMax int , In priceMin int)
BEGIN

 SELECT * FROM book
  WHERE 
  (booktitle is null or book.Title like CONCAT('%',booktitle,'%'))
  and(publishername is null or book.PubID in (
  SELECT publisher.PublisherID from publisher where (
  publishername is null or publisher.Name like CONCAT('%',publishername,'%')
  )
  ) 
  )
  and (
  categoryy is null or book.categoryID in (
  SELECT category.categoryID from category where (
  category.Name like CONCAT('%',categoryy,'%')
  )))
  and (
  authorname is null or book.ISBN in (
  select book_authors.ISBN from book_authors where book_authors.AuthorID in (
  select authors.authorId from authors where (
  authors.Name like CONCAT('%',authorname,'%')))))
  and (
  pubYear is null or book.PublishYear between (pubYear-1) and (pubYear+1))
  and (priceMax is null or priceMin is null or book.price between (priceMin) and (priceMax)) ;
  /*select if(booktitle is null,'trueeee','faaaaaaaalse') union all
   select if(publishername is null,'trueeee','faaaaaaaalse') union all
  select if(categoryy is null,'trueeee','faaaaaaaalse') union all
  select if(authorname is null,'trueeee','faaaaaaaalse') union all
  select if(pubYear is null,'trueeee','faaaaaaaalse') union all
  select if(priceMax is null,'trueeee','faaaaaaaalse') ;*/

 -- select * from book where (booktitle is null or book.Title like CONCAT('%',booktitle,'%')) ;
 -- select * from book where (publishername is null or book.PubID in (SELECT publisher.PublisherID from publisher where (publishername is null or publisher.Name like CONCAT('%',publishername,'%')))) ;

-- select * from book where (categoryy is null or book.categoryID in (SELECT category.categoryID from category where ( category.Name like CONCAT('%',categoryy,'%'))));
--  select * from book where (authorname is null or book.ISBN in (select book_authors.ISBN from book_authors where book_authors.AuthorID in (select authors.authorId from authors where (authors.Name like CONCAT('%',authorname,'%')))  )  );

 -- select * from book where (pubYear is null or book.PublishYear between (pubYear-1) and (pubYear+1));
  -- select * from book where (priceMax is null or priceMin is null or book.price between (priceMin) and (priceMax)) ;
  /*signal sqlstate '45'faaaaaaaalse''faaaaaaaalse'5' set message_text = @idk; 
*/

end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure Signup
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Signup`(in usrName varchar(45),
in usrpass varchar(45),in usrFirstName varchar(45),in usrLastName varchar(45),
in usrEmail varchar(45),in usrAddress varchar(100),in usrPhone varchar(45))
BEGIN
insert into users (username,pasword,firstName,lastName,email,phonenumber,user_type,users.shippingAddress) values 
(usrName,usrpass,usrFirstName,usrLastName,usrEmail,usrPhone,false,usrAddress);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure add_publisher_address
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`ai`@`%` PROCEDURE `add_publisher_address`(in pubName varchar(45),in pubAddress varchar(100))
BEGIN
 set @pubID = (select PublisherID from publisher where publisher.Name =pubName);
insert into publisheraddresses values (@pubID,pubAddress);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure add_publisher_phone
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`ai`@`%` PROCEDURE `add_publisher_phone`(in pubName varchar(45),in pubPhone varchar(45)
)
BEGIN
 set @pubID = (select PublisherID from publisher where publisher.Name =pubName);

insert into publishernumbers values (@pubID,pubPhone);


END$$

DELIMITER ;

-- -----------------------------------------------------
-- function check_login
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `check_login`(userName varchar(45),pass varchar(45)) RETURNS int(11)
    DETERMINISTIC
BEGIN
DECLARE login_status int DEFAULT 0;

 select users.userID,users.pasword,users.user_type into @userid,@userpass,@typeu from users where users.username = userName;
   if( @userid =null)then
   return 0;/*no user*/
   elseif (pass=@userpass and @typeu=1) then  
   return 2;/*crct manager*/
   elseif(pass=@userpass and @typeu =0 ) then
   return 3;/*crct user*/
   else
   return 1;/*wrng pass*/
   end if;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure confirm_order
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `confirm_order`(in pubName varchar(45), in bookID int)
BEGIN
select PublisherID into @pubid from publisher where publisher.Name = pubName;
update ordertable set confirmed=true where (ordertable.PublisherId=@pubid) and (ordertable.ISBN=bookID) and ordertable.confirmed=false
ORDER BY OrderID asc
LIMIT 1;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_order_history
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_order_history`(in bookID int , in q int , in userName varchar(45))
BEGIN
set @userid =(select userID from users where users.username=userName);
insert into topsales (ISBN,quantity,userID,shippingMonth) values (bookID,q,@userid,current_date());
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure modify_book_quantity
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `modify_book_quantity`(in id int , in q int)
BEGIN
update book set book.quantity=q where book.ISBN=id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure place_book_order
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `place_book_order`(in pubName varchar(45),in bookId int , in q int)
BEGIN
select PublisherID into @pubid from publisher where publisher.Name = pubName;
insert into ordertable (PublisherId,ISBN,QUANTITY,confirmed) values (@pubid,bookId,q,false);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure promote_user
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `promote_user`(in usrName varchar(45))
BEGIN
if exists (select users.username from users where users.username=usrName) then
update users set users.user_type = true where users.username=usrName;
else
signal sqlstate '45010' set message_text = 'username is not found';
end if;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retreive_top_customers
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retreive_top_customers`()
BEGIN
select username, sum(quantity) from ((select userID, sum(quantity) from topsales where month(topsales.shippingMonth)!=MONTH(CURRENT_DATE)group by userID)as tab1  natural join users) order by sum (quantity) limit 5;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retreive_top_sales
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retreive_top_sales`()
BEGIN
select Title, sum(quantity) from  ((select ISBN, sum(quantity) from topsales where month(topsales.shippingMonth)!=MONTH(CURRENT_DATE) group by ISBN
)as tab1  natural join book) order by sum (quantity) limit 10;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retreive_total_sales
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retreive_total_sales`()
BEGIN
select * from topsales where month(topsales.shippingMonth)=MONTH(CURRENT_DATE - INTERVAL 1 MONTH);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure update_user_info
-- -----------------------------------------------------

DELIMITER $$
USE `bookstore`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_user_info`(
in usrName varchar(45),in newusrName varchar(45),in oldpass varchar(45),in newpass varchar(45),
in fn varchar(45),
in ln varchar(45),
in em varchar(45),
in address varchar(100),in phone varchar(45))
BEGIN
if (oldpass is not null) then
(select pasword into @pass from users where users.username  = usrName);
(select userID into @id from users where users.username  = newusrName);
(select userID into @mail from users where users.email  = em);


if (@pass != oldpass ) then 
signal sqlstate '45009' set message_text = 'old password is incorrect';
elseif (@id != null) then
signal sqlstate '45010' set message_text = 'new user name is repeated';
elseif (@mail != null) then
signal sqlstate '45011' set message_text = 'new mail is repeated';
else 
update users set users.pasword = newpass,
users.username = newusrName,
users.firstName = fn,
users.lastName =ln,
users.email=em,
 users.shippingAddress = address, users.phonenumber = phone where users.username = usrName;
end if;
end if;
END$$

DELIMITER ;
USE `bookstore`;

DELIMITER $$
USE `bookstore`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `bookstore`.`book_BEFORE_UPDATE`
BEFORE UPDATE ON `bookstore`.`book`
FOR EACH ROW
BEGIN
IF new.quantity<0 then
	signal sqlstate '45000' set message_text ='QUANTITY OF XCECUTING THAT QUERY';
 END if;
END$$

USE `bookstore`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `bookstore`.`ordertable_BEFORE_DELETE`
BEFORE DELETE ON `bookstore`.`ordertable`
FOR EACH ROW
BEGIN
	select threshold,quantity into @threshhold1,@qaun from book where book.ISBN=old.ISBN;
if !old.confirmed and@threshold1<=@quan then
         update book set quantity=quantity+@quantity where book.ISBN=old.ISBN;
elseif !old.confirmed then 
  signal sqlstate '45004' set message_text= "sorry";
  end if;
END$$

USE `bookstore`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `bookstore`.`ordertable_BEFORE_INSERT`
BEFORE INSERT ON `bookstore`.`ordertable`
FOR EACH ROW
BEGIN
	select threshold,quantity into @threshold1,@qaun from book where book.ISBN=new.ISBN;
    
    IF @qaun >=@threshold1 then
    signal sqlstate '45000' set message_text='ERROR QUANTITY BIGGER THAN THE THRESHOLD';
    END IF;
    	select threshold,quantity into @threshhold1,@qaun from book where book.ISBN=new.ISBN;
    IF NEW.confirmed then
    update book set quantity=new.QUANTITY+@qaun where book.ISBN=NEW.ISBN;
end if;
END$$

USE `bookstore`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `bookstore`.`ordertable_BEFORE_UPDATE`
BEFORE UPDATE ON `bookstore`.`ordertable`
FOR EACH ROW
BEGIN
	select threshold,quantity into @threshhold1,@qaun from book where book.ISBN=new.ISBN;
 if old.confirmed then 
 signal sqlstate '45001' set message_text= "can upadte a confirmed order";
 elseif old.quantity != new.quantity or @threshhold1<=@qaun then
  signal sqlstate '45003' set message_text= "can upadte the order old.quantity != new.quantity or threshhold<=quantity";
  elseif !new.confirmed then
    signal sqlstate '45002' set message_text= "can upadte the order old.quantity != new.quantity or threshhold<=quantity";
else
	update book set quantity=old.QUANTITY+@qaun where book.ISBN=NEW.ISBN;
end if;
END$$

USE `bookstore`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `bookstore`.`users_BEFORE_INSERT`
BEFORE INSERT ON `bookstore`.`users`
FOR EACH ROW
BEGIN
if exists (select userID from users where users.username = new.username) then
signal sqlstate '45008' set message_text = 'username already exists';
end if;
END$$

USE `bookstore`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `bookstore`.`topsales_AFTER_INSERT_DEACREASE_QUANTITY`
AFTER INSERT ON `bookstore`.`topsales`
FOR EACH ROW
BEGIN
update book set quantity=quantity-new.quantity where book.ISBN=new.ISBN;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
