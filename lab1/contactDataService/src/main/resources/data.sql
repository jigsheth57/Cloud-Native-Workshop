-- This file will be automatically run against the app's database on startup.
-- Spring Boot looks for a file named data.sql (and schema.sql) in the classpath
-- to run on startup.
-- See http://docs.spring.io/spring-boot/docs/1.2.2.RELEASE/reference/htmlsingle/#howto-intialize-a-database-using-spring-jdbc
-- for more information.
insert into contact (title, first_name, last_name, email, phone) values ('Mr.', 'Jig', 'Sheth', 'jigsheth@pivotal.io', '312-555-1212');
