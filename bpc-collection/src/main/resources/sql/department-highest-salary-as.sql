


-- 每个部门的最高收入者
SELECT
  d.Name      Department,
  e.Name      Employee,
  max(Salary) Salary
FROM Employee e LEFT JOIN Department d ON e.DepartmentId = d.Id
GROUP BY e.DepartmentId;


--

SELECT
  d.Name Department,
  e.Name Employee,
  e.Salary Salary
FROM Employee e
  JOIN (SELECT
          DepartmentId,
          max(Salary) Salary
        FROM Employee
        GROUP BY DepartmentId) t1 ON e.DepartmentId = t1.DepartmentId AND e.Salary = t1.Salary
  JOIN Department d ON e.DepartmentId = d.Id ORDER BY e.Salary;