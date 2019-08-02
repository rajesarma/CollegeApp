package in.education.college.management;

import in.education.college.model.Batch;
import in.education.college.model.Branch;
import in.education.college.model.Year;
import in.education.college.model.repository.BatchRepository;
import in.education.college.model.repository.BranchRepository;
import in.education.college.model.repository.EmployeeRepository;
import in.education.college.model.repository.StudentAttendanceRepository;
import in.education.college.model.repository.YearRepository;
import in.education.college.student.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ManagementReportService {

	private StudentRepository studentRepository;
	private EmployeeRepository employeeRepository;
	private StudentAttendanceRepository studentAttendanceRepository;

	private BranchRepository branchRepository;
	private BatchRepository batchRepository;
	private YearRepository yearRepository;

	private static final Logger log = LoggerFactory.getLogger(ManagementReportService.class);

	List<String> getBranchNames() {
		return StreamSupport.stream(branchRepository.findAll().spliterator(), false)
				.map(Branch::getBranchName)
				.collect(Collectors.toList());
	}

	List<String> getBatchNames() {
		return StreamSupport.stream(batchRepository.findAll().spliterator(), false)
				.map(Batch::getBatchName)
				.collect(Collectors.toList());
	}

	List<String> getYearNames() {
		return StreamSupport.stream(yearRepository.findAll().spliterator(), false)
				.map(Year::getYear)
				.collect(Collectors.toList());
	}

	List<Map<String,String>> findByBatchWise() {
		return studentRepository.findByBatchWise();
	}
	List<Map<String,String>> findByBranchWise() {
		return studentRepository.findByBranchWise();
	}
	List<Map<String,String>> findByYearWise() {
		return studentRepository.findByYearWise();
	}
	List<Map<String,String>> findByYearSemWise() { return studentRepository.findByYearSemWise(); }
	List<Map<String,String>> findByBatchBranchWise() { return studentRepository.findByBatchBranchWise();}
	List<Map<String,String>> findByBatchBranchYearWise() {
		return studentRepository.findByBatchBranchYearWise();
	}
	List<Map<String,String>> findByBatchBranchYearSemWise() {
		return studentRepository.findByBatchBranchYearSemWise();
	}
	List<Map<String,String>> findByEmpQlyWise() {
		return employeeRepository.findByEmpQlyWise();
	}

	Map<String, Object> findByYearSemWiseData() {

		List<Map<String,String>> yearSemList = studentRepository.findByYearSemWise();

		Map<String, Object> semMap = new HashMap<>();
		List<Integer> sem1List = new ArrayList<>();
		List<Integer> sem2List = new ArrayList<>();
		List<String> yearList = new ArrayList<>();

		for(Map<String, String> map : yearSemList) {
			if(!yearList.contains(map.get("year"))) {
				yearList.add(map.get("year"));
			}

			if(Integer.parseInt(map.get("semester_id")) % 2 == 0) {
				sem1List.add(Integer.parseInt(map.get("student_count")));
			} else {
				sem2List.add(Integer.parseInt(map.get("student_count")));
			}
		}
		semMap.put("sem1List", sem1List);
		semMap.put("sem2List", sem2List);
		semMap.put("yearList", yearList);

		semMap.put("yearSemList", yearSemList);

		return semMap;
	}

	Map<String, Object> findByBatchBranchWiseData() {

		List<Map<String,String>> batchBranchList = studentRepository.findByBatchBranchWise();
		Map<String, Object> batchBranchMapData = new HashMap<>();
		Map<String, Object> batchWiseMap = new HashMap<>();

		for(String batch : getBatchNames()) {
			for(Map batchBranchMap : batchBranchList) {
				if(batchBranchMap.containsValue(batch) && batch.equals(batchBranchMap.get("batch")) ) {
					if(batchWiseMap.get(batch) != null) {
						batchWiseMap.put(batch,
								Integer.parseInt(batchWiseMap.get(batch)+"") + Integer.parseInt(batchBranchMap.get(
								"student_count")+""));
					} else {
						batchWiseMap.put(batch, batchBranchMap.get("student_count"));
					}
				}
			}
		}

		Map<String, Object> branchWiseMap = new HashMap<>();
		int[] branchDataArray;
		int index;

		for(String branch : getBranchNames()) {
			branchDataArray = new int[getBranchNames().size()];

			for(Map batchBranchMap : batchBranchList) {
				if(batchBranchMap.containsValue(branch) ) {
					if(branch.equals(batchBranchMap.get("branch"))) {
						index = getBatchNames().indexOf(batchBranchMap.get("batch"));
						branchDataArray[index] = Integer.parseInt(batchBranchMap.get("student_count")+"");
					}
				}
			}
			branchWiseMap.put(branch, Arrays.stream(branchDataArray).boxed().collect(Collectors.toList()) );
		}

		batchBranchMapData.put("batchWiseMap", batchWiseMap);
		batchBranchMapData.put("branchWiseMap", branchWiseMap);

		return batchBranchMapData;
	}

	Map<String, Object> findByBatchBranchYearWiseData() {

		List<Map<String,String>> batchBranchYearList = studentRepository.findByBatchBranchYearWise();
		Map<String, Object> batchBranchYearMapData = new HashMap<>();
		Map<String, Object> batchWiseMap = new HashMap<>();


		for(String batch : getBatchNames()) {
			for(Map batchBranchMap : batchBranchYearList) {
				if(batchBranchMap.containsValue(batch) && batch.equals(batchBranchMap.get("batch")) ) {
					if(batchWiseMap.get(batch) != null) {
						batchWiseMap.put(batch,
								Integer.parseInt(batchWiseMap.get(batch)+"") + Integer.parseInt(batchBranchMap.get("student_count")+""));
					} else {
						batchWiseMap.put(batch, batchBranchMap.get("student_count"));
					}
				}
			}
		}

		Map<String, Object> branchWiseMap = new HashMap<>();
		int[] branchDataArray;
		int index;

		for(String branch : getBranchNames()) {
			branchDataArray = new int[getBranchNames().size()];

			for(Map batchBranchMap : batchBranchYearList) {
				if(batchBranchMap.containsValue(branch) ) {
					if(branch.equals(batchBranchMap.get("branch"))) {
						index = getBatchNames().indexOf(batchBranchMap.get("batch"));
						branchDataArray[index] = Integer.parseInt(batchBranchMap.get("student_count")+"") + branchDataArray[index];
					}
				}
			}
			branchWiseMap.put(branch, Arrays.stream(branchDataArray).boxed().collect(Collectors.toList()) );
		}

		Map<String, Object> yearWiseMap = new HashMap<>();
		int[] yearDataArray;

		for(String year : getYearNames()) {
			yearDataArray = new int[getBatchNames().size()];

			for(Map batchBranchYearMap : batchBranchYearList) {
				if(batchBranchYearMap.containsValue(year) ) {
					if(year.equals(batchBranchYearMap.get("year"))) {
						index = getBatchNames().indexOf(batchBranchYearMap.get("batch"));
						yearDataArray[index] = Integer.parseInt(batchBranchYearMap.get("student_count")+"") + yearDataArray[index];
					}
				}
			}
			yearWiseMap.put(year, Arrays.stream(yearDataArray).boxed().collect(Collectors.toList()) );
		}

		batchBranchYearMapData.put("batchWiseMap", batchWiseMap);
		batchBranchYearMapData.put("branchWiseMap", branchWiseMap);
		batchBranchYearMapData.put("yearWiseMap", yearWiseMap);

		return batchBranchYearMapData;
	}

	Map<String,Long> findByStudentsCount() {

		return new TreeMap<>(studentRepository.findByStudentsCount());
	}

	List<Map<String,String>> findByBranchWiseCount() {
//		return new TreeMap<>(studentRepository.findByBranchWiseCount());
		return studentRepository.findByBranchWiseCountData();
	}

	List<Map<String,String>> findByRecentStudents() {
		return studentRepository.findByRecentStudents();
	}

	List<Map<String, String>> getBranchWiseStudents(String branchId) {

		return studentRepository.findByBranchWiseStudents(branchId);
	}

	List<Map<String, String>> getBatchWiseStudentsAttendances() {
		List<Map<String, String>> branchWiseStudents = studentAttendanceRepository.findByBatchWiseStudentsAttendances();

		return branchWiseStudents;
	}



	@Autowired
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Autowired
	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Autowired
	public void setBranchRepository(BranchRepository branchRepository) {
		this.branchRepository = branchRepository;
	}

	@Autowired
	public void setBatchRepository(BatchRepository batchRepository) {
		this.batchRepository = batchRepository;
	}

	@Autowired
	public void setYearRepository(YearRepository yearRepository) {
		this.yearRepository = yearRepository;
	}

	@Autowired
	public void setStudentAttendanceRepository(StudentAttendanceRepository studentAttendanceRepository) {
		this.studentAttendanceRepository = studentAttendanceRepository;
	}
}
