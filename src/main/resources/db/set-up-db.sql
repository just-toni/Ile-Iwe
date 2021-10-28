create database if not exists ileiwedb;

create user if not exists 'ileiwe_user'@'localhost' identified by 'ileiwe123';
grant all privileges on ileiwedb.* to 'ileiwe_user'@'localhost';
flush privileges;