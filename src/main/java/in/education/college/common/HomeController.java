package in.education.college.common;

import in.education.college.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	/*@RequestMapping("/")
	public String home( Map<String, Object> model) {

		return "index";
	}*/

	@RequestMapping(value="/")
	public ModelAndView start() {
		return new ModelAndView("index", "user",new User());
	}

	@RequestMapping(method= RequestMethod.GET, value="/test")
	public ModelAndView next() {

		return new ModelAndView("test");
	}

	@RequestMapping(value="/home")
	public ModelAndView home() {

		return new ModelAndView("home");
	}

	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "accessDenied";
	}

	@RequestMapping(value = "/sessionTimeout", method = RequestMethod.GET)
	public String sessionTimeout(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "sessionTimeout";
	}


	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}
