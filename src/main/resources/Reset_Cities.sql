drop table if exists cities;


CREATE TABLE if not exists cities(
    id INT PRIMARY KEY AUTO_INCREMENT,
    city varchar(255) not null,
    isPlayed BIT not null
    );

INSERT INTO cities values (1, 'антверпен', false);
INSERT INTO cities values (2, 'новгород', false);
INSERT INTO cities values (3, 'днепр', false);
INSERT INTO cities values (4, 'рига', false);
INSERT INTO cities values (5, 'николаев', false);
INSERT INTO cities values (6, 'вологда', false);
INSERT INTO cities values (7, 'вильнюс', false);
INSERT INTO cities values (8, 'самара', false);
INSERT INTO cities values (9, 'армавир', false);
INSERT INTO cities values (10, 'ростов', false);