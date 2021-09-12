create table userpassw (
    id BIGINT PRIMARY KEY auto_increment,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    role varchar(20)
);
--create table users (
--    id BIGINT PRIMARY KEY auto_increment,
--    email varchar(255) NOT NULL UNIQUE,
--    first_name varchar(255) NOT NULL,
--    last_name varchar(255) NOT NULL,
--    password varchar(255) NOT NULL,
--    role varchar(20),
--    status varchar(20)
--);