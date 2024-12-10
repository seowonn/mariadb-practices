use webdb;
select version(), current_date(), now() FROM dual;

-- 수학함수, 사칙연산도 된다.
select sin(pi()/4), 1 + 2 * 3 - 4 / 5 from dual;

-- 대소문자 구분이 없다. 
select version(), current_DATE(), NOW() from dUAL;

-- table 생성
create table pet (
	name varchar(100),
    owner varchar(20),
    species varchar(20),
    gender char(1),
    birth date,
    death date
);

-- schema 확인
desc pet;
describe pet;

-- table 삭제
drop table pet;

-- insert (C)
insert into pet values('성탄이', '손서원', 'dog', 'm', '2012-12-25', null);

-- select (R)
select * from pet;

-- update (U)
-- 아래처럼 하면 반영이 안됨. 보안 때문임! 
update pet set name='성타니' where name='성탄이';
update pet set death=null where death= '0000-00-00';

-- delete (D)
delete from pet where name = '성타니';

-- load data: mysql(CLI) Local 전용 (원격으로 안됨) 
load data local infile '/root/pet.txt' into table pet;

-- select 연습
select name, species from pet
where name = 'bowser';

select name, birth, death
from pet
where death is not null;

-- 와일드 카드
-- 언더바는 한 자리 의미
-- %는 여러 자리 수 가능
select name from pet
where name like '____';

-- 집계
-- count는 특정 칼럼을 셀때는 null을 뺀다.
-- 하지만 *로 COUNT할 경우 NULL도 센다. 
select count(*) from pet;