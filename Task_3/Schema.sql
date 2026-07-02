create table product (
	id serial unique not null;
	name char(30) not null;
	price integer check(price > 0)
);

insert into product('name', price) value ('laptop', 100000)

insert into product('name', price) value ('phone', 80000)

insert into product('name', price) value ('tv', 150000)