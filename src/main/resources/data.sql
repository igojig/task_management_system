insert into users(name, email, password) values('igor', 'igojig@mail.ru', '$2y$10$3R8i09vdUbKlGe/dE38uzOQxdskymgcFrKbo3kkQdb23X.OjvFELS');
insert into users(name, email, password) values('ivan', 'ivan@mail.ru', '$2y$10$3R8i09vdUbKlGe/dE38uzOQxdskymgcFrKbo3kkQdb23X.OjvFELS');

insert into roles(role) values('USER'), ('ADMIN');

insert into users_roles(user_id, role_id) values (1, 2);
insert into users_roles(user_id, role_id) values (2, 1);