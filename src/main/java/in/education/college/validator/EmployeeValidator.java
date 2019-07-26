package in.education.college.validator;

import in.education.college.dto.EmployeeForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class EmployeeValidator implements Validator {


	private static final Pattern EMAIL_REGEX =
			Pattern.compile("^[\\w\\d._-]+@[\\w\\d.-]+\\.[\\w\\d]{2,6}$");

	private static final Pattern NUMERIC_REGEX =
			Pattern.compile("\\d+");


	@Override
	public boolean supports(Class<?> candidate) {
		return EmployeeForm.class.isAssignableFrom(candidate);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.employee.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fatherName", "required.employee.fatherName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "empNo", "required.employee.empNo");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "required.employee.mobile");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.employee.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "required.employee.address");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dob", "required.employee.dob");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doj", "required.employee.doj");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfRelieving", "required.employee.dateOfRelieving");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aadharNo", "required.employee.aadharNo");

		EmployeeForm employeeForm = (EmployeeForm) obj;

		if(employeeForm.getDeptId() == 0) {
			errors.rejectValue("deptId", "zeroValue.employee.deptId",
					new Object[]{
					"Department"}, "Please select this");
		}

		if(employeeForm.getQlyId() == 0) {
			errors.rejectValue("qlyId", "zeroValue.employee.qlyId", new Object[]{
					"Qualification"}, "Please select this");
		}

		/*if(employeeForm.getBloodGroupId() == 0 ) {
			errors.rejectValue("bloodGroupId", "zeroValue.employee.bloodGroupId", new Object[]{
					"Blood Group"}, "Please select this");
		}*/

		if(employeeForm.getJoiningAcademicYearId() == 0) {
			errors.rejectValue("joiningAcademicYearId", "zeroValue.employee.joiningAcademicYearId",
					new Object[]{
					"Joining Academic Year"}, "Please select this");
		}

		if(employeeForm.getJoiningSemesterId() == 0) {
			errors.rejectValue("joiningSemesterId", "zeroValue.employee.joiningSemesterId",
					new Object[]{
					"Joining Semester"}, "Please select this");
		}

		if (employeeForm.getEmail() != null && !EMAIL_REGEX.matcher(employeeForm.getEmail()).matches()) {
			errors.rejectValue("email", "invalid.employee.email");
		}

		if(employeeForm.getMobile() != null && !NUMERIC_REGEX.matcher(employeeForm.getMobile()).matches()
			&& employeeForm.getMobile().length() != 10) {
			errors.rejectValue("mobile", "invalid.employee.mobile",
					new Object[]{"Mobile No."}, "Please Enter valid Mobile No.");
		}

	}
}
