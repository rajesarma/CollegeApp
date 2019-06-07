package in.education.college.model.repository;

import in.education.college.model.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

	List<Subject> findAllByBranchIdAndSemesterId(
			@Param("branchId") String branchId,
			@Param("semesterId") Long semesterId);
}
