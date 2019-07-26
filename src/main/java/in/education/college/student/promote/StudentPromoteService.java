package in.education.college.student.promote;

import in.education.college.converter.StudentConverter;
import in.education.college.dto.StudentDto;
import in.education.college.dto.StudentPromoteForm;
import in.education.college.model.Student;
import in.education.college.model.repository.StudentAttendanceRepository;
import in.education.college.model.repository.StudentMarksRepository;
import in.education.college.student.StudentRepository;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentPromoteService {

	private StudentRepository studentRepository;
	private StudentAttendanceRepository studentAttendanceRepository;
	private StudentMarksRepository studentMarksRepository;
	private StudentConverter studentConverter;

	@Autowired
	StudentPromoteService(
			final StudentRepository studentRepository,
			final StudentAttendanceRepository studentAttendanceRepository,
			final StudentMarksRepository studentMarksRepository,
			final StudentConverter studentConverter) {
		this.studentRepository = studentRepository;
		this.studentAttendanceRepository = studentAttendanceRepository;
		this.studentMarksRepository = studentMarksRepository;
		this.studentConverter = studentConverter;
	}

	@Transactional
	List<StudentDto> getStudentsList(StudentPromoteForm studentPromoteForm){

		List<Student> students = studentRepository.findByBatchIdAndBranchIdAndCurrentYearIdAndCurrentSemesterId(
				studentPromoteForm.getBatchId(),
				studentPromoteForm.getBranchId(),
				studentPromoteForm.getYearId(),
				studentPromoteForm.getSemesterId()
		);

		List<StudentDto> studentDtos = students.stream().map(student -> studentConverter.convert(student)).collect(Collectors.toList());

		return studentDtos;
	}

	Triple<Integer, List<Long>, List<Long>> promoteStudents(List<Long> studentIds) {

		// Check if attendance and marks are posted or not
		int updateCount = 0;

		List<Long> notEligibleStudentIds = new ArrayList<>();
		List<Long> eligibleStudentIds = new ArrayList<>();

		for(Long studentId : studentIds) {
			long studId = studentAttendanceRepository.findStudentByCurrentSemesterIdAndCurrentYearId(studentId);

			if (studId != 0) {
				long subjectCount = studentMarksRepository.findStudentByCurrentSemesterIdAndCurrentYearId(studId);
				if (subjectCount > 0) {
					eligibleStudentIds.add(studId);
				} else {
					notEligibleStudentIds.add(studId);
				}
			} else {
				notEligibleStudentIds.add(studId);
			}
		}

		if(eligibleStudentIds.size() > 0) {
			updateCount = studentRepository.updateCurrentYearIdAndCurrentSemesterId(eligibleStudentIds);
		}

		return Triple.of(updateCount, eligibleStudentIds, notEligibleStudentIds);
	}

	/*
	@Transactional
	Optional<StudentAttendanceDto> findById(Long studentAttendanceId) {
		Optional<StudentAttendance> studentAttendanceOptional =
				studentAttendanceRepository.findById(studentAttendanceId);
		if(studentAttendanceOptional.isPresent()) {
			StudentAttendanceDto studentAttendanceDto =
					studentAttendanceConverter.convert(studentAttendanceOptional.get());
			return Optional.of(studentAttendanceDto);
		}

		return Optional.empty();
	}

	@Transactional
	Optional<StudentAttendanceDto> update(StudentAttendanceDto studentAttendanceDto){

		StudentAttendance studentAttendance = studentAttendanceConverter.convert(studentAttendanceDto);
		Optional<StudentAttendance> studentAttendanceOptional =
				studentAttendanceRepository.findById(studentAttendanceDto.getStudentAttendanceId());

		if(studentAttendanceOptional.isPresent()) {
			StudentAttendance toSaveStudentAttendance = studentAttendanceOptional.get();
			BeanUtils.copyProperties(studentAttendance, toSaveStudentAttendance);
			StudentAttendance savedStudentAttendance =
					studentAttendanceRepository.save(toSaveStudentAttendance);

			if(studentAttendanceRepository.existsById(savedStudentAttendance.getStudentAttendanceId())) {
				return Optional.of(studentAttendanceConverter.convert(savedStudentAttendance));
			}
		}
		return Optional.empty();
	}

	@Transactional
	Optional<StudentAttendanceDto> delete(StudentAttendanceDto studentAttendanceDto){
		StudentAttendance studentAttendance = studentAttendanceConverter.convert(studentAttendanceDto);

		studentAttendanceRepository.deleteById(studentAttendance.getStudentAttendanceId());

		Optional<StudentAttendance> studentAttendanceOptional =
				studentAttendanceRepository.findById(studentAttendance.getStudentAttendanceId());

		if(studentAttendanceOptional.isPresent()) {
			return Optional.of(studentAttendanceConverter.convert(studentAttendanceOptional.get()));
		}

		return Optional.empty();
	}*/
}
