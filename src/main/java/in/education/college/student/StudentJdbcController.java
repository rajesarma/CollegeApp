package in.education.college.student;

import in.education.college.common.util.DBDataUtils;
import in.education.college.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;

//@Controller
@RequestMapping(value="/super")
public class StudentJdbcController {

	private DBDataUtils dbDataUtils;
	private StudentJdbcService studentJdbcService;

	/*@Autowired
	private MessageSource messageSource;
	*/
//	Configure this and get values by using the following
//	messageSource.getMessage("save", null, Locale.US);

	@Value("${save}")
	String save;

	static String Role = "/super";

	@Autowired
	StudentJdbcController(StudentJdbcService studentJdbcService, DBDataUtils dbDataUtils) {
		this.studentJdbcService = studentJdbcService;
		this.dbDataUtils = dbDataUtils;
	}

	private void loadDbData(DBDataUtils dbDataUtils, ModelAndView mav) {

		try {
			mav.addObject("academicYears", dbDataUtils.getAcademicYears());
			mav.addObject("bloodGroups", dbDataUtils.getBloodGroups());
			mav.addObject("branches", dbDataUtils.getBranches());
			mav.addObject("years", dbDataUtils.getYears());
		}catch (SQLException e) {
			mav.addObject("message", "Problem in Fetching Data");
		}

		mav.addObject("Role", Role);

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVIZOR')")
	@GetMapping(value = "/student/add")
	public ModelAndView save()  {

		Student student = new Student();
//		student.setGender("M");

		ModelAndView mav = new ModelAndView("studentAdd", "studentData", student);

		loadDbData(dbDataUtils, mav);

		mav.addObject("buttonValue", save );
		mav.addObject("action",Role + "/student/add");

		mav.addObject("Role", Role);

		return mav;
	}

	@PostMapping("/student/add")
	public ModelAndView save( @Valid @ModelAttribute("studentData") Student studentData,
			@RequestParam("image") MultipartFile image,
			BindingResult bindingResult) {

		// If we are using InitBinder and specify custom validator, then validation is
		// not done in the Form itself. Else validation is done in the form for the
		// specified constraints
		// We can also create new instance of the custom validator and bind it like below

		//new StudentValidator().validate(studentData, bindingResult);

		if(bindingResult.hasErrors()) {

			ModelAndView mav = new ModelAndView("studentAdd", "studentData",
					studentData);

			mav.addObject("Role", Role);

			return mav;
		}

		Student student = new Student();
//		student.setGender("M");

		ModelAndView mav = new ModelAndView("studentAdd", "studentData",
				student);

		try {
			studentData.setPhotoName(image.getOriginalFilename());
			studentData.setPhoto(image.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		loadDbData(dbDataUtils, mav);
		mav.addObject("buttonValue","Save");
		mav.addObject("action",Role + "/student/add");

		int result = studentJdbcService.addStudentData(studentData);

		if(result > 0) {
			mav.addObject("message", "Student Information inserted successfully");
		} else {

			mav.addObject("studentData", studentData);
			mav.addObject("message", "Students Information is not inserted");
		}

		mav.addObject("Role", Role);
		return mav;
	}

	@Autowired
	@Qualifier("studentValidator")
	private Validator studentValidator;

	@InitBinder
	private void dataBinding(WebDataBinder binder) {
		binder.setValidator(studentValidator);
		//binder.addValidators(studentValidator, emailValidator); // Multiple Validators
	}

	@PostMapping(value = "/student/{type}/{rollNo}")
	public ResponseEntity<?> find(@PathVariable("type") String type,
			@PathVariable("rollNo") String rollNo)  {

		String result = null;

		try {
			if(type.equalsIgnoreCase("checkRollNo")) {
				result = dbDataUtils.isRollNoExists(rollNo);
			}

		} catch (SQLException e) {
			return new ResponseEntity<>("Problem in Fetching Data", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// list //
	@GetMapping("/student/list")
	public ModelAndView list() {

		ModelAndView mav = new ModelAndView("studentList", "studentData",
				new Student());
		loadDbData(dbDataUtils, mav);

		mav.addObject("Role", Role);
		return mav;
	}

	@PostMapping("/student/list")
	public ModelAndView list(@ModelAttribute("studentData") Student studentData) {

		ModelAndView mav = new ModelAndView("studentList");
		loadDbData(dbDataUtils, mav);
		mav.addObject("studentList", studentJdbcService.getSpecifiedStudentsData(studentData));

		mav.addObject("Role", Role);
		return mav;
	}
	// list //

	// Get //
	@GetMapping("/student/edit/{studentId}/{operation}")
	public ModelAndView findById(@PathVariable("studentId") int studentId,
			@PathVariable("operation") String operation) {

		ModelAndView mav = new ModelAndView("studentAdd");
		Student studentData = studentJdbcService.getStudentData(studentId);

		/*if(studentData.getPhotoData() != null) {
			mav.addObject("photoExt", studentData.getPhotoName().substring(studentData.getPhotoName().indexOf('.') + 1));
			mav.addObject("photoData", studentData.getPhotoData());
		}*/

		mav.addObject("studentData", studentData);

		mav.addObject("buttonValue", operation.toUpperCase());
		mav.addObject("action",Role + "/student/" + operation); // operation as Update / Delete

		loadDbData(dbDataUtils, mav);


		mav.addObject("Role", Role);
		return mav;
	}
	// Get //

	// Update //
	@PostMapping("/student/update")
	public ModelAndView update(@ModelAttribute("studentData") Student studentData)  {

		ModelAndView mav = new ModelAndView("studentList", "studentData", studentData);

		loadDbData(dbDataUtils, mav);

		int result = studentJdbcService.updateStudentData(studentData);

		if(result > 0) {

			mav.addObject("message", "Student Information updated successfully");
			mav.addObject("studentList", studentJdbcService.getSpecifiedStudentsData(studentData));

		} else {
			mav.setViewName("studentAdd");
			mav.addObject("message", "Students Information is not updated");
		}

		mav.addObject("Role", Role);
		return mav;
	}

	// Delete //
	@PostMapping("/student/delete")
	public ModelAndView delete(@ModelAttribute("studentData") Student studentData)  {

		ModelAndView mav = new ModelAndView("studentAdd", "studentData",
				new Student());

		int result = studentJdbcService.deleteStudentData(studentData);

		if(result > 0) {

			mav.addObject("message", "Student Information deleted successfully");

			mav.addObject("buttonValue","Save");
			mav.addObject("action",Role + "/student/add");

		} else {

			mav = new ModelAndView("studentAdd", "studentData",
					studentData);
			mav.addObject("message", "Students Information is not deleted");

			mav.addObject("buttonValue","Delete");
			mav.addObject("action",Role + "/student/delete");
		}

		loadDbData(dbDataUtils, mav);

		mav.addObject("Role", Role);
		return mav;
	}
}
