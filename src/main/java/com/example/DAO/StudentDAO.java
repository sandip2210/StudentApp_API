package com.example.DAO;

import java.util.List;

import com.example.model.Student;

public interface StudentDAO {
	
	List<Student> findByRoll(long roll);
	
	int save(Student s);
	
	int update(Student s);
	
	int delete(long roll);
	
	List<Student> findAll();
	


}
