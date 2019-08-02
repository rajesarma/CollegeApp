package in.education.college.model.repository;

import in.education.college.model.StudentAttendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface StudentAttendanceRepository extends CrudRepository<StudentAttendance,Long> {

	/*@Query(value = " select sd.batch_id, sa.branch_id, sa.academic_year_id, sa.semester_id, sd" +
			".student_id,  roll_no, student_name, father_name,no_of_days, days_present " +
			" from " +
			" (" +
			"   select sd.batch_id, sa.branch_id, sa.academic_year_id, sa.semester_id, sd.student_id, roll_no, student_name, " +
			"   coalesce(sa.no_of_days,0) as no_of_days, coalesce(sa.days_present,0) as days_present" +
			"   from student_attendance sa " +
			"   left join student_details sd on (sd.student_id = sa.student_id )" +
			"   where sa.batch_id = ?1" +
			"   and sa.branch_id = ?2" +
			"   and sa.academic_year_id = ?3" +
			"   and sa.semester_id = ?4" +
			" )list" +
			" union" +
			" (" +
			" select sd.batch_id, sd.branch_id, ?3 as academic_year_id, " +
			" ?4 as semester_id, sd.student_id, roll_no, student_name," +
			"  0 as no_of_days, 0 as days_present" +
			"  from student_details sd" +
			"  where student_id not in" +
			"  ( select student_id from student_attendance " +
			"     where batch_id = ?1" +
			"     and branch_id = ?2" +
			"     and academic_year_id = ?3" +
			"     and semester_id = ?4" +
			"  )" +
			"   and batch_id = ?1" +
			"   and sd.branch_id = ?2" +
			" )" +
			" order by roll_no, student_name", nativeQuery = true)*/
//	@Query(nativeQuery = true)
	List<StudentAttendance> findAllByBatchIdAndBranchIdAndYearIdAndSemesterId(
			@Param("batch_id") long batchId,
			@Param("branch_id") String branchId,
			@Param("year_id") long yearId,
			@Param("semester_id") long semesterId);

	@Query(value = "SELECT round(avg(no_of_days),2) as no_of_days, round(avg(days_present),2) as days_present " +
			" FROM student_attendance s " +
			" WHERE s.batch_id =?1 " +
			" and s.branch_id=?2 " +
			" and s.year_id=?3 " +
			" and s.semester_id=?4 ", nativeQuery = true)
	List<Map<String, String>> averageByBatchIdAndBranchIdAndYearIdAndSemesterId(
			@Param("batch_id") long batchId,
			@Param("branch_id") String branchId,
			@Param("year_id") long yearId,
			@Param("semester_id") long semesterId);

	@Query(value = "SELECT batch_id, branch_id, year_id, semester_id, round(avg(no_of_days),2) as no_of_days, round(avg(days_present),2) as days_present " +
			" FROM student_attendance s " +
			" group by batch_id, branch_id, year_id, semester_id ", nativeQuery = true)
	List<Map<String, String>> allAverageByBatchIdAndBranchIdAndYearIdAndSemesterId();


	@Query(value = "select case when count(sa.id) > 0 then sa.student_id else 0 end as student_id" +
			" from student_attendance sa" +
			" join student_details sd on (sa.student_id = sd.student_id and sa.semester_id = sd.current_semester_id " +
			" and sa.year_id = sd.current_year_id)" +
			" where sa.student_id = ?1", nativeQuery = true)
	int findStudentByCurrentSemesterIdAndCurrentYearId(@Param("studentId") Long studentId);

	@Query(value = "SELECT batch_name, coalesce(case when sum(days_present)=0 then 0 else round((sum(days_present)/sum(no_of_days))*100,2) " +
			" end, 0) as att_avg" +
			" FROM batch b" +
			" left join student_attendance sa on(sa.batch_id = b.batch_id)" +
			" group by batch_name", nativeQuery = true)
	List<Map<String, String>> findByBatchWiseStudentsAttendances();


	/*@Modifying
	@Query("insert into Person (id,name,age) select :id,:name,:age from Dual")
	public int modifyingQueryInsertPerson(@Param("id")Long id, @Param("name")String name, @Param("age")Integer age);*/



}
