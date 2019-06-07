package in.education.college.employee;

import in.education.college.common.util.Constants.Conditions;
import in.education.college.common.util.Constants.Roles;
import in.education.college.common.util.Constants.StrConstants;
import in.education.college.converter.EmployeeConverter;
import in.education.college.converter.UserConverter;
import in.education.college.dto.EmployeeForm;
import in.education.college.dto.UserDto;
import in.education.college.model.AcademicYear;
import in.education.college.model.BloodGroup;
import in.education.college.model.Department;
import in.education.college.model.Employee;
import in.education.college.model.Qualification;
import in.education.college.model.Semester;
import in.education.college.model.User;
import in.education.college.model.repository.AcademicYearRepository;
import in.education.college.model.repository.BloodGroupRepository;
import in.education.college.model.repository.DepartmentRepository;
import in.education.college.model.repository.EmployeeRepository;
import in.education.college.model.repository.QualificationRepository;
import in.education.college.model.repository.RoleRepository;
import in.education.college.model.repository.SemesterRepository;
import in.education.college.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeService {

	private AcademicYearRepository academicYearRepository;
	private SemesterRepository semesterRepository;
	private EmployeeRepository employeeRepository;
	private BloodGroupRepository bloodGroupRepository;
	private DepartmentRepository departmentRepository;
	private QualificationRepository qualificationRepository;
	private EmployeeConverter employeeConverter;

	private RoleRepository roleRepository;
	private UserRepository userRepository;

	private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

	Map<Long,String> getDepartments() {

		return StreamSupport.stream(departmentRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(Department::getDetpId,
						Department::getDeptName));
	}

	Map<Long,String> getBloodGroups() {
		return StreamSupport.stream(bloodGroupRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(BloodGroup::getBloodGroupId,
						BloodGroup::getBloodGroup));
	}

	Map<Long,String> getAcademicYears() {

		return StreamSupport.stream(academicYearRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(AcademicYear::getAcademicYearId, AcademicYear::getAcademicYear));
	}

	Map<Long,String> getSemesters() {

		return StreamSupport.stream(semesterRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(Semester::getSemesterId,
						Semester::getSemesterName));
	}

	Map<Long,String> getQualifications() {

		return StreamSupport.stream(qualificationRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(Qualification::getQlyId ,
						Qualification::getQlyName))
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey,
						Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	Optional<EmployeeForm> save(HttpServletRequest request, EmployeeForm employeeForm) {

		Employee employee = employeeConverter.convert(employeeForm);
		Employee savedEmployee = employeeRepository.save(employee);

		if(employeeRepository.existsById(savedEmployee.getEmployeeId())) {

			UserDto userDto = new UserDto(savedEmployee.getEmpNo(),
					new BCryptPasswordEncoder().encode(savedEmployee.getEmpNo()),
					false,
					savedEmployee.getName(),
					savedEmployee.getEmail(),
					roleRepository.findByRoleId(Roles.EMPLOYEE_ROLE_ID)
			);

			User savedUser = userRepository.save(UserConverter.convert(userDto));

			if(userRepository.existsById(savedUser.getUserId())) {

				log.info("<EMPLOYEE><EMPLOYEE:SAVE>"
						+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
						+ "<Employee : " + savedEmployee.getName() +" created with username " + savedEmployee.getEmpNo() + ">");

			} else {
				log.info("<EMPLOYEE><EMPLOYEE:SAVE>"
						+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
						+ "<Employee : " + savedEmployee.getName() +" created without username >");
			}

			return Optional.of(employeeConverter.convert(savedEmployee));
		}

		return Optional.empty();
	}

	List<EmployeeForm> list(HttpServletRequest request, long deptId,
			long joiningAcademicYearId,
			long joiningSemesterId) {

		List<Employee> employeesList = new ArrayList<>();
		List<EmployeeForm> employeeForms = new ArrayList<>();

		if(deptId > 0 && joiningAcademicYearId > 0 && joiningSemesterId > 0 ) {
			employeesList = employeeRepository.findByDeptIdAndJoiningAcademicYearIdAndJoiningSemesterId(deptId,
					joiningAcademicYearId, joiningSemesterId);

		} else if(deptId > 0 && joiningAcademicYearId > 0 ) {
			employeesList = employeeRepository.findByDeptIdAndJoiningAcademicYearId(deptId,
					joiningAcademicYearId);

		} else if(deptId > 0 && joiningSemesterId > 0 ) {
			employeesList = employeeRepository.findByDeptIdAndJoiningSemesterId(deptId,
					joiningSemesterId);

		} else if(joiningAcademicYearId > 0 && joiningSemesterId > 0 ) {
			employeesList = employeeRepository.findByJoiningAcademicYearIdAndJoiningSemesterId(
					joiningAcademicYearId, joiningSemesterId);

		} else if(deptId > 0) {
			employeesList = employeeRepository.findByDeptId(deptId);

		} else if(joiningAcademicYearId > 0) {
			employeesList =
					employeeRepository.findByJoiningAcademicYearId(joiningAcademicYearId);

		} else if(joiningSemesterId > 0) {
			employeesList = employeeRepository.findByJoiningSemesterId(joiningSemesterId);
		}

		if(!employeesList.isEmpty()) {
			employeesList.forEach(employee -> employeeForms.add(employeeConverter.convert(employee)));
		}

		log.info("<EMPLOYEE><EMPLOYEE:LIST>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<Record count: " + employeesList.size() + ">");

		return employeeForms;
	}


	List<EmployeeForm> listBySalaries(HttpServletRequest request, long deptId,
			long joiningAcademicYearId,
			long joiningSemesterId, double salary, String condition) {

		List<Employee> employeesList = new ArrayList<>();
		List<EmployeeForm> employeeForms = new ArrayList<>();
		List<Long> empIds;

		// If salary not selected and other parameters selected
		if((salary <= 0) && (deptId > 0 || joiningAcademicYearId > 0 || joiningSemesterId > 0) ) {
			return list(request, deptId, joiningAcademicYearId, joiningSemesterId);
		}

		// If salary selected
		if(salary > 0) {

			// If salary selected and other parameters are also selected
			if((deptId > 0 || joiningAcademicYearId > 0 || joiningSemesterId > 0)) {

				empIds = list(request, deptId, joiningAcademicYearId, joiningSemesterId)
						.stream()
						.map(EmployeeForm::getEmployeeId)
						.collect(Collectors.toList());

			} else {	// Only Salary selected
				empIds = StreamSupport.stream(employeeRepository.findAll().spliterator(),false)
						.map(Employee::getEmployeeId)
						.collect(Collectors.toList());
			}

			switch (condition) {

				case Conditions.LT : {
					employeesList =
							employeeRepository.findAllBySalaryLessThanAndEmployeeIdIsIn(salary, empIds);
					break;
				}
				case Conditions.LE : {
					employeesList =
							employeeRepository.findAllBySalaryLessThanEqualAndEmployeeIdIsIn(salary, empIds);
					break;
				}
				case Conditions.EQ : {
					employeesList =
							employeeRepository.findAllBySalaryEqualsAndEmployeeIdIsIn(salary, empIds);
					break;
				}
				case Conditions.GE : {
					employeesList =
							employeeRepository.findAllBySalaryGreaterThanEqualAndEmployeeIdIsIn(salary, empIds);
					break;
				}
				case Conditions.GT : {
					employeesList =
							employeeRepository.findAllBySalaryGreaterThanAndEmployeeIdIsIn(salary, empIds);
					break;
				}
				default: { break; }
			}
		}

		if(!employeesList.isEmpty()) {
			employeesList.forEach(employee -> employeeForms.add(employeeConverter.convert(employee)));
		}

		log.info("<EMPLOYEE><EMPLOYEE:LIST>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<Record count: " + employeesList.size() + ">");

		return employeeForms;
	}

	Optional<EmployeeForm> findByType(String type, String value) {

		Optional<Employee> employeeOptional;

		switch(type) {
			case StrConstants.EMP_NO : {
				employeeOptional = employeeRepository.findByEmpNo(value);
				break;
			}

			case StrConstants.AADHAR_NO : {
				employeeOptional = employeeRepository.findFirstByAadharNo(value);
				break;
			}

			case StrConstants.MOBILE_NO : {
				employeeOptional = employeeRepository.findFirstByMobile(value);
				break;
			}

			case StrConstants.EMAIL : {
				employeeOptional = employeeRepository.findFirstByEmail(value);
				break;
			}

			default : {
				employeeOptional = Optional.empty();
				break;
			}
		}

		if(employeeOptional.isPresent()) {
			return Optional.of(employeeConverter.convert(employeeOptional.get()));
		}
		return Optional.empty();
	}

	Optional<EmployeeForm> findById(Long employeeId) {
		Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
		if(employeeOptional.isPresent()) {
			EmployeeForm employeeForm = employeeConverter.convert(employeeOptional.get());
			return Optional.of(employeeForm);
		}

		return Optional.empty();
	}

	Optional<EmployeeForm> update(HttpServletRequest request, EmployeeForm employeeForm){

		Employee employee = employeeConverter.convert(employeeForm);
		Optional<Employee> employeeOptional = employeeRepository.findById(employee.getEmployeeId());

		if(employeeOptional.isPresent()) {

			Employee toSaveEmployee = employeeOptional.get();

			String oldEmpNo = toSaveEmployee.getEmpNo();


			BeanUtils.copyProperties(employee, toSaveEmployee);
			Employee savedEmployee = employeeRepository.save(toSaveEmployee);

			if(employeeRepository.existsById(savedEmployee.getEmployeeId())) {

				// Update Username
				Optional<User> userOptional = userRepository.findByUsername(oldEmpNo);

				if(userOptional.isPresent()) {
					User toUpdateUser = userOptional.get();
					toUpdateUser.setUsername(savedEmployee.getEmpNo());
					toUpdateUser.setPassword(new BCryptPasswordEncoder().encode(savedEmployee.getEmpNo()));

					User savedUser = userRepository.save(toUpdateUser);

					log.info("<EMPLOYEE><EMPLOYEE:UPDATE>"
							+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
							+ "<EMPLOYEE : " + savedEmployee.getName() + " updated with new User Name " + savedUser.getUsername() + ">");
				} else {

					log.info("<EMPLOYEE><EMPLOYEE:UPDATE>"
							+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
							+ "<EMPLOYEE : " + savedEmployee.getName() +" updated, Username not found to update >");
				}
				return Optional.of(employeeConverter.convert(savedEmployee));
			}
		}

		log.info("<EMPLOYEE><EMPLOYEE:UPDATE>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<EMPLOYEE : " + employeeOptional.get().getName() +" not updated, Employee not found to update >");

		return Optional.empty();
	}

	Optional<EmployeeForm> delete(HttpServletRequest request, EmployeeForm employeeForm){
		Employee employee = employeeConverter.convert(employeeForm);
		employeeRepository.deleteById(employee.getEmployeeId());
		Optional<Employee> employeeOptional = employeeRepository.findById(employee.getEmployeeId());

		if(employeeOptional.isPresent()) {

			log.info("<EMPLOYEE><EMPLOYEE:DELETE>"
					+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
					+ "<" + employeeOptional.get().getName() +" : not deleted>");

			return Optional.of(employeeConverter.convert(employeeOptional.get()));
		}

		// Delete Username
		Optional<User> userOptional =
				userRepository.findByUsername(employeeForm.getEmpNo());

		if(userOptional.isPresent()) {
			userRepository.deleteById(userOptional.get().getUserId());

			log.info("<EMPLOYEE><EMPLOYEE:DELETE>"
					+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
					+ "<EMPLOYEE : " + employeeForm.getName() +" deleted with user>");
		} else {

			log.info("<EMPLOYEE><EMPLOYEE:DELETE>"
					+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
					+ "<Student : " + employeeForm.getName() +" deleted, Username not found to delete >");
		}

		log.info("<EMPLOYEE><EMPLOYEE:DELETE>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<" + employeeForm.getName() +" : deleted>");

		return Optional.empty();
	}

	Pair search(HttpServletRequest request, EmployeeForm employeeForm) {

		String message = "";

		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeForm, employee);

		List<EmployeeForm> employeeForms = new ArrayList<>();

		if(employee.getName().isEmpty() && !employee.getEmpNo().isEmpty()) {

			Optional<EmployeeForm> employeeOptional = findByType(StrConstants.EMP_NO,
					employee.getEmpNo());

			if(employeeOptional.isPresent()) {
				employeeForms.add(employeeOptional.get());
			}

			message = "Search results based on Emp No.: '"+ employeeForm.getEmpNo()+"'";

		} else if(!employee.getName().isEmpty() ) {

			List<Employee> employeeList;

			if(employee.getEmpNo().isEmpty()){
				employeeList = employeeRepository.findByNameContains(employee.getName());
				message = "Search results based on Name: '"+ employeeForm.getName() + "'";
			} else {
				employeeList =
						employeeRepository.findByEmpNoAndNameContains(employee.getEmpNo(),
						employee.getName());
				message = "Search results based on Name: '"+ employeeForm.getName() + "' " +
						"and Roll No.: '"+ employeeForm.getEmpNo()+"'";
			}

			if(!employeeList.isEmpty()) {
				employeeList.forEach(stud -> employeeForms.add(employeeConverter.convert(stud)));
			}
		}

		if(employeeForms.isEmpty()) {
			message = message + " No records found based on entered data.";
		}

		log.info("<EMPLOYEE><EMPLOYEE:SEARCH>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<" + message +" >");

		return Pair.of(message, employeeForms);
	}

	@Autowired
	public void setAcademicYearRepository(AcademicYearRepository academicYearRepository) {
		this.academicYearRepository = academicYearRepository;
	}

	@Autowired
	public void setSemesterRepository(SemesterRepository semesterRepository) {
		this.semesterRepository = semesterRepository;
	}

	@Autowired
	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Autowired
	public void setBloodGroupRepository(BloodGroupRepository bloodGroupRepository) {
		this.bloodGroupRepository = bloodGroupRepository;
	}

	@Autowired
	public void setDepartmentRepository(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Autowired
	public void setQualificationRepository(QualificationRepository qualificationRepository) {
		this.qualificationRepository = qualificationRepository;
	}

	@Autowired
	public void setEmployeeConverter(EmployeeConverter employeeConverter) {
		this.employeeConverter = employeeConverter;
	}

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
