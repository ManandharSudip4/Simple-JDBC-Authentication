create table users(
	username varchar_ignorecase(50) not null primary key,
	password varchar_ignorecase(500) not null,
	enabled boolean not null
);

create table authorities (
	username varchar_ignorecase(50) not null,
	authority varchar_ignorecase(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username, authority);

INSERT INTO users (username, password, enabled) values ('user', 'pass', true);
INSERT INTO users (username, password, enabled) values ('admin', 'admin', true);

INSERT INTO authorities (username, authority) values ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) values ('admin', 'ROLE_ADMIN');


-- Even though our role is USER we user ROLE_USER

-- ROLE_USER is not just a syntax, but a convention used 
-- in Spring Security to represent a role. 
-- In Spring Security, roles are typically represented 
-- as strings with the "ROLE_" prefix followed 
-- by the role name.
