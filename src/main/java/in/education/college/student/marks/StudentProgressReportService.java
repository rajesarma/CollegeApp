package in.education.college.student.marks;

import in.education.college.common.util.Constants.StrConstants;
import in.education.college.model.Batch;
import in.education.college.model.Branch;
import in.education.college.model.Semester;
import in.education.college.model.Student;
import in.education.college.model.Year;
import in.education.college.model.repository.BatchRepository;
import in.education.college.model.repository.BranchRepository;
import in.education.college.model.repository.StudentMarksRepository;
import in.education.college.model.repository.YearRepository;
import in.education.college.student.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentProgressReportService {

	private BatchRepository batchRepository;
	private BranchRepository branchRepository;
	private YearRepository yearRepository;
	private StudentRepository studentRepository;
	private StudentMarksRepository studentMarksRepository;

	private static final Logger log = LoggerFactory.getLogger(StudentProgressReportService.class);

	Map<Long,String> getBatches() {

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

	Optional<Map<Long,String>> getSemestersByYearId(long yearId) {

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

	Optional<Map<Long,String>> getStudentsByBatchIdAndBranchIdAndYearIdAndSemesterId(
			long batchId, String branchId, long yearId, long semesterId ) {

		List<Student> studentsList =
				studentRepository.findByBatchIdAndBranchIdAndJoiningYearIdAndJoiningSemesterId(
						batchId, branchId, yearId, semesterId);

		if(!studentsList.isEmpty()) {

			Map<Long,String> studentMap = studentsList.stream()
											.collect(Collectors.toMap(Student::getStudentId, Student::getName));
			return Optional.of(studentMap);
		}

		return Optional.empty();
	}

	Pair list(HttpServletRequest request, String branchId, long studentId,
			long semesterId) {

		List<Map<String, Double>> studentsMarksTotals = new ArrayList<>();
		Map<String, Double> totalsMap = new HashMap<>();

		List<Map<String, String>> studentsMarksList =
				studentMarksRepository.studentProgressReportByBranchIdSemesterIdStudentId(branchId, studentId, semesterId);

		for (Map<String, String> studentMarks : studentsMarksList) {
			for(Entry<String, String> entry : studentMarks.entrySet()) {

				if(!entry.getKey().equalsIgnoreCase("subject_name") && !entry.getKey().equalsIgnoreCase("subject_id") ) {

					totalsMap.putIfAbsent(entry.getKey(), 0.0);

					totalsMap.put(entry.getKey(),
							totalsMap.get(entry.getKey()) + Double.parseDouble(entry.getValue()));
				}
			}
		}

		studentsMarksTotals.add(totalsMap);

		String logMessage = "<STUDENT_PROGRESS_REPORT><STUDENT:MARKS_LIST>"
				+ "<User:" + request.getSession().getAttribute(StrConstants.SESSION_USER_NAME) + ">"
				+ "<Record count: " + studentsMarksList.size() + ">";

		log.info(logMessage);

		return Pair.of(studentsMarksList, studentsMarksTotals);
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
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Autowired
	public void setStudentMarksRepository(StudentMarksRepository studentMarksRepository) {
		this.studentMarksRepository = studentMarksRepository;
	}
}
