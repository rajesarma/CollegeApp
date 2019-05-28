package in.education.college.converter;

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
import in.education.college.model.repository.QualificationRepository;
import in.education.college.model.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

@Component
public class EmployeeConverter {

	private DepartmentRepository departmentRepository;
	private QualificationRepository qualificationRepository;
	private BloodGroupRepository bloodGroupRepository;
	private SemesterRepository semesterRepository;
	private AcademicYearRepository academicYearRepository;

	@Autowired
	EmployeeConverter(
			final DepartmentRepository departmentRepository,
			final QualificationRepository qualificationRepository,
			final BloodGroupRepository bloodGroupRepository,
			final SemesterRepository semesterRepository,
			final AcademicYearRepository academicYearRepository) {
		this.departmentRepository = departmentRepository;
		this.qualificationRepository = qualificationRepository;
		this.bloodGroupRepository = bloodGroupRepository;
		this.semesterRepository = semesterRepository;
		this.academicYearRepository = academicYearRepository;
	}


	public Employee convert(final EmployeeForm employeeForm) {

		Calendar cal = Calendar.getInstance();
		Employee employee = new Employee();
		employee.setEmployeeId(employeeForm.getEmployeeId());
		employee.setName(employeeForm.getName().trim());
		employee.setEmpNo(employeeForm.getEmpNo().trim());
		employee.setDeptId(employeeForm.getDeptId());
		employee.setFatherName(employeeForm.getFatherName().trim());

		if(!employeeForm.getDob().isEmpty() && !employeeForm.getDoj().isEmpty()) {
			try {
				cal.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(employeeForm.getDob().replace("/", "-")));
				employee.setDob(cal.getTime());
				cal.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(employeeForm.getDoj().replace("/", "-")));
				employee.setDoj(cal.getTime());
			} catch (ParseException e) {

			}
		}

		if(employeeForm.getDateOfRelieving()!=null && !employeeForm.getDateOfRelieving().isEmpty()) {
			try {
				cal.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(employeeForm.getDateOfRelieving().replace("/", "-")));
				employee.setDateOfRelieving(cal.getTime());

			} catch (ParseException e) {

			}
		}

		employee.setAadharNo(employeeForm.getAadharNo().trim());
		employee.setAddress(employeeForm.getAddress().trim());
		employee.setEmail(employeeForm.getEmail().trim());
		employee.setMobile(employeeForm.getMobile());
		employee.setBloodGroupId(employeeForm.getBloodGroupId());
		employee.setQlyId(employeeForm.getQlyId());
		employee.setGender(employeeForm.getGender());
		employee.setSalary(employeeForm.getSalary());
		employee.setJoiningAcademicYearId(employeeForm.getJoiningAcademicYearId());
		employee.setJoiningSemesterId(employeeForm.getJoiningSemesterId());
		try {
			if(employeeForm.getImage() != null && employeeForm.getImage().getBytes().length > 0) {
				employee.setPhotoName(employeeForm.getImage().getOriginalFilename());
				employee.setPhoto(employeeForm.getImage().getBytes());

			} else if(employeeForm.getPhotoData() != null) {

				employee.setPhoto(java.util.Base64.getDecoder().decode(employeeForm.getPhotoData()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(employeeForm.getSalary()!=null) {
			employee.setSalary(employeeForm.getSalary());
		}
		return employee;
	}

	public EmployeeForm convert(final Employee employee) {

		EmployeeForm employeeForm = new EmployeeForm();
		employeeForm.setEmployeeId(employee.getEmployeeId());
		employeeForm.setName(employee.getName());
		employeeForm.setEmpNo(employee.getEmpNo());
		employeeForm.setDeptId(employee.getDeptId());
		employeeForm.setFatherName(employee.getFatherName());
		employeeForm.setDob(new SimpleDateFormat("dd/MM/yyyy").format(employee.getDob()));
		employeeForm.setDoj(new SimpleDateFormat("dd/MM/yyyy").format(employee.getDoj()));
		employeeForm.setAadharNo(employee.getAadharNo());
		employeeForm.setAddress(employee.getAddress());
		employeeForm.setEmail(employee.getEmail());
		employeeForm.setMobile(employee.getMobile());
		employeeForm.setBloodGroupId(employee.getBloodGroupId());

		if(employee.getDateOfRelieving() != null) {
			employeeForm.setDateOfRelieving(new SimpleDateFormat("dd/MM/yyyy").format(employee.getDateOfRelieving()));
		}

		employeeForm.setQlyId(employee.getQlyId());
		employeeForm.setGender(employee.getGender());
		employeeForm.setSalary(employee.getSalary());
		employeeForm.setJoiningAcademicYearId(employee.getJoiningAcademicYearId());
		employeeForm.setJoiningSemesterId(employee.getJoiningSemesterId());

		if (employee.getPhoto() != null && employee.getPhoto().length > 0) {
			employeeForm.setPhotoData(new String(java.util.Base64.getEncoder().encode(employee.getPhoto())));
			employeeForm.setPhotoName(employee.getPhotoName());
		}

		if(employee.getSalary() != null) {
			employeeForm.setSalary(employee.getSalary());
		}

		Optional<Department> departmentOptional  =
				departmentRepository.findById(employee.getDeptId());
		if(departmentOptional.isPresent()) {
			employeeForm.setDepartment(departmentOptional.get().getDeptName());
		}

		Optional<Qualification> qualificationOptional =
				qualificationRepository.findById(employee.getQlyId());
		if(qualificationOptional.isPresent()) {
			employeeForm.setQualification(qualificationOptional.get().getQlyName());
		}

		Optional<BloodGroup> bloodGroupOptional =
				bloodGroupRepository.findById(employee.getBloodGroupId());
		if(bloodGroupOptional.isPresent()) {
			employeeForm.setBloodGroup(bloodGroupOptional.get().getBloodGroup());
		}

		Optional<AcademicYear> joiningAcademicYearOptional  =
				academicYearRepository.findById(employee.getJoiningAcademicYearId());
		if(joiningAcademicYearOptional.isPresent()) {
			employeeForm.setJoiningAcademicYear(joiningAcademicYearOptional.get().getAcademicYear());
		}

		Optional<Semester> joiningSemesterOptional =
				semesterRepository.findById(employee.getJoiningSemesterId());
		if(joiningSemesterOptional.isPresent()) {
			employeeForm.setJoiningSemester(joiningSemesterOptional.get().getSemesterName());
		}

		return employeeForm;
	}
}
