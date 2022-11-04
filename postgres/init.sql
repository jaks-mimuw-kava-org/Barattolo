DROP DATABASE IF EXISTS test;    

CREATE DATABASE test;    

\c test;        

CREATE TABLE TestEntity(
id SERIAL PRIMARY KEY,
name varchar(50)
);    
