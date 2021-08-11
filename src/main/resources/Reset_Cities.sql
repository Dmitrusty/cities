drop table if exists city;


CREATE TABLE if not exists city(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(255) not null,
    played BIT not null
    );

INSERT INTO city values (1, 'антверпен', false);
INSERT INTO city values (2, 'новгород', false);
INSERT INTO city values (3, 'днепр', false);
INSERT INTO city values (4, 'рига', false);
INSERT INTO city values (5, 'николаев', false);
INSERT INTO city values (6, 'вологда', false);
INSERT INTO city values (7, 'вильнюс', false);
INSERT INTO city values (8, 'самара', false);
INSERT INTO city values (9, 'армавир', false);
INSERT INTO city values (10, 'ростов', false);