-- JOIN
use employees;

-- 예제: 이름이 'parto'인 직원의 평균 급여를 구하세요.
SELECT 
    emp_no
FROM
    employees
WHERE
    CONCAT(first_name, ' ', last_name) = 'parto hitomi';

-- inner join (equi join은 inner join의 하위 집합이다.)
-- 예제1) 현재 근무하고 있는 직원의 이름과 직책을 모두 출력하세요.
SELECT 
    *
FROM
    employees a,
    titles b
WHERE
    a.emp_no = b.emp_no
        AND b.to_date > CURRENT_DATE();

-- 예제2) 현재, 근무하고 있는 직원의 이름과 직책을 모두 출력하되, 여성 엔지니어(Engineer)만 출력하기 
-- 단, 아래 방법은 예전에 사용하던 방식으로, 현재는 inner join (where절 조건 작성 대신)을 사용하는 것을 권장한다.
SELECT 
    CONCAT(a.first_name, ' ', a.last_name) AS full_name, b.title
FROM
    employees a
        JOIN
    titles b
WHERE
    b.to_date > CURRENT_DATE();
        
        
-- ANSI / ISO SQL1999 JOIN 표준 문법

-- 1) natural join
-- 특징: 💡 공통 컬럼의 이름이 같아야 하며(중복 컬럼 제거), 조인 조건(ON 절)을 명시하지 않아도 자동으로 조인 조건이 적용된다.
SELECT 
    a.first_name, b.title
FROM
    employees a
        NATURAL JOIN
    titles b
WHERE
    a.emp_no = b.emp_no
        AND b.to_date > CURRENT_DATE();
-- natural join 현재까지도 자주 쓰는가?
-- A: 아니다! 명확한 조인 조건이 보이지 않아 코드 해석이 어렵기 때문이다. (+ 예기치 않은 join이 동일 컬럼명으로 인해 발생할 수 있다.) 
        
        
-- 2) join ~ using 
-- natural join의 문제점을 해결하고자 나왔다. 
-- 보완한 점: 명확성 부족, 예기치 않은 조인 조건 추가를 보완
-- 방법: 공통 컬럼을 USING (컬럼명1, 컬럼명2, ...)의 형태로 명시한다. 
select * from salaries;
select * from titles;
-- natural join vs join ~ using
-- natural join은 공통 컬럼을 기준으로 join하기 때문에 salaries의 emp_no 뿐만 아니라 to_date도 join하게 된다. 
-- -> 데이터 select을 기존 목적(emp_no만 교집합하기) 대비 더 적게 고르게 된다. 
SELECT 
    count(*)
FROM
    salaries a
        NATURAL JOIN
    titles b
WHERE
    a.to_date > CURRENT_DATE()
        AND b.to_date > CURRENT_DATE(); -- 결과: 4

-- join ~ using: emp_no를 명시함으로써 해당 컬럼으로만 join을 수행하게 된다.
SELECT 
    COUNT(*)
FROM
    salaries a
        JOIN
    titles b USING (emp_no)
WHERE
    a.to_date > CURRENT_DATE()
        AND b.to_date > CURRENT_DATE();	-- 결과: 240124


-- 3) join ~ on
SELECT 
    COUNT(*)
FROM
    salaries a
        JOIN
    titles b ON a.emp_no = b.emp_no
WHERE
    a.to_date > CURRENT_DATE()
        AND b.to_date > CURRENT_DATE();


-- 실습문제1. 현재, 직책별 평균 연봉을 큰 순서대로 출력하세요.
-- 최적화: join 전에 조건에 맞춰 데이터를 거른다. 
SELECT 
    b.title, AVG(a.salary) AS avg_salary
FROM
    (SELECT 
        *
    FROM
        salaries
    WHERE
        to_date > CURRENT_DATE()) a
        JOIN
    (SELECT 
        *
    FROM
        titles
    WHERE
        to_date > CURRENT_DATE()) b ON a.emp_no = b.emp_no
GROUP BY b.title
ORDER BY avg_salary DESC;
-- 주의!!: 여기서 order by 뒤에 작성되는 건 이미 계산된 데이터지 다시 실행되는게 아니다. 


-- 실습문제2. 현재, 직책별 평균 연봉과 직원수를 구하되 직원수가 100명 이상인 직책만 출력하기 
-- projection: 직책, 평균연봉, 직원수
SELECT 
    a.title, AVG(b.salary), COUNT(a.title) AS member_cnt
FROM
    titles a
        JOIN
    salaries b ON a.emp_no = b.emp_no
WHERE
    a.to_date > CURRENT_DATE()
        AND b.to_date > CURRENT_DATE()
GROUP BY a.title
HAVING COUNT(a.title) >= 100;


-- 실습문제3. 현재, 부서별로 직책이 Engineer인 직원들에 대한 평균연봉을 구하기 
select b.dept_name, avg(c.salary) as '부서별 평균연봉'
from dept_emp a 
join departments b on a.dept_no = b.dept_no 
join salaries c on a.emp_no = c.emp_no 
join titles d on c.emp_no = d.emp_no
where d.title = 'Engineer'
	and a.to_date > current_date() 
	and c.to_date > current_date() 
	and d.to_date > current_date()
group by b.dept_name
order by avg(c.salary) desc;
