package in.education.college.role;


import in.education.college.model.Role;
import in.education.college.model.repository.RoleRepository;
import in.education.college.model.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoleServicesMappingService {

	private RoleRepository roleRepository;
	private ServiceRepository serviceRepository;

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Autowired
	public void setServiceRepository(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}

	Map<Long,String> getRoles() {
		return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(Role::getRoleId, Role::getRoleName));
	}

	Map<Long,String> getServices() {
		return StreamSupport.stream(serviceRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(in.education.college.model.Service::getServiceId,
						in.education.college.model.Service::getServiceName));
	}

	List<Long> getRoleMappedServices(Long roleId) {

		List<in.education.college.model.Service> services = new ArrayList<>();

		roleRepository.findByRoleId(roleId)
				.forEach(role -> services.addAll(role.getServices()));

		/*roleRepository.findByRoleId(roleId)
				.stream()
				.map(Service::getServiceId)
				.distinct()
				.collect(Collectors.toList());*/ // Modify This

		return services
				.stream()
				.map(in.education.college.model.Service::getServiceId)
				.distinct()
				.collect(Collectors.toList());
	}

	Optional<Role> updateServices(Role toUpdateRole) {

		Optional<Role> optionalRole = roleRepository.findById(toUpdateRole.getRoleId());

		if(optionalRole.isPresent()) {
			Role role = optionalRole.get();

			role.setServices(toUpdateRole.getServices());
			Role savedRole = roleRepository.save(role);

			if(savedRole.getRoleId() > 0 ) {
				return Optional.of(savedRole);
			}
		}
		return Optional.empty();
	}


}
