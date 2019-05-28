package in.education.college.model.repository;

import in.education.college.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	List<Role> findByRoleId(long roleId);

	List<Role> findByRoleName(String roleName);

	List<Role> findByRoleNameIn(String... roles);

	List<Role> findAllByRoleIdIn(long[] roleIds);

//	List<Role> findByRoleNameInOrderByServices(String... roles);

}
