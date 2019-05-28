package in.education.college.common.security;

import in.education.college.model.User;
import in.education.college.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

//	public static ArrayList<String> serviceUrls;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> userOptional = userRepository.findByUsername(username);

		if(userOptional.isPresent()){
			User user = userOptional.get();
			// Actual Password should be stored as BCrypt
//			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())) ;
//			CustomUserDetails customUserDetails = new CustomUserDetails(user);
//			serviceUrls = customUserDetails.getServiceUrls();

			return new CustomUserDetails(user);

		} else {
			throw new UsernameNotFoundException("user not found" + username);
		}
	}

	private static class CustomUserDetails extends User implements UserDetails{

		// Any login attempt after 20 consecutive failed login attempts will return 401
		private static final int MAX_FAILED_LOGIN_ATTEMPTS = 20;
		private static final long serialVersionUID = 6020417971232843532L;

		CustomUserDetails(User user) {
			super(user);
		}

//		private ArrayList<String> serviceUrls = new ArrayList<>();

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {

			String[] roles = {};

//			this.getRoles().stream().forEach( role -> System.out.println(role.getRoleName()));

			/*ArrayList<in.education.college.model.Service> services = new ArrayList<>();

			this.getRoles().stream().forEach( role -> {
				role.getServices().stream().forEach(service -> services.add(service) );
			});

			services.stream().forEach( service -> serviceUrls.add(service.getServiceUrl()));*/

			if(this.getRoles() != null){

				// Prepend ROLE_ to every role in DB
				roles = this.getRoles()
						.stream()
						.map(role -> "ROLE_" + role.getRoleName().toUpperCase()).toArray(String[]::new);
			}

//			this.getRoles().stream().forEach( role -> System.out.print(role.getRoleName()));

			/*return getRoles()
				.stream()
				.map(role-> new SimpleGrantedAuthority("ROLE_"+role.getRoleName()))
				.collect(Collectors.toList());*/

			return AuthorityUtils.createAuthorityList(roles);
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {

			Long prevFailedAttempts = this.getFailedLoginAttempts();
			return prevFailedAttempts == null || prevFailedAttempts <= MAX_FAILED_LOGIN_ATTEMPTS;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return this.getDisabled() == null || !this.getDisabled();
		}

//		ArrayList<String> getServiceUrls() {
//			return serviceUrls;
//		}

		/*void setServiceUrls(ArrayList<String> serviceUrls) {
			this.serviceUrls = serviceUrls;
		}*/
	}
}


//	JDBC Code
	/*@Autowired
	AuthenticationRepository authenticationRepository;*/

//	JDBC Code
		/*User user = null;
		try {
			user = authenticationRepository.authenticateUser(userName);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

		if (user != null) {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())) ;
			return new CustomUserDetails(user);
		} else {
			throw new UsernameNotFoundException("User not found.");
		}*/

/*userOptional.orElseThrow(() ->  new UsernameNotFoundException("user not " +
				"found" + userName));*/

/*Optional<User> optionalUser = usersRepository.findByFirstName(userName);
		return Optional.ofNullable(optionalUser).orElseThrow(()->new UsernameNotFoundException("Username Not Found"))
				.map(UserDetailsImpl::new).get();*/

//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		/*final org.springframework.security.core.userdetails.User.UserBuilder userBuilder
				= org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder::encode);*/

		/*org.springframework.security.core.userdetails.User.UserBuilder userBuilder =
				org.springframework.security.core.userdetails.User.withUserDetails(new CustomUserDetails(user));*/

		/*builder = org.springframework.security.core.userdetails.User.withUsername(userName);
			builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
			builder.roles(user.getRoles());

			System.out.println(user.getRoles());

			return builder.build();*/

//		Iterable<User> users = userRepository.findAll();
		/*Optional<User> userOptional =
				userRepository.findById(userName.toLowerCase(Locale.US));*/