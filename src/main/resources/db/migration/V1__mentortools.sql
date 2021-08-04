create table training_classes (id bigint not null auto_increment, name varchar(255), date_of_start date, date_of_finish date, primary key (id));
insert into training_classes (name, date_of_start, date_of_finish) values ('Java kezdő', '2020-11-01', '2021-03-31');
insert into training_classes (name, date_of_start, date_of_finish) values ('Java haladó', '2021-06-01', '2021-08-31');
insert into training_classes (name, date_of_start, date_of_finish) values ('JavaScript kezdő', '2020-11-01', '2021-03-31');
insert into training_classes (name, date_of_start, date_of_finish) values ('JavaScript haladó', '2020-06-01', '2021-08-31');