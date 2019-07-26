package in.education.college.employee;

import in.education.college.dto.EmployeeForm;
import in.education.college.validator.EmployeeValidator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value="/super")
public class EmployeeController {

	private EmployeeService employeeService;

	@Value("${save}")
	String save;
	@Value("${update}")
	String update;
	@Value("${delete}")
	String delete;

	private static String Role = "/super";

	@Autowired
	EmployeeController(final EmployeeService employeeService ) {
		this.employeeService = employeeService;
	}

	private String departments = "departments";
	private String qualifications = "qualifications";
	private String academicYears = "academicYears";
	private String bloodGroups = "bloodGroups";
	private String semesters = "semesters";
	private String role = "Role";
	private String message = "";

	private void getData(ModelAndView mav) {
		mav.addObject(departments, employeeService.getDepartments());
		mav.addObject(bloodGroups, employeeService.getBloodGroups());
		mav.addObject(academicYears, employeeService.getAcademicYears());
		mav.addObject(qualifications, employeeService.getQualifications());
		mav.addObject(semesters, employeeService.getSemesters());
	}


	@GetMapping(value = "/employee/add")
	public ModelAndView save()  {

		ModelAndView mav = new ModelAndView("addEmployee", "employeeForm", new EmployeeForm());

		mav.addObject("buttonValue", save );
		mav.addObject("action",Role + "/employee/add");
		mav.addObject(role, Role);
		getData(mav);

		return mav;
	}

	@PostMapping("/employee/add")
	public ModelAndView save(@ModelAttribute("employeeForm") EmployeeForm employeeForm,
			BindingResult bindingResult, HttpServletRequest request) { //@Valid

		ModelAndView mav = new ModelAndView("addEmployee", "employeeForm", employeeForm);
		getData(mav);
		mav.addObject(role, Role);
		mav.addObject("buttonValue", "Save");
		mav.addObject("action", Role + "/employee/add");

		new EmployeeValidator().validate(employeeForm, bindingResult);

		if(bindingResult.hasErrors()) {
			mav.addObject("message", "Problem in Saving Employee Data");
			return mav;
		}

		Optional<EmployeeForm> employeeFormOptional = employeeService.save(request,
				employeeForm);

		if(!employeeFormOptional.isPresent()) {
			mav.addObject("message", "Problem in Saving Employee Data");
			return mav;
		}

		mav.addObject("message", "Employee Information is added successfully");
		mav.addObject("employeeForm", new EmployeeForm());

		return mav;
	}

	@GetMapping("/employee/list")
	public ModelAndView list() {

		ModelAndView mav = new ModelAndView("employeeList", "employeeForm",
				new EmployeeForm());
		getData(mav);

		Map<String, String> conditions = new HashMap<>();
		conditions.put("<","Less Than");
		conditions.put("<=","Less Than Equal To");
		conditions.put("=","Equal To");
		conditions.put(">","Greater Than");
		conditions.put(">=","Greater Than Equal To");

		mav.addObject("conditions", conditions.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));

		mav.addObject(role, Role);
		return mav;
	}

	@PostMapping("/employee/list")
	public ModelAndView list(@ModelAttribute("employeeForm") EmployeeForm employeeForm, BindingResult bindingResult,
			@RequestParam String conditionString, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("employeeList");
		getData(mav);
		Map<String, String> conditions = new HashMap<>();
		conditions.put("<","Less Than");
		conditions.put("<=","Less Than Equal To");
		conditions.put("=","Equal To");
		conditions.put(">","Greater Than");
		conditions.put(">=","Greater Than Equal To");
		mav.addObject("conditions", conditions.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));
		mav.addObject(role, Role);

		/*List employeesList = employeeService.listBySalaries(request,
				employeeForm.getDeptId(),
				employeeForm.getJoiningAcademicYearId(),
				employeeForm.getJoiningSemesterId(),
				employeeForm.getSalary(),
				conditionString);*/

		employeeForm.setConditionString(conditionString);

		List employeesList = employeeService.listBySalaries(request, employeeForm);

		if(employeesList.isEmpty()) {
			mav.addObject("message", "No records found based on your selection");
		}

		mav.addObject("employeeList", employeesList);

		mav.addObject("selectedDeptId", employeeForm.getDeptId());
		mav.addObject("selectedJoiningAcademicYearId", employeeForm.getJoiningAcademicYearId());
		mav.addObject("selectedJoiningSemesterId", employeeForm.getJoiningSemesterId());
		mav.addObject("conditionString", conditionString);

		return mav;
	}

	// Ajax request
	@GetMapping(value = "/employee/{type}/{value}")
	public ResponseEntity<?> find(@PathVariable("type") String type,
			@PathVariable("value") String value) {

		String result;
		Optional<EmployeeForm> employeeForm;

		employeeForm = employeeService.findByType(type, value);

		String typeString = StringUtils.join(type.split("(?=\\p{Upper})"), " ");

		if(employeeForm.isPresent()) {
			result =
					"{\"valueExists\":\"true\", \"message\":\"" + StringUtils.capitalize(typeString) +
					" already exists\" }";
		} else {
			result = "{\"valueExists\":\"false\" }";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/employee/edit/{employeeId}/{operation}")
	public ModelAndView edit(@PathVariable("employeeId") long employeeId,
			@PathVariable("operation") String operation) {

		ModelAndView mav = new ModelAndView("addEmployee", "employeeForm", new EmployeeForm());

		Optional<EmployeeForm> employeeOptional = employeeService.findById(employeeId);
		if(employeeOptional.isPresent()) {
			EmployeeForm employeeForm = employeeOptional.get();

			mav.addObject("selectedDeptId", employeeForm.getDeptId());
			mav.addObject("selectedJoiningAcademicYearId", employeeForm.getJoiningAcademicYearId());
			mav.addObject("selectedJoiningSemesterId", employeeForm.getJoiningSemesterId());

			mav.addObject("photoData",employeeForm.getPhotoData());
			mav.addObject("employeeForm", employeeForm);
		}

		mav.addObject(role, Role);
		getData(mav);

		mav.addObject("buttonValue", operation.toUpperCase() );
		mav.addObject("action",Role + "/employee/"+operation);

		return mav;
	}

	@PostMapping("/employee/update")
	public ModelAndView update(@ModelAttribute("employeeForm") EmployeeForm employeeForm,
			BindingResult bindingResult, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("employeeList", "employeeForm", employeeForm);
		getData(mav);
		mav.addObject(role, Role);

		mav.addObject("buttonValue", update);
		mav.addObject("action", Role + "/employee/update");

		mav.addObject("selectedDeptId", employeeForm.getDeptId());
		mav.addObject("selectedJoiningAcademicYearId", employeeForm.getJoiningAcademicYearId());
		mav.addObject("selectedJoiningSemesterId", employeeForm.getJoiningSemesterId());

		new EmployeeValidator().validate(employeeForm, bindingResult);

		if(bindingResult.hasErrors()) {

			mav.setViewName("addEmployee");
			mav.addObject("message", "Employee Information is not updated");
			mav.addObject("buttonValue", update );
			mav.addObject("action","/employee/update");
			return mav;
		}

		Optional<EmployeeForm> employeeFormOptional =
				employeeService.update(request, employeeForm);

		if(employeeFormOptional.isPresent()) {
			EmployeeForm updatedEmployee = employeeFormOptional.get();

			mav.addObject("employeeList", employeeService.list(request,
					updatedEmployee.getDeptId(),
					updatedEmployee.getJoiningAcademicYearId(),
					updatedEmployee.getJoiningSemesterId()));

			mav.addObject("selectedDeptId", updatedEmployee.getDeptId());
			mav.addObject("selectedJoiningAcademicYearId", updatedEmployee.getJoiningAcademicYearId());
			mav.addObject("selectedJoiningSemesterId", updatedEmployee.getJoiningSemesterId());

			mav.addObject("message", "Employee Information updated successfully");
			return mav;
		}

		mav.addObject("employeeList", employeeService.list(request,
				employeeForm.getDeptId(),
				employeeForm.getJoiningAcademicYearId(),
				employeeForm.getJoiningSemesterId()));

		mav.addObject("message", "Problem in Updating Employee Data");
		return mav;

	}

	@PostMapping("/employee/delete")
	public ModelAndView delete(@ModelAttribute("employeeForm") EmployeeForm employeeForm, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("employeeList", "employeeForm", employeeForm);
		getData(mav);
		mav.addObject(role, Role);

		mav.addObject("buttonValue", delete);
		mav.addObject("action", Role + "/employee/delete");

		mav.addObject("selectedDeptId", employeeForm.getDeptId());
		mav.addObject("selectedJoiningAcademicYearId", employeeForm.getJoiningAcademicYearId());
		mav.addObject("selectedJoiningSemesterId", employeeForm.getJoiningSemesterId());

		Optional<EmployeeForm> employeeFormOptional = employeeService.delete(request,
				employeeForm);

		if(!employeeFormOptional.isPresent()) {
			mav.addObject("message", "Employee Information deleted successfully");
		}  else {
			mav.addObject("message", "Problem in deletion");
		}

		mav.addObject("employeeList", employeeService.list(request,
				employeeForm.getJoiningAcademicYearId(),
				employeeForm.getDeptId(),
				employeeForm.getJoiningSemesterId()));

		return mav;
	}

	@GetMapping("/searchEmployee")
	public ModelAndView search() {

		return new ModelAndView("searchEmployee", "employeeForm",
				new EmployeeForm());
	}

	@PostMapping("/searchEmployee")
	public ModelAndView search(@ModelAttribute("employeeForm") EmployeeForm employeeForm, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("searchEmployee", "employeeForm",
				new EmployeeForm());

		Pair<String, List> employeeData = employeeService.search(request, employeeForm);

		mav.addObject("message", employeeData.getFirst());
		mav.addObject("employeeList", employeeData.getSecond());

		return mav;
	}
}