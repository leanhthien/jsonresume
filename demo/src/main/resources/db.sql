
--CREATE DATABASE IF NOT EXISTS `myDb`;
--USE `myDb`;
--
--CREATE TABLE IF NOT EXISTS `user` (
--  `id` int(11) NOT NULL AUTO_INCREMENT,
--  `username` varchar(255) DEFAULT NULL,
--  `email` varchar(5000) DEFAULT NULL,
--  `password` varchar(255) DEFAULT NULL,
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Create table
CREATE TABLE IF NOT EXISTS APP_USER
(
  USER_ID           BIGINT not null,
  USER_NAME         VARCHAR(36) not null,
  ENCRYPTED_PASSWORD VARCHAR(128) not null,
  ENABLED           BIT not null
) ;
--

ALTER TABLE APP_USER MODIFY USER_ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY;

alter table APP_USER
  add constraint APP_USER_UK unique (USER_NAME);


-- Create table
CREATE TABLE IF NOT EXISTS APP_ROLE
(
  ROLE_ID   BIGINT not null,
  ROLE_NAME VARCHAR(30) not null
) ;
--
alter table APP_ROLE
  add constraint APP_ROLE_PK primary key (ROLE_ID);

alter table APP_ROLE
  add constraint APP_ROLE_UK unique (ROLE_NAME);


-- Create table
CREATE TABLE IF NOT EXISTS USER_ROLE
(
  ID      BIGINT not null,
  USER_ID BIGINT not null,
  USER_NAME VARCHAR(36) not null,
  ROLE_ID BIGINT not null
);
--

ALTER TABLE USER_ROLE MODIFY ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY;

alter table USER_ROLE
  add constraint USER_ROLE_UK unique (USER_NAME, ROLE_ID);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (USER_ID)
  references APP_USER (USER_ID);

alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (ROLE_ID)
  references APP_ROLE (ROLE_ID);


-- Used by Spring Remember Me API.
CREATE TABLE IF NOT EXISTS Persistent_Logins
(
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
);


-- Create table
CREATE TABLE IF NOT EXISTS Product
(
    product_id BIGINT not null AUTO_INCREMENT,
    name varchar(64) not null,
    job_title varchar(64) not null,
    address varchar(64) not null,
    telephone varchar(64) not null,
    email varchar(64) not null,
    website varchar(64) not null,
    language varchar(64) not null,
    about varchar(64) not null,
    USER_ID BIGINT not null,
    PRIMARY KEY (product_id)
);

--------------------------------------

insert into App_User (USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
values ('dbuser1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into App_User (USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
values ('dbadmin1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

---

insert into APP_ROLE (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_ADMIN');

insert into APP_ROLE (ROLE_ID, ROLE_NAME)
values (2, 'ROLE_USER');

---

insert into user_role (USER_ID, USER_NAME, ROLE_ID)
values (1, 'dbadmin1', 1);

insert into user_role (USER_ID, USER_NAME, ROLE_ID)
values (2, 'dbuser1', 2);

---


---

