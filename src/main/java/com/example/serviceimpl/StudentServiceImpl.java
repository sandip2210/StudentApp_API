package com.example.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.StudentDAO;
import com.example.model.Student;
import com.example.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentDAO studentDAO;

	@Override
	public List<Student> findByRoll(long roll) {
		System.out.println("************StudentServiceImpl*************");
		return studentDAO.findByRoll(roll);
	}

	@Override
	public int save(Student s) {
		// TODO Auto-generated method stub
		return studentDAO.save(s);
	}

	@Override
	public int update(Student s) {
		// TODO Auto-generated method stub
		return studentDAO.update(s);
	}

	@Override
	public int delete(long roll) {
		// TODO Auto-generated method stub
		return studentDAO.delete(roll);
	}

	@Override
	public List<Student> findAll() {
		System.out.println("***************112222StudentServiceImpl***************");
		return studentDAO.findAll();
	}

}
