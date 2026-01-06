package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Student;

@Service
public interface StudentService {

List<Student> findByRoll(long roll);
	
	int save(Student s);
	
	int update(Student s);
	
	int delete(long roll);
	
	List<Student> findAll();
}
