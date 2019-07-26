package in.education.college.student.attendance;

import in.education.college.converter.StudentAttendanceConverter;
import in.education.college.dto.StudentAttendanceDto;
import in.education.college.model.Student;
import in.education.college.model.StudentAttendance;
import in.education.college.model.repository.StudentAttendanceRepository;
import in.education.college.student.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentAttendanceService {

	private StudentRepository studentRepository;
	private StudentAttendanceRepository studentAttendanceRepository;
	private StudentAttendanceConverter studentAttendanceConverter;

	@Autowired
	StudentAttendanceService(
			final StudentAttendanceRepository studentAttendanceRepository,
			final StudentRepository studentRepository,
			final StudentAttendanceConverter studentAttendanceConverter) {
		this.studentAttendanceRepository = studentAttendanceRepository;
		this.studentRepository = studentRepository;
		this.studentAttendanceConverter = studentAttendanceConverter;
	}

	@Transactional
	List<StudentAttendanceDto> getStudentsAttendanceList(StudentAttendanceDto studentAttendanceDto){

		StudentAttendance studentAttendance = studentAttendanceConverter.convert(studentAttendanceDto);

		/*List<Map<String, String>> studentAttendanceAverage =
				studentAttendanceRepository.averageByBatchIdAndBranchIdAndYearIdAndSemesterId(studentAttendance.getBatchId(),
				studentAttendance.getBranchId(),
				studentAttendance.getYearId(),
				studentAttendance.getSemesterId());

		System.out.println(studentAttendanceAverage);

		List<Map<String, String>> allStudentAttendanceAverage =
				studentAttendanceRepository.allAverageByBatchIdAndBranchIdAndYearIdAndSemesterId();

		System.out.println(allStudentAttendanceAverage);*/

		List<StudentAttendanceDto> studentAttendanceDtos = new ArrayList<>();
		List<Student> notInAttendanceList;

		List<StudentAttendance> inAttendances =
			studentAttendanceRepository.findAllByBatchIdAndBranchIdAndYearIdAndSemesterId(
					studentAttendance.getBatchId(),
					studentAttendance.getBranchId(),
					studentAttendance.getYearId(),
					studentAttendance.getSemesterId()
			);

		List<Long> attendanceStudentIds =
				inAttendances.stream().map(StudentAttendance::getStudentId).collect(Collectors.toList());

		if(!attendanceStudentIds.isEmpty()) {
			notInAttendanceList =
					studentRepository.findAllByBatchIdAndBranchIdAndCurrentYearIdAndCurrentSemesterIdAndStudentIdIsNotIn(
							studentAttendance.getBatchId(),
							studentAttendance.getBranchId(),
							studentAttendance.getYearId(),
							studentAttendance.getSemesterId(),
							attendanceStudentIds);

//			notInAttendanceList.forEach(student -> { System.out.println(student.getStudentId());});

		} else {
			notInAttendanceList =
					studentRepository.findByBatchIdAndBranchIdAndCurrentYearIdAndCurrentSemesterId(
					studentAttendance.getBatchId(),
					studentAttendance.getBranchId(),
					studentAttendance.getYearId(),
					studentAttendance.getSemesterId()
			);
		}

		List<StudentAttendance> notInAttendances =
				notInAttendanceList.stream().map(student -> studentAttendanceConverter.convertToStudentAttendance(student)).collect(Collectors.toList());

		notInAttendances.addAll(inAttendances);

		if(!notInAttendances.isEmpty()) {
			notInAttendances.forEach(student -> studentAttendanceDtos.add(studentAttendanceConverter.convert(student)));
		}

		/*studentAttendanceDtos.sort(Comparator.comparing(StudentAttendanceDto :: getEmpNo)
				.thenComparing(StudentAttendanceDto :: getStudentName));*/

		studentAttendanceDtos.sort(Comparator.comparing(StudentAttendanceDto :: getRollNo));

		return studentAttendanceDtos;
	}

	@Transactional
	List<StudentAttendanceDto> saveAll(List<StudentAttendanceDto> studentAttendanceDtos) {

		List<StudentAttendance> studentAttendances = new ArrayList<>();

		// Save only new records
		studentAttendanceDtos.stream()
				.filter(studentAttendanceDto -> studentAttendanceDto.getStudentAttendanceId() == 0)
				.filter(studentAttendanceDto -> studentAttendanceDto.getNoOfDays() > 0)
				.filter(studentAttendanceDto -> studentAttendanceDto.getDaysPresent() > 0)
				.filter(studentAttendanceDto -> studentAttendanceDto.getNoOfDays() >= studentAttendanceDto.getDaysPresent())
				.forEach(student -> studentAttendances.add(studentAttendanceConverter.convert(student)));

		Iterable<StudentAttendance> updatedStudentAttendances =
				studentAttendanceRepository.saveAll(studentAttendances);

		return StreamSupport.stream(updatedStudentAttendances.spliterator(), false)
				.map(stud -> studentAttendanceConverter.convert(stud))
				.collect(Collectors.toList());
	}

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
	}
}
