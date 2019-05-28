package in.education.college.model.repository;

import in.education.college.model.Year;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface YearRepository extends CrudRepository<Year, Long> {

	Optional<Year> findByYearId(long yearId);

}
