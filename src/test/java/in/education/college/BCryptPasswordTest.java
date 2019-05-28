package in.education.college;

import java.util.Arrays;
import java.util.List;

public class BCryptPasswordTest {

	public static void main(String... args) {

		org.springframework.security.crypto.password.PasswordEncoder encoder
				= new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

		String[] pwds = {"guest","manage","college","super","rajesh","raje","sreedhar","testing",
				"swamy","vv","dd", "faculty"};

		List<String> pwdList = Arrays.asList(pwds);

		for(String pwd : pwdList) {
			String passwd = encoder.encode(pwd);
			System.out.println(pwd+ " : "+passwd); // print hash
			System.out.println(encoder.matches(pwd, passwd));
		}
	}

}
