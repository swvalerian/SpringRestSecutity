create table events (
    id BIGINT PRIMARY KEY Auto_increment,
    created DATETIME,
    updated DATETIME,
    deleted DATETIME,
    file_id BIGINT not null,
    user_id BIGINT not null,
    foreign key (user_id) REFERENCES users (id),
    foreign key (file_id) REFERENCES files (id)
);