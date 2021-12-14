CREATE DATABASE BikesStations ;
use BikesStations;
CREATE TABLE STATIONS(
	id BIGINT NOT NULL PRIMARY KEY auto_increment,
	name VARCHAR(255),
	address VARCHAR(255),
	available_bike_stands INT,
    available_bikes INT,
	lat DECIMAL(3,3),
	lng DECIMAL(3,3),
    status bool
    );
