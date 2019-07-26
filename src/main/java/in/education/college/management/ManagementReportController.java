package in.education.college.management;

import in.education.college.dto.StudentMarksDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/management")
public class ManagementReportController {

	private ManagementReportService managementReportService;

	private static String Role = "/management";

	@Autowired
	ManagementReportController(final ManagementReportService managementReportService ) {
		this.managementReportService = managementReportService;
	}

	@GetMapping("/managementReport")
	public ModelAndView getData() {

		ModelAndView mav = new ModelAndView("managementReport");

		mav.addObject("batchWise", managementReportService.findByBatchWise());
		mav.addObject("branchWise", managementReportService.findByBranchWise());
		mav.addObject("yearWise", managementReportService.findByYearWise());
		mav.addObject("yearSemWise", managementReportService.findByYearSemWise());
		mav.addObject("batchBranchWise", managementReportService.findByBatchBranchWise());
		mav.addObject("batchBranchYearWise", managementReportService.findByBatchBranchYearWise());
		mav.addObject("batchBranchYearSemWise", managementReportService.findByBatchBranchYearSemWise());
		mav.addObject("empQlyWise", managementReportService.findByEmpQlyWise());

		return mav;
	}



}
