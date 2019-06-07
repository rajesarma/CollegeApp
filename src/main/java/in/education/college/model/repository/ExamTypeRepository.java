package in.education.college.model.repository;

import in.education.college.model.ExamType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ExamTypeRepository extends CrudRepository<ExamType, Long> {

	@Query(value = "select et.exam_type_id, et.exam_type, et.max_marks" +
			" from exam_type et" +
			" join subject_exam_types es on (es. exam_type_id = et.exam_type_id)" +
			" where es.subject_id = ?1" +
			" order by et.exam_type_id", nativeQuery = true)
	List<ExamType> findAllBySubjectId(@Param("subject_id") long studentId);

}
