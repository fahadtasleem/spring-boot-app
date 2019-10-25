## Spring boot app

A multi tenancy based app using different databases as strategy. 

The databases should be of name

#### default_tenantid

eg. `default_t0` `default_t1`

A default database should be created with `default_t0` name.

*** 

#### DB schema

 ```SQL
 CREATE DATABASE default_t1;
 
 CREATE TABLE 'user' (
   'id' bigint(10) unsigned NOT NULL AUTO_INCREMENT,
   'name' varchar(255) NOT NULL,
   'age' int(11) NOT NULL,
   PRIMARY KEY ('id),
   UNIQUE KEY 'id_UNIQUE (id')
 ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
 