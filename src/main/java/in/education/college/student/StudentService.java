package in.education.college.student;

import in.education.college.common.util.Constants.Roles;
import in.education.college.common.util.Constants.StrConstants;
import in.education.college.converter.StudentConverter;
import in.education.college.converter.UserConverter;
import in.education.college.dto.StudentDto;
import in.education.college.dto.UserDto;
import in.education.college.model.AcademicYear;
import in.education.college.model.Batch;
import in.education.college.model.BloodGroup;
import in.education.college.model.Branch;
import in.education.college.model.Semester;
import in.education.college.model.Student;
import in.education.college.model.User;
import in.education.college.model.Year;
import in.education.college.model.repository.AcademicYearRepository;
import in.education.college.model.repository.BatchRepository;
import in.education.college.model.repository.BloodGroupRepository;
import in.education.college.model.repository.BranchRepository;
import in.education.college.model.repository.RoleRepository;
import in.education.college.model.repository.UserRepository;
import in.education.college.model.repository.YearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentService {

	private BatchRepository batchRepository;
	private BranchRepository branchRepository;
	private YearRepository yearRepository;
	private AcademicYearRepository academicYearRepository;
	private StudentRepository studentRepository;
	private BloodGroupRepository bloodGroupRepository;
	private StudentConverter studentConverter;

	private RoleRepository roleRepository;
	private UserRepository userRepository;

	private static final Logger log = LoggerFactory.getLogger(StudentService.class);

	public Map<Long,String> getBatches() {

		return StreamSupport.stream(batchRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(Batch::getBatchId,
						Batch::getBatchName));
	}

	public Map<String,String> getBranches() {

		return StreamSupport.stream(branchRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(Branch::getBranchId,Branch::getBranchName));
	}

	public Map<Long,String> getYears() {

		return StreamSupport.stream(yearRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(Year::getYearId, Year::getYear));
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

	public Optional<Map<Long,String>> getSemesetersByYearId(long yearId) {

		Optional<Year> yearOptional = yearRepository.findByYearId(yearId);

		if(yearOptional.isPresent()) {

			return Optional.of(yearOptional.get()
					.getSemseters()
					.stream()
					.collect(Collectors.toMap(Semester::getSemesterId,
							Semester::getSemesterName)));
		}

		return Optional.empty();
	}

	Optional<StudentDto> save(HttpServletRequest request, StudentDto studentDto) {

		Student student = studentConverter.convert(studentDto);
		Student savedStudent = studentRepository.save(student);

		if(studentRepository.existsById(savedStudent.getStudentId())) {

			// Taking Roll No as User Name
			UserDto userDto = new UserDto(savedStudent.getRollNo(),
					new BCryptPasswordEncoder().encode(savedStudent.getRollNo()),
					false,
					savedStudent.getName() + savedStudent.getBranchId(),
					savedStudent.getEmail(),
					roleRepository.findByRoleId(Roles.STUDENT_ROLE_ID)
					);

			User savedUser = userRepository.save(UserConverter.convert(userDto));

			if(userRepository.existsById(savedUser.getUserId())) {
				log.info("<STUDENT><STUDENT:SAVE>"
						+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
						+ "<Student : " + savedStudent.getName() +" created with username " + savedStudent.getRollNo() + ">");
			} else {
				log.info("<STUDENT><STUDENT:SAVE>"
						+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
						+ "<Student : " + savedStudent.getName() +" created without username >");
			}

			return Optional.of(studentConverter.convert(savedStudent));
		}

		return Optional.empty();
	}

	List<StudentDto> list(HttpServletRequest request, String branchId, long batchId,
			long joiningYearId) {

		List<Student> studentsList = new ArrayList<>();
		List<StudentDto> studentDtos = new ArrayList<>();

		boolean isBranchSelected = false;

		if(branchId !=null && branchId.length() > 0 && !branchId.equals("0")) {
			isBranchSelected = true;
		}

		if(isBranchSelected && (batchId > 0) && (joiningYearId > 0)) {
			studentsList =
					studentRepository.findByBranchIdAndBatchIdAndJoiningYearId(branchId,
					batchId, joiningYearId);
		} else if(isBranchSelected && (batchId > 0)) {
			studentsList = studentRepository.findByBranchIdAndBatchId(branchId,
					batchId);
		} else if(isBranchSelected && (joiningYearId > 0)) {
			studentsList = studentRepository.findByBranchIdAndJoiningYearId(branchId,
					joiningYearId);
		} else if((batchId > 0) && (joiningYearId > 0)) {
			studentsList =
					studentRepository.findByBatchIdAndJoiningYearId(batchId, joiningYearId);
		} else if(isBranchSelected) {
			studentsList =
					studentRepository.findByBranchId(branchId);
		} else if(batchId > 0) {
			studentsList =
					studentRepository.findByBatchId(batchId);
		} else if(joiningYearId > 0) {
			studentsList =
					studentRepository.findByJoiningYearId(joiningYearId);
		}

		if(!studentsList.isEmpty()) {
			studentsList.forEach(student -> studentDtos.add(studentConverter.convert(student)));
		}

		log.info("<STUDENT><STUDENT:LIST>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<Record count: " + studentsList.size() + ">");

		return studentDtos;
	}

	Optional<StudentDto> findByType(String type, String value) {

		Optional<Student> studentOptional;

		switch(type) {
			case StrConstants.ROLL_NO : {
				studentOptional = studentRepository.findByRollNo(value);
				break;
			}

			case StrConstants.AADHAR_NO : {
				studentOptional = studentRepository.findFirstByAadharNo(value);
				break;
			}

			case StrConstants.MOBILE_NO : {
				studentOptional = studentRepository.findFirstByMobileNo(value);
				break;
			}

			case StrConstants.EMAIL : {
				studentOptional = studentRepository.findFirstByEmail(value);
				break;
			}

			default : {
				studentOptional = Optional.empty();
				break;
			}
		}

		if(studentOptional.isPresent()) {
			return Optional.of(studentConverter.convert(studentOptional.get()));
		}

		return Optional.empty();
	}

	Optional<StudentDto> findById(Long studentId) {
		Optional<Student> studentOptional = studentRepository.findById(studentId);
		if(studentOptional.isPresent()) {
			StudentDto studentDto = studentConverter.convert(studentOptional.get());
			return Optional.of(studentDto);
		}

		return Optional.empty();
	}

	Optional<StudentDto> update(HttpServletRequest request, StudentDto studentDto){

		Student student = studentConverter.convert(studentDto);
		Optional<Student> studentOptional = studentRepository.findById(student.getStudentId());

		if(studentOptional.isPresent()) {
			Student toSaveStudent = studentOptional.get();

			String oldRollNo = toSaveStudent.getRollNo();

			BeanUtils.copyProperties(student, toSaveStudent);
			Student savedStudent = studentRepository.save(toSaveStudent);

			if(studentRepository.existsById(savedStudent.getStudentId())) {

				// Update Username
				Optional<User> userOptional = userRepository.findByUsername(oldRollNo);

				if(userOptional.isPresent()) {
					User toUpdateUser = userOptional.get();
					toUpdateUser.setUsername(savedStudent.getRollNo());
					toUpdateUser.setPassword(new BCryptPasswordEncoder().encode(savedStudent.getRollNo()));

					User savedUser = userRepository.save(toUpdateUser);

					log.info("<STUDENT><STUDENT:UPDATE>"
							+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
							+ "<Student : " + savedStudent.getName() + " updated with new User Name " + savedUser.getUsername() + ">");
				} else {

					log.info("<STUDENT><STUDENT:UPDATE>"
							+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
							+ "<Student : " + toSaveStudent.getName() +" updated, Username not found to update >");
				}

				return Optional.of(studentConverter.convert(savedStudent));
			}
		}

		log.info("<STUDENT><STUDENT:UPDATE>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<Student : " + studentOptional.get().getName() +" not updated, Student not found to update >");

		return Optional.empty();
	}

	Optional<StudentDto> delete(HttpServletRequest request, StudentDto studentDto){
		Student student = studentConverter.convert(studentDto);
		studentRepository.deleteById(student.getStudentId());
		Optional<Student> studentOptional = studentRepository.findById(student.getStudentId());

		if(studentOptional.isPresent()) {

			log.info("<STUDENT><STUDENT:DELETE>"
					+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
					+ "<" + studentOptional.get().getName() +" : not deleted>");

			return Optional.of(studentConverter.convert(studentOptional.get()));
		}

		// Delete Username
		Optional<User> userOptional =
				userRepository.findByUsername(studentDto.getRollNo());

		if(userOptional.isPresent()) {
			userRepository.deleteById(userOptional.get().getUserId());

			log.info("<STUDENT><STUDENT:DELETE>"
					+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
					+ "<Student : " + studentDto.getName() +" deleted with user>");
		} else {

			log.info("<STUDENT><STUDENT:DELETE>"
					+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
					+ "<Student : " + studentDto.getName() +" deleted, Username not found to delete >");
		}

		return Optional.empty();
	}

	Pair search(HttpServletRequest request, StudentDto studentDto) {

		String message = "";

		Student student = new Student();
		BeanUtils.copyProperties(studentDto, student);

		List<StudentDto> studentDtos = new ArrayList<>();

		if(student.getName().isEmpty() && !student.getRollNo().isEmpty()) {

			Optional<StudentDto> studentOptional = findByType(StrConstants.ROLL_NO,
					student.getRollNo());

			if(studentOptional.isPresent()) {
				studentDtos.add(studentOptional.get());
			}

			message = "Search results based on Roll No.: '"+ studentDto.getRollNo()+"'";

		} else if(!student.getName().isEmpty() ) {

			List<Student> studentList;

			if(student.getRollNo().isEmpty()){
				studentList = studentRepository.findByNameContains(student.getName());
				message = "Search results based on Name: '"+ studentDto.getName() + "'";
			} else {
				studentList =
						studentRepository.findByRollNoAndNameContains(student.getRollNo(),
						student.getName());
				message = "Search results based on Name: '"+ studentDto.getName() + "' " +
						"and Roll No.: '"+ studentDto.getRollNo()+"'";
			}

			if(!studentList.isEmpty()) {
				studentList.forEach(stud -> studentDtos.add(studentConverter.convert(stud)));
			}
		}

		if(studentDtos.isEmpty()) {
			message = message + " No records found based on entered data.";
		}

		log.info("<STUDENT><STUDENT:SEARCH>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<" + message +" >");

		return Pair.of(message, studentDtos);
	}


	@Autowired
	public void setBatchRepository(BatchRepository batchRepository) {
		this.batchRepository = batchRepository;
	}

	@Autowired
	public void setBranchRepository(BranchRepository branchRepository) {
		this.branchRepository = branchRepository;
	}

	@Autowired
	public void setYearRepository(YearRepository yearRepository) {
		this.yearRepository = yearRepository;
	}

	@Autowired
	public void setAcademicYearRepository(AcademicYearRepository academicYearRepository) {
		this.academicYearRepository = academicYearRepository;
	}

	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Autowired
	public void setBloodGroupRepository(BloodGroupRepository bloodGroupRepository) {
		this.bloodGroupRepository = bloodGroupRepository;
	}

	@Autowired
	public void setStudentConverter(StudentConverter studentConverter) {
		this.studentConverter = studentConverter;
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
