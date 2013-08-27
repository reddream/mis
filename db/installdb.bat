@ECHO OFF
@set password=
@set /p password=Please input the password of root:
@set isdropdb=y
@set /p isdropdb=Do you want to drop your current db and user mis? please choose y/n,the default is y:
:begin
@echo begin db installation
if isdropdb=="y" goto dropdb else goto createdb
:dropdb
@echo drop and create database
mysql -h localhost -uroot -p%password% < dropuseranddb.sql >sql.out
:createdb
mysql -h localhost -uroot -p%password% < createdb.sql >sql.out
:process  
@echo install the strcture......
mysql -h localhost -uroot -p%password% < mis.sql >sql.out 
@echo install procedures
mysql -h localhost -uroot -p%password% < procedure.sql >sql.out 
@echo initalize data......
mysql -h localhost -uroot -p%password% < data.sql >sql.out 
:grant
@echo grant all privileges to user mis with password...
mysql -h localhost -uroot -p%password% < grant.sql >sql.out 
@echo database installed
:end