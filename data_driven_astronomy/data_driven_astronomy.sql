/* WEEK 3 */
/* init,sql*/
CREATE TABLE Star (
  kepler_id INTEGER NOT NULL,
  koi_name VARCHAR(20) NOT NULL,
  t_eff INTEGER,
  radius FLOAT(5),
  PRIMARY KEY (koi_name)
);

COPY Star (kepler_id, koi_name, t_eff, radius) FROM 'week3data/stars.csv' CSV;

\d star

CREATE TABLE Planet (
  kepler_id INTEGER NOT NULL,
  koi_name VARCHAR(20) NOT NULL,
  kepler_name VARCHAR(20),
  status VARCHAR(20) NOT NULL,
  period FLOAT,
  radius FLOAT,
  t_eq INTEGER,
  PRIMARY KEY (koi_name)
);

COPY Planet (kepler_id, koi_name, kepler_name, status, period, radius, t_eq) FROM 'week3data/planets.csv' CSV;

\d planet
/**/
select radius, t_eff from star where radius > 1
select kepler_id, t_eff from star where t_eff >= 5000 and t_eff <= 6000
select kepler_id, t_eff from star where t_eff between 5000 and 6000
select kepler_name, radius from planet where kepler_name is not null and radius between 1 and 3
select min(radius), max(radius), avg(radius), stddev(radius) from planet where kepler_name is null
select kepler_id, count(koi_name) from planet group by kepler_id having count(koi_name) > 1 order by count(koi_name) desc
/*Write a query that returns the radius of each star and planet pair whose radii have a ratio greater than the Sun-to-Earth radius ratio. 
Order the results in descending order based on the stellar radii. Use sun_radius and planet_radius as attribute aliases for the star and planet radii.*/
select s.radius as sun_radius, p.radius as planet_radius from star as s, planet as p
where s.kepler_id = p.kepler_id and s.radius > p.radius order by s.radius desc
/*Write a query which counts the number of planets in each solar system where the corresponding stars are larger than our sun (i.e. their radius is larger than 1).*/
select s.radius as radius, count(p.koi_name) as count from star as s join planet as p using (kepler_id)
where s.radius >=1 group by s.kepler_id having count(p.koi_name) > 1 order by s.radius desc
/*returns the kepler_id, t_eff and radius for all stars in the Star table which haven't got a planet as join partner. Order the resulting table based on the t_eff attribute in descending order.*/
/*i.e start which kepler_id is not presnet in the planet table */
select s.kepler_id, s.t_eff, s.radius from star as s left outer join planet as p using (kepler_id) where p.koi_name is null order by s.t_eff desc
/*Write a query which queries both the Star and the Planet table and calculates the following quantities:
1) the average value of the planets' equilibrium temperature t_eq, rounded to one decimal place;
2) the minimum effective temperature t_eff of the stars;
3) the maximum value of t_eff;
Your query should only use those star-planet pairs whose stars have a higher temperature (t_eff) than the average star temperature in the table. Try to use a subquery to solve this problem!*/
select round(avg(p.t_eq), 1), min(s.t_eff), max(s.t_eff) from star as s join planet as p using (kepler_id)
where s.t_eff > (select avg(s.t_eff) from star as s)
/*planets which ordbits orund the 5 largest stars*/
select planet.koi_name, planet.radius, star.radius from planet join star using (kepler_id) where star.kepler_id in 
(select kepler_id from star order by radius desc limit 5)

/**/
SELECT koi_name, radius FROM Planet ORDER BY radius DESC LIMIT 5;
/*HAVING instead of WHERE on the aggregate function, other attributes can be used with WHERE */
SELECT radius, COUNT(koi_name) FROM Planet GROUP BY radius HAVING COUNT(koi_name) > 1;
SELECT radius, COUNT(koi_name) FROM Planet WHERE t_eq BETWEEN 500 AND 1000 GROUP BY radius HAVING COUNT(koi_name) > 1;
SELECT Star.kepler_id, Planet.koi_name FROM Star as s , Planet as p WHERE s.kepler_id = p.kepler_id;
/* JOIN ... USING: field or attribute or JOIN .. ON: condition */
SELECT Star.kepler_id, Planet.koi_name FROM Star, Planet WHERE Star.kepler_id = Planet.kepler_id;
SELECT Star.kepler_id, Planet.koi_name FROM Star JOIN Planet ON Star.kepler_id = Planet.kepler_id;
SELECT Star.kepler_id, Planet.koi_name FROM Star JOIN Planet USING (kepler_id);
SELECT Star.kepler_id, Planet.koi_name FROM Star JOIN Planet ON Star.radius > 1.5 AND Planet.t_eq > 2000;
/* <table1> LEFT OUTER JOIN <table2>
Here all rows from <table1> are kept and missing matches from <table2> are replaced with NULL values.

<table1> RIGHT OUTER JOIN <table2>
All rows from <table2> are kept and missing matches from <table1> are replaced with NULL values.

<table1> FULL OUTER JOIN <table2>
All rows from both tables are kept. */

/*If an attribute in the encapsulating query is used in the nested query, it's going to be a co-related subquery.*/
/* both examples below return the same result. use \timing to see execution times*/
/* CO-RELATED*/
\timing
SELECT s.kepler_id FROM Star s WHERE EXISTS (
  SELECT * FROM Planet p WHERE s.kepler_id = p.kepler_id AND p.radius < 1
  );
/* NON-CO-RELATED*/
SELECT s.kepler_id FROM Star s WHERE s.kepler_id IN (
  SELECT p.kepler_id FROM Planet p WHERE p.radius < 1
);

/* WEEK 4 */
INSERT INTO Star (kepler_id, t_eff, radius) VALUES (2713050, 5000, 0.956);
DELETE FROM Planet WHERE kepler_id = 2713049;
UPDATE Star SET t_eff = 6000 WHERE kepler_id = 2713049;

/* constraints: NOT NULL, UNIQUE, DEFAULT, CHECK, PRIMARY KEY (= NOT NULL + UNQUE), FOREIGN KEY (e.g. REFERENCES Star (kepler_id)) */
CREATE TABLE Star (
  kepler_id INTEGER PRIMARY KEY,
  t_eff INTEGER CHECK (t_eff > 3000),
  radius FLOAT
);

ALTER TABLE Star ADD COLUMN ra FLOAT, ADD COLUMN decl FLOAT;
ALTER TABLE Star DROP COLUMN ra, DROP COLUMN decl;
ALTER TABLE Star ALTER COLUMN t_eff SET DATA TYPE FLOAT;
ALTER TABLE Star ADD CONSTRAINT radius CHECK(radius > 0);