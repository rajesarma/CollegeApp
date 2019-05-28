package in.education.college.converter;

import in.education.college.dto.StudentAttendanceDto;
import in.education.college.model.Batch;
import in.education.college.model.Branch;
import in.education.college.model.Semester;
import in.education.college.model.Student;
import in.education.college.model.StudentAttendance;
import in.education.college.model.Year;
import in.education.college.model.repository.BatchRepository;
import in.education.college.model.repository.BranchRepository;
import in.education.college.model.repository.SemesterRepository;
import in.education.college.model.repository.YearRepository;
import in.education.college.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentAttendanceConverter {

	private BranchRepository branchRepository;
	private BatchRepository batchRepository;
	private YearRepository yearRepository;
	private SemesterRepository semesterRepository;
	private StudentRepository studentRepository;

	@Autowired
	StudentAttendanceConverter(
			final BranchRepository branchRepository,
			final BatchRepository batchRepository,
			final YearRepository yearRepository,
			final SemesterRepository semesterRepository,
			final StudentRepository studentRepository) {
		this.branchRepository = branchRepository;
		this.batchRepository = batchRepository;
		this.yearRepository = yearRepository;
		this.semesterRepository = semesterRepository;
		this.studentRepository = studentRepository;
	}

	public StudentAttendance convert(final StudentAttendanceDto studentAttendanceDto) {

		StudentAttendance studentAttendance = new StudentAttendance();
		studentAttendance.setStudentAttendanceId(studentAttendanceDto.getStudentAttendanceId());
		studentAttendance.setBatchId(studentAttendanceDto.getBatchId());
		studentAttendance.setBranchId(studentAttendanceDto.getBranchId());
		studentAttendance.setYearId(studentAttendanceDto.getYearId());
		studentAttendance.setSemesterId(studentAttendanceDto.getSemesterId());
		studentAttendance.setStudentId(studentAttendanceDto.getStudentId());
		studentAttendance.setNoOfDays(studentAttendanceDto.getNoOfDays());
		studentAttendance.setDaysPresent(studentAttendanceDto.getDaysPresent());

		return studentAttendance;
	}

	public StudentAttendanceDto convert(final StudentAttendance studentAttendance) {

		StudentAttendanceDto studentAttendanceDto = new StudentAttendanceDto();
		studentAttendanceDto.setStudentAttendanceId(studentAttendance.getStudentAttendanceId());
		studentAttendanceDto.setBatchId(studentAttendance.getBatchId());
		studentAttendanceDto.setBranchId(studentAttendance.getBranchId());
		studentAttendanceDto.setYearId(studentAttendance.getYearId());
		studentAttendanceDto.setSemesterId(studentAttendance.getSemesterId());
		studentAttendanceDto.setStudentId(studentAttendance.getStudentId());
		studentAttendanceDto.setNoOfDays(studentAttendance.getNoOfDays());
		studentAttendanceDto.setDaysPresent(studentAttendance.getDaysPresent());

		Optional<Batch> batchOptional  =
				batchRepository.findById(studentAttendance.getBatchId());
		if(batchOptional.isPresent()) {
			studentAttendanceDto.setBatch(batchOptional.get().getBatchName());
		}

		Optional<Branch> branchOptional  =
				branchRepository.findById(studentAttendance.getBranchId());
		if(branchOptional.isPresent()) {
			studentAttendanceDto.setBranch(branchOptional.get().getBranchName());
		}

		Optional<Year> yearOptional  =
				yearRepository.findById(studentAttendance.getYearId());
		if(yearOptional.isPresent()) {
			studentAttendanceDto.setYear(yearOptional.get().getYear());
		}

		Optional<Semester> semesterOptional  =
				semesterRepository.findById(studentAttendance.getSemesterId());
		if(semesterOptional.isPresent()) {
			studentAttendanceDto.setSemester(semesterOptional.get().getSemesterName());
		}

		Optional<Student> studentOptional  =
				studentRepository.findById(studentAttendance.getStudentId());
		if(studentOptional.isPresent()) {

			studentAttendanceDto.setName(studentOptional.get().getName());
			studentAttendanceDto.setRollNo(studentOptional.get().getRollNo());
		}

		return studentAttendanceDto;
	}

	public StudentAttendance convertToStudentAttendance(final Student student) {

		StudentAttendance studentAttendance = new StudentAttendance();
		studentAttendance.setBatchId(student.getBatchId());
		studentAttendance.setBranchId(student.getBranchId());
		studentAttendance.setYearId(student.getCurrentYearId());
		studentAttendance.setSemesterId(student.getCurrentSemesterId());
		studentAttendance.setStudentId(student.getStudentId());
		studentAttendance.setNoOfDays(0);
		studentAttendance.setDaysPresent(0);
		return studentAttendance;
	}

}

