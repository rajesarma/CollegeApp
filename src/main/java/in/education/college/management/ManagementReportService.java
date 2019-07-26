package in.education.college.management;

import in.education.college.common.util.Constants.StrConstants;
import in.education.college.model.Batch;
import in.education.college.model.Branch;
import in.education.college.model.Semester;
import in.education.college.model.Student;
import in.education.college.model.Year;
import in.education.college.model.repository.BatchRepository;
import in.education.college.model.repository.BranchRepository;
import in.education.college.model.repository.EmployeeRepository;
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
public class ManagementReportService {

	private StudentRepository studentRepository;
	private EmployeeRepository employeeRepository;

	private static final Logger log = LoggerFactory.getLogger(ManagementReportService.class);

	List<Map<String,String>> findByBatchWise() {
		return studentRepository.findByBatchWise();
	}

	List<Map<String,String>> findByBranchWise() {
		return studentRepository.findByBranchWise();
	}

	List<Map<String,String>> findByYearWise() {
		return studentRepository.findByYearWise();
	}

	List<Map<String,String>> findByYearSemWise() {
		return studentRepository.findByYearSemWise();
	}

	List<Map<String,String>> findByBatchBranchWise() {
		return studentRepository.findByBatchBranchWise();
	}

	List<Map<String,String>> findByBatchBranchYearWise() {
		return studentRepository.findByBatchBranchYearWise();
	}

	List<Map<String,String>> findByBatchBranchYearSemWise() {
		return studentRepository.findByBatchBranchYearSemWise();
	}

	List<Map<String,String>> findByEmpQlyWise() {
		return employeeRepository.findByEmpQlyWise();
	}

	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Autowired
	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
}
