create DATABASE osbpldb;
use osbpldb;
create user 'adminosbplusr'@'localhost' identified by 'adminosbplpwd';
grant all on osbpldb.* to 'adminosbplusr'@'localhost';
FLUSH PRIVILEGES;

create user 'adminosbplusr'@'127.0.0.1' identified by 'adminosbplpwd';
create user 'adminosbplusr'@'contetial-test-1' identified by 'adminosbplpwd';
create user 'adminosbplusr'@'::1' identified by 'adminosbplpwd';
grant all on osbpldb.* to 'adminosbplusr'@'127.0.0.1';
grant all on osbpldb.* to 'adminosbplusr'@'contetial-test-1';
grant all on osbpldb.* to 'adminosbplusr'@'::1';
