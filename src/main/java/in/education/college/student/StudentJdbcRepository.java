package in.education.college.student;

import in.education.college.common.util.DBUtils;
import in.education.college.common.util.GeneralQueries;
import in.education.college.model.Gender;
import in.education.college.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentJdbcRepository {

	private DBUtils dbUtils;

	@Autowired
	StudentJdbcRepository(DBUtils dbUtils) {
		this.dbUtils = dbUtils;
	}

	int addStudentData(Student student) {

		PreparedStatement pstmt;

		String sql;
		String studentId = "";
		int index = 0;
		int result = 0;
		String photoName = "";
		ResultSet rs;

		Statement stmt = dbUtils.getDBStatement();

		try {

			rs = stmt.executeQuery("select nextval('student_details_student_id_seq') as student_id");

			if (rs.next()) {
				studentId = rs.getString("student_id");
			}

//			if(student.getPhoto()!=null && !"".equals(student.getPhoto().toString().trim()) && student.getPhoto().getBytes().length >0 ) {

//			if(student.getPhoto()!=null && !"".equals(student.getPhoto().toString().trim()) && student.getPhoto().length() >0 ) {
//				photoName =
//						"stu_"+studentId+"_"+student.getPhoto().getOriginalFilename().toLowerCase().replace("_","-");

				photoName ="stu_"+studentId+"_"+ student.getPhotoName();
//			}

			sql = " insert into student_details " +
					" (" +
					" student_id, " +
					" student_name, father_name, dob, " +
					" doj, address, email, " +
					" guardian_mobile, " +
//					" blood_group_id, " +
					" branch_id," +
					" roll_no, mobile_no, mother_name, " +
					" gender, academic_year_id, aadhar, " +
					" height, joining_year_id " +
//					"" + ((student.getPhoto().getBytes().length >0) ? ",photo_name, photo" :
//					"" + ((student.getPhoto().length() >0) ? ",photo_name, photo" :
//					"" )+
					" , photo_name, photo) " +
					" values (" +
					" ?, "+
//					" ?, ?, STR_TO_DATE(?,'%d/%m/%Y'), " +
					" ?, ?, ?, " +
//					" STR_TO_DATE(?,'%d/%m/%Y'), ?, ?, " +
					" ?, ?, ?, " +
					" ?, ?, ?," + //?" +
					" ?, ?, ?," +
					" ?, ?, ?," +
					" ?, ?" +
//					"" + ((student.getPhoto().getBytes().length >0) ? ",? , ?" : "" ) +
//					"" + ((student.getPhoto().length() >0) ? ",? , ?" : "" ) +
					" ,? , ? "  +
					" )";

			pstmt = dbUtils.getDBConnection().prepareStatement(sql);

			pstmt.setObject( (++index), Long.parseLong(studentId));

			pstmt.setObject( (++index), student.getName());
			pstmt.setObject((++index), student.getFatherName());
			pstmt.setObject((++index), student.getDob());

			pstmt.setObject((++index), student.getDoj());
			pstmt.setObject((++index), student.getAddress());
			pstmt.setObject((++index), student.getEmail());

			pstmt.setObject((++index), student.getParentPhoneNo());
//			pstmt.setObject((++index), student.getBloodGroupId());
			pstmt.setObject((++index), student.getBranchId());

			pstmt.setObject((++index), student.getRollNo());
			pstmt.setObject((++index), student.getMobileNo());
			pstmt.setObject((++index), student.getMotherName());

			pstmt.setObject((++index), student.getGender());
			pstmt.setObject((++index), student.getBatchId());
			pstmt.setObject((++index), student.getAadharNo());

			pstmt.setObject((++index), student.getHeight());
			pstmt.setObject((++index), student.getJoiningYearId());

//			if(student.getPhoto().getBytes().length >0) {
//			if(student.getPhoto().length() >0) {
				pstmt.setObject((++index), photoName);
//				pstmt.setBlob((++index), student.getPhoto().getInputStream());
				pstmt.setBytes((++index), student.getPhoto());
//			}

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	List getSpecifiedStudentsData(Student studentData) {

		String sql;
		ResultSet rs;
		ArrayList<Student> studentList = new ArrayList<>();

		Statement stmt = dbUtils.getDBStatement();

		try {

			String branchPart = " ";

			if(!studentData.getBranchId().equals("0")) {
				branchPart = " and b.branch_id ='" + studentData.getBranchId() + "' ";
			}

			sql = "select sd.student_id, ay.year, b.branch_name,  roll_no, student_name, father_name," +
//					" date_format(dob,'%d/%m/%Y') as dob, " +
					" dob, " +
//					" date_format(doj,'%d/%m/%Y') as doj, " +
					" doj," +
					" photo, photo_name" +
					" from student_details sd" +
					" left join academic_year ay on (ay.year_id = sd.academic_year_id)" +
					" left join branch b on (b.branch_id = sd.branch_id)" +
					" where" +
					" ay.year_id = " +studentData.getBatchId()+
					" " + branchPart +
					" order by branch_name, roll_no, student_name";

			rs = stmt.executeQuery(sql);

			while(rs != null && rs.next())
			{
				Student student = new Student();
				student.setStudentId(rs.getInt("student_id"));
//				student.setBatch(rs.getString("year"));

//				student.setBranch(rs.getString("branch_name"));
				student.setRollNo(rs.getString("roll_no"));

				student.setName(rs.getString("student_name"));
				student.setFatherName(rs.getString("father_name"));
				student.setDob(rs.getDate("dob"));
				student.setDoj(rs.getDate("doj"));
//				student.setPhotoName(rs.getString("photo_name").split("_")[2]);
				student.setPhotoName(rs.getString("photo_name"));
				student.setPhoto(rs.getBytes("photo"));

//				student.setPhotoData(new String(Base64.getEncoder().encode(rs.getBytes("photo"))) );

//				byte[] b = rs.getBytes("photo");

//				student.setPhotoData(new String(Base64.getEncoder().encode(rs.getBytes("photo"))) );
//				student.setPhotoData(new String(Base64.getEncoder().encode(b) ));

				studentList.add(student);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentList;
	}

	Student getStudentData(int studentId) {

		String sql;
		ResultSet rs;
		Student student = new Student();

		Statement stmt = dbUtils.getDBStatement();

		try {

			sql = " select student_id, student_name, father_name,  " +
//					" date_format(dob,'%d/%m/%Y') as dob, " +
					" dob, " +
//					" date_format(doj,'%d/%m/%Y') as doj" +
					" doj, photo, photo_name, " +
					" aadhar, address, email, " +
					" guardian_mobile, (select blood_group from blood_group where " +
					"blood_group_id = sd.blood_group_id) as blood_group, " +
					"academic_year_id, " +
					" branch_id, is_active, roll_no, " +
					" mobile_no, mother_name, " +
					" gender, mentor_name, address," +
					" coalesce(joining_year_id,1) as joining_year_id, blood_group_id, " +
					"height " +
					" from student_details sd " +
					" where student_id = " + studentId;

			rs = stmt.executeQuery(sql);

			if(rs.next())
			{
				student.setStudentId(rs.getInt("student_id"));
				student.setName(rs.getString("student_name"));
				student.setFatherName(rs.getString("father_name"));
//				student.setDob(rs.getString("dob"));
				student.setDob(rs.getDate("dob"));
//				student.setDoj(rs.getString("doj"));
				student.setDoj(rs.getDate("doj"));
				student.setPhotoName(rs.getString("photo"));
				student.setAadharNo(rs.getString("aadhar"));
				student.setEmail(rs.getString("email"));
				student.setParentPhoneNo(rs.getString("guardian_mobile"));
//				student.setBloodGroup(rs.getString("blood_group"));
				student.setJoiningYearId(rs.getInt("joining_year_id"));
				student.setBatchId(rs.getInt("academic_year_id"));
				student.setBranchId(rs.getString("branch_id"));
				student.setRollNo(rs.getString("roll_no"));
				student.setMobileNo(rs.getString("mobile_no"));
				student.setMotherName(rs.getString("mother_name"));
				student.setGender(Gender.valueOf(rs.getString("gender")));
				student.setAddress(rs.getString("address"));
				student.setJoiningYearId(rs.getInt("joining_year_id"));
//				student.setBloodGroupId(rs.getInt("blood_group_id"));
				student.setHeight(rs.getInt("height"));
				//student.setPhotoName(rs.getString("photo_name").split("_")[2]);
				student.setPhotoName(rs.getString("photo_name"));

//				student.setPhotoData(new String(Base64.getEncoder().encode(rs.getBytes("photo"))) );
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return student;
	}

	int updateStudentData(Student student) {

		PreparedStatement pstmt;

		String sql;
		int index = 0;
		int result = 0;
		String photoName = "";

		try {

	sql = " update student_details " +
			" set " +
			" student_name = ?, " +
			" father_name = ?, " +
			" dob = STR_TO_DATE(?,'%d/%m/%Y'), " +
			" doj = STR_TO_DATE(?,'%d/%m/%Y'), " +
			" address = ?," +
			" email = ?, " +
			" guardian_mobile = ?, " +
//			" blood_group_id = ?, " +
			" branch_id = ?," +
			" roll_no = ?, " +
			" mobile_no = ?, " +
			" mother_name = ?, " +
			" gender = ?, " +
			" academic_year_id = ?, " +
			" aadhar = ?, " +
			" height = ?, " +
			" joining_year_id = ? " +
//			((student.getPhoto().getBytes().length >0) ? ",photo_name = ?, photo = ?" :"" )+
//			((student.getPhoto().length() >0) ? ",photo_name = ?, photo = ?" :"" )+
			((student.getPhoto().length >0) ? ",photo_name = ?, photo = ?" :"" )+
			"  " +
			" where student_id = ?";

			pstmt = dbUtils.getDBConnection().prepareStatement(sql);

			pstmt.setObject( (++index), student.getName());
			pstmt.setObject((++index), student.getFatherName());
			pstmt.setObject((++index), student.getDob());

			pstmt.setObject((++index), student.getDoj());
			pstmt.setObject((++index), student.getAddress());
			pstmt.setObject((++index), student.getEmail());

			pstmt.setObject((++index), student.getParentPhoneNo());
//			pstmt.setObject((++index), student.getBloodGroupId());
			pstmt.setObject((++index), student.getBranchId());

			pstmt.setObject((++index), student.getRollNo());
			pstmt.setObject((++index), student.getMobileNo());
			pstmt.setObject((++index), student.getMotherName());

			pstmt.setObject((++index), student.getGender());
			pstmt.setObject((++index), student.getBatchId());
			pstmt.setObject((++index), student.getAadharNo());

			pstmt.setObject((++index), student.getHeight());
			pstmt.setObject((++index), student.getJoiningYearId());

//			if(student.getPhoto().getBytes().length >0) {
//			if(student.getPhoto().length() >0) {
			if(student.getPhoto().length >0) {
				pstmt.setObject((++index), photoName);
//				pstmt.setBlob((++index), student.getPhoto().getInputStream());
				pstmt.setBytes((++index), student.getPhoto());
//				pstmt.setBlob((++index), student.getPhoto());
			}

			pstmt.setObject((++index), student.getStudentId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	int deleteStudentData(Student student) {

		String sql;
		int saResult = 1;
		int smResult = 1;
		int sdResult = 1;

		Statement stmt = dbUtils.getDBStatement();

		try {
			if(dbUtils.recordExists(GeneralQueries.isAttendanceExists(student.getStudentId()))) {
				saResult = stmt.executeUpdate(GeneralQueries.deleteStudentAttendance(student.getStudentId()));
			}

			if(dbUtils.recordExists(GeneralQueries.isMarksExists(student.getStudentId()))) {
				smResult = stmt.executeUpdate(GeneralQueries.deleteStudentMarks(student.getStudentId()));
			}

			if(saResult > 0 && smResult > 0 ) {
				sdResult = stmt.executeUpdate(GeneralQueries.deleteStudenData(student.getStudentId()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sdResult;
	}

}
