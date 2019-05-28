package in.education.college.employee;

import in.education.college.converter.EmployeeConverter;
import in.education.college.dto.EmployeeForm;
import in.education.college.model.AcademicYear;
import in.education.college.model.BloodGroup;
import in.education.college.model.Department;
import in.education.college.model.Employee;
import in.education.college.model.Qualification;
import in.education.college.model.Semester;
import in.education.college.model.repository.AcademicYearRepository;
import in.education.college.model.repository.BloodGroupRepository;
import in.education.college.model.repository.DepartmentRepository;
import in.education.college.model.repository.EmployeeRepository;
import in.education.college.model.repository.QualificationRepository;
import in.education.college.model.repository.SemesterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

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

	@Autowired
	EmployeeService(
			final SemesterRepository semesterRepository,
			final AcademicYearRepository academicYearRepository,
			final EmployeeRepository employeeRepository,
			final BloodGroupRepository bloodGroupRepository,
			final DepartmentRepository departmentRepository,
			final QualificationRepository qualificationRepository,
			final EmployeeConverter employeeConverter) {
		this.semesterRepository = semesterRepository;
		this.academicYearRepository = academicYearRepository;
		this.employeeRepository = employeeRepository;
		this.bloodGroupRepository = bloodGroupRepository;
		this.departmentRepository = departmentRepository;
		this.qualificationRepository = qualificationRepository;
		this.employeeConverter = employeeConverter;
	}

	private String empNo = "Emp No.";
	private String mobileNo = "Mobile No.";
	private String aadharNo = "Aadhar No.";
	private String email = "Email";

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

	Optional<EmployeeForm> save(EmployeeForm employeeForm) {

		Employee employee = employeeConverter.convert(employeeForm);
		Employee savedEmployee = employeeRepository.save(employee);

		if(savedEmployee.getEmployeeId() > 0) {
			return Optional.of(employeeConverter.convert(savedEmployee));
		}

		return Optional.empty();
	}

	List<EmployeeForm> list(long deptId, long joiningAcademicYearId,
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

		return employeeForms;
	}


	List<EmployeeForm> listBySalaries(long deptId, long joiningAcademicYearId,
			long joiningSemesterId, double salary, String condition) {

		List<Employee> employeesList = new ArrayList<>();
		List<EmployeeForm> employeeForms = new ArrayList<>();
		List<Long> empIds;

		// If salary not selected and other parameters selected
		if((salary <= 0) && (deptId > 0 || joiningAcademicYearId > 0 || joiningSemesterId > 0) ) {
			return list(deptId, joiningAcademicYearId, joiningSemesterId);
		}

		// If salary selected
		if(salary > 0) {

			// If salary selected and other parameters are also selected
			if((deptId > 0 || joiningAcademicYearId > 0 || joiningSemesterId > 0)) {

				empIds = list(deptId, joiningAcademicYearId, joiningSemesterId)
						.stream()
						.map(EmployeeForm::getEmployeeId)
						.collect(Collectors.toList());

			} else {	// Only Salary selected
				empIds = StreamSupport.stream(employeeRepository.findAll().spliterator(),false)
						.map(Employee::getEmployeeId)
						.collect(Collectors.toList());
			}

			switch (condition) {

				case "<": {
					employeesList =
							employeeRepository.findAllBySalaryLessThanAndEmployeeIdIsIn(salary, empIds);
					break;
				}
				case "<=": {
					employeesList =
							employeeRepository.findAllBySalaryLessThanEqualAndEmployeeIdIsIn(salary, empIds);
					break;
				}
				case "=": {
					employeesList =
							employeeRepository.findAllBySalaryEqualsAndEmployeeIdIsIn(salary, empIds);
					break;
				}
				case ">=": {
					employeesList =
							employeeRepository.findAllBySalaryGreaterThanEqualAndEmployeeIdIsIn(salary, empIds);
					break;
				}
				case ">": {
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

		return employeeForms;
	}

	Optional<EmployeeForm> findByType(String type, String value) {

		Optional<Employee> employeeOptional = null;

		if(type.equalsIgnoreCase(empNo)) {
			employeeOptional = employeeRepository.findByEmpNo(value);

		} else if(type.equalsIgnoreCase(email)) {
			employeeOptional = employeeRepository.findFirstByEmail(value);
		} else if(type.equalsIgnoreCase(aadharNo)) {
			employeeOptional = employeeRepository.findFirstByAadharNo(value);
		} else if(type.equalsIgnoreCase(mobileNo)) {
			employeeOptional = employeeRepository.findFirstByMobile(value);
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

	Optional<EmployeeForm> update(EmployeeForm employeeForm){

		Employee employee = employeeConverter.convert(employeeForm);
		Optional<Employee> employeeOptional = employeeRepository.findById(employee.getEmployeeId());

		if(employeeOptional.isPresent()) {
			Employee toSaveEmployee = employeeOptional.get();
			BeanUtils.copyProperties(employee, toSaveEmployee);
			Employee savedEmployee = employeeRepository.save(toSaveEmployee);

			if(employeeRepository.existsById(savedEmployee.getEmployeeId())) {
				return Optional.of(employeeConverter.convert(savedEmployee));
			}
		}
		return Optional.empty();
	}

	Optional<EmployeeForm> delete(EmployeeForm employeeForm){
		Employee employee = employeeConverter.convert(employeeForm);
		employeeRepository.deleteById(employee.getEmployeeId());
		Optional<Employee> employeeOptional = employeeRepository.findById(employee.getEmployeeId());

		if(employeeOptional.isPresent()) {
			return Optional.of(employeeConverter.convert(employeeOptional.get()));
		}

		return Optional.empty();
	}

	Pair search(EmployeeForm employeeForm) {

		String message = "";

		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeForm, employee);

		List<EmployeeForm> employeeForms = new ArrayList<>();

		if(employee.getName().isEmpty() && !employee.getEmpNo().isEmpty()) {

			Optional<EmployeeForm> employeeOptional = findByType(empNo,
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

		return Pair.of(message, employeeForms);
	}
}
