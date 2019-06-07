package in.education.college.student;

import in.education.college.dto.StudentDto;
import in.education.college.validator.StudentValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
public class StudentController {

	private StudentService studentService;

	@Value("${save}")
	String save;
	@Value("${update}")
	String update;
	@Value("${delete}")
	String delete;

	private static String Role = "/super";

	@Autowired
	StudentController(final StudentService studentService ) {
		this.studentService = studentService;
	}

	private String batches = "batches";
	private String branches = "branches";
	private String years = "years";
	private String academicYears = "academicYears";
	private String bloodGroups = "bloodGroups";
	private String role = "Role";
	private String message = "";

	private void getData(ModelAndView mav) {
		mav.addObject(years, studentService.getYears());
		mav.addObject(bloodGroups, studentService.getBloodGroups());
		mav.addObject(branches, studentService.getBranches());
		mav.addObject(batches, studentService.getBatches());
		mav.addObject(academicYears, studentService.getAcademicYears());
	}


//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVIZOR')")
	@GetMapping(value = "/student/add")
	public ModelAndView save()  {

		ModelAndView mav = new ModelAndView("addStudent", "studentDto", new StudentDto());

		mav.addObject("buttonValue", save );
		mav.addObject("action",Role + "/student/add");
		mav.addObject(role, Role);
		getData(mav);

		return mav;
	}

	@PostMapping("/student/add")
	public ModelAndView save(@ModelAttribute("studentDto") StudentDto studentDto,
			BindingResult bindingResult, HttpServletRequest request) { //@Valid

		ModelAndView mav = new ModelAndView("addStudent", "studentDto", studentDto);
		getData(mav);
		mav.addObject(role, Role);
		mav.addObject("buttonValue", "Save");
		mav.addObject("action", Role + "/student/add");

		new StudentValidator().validate(studentDto, bindingResult);

		if(bindingResult.hasErrors()) {
			mav.addObject("message", "Problem in Saving Student Data");
			return mav;
		}

		Optional<StudentDto> studentDtoOptional = studentService.save(request,
				studentDto);

		if(!studentDtoOptional.isPresent()) {
			mav.addObject("message", "Problem in Saving Student Data");
			return mav;
		}

		mav.addObject("message", "Student Information is added successfully");
		mav.addObject("studentDto", new StudentDto());

		return mav;
	}

	/*@Autowired
	@Qualifier("studentValidator")
	private Validator studentValidator;

	@InitBinder
	private void dataBinding(WebDataBinder binder) {
		binder.setValidator(studentValidator);
	}*/

	@GetMapping("/student/list")
	public ModelAndView list() {

		ModelAndView mav = new ModelAndView("studentList", "studentDto",
				new StudentDto());
		getData(mav);

		mav.addObject(role, Role);
		return mav;
	}

	@PostMapping("/student/list")
	public ModelAndView list(@ModelAttribute("studentDto") StudentDto studentDto, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("studentList");
		getData(mav);
		mav.addObject(role, Role);

		List studentsList = studentService.list(request, studentDto.getBranchId(),
				studentDto.getBatchId(), studentDto.getJoiningYearId());

		if(studentsList.isEmpty()) {
			mav.addObject("message", "No records found based on your selection");
		}

		mav.addObject("studentList", studentsList);

		mav.addObject("selectedBatchId", studentDto.getBatchId());
		mav.addObject("selectedBranchId", studentDto.getBranchId());
		mav.addObject("selectedYearId", studentDto.getJoiningYearId());

		return mav;
	}

	// Ajax request
	@GetMapping(value = "/student/{type}/{value}")
	public ResponseEntity<?> find(@PathVariable("type") String type,
			@PathVariable("value") String value) {

		String result = null;

		if(type.equalsIgnoreCase("getSemesters")) {

			Optional<Map<Long,String>> semestersOptional =
					studentService.getSemesetersByYearId(Long.parseLong(value));

			if(semestersOptional.isPresent()) {

				result =
						"{\"semestersExists\":\"true\", \"semesters\":\""+semestersOptional.get()+"\"  }";
			} else {
				result = "{\"semestersExists\":\"false\" }";
			}
		} else {

			Optional<StudentDto> studentDto = studentService.findByType(type, value);
			String typeString = StringUtils.join(type.split("(?=\\p{Upper})"), " ");

			if(studentDto.isPresent()) {
				result =
						"{\"valueExists\":\"true\", \"message\":\"" + StringUtils.capitalize(typeString) +
						" already exists\"  }";
			} else {
				result = "{\"valueExists\":\"false\" }";
			}
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/student/edit/{studentId}/{operation}")
	public ModelAndView edit(@PathVariable("studentId") long studentId,
			@PathVariable("operation") String operation) {

		ModelAndView mav = new ModelAndView("addStudent", "studentDto", new StudentDto());

		Optional<StudentDto> studentOptional = studentService.findById(studentId);
		if(studentOptional.isPresent()) {
			StudentDto studentDto = studentOptional.get();

			mav.addObject("selectedBatchId", studentDto.getBatchId());
			mav.addObject("selectedBranchId", studentDto.getBranchId());
			mav.addObject("selectedYearId", studentDto.getJoiningYearId());

			mav.addObject("photoData",studentDto.getPhotoData());
			mav.addObject("studentDto", studentDto);

			Optional<Map<Long,String>> joiningSemestersOptional =
					studentService.getSemesetersByYearId(studentDto.getJoiningYearId());

			if(joiningSemestersOptional.isPresent()) {
				mav.addObject("joiningSemesters", joiningSemestersOptional.get());
			}

			Optional<Map<Long,String>> currentSemestersOptional =
					studentService.getSemesetersByYearId(studentDto.getCurrentYearId());

			if(currentSemestersOptional.isPresent()) {
				mav.addObject("currentSemesters", currentSemestersOptional.get());
			}
		}

		mav.addObject(role, Role);
		getData(mav);

		mav.addObject("buttonValue", operation.toUpperCase() );
		mav.addObject("action",Role + "/student/"+operation);

		return mav;
	}

	@PostMapping("/student/update")
	public ModelAndView update(@ModelAttribute("studentDto") StudentDto studentDto,
			BindingResult bindingResult, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("studentList", "studentDto", studentDto);
		getData(mav);
		mav.addObject(role, Role);

		mav.addObject("buttonValue", update);
		mav.addObject("action", Role + "/student/update");

		mav.addObject("selectedBatchId", studentDto.getBatchId());
		mav.addObject("selectedBranchId", studentDto.getBranchId());
		mav.addObject("selectedYearId", studentDto.getJoiningYearId());

		new StudentValidator().validate(studentDto, bindingResult);

		if(bindingResult.hasErrors()) {

			mav.setViewName("addStudent");
			mav.addObject("message", "Student Information is not updated");
			mav.addObject("buttonValue", update );
			mav.addObject("action","/student/update");
			return mav;
		}

		Optional<StudentDto> studentDtoOptional = studentService.update(request, studentDto);

		if(studentDtoOptional.isPresent()) {
			StudentDto updatedStudent = studentDtoOptional.get();

			mav.addObject("studentList", studentService.list(request,
					updatedStudent.getBranchId(),
					updatedStudent.getBatchId(), updatedStudent.getJoiningYearId()));

			mav.addObject("selectedBatchId", updatedStudent.getBatchId());
			mav.addObject("selectedBranchId", updatedStudent.getBranchId());
			mav.addObject("selectedYearId", updatedStudent.getJoiningYearId());

			mav.addObject("message", "Student Information updated successfully");
			return mav;
		}

		mav.addObject("studentList", studentService.list(request,
				studentDto.getBranchId(),
				studentDto.getBatchId(), studentDto.getJoiningYearId()));

		mav.addObject("message", "Problem in Updating Student Data");
		return mav;

	}

	@PostMapping("/student/delete")
	public ModelAndView delete(@ModelAttribute("studentDto") StudentDto studentDto,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("studentList", "studentDto", studentDto);
		getData(mav);
		mav.addObject(role, Role);

		mav.addObject("buttonValue", delete);
		mav.addObject("action", Role + "/student/delete");

		mav.addObject("selectedBatchId", studentDto.getBatchId());
		mav.addObject("selectedBranchId", studentDto.getBranchId());
		mav.addObject("selectedYearId", studentDto.getJoiningYearId());

		Optional<StudentDto> studentDtoOptional = studentService.delete(request,
				studentDto);

		if(!studentDtoOptional.isPresent()) {
			mav.addObject("message", "Student Information deleted successfully");
		}  else {
			mav.addObject("message", "Problem in deletion");
		}

		mav.addObject("studentList", studentService.list(request,
				studentDto.getBranchId(),
				studentDto.getBatchId(),
				studentDto.getJoiningYearId()));

		return mav;
	}

	@GetMapping("/searchStudent")
	public ModelAndView search() {

		return new ModelAndView("searchStudent", "studentDto",
				new StudentDto());
	}

	@PostMapping("/searchStudent")
	public ModelAndView search(@ModelAttribute("studentDto") StudentDto studentDto,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("searchStudent", "studentDto",
				new StudentDto());

		Pair<String, List> studentData = studentService.search(request, studentDto);

//		if(studentData.getSecond().isEmpty()) {
//			mav.addObject("message", "No records found based on your selection");
//		}

		mav.addObject("message", studentData.getFirst());
		mav.addObject("studentList", studentData.getSecond());

		return mav;
	}

}

//	return "redirect:/recipes/" + recipe.getId();

	/*@GetMapping(value = "/student/add")
	public ResponseEntity<?> save()  {

		List<Semester> semesters = new ArrayList<>();

		StreamSupport.stream(yearRepository.findByYearId(1).spliterator(), false)
				.forEach(year -> semesters.addAll(year.getSemseters()));

		return new ResponseEntity<>(semesters, HttpStatus.OK);
	}*/
