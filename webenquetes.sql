--
-- Create schema webenquetes
--
CREATE DATABASE IF NOT EXISTS webenquetes;
USE webenquetes;


SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `Alternatief`
-- ----------------------------
DROP TABLE IF EXISTS `Alternatief`;
CREATE TABLE `Alternatief` (
  `nr` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `vraag` int(10) unsigned NOT NULL,
  `volgnr` smallint(5) unsigned NOT NULL,
  `tekst` varchar(100) NOT NULL,
  PRIMARY KEY (`nr`),
  KEY `FK_alternatief_1` (`vraag`),
  KEY `UC_alternatief_1` (`vraag`,`volgnr`),
  CONSTRAINT `FK_alternatief_1` FOREIGN KEY (`vraag`) REFERENCES `vraag` (`nr`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of Alternatief
-- ----------------------------
INSERT INTO `Alternatief` VALUES ('1', '2', '1', 'nooit');
INSERT INTO `Alternatief` VALUES ('2', '2', '2', 'een enkele keer');
INSERT INTO `Alternatief` VALUES ('3', '2', '3', 'regelmatig');
INSERT INTO `Alternatief` VALUES ('4', '2', '4', 'altijd');
INSERT INTO `Alternatief` VALUES ('5', '3', '1', 'heel lekker');
INSERT INTO `Alternatief` VALUES ('6', '3', '2', 'lekker');
INSERT INTO `Alternatief` VALUES ('7', '3', '3', 'neutraal');
INSERT INTO `Alternatief` VALUES ('8', '3', '4', 'niet zo lekker');
INSERT INTO `Alternatief` VALUES ('9', '3', '5', 'vies');
INSERT INTO `Alternatief` VALUES ('10', '4', '1', 'heel lekker');
INSERT INTO `Alternatief` VALUES ('11', '4', '2', 'lekker');
INSERT INTO `Alternatief` VALUES ('12', '4', '3', 'neutraal');
INSERT INTO `Alternatief` VALUES ('13', '4', '4', 'niet zo lekker');
INSERT INTO `Alternatief` VALUES ('14', '4', '5', 'vies');
INSERT INTO `Alternatief` VALUES ('15', '5', '1', 'Ik vind Bonzo te duur');
INSERT INTO `Alternatief` VALUES ('16', '5', '2', 'Mijn hond vindt Bonzo niet lekker');
INSERT INTO `Alternatief` VALUES ('17', '5', '3', 'Mijn supermarkt verkoopt geen Bonzo');
INSERT INTO `Alternatief` VALUES ('18', '5', '4', 'Bonzo is vaak uitverkocht');
INSERT INTO `Alternatief` VALUES ('19', '5', '5', 'Ik koop een ander merk als dat in de aanbieding is');
INSERT INTO `Alternatief` VALUES ('20', '5', '6', 'Anders / Weet niet');
INSERT INTO `Alternatief` VALUES ('21', '7', '1', 'Ja');
INSERT INTO `Alternatief` VALUES ('22', '7', '2', 'Neutraal');
INSERT INTO `Alternatief` VALUES ('23', '7', '3', 'Nee');
INSERT INTO `Alternatief` VALUES ('24', '8', '1', 'Ja');
INSERT INTO `Alternatief` VALUES ('25', '8', '2', 'Neutraal');
INSERT INTO `Alternatief` VALUES ('26', '8', '3', 'Nee');
INSERT INTO `Alternatief` VALUES ('27', '9', '1', 'Minder dan een jaar');
INSERT INTO `Alternatief` VALUES ('28', '9', '2', '1-3 jaar');
INSERT INTO `Alternatief` VALUES ('29', '10', '1', '0 x');
INSERT INTO `Alternatief` VALUES ('30', '10', '2', '1-2 x');
INSERT INTO `Alternatief` VALUES ('31', '10', '3', '3-5 x');
INSERT INTO `Alternatief` VALUES ('32', '10', '4', '5-10 x');
INSERT INTO `Alternatief` VALUES ('33', '10', '5', 'vaker dan 10 x');
INSERT INTO `Alternatief` VALUES ('34', '11', '1', 'Heel tevreden');
INSERT INTO `Alternatief` VALUES ('35', '11', '2', 'Redelijk');
INSERT INTO `Alternatief` VALUES ('36', '11', '3', 'Neutraal');
INSERT INTO `Alternatief` VALUES ('37', '11', '4', 'Ontevreden');
INSERT INTO `Alternatief` VALUES ('38', '11', '5', 'Heel ontevreden');
INSERT INTO `Alternatief` VALUES ('39', '12', '1', 'Nieuwtjes');
INSERT INTO `Alternatief` VALUES ('40', '12', '2', 'Wanneer ik waar moet spelen');
INSERT INTO `Alternatief` VALUES ('41', '12', '3', 'Uitslagen');
INSERT INTO `Alternatief` VALUES ('42', '12', '4', 'Anders');
INSERT INTO `Alternatief` VALUES ('43', '9', '3', '3-5 jaar');
INSERT INTO `Alternatief` VALUES ('44', '9', '4', 'langer dan 5 jaar');

-- ----------------------------
-- Table structure for `Enquete`
-- ----------------------------
DROP TABLE IF EXISTS `Enquete`;
CREATE TABLE `Enquete` (
  `nr` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `titel` varchar(40) NOT NULL,
  `koptekst` varchar(250) NOT NULL,
  PRIMARY KEY (`nr`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of Enquete
-- ----------------------------
INSERT INTO `Enquete` VALUES ('2', 'Bonzo hondenbrokken', 'Wij vragen graag uw medewerking voor een onderzoek naar de waardering van u en uw hond voor onze brokken');
INSERT INTO `Enquete` VALUES ('3', 'Website DWS', 'We houden deze enquete om te kijken wat u vindt van de website van onze voetbalclub. Dank u voor uw medewerking. ');
INSERT INTO `Enquete` VALUES ('4', 'Bonzo hondenbrokken', 'Wij vragen graag uw medewerking voor een onderzoek naar de waardering van u en uw hond voor onze brokken');

-- ----------------------------
-- Table structure for `Vraag`
-- ----------------------------
DROP TABLE IF EXISTS `Vraag`;
CREATE TABLE `Vraag` (
  `nr` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `enquete` int(10) unsigned NOT NULL,
  `volgnr` int(10) unsigned NOT NULL,
  `vraagtype` int(5) unsigned NOT NULL,
  `tekst` varchar(100) NOT NULL,
  PRIMARY KEY (`nr`),
  UNIQUE KEY `UC_vraag_1` (`enquete`,`volgnr`),
  KEY `FK_vraag_1` (`enquete`),
  KEY `FK_type_1` (`vraagtype`),
  CONSTRAINT `FK_vraag_1` FOREIGN KEY (`enquete`) REFERENCES `enquete` (`nr`) ON DELETE CASCADE,
  CONSTRAINT `FK_type_1` FOREIGN KEY (`vraagtype`) REFERENCES `vraagtype` (`nr`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of Vraag
-- ----------------------------
INSERT INTO `Vraag` VALUES ('2', '2', '1', '1', 'Hoe vaak koopt u Bonzo hondenbrokken?');
INSERT INTO `Vraag` VALUES ('3', '2', '2', '1', 'Vindt uw hond de brokken lekker?');
INSERT INTO `Vraag` VALUES ('4', '2', '3', '1', 'Hoe vindt u de brokken ruiken?');
INSERT INTO `Vraag` VALUES ('5', '2', '4', '2', 'Als u Bonzo niet koopt, waarom is dat dan?');
INSERT INTO `Vraag` VALUES ('6', '2', '5', '0', 'Heeft u nog andere opmerkingen over Bonzo?');
INSERT INTO `Vraag` VALUES ('7', '3', '1', '1', 'Vindt u de website belangrijk voor DWS?');
INSERT INTO `Vraag` VALUES ('8', '3', '2', '1', 'Vindt u dat DWS zichtbaarder is geworden door de website?');
INSERT INTO `Vraag` VALUES ('9', '3', '3', '1', 'Hoe lang bent u al lid van DWS?');
INSERT INTO `Vraag` VALUES ('10', '3', '4', '1', 'Hoeveel keer heeft u de website van DWS (www.dws.nl) het afgelopen jaar bezocht?');
INSERT INTO `Vraag` VALUES ('11', '3', '5', '1', 'Hoe tevreden bent u over de inhoud van de website www.dws.nl?');
INSERT INTO `Vraag` VALUES ('12', '3', '6', '2', 'Welke informatie zoekt u vooral op de website www.dws.nl?');
INSERT INTO `Vraag` VALUES ('13', '3', '7', '0', 'Beschrijf hier uw suggesties of wensen mbt de website van DWS');

-- ----------------------------
-- Table structure for `Vraagtype`
-- ----------------------------
DROP TABLE IF EXISTS `Vraagtype`;
CREATE TABLE `Vraagtype` (
  `nr` int(10) unsigned NOT NULL,
  `soort` varchar(5) NOT NULL,
  PRIMARY KEY (`nr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Vraagtype
-- ----------------------------
INSERT INTO `Vraagtype` VALUES ('0', 'OPEN');
INSERT INTO `Vraagtype` VALUES ('1', 'RADIO');
INSERT INTO `Vraagtype` VALUES ('2', 'LIJST');
