package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DAO.StudentDAO;
import com.example.model.Student;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	StudentDAO studentDAO;

	@Autowired
	JdbcTemplate template;

	RowMapper<Student> rowMapper = (rs, rowNum) -> {
		Student s = new Student();
		s.setRoll(rs.getLong("roll"));
		s.setName(rs.getString("name"));
		s.setCity(rs.getString("city"));
		return s;
	};

	@GetMapping("/getStudent")
	public ResponseEntity<?> getAllStudents() {
	    System.out.println("call getStudent");
	    
	    try {
	        List<Student> students = studentDAO.findAll();
	        
	        if (students == null || students.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .body("No students found");
	        }

	        return ResponseEntity.ok(students);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error fetching student data: " + e.getMessage());
	    }
	}


	@PostMapping("/saveStudent")
	public ResponseEntity<?> addStudent(@RequestBody Student s) {
		System.out.println("Save call initiated for student with roll: " + s.getRoll());

		try {
			// Check if a student already exists with the same roll
			List<Student> existingStudents = studentDAO.findByRoll(s.getRoll());

			if (existingStudents != null && !existingStudents.isEmpty()) {
				System.out.println("Student with roll " + s.getRoll() + " already exists");
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("Student already exists with roll: " + s.getRoll());
			}

			// Save the new student
			studentDAO.save(s);
			System.out.println("Student saved successfully: " + s.getRoll());
			return ResponseEntity.ok(s);

		} catch (Exception e) {
			System.err.println("Error saving student: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving student");
		}
	}

	/*
	 * @PutMapping("/student/{id}") public ResponseEntity<String>
	 * updateStudent(@PathVariable("id") long id,@RequestBody Student s) { int
	 * result =studentDAO.update(s); return new
	 * ResponseEntity<>("Data updated Successfully",HttpStatus.OK); }
	 */
	@PutMapping("/updateStudent/{roll}")
	public ResponseEntity<String> updateStudent(@PathVariable("roll") long roll, @RequestBody Student updatedStudent) {
		System.out.println("First Roll is ==" + roll);
		List<Student> existingStudents = studentDAO.findByRoll(roll);
		System.out.println("Roll is==" + roll);
		if (existingStudents != null && !existingStudents.isEmpty()) {
			Student existingStudent = existingStudents.get(0);
			existingStudent.setName(updatedStudent.getName());
			existingStudent.setCity(updatedStudent.getCity());
			existingStudent.setRoll(updatedStudent.getRoll());
			System.out.println("existingStudent == " + existingStudent.getRoll() + " " + existingStudent.getName());
			studentDAO.update(existingStudent);
			return ResponseEntity.ok("Student updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with roll: " + roll);
		}
	}

	@DeleteMapping("/deleteStudent/{roll}")
	public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable("roll") long roll) {
		System.out.println("Delete call 2221");
		int result = studentDAO.delete(roll);

		Map<String, String> response = new HashMap();
		if (result > 0) {
			response.put("message", "Student with roll " + roll + " deleted successfully.");
			return ResponseEntity.ok(response);
		} else {
			response.put("error", "Student with roll " + roll + " not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

}
