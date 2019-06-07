package in.education.college.student.marks;

import in.education.college.dto.StudentMarksDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value="/super")
public class StudentProgressReportController {

	private StudentProgressReportService studentProgressReportService;

	private static String Role = "/super";

	@Autowired
	StudentProgressReportController(final StudentProgressReportService studentProgressReportService ) {
		this.studentProgressReportService = studentProgressReportService;
	}

	private String role = "Role";
	private String message = "";
	private String method= "method";

	private void getData(ModelAndView mav) {
		mav.addObject("batches", studentProgressReportService.getBatches());
		mav.addObject("branches", studentProgressReportService.getBranches());
		mav.addObject("years", studentProgressReportService.getYears());
	}

	@GetMapping("/student/studentProgressReport")
	public ModelAndView list() {

		ModelAndView mav = new ModelAndView("studentProgressReport", "studentMarksDto",
				new StudentMarksDto());
		getData(mav);

		mav.addObject(role, Role);
		mav.addObject(method,"POST");
		return mav;
	}

	@PostMapping("/student/studentProgressReport")
	public ModelAndView list(@ModelAttribute("studentDto") StudentMarksDto studentMarksDto,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("studentProgressReport", "studentMarksDto",
				studentMarksDto);
		getData(mav);
		mav.addObject(role, Role);
		mav.addObject(method,"POST");

		Pair<List<Map<String, String>>, List<Map<String, String>>> studentMarksData = studentProgressReportService.list(request,
				studentMarksDto.getBranchId(),
				studentMarksDto.getStudentId(),
				studentMarksDto.getSemesterId());

		List<Map<String, String>> studentsMarksList = studentMarksData.getFirst();

		List<Map<String, String>> studentsMarksTotals = studentMarksData.getSecond();

		if(studentsMarksList.isEmpty()) {
			mav.addObject("message", "No records found based on your selection");
		}

		mav.addObject("studentsMarksList", studentsMarksList);
		mav.addObject("studentsMarksTotals", studentsMarksTotals);

		mav.addObject("selectedBatchId", studentMarksDto.getBatchId());
		mav.addObject("selectedBranchId", studentMarksDto.getBranchId());
		mav.addObject("selectedYearId", studentMarksDto.getYearId());
		mav.addObject("selectedSemesterId", studentMarksDto.getSemesterId());
		mav.addObject("selectedStudentId", studentMarksDto.getStudentId());

		Optional<Map<Long,String>> semestersOptional =
				studentProgressReportService.getSemestersByYearId(studentMarksDto.getYearId());

		if(semestersOptional.isPresent()) {
			mav.addObject("semesters", semestersOptional.get());
		}

		Optional<Map<Long,String>> studentsOptional =
				studentProgressReportService.getStudentsByBatchIdAndBranchIdAndYearIdAndSemesterId(
						studentMarksDto.getBatchId(),
						studentMarksDto.getBranchId(),
						studentMarksDto.getYearId(),
						studentMarksDto.getSemesterId()
				);

		if(studentsOptional.isPresent()) {
			mav.addObject("students", studentsOptional.get());
		}

		return mav;
	}

	// Ajax request
	@GetMapping(value = "/student/studentProgressReport/{type}/{batchId}/{branchId}/{yearId}/{semesterId}")
	public ResponseEntity<?> find(@PathVariable("type") String type,
			@PathVariable("batchId") String batchId,
			@PathVariable("branchId") String branchId,
			@PathVariable("yearId") String yearId,
			@PathVariable("semesterId") String semesterId) {

		String result = null;

		if(type.equalsIgnoreCase("getSemesters")) {

			Optional<Map<Long,String>> semestersOptional =
					studentProgressReportService.getSemestersByYearId(Long.parseLong(yearId));

			if(semestersOptional.isPresent()) {

				result =
						"{\"semestersExists\":\"true\", \"semesters\":\""+semestersOptional.get()+"\" }";
			} else {
				result = "{\"semestersExists\":\"false\" }";
			}
		} else if(type.equalsIgnoreCase("getStudents")) {

			Optional<Map<Long,String>> studentsOptional =
					studentProgressReportService.getStudentsByBatchIdAndBranchIdAndYearIdAndSemesterId(
							Long.parseLong(batchId),
							branchId,
							Long.parseLong(yearId),
							Long.parseLong(semesterId)
					);

			if(studentsOptional.isPresent()) {

				result =
						"{\"studentsExists\":\"true\", \"students\":\""+studentsOptional.get()+"\" }";
			} else {
				result = "{\"studentsExists\":\"false\" }";
			}
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
