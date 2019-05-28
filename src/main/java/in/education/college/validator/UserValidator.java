package in.education.college.validator;

import in.education.college.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

	private static final Pattern EMAIL_REGEX =
			Pattern.compile("^[\\w\\d._-]+@[\\w\\d.-]+\\.[\\w\\d]{2,6}$");

	@Override
	public boolean supports(Class<?> clazz) {
		return UserDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		UserDto userDto = (UserDto) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required.user" +
				".name", "User Name cannot be Empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.user" +
				".password", "User Passsword cannot be Empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userDesc", "required.user.userDesc", "User Description cannot be Empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.user" +
				".email", "User Email cannot be Empty");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roles", "required.user" +
				".roles", "User Roles cannot be Empty");

//		if(!errors.hasErrors()) {

//			if(userDto.getRoles().size() < 1) {

			if(userDto.getRoles().size() == 0) {
				errors.rejectValue("roles", "invalid.user.roles", "Select atleast " +
						"one User Role.");
			}

			/*if(!EmailValidator.getInstance().isValid(userDto.getEmail())) {
				errors.rejectValue("email", "invalid.user.email", "User Email must be a" +
						" valid email address.");
			}*/

			if (userDto.getEmail() != null && !EMAIL_REGEX.matcher(userDto.getEmail()).matches()) {
				errors.rejectValue("email", "invalid.user.email");
			}

//		}

	}
}
