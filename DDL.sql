create schema uol;

use uol;

create user 'user'@'localhost' identified by 'pass123';

grant select, insert, delete, update on uol.* to user@'localhost';

create table registration (
  registration_id bigint unsigned not null auto_increment,
  registration_email varchar(255) not null,
  registration_password varchar(255) not null,
  registration_name varchar(255) not null,
  registration_cellphone varchar(100) not null,
  registration_user_agent varchar(255) not null,
  registration_name_browser varchar(255) not null,
  registration_version_browser varchar(255) not null,
  registration_system varchar(255) not null,
  registration_gpu_model varchar(255) not null,
  registration_ip varchar(255) not null,
  primary key (registration_id),
  unique key uni_registration_email (registration_email)
);

create table profile (
  profile_id bigint unsigned not null auto_increment,
  profile_uuid varchar(100) not null,
  profile_unique_hash varchar(255) not null,
  primary key (profile_id),
  unique key uni_profile_uuid (profile_uuid)
);

create table profile_registration (
  profile_id bigint unsigned not null,
  registration_id bigint unsigned not null,
  primary key (profile_id, registration_id),
  foreign key pr_profile_fk (profile_id) references profile (profile_id) on delete restrict on update cascade,
  foreign key pr_registration_fk (registration_id) references registration (registration_id) on delete restrict on update cascade
);

create table auth (
  auth_id bigint unsigned not null auto_increment,
  auth_role varchar(20) not null,
  primary key (auth_id),
  unique key unique_auth_role (auth_role)
);

create table registration_auth (
  registration_id bigint unsigned not null,
  auth_id bigint unsigned not null,
  primary key (registration_id, auth_id),
  foreign key auth_registration_fk (registration_id) references registration (registration_id) on delete restrict on update cascade,
  foreign key auth_auth_fk (auth_id) references auth (auth_id) on delete restrict on update cascade
);

insert into registration 
(registration_email, registration_password, registration_name, registration_cellphone, registration_user_agent, registration_name_browser, registration_version_browser, registration_system, registration_gpu_model, registration_ip)
values
('admin@admin.com', '$2a$10$i3.Z8Yv1Fwl0I5SNjdCGkOTRGQjGvHjh/gMZhdc3e7LIovAklqM6C', 'admin', '12', 'admin', 'admin', '10', 'windows', 'rtx', '0.0.0.0'),
('user@user.com', '$2a$10$i3.Z8Yv1Fwl0I5SNjdCGkOTRGQjGvHjh/gMZhdc3e7LIovAklqM6C', 'user', '12', 'user', 'usser', '10', 'windows', 'rtx', '0.0.0.0');

insert into profile 
(profile_uuid, profile_unique_hash)
values
('1234', '1234'),
('12345', '12345');

insert into profile_registration 
values
(1, 1),
(2, 2);

insert into auth 
(auth_role)
values
('ROLE_ADMIN'),
('ROLE_USER');

insert into registration_auth
values
(1, 1),
(2, 2);