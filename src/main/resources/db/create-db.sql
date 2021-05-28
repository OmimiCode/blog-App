drop database if not exists blogdb;
create user if not exists 'bloguser'@'localhost' identified by 'Blog@123';
grant all privileges on blogdb.* to 'bloguser'@'localhost';
flush privileges ;