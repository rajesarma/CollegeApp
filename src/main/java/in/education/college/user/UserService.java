package in.education.college.user;

import in.education.college.converter.UserConverter;
import in.education.college.dto.UserDto;
import in.education.college.model.Role;
import in.education.college.model.User;
import in.education.college.model.repository.RoleRepository;
import in.education.college.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;

	@Autowired
	UserService(final UserRepository userRepository,
			final RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	Map<Long,String> getRoles() {
		return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(Role::getRoleId, Role::getRoleName));
	}

	Optional<UserDto> save(UserDto userDto) {

		User user = UserConverter.convert(userDto);

		Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

		if(existingUser.isPresent()) {
			return Optional.empty();
		}

		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		User savedUser = userRepository.save(user);

		return Optional.of(UserConverter.convert(savedUser));
	}

	List<UserDto> findUsersByRoles(UserDto userDto) {

		List<User> users =
				userRepository.findAllByRoles(UserConverter.convert(userDto).getRoles());

		List<UserDto> userDtos = new ArrayList<>();

		users.stream().forEach(user -> userDtos.add(UserConverter.convert(user)));

		/*for (User user : users) {
			userDtos.add(UserConverter.convert(user));
		}*/
		return userDtos;
	}

	Optional<UserDto> findUsersById(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
			if(userOptional.isPresent()) {

				UserDto userDto = UserConverter.convert(userOptional.get());

				return Optional.of(userDto);
			}

		return Optional.empty();
	}

	Optional<UserDto> changePassword(String username, String password,
			String newPassword) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		Optional<User> userOptional =
				userRepository.findByUsername(username.toLowerCase(Locale.US));

		if(!userOptional.isPresent()) {
			return Optional.empty();
		} else {
			User user = userOptional.get();
			if(encoder.matches(password, user.getPassword())) {

				user.setPassword(encoder.encode(newPassword));
				user.setLastPasswordChange(new Date());
				User savedUser = userRepository.save(user);

				return Optional.of(UserConverter.convert(savedUser));
			} else {
				return Optional.empty();
			}
		}
	}

	Optional<UserDto> delete(UserDto userDto) {

		User user = UserConverter.convert(userDto);

		userRepository.deleteById(user.getUserId());
		Optional<User> userOptional = userRepository.findById(user.getUserId());

		if(userOptional.isPresent()) {
			return Optional.of(UserConverter.convert(userOptional.get()));
		} else {
			return Optional.empty();
		}
	}

	Optional<UserDto> update(UserDto userDto) {
		User user = UserConverter.convert(userDto);

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<User> userOptional = userRepository.findById(user.getUserId());

		if(!userOptional.isPresent()) {
			return Optional.empty();
		} else {
			User toUpdateUser = userOptional.get();

			toUpdateUser.setUsername(user.getUsername());
			toUpdateUser.setPassword(encoder.encode(user.getPassword()));
			toUpdateUser.setEmail(user.getEmail());
			toUpdateUser.setRoles(user.getRoles());
			toUpdateUser.setUserDesc(user.getUserDesc());

			User savedUser = userRepository.save(toUpdateUser);

			if(savedUser.getUserId() > 0 ) {
				return Optional.of(UserConverter.convert(savedUser));
			}

			return Optional.empty();
		}
	}

	public void registerSuccessfulLogin(Long userId){
		Optional<User> userOptional = userRepository.findById(userId);

		if(!userOptional.isPresent()){
			return;
		}
		User user = userOptional.get();
		user.setLastLogin(new Date());
		user.setFailedLoginAttempts(0L);
		userRepository.save(user);
	}

	public void registerFaliureLogin(String username){
		if(username  == null) {
			return;
		}

		Optional<User> userOptional = userRepository.findByUsername(username);

		if(!userOptional.isPresent()){
			return;
		}

		User user = userOptional.get();

		Long prevFailureLoginCount = user.getFailedLoginAttempts();
		long failureLoginCount = prevFailureLoginCount != null ?
				prevFailureLoginCount + 1 : 1L;

		user.setFailedLoginAttempts(failureLoginCount);
		userRepository.save(user);
	}

}
