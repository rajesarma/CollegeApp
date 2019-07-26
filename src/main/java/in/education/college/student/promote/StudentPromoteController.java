package in.education.college.student.promote;

import in.education.college.dto.StudentDto;
import in.education.college.dto.StudentPromoteForm;
import in.education.college.student.StudentService;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value="/super")
public class StudentPromoteController {

	private StudentPromoteService studentPromoteService;
	private StudentService studentService;

	@Value("${save}")
	String save;

	@Autowired
	StudentPromoteController(
			final StudentPromoteService studentPromoteService,
			final StudentService studentService ) {
		this.studentPromoteService = studentPromoteService;
		this.studentService = studentService;
	}

	private String Role = "/super";
	private String role = "Role";
	private String message = "message";
	private String buttonValue = "buttonValue";
	private String action = "action";
	private String method = "method";
	private String showTab = "showTab";

	@GetMapping(value = "/student/studentPromote")
	public ModelAndView getData()  {

		ModelAndView mav = new ModelAndView("studentPromote", "studentPromoteForm",
				new StudentPromoteForm());

		mav.addObject(buttonValue, save );
		mav.addObject(action,Role + "/student/studentPromote");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");
		getInitialData(mav);
		return mav;
	}

	@PostMapping("/student/studentPromote")
	public ModelAndView getStudentsList(@ModelAttribute("studentPromote") StudentPromoteForm studentPromoteForm) {

		ModelAndView mav = new ModelAndView("studentPromote", "studentPromoteForm", studentPromoteForm );

		getInitialData(mav);
		getSelectedData(mav, studentPromoteForm);
		mav.addObject(buttonValue, "Save");
		mav.addObject(action, Role + "/student/studentPromote");
		mav.addObject("attendanceAction", Role + "/student/studentPromote/add");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");

		List<StudentDto> students = studentPromoteService.getStudentsList(studentPromoteForm);

		if(students.isEmpty()) {
			mav.addObject(message, "No records found based on your selection");
		}

		studentPromoteForm.setStudents(students);

//		List<Long> studentIds = students.stream().map(StudentDto::getStudentId).collect(Collectors.toList());

//		students.stream().forEach(stud -> stud.setStudId(String.valueOf(stud.getStudentId())));
//		studentPromoteForm.setStudentMap(students.stream().collect(Collectors.toMap(e -> e.getStudentId()+"",
//				e -> e.getName())));

//		mav.addObject("studentIds", studentIds);

//		mav.addObject("studentMap", studentPromoteForm.getStudentMap());

		return mav;
	}

	@PostMapping("/student/studentPromote/add")
	public ModelAndView save(@ModelAttribute("studentPromote") StudentPromoteForm studentPromoteForm) {

		ModelAndView mav = new ModelAndView("studentPromote", "studentPromoteForm", studentPromoteForm );
		String msg = "";

		mav.addObject(buttonValue, "Save");
		mav.addObject(action, Role + "/student/studentPromote");
		mav.addObject("attendanceAction", Role + "/student/studentPromote/add");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");

		// <UpdateCount, Eligible Students, In Eligible Students>
		Triple<Integer, List<Long>, List<Long>> students = studentPromoteService.promoteStudents(studentPromoteForm.getStudentIds());

		int updateCount = students.getLeft();
		List<Long> eligibleStudentIds = students.getMiddle();
		List<Long> notEligibleStudentIds = students.getRight();

		// Students = In Eligible
		if( studentPromoteForm.getStudentIds().size() == notEligibleStudentIds.size()) {
			msg = "Either Attendance / Marks not posted for the selected students.";
		} else if(updateCount == eligibleStudentIds.size()) {
			msg = updateCount+ " Students Promoted.";
		} else if(updateCount > 0 && updateCount < eligibleStudentIds.size()) {
			msg = "Problem in promoting few 1 or more students.";
		} /*else {
			msg = "Problem in promoting few 1 or more students.";
		}*/

		getInitialData(mav);
		getSelectedData(mav, studentPromoteForm);

		List<StudentDto> toPromoteStudents = studentPromoteService.getStudentsList(studentPromoteForm);

		if(toPromoteStudents.isEmpty()) {
			msg = msg + " No records found based on your selection";
		}

		studentPromoteForm.setStudents(toPromoteStudents);
		studentPromoteForm.setStudentIds(null);
		mav.addObject(message, msg);

		return mav;
	}

	/*@PostMapping("/student/studentAttendance/edit/{studentAttendanceId}/{operation}")
	public ModelAndView edit(@PathVariable("studentAttendanceId") long studentAttendanceId,
			@PathVariable("operation") String operation) {

		ModelAndView mav = new ModelAndView("studentAttendance");
		mav.addObject(role, Role);
		mav.addObject(buttonValue, operation.toUpperCase() );
		mav.addObject(action,Role + "/student/studentAttendance/"+operation);
		mav.addObject(method,"POST");
//		mav.addObject("attendanceAction", Role + "/student/studentAttendance/add");
		mav.addObject(showTab, "edit");
		mav.addObject("operation", "/"+operation);

		Optional<StudentPromoteForm> studentAttendanceOptional =
				studentPromoteService.findById(studentAttendanceId);
		if(studentAttendanceOptional.isPresent()) {
			StudentPromoteForm studentAttendanceDto = studentAttendanceOptional.get();

			mav.addObject("studentAttendanceDto", studentAttendanceDto);
		}
		return mav;
	}

	@PostMapping("/student/studentAttendance/update")
	public ModelAndView update(@ModelAttribute("studentAttendanceDto") StudentPromoteForm studentAttendanceDto) {

		ModelAndView mav = new ModelAndView("studentAttendance", "studentAttendanceDto",
				studentAttendanceDto);

		mav.addObject(action,Role + "/student/studentAttendance");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");
		getInitialData(mav);

		if(studentAttendanceDto.getNoOfDays() < studentAttendanceDto.getDaysPresent()) {

			mav.addObject(message, "Attendance not updated, Please check values");
			mav.addObject(buttonValue, "UPDATE" );
			mav.addObject(action,Role + "/student/studentAttendance/update");
			mav.addObject(showTab, "edit");
			return mav;
		}

		Optional<StudentPromoteForm> studentAttendanceDtoOptional = studentPromoteService.update(studentAttendanceDto);

		if(studentAttendanceDtoOptional.isPresent()) {
			mav.addObject(message, "Student Attendance updated successfully");
			StudentPromoteForm updatedAttendanceDto = studentAttendanceDtoOptional.get();
			getSelectedData(mav, updatedAttendanceDto);
//			getDataList(mav, updatedAttendanceDto);
//			studentAttendanceDto.setAttendanceDtos(updatedAttendanceDto.getAttendanceDtos());
			List<StudentPromoteForm> studentsAttendanceList = studentPromoteService.getStudentsAttendanceList(updatedAttendanceDto);
			studentAttendanceDto.setAttendanceDtos(studentsAttendanceList);
		}

		return mav;
	}

	@PostMapping("/student/studentAttendance/delete")
	public ModelAndView delete(@ModelAttribute("studentDto") StudentPromoteForm studentAttendanceDto) {

		ModelAndView mav = new ModelAndView("studentAttendance", "studentAttendanceDto",
				studentAttendanceDto);

		mav.addObject(action, Role + "/student/studentAttendance");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");
		getInitialData(mav);

		Optional<StudentPromoteForm> studentAttendanceDtoOptional =
				studentPromoteService.delete(studentAttendanceDto);

		if(studentAttendanceDtoOptional.isPresent()) {

			mav.addObject(message, "Problem in deletion");
			mav.addObject(buttonValue, "DELETE" );
			mav.addObject(action,Role + "/student/studentAttendance/delete");
			mav.addObject(showTab, "edit");
			return mav;

		}  else {
			mav.addObject(message, "Student Attendance deleted successfully");
			getSelectedData(mav, studentAttendanceDto);
//			getDataList(mav, studentAttendanceDto);

			List<StudentPromoteForm> studentsAttendanceList = studentPromoteService.getStudentsAttendanceList(studentAttendanceDto);
			studentAttendanceDto.setAttendanceDtos(studentsAttendanceList);
		}

		return mav;
	}*/

	private void getInitialData(ModelAndView mav) {
		mav.addObject(role, Role);
		mav.addObject("batches", studentService.getBatches());
		mav.addObject("branches", studentService.getBranches());
		mav.addObject("years", studentService.getYears());
	}

	private void getSelectedData(ModelAndView mav, StudentPromoteForm studentPromoteForm) {
		mav.addObject("selectedBatchId", studentPromoteForm.getBatchId());
		mav.addObject("selectedBranchId", studentPromoteForm.getBranchId());
		mav.addObject("selectedYearId", studentPromoteForm.getYearId());
		mav.addObject("selectedSemesterId", studentPromoteForm.getSemesterId());

		Optional<Map<Long,String>> semestersOptional =
				studentService.getSemesetersByYearId(studentPromoteForm.getYearId());

		if(semestersOptional.isPresent()) {
			mav.addObject("semesters", semestersOptional.get());
		}
	}
}
