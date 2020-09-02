drop table if exists products cascade;
create table products (id bigserial, title varchar(255), description varchar(5000), quantity int, primary key(id));
insert into products
(title, description, quantity) values
('Шляпа', 'котелок', 320),
('Шляпа', 'цилиндр', 80),
('Панама', 'панама', 80),
('Очки', '+2', 30);

drop table if exists categories cascade;
create table categories (id bigserial, title varchar(255), primary key(id));
insert into categories
(title) values
('Головные уборы'),
('Оптика');

drop table if exists products_categories cascade;
create table products_categories (product_id bigint not null, category_id bigint not null, primary key(product_id, category_id),
foreign key (product_id) references products(id), foreign key (category_id) references categories(id));
insert into products_categories (product_id, category_id) values (1, 1), (2, 1), (3, 1), (4, 2);