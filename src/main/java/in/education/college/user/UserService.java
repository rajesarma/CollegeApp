package in.education.college.user;

import in.education.college.common.util.Constants.StrConstants;
import in.education.college.converter.UserConverter;
import in.education.college.dto.UserDto;
import in.education.college.model.Role;
import in.education.college.model.User;
import in.education.college.model.repository.RoleRepository;
import in.education.college.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	Map<Long,String> getRoles() {
		return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(Role::getRoleId, Role::getRoleName));
	}

	public Optional<UserDto> save(HttpServletRequest request, UserDto userDto) {

		User user = UserConverter.convert(userDto);

		Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

		if(existingUser.isPresent()) {
			log.info("<USER><USER:SAVE>"
					+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
					+ "<" + existingUser.get().getUsername() +" : already exists>");

			return Optional.empty();
		}

		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		User savedUser = userRepository.save(user);

		log.info("<USER><USER:SAVE>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<" + savedUser.getUsername() +" : saved>");

		return Optional.of(UserConverter.convert(savedUser));
	}

	List<UserDto> findUsersByRoles(UserDto userDto) {

		List<User> users =
				userRepository.findAllByRoles(UserConverter.convert(userDto).getRoles());

		List<UserDto> userDtos = new ArrayList<>();

		users.forEach(user -> userDtos.add(UserConverter.convert(user)));

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

	Optional<UserDto> changePassword(HttpServletRequest request, String username,
			String password, String newPassword) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		Optional<User> userOptional =
				userRepository.findByUsername(username.toLowerCase(Locale.US));

		if(!userOptional.isPresent()) {

			log.info("<USER><USER:CHANGE_PASSWORD>"
					+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
					+ "<" + username +" : not found>");

			return Optional.empty();
		} else {
			User user = userOptional.get();
			if(encoder.matches(password, user.getPassword())) {

				user.setPassword(encoder.encode(newPassword));
				user.setLastPasswordChange(new Date());
				User savedUser = userRepository.save(user);

				log.info("<USER><USER:CHANGE_PASSWORD>"
						+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
						+ "<" + userOptional.get().getUsername() +" : Password updated>");

				return Optional.of(UserConverter.convert(savedUser));
			} else {

				log.info("<USER><USER:CHANGE_PASSWORD>"
						+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
						+ "<" + userOptional.get().getUsername() +" : User credentials " +
						"does not match>");
				return Optional.empty();
			}
		}
	}

	Optional<UserDto> delete(HttpServletRequest request, UserDto userDto) {

		User user = UserConverter.convert(userDto);

		userRepository.deleteById(user.getUserId());
		Optional<User> userOptional = userRepository.findById(user.getUserId());

		if(userOptional.isPresent()) {
			log.info("<USER><USER:DELETE>"
					+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
					+ "<" + userOptional.get().getUsername() +" : not deleted>");
			return Optional.of(UserConverter.convert(userOptional.get()));
		}

		log.info("<USER><USER:DELETE>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<" + userDto.getUsername() +" : deleted>");

		return Optional.empty();
	}

	Optional<UserDto> update(HttpServletRequest request, UserDto userDto) {
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

			if(userRepository.existsById(savedUser.getUserId())) {
				log.info("<USER><USER:UPDATE>"
						+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
						+ "<" + savedUser.getUserId() +" : updated>");

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
