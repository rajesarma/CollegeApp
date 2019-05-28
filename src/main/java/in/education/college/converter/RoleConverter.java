package in.education.college.converter;

import in.education.college.dto.RoleDto;
import in.education.college.model.Role;

public class RoleConverter {

	public static Role dtoToEntity(final RoleDto roleDto) {

		Role role = new Role();
		role.setRoleId(roleDto.getRoleId());
		role.setRoleName(roleDto.getRoleName());
		role.setServices(roleDto.getServices());
		return role;
	}

	public static RoleDto entityToDto(final Role role) {

		RoleDto roleDto = new RoleDto();
		roleDto.setRoleId(role.getRoleId());
		roleDto.setRoleName(role.getRoleName());
		roleDto.setServices(role.getServices());

		return roleDto;
	}
}
