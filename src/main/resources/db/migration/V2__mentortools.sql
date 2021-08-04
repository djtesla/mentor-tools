create table students (id bigint not null auto_increment, name varchar(255), email varchar(255), github_username varchar(255), comment varchar(255), primary key (id));
insert into students (name, email, github_username, comment) values ('Jack Sparrow', 'jack_sparrow@carib.com', 'jacksparrow', 'ok');
insert into students (name, email, github_username, comment) values ('Mary Martial', 'mary_martial@mortal.com', 'marymartial', 'ok');
insert into students (name, email, github_username, comment) values ('Liu King', 'liu_king@mortal.com', 'liuking', 'ok');
create table trainingclass_student (trainingclass_id bigint not null, student_id bigint not null);
