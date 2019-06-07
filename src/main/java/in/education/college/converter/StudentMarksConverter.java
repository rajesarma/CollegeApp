package in.education.college.converter;

import in.education.college.dto.StudentMarksDto;
import in.education.college.model.Batch;
import in.education.college.model.Branch;
import in.education.college.model.ExamType;
import in.education.college.model.Semester;
import in.education.college.model.Student;
import in.education.college.model.StudentMarks;
import in.education.college.model.Subject;
import in.education.college.model.Year;
import in.education.college.model.repository.BatchRepository;
import in.education.college.model.repository.BranchRepository;
import in.education.college.model.repository.ExamTypeRepository;
import in.education.college.model.repository.SemesterRepository;
import in.education.college.model.repository.SubjectRepository;
import in.education.college.model.repository.YearRepository;
import in.education.college.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentMarksConverter {

	private BatchRepository batchRepository;
	private BranchRepository branchRepository;
	private YearRepository yearRepository;
	private SemesterRepository semesterRepository;
	private StudentRepository studentRepository;
	private SubjectRepository subjectRepository;
	private ExamTypeRepository examTypeRepository;

	public StudentMarks convert(final StudentMarksDto studentMarksDto) {

		StudentMarks studentMarks = new StudentMarks();
		studentMarks.setStudentMarksId(studentMarksDto.getStudentMarksId());
		studentMarks.setBatchId(studentMarksDto.getBatchId());
		studentMarks.setBranchId(studentMarksDto.getBranchId());
		studentMarks.setYearId(studentMarksDto.getYearId());
		studentMarks.setSemesterId(studentMarksDto.getSemesterId());
		studentMarks.setStudentId(studentMarksDto.getStudentId());
		studentMarks.setSubjectId(studentMarksDto.getSubjectId());
		studentMarks.setExamTypeId(studentMarksDto.getExamTypeId());
		studentMarks.setMarks(studentMarksDto.getMarks());
		studentMarks.setMaxMarks(studentMarksDto.getMaxMarks());

		return studentMarks;
	}

	public StudentMarksDto convert(final StudentMarks studentMarks) {

		StudentMarksDto studentMarksDto = new StudentMarksDto();
		studentMarksDto.setStudentMarksId(studentMarks.getStudentMarksId());
		studentMarksDto.setBatchId(studentMarks.getBatchId());
		studentMarksDto.setBranchId(studentMarks.getBranchId());
		studentMarksDto.setYearId(studentMarks.getYearId());
		studentMarksDto.setSemesterId(studentMarks.getSemesterId());
		studentMarksDto.setStudentId(studentMarks.getStudentId());
		studentMarksDto.setSubjectId(studentMarks.getSubjectId());
		studentMarksDto.setExamTypeId(studentMarks.getExamTypeId());
		studentMarksDto.setMarks(studentMarks.getMarks());

		Optional<Batch> batchOptional  =
				batchRepository.findById(studentMarks.getBatchId());
		if(batchOptional.isPresent()) {
			studentMarksDto.setBatch(batchOptional.get().getBatchName());
		}

		Optional<Branch> branchOptional  =
				branchRepository.findById(studentMarks.getBranchId());
		if(branchOptional.isPresent()) {
			studentMarksDto.setBranch(branchOptional.get().getBranchName());
		}

		Optional<Year> yearOptional  =
				yearRepository.findById(studentMarks.getYearId());
		if(yearOptional.isPresent()) {
			studentMarksDto.setYear(yearOptional.get().getYear());
		}

		Optional<Semester> semesterOptional  =
				semesterRepository.findById(studentMarks.getSemesterId());
		if(semesterOptional.isPresent()) {
			studentMarksDto.setSemester(semesterOptional.get().getSemesterName());
		}

		Optional<Student> studentOptional  =
				studentRepository.findById(studentMarks.getStudentId());
		if(studentOptional.isPresent()) {

			studentMarksDto.setName(studentOptional.get().getName());
			studentMarksDto.setRollNo(studentOptional.get().getRollNo());
		}

		Optional<Subject> subjectOptional =
				subjectRepository.findById(studentMarks.getSubjectId());
		if(subjectOptional.isPresent()) {
			studentMarksDto.setSubject(subjectOptional.get().getSubjectName());
		}

		Optional<ExamType> examTypeOptional =
				examTypeRepository.findById(studentMarks.getExamTypeId());
		if(examTypeOptional.isPresent()) {
			studentMarksDto.setExamType(examTypeOptional.get().getExamType());
			studentMarksDto.setMaxMarks(examTypeOptional.get().getMaxMarks());
		}

		return studentMarksDto;
	}

	public StudentMarks convertToStudentMarks(final Student student, long subjectId,
			long examTypeId) {

		StudentMarks studentMarks = new StudentMarks();
		studentMarks.setBatchId(student.getBatchId());
		studentMarks.setBranchId(student.getBranchId());
		studentMarks.setYearId(student.getCurrentYearId());
		studentMarks.setSemesterId(student.getCurrentSemesterId());
		studentMarks.setStudentId(student.getStudentId());
		studentMarks.setSubjectId(subjectId);
		studentMarks.setExamTypeId(examTypeId);
		studentMarks.setMarks(0);
		studentMarks.setMaxMarks(0);
		return studentMarks;
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
	public void setSemesterRepository(SemesterRepository semesterRepository) {
		this.semesterRepository = semesterRepository;
	}

	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Autowired
	public void setSubjectRepository(SubjectRepository subjectRepository) {
		this.subjectRepository = subjectRepository;
	}

	@Autowired
	public void setExamTypeRepository(ExamTypeRepository examTypeRepository) {
		this.examTypeRepository = examTypeRepository;
	}
}

