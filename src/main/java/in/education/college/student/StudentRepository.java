package in.education.college.student;

import in.education.college.model.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OrderColumn;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {

	List<Student> findByBatchIdAndBranchIdAndCurrentYearIdAndCurrentSemesterId(long batchId,
			String branchId, long yearId, long semesterId);

	@Query(value = "SELECT s FROM Student s " +
			" WHERE s.batchId =?1 " +
			" and s.branchId=?2 " +
			" and s.currentYearId=?3 " +
			" and s.currentSemesterId=?4 " +
			" and s.studentId NOT IN ?5")
	/*@Query(value = "select * from student_details where s.student_id not in (?1)",
			nativeQuery = true)*/
	List<Student> findAllByBatchIdAndBranchIdAndCurrentYearIdAndCurrentSemesterIdAndStudentIdIsNotIn(
			@Param("batchId") Long batchId,
			@Param("branchId") String branchId,
			@Param("yearId") Long yearId,
			@Param("semesterId") Long semesterId,
			@Param("studentIds") Collection<Long> studentIds);

	Optional<Student> findByRollNo(String rollNo);
	Optional<Student> findFirstByEmail(String email);
	Optional<Student> findFirstByAadharNo(String aadharNo);
	Optional<Student> findFirstByMobileNo(String mobile);

	List<Student> findByNameContains(String name);
	List<Student> findByRollNoAndNameContains(String rollNo, String name);

	List<Student> findByBranchId(String branchId);
	List<Student> findByBatchId(long academicYearId);
	List<Student> findByJoiningYearId(long joiningYearId);
	List<Student> findByBranchIdAndBatchId(String branchId, long batchId);
	List<Student> findByBranchIdAndJoiningYearId(String branchId, long joiningYearId);
	List<Student> findByBatchIdAndJoiningYearId(long batchId, long joiningYearId);
	List<Student> findByBranchIdAndBatchIdAndJoiningYearId(String branchId,
			long batchId, long joiningYearId);

	List<Student> findByBatchIdAndBranchIdAndJoiningYearIdAndJoiningSemesterId(
			long batchId, String branchId, long joiningYearId, long semesterId);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE student_details s" +
			" SET current_year_id = (SELECT year_id FROM semester WHERE semester_id = (s.current_semester_id + 1))," +
			" current_semester_id = (SELECT semester_id FROM semester WHERE semester_id = (s.current_semester_id + 1)),"+
			" promoted_date = current_timestamp()" +
			" WHERE s.student_id IN :studentIds", nativeQuery = true)
	@Transactional
	int updateCurrentYearIdAndCurrentSemesterId(@Param("studentIds") List<Long> studentIds);

	@Query(value = "select bt.batch_name as batch, count(sd.student_id) as student_count" +
			" from batch bt " +
			" left join student_details sd on (sd.batch_id = bt.batch_id)" +
			" group by bt.batch_name" +
			" order by bt.batch_name", nativeQuery = true)
	List<Map<String, String>> findByBatchWise();

	@Query(value = "select b.branch_name, count(sd.student_id) as student_count" +
			" from branch b" +
			" left join student_details sd on (sd.branch_id = b.branch_id)" +
			" group by b.branch_name" +
			" order by b.branch_name", nativeQuery = true)
	List<Map<String, String>> findByBranchWise();

	@Query(value = "select y.year_name as year, count(sd.student_id) as student_count" +
			" from year y " +
			" left join student_details sd on (sd.current_year_id = y.year_id)" +
			" group by y.year_name" +
			" order by y.year_name", nativeQuery = true)
	List<Map<String, String>> findByYearWise();

	/*@Query(value = "select y.year_name as year, coalesce(s.semester_name,'-') as semester, count(sd.student_id) as student_count" +
			" from year y " +
			" left join student_details sd on (sd.current_year_id = y.year_id)" +
			" left join semester s on (s.semester_id = sd.current_semester_id)" +
			" group by y.year_name, s.semester_name" +
			" order by y.year_name, s.semester_name", nativeQuery = true)
	List<Map<String, String>> findByYearSemWise();*/

	@Query(value = "select y.year_name as year, coalesce(s.semester_name,'-') as semester, s.semester_id, coalesce(sd.student_id, 0) as student_count " +
	" from semester s " +
	" left join student_details sd on (s.semester_id = sd.current_semester_id) " +
	" left join year y on (s.year_id = y.year_id) " +
	" group by s.semester_name, y.year_name " +
	" order by y.year_name, s.semester_name ", nativeQuery = true)
	List<Map<String, String>> findByYearSemWise();


	@Query(value = "select bt.batch_name as batch, coalesce(b.branch_name, '-') as branch, count(sd.student_id) as student_count" +
			" from batch bt " +
			" left join student_details sd on (sd.batch_id = bt.batch_id)" +
			" left join branch b on (sd.branch_id = b.branch_id)" +
			" group by bt.batch_name, b.branch_name" +
			" order by bt.batch_id, b.branch_name", nativeQuery = true)
	List<Map<String, String>> findByBatchBranchWise();

	@Query(value = "select bt.batch_name as batch, coalesce(b.branch_name, '-') as branch, " +
			" coalesce(y.year_name,'-') as year, count(sd.student_id) as student_count" +
			" from batch bt " +
			" left join student_details sd on (sd.batch_id = bt.batch_id)" +
			" left join year y on (y.year_id = sd.current_year_id)" +
			" left join branch b on (sd.branch_id = b.branch_id)" +
			" group by bt.batch_name, b.branch_name, y.year_name" +
			" order by bt.batch_id, b.branch_name, y.year_name", nativeQuery = true)
	List<Map<String, String>> findByBatchBranchYearWise();

	@Query(value = "select bt.batch_name as batch, coalesce(b.branch_name, '-') as branch, coalesce(y.year_name,'-') as year, " +
			" coalesce(s.semester_name,'-') as semester, count(sd.student_id) as student_count" +
			" from batch bt " +
			" left join student_details sd on (sd.batch_id = bt.batch_id)" +
			" left join branch b on (sd.branch_id = b.branch_id)" +
			" left join year y on (y.year_id = sd.current_year_id)" +
			" left join semester s on (s.semester_id = sd.current_semester_id)" +
			" group by bt.batch_name, b.branch_name, y.year_name, s.semester_name" +
			" order by bt.batch_id, b.branch_name, y.year_name, s.semester_name", nativeQuery = true)
	List<Map<String, String>> findByBatchBranchYearSemWise();

	@Query(value = "select sum(employees_count) as 1_total_employees, sum(students_total) as 2_total_students, " +
			" sum(male) as 3_male_students, sum(female) as 4_female_students" +
			" from (" +
			" select sum(student_id) as students_total, sum(male) as male, sum(female) as female, 0 as employees_count" +
			" from (" +
			" select " +
			" case when student_id is not null then 1 else 0 end as student_id," +
			" case when gender = 'MALE' then 1 else 0 end as male," +
			" case when gender = 'FEMALE' then 1 else 0 end as female" +
			" from student_details" +
			" ) t" +
			" union all " +
			" select 0 as students_total, 0 as male, 0 as female, count(*) as employees_count from employee" +
			" )p", nativeQuery = true)
	Map<String, Long> findByStudentsCount();

	/*@Query(value = "select sum(civil) as civil, sum(eee) as eee, sum(mech) as mech, sum(ece) as ece, sum(cse) as cse, sum(it) as it" +
	" from ( " +
	" select " +
	" case when branch_id = '01' then 1 else 0 end as civil, " +
	" case when branch_id = '02' then 1 else 0 end as eee, " +
	" case when branch_id = '03' then 1 else 0 end as mech, " +
	" case when branch_id = '04' then 1 else 0 end as ece, " +
	" case when branch_id = '05' then 1 else 0 end as cse, " +
	" case when branch_id = '12' then 1 else 0 end as it " +
	" from student_details) t",  nativeQuery = true)
	Map<String, Long> findByBranchWiseCount();*/

	@Query(value = "select b.branch_id, b.branch_name, count(sd.student_id) as count  " +
	" from " +
	" branch b " +
	" left join student_details sd on (sd.branch_id = b.branch_id) " +
	" group by b.branch_id, b.branch_name",  nativeQuery = true)
	List<Map<String, String>> findByBranchWiseCountData();


	@Query(value = "select student_id, student_name, DATE_FORMAT(doj, '%d %M, %Y') as doj from student_details " +
			" order by timestamp(doj) desc limit 5",  nativeQuery = true)	// DATE_FORMAT(doj, '%d %M %Y')
	List<Map<String, String>> findByRecentStudents();


	/*@Query(value = "select student_name, roll_no, father_name, batch_name, year_name as current_year, semester_name as current_semester" +
			" from student_details sd" +
			" left join batch b on (b.batch_id = sd.batch_id)" +
			" left join year y on (y.year_id = sd.current_year_id)" +
			" left join semester s on (s.semester_id = sd.current_semester_id)" +
			" where branch_id = ?1",  nativeQuery = true)
	List<Map<String, String>> findByBranchWiseStudents(@Param("branchId") String branchId);*/


	@Query(value = "select student_name, roll_no, father_name, " +
			" (select batch_name from batch b where b.batch_id = sd.batch_id) as batch_name, " +
			" (select year_name from year y where y.year_id = sd.current_year_id) as current_year," +
			" (select semester_name from semester s where s.semester_id = sd.current_semester_id) as " +
			" current_semester" +
			" from student_details sd" +
			" where branch_id =  ?1 ", nativeQuery = true)
	List<Map<String, String>> findByBranchWiseStudents(@Param("branchId") String branchId);

}
