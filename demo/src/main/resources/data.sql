--------------------------------------

insert into APP_USER (USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
values ('dbadmin1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into APP_USER (USER_NAME, ENCRYPTED_PASSWORD, ENABLED)
values ('dbuser1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
---

insert into APP_ROLE (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_ADMIN');

insert into APP_ROLE (ROLE_ID, ROLE_NAME)
values (2, 'ROLE_USER');

---

insert into USER_ROLE (USER_ID, USER_NAME, ROLE_ID)
values (1, 'dbadmin1', 1);

insert into USER_ROLE (USER_ID, USER_NAME, ROLE_ID)
values (2, 'dbuser1', 2);

---

insert into PRODUCT (ABOUT, ADDRESS, USER_ID, EMAIL, JOB_TITLE, LANGUAGE, NAME, TELEPHONE, WEBSITE, ENABLED)
values ('Something about you', '123 WallStreet', 1, 'john@mail.com', 'Engineer', 'English', 'John', '01234567', 'john@site.com', 1);

insert into PRODUCT (ABOUT, ADDRESS, USER_ID, EMAIL, JOB_TITLE, LANGUAGE, NAME, TELEPHONE, WEBSITE, ENABLED)
values ('Something about you', '456 Patrict', 1, 'alex@mail.com', 'Worker', 'English', 'Alex', '099999999', 'alex@site.com', 0);

---
