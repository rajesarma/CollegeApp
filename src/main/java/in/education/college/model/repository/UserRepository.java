package in.education.college.model.repository;

import in.education.college.model.Role;
import in.education.college.model.User;
import org.springframework.data.jpa.repository.Query;
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

	@Query(value = "select id, user_name, password, disabled, user_desc, email, last_login, failed_login_attempts, last_password_change" +
			" from user_roles ur" +
			" join users u on (u.id = ur.user_id)" +
			" where role_id = ?1",nativeQuery = true)
	List<User> findAllByRoleId(long roleId);


}
