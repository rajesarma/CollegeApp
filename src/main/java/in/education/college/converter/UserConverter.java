package in.education.college.converter;

import in.education.college.dto.UserDto;
import in.education.college.model.User;

public class UserConverter {

	public static User convert(final UserDto userDto) {

		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());		
		user.setDisabled(userDto.getDisabled() != null ? userDto.getDisabled() : true);
		user.setUserDesc(userDto.getUserDesc());
		user.setEmail(userDto.getEmail());		
//		user.setLastLogin(userDto.getLastLogin() != null ? userDto.getLastLogin() :
//				null );
//		user.setFailedLoginAttempts(userDto.getFailedLoginAttempts()!=null ?
//				userDto.getFailedLoginAttempts() : null );
		user.setRoles(userDto.getRoles());		
//		user.setLastPasswordChange(userDto.getLastPasswordChange() != null ?
//				userDto.getLastPasswordChange() : null);
		
		return user;
	}

	public static UserDto convert(final User user) {

		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUsername(user.getUsername());
		userDto.setPassword(user.getPassword());
		userDto.setDisabled(user.getDisabled());
		userDto.setActiveStatus(user.getDisabled() ? "Disabled" : "Active" );
		userDto.setUserDesc(user.getUserDesc());
		userDto.setEmail(user.getEmail());
		userDto.setLastLogin(user.getLastLogin());
		userDto.setFailedLoginAttempts(user.getFailedLoginAttempts());
		userDto.setRoles(user.getRoles());
		userDto.setLastPasswordChange(user.getLastPasswordChange());

		return userDto;
	}
}
