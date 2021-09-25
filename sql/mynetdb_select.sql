USE mynetdb;
INSERT INTO `user` (login, password, role_id, cash) VALUES ("ivan", "ivan", 1, 0);
INSERT INTO `user` (login, password) VALUES ("ivan", "ivan");

SET @p_id = 7;
INSERT INTO service (user_id, product_id, category_id, status_id) VALUES(1, 1, (SELECT category_id FROM product WHERE @p_id = id), (SELECT id FROM status WHERE name LIKE "Run"));


SELECT * FROM role;
SELECT * FROM `user`;

SELECT * FROM `user` ORDER BY id;
SELECT * FROM status;
SELECT * FROM category;
SELECT * FROM product;
DELETE FROM product WHERE id LIKE 12; 

SELECT user.id, user.login, product_id
FROM user, service
WHERE product_id=4 and user.id = user_id;

UPDATE product SET name="inTerNet", price=444 WHERE id=4;

SELECT * FROM service;


use mynetdb;
SELECT * FROM status WHERE id LIKE 2;
SELECT * FROM service;
SELECT * FROM service WHERE user_id LIKE 1;
SELECT * FROM service WHERE id LIKE 2;

INSERT INTO category (name) VALUES("My category");
DELETE FROM category WHERE id LIKE 4;
INSERT INTO product (name, price, description, category_id) VALUES ("telephon", 33.0, DEFAULT, 17);

SELECT * FROM category;

UPDATE service SET status_id = 1 WHERE id = 1;

INSERT INTO service(user_id, product_id, category_id, status_id) VALUES(2, 4, 3, 1);

SELECT * FROM user WHERE login="admin" AND password="admin";

SELECT * FROM product WHERE category_id LIKE 1;
SELECT * FROM users_teams;
INSERT INTO `role`(name) VALUES("user");
SELECT * FROM `role`;

SELECT * FROM category WHERE id LIKE 6;
SELECT * FROM timeT;
SELECT * FROM timeT WHERE service_id LIKE 3;

UPDATE timeT SET `time`=CURRENT_TIMESTAMP, total=0 WHERE id=3;



UPDATE service SET status_id=1 WHERE id=1; 