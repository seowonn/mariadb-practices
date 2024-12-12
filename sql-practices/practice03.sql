-- 테이블 조인(JOIN) SQL 문제입니다.

-- 문제 1. 
-- 현재 급여가 많은 직원부터 직원의 사번, 이름, 그리고 연봉을 출력 하시오.
SELECT a.emp_no, CONCAT(a.first_name, ' ', a.last_name) AS full_name, b.salary
FROM employees a
	JOIN salaries b ON a.emp_no = b.emp_no
WHERE b.to_date > CURRENT_DATE()
ORDER BY b.salary DESC;

-- 문제2.
-- 전체 사원의 사번, 이름, 현재 직책을 이름 순서로 출력하세요.
SELECT  a.emp_no, CONCAT(a.first_name, ' ', a.last_name) AS full_name, b.title
FROM employees a
	JOIN titles b ON a.emp_no = b.emp_no
WHERE b.to_date > CURRENT_DATE()
ORDER BY a.last_name , a.first_name;

-- 문제3.
-- 전체 사원의 사번, 이름, 현재 부서를 이름 순서로 출력하세요.
SELECT a.emp_no, CONCAT(a.first_name, ' ', a.last_name) AS full_name, c.dept_name
FROM employees a
	JOIN dept_emp b ON b.emp_no = a.emp_no
	JOIN departments c ON c.dept_no = b.dept_no
WHERE b.to_date > CURRENT_DATE()
ORDER BY a.first_name;

-- 문제4.
-- 현재 근무중인 전체 사원의 사번, 이름, 연봉, 직책, 부서를 모두 이름 순서로 출력합니다.
SELECT a.emp_no, CONCAT(a.first_name, ' ', a.last_name) AS full_name, b.salary, c.title, e.dept_name 
FROM employees a
	JOIN salaries b ON b.emp_no = a.emp_no
	JOIN titles c ON c.emp_no = a.emp_no
    JOIN dept_emp d ON d.emp_no = a.emp_no
    JOIN departments e ON e.dept_no = d.dept_no
WHERE b.to_date > CURRENT_DATE()
	AND c.to_date > CURRENT_DATE()
    AND d.to_date > CURRENT_DATE()
ORDER BY a.first_name;

-- 문제5.
-- 'Technique Leader'의 직책으로 과거에 근무한 적이 있는 모든 사원의 사번과 이름을 출력하세요.
-- (현재 'Technique Leader'의 직책으로 근무하는 사원은 고려하지 않습니다.) 현재가 아닌! 문법이 들어가야함!     
SELECT a.emp_no, CONCAT(a.first_name, ' ', a.last_name) AS full_name 
FROM employees a
	JOIN titles b ON b.emp_no = a.emp_no
WHERE b.title = 'Technique Leader'
	AND b.to_date <> CURRENT_DATE(); -- 현재가 아님! (<>) 반영

-- 문제6.
-- 직원 이름(last_name) 중에서 S로 시작하는 직원들의 이름, 부서명, 직책을 조회하세요.
-- 고려사항: titles, dept_emp에서 중복 emp_no 존재함 (미반영)    
SELECT a.emp_no, CONCAT(a.first_name, ' ', a.last_name) AS full_name, c.dept_name, d.title 
FROM employees a
	JOIN dept_emp b ON b.emp_no = a.emp_no
	JOIN departments c ON c.dept_no = b.dept_no
	JOIN titles d ON d.emp_no = a.emp_no
WHERE a.last_name LIKE 'S%';

-- 문제7.
-- 현재, 직책이 Engineer인 사원 중에서 현재 급여가 40,000 이상인 사원들의 
-- 사번, 이름, 급여 그리고 타이틀을 급여가 큰 순서대로 출력하세요.      
SELECT a.emp_no, CONCAT(a.first_name, ' ', a.last_name) AS full_name, b.salary, c.title 
FROM employees a
	JOIN salaries b ON b.emp_no = a.emp_no
	JOIN titles c ON c.emp_no = a.emp_no
WHERE b.to_date > CURRENT_DATE()
	AND c.to_date > CURRENT_DATE()
	AND b.salary >= 40000
    AND c.title = 'Engineer'
ORDER BY b.salary DESC;

-- 문제8.
-- 현재, 평균급여가 50,000이 넘는 직책을 직책과 평균급여을 평균급여가 큰 순서대로 출력하세요. 
SELECT a.title, AVG(b.salary) FROM titles a
	JOIN salaries b ON a.emp_no = b.emp_no
WHERE a.to_date > CURRENT_DATE()
	AND b.to_date > CURRENT_DATE()
GROUP BY a.title
	HAVING AVG(b.salary) > 50000
ORDER BY AVG(b.salary) DESC;

-- 문제9.
-- 현재, 부서별 평균급여을 평균급여가 큰 순서대로 부서명과 평균연봉을 출력 하세요.
SELECT d.dept_name, AVG(b.salary) 
FROM employees a
	JOIN salaries b ON b.emp_no = a.emp_no
	JOIN dept_emp c ON c.emp_no = a.emp_no
	JOIN departments d ON d.dept_no = c.dept_no
WHERE b.to_date > CURRENT_DATE()
	AND c.to_date > CURRENT_DATE()
GROUP BY d.dept_name
ORDER BY AVG(b.salary) DESC;

-- 문제10.
-- 현재, 직책별 평균급여를 평균급여가 큰 직책 순서대로 직책명과 그 평균연봉을 출력 하세요. 
SELECT c.title, AVG(b.salary) 
FROM employees a
	JOIN salaries b ON b.emp_no = a.emp_no
	JOIN titles c ON c.emp_no = a.emp_no
WHERE b.to_date > CURRENT_DATE()
	AND c.to_date > CURRENT_DATE()
GROUP BY c.title
ORDER BY AVG(b.salary) DESC;
