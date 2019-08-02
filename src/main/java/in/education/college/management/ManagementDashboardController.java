package in.education.college.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/management")
public class ManagementDashboardController {

	private ManagementReportService managementReportService;

	private static String Role = "/management";

	@Autowired
	ManagementDashboardController(final ManagementReportService managementReportService ) {
		this.managementReportService = managementReportService;
	}

	@GetMapping("/managementDashboard")
	public ModelAndView getData() {

		ModelAndView mav = new ModelAndView("managementDashboard");

		mav.addObject("batches", managementReportService.getBatchNames());
		mav.addObject("branches", managementReportService.getBranchNames());

		mav.addObject("batchWise", managementReportService.findByBatchWise());
		mav.addObject("branchWise", managementReportService.findByBranchWise());
		mav.addObject("yearWise", managementReportService.findByYearWise());
		mav.addObject("yearSemWise", managementReportService.findByYearSemWise());
		mav.addObject("batchBranchWise", managementReportService.findByBatchBranchWise());
		mav.addObject("batchBranchYearWise", managementReportService.findByBatchBranchYearWise());
		mav.addObject("batchBranchYearSemWise", managementReportService.findByBatchBranchYearSemWise());
		mav.addObject("empQlyWise", managementReportService.findByEmpQlyWise());

		mav.addObject("yearSemWiseData", managementReportService.findByYearSemWiseData());
		mav.addObject("batchBranchWiseData", managementReportService.findByBatchBranchWiseData());
		mav.addObject("batchBranchYearWiseData", managementReportService.findByBatchBranchYearWiseData());

		mav.addObject("dataCount", managementReportService.findByStudentsCount());
		mav.addObject("branchWiseCount", managementReportService.findByBranchWiseCount());
		mav.addObject("recentStudents", managementReportService.findByRecentStudents());
		mav.addObject("batchWiseAttendances", managementReportService.getBatchWiseStudentsAttendances());




		return mav;
	}

	//	Ajax Request
	@GetMapping("/managementDashboard/branchWiseData/{branchId}")
	public ResponseEntity<?> getBrachWiseStudents(@PathVariable("branchId") String branchId) {

		List<Map<String, String>> branchWiseStudents = managementReportService.getBranchWiseStudents(branchId);

		StringBuffer data = new StringBuffer();
		data.append("<div class='ajax'><table class=\"table table-bordered \">");
		data.append("<thead>");
		data.append("<tr>");
		data.append("<th>S. No.</th>");
		data.append("<th>Student Name</th>");
		data.append("<th>Roll No.</th>");
		data.append("<th>Father Name</th>");
		data.append("<th>Batch</th>");
		data.append("<th>Current Year</th>");
		data.append("<th>Current Semester</th>");
		data.append("</tr>");
		data.append("</thead>");
		data.append("<tbody>");

		int index = 0;
		for(Map map : branchWiseStudents) {
			data.append("<tr>");
			data.append("<td style=\"text-align: center\">" + (++index) +"</td>");
			data.append("<td>" + map.get("student_name")+"</td>");
			data.append("<td>" + map.get("roll_no")+"</td>");
			data.append("<td>" + map.get("father_name")+"</td>");
			data.append("<td>" + map.get("batch_name")+"</td>");
			data.append("<td>" + map.get("current_year")+"</td>");
			data.append("<td>" + map.get("current_semester")+"</td>");
			data.append("</tr>");
		}
		data.append("</tbody>");
		data.append("</table></div>");

		return new ResponseEntity<>(data.toString(), HttpStatus.OK);
	}

}
