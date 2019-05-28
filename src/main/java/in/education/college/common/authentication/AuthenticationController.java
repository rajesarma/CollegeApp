package in.education.college.common.authentication;

import in.education.college.common.exception.AuthenticationException;
import in.education.college.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//@Controller
public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;

	@PostMapping("/login")
	public ModelAndView authenticateUser(
			@RequestParam String username,
			@RequestParam String password,
			HttpServletRequest request,
			@AuthenticationPrincipal UserDetails userDetails
	) {

//		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

//		authorities.forEach(System.out :: print);

		ModelAndView mav = new ModelAndView("index", "user",new User());

		try {
			if(authenticationService.authenticateUser(username, password, request )) {
				return new ModelAndView("home");
			}
		} catch (AuthenticationException e) {
			mav.addObject("message",e.getMessage());
			return mav;
		}

		return mav;
	}

	/*@GetMapping("/login")
	public ModelAndView loginPage(HttpServletRequest request) {
		return new ModelAndView("index", "user",new User());
	}*/

	@GetMapping("/logout-manual")
	public ModelAndView logout(HttpServletRequest request) {

		HttpSession session=request.getSession();
		session.setAttribute("services", null);
		session.setAttribute("servicesMenu", null);
//		session.setAttribute("servicesTarget", null);

		session.removeAttribute("services");
		session.removeAttribute("servicesMenu");
//		session.removeAttribute("servicesTarget");

		session.invalidate();

		ModelAndView mav = new ModelAndView("index", "user",new User());
		mav.addObject("message","You have been successfully logged out.");

		return mav;
	}

	@GetMapping("/expireSession")
	public ModelAndView expireSession(HttpServletRequest request) {

		HttpSession session = request.getSession();

		session.setAttribute("services", null);
		session.setAttribute("servicesMenu", null);
		session.removeAttribute("services");
		session.removeAttribute("servicesMenu");

		if(session != null) {
			session.invalidate();
		}

		ModelAndView mav = new ModelAndView("index", "user", new User());
		mav.addObject("message","Unauthorized Access. You have been logged" +
				" out.");

		return mav;
	}


	/*@GetMapping("/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
		//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}*/


}
