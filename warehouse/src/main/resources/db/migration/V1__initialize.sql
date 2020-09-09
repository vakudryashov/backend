ALTER DATABASE `webserver_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
drop table if exists products cascade;
create table products (id bigint NOT NULL AUTO_INCREMENT, title varchar(255), description varchar(5000), quantity int, primary key(id));
insert into products
(title, description, quantity) values
('Шляпа', 'котелок', 320),
('Шляпа', 'цилиндр', 80),
('Панама', 'панама', 80),
('Очки', '+2', 30);

drop table if exists categories cascade;
create table categories (id bigint NOT NULL AUTO_INCREMENT, title varchar(255), primary key(id));
insert into categories
(title) values
('Головные уборы'),
('Оптика');

drop table if exists products_categories cascade;
create table products_categories (product_id bigint not null, category_id bigint not null, primary key(product_id, category_id),
foreign key (product_id) references products(id), foreign key (category_id) references categories(id));
insert into products_categories (product_id, category_id) values (1, 1), (2, 1), (3, 1), (4, 2);


drop table if exists users;
create table users (
  id                    bigint NOT NULL AUTO_INCREMENT,
  login                 VARCHAR(50) not null unique,
  password              VARCHAR(80) not null,
  fullname              VARCHAR(255),
  phone                 VARCHAR(30),
  email                 VARCHAR(50),
  PRIMARY KEY (id)
);

drop table if exists roles;
create table roles (
  id                    bigint NOT NULL AUTO_INCREMENT,
  name                  VARCHAR(50) not null,
  primary key (id)
);

drop table if exists users_roles;
create table users_roles (
  user_id               BIGINT NOT NULL,
  role_id               BIGINT NOT NULL,
  primary key (user_id, role_id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
);

insert into roles (name)
values
('ROLE_CUSTOMER'), ('ROLE_MANAGER'), ('ROLE_ADMIN');

insert into users (login,password, fullname, phone, email)
values

-- ('11111111','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i','admin','admin','admin@gmail.com');
-- пароль: admin

('admin', '$2a$10$iIngXqEsF7aTmZ4IvPZs/enNAba47.OOq5OlqWpQM3JGdlrPCuHTS','Иванов Иван Иванович', '+7 (987) 654-32-10', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values
(1, 1),
(1, 2),
(1, 3);