-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 단 조회결과는 급여의 내림차순으로 정렬되어 나타나야 합니다. 

-- 문제1.
-- 현재 전체 사원의 평균 급여보다 많은 급여를 받는 사원은 몇 명이나 있습니까?
SELECT COUNT(a.emp_no) FROM employees a
	JOIN salaries b ON b.emp_no = a.emp_no
WHERE b.to_date > current_date()
AND b.salary > (
	SELECT AVG(s.salary) FROM salaries s 
	WHERE s.to_date > current_date()
);

-- 문제2. 
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 급여을 조회하세요. 단 조회결과는 급여의 내림차순으로 정렬합니다.
SELECT a.emp_no, a.last_name, MAX(b.salary) FROM employees a
	JOIN salaries b ON b.emp_no = a.emp_no
	JOIN dept_emp c ON c.emp_no = a.emp_no
WHERE b.to_date > current_date()
	AND c.to_date > current_date()
GROUP BY c.dept_no
ORDER BY MAX(b.salary) DESC;

-- 문제3.
-- 현재, 사원 자신들의 부서의 평균급여보다 급여가 많은 사원들의 사번, 이름 그리고 급여를 조회하세요 
SELECT a.emp_no, a.last_name, b.salary, t1.dept_no, t1.avg_salary FROM employees a
	JOIN salaries b ON b.emp_no = a.emp_no
	JOIN dept_emp c ON c.emp_no = a.emp_no
	JOIN (
		SELECT c.dept_no, AVG(b.salary) AS avg_salary FROM employees a
			JOIN salaries b ON b.emp_no = a.emp_no
			JOIN dept_emp c ON c.emp_no = a.emp_no
		WHERE b.to_date > current_date()
			AND c.to_date > current_date()
		GROUP BY c.dept_no
	) t1 ON c.dept_no = t1.dept_no
WHERE b.to_date > current_date()
	AND b.salary > t1.avg_salary
ORDER BY b.salary DESC;

        
-- 문제4.
-- 현재, 사원들의 사번, 이름, 그리고 매니저 이름과 부서 이름을 출력해 보세요.
SELECT a.emp_no, a.last_name as 'employee_name', 
d.last_name as 'manager_name', e.dept_name FROM employees a
	JOIN salaries b ON b.emp_no = a.emp_no
	JOIN dept_manager c ON c.emp_no = a.emp_no
	JOIN employees d ON d.emp_no = c.emp_no
	JOIN departments e ON e.dept_no = c.dept_no
WHERE b.to_date > current_date()
	AND c.to_date > current_date()
ORDER BY b.salary DESC;

-- 문제5.
-- 현재, 평균급여가 가장 높은 부서의 사원들의 사번, 이름, 직책 그리고 급여를 조회하고 급여 순으로 출력하세요.
SELECT a.emp_no, a.last_name, d.title, b.salary FROM employees a
	JOIN salaries b ON b.emp_no = a.emp_no
	JOIN dept_emp c ON c.emp_no = a.emp_no
	JOIN titles d ON d.emp_no = a.emp_no
WHERE b.to_date > current_date()
	AND c.to_date > current_date()
	AND c.dept_no = (
		SELECT c.dept_no FROM employees a
			JOIN salaries b ON b.emp_no = a.emp_no
			JOIN dept_emp c ON c.emp_no = a.emp_no
		WHERE b.to_date > current_date()
			AND c.to_date > current_date()
		GROUP BY c.dept_no
		ORDER BY AVG(b.salary) DESC
		LIMIT 1
	)
ORDER BY b.salary DESC;

-- 문제6.
-- 현재, 평균 급여가 가장 높은 부서의 이름 그리고 평균급여를 출력하세요.
SELECT d.dept_name, AVG(b.salary) FROM employees a
	JOIN salaries b ON b.emp_no = a.emp_no
	JOIN dept_emp c ON c.emp_no = a.emp_no
	JOIN departments d ON d.dept_no = c.dept_no
WHERE b.to_date > current_date()
	AND c.to_date > current_date()
GROUP BY d.dept_name
ORDER BY AVG(b.salary) DESC
LIMIT 1;

-- 문제7.
-- 현재, 평균 급여가 가장 높은 직책의 타이틀 그리고 평균급여를 출력하세요.
SELECT e.title, AVG(b.salary) FROM employees a
	JOIN titles e ON a.emp_no = e.emp_no
	JOIN salaries b ON b.emp_no = a.emp_no
WHERE b.to_date > current_date()
    AND e.to_date > current_date()
GROUP BY e.title
ORDER BY AVG(b.salary) DESC
LIMIT 1;
