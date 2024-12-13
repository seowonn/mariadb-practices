-- DDL/DML 연습

create table member (
	id int not null AUTO_INCREMENT,
    email varchar(200) not null,
    password varchar(64) not null,
    name varchar(50) not null,
    department varchar(100),
    primary key(id)
);

-- 해당 테이블의 컬럼들을 볼 수 있는 명령어 (DESC 테이블명)
desc member;

-- after: 컬럼 배치에 순서를 주고자할 경우
alter table member add column identification_number char(13) not null after email;
alter table member add column department varchar(100);

-- DDL - ALTER: 컬럼 제거
alter table member drop renewal_department;

alter table member change column department renewal_department varchar(100) not null;

alter table member add column profile text;
alter table member drop identification_number;

-- insert, 컬럼들은 auto라도 생략은 할 수 없다.
insert into member values (1, 'sonseowon5@gmail.com', '1234567890123', 
	password('1234'), '손서원', '개발팀', null);
insert into member values (null, 'sonseowon2@gmail.com', '1234567890123', 
	password('1234'), '손서원2', '개발팀', null);
select * from member;

