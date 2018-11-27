
 DROP TABLE IF EXISTS log;
CREATE TABLE log (
  id INT NOT NULL AUTO_INCREMENT,
  date TIMESTAMP,
  ip VARCHAR(100),
  request VARCHAR(300),
  status INT(10),
  userAgent VARCHAR(400),
  PRIMARY KEY (id));
  
  DROP TABLE IF EXISTS blockedips;
CREATE TABLE blockedips (
  id INT NOT NULL AUTO_INCREMENT,
  ip VARCHAR(100),
  count INT(9),
  reasonForBlocking VARCHAR(1000),
  PRIMARY KEY (id));
  
  