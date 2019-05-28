package in.education.college.student;

import in.education.college.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentJdbcService {

	private StudentJdbcRepository studentRepository;

	@Autowired
	StudentJdbcService(StudentJdbcRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public int addStudentData(Student student) {

//		Blob file = student.getPhotoFile();

		/*try {
			student.setPhoto(file.getBytes());
			student.setPhotoName(file.getOriginalFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		return studentRepository.addStudentData(student);
	}

	public List getSpecifiedStudentsData(Student studentData) {

		return studentRepository.getSpecifiedStudentsData(studentData);
	}

	public Student getStudentData(int studentId) {
		Student studentData = studentRepository.getStudentData(studentId);
		return studentData;
	}

	public int updateStudentData(Student student) {

		return studentRepository.updateStudentData(student);
	}

	public int deleteStudentData(Student student) {

		return studentRepository.deleteStudentData(student);
	}

}
