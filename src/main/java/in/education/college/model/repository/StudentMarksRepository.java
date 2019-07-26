package in.education.college.model.repository;

import in.education.college.model.StudentMarks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface StudentMarksRepository extends CrudRepository<StudentMarks, Long> {

	List<StudentMarks> findAllByBatchIdAndBranchIdAndYearIdAndSemesterIdAndSubjectIdAndExamTypeId(
			Long batchId, String branchId, Long yearId, Long semesterId, Long subjectId,
			Long examTypeId );

	@Query(value = "SELECT round(avg(no_of_days),2) as no_of_days, round(avg(days_present),2) as days_present " +
			" FROM student_marks s " +
			" WHERE s.batch_id =?1 " +
			" and s.branch_id=?2 " +
			" and s.year_id=?3 " +
			" and s.semester_id=?4 ", nativeQuery = true)
	List<Map<String, String>> averageByBatchIdAndBranchIdAndYearIdAndSemesterId(
			@Param("batch_id") long batchId,
			@Param("branch_id") String branchId,
			@Param("year_id") long yearId,
			@Param("semester_id") long semesterId);

	/*@Query(value = "select *, round( ((internal1 + internal2) / 2) ,1) as internal, " +
			" (internal1_max + external_max) as total_max," +
			" (round( ((internal1 + internal2) / 2) ,1) + external) as subject_total" +
			" from (" +
			" 	select subject_id, subject_name," +
			"	(select max_marks from exam_type where exam_type_id = 1) as internal1_max," +
			" 	sum(internal1) as internal1," +
			"   (select max_marks from exam_type where exam_type_id = 2) as internal2_max," +
			" 	sum(internal2) as internal2," +
			"   (select max_marks from exam_type where exam_type_id = 3) as external_max," +
			" 	sum(external) as external," +
			"   (select max_marks from exam_type where exam_type_id = 4) as lab_max," +
			" 	sum(lab) as lab" +
			" 	from" +
			"   (" +
			"   	select" +
			"    	s.subject_id, s.subject_name," +
			"    	(select exam_type from exam_type where exam_type_id = sm.exam_type_id )," +
			"    	case when exam_type_id = 1 then marks else 0 end as internal1," +
			"    	case when exam_type_id = 2 then marks else 0 end as internal2, " +
			"    	case when exam_type_id = 3 then marks else 0 end as external," +
			"    	case when exam_type_id = 4 then marks else 0 end as lab" +
			"    	from subjects s" +
			"    	left join student_marks sm on (sm.subject_id = s.subject_id and sm.year_id= ?1 and sm.student_id = ?2)" +
			"    	where s.semester_id = ?3 and s.branch_id = (select branch_id from student_details where student_id = ?2)" +
			" 	)t" +
			" 	group by subject_id, subject_name " +
			" 	order by subject_id, subject_name " +
			" ) p", nativeQuery = true)*/

	@Query(value = "select subject_id, subject_name, internal1_max, internal1, internal2_max, internal2, internal, " +
//				" case when (select is_lab from subjects s where subject_id = k.subject_id) = true then external_lab_max else external_descriptive_max end as external_max, " +
//				" case when (select is_lab from subjects s where subject_id = k.subject_id) = true then external_lab_max else external_descriptive_max end as external_max, " +
//				" case when (select is_lab from subjects s where subject_id = k.subject_id) = true then external_lab_max else external_descriptive_max end as external_max, " +
//				" case when (select is_lab from subjects s where subject_id = k.subject_id) = true then external_lab_max else external_descriptive_max end as external_max, " +
				" case when (select case when group_concat(et.exam_type_id) = '1,2,4' then true else false end as is_lab from exam_type et join subject_exam_types es on (es. exam_type_id = et.exam_type_id) where es.subject_id = k.subject_id) = true then external_lab_max else external_descriptive_max end as external_max, " +
				" case when (select case when group_concat(et.exam_type_id) = '1,2,4' then true else false end as is_lab from exam_type et join subject_exam_types es on (es. exam_type_id = et.exam_type_id) where es.subject_id = k.subject_id) = true then external_lab else external_descriptive end as external," +
				" case when (select case when group_concat(et.exam_type_id) = '1,2,4' then true else false end as is_lab from exam_type et join subject_exam_types es on (es. exam_type_id = et.exam_type_id) where es.subject_id = k.subject_id) = true then total_lab_max else total_descriptive_max end as subject_total_max, " +
				" case when (select case when group_concat(et.exam_type_id) = '1,2,4' then true else false end as is_lab from exam_type et join subject_exam_types es on (es. exam_type_id = et.exam_type_id) where es.subject_id = k.subject_id) = true then total_lab else total_descriptive end as subject_total" +
				" from" +
				" (" +
				"	select p.subject_id, subject_name," +
				" 	internal1_max, coalesce(internal1,0) as internal1, internal2_max, coalesce(internal2, 0) as internal2," +
				" 	round( ((coalesce(internal1,0) + coalesce(internal2,0)) / 2) ,1) as internal," +
				" 	external_max as external_descriptive_max, coalesce(external,0) as external_descriptive, lab_max as external_lab_max, coalesce(lab,0) as external_lab," +
				" 	(round( ((internal1_max + internal2_max) / 2) ,0) + lab_max) as total_lab_max, ( round( ((internal1_max + internal2_max) / 2) ,0) + external_max ) as total_descriptive_max," +
				" 	coalesce(( round( ((internal1 + internal2) / 2) ,0) + lab),0) as total_lab, coalesce(( round( ((internal1 + internal2) / 2) ,0) + external ),0) as total_descriptive" +
				" 	from " +
				"	(" +
				"		select s.subject_id, s.subject_name," +
				" 		(select max_marks from exam_type where exam_type_id = 1) as internal1_max, sum(internal1) as internal1," +
				" 		(select max_marks from exam_type where exam_type_id = 2) as internal2_max,  sum(internal2) as internal2, " +
				" 		(select max_marks from exam_type where exam_type_id = 3) as external_max,  sum(external) as external, " +
				" 		(select max_marks from exam_type where exam_type_id = 4) as lab_max, sum(lab) as lab" +
				" 		from" +
				" 		(" +
				" 			select subject_id, exam_type_id," +
				" 			case when sm.exam_type_id = 1 then marks else 0 end as internal1," +
				" 			case when sm.exam_type_id = 2 then marks else 0 end as internal2, " +
				" 			case when sm.exam_type_id = 3 then marks else 0 end as external," +
				" 			case when sm.exam_type_id = 4 then marks else 0 end as lab" +
				" 			from" +
				" 			student_marks sm" +
				" 			where student_id = ?2" +
				" 		) t" +
				" 		right join subjects s on (t.subject_id = s.subject_id )" +
				" 		where s.semester_id = ?3 and s.branch_id = ?1" +
				" 		group by s.subject_name" +
				" 	)p" +
				" )k" +
				" order by subject_id", nativeQuery = true)

	List<Map<String, String>> studentProgressReportByBranchIdSemesterIdStudentId(
			@Param("branch_id") String branchId,
			@Param("student_id") long studentId,
			@Param("semester_id") long semesterId);

	@Query(value = "SELECT batch_id, branch_id, year_id, semester_id, round(avg(no_of_days),2) as no_of_days, round(avg(days_present),2) as days_present " +
			" FROM student_marks s " +
			" group by batch_id, branch_id, year_id, semester_id ", nativeQuery = true)
	List<Map<String, String>> allAverageByBatchIdAndBranchIdAndYearIdAndSemesterId();


	/*@Query(value = "select coalesce(case when count(su.subject_id) > 0 then 0 else sd.student_id end,0) as student_id" +
			" from subjects su" +
			" left join student_details sd on (sd.current_semester_id = su.semester_id and sd.branch_id = su.branch_id)" +
			" left join student_marks sm on (sm.student_id = sd.student_id and sm.semester_id = sd.current_semester_id " +
			" and sm.year_id = sd.current_year_id and sm.branch_id = su.branch_id and sm.subject_id = su.subject_id)" +
			" where marks is null and sd.student_id = ?1", nativeQuery = true)
	int findStudentByCurrentSemesterIdAndCurrentYearId(@Param("studentId") Long studentId);*/

	@Query(value = "select case when sum(subj) = sum(subm) then subj else 0 end as student_id" +
			" from (" +
			" select count(su.subject_id) as subj, 0 as subm" +
			" from subjects su" +
			" left join student_details sd on (sd.current_semester_id = su.semester_id and sd.branch_id = su.branch_id)" +
			" where sd.student_id = ?1" +
			" union all" +
			" select 0 as subj, count(sm.subject_id) as subm" +
			" from subjects su" +
			" left join student_details sd on (sd.current_semester_id = su.semester_id and sd.branch_id = su.branch_id)" +
			" left join student_marks sm on (sm.student_id = sd.student_id and sm.semester_id = sd.current_semester_id " +
			" and sm.year_id = sd.current_year_id and sm.branch_id = su.branch_id and sm.subject_id = su.subject_id)" +
			" where (exam_type_id = 3 or exam_type_id = 4) and marks is not null and" +
			" sd.student_id = ?1" +
			" )t", nativeQuery = true)
	int findStudentByCurrentSemesterIdAndCurrentYearId(@Param("studentId") Long studentId);


	@Query(value = "select " +
			" case when count(exam_type_id) = 0 then 1" +
			" when count(exam_type_id) = 1 then 2" +
			" when (count(exam_type_id) = 2 and sum(is_lab) = 0) then 3" +
			" when (count(exam_type_id) = 3 and sum(is_lab) = 0) then 0" +
			" when (count(exam_type_id) = 2 and sum(is_lab) = 2) then 4" +
			" when (count(exam_type_id) = 3 and sum(is_lab) = 3) then '-'" +
			" else 0 end as exam_type" +
			" from student_marks sm" +
			" left join subjects su on (su.subject_id = sm.subject_id and su.semester_id = sm.semester_id)" +
			" where student_id = ?1" +
			" and sm.semester_id = ?2 " +
			" and sm.year_id = ?3 " +
			" and sm.branch_id = ?4" +
			" and sm.subject_id = ?5", nativeQuery = true)
	int findByExamTypeId(@Param("studentId") Long studentId,
			@Param("semesterId") Long semesterId,
			@Param("yearId") Long yearId,
			@Param("branchId") String branchId,
			@Param("subjectId") Long subjectId);



	/*@Modifying
	@Query("insert into Person (id,name,age) select :id,:name,:age from Dual")
	public int modifyingQueryInsertPerson(@Param("id")Long id, @Param("name")String name, @Param("age")Integer age);*/


	/*@Query("update Customer c set c.name = :name WHERE c.id = :customerId")
	void setCustomerName(@Param("customerId") Long id);*/


}
