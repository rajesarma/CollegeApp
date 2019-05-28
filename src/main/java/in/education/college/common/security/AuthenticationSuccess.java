package in.education.college.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.education.college.model.Role;
import in.education.college.model.Service;
import in.education.college.model.User;
import in.education.college.model.repository.RoleRepository;
import in.education.college.user.UserService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthenticationSuccess extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		HashMap<String, String> serviceMap;
		ObjectMapper oMapper = new ObjectMapper();

		// Get the Principal User object
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User) authentication.getPrincipal();

		// Update the LastLogin of User
		userService.registerSuccessfulLogin(user.getUserId());

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

		String[] roleNames = user.getRoles()
								.stream()
								.map(Role::getRoleName)
								.collect(Collectors.toList())
								.stream()
								.toArray(String[] :: new);

		List<Service> services = new ArrayList<>();

		roleRepository.findByRoleNameIn(roleNames)
						.forEach(role -> services.addAll(role.getServices()));

		List<Service> services1 = new ArrayList<>();

		/*roleRepository.findByRoleNameIn(roleNames)
				.stream()
				.map(role -> role.getServices())
				.forEach(service -> Collectors.toCollection(() -> services1));

		System.out.println(services1);*/

		services.sort(Comparator.comparing(Service :: getParentId)
								.thenComparing(Service :: getServiceId));

		List serviceUrls = services.stream()
						.filter(service -> service.getMenuDisplay() == 1 )
						.collect(Collectors.toList())
						.stream()
						.map(service -> oMapper.convertValue(service, Map.class))
						.collect(Collectors.toList());

		List<HashMap<String, String>> servicesMenuList = new ArrayList<>();

		for(Object service: serviceUrls) {

			HashMap<String, String> sMap = (HashMap) service;

			serviceMap = new HashMap<>();
			serviceMap.put("service_id", sMap.get("serviceId"));
			serviceMap.put("service_url", sMap.get("serviceUrl"));
			serviceMap.put("service_name", sMap.get("serviceName"));
			serviceMap.put("parent_id", sMap.get("parentId"));
			serviceMap.put("display_order", sMap.get("displayOrder"));
			serviceMap.put("menu_display", sMap.get("menuDisplay"));

			servicesMenuList.add(serviceMap);
		}
		session.setAttribute("servicesMenu", new JSONArray(servicesMenuList));

		response.sendRedirect("/home");
//		super.onAuthenticationSuccess(request, response, authentication);
	}
}


/*StreamSupport.stream(roleRepository.findByRoleName("admin").spliterator(), false)
				.forEach(role -> services1.addAll(role.getServices()));*/