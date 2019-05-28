package in.education.college.role;

import in.education.college.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/super")
public class RoleServicesMappingController {

	private RoleServicesMappingService roleServicesMappingService;

	@Autowired
	public void setRoleServicesMappingService(RoleServicesMappingService roleServicesMappingService) {
		this.roleServicesMappingService = roleServicesMappingService;
	}

	private void loadDbData(final ModelAndView mav) {
		mav.addObject("roles", roleServicesMappingService.getRoles());
		mav.addObject("services", roleServicesMappingService.getServices());
	}

	@GetMapping("/roleServices")
	public ModelAndView roleServiceMapping() {

		Role role = new Role();
		role.setRoleId(1);
		ModelAndView mav = new ModelAndView("roleServices", "role", role);
		loadDbData(mav);

		mav.addObject("selectedServiceIds",
				roleServicesMappingService.getRoleMappedServices(role.getRoleId()));
		mav.addObject("roleId", role.getRoleId());

		Map<String, Map<String, String>> sites = new HashMap<>();
		Map<String, String> siteStatus = new HashMap<>();
		siteStatus.put("192.168.34.33","DOWN");
		siteStatus.put("192.168.34.43","UP");

		sites.put("Swamy",siteStatus);

		siteStatus = new HashMap<>();
		siteStatus.put("192.168.34.34","UP");
		siteStatus.put("192.168.34.44","UP");

		sites.put("Sreedhar",siteStatus);

		siteStatus = new HashMap<>();
		siteStatus.put("192.168.34.35","UP");
		siteStatus.put("192.168.34.45","UP");

		sites.put("Rajesh",siteStatus);

//		System.out.println(sites);

		mav.addObject("sites", sites);

		return mav;
	}

	@PostMapping("/roleServices/{roleId}") // Ajax Call
	public ResponseEntity<List> getMappedServices(@PathVariable("roleId") Long roleId) {

		List<Long> selectedServiceIds =
				roleServicesMappingService.getRoleMappedServices(roleId);
		return new ResponseEntity<>(selectedServiceIds, HttpStatus.OK);
	}

	@PostMapping("/roleServices")
	public ModelAndView mapRoleServices(@ModelAttribute("role") Role role) {

		ModelAndView mav = new ModelAndView("roleServices", "role", role);
		loadDbData(mav);

		Optional<Role> roleOptional = roleServicesMappingService.updateServices(role);

		if(roleOptional.isPresent()) {
			role = roleOptional.get();
			mav.addObject("message", "Role Services mapped successfully");

		} else {
			mav.addObject("message", "Role Services is not mapped");
		}

		mav.addObject("role", role);
		mav.addObject("roleId", role.getRoleId());
		mav.addObject("selectedServiceIds",
				roleServicesMappingService.getRoleMappedServices(role.getRoleId()));

		return mav;
	}
}
