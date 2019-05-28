package in.education.college.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

	@GetMapping("/testInterceptor")
	public String testInterceptor(@RequestHeader("college-auth-key") String authorization) {

		if(authorization != null) {
			return "Tested OK";
		}

		return "Empty Data";
	}
}
