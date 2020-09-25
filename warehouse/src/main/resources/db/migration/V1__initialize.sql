create table products (id bigint NOT NULL AUTO_INCREMENT, title varchar(255), primary key(id));
insert into products (title) values ('Шляпа'), ('Панама'), ('Очки');

create table categories (id bigint NOT NULL AUTO_INCREMENT, title varchar(255) NOT NULL, primary key(id));
insert into categories (title) values ('Головные уборы'), ('Оптика');

create table products_categories (product_id bigint not null, category_id bigint not null, primary key(product_id, category_id),
foreign key (product_id) references products(id), foreign key (category_id) references categories(id));
insert into products_categories (product_id, category_id) values (1, 1), (2, 1), (3, 2);

CREATE TABLE units (id bigint NOT NULL AUTO_INCREMENT, title VARCHAR(40) NULL, PRIMARY KEY (id));
insert into units (title) values ('шт');
create table products_units (product_id bigint not null, unit_id bigint not null, primary key(product_id, unit_id),
foreign key (product_id) references products(id), foreign key (unit_id) references units(id));
insert into products_units values (1,1),(2,1),(3,1);

CREATE TABLE contractors (id bigint NOT NULL AUTO_INCREMENT, title VARCHAR(255) NULL, PRIMARY KEY (id));
insert into contractors (title) values ('ООО "Поставщик"'),('ООО "Получатель"');
create table products_contractors (product_id bigint not null, contractor_id bigint not null, primary key(product_id, contractor_id),
foreign key (product_id) references products(id), foreign key (contractor_id) references contractors(id));
insert into products_contractors values (1,1),(2,1),(3,1);

CREATE TABLE postings (id bigint NOT NULL AUTO_INCREMENT, posting_date DATETIME, quantity DECIMAL(12,3) UNSIGNED, PRIMARY KEY (id));
create table postings_products (posting_id bigint not null, product_id bigint not null, primary key(posting_id, product_id),
foreign key (posting_id) references postings(id), foreign key (product_id) references products(id));

CREATE TABLE shipments (id bigint NOT NULL AUTO_INCREMENT, shipment_date DATETIME, quantity DECIMAL(12,3) UNSIGNED, PRIMARY KEY (id));
create table shipments_products (shipment_id bigint not null, product_id bigint not null, primary key(shipment_id, product_id),
foreign key (shipment_id) references shipments(id), foreign key (product_id) references products(id));

create table shipments_contractors (shipment_id bigint not null, contractor_id bigint not null, primary key(shipment_id, contractor_id),
foreign key (shipment_id) references shipments(id), foreign key (contractor_id) references contractors(id));

create table products_history (id bigint NOT NULL AUTO_INCREMENT, products_id bigint not null, quantity int, primary key(id),
foreign key (products_id) references products(id));

create table users (
  id                    bigint NOT NULL AUTO_INCREMENT,
  login                 VARCHAR(50) not null unique,
  password              VARCHAR(80) not null,
  firstname              VARCHAR(50),
  lastname              VARCHAR(50),
  phone                 VARCHAR(30),
  email                 VARCHAR(50),
  PRIMARY KEY (id)
);

create table roles (
  id                    bigint NOT NULL AUTO_INCREMENT,
  name                  VARCHAR(50) not null,
  primary key (id)
);

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

insert into users (login,password, firstname, lastname, phone, email)
values

-- ('11111111','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i','admin','admin','admin@gmail.com');
-- пароль: admin

('admin', '$2a$10$iIngXqEsF7aTmZ4IvPZs/enNAba47.OOq5OlqWpQM3JGdlrPCuHTS','Иван', 'Иванов', '+7 (987) 654-32-10', 'admin@example.com');

insert into users_roles (user_id, role_id)
values
(1, 1),
(1, 2),
(1, 3);

create table postings_users (posting_id bigint not null, user_id bigint not null, primary key(posting_id, user_id),
foreign key (posting_id) references postings(id), foreign key (user_id) references users(id));

create table shipments_users (shipment_id bigint not null, user_id bigint not null, primary key(shipment_id, user_id),
foreign key (shipment_id) references shipments(id), foreign key (user_id) references users(id));

CREATE VIEW funds AS
select postings.id, product_title, unit_title, (positive-ifnull(negative,0)) as balance
from (
	select pr.id, pr.title as product_title, sum(ps.quantity) positive, ms.title as unit_title
	from products pr
	join postings ps on 1 = 1
	join postings_products pspr on 1 = 1
		and pspr.product_id = pr.id
		and pspr.posting_id = ps.id
	join units ms on 1 = 1
	join products_units prms on 1 = 1
		and prms.product_id = pr.id
		and prms.unit_id = ms.id
	group by pr.id) as postings
left join (
	select pr.id, sum(sh.quantity) negative
	from products pr
	join shipments sh on 1 = 1
	join shipments_products shpr on 1 = 1
		and shpr.product_id = pr.id
		and shpr.shipment_id = sh.id
	join units ms on 1 = 1
	join products_units prms on 1 = 1
		and prms.product_id = pr.id
		and prms.unit_id = ms.id
	group by pr.id) shipments on 1 = 1
and postings.id = shipments.id
where positive > ifnull(negative,0);