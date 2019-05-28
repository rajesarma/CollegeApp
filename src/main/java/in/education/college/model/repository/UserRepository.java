package in.education.college.model.repository;

import in.education.college.model.Role;
import in.education.college.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);

	List<User> findAllByRoles(List<Role> role);

	@Override
	void deleteById(Long userId);


}
