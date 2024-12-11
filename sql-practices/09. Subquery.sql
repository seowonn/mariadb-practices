-- Subquery
use employees;

-- select 절의 서브쿼리
select (select 1+1 from dual) from dual;
-- insert into t1 values(null, (select max(no) + 1 from t1))

-- 2) from 절의 서브쿼리
select now() as n, sysdate() as s, 3 + 1 as r from dual;
select a.n, a.r

-- 3) where 절의 서브쿼리
-- 예) 현재, Fai Bale이 근무하는 부서에서 근무하는 직원의 사번, 전체 이름을 출력해보기 
select b.dept_no
from employees a, dept_emp b
where a.emp_no = b.emp_no
and b.to_date > current_date()
and concat(a.first_name, ' ', a.last_name) = 'Fai Bale';

-- d004
select a.emp_no, a.first_name
from employees a, dept_emp b
where a.emp_no = b.emp_no
and b.to_date > current_date()
and b.dept_no = 'd004';

select a.emp_no, a.first_name
from employees a, dept_emp b
where a.emp_no = b.emp_no
and b.to_date > current_date(); -- mysql에서는 curdate()를 더 권장


-- 3-1) 단일행 연산자: =, >, <, >=, <=, <>, !=
-- 실습문제1: 현재, 전체 사원의 평균 연봉보다 적은 급여를 받는 사원의 이름과 급여를 출력하기
SELECT a.first_name, b.salary 
FROM employees a 
JOIN salaries b ON a.emp_no = b.emp_no 
WHERE b.to_date > CURDATE() 
  AND b.salary < (
      SELECT AVG(salary) 
      FROM salaries 
      WHERE to_date < CURDATE()
  );

-- 실습문제2: 현재, 직책별 평균 급여 중에 가장 적은 평균 급여의 직책이름과 그 평균급여를 출력하기
select * from titles;
select * from salaries;
select * from departments;
select * from employees;
select * from dept_emp;

-- 1) 직책별 평균 급여
select b.title, avg(a.salary) from salaries a, titles b where a.emp_no = b.emp_no
and a.to_date > curdate()
and b.to_date > curdate()
GROUP BY b.title;

-- 2) 직책별 가장 적은 평균 급여
select title, min(avg_salary) from (
	select b.title, avg(a.salary) as avg_salary from salaries a, titles b 
    where a.emp_no = b.emp_no
	and a.to_date > curdate()
	and b.to_date > curdate()
	GROUP BY b.title
) a;

-- 3) sol1: where절 subquery(=)
select b.title, avg(a.salary) as avg_salary from salaries a, titles b 
    where a.emp_no = b.emp_no
	and a.to_date > curdate()
	and b.to_date > curdate()
	GROUP BY b.title having avg(a.salary) = (
		select min(avg_salary) from (
			select b.title, avg(a.salary) as avg_salary from salaries a, titles b 
			where a.emp_no = b.emp_no
			and a.to_date > curdate()
			and b.to_date > curdate()
			GROUP BY b.title
		) a
    );

-- 4) sol2: top-k
select b.title, avg(a.salary) from salaries a, titles b where a.emp_no = b.emp_no
and a.to_date > curdate()
and b.to_date > curdate()
GROUP BY b.title ORDER BY avg(a.salary) asc
limit 1;


-- 3-2) 복수행 연산자

-- 실습문제3
-- 현재, 급여가 50000 이상인 직원의 이름과 급여를 출력하기
-- sol1
select first_name from employees a
join salaries b on a.emp_no = b.emp_no
where b.salary >= 50000 and b.to_date > curdate();

-- sol2
select first_name from employees a
join salaries b on a.emp_no = b.emp_no
where b.to_date > curdate() 
and (a.emp_no, b.salary) in (
	select emp_no, salary from salaries where to_date > curdate()
);

-- 실습문제4: 현재, 각 부서별 최고급여를 받고 있는 직원의 이름, 그 부서 이름, 급여를 출력해보기
select a.first_name, d.dept_name, b.salary 
from employees a
join salaries b on a.emp_no = b.emp_no
join dept_emp c on c.emp_no = a.emp_no
join departments d on d.dept_no = c.dept_no
where c.to_date > curdate()
and b.to_date > curdate()
and (b.salary, d.dept_no) in (
	select max(b.salary), a.dept_no from dept_emp a
	join salaries b on a.emp_no = b.emp_no
    where a.to_date > curdate()
    and b.to_date > curdate()
	group by a.dept_no
);
    
-- sol2: from절 subquery & join
select a.first_name, d.dept_name, b.salary 
from employees a
join salaries b on a.emp_no = b.emp_no
join dept_emp c on c.emp_no = a.emp_no
join departments d on d.dept_no = c.dept_no
where c.to_date > curdate()
and b.to_date > curdate()
and (b.salary, d.dept_no) in (
	select max(b.salary), a.dept_no from dept_emp a
	join salaries b on a.emp_no = b.emp_no
    where a.to_date > curdate()
    and b.to_date > curdate()
	group by a.dept_no
);
