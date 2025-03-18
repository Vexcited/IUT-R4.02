create table individu (
  id    bigint  not null AUTO_INCREMENT, 
  birth_date date, 
  first_name varchar(255), 
  height integer, 
  last_name varchar(255), 
  title varchar(255), 
  primary key (id)
) engine=InnoDB;
