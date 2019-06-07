package in.education.college.student;

import in.education.college.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
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


}
