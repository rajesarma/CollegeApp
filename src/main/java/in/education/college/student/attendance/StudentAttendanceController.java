package in.education.college.student.attendance;

import in.education.college.dto.StudentAttendanceDto;
import in.education.college.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value="/super")
public class StudentAttendanceController {

	private StudentAttendanceService studentAttendanceService;
	private StudentService studentService;

	@Value("${save}")
	String save;

	@Autowired
	StudentAttendanceController(
			final StudentAttendanceService studentAttendanceService,
			final StudentService studentService ) {
		this.studentAttendanceService = studentAttendanceService;
		this.studentService = studentService;
	}

	private String Role = "/super";
	private String role = "Role";
	private String message = "message";
	private String buttonValue = "buttonValue";
	private String action = "action";
	private String method = "method";
	private String showTab = "showTab";

	@GetMapping(value = "/student/studentAttendance")
	public ModelAndView getData()  {

		ModelAndView mav = new ModelAndView("studentAttendance", "studentAttendanceDto",
				new StudentAttendanceDto());

		mav.addObject(buttonValue, save );
		mav.addObject(action,Role + "/student/studentAttendance");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");
		getInitialData(mav);
		return mav;
	}

	@PostMapping("/student/studentAttendance")
	public ModelAndView getStudentsAttendanceList(@ModelAttribute("studentAttendanceDto") StudentAttendanceDto studentAttendanceDto) {

//		ModelAndView mav = new ModelAndView("studentAttendance", "attendanceForm", studentAttendanceDto);
		ModelAndView mav = new ModelAndView("studentAttendance");

		getInitialData(mav);
		getSelectedData(mav, studentAttendanceDto);
		mav.addObject(buttonValue, "Save");
		mav.addObject(action, Role + "/student/studentAttendance");
		mav.addObject("attendanceAction", Role + "/student/studentAttendance/add");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");

		List<StudentAttendanceDto> studentAttendanceDtos =
				studentAttendanceService.getStudentsAttendanceList(studentAttendanceDto);

		if(studentAttendanceDtos.isEmpty()) {
			mav.addObject(message, "No records found based on your selection");
		}

		studentAttendanceDto.setAttendanceDtos(studentAttendanceDtos);

//		StudentAttendanceForm attendanceForm = new StudentAttendanceForm();
//		attendanceForm.setAttendanceDtos(studentAttendanceDtos);
//		attendanceForm.setBatchId(studentAttendanceDto.getBatchId());
//		attendanceForm.setBranchId(studentAttendanceDto.getBranchId());
//		attendanceForm.setYearId(studentAttendanceDto.getYearId());
//		attendanceForm.setSemesterId(studentAttendanceDto.getSemesterId());
//
//		mav.addObject("attendanceForm", attendanceForm);

		return mav;
	}

	@PostMapping("/student/studentAttendance/add")
	public ModelAndView save(@ModelAttribute("studentAttendanceDto") StudentAttendanceDto studentAttendanceDto) {

		List<StudentAttendanceDto> attendanceDtos = studentAttendanceDto.getAttendanceDtos();

		ModelAndView mav = new ModelAndView("studentAttendance");

//		ModelAndView mav = new ModelAndView("studentAttendance", "attendanceForm", new StudentAttendanceForm());

		mav.addObject(buttonValue, "Save");
//		mav.addObject(action, Role + "/student/studentAttendance/add");
		mav.addObject(action, Role + "/student/studentAttendance");
		mav.addObject("attendanceAction", Role + "/student/studentAttendance/add");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");

		List<StudentAttendanceDto> insertedStudentDtos = studentAttendanceService.saveAll(attendanceDtos);

		if(insertedStudentDtos.isEmpty()) {
			mav.addObject(message, "Problem in inserting records");
		} else {
			mav.addObject(message, "Student Attendance Records Updated");
		}

//		mav.addObject("selectedBatchId", studentAttendanceDto.getBatchId());
//		mav.addObject("selectedBranchId", studentAttendanceDto.getBranchId());
//		mav.addObject("selectedYearId", studentAttendanceDto.getYearId());
//		mav.addObject("selectedSemesterId", studentAttendanceDto.getSemesterId());
//		mav.addObject("studentAttendanceDto", studentAttendanceDto);
//
//		Optional<Map<Long,String>> semestersOptional =
//				studentService.getSemesetersByYearId(studentAttendanceDto.getYearId());

//		if(semestersOptional.isPresent()) {
//			mav.addObject("semesters", semestersOptional.get());
//		}

		studentAttendanceDto.setAttendanceDtos(studentAttendanceService.getStudentsAttendanceList(studentAttendanceDto));

		getInitialData(mav);
		getSelectedData(mav, studentAttendanceDto);
//		getDataList(mav, studentAttendanceDto);

		return mav;
	}

	@PostMapping("/student/studentAttendance/edit/{studentAttendanceId}/{operation}")
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

		Optional<StudentAttendanceDto> studentAttendanceOptional =
				studentAttendanceService.findById(studentAttendanceId);
		if(studentAttendanceOptional.isPresent()) {
			StudentAttendanceDto studentAttendanceDto = studentAttendanceOptional.get();

			mav.addObject("studentAttendanceDto", studentAttendanceDto);
		}
		return mav;
	}

	@PostMapping("/student/studentAttendance/update")
	public ModelAndView update(@ModelAttribute("studentAttendanceDto") StudentAttendanceDto studentAttendanceDto) {

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

		Optional<StudentAttendanceDto> studentAttendanceDtoOptional = studentAttendanceService.update(studentAttendanceDto);

		if(studentAttendanceDtoOptional.isPresent()) {
			mav.addObject(message, "Student Attendance updated successfully");
			StudentAttendanceDto updatedAttendanceDto = studentAttendanceDtoOptional.get();
			getSelectedData(mav, updatedAttendanceDto);
//			getDataList(mav, updatedAttendanceDto);
//			studentAttendanceDto.setAttendanceDtos(updatedAttendanceDto.getAttendanceDtos());
			List<StudentAttendanceDto> studentsAttendanceList = studentAttendanceService.getStudentsAttendanceList(updatedAttendanceDto);
			studentAttendanceDto.setAttendanceDtos(studentsAttendanceList);
		}

		return mav;
	}

	@PostMapping("/student/studentAttendance/delete")
	public ModelAndView delete(@ModelAttribute("studentDto") StudentAttendanceDto studentAttendanceDto) {

		ModelAndView mav = new ModelAndView("studentAttendance", "studentAttendanceDto",
				studentAttendanceDto);

		mav.addObject(action, Role + "/student/studentAttendance");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");
		getInitialData(mav);

		Optional<StudentAttendanceDto> studentAttendanceDtoOptional =
				studentAttendanceService.delete(studentAttendanceDto);

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

			List<StudentAttendanceDto> studentsAttendanceList = studentAttendanceService.getStudentsAttendanceList(studentAttendanceDto);
			studentAttendanceDto.setAttendanceDtos(studentsAttendanceList);
		}

		return mav;
	}

	private void getInitialData(ModelAndView mav) {
		mav.addObject(role, Role);
		mav.addObject("batches", studentService.getBatches());
		mav.addObject("branches", studentService.getBranches());
		mav.addObject("years", studentService.getYears());
	}

	private void getSelectedData(ModelAndView mav, StudentAttendanceDto studentAttendanceDto) {
		mav.addObject("selectedBatchId", studentAttendanceDto.getBatchId());
		mav.addObject("selectedBranchId", studentAttendanceDto.getBranchId());
		mav.addObject("selectedYearId", studentAttendanceDto.getYearId());
		mav.addObject("selectedSemesterId", studentAttendanceDto.getSemesterId());

		Optional<Map<Long,String>> semestersOptional =
				studentService.getSemesetersByYearId(studentAttendanceDto.getYearId());

		if(semestersOptional.isPresent()) {
			mav.addObject("semesters", semestersOptional.get());
		}
	}


	/*@PostMapping("/student/studentAttendance/add")
	public ModelAndView save(@ModelAttribute("attendanceForm") StudentAttendanceForm attendanceForm) {

		List<StudentAttendanceDto> attendanceDtos = attendanceForm.getAttendanceDtos();

		ModelAndView mav = new ModelAndView("studentAttendance", "attendanceForm",
				new StudentAttendanceForm());

		mav.addObject(buttonValue, "Save");
//		mav.addObject(action, Role + "/student/studentAttendance/add");
		mav.addObject(action, Role + "/student/studentAttendance");
		mav.addObject("attendanceAction", Role + "/student/studentAttendance/add");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");

		List<StudentAttendanceDto> insertedStudentDtos =
				studentAttendanceService.saveAll(attendanceDtos);

		if(insertedStudentDtos.isEmpty()) {
			mav.addObject(message, "Problem in inserting records");
		} else {
			mav.addObject(message, "Student Attendance Records Updated");
		}

		StudentAttendanceForm newAttendanceForm = new StudentAttendanceForm();
		attendanceForm.setAttendanceDtos(insertedStudentDtos);
		mav.addObject("attendanceForm", newAttendanceForm);

		StudentAttendanceDto studentAttendanceDto = new StudentAttendanceDto();
		studentAttendanceDto.setBatchId(attendanceForm.getBatchId());
		studentAttendanceDto.setBranchId(attendanceForm.getBranchId());
		studentAttendanceDto.setYearId(attendanceForm.getYearId());
		studentAttendanceDto.setSemesterId(attendanceForm.getSemesterId());

		mav.addObject("selectedBatchId", attendanceForm.getBatchId());
		mav.addObject("selectedBranchId", attendanceForm.getBranchId());
		mav.addObject("selectedYearId", attendanceForm.getYearId());
		mav.addObject("selectedSemesterId", attendanceForm.getSemesterId());
		mav.addObject("studentAttendanceDto", studentAttendanceDto);

		Optional<Map<Long,String>> semestersOptional =
				studentService.getSemesetersByYearId(attendanceForm.getYearId());

		if(semestersOptional.isPresent()) {
			mav.addObject("semesters", semestersOptional.get());
		}

		attendanceForm.setAttendanceDtos(studentAttendanceService.getStudentsAttendanceList(studentAttendanceDto));
		attendanceForm.setBatchId(attendanceForm.getBatchId());
		attendanceForm.setBranchId(attendanceForm.getBranchId());
		attendanceForm.setYearId(attendanceForm.getYearId());
		attendanceForm.setSemesterId(attendanceForm.getSemesterId());

		mav.addObject("attendanceForm", attendanceForm);

		getInitialData(mav);
		getDataList(mav, studentAttendanceDto);

		return mav;
	}*/

//	private void getDataList(ModelAndView mav, StudentAttendanceDto studentAttendanceDto) {
//
//		studentAttendanceDto.setAttendanceDtos(studentAttendanceService.getStudentsAttendanceList(studentAttendanceDto));
//
//		StudentAttendanceForm attendanceForm = new StudentAttendanceForm();
//		attendanceForm.setAttendanceDtos(studentAttendanceService.getStudentsAttendanceList(studentAttendanceDto));
//		attendanceForm.setBatchId(studentAttendanceDto.getBatchId());
//		attendanceForm.setBranchId(studentAttendanceDto.getBranchId());
//		attendanceForm.setYearId(studentAttendanceDto.getYearId());
//		attendanceForm.setSemesterId(studentAttendanceDto.getSemesterId());
//		mav.addObject("attendanceForm", attendanceForm);
//	}

//	public class StudentAttendanceForm {
//
//		private long batchId;
//		private String branchId;
//		private long yearId;
//		private long semesterId;
//
//		public long getBatchId() {
//			return batchId;
//		}
//
//		public void setBatchId(long batchId) {
//			this.batchId = batchId;
//		}
//
//		public String getBranchId() {
//			return branchId;
//		}
//
//		public void setBranchId(String branchId) {
//			this.branchId = branchId;
//		}
//
//		public long getYearId() {
//			return yearId;
//		}
//
//		public void setYearId(long yearId) {
//			this.yearId = yearId;
//		}
//
//		public long getSemesterId() {
//			return semesterId;
//		}
//
//		public void setSemesterId(long semesterId) {
//			this.semesterId = semesterId;
//		}
//
//		private List<StudentAttendanceDto> attendanceDtos;
//
//		public List<StudentAttendanceDto> getAttendanceDtos() {
//			return attendanceDtos;
//		}
//
//		public void setAttendanceDtos(List<StudentAttendanceDto> attendanceDtos) {
//			this.attendanceDtos = attendanceDtos;
//		}
//
//		@Override
//		public String toString() {
//			return "StudentAttendanceForm{" +
//					"batchId=" + batchId +
//					", branchId='" + branchId + '\'' +
//					", yearId=" + yearId +
//					", semesterId=" + semesterId +
//					", attendanceDtos=" + attendanceDtos +
//					'}';
//		}
//	}
}
