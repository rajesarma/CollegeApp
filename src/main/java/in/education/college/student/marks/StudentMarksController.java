package in.education.college.student.marks;

import in.education.college.dto.StudentMarksDto;
import in.education.college.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class StudentMarksController {

	private StudentMarksService studentMarksService;
	private StudentService studentService;

	@Value("${save}")
	String save;

	@Autowired
	StudentMarksController(
			final StudentMarksService studentMarksService,
			final StudentService studentService ) {
		this.studentMarksService = studentMarksService;
		this.studentService = studentService;
	}

	private String Role = "/super";
	private String role = "Role";
	private String message = "";
	private String buttonValue= "buttonValue";
	private String action= "action";
	private String method= "method";
	private String showTab= "showTab";

	@GetMapping(value = "/student/studentMarks")
	public ModelAndView getData()  {

		ModelAndView mav = new ModelAndView("studentMarks", "studentMarksDto",
				new StudentMarksDto());

		mav.addObject(buttonValue, save );
		mav.addObject(action,Role + "/student/studentMarks");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");
		getInitialData(mav);
		return mav;
	}

	@PostMapping("/student/studentMarks")
	public ModelAndView getStudentsMarksList(@ModelAttribute("studentMarksDto") StudentMarksDto studentMarksDto) {

		ModelAndView mav = new ModelAndView("studentMarks");

		getInitialData(mav);
		getSelectedData(mav, studentMarksDto);
		mav.addObject(buttonValue, "Save");
		mav.addObject(action, Role + "/student/studentMarks");
		mav.addObject("marksAction", Role + "/student/studentMarks/add");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");

		List<StudentMarksDto> studentMarksDtos = studentMarksService.getStudentsMarksList(studentMarksDto);

		if(studentMarksDtos.isEmpty()) {
			mav.addObject(message, "No records found based on your selection");
		}

		studentMarksDto.setMarksDtos(studentMarksDtos);

//		StudentMarksForm marksForm = new StudentMarksForm();
//		marksForm.setMarksDtos(studentMarksDtos);
//		marksForm.setBatchId(studentMarksDto.getBatchId());
//		marksForm.setBranchId(studentMarksDto.getBranchId());
//		marksForm.setYearId(studentMarksDto.getYearId());
//		marksForm.setSemesterId(studentMarksDto.getSemesterId());
//		marksForm.setSubjectId(studentMarksDto.getSubjectId());
//		marksForm.setExamTypeId(studentMarksDto.getExamTypeId());
//
//		mav.addObject("marksForm", marksForm);

		return mav;
	}

	@PostMapping("/student/studentMarks/add")
	public ModelAndView save(@ModelAttribute("studentMarksDto") StudentMarksDto studentMarksDto) {

		List<StudentMarksDto> marksDtos = studentMarksDto.getMarksDtos();

//		ModelAndView mav = new ModelAndView("studentMarks", "marksForm", new StudentMarksForm());
		ModelAndView mav = new ModelAndView("studentMarks");

		mav.addObject(buttonValue, "Save");
//		mav.addObject(action, Role + "/student/studentMarks/add");
		mav.addObject(action, Role + "/student/studentMarks");
		mav.addObject("marksAction", Role + "/student/studentMarks/add");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");

		List<StudentMarksDto> insertedStudentDtos = studentMarksService.saveAll(marksDtos, studentMarksDto);

		if(insertedStudentDtos.isEmpty()) {
			mav.addObject(message, "Problem in inserting records");
		} else {
			mav.addObject(message, "Student Marks Records Updated");
		}

		studentMarksDto.setMarksDtos(studentMarksService.getStudentsMarksList(studentMarksDto));

//		StudentMarksForm newMarksForm = new StudentMarksForm();
//		marksForm.setMarksDtos(insertedStudentDtos);
//		mav.addObject("marksForm", newMarksForm);
//
//		StudentMarksDto studentMarksDto = new StudentMarksDto();
//		studentMarksDto.setBatchId(marksForm.getBatchId());
//		studentMarksDto.setBranchId(marksForm.getBranchId());
//		studentMarksDto.setYearId(marksForm.getYearId());
//		studentMarksDto.setSemesterId(marksForm.getSemesterId());
//		studentMarksDto.setSubjectId(marksForm.getSubjectId());
//		studentMarksDto.setExamTypeId(marksForm.getExamTypeId());
//
//		mav.addObject("studentMarksDto", studentMarksDto);

		getInitialData(mav);
		getSelectedData(mav, studentMarksDto);

//		marksForm.setMarksDtos(studentMarksService.getStudentsMarksList(studentMarksDto));
//		marksForm.setBatchId(marksForm.getBatchId());
//		marksForm.setBranchId(marksForm.getBranchId());
//		marksForm.setYearId(marksForm.getYearId());
//		marksForm.setSemesterId(marksForm.getSemesterId());
//		marksForm.setSubjectId(marksForm.getSubjectId());
//		marksForm.setExamTypeId(marksForm.getExamTypeId());

//		mav.addObject("marksForm", marksForm);
//		getDataList(mav, studentMarksDto);

		return mav;
	}

	/*@PostMapping("/student/studentMarks/add")
	public ModelAndView save(@ModelAttribute("marksForm") StudentMarksForm marksForm) {

		List<StudentMarksDto> marksDtos = marksForm.getMarksDtos();

		ModelAndView mav = new ModelAndView("studentMarks", "marksForm",
				new StudentMarksForm());

		mav.addObject(buttonValue, "Save");
//		mav.addObject(action, Role + "/student/studentMarks/add");
		mav.addObject(action, Role + "/student/studentMarks");
		mav.addObject("marksAction", Role + "/student/studentMarks/add");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");

		List<StudentMarksDto> insertedStudentDtos =
				studentMarksService.saveAll(marksDtos);

		if(insertedStudentDtos.isEmpty()) {
			mav.addObject(message, "Problem in inserting records");
		} else {
			mav.addObject(message, "Student Marks Records Updated");
		}

		StudentMarksForm newMarksForm = new StudentMarksForm();
		marksForm.setMarksDtos(insertedStudentDtos);
		mav.addObject("marksForm", newMarksForm);

		StudentMarksDto studentMarksDto = new StudentMarksDto();
		studentMarksDto.setBatchId(marksForm.getBatchId());
		studentMarksDto.setBranchId(marksForm.getBranchId());
		studentMarksDto.setYearId(marksForm.getYearId());
		studentMarksDto.setSemesterId(marksForm.getSemesterId());
		studentMarksDto.setSubjectId(marksForm.getSubjectId());
		studentMarksDto.setExamTypeId(marksForm.getExamTypeId());

		mav.addObject("studentMarksDto", studentMarksDto);

		getSelectedData(mav, studentMarksDto);

//		mav.addObject("selectedBatchId", marksForm.getBatchId());
//		mav.addObject("selectedBranchId", marksForm.getBranchId());
//		mav.addObject("selectedYearId", marksForm.getYearId());
//		mav.addObject("selectedSemesterId", marksForm.getSemesterId());
//		mav.addObject("selectedSubjectId", marksForm.getSubjectId());
//		mav.addObject("selectedExamTypeId", marksForm.getExamTypeId());
//
//		Optional<Map<Long,String>> semestersOptional =
//				studentService.getSemesetersByYearId(marksForm.getYearId());
//
//		if(semestersOptional.isPresent()) {
//			mav.addObject("semesters", semestersOptional.get());
//		}
//
//		Optional<Map<Long,String>> subjectsOptional =
//				studentMarksService.getSubjectsByBranchIdAndSemesterId(marksForm.getBranchId(),
//						marksForm.getSemesterId());
//
//		if(subjectsOptional.isPresent()) {
//			mav.addObject("subjects", subjectsOptional.get());
//		}

		marksForm.setMarksDtos(studentMarksService.getStudentsMarksList(studentMarksDto));
		marksForm.setBatchId(marksForm.getBatchId());
		marksForm.setBranchId(marksForm.getBranchId());
		marksForm.setYearId(marksForm.getYearId());
		marksForm.setSemesterId(marksForm.getSemesterId());
		marksForm.setSubjectId(marksForm.getSubjectId());
		marksForm.setExamTypeId(marksForm.getExamTypeId());

		mav.addObject("marksForm", marksForm);

		getInitialData(mav);
		getDataList(mav, studentMarksDto);

		return mav;
	}*/

	@PostMapping("/student/studentMarks/edit/{studentMarksId}/{operation}")
	public ModelAndView edit(@PathVariable("studentMarksId") long studentMarksId,
			@PathVariable("operation") String operation) {

		ModelAndView mav = new ModelAndView("studentMarks");
		mav.addObject(role, Role);
		mav.addObject(buttonValue, operation.toUpperCase() );
		mav.addObject(action,Role + "/student/studentMarks/"+operation);
		mav.addObject(method,"POST");
//		mav.addObject("marksAction", Role + "/student/studentMarks/add");
		mav.addObject(showTab, "edit");
		mav.addObject("operation", "/"+operation);

		Optional<StudentMarksDto> studentMarksOptional =
				studentMarksService.findById(studentMarksId);
		if(studentMarksOptional.isPresent()) {
			StudentMarksDto studentMarksDto = studentMarksOptional.get();

			mav.addObject("studentMarksDto", studentMarksDto);
		}
		return mav;
	}

	@PostMapping("/student/studentMarks/update")
	public ModelAndView update(@ModelAttribute("studentMarksDto") StudentMarksDto studentMarksDto) {

		ModelAndView mav = new ModelAndView("studentMarks", "studentMarksDto",
				studentMarksDto);

		mav.addObject(action,Role + "/student/studentMarks");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");
		getInitialData(mav);

		if(studentMarksDto.getMaxMarks() < studentMarksDto.getMarks()) {

			mav.addObject(message, "Marks not updated, Please check values");
			mav.addObject(buttonValue, "UPDATE" );
			mav.addObject(action,Role + "/student/studentMarks/update");
			mav.addObject(showTab, "edit");
			return mav;
		}

		Optional<StudentMarksDto> studentMarksDtoOptional =
				studentMarksService.update(studentMarksDto);

		if(studentMarksDtoOptional.isPresent()) {
			mav.addObject(message, "Student Marks updated successfully");
			StudentMarksDto updatedMarksDto = studentMarksDtoOptional.get();
			getSelectedData(mav, updatedMarksDto);
//			getDataList(mav, updatedMarksDto);

			List<StudentMarksDto> studentsAttendanceList = studentMarksService.getStudentsMarksList(studentMarksDto);
			studentMarksDto.setMarksDtos(studentsAttendanceList);
		}

		return mav;
	}

	@PostMapping("/student/studentMarks/delete")
	public ModelAndView delete(@ModelAttribute("studentDto") StudentMarksDto studentMarksDto) {

		ModelAndView mav = new ModelAndView("studentMarks", "studentMarksDto",
				studentMarksDto);

		mav.addObject(action, Role + "/student/studentMarks");
		mav.addObject(method,"POST");
		mav.addObject(showTab, "list");
		getInitialData(mav);

		Optional<StudentMarksDto> studentMarksDtoOptional = studentMarksService.delete(studentMarksDto);

		if(studentMarksDtoOptional.isPresent()) {

			mav.addObject(message, "Problem in deletion");
			mav.addObject(buttonValue, "DELETE" );
			mav.addObject(action,Role + "/student/studentMarks/delete");
			mav.addObject(showTab, "edit");
			return mav;

		}  else {
			mav.addObject(message, "Student Marks deleted successfully");
			getSelectedData(mav, studentMarksDto);
//			getDataList(mav, studentMarksDto);

			List<StudentMarksDto> studentsAttendanceList = studentMarksService.getStudentsMarksList(studentMarksDto);
			studentMarksDto.setMarksDtos(studentsAttendanceList);
		}

		return mav;
	}


	// Ajax request
	@GetMapping(value = "/student/studentMarks/{type}/{value1}/{value2}")
	public ResponseEntity<String> find(@PathVariable("type") String type,
			@PathVariable("value1") String value1,
			@PathVariable("value2") String value2) {

		String result = null;

		if(type.equalsIgnoreCase("getSemesters")) {

			Optional<Map<Long,String>> semestersOptional =
					studentService.getSemesetersByYearId(Long.parseLong(value1));

			if(semestersOptional.isPresent()) {

				result =
						"{\"semestersExists\":\"true\", \"semesters\":\""+semestersOptional.get()+"\"  }";
			} else {
				result = "{\"semestersExists\":\"false\" }";
			}
		} else if(type.equalsIgnoreCase("getSubjects")) {

			Optional<Map<Long,String>> subjectsOptional =
					studentMarksService.getSubjectsByBranchIdAndSemesterId(value1,
							Long.parseLong(value2));

			if(subjectsOptional.isPresent()) {

				result =
						"{\"subjectsExists\":\"true\", \"subjects\":\""+subjectsOptional.get()+"\"  }";
			} else {
				result = "{\"subjectsExists\":\"false\" }";
			}
		} else if(type.equalsIgnoreCase("getExamTypes")) {

			Optional<Map<Long,String>> examTypesOptional =
					studentMarksService.getExamTypesBySubjectId(Long.parseLong(value1));

			if(examTypesOptional.isPresent()) {

				result =
						"{\"examTypesExists\":\"true\", \"examTypes\":\""+examTypesOptional.get()+"\"  }";
			} else {
				result = "{\"examTypesExists\":\"false\" }";
			}
		}



		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	private void getInitialData(ModelAndView mav) {
		mav.addObject(role, Role);
		mav.addObject("batches", studentService.getBatches());
		mav.addObject("branches", studentService.getBranches());
		mav.addObject("years", studentService.getYears());
//		mav.addObject("examTypes", studentMarksService.getExamTypes());
	}

	private void getSelectedData(ModelAndView mav, StudentMarksDto studentMarksDto) {
		mav.addObject("selectedBatchId", studentMarksDto.getBatchId());
		mav.addObject("selectedBranchId", studentMarksDto.getBranchId());
		mav.addObject("selectedYearId", studentMarksDto.getYearId());
		mav.addObject("selectedSemesterId", studentMarksDto.getSemesterId());
		mav.addObject("selectedSubjectId", studentMarksDto.getSubjectId());
		mav.addObject("selectedExamTypeId", studentMarksDto.getExamTypeId());

		Optional<Map<Long,String>> semestersOptional =
				studentService.getSemesetersByYearId(studentMarksDto.getYearId());

		if(semestersOptional.isPresent()) {
			mav.addObject("semesters", semestersOptional.get());
		}

		Optional<Map<Long,String>> subjectsOptional =
				studentMarksService.getSubjectsByBranchIdAndSemesterId(studentMarksDto.getBranchId(),
						studentMarksDto.getSemesterId());

		if(subjectsOptional.isPresent()) {
			mav.addObject("subjects", subjectsOptional.get());
		}

		Optional<Map<Long,String>> examTypesOptional =
				studentMarksService.getExamTypesBySubjectId(studentMarksDto.getExamTypeId());

		if(examTypesOptional.isPresent()) {
			mav.addObject("examTypes", examTypesOptional.get());
		}
	}

//	private void getDataList(ModelAndView mav, StudentMarksDto studentMarksDto) {
//
//		studentMarksDto.setMarksDtos(studentMarksService.getStudentsMarksList(studentMarksDto));
//
//		StudentMarksForm marksForm = new StudentMarksForm();
//		marksForm.setMarksDtos(studentMarksService.getStudentsMarksList(studentMarksDto));
//		marksForm.setBatchId(studentMarksDto.getBatchId());
//		marksForm.setBranchId(studentMarksDto.getBranchId());
//		marksForm.setYearId(studentMarksDto.getYearId());
//		marksForm.setSemesterId(studentMarksDto.getSemesterId());
//		marksForm.setSubjectId(studentMarksDto.getSubjectId());
//		marksForm.setExamTypeId(studentMarksDto.getExamTypeId());
//
//		mav.addObject("marksForm", marksForm);
//	}

//	public class StudentMarksForm {
//
//		private long batchId;
//		private String branchId;
//		private long yearId;
//		private long semesterId;
//		private long subjectId;
//		private long examTypeId;
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
//		public long getSubjectId() {
//			return subjectId;
//		}
//
//		public void setSubjectId(long subjectId) {
//			this.subjectId = subjectId;
//		}
//
//		public long getExamTypeId() {
//			return examTypeId;
//		}
//
//		public void setExamTypeId(long examTypeId) {
//			this.examTypeId = examTypeId;
//		}
//
//		private List<StudentMarksDto> marksDtos;
//
//		public List<StudentMarksDto> getMarksDtos() {
//			return marksDtos;
//		}
//
//		public void setMarksDtos(List<StudentMarksDto> marksDtos) {
//			this.marksDtos = marksDtos;
//		}
//
//		@Override
//		public String toString() {
//			return "StudentMarksForm{" +
//					"batchId=" + batchId +
//					", branchId='" + branchId + '\'' +
//					", yearId=" + yearId +
//					", semesterId=" + semesterId +
//					", subjectId=" + subjectId +
//					", examTypeId=" + examTypeId +
//					", marksDtos=" + marksDtos +
//					'}';
//		}
//	}

}
