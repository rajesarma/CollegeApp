package in.education.college.student;

import in.education.college.model.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

	@Query(value = "select y.year_name as year, coalesce(s.semester_name,'-') as semester, count(sd.student_id) as student_count" +
			" from year y " +
			" left join student_details sd on (sd.current_year_id = y.year_id)" +
			" left join semester s on (s.semester_id = sd.current_semester_id)" +
			" group by y.year_name, s.semester_name" +
			" order by y.year_name, s.semester_name", nativeQuery = true)
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


}
