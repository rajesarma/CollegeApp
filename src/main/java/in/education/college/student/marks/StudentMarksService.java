package in.education.college.student.marks;

import in.education.college.converter.StudentMarksConverter;
import in.education.college.dto.StudentMarksDto;
import in.education.college.model.ExamType;
import in.education.college.model.Student;
import in.education.college.model.StudentMarks;
import in.education.college.model.Subject;
import in.education.college.model.repository.ExamTypeRepository;
import in.education.college.model.repository.StudentMarksRepository;
import in.education.college.model.repository.SubjectRepository;
import in.education.college.student.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentMarksService {

	private StudentRepository studentRepository;
	private StudentMarksRepository studentMarksRepository;
	private StudentMarksConverter studentMarksConverter;
	private SubjectRepository subjectRepository;
	private ExamTypeRepository examTypeRepository;

	@Autowired
	StudentMarksService(
			final StudentMarksRepository studentMarksRepository,
			final StudentRepository studentRepository,
			final SubjectRepository subjectRepository,
			final ExamTypeRepository examTypeRepository,
			final StudentMarksConverter studentMarksConverter) {
		this.studentMarksRepository = studentMarksRepository;
		this.studentRepository = studentRepository;
		this.subjectRepository = subjectRepository;
		this.examTypeRepository = examTypeRepository;
		this.studentMarksConverter = studentMarksConverter;
	}

	Map<Long,String> getExamTypes() {

		return StreamSupport.stream(examTypeRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(ExamType::getExamTypeId,
						ExamType::getExamType));
	}

	public Map<Long,String> getSubjects() {

		return StreamSupport.stream(subjectRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(Subject::getSubjectId,
						Subject::getSubjectName));
	}

	Optional<Map<Long,String>> getSubjectsByBranchIdAndSemesterId(String branchId, long semesterId) {

		List<Subject> subjects =
				subjectRepository.findAllByBranchIdAndSemesterId(branchId, semesterId);

		if(!subjects.isEmpty()) {
			return Optional.of(subjects
					.stream()
					.collect(Collectors.toMap(Subject::getSubjectId, Subject::getSubjectName)));
		}

		return Optional.empty();
	}

	Optional<Map<Long,String>> getExamTypesBySubjectId(long subjectId) {

		List<ExamType> examTypes =
				examTypeRepository.findAllBySubjectId(subjectId);

		if(!examTypes.isEmpty()) {

			return Optional.of(examTypes
					.stream()
					.collect(Collectors.toMap(ExamType::getExamTypeId, ExamType::getExamType)));

		}

		return Optional.empty();
	}

	List<StudentMarksDto> getStudentsMarksList(StudentMarksDto studentMarks){

		List<StudentMarksDto> studentMarksDtos = new ArrayList<>();
		List<Student> notInMarksList;

		List<StudentMarks> inMarkss =
			studentMarksRepository.findAllByBatchIdAndBranchIdAndYearIdAndSemesterIdAndSubjectIdAndExamTypeId(
					studentMarks.getBatchId(),
					studentMarks.getBranchId(),
					studentMarks.getYearId(),
					studentMarks.getSemesterId(),
					studentMarks.getSubjectId(),
					studentMarks.getExamTypeId()
			);

		List<Long> marksStudentIds =
				inMarkss.stream().map(StudentMarks::getStudentId).collect(Collectors.toList());

		if(!marksStudentIds.isEmpty()) {
			notInMarksList =
					studentRepository.findAllByBatchIdAndBranchIdAndCurrentYearIdAndCurrentSemesterIdAndStudentIdIsNotIn(
							studentMarks.getBatchId(),
							studentMarks.getBranchId(),
							studentMarks.getYearId(),
							studentMarks.getSemesterId(),
							marksStudentIds);

//			notInMarksList.forEach(student -> { System.out.println(student.getStudentId());});

		} else {
			notInMarksList =
					studentRepository.findByBatchIdAndBranchIdAndCurrentYearIdAndCurrentSemesterId(
					studentMarks.getBatchId(),
					studentMarks.getBranchId(),
					studentMarks.getYearId(),
					studentMarks.getSemesterId()
			);
		}

		List<StudentMarks> notInMarkss =
				notInMarksList.stream().map(student -> studentMarksConverter.convertToStudentMarks(student, studentMarks.getSubjectId(),
						studentMarks.getExamTypeId())).collect(Collectors.toList());

		notInMarkss.addAll(inMarkss);

		if(!notInMarkss.isEmpty()) {
			notInMarkss.forEach(student -> studentMarksDtos.add(studentMarksConverter.convert(student)));
		}

		/*studentMarksDtos.sort(Comparator.comparing(StudentMarksDto :: getEmpNo)
				.thenComparing(StudentMarksDto :: getStudentName));*/

		studentMarksDtos.sort(Comparator.comparing(StudentMarksDto :: getRollNo));

		return studentMarksDtos;
	}

	List<StudentMarksDto> saveAll(List<StudentMarksDto> studentMarksDtos) {

		List<StudentMarks> studentMarkss = new ArrayList<>();

		// Save only new records
		studentMarksDtos.stream()
				.filter(studentMarksDto -> studentMarksDto.getStudentMarksId() == 0)
				.filter(studentMarksDto -> studentMarksDto.getMarks() > 0)
				.filter(studentMarksDto -> studentMarksDto.getMaxMarks() > 0)
				.filter(studentMarksDto -> studentMarksDto.getMaxMarks() >= studentMarksDto.getMarks())
				.forEach(student -> studentMarkss.add(studentMarksConverter.convert(student)));

		Iterable<StudentMarks> updatedStudentMarkss =
				studentMarksRepository.saveAll(studentMarkss);

		return StreamSupport.stream(updatedStudentMarkss.spliterator(), false)
				.map(stud -> studentMarksConverter.convert(stud))
				.collect(Collectors.toList());
	}

	Optional<StudentMarksDto> findById(Long studentMarksId) {
		Optional<StudentMarks> studentMarksOptional =
				studentMarksRepository.findById(studentMarksId);
		if(studentMarksOptional.isPresent()) {
			StudentMarksDto studentMarksDto =
					studentMarksConverter.convert(studentMarksOptional.get());
			return Optional.of(studentMarksDto);
		}

		return Optional.empty();
	}

	Optional<StudentMarksDto> update(StudentMarksDto studentMarksDto){

		StudentMarks studentMarks = studentMarksConverter.convert(studentMarksDto);
		Optional<StudentMarks> studentMarksOptional =
				studentMarksRepository.findById(studentMarksDto.getStudentMarksId());

		if(studentMarksOptional.isPresent()) {
			StudentMarks toSaveStudentMarks = studentMarksOptional.get();
			BeanUtils.copyProperties(studentMarks, toSaveStudentMarks);

			StudentMarks savedStudentMarks =
					studentMarksRepository.save(toSaveStudentMarks);

			if(studentMarksRepository.existsById(savedStudentMarks.getStudentMarksId())) {
				return Optional.of(studentMarksConverter.convert(savedStudentMarks));
			}
		}
		return Optional.empty();
	}

	Optional<StudentMarksDto> delete(StudentMarksDto studentMarksDto){
		StudentMarks studentMarks = studentMarksConverter.convert(studentMarksDto);

		studentMarksRepository.deleteById(studentMarks.getStudentMarksId());

		Optional<StudentMarks> studentMarksOptional =
				studentMarksRepository.findById(studentMarks.getStudentMarksId());

		if(studentMarksOptional.isPresent()) {
			return Optional.of(studentMarksConverter.convert(studentMarksOptional.get()));
		}

		return Optional.empty();
	}
}
