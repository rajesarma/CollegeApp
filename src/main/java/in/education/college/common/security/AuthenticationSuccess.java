package in.education.college.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.education.college.common.util.Constants.Roles;
import in.education.college.model.Role;
import in.education.college.model.Service;
import in.education.college.model.User;
import in.education.college.model.repository.RoleRepository;
import in.education.college.user.UserService;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthenticationSuccess extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccess.class);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession(true);

		ObjectMapper oMapper = new ObjectMapper();

		// Get the Principal User object
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User) authentication.getPrincipal();

		log.info("User logged in: " + user.getUsername() + " - " + request.getRemoteHost());

		// Update the LastLogin of User
		userService.registerSuccessfulLogin(user.getUserId());
		session.setAttribute("username", user.getUsername());

		String[] roleNames = user.getRoles()
								.stream()
								.map(Role::getRoleName)
								.collect(Collectors.toList())
								.stream()
								.toArray(String[] :: new);

		List<Service> services = new ArrayList<>();

		roleRepository.findByRoleNameIn(roleNames)
						.forEach(role -> services.addAll(role.getServices()));

		services.sort(Comparator.comparing(Service :: getParentId)
								.thenComparing(Service :: getServiceId));

		List serviceUrls = services.stream()
				.filter(service -> service.getMenuDisplay() == 1 )
				.collect(Collectors.toList())
				.stream()
				.map(service -> oMapper.convertValue(service, Map.class))
				.collect(Collectors.toList());

		session.setAttribute("servicesMenu", new JSONArray(serviceUrls));

//		response.sendRedirect("/home");

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		authorities.forEach(authority -> {
			if(getRole(Roles.MANAGEMENT_ROLE).equals(authority.getAuthority())) {
				try {
					redirectStrategy.sendRedirect(request, response, "/management/managementDashboard");

				} catch (Exception e) {
				}
			} else if(!Roles.MANAGEMENT_ROLE.equals(authority.getAuthority())) {
				try {
//					response.sendRedirect("/home");
					redirectStrategy.sendRedirect(request, response, "/home");
				} catch (Exception e) { }
			} else {
				throw new IllegalStateException();
			}
		});

		// To get Role Names
		/*Set<String> roles = authentication.getAuthorities().stream()
				.map(r -> r.getAuthority())
				.collect(Collectors.toSet());*/

//		 serviceRepository.findAll().forEach(System.out::print);

		/*List servicesUrls =
				StreamSupport.stream(serviceRepository.findAll().spliterator(), false)
						.filter(service -> service.getMenuDisplay() == 0 )
						.map(service -> service.getServiceUrl())
						.collect(Collectors.toList());*/

//		Iterable<Service> services = serviceRepository.findAll();

//		user.getRoles().stream().forEach(role -> System.out.print(role.getRoleName()));

		/// Services
		/*Iterable<Service> servicesActual =
				serviceRepository.findByServiceName(user.getUsername());*/

		/*List<Service> services1 = new ArrayList<>();
		roleRepository.findByRoleNameIn(roleNames)
				.stream()
				.map(role -> role.getServices())
				.forEach(service -> Collectors.toCollection(() -> services1));
		System.out.println(services1);
		*/

		// Working Code //

		/*
		HashMap<String, String> serviceMap;
		List serviceUrls = services.stream()
						.filter(service -> service.getMenuDisplay() == 1 )
						.collect(Collectors.toList())
						.stream()
						.map(service -> oMapper.convertValue(service, Map.class))
						.collect(Collectors.toList());

		List<HashMap<String, String>> servicesMenuList = new ArrayList<>();

		for(Object service: serviceUrls) {

			HashMap<String, String> sMap = (HashMap) service;

			System.out.println(sMap);

			serviceMap = new HashMap<>();
			serviceMap.put("serviceId", sMap.get("serviceId"));
			serviceMap.put("serviceUrl", sMap.get("serviceUrl"));
			serviceMap.put("serviceName", sMap.get("serviceName"));
			serviceMap.put("parentId", sMap.get("parentId"));
			serviceMap.put("displayOrder", sMap.get("displayOrder"));
			serviceMap.put("menuDisplay", sMap.get("menuDisplay"));

			servicesMenuList.add(serviceMap);
		}*/
		// Working Code //




//		super.onAuthenticationSuccess(request, response, authentication);
	}

	private String getRole(String role){
		if(role.length() == 0){
			throw new IllegalArgumentException("Role should be passed");
		} else {
			return "ROLE_" + role;
		}
	}
}



/*StreamSupport.stream(roleRepository.findByRoleName("admin").spliterator(), false)
				.forEach(role -> services1.addAll(role.getServices()));*/