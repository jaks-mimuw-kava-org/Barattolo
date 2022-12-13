CREATE TABLE SimpleTestEntity(
id BIGINT PRIMARY KEY,
name varchar(50)
);

CREATE TABLE ComplexTestEntity(
name varchar(255) PRIMARY KEY,
last varchar(255),
height BIGINT,
age INT,
lastLogin DATE
);
