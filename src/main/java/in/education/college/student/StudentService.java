package in.education.college.student;

import in.education.college.converter.StudentConverter;
import in.education.college.dto.StudentDto;
import in.education.college.model.AcademicYear;
import in.education.college.model.Batch;
import in.education.college.model.BloodGroup;
import in.education.college.model.Branch;
import in.education.college.model.Semester;
import in.education.college.model.Student;
import in.education.college.model.Year;
import in.education.college.model.repository.AcademicYearRepository;
import in.education.college.model.repository.BatchRepository;
import in.education.college.model.repository.BloodGroupRepository;
import in.education.college.model.repository.BranchRepository;
import in.education.college.model.repository.SemesterRepository;
import in.education.college.model.repository.YearRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

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
	private SemesterRepository semesterRepository;
	private AcademicYearRepository academicYearRepository;
	private StudentRepository studentRepository;
	private BloodGroupRepository bloodGroupRepository;
	private StudentConverter studentConverter;


	@Autowired
	StudentService(
			final BatchRepository batchRepository,
			final BranchRepository branchRepository,
			final YearRepository yearRepository,
			final SemesterRepository semesterRepository,
			final AcademicYearRepository academicYearRepository,
			final StudentRepository studentRepository,
			final BloodGroupRepository bloodGroupRepository,
			final StudentConverter studentConverter) {
		this.batchRepository = batchRepository;
		this.branchRepository = branchRepository;
		this.yearRepository = yearRepository;
		this.semesterRepository = semesterRepository;
		this.academicYearRepository = academicYearRepository;
		this.studentRepository = studentRepository;
		this.bloodGroupRepository = bloodGroupRepository;
		this.studentConverter = studentConverter;
	}

	private String rollNo="RollNo.";
	private String mobileNo="mobileNo.";
	private String aadharNo="aadharNo.";
	private String email="Email";

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

			return  Optional.of(yearOptional.get()
					.getSemseters()
					.stream()
					.collect(Collectors.toMap(Semester::getSemesterId,
							Semester::getSemesterName)));
		}

		return Optional.empty();
	}

	Optional<StudentDto> save(StudentDto studentDto) {

		Student student = studentConverter.convert(studentDto);
		Student savedStudent = studentRepository.save(student);

		if(savedStudent.getStudentId() > 0) {
			return Optional.of(studentConverter.convert(savedStudent));
		}

		return Optional.empty();
	}

	List<StudentDto> list(String branchId, long batchId, long joiningYearId) {

		List<Student> studentsList = new ArrayList<>();
		List<StudentDto> studentDtos = new ArrayList<>();

		boolean isBranchSelected = false;
		boolean isBatchSelected = false;
		boolean isJoiningYearIdSelected = false;

		if(branchId !=null && branchId.length() > 0 && !branchId.equals("0")) {
			isBranchSelected = true;
		}
		if(batchId > 0) { isBatchSelected = true; }
		if(joiningYearId > 0) { isJoiningYearIdSelected = true; }

		if(isBranchSelected && isBatchSelected && isJoiningYearIdSelected) {
			studentsList =
					studentRepository.findByBranchIdAndBatchIdAndJoiningYearId(branchId,
					batchId, joiningYearId);
		} else if(isBranchSelected && isBatchSelected) {
			studentsList = studentRepository.findByBranchIdAndBatchId(branchId,
					batchId);
		} else if(isBranchSelected && isJoiningYearIdSelected) {
			studentsList = studentRepository.findByBranchIdAndJoiningYearId(branchId,
					joiningYearId);
		} else if(isBatchSelected && isJoiningYearIdSelected) {
			studentsList =
					studentRepository.findByBatchIdAndJoiningYearId(batchId, joiningYearId);
		} else if(isBranchSelected) {
			studentsList =
					studentRepository.findByBranchId(branchId);
		} else if(isBatchSelected) {
			studentsList =
					studentRepository.findByBatchId(batchId);
		} else if(isJoiningYearIdSelected) {
			studentsList =
					studentRepository.findByJoiningYearId(joiningYearId);
		}

		if(!studentsList.isEmpty()) {
			studentsList.forEach(student -> studentDtos.add(studentConverter.convert(student)));
//			setBranchYearBatch(studentDtos);
		}

		return studentDtos;
	}

	Optional<StudentDto> findByRollNo(String rollNo) {

		Optional<Student> studentOptional = studentRepository.findByRollNo(rollNo);

		if(studentOptional.isPresent()) {
			return Optional.of(studentConverter.convert(studentOptional.get()));
		}
		return Optional.empty();

		/*Optional studentOptional =
				Optional.ofNullable(studentRepository.findByRollNo(rollNo));
		StudentDto studentDto = StudentConverter.convert((Student)studentOptional.get());
		return Optional.ofNullable(studentDto);*/
	}

	Optional<StudentDto> findByType(String type, String value) {

		Optional<Student> studentOptional = null;

		if(type.equalsIgnoreCase(rollNo)) {
			studentOptional = studentRepository.findByRollNo(value);
		} else if(type.equalsIgnoreCase(aadharNo)) {
			studentOptional = studentRepository.findFirstByAadharNo(value);
		} else if(type.equalsIgnoreCase(mobileNo)) {
			studentOptional = studentRepository.findFirstByMobileNo(value);
		} else if(type.equalsIgnoreCase(email)) {
			studentOptional = studentRepository.findFirstByEmail(value);
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

	Optional<StudentDto> update(StudentDto studentDto){

		Student student = studentConverter.convert(studentDto);
		Optional<Student> studentOptional = studentRepository.findById(student.getStudentId());

		if(studentOptional.isPresent()) {
			Student toSaveStudent = studentOptional.get();
			BeanUtils.copyProperties(student, toSaveStudent);
			Student savedStudent = studentRepository.save(toSaveStudent);

//			if(savedStudent.getStudentId() > 0 ) {
			if(studentRepository.existsById(savedStudent.getStudentId())) {
				return Optional.of(studentConverter.convert(savedStudent));
			}
		}
		return Optional.empty();
	}

	Optional<StudentDto> delete(StudentDto studentDto){
		Student student = studentConverter.convert(studentDto);
		studentRepository.deleteById(student.getStudentId());
		Optional<Student> studentOptional = studentRepository.findById(student.getStudentId());

		if(studentOptional.isPresent()) {
			return Optional.of(studentConverter.convert(studentOptional.get()));
		}

		return Optional.empty();
	}

	Pair search(StudentDto studentDto) {

		String message = "";

		Student student = new Student();
		BeanUtils.copyProperties(studentDto, student);

		List<StudentDto> studentDtos = new ArrayList<>();

		if(student.getName().isEmpty() && !student.getRollNo().isEmpty()) {

//			Optional<StudentDto> studentOptional = findByRollNo(student.getRollNo());
			Optional<StudentDto> studentOptional = findByType(rollNo,
					student.getRollNo());

			if(studentOptional.isPresent()) {
				studentDtos.add(studentOptional.get());
			}

//			setBranchYearBatch(studentDtos);

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
//				setBranchYearBatch(studentDtos);
			}
		}

		if(studentDtos.isEmpty()) {
			message = message + " No records found based on entered data.";
		}

		return Pair.of(message, studentDtos);
	}

	/*private void setBranchYearBatch(List<StudentDto> studentDtos){
		studentDtos.forEach(student -> student.setBatch(batchRepository.findById(student
				.getBatchId()).get().getBatchName()));

		studentDtos.forEach(student -> student.setBranch(branchRepository.findById(student.getBranchId()).get().getBranchName()));

		studentDtos.forEach(student -> student.setYear(yearRepository.findById(student
				.getJoiningYearId()).get().getYear()));

		studentDtos.forEach(student -> student.setSemester(semesterRepository.findById(student
				.getJoiningSemesterId()).get().getSemesterName()));
	}*/

	/*studentDtos.stream()
					.forEach(student -> student.setBloodGroup(bloodGroupRepository.findById(student.getBloodGroupId()).get().getBloodGroup()));*/

}
