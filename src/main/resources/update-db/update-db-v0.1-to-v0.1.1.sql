CREATE TABLE IF NOT EXISTS `base_image_custom` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `imageName`  VARCHAR(255) NOT NULL DEFAULT '0',
  `imageTag` VARCHAR(255) DEFAULT NULL,
  `description` TEXT,
  `autoCustom` TINYINT(1) NOT NULL,
  `createTime` BIGINT(20) NOT NULL DEFAULT '0',
  `status` VARCHAR(255) DEFAULT NULL,
  `sourceImageJson` VARCHAR(255) DEFAULT NULL,
  `dockerfile` VARCHAR(255) DEFAULT NULL,
  `fileJson` TEXT,
  `secret` VARCHAR(255) DEFAULT NULL,
  `logMD5` VARCHAR(255) DEFAULT NULL,
  `finishTime` BIGINT(20) DEFAULT NULL,
  `imageSize` DOUBLE DEFAULT NULL,
  `publish` TINYINT(1) DEFAULT NULL,
  `username` VARCHAR (50) DEFAULT NULL,
  `message` TEXT,
  `isGC` int(11) DEFAULT '0'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `deploymentExtra` (
  `deployId` INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `content` TEXT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE Lunaros.env_conf ADD type VARCHAR(255) DEFAULT 'PROJECT' NOT NULL;

ALTER TABLE Lunaros.kube_build ADD jobType VARCHAR(255) DEFAULT 'PROJECT' NOT NULL;

CREATE TABLE IF NOT EXISTS `monitor_targets` (
  `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `target` VARCHAR(10240) NULL DEFAULT NULL,
  `createTime` DATETIME NULL DEFAULT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE Lunaros.deployment ADD networkMode VARCHAR(255) DEFAULT 'DEFAULT' NOT NULL;

CREATE TABLE IF NOT EXISTS `inner_service` (
  `id` INT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `deployId` INT(20) NOT NULL,
  `name` VARCHAR(20) NULL DEFAULT NULL,
  `protocol` VARCHAR(20) NULL DEFAULT NULL,
  `port` INT(11) NOT NULL,
  `targetPort` INT(11) NULL DEFAULT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE Lunaros.rsa_keypair ADD UNIQUE KEY(`projectId`);

ALTER TABLE Lunaros.kube_build DROP INDEX `buildId`, ADD UNIQUE INDEX (`buildId`, `jobType`);

UPDATE Lunaros.global SET `value`=`pub.Lunaros.org/Lunaros/build:0.1.2` WHERE `type`=`BUILD_IMAGE`;