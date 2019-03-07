/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.17-log : Database - communitysystem
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`communitysystem` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `communitysystem`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `content` text,
  `authorname` varchar(20) DEFAULT NULL,
  `publishtime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `authorname` (`authorname`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`authorname`) REFERENCES `usertable` (`username`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `article` */

insert  into `article`(`id`,`title`,`content`,`authorname`,`publishtime`) values 
(9,'随记','今天是我来到论坛的第一天，好开心呀！','xiaoxiao','2019-03-01'),
(16,'日记-2019.3.2','今天又下雨了，好烦！被子啥时候能干呀。','admin','2019-03-02'),
(17,'滕王阁序','滕王高阁临江渚，佩玉鸣鸾罢歌舞。\r\n\r\n画栋朝飞南浦云，珠帘暮卷西山雨。\r\n\r\n闲云潭影日悠悠，物换星移几度秋。\r\n\r\n阁中帝子今何在？槛外长江空自流。','tony','2019-03-04'),
(18,'日记-2019.3.4','今天天气正好，我要日不落的太阳……可以晒被子了^_^','admin','2019-03-04'),
(19,'大风歌','大风起兮云飞扬。\r\n威加海内兮归故乡。\r\n安得猛士兮守四方！','xtx','2019-03-07');

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `message` text,
  `mdate` date DEFAULT NULL,
  `messageperson` varchar(20) DEFAULT NULL,
  `articleid` int(255) DEFAULT NULL,
  `imgpath` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `articleid` (`articleid`),
  KEY `messageperson` (`messageperson`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`messageperson`) REFERENCES `usertable` (`username`) ON UPDATE CASCADE,
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`articleid`) REFERENCES `article` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `message` */

insert  into `message`(`id`,`message`,`mdate`,`messageperson`,`articleid`,`imgpath`) values 
(1,'啦啦啦','2019-03-06','admin',9,'/images/default.jpg'),
(2,'嘻嘻嘻','2019-03-06','xtx',9,'/images/xtx.jpg');

/*Table structure for table `usertable` */

DROP TABLE IF EXISTS `usertable`;

CREATE TABLE `usertable` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `imgpath` varchar(100) DEFAULT '/images/default.jpg',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `usertable` */

insert  into `usertable`(`id`,`username`,`password`,`imgpath`) values 
(1,'admin','1314','/images/default.jpg'),
(6,'tony','1','/images/ecut.jpg'),
(13,'xiaoxiao','1','/images/xiaoxiao.jpg'),
(14,'xtx','123','/images/xtx.jpg');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
