CREATE TABLE SimpleTestEntity(
id SERIAL PRIMARY KEY,
name varchar(50)
);

CREATE TABLE ComplexTestEntity(
name varchar(255) PRIMARY KEY,
lastName varchar(255),
height BIGINT,
age INT,
lastLogin DATE
);
