package in.education.college.model.repository;

import in.education.college.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	Optional<Employee> findByEmpNo(String empNo);
	Optional<Employee> findFirstByEmail(String email);
	Optional<Employee> findFirstByAadharNo(String aadharNo);
	Optional<Employee> findFirstByMobile(String mobile);

	List<Employee> findByNameContains(String name);
	List<Employee> findByEmpNoAndNameContains(String rollNo, String name);

	List<Employee> findByDeptId(long deptId);
	List<Employee> findByJoiningAcademicYearId(long academicYearId);
	List<Employee> findByJoiningSemesterId(long joiningSemesterId);
	List<Employee> findByDeptIdAndJoiningAcademicYearId(long deptId, long academicYearId);
	List<Employee> findByDeptIdAndJoiningSemesterId(long deptId, long semesterId);
	List<Employee> findByJoiningAcademicYearIdAndJoiningSemesterId(long academicYearId,
			long semesterId);

	List<Employee> findByDeptIdAndJoiningAcademicYearIdAndJoiningSemesterId(long deptId
			, long academicYearId, long semesterId);


	@Query(value = "SELECT e FROM Employee e WHERE e.salary < ?1 and e.employeeId IN ?2")
	List<Employee> findAllBySalaryLessThanAndEmployeeIdIsIn(
			@Param("salary") Double salary,
			@Param("employeeIds") Collection<Long> empIds);

	List<Employee> findAllBySalaryLessThanEqualAndEmployeeIdIsIn(Double salary,Collection<Long> empIds);

	List<Employee> findAllBySalaryEqualsAndEmployeeIdIsIn(Double salary,Collection<Long> empIds);
	List<Employee> findAllBySalaryGreaterThanEqualAndEmployeeIdIsIn(Double salary,
			Collection<Long> empIds);

	List<Employee> findAllBySalaryGreaterThanAndEmployeeIdIsIn(Double salary,
			Collection<Long> empIds);

	@Query(value = "select q.qly_name, count(e.emp_id) as emp_count " +
			" from employee e " +
			" left join qualifications q on (e. qly_id = q.qly_id)" +
			" group by q.qly_name" +
			" order by qly_name", nativeQuery = true)
	List<Map<String, String>> findByEmpQlyWise();

}
