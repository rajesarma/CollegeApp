package in.education.college.common.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError() {
//		System.out.println("Error");
		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
