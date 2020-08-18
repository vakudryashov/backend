-- Table: users
CREATE TABLE IF NOT EXISTS users  (
  id       INT           PRIMARY KEY  NOT NULL,
  username VARCHAR (255) UNIQUE NOT NULL,
  password VARCHAR (255) NOT NULL,
  fullname VARCHAR (255)
);

-- Table: roles
CREATE TABLE IF NOT EXISTS roles (
  id   INT           PRIMARY KEY  NOT NULL,
  name VARCHAR (100) UNIQUE NOT NULL
);

-- Table for mapping user and roles: user_roles
CREATE TABLE IF NOT EXISTS user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  FOREIGN KEY (user_id)   REFERENCES users (id),
  FOREIGN KEY (role_id)   REFERENCES roles (id),
  UNIQUE (user_id,  role_id)
);

-- Insert data
INSERT INTO users values (1,'admin','$2a$10$.2cagzXwfBOLoMoumb9uruPPZ4Ls6PdsijVBMKO09.9fkrc3f6fje','admin');
insert into roles values (2,'ROLE_USER'),(2,'ROLE_ADMIN');
insert into user_roles values (1,2);
