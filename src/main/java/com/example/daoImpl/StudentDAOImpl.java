package com.example.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.model.Student;

@Repository
public class StudentDAOImpl implements com.example.DAO.StudentDAO {

	@Autowired
	JdbcTemplate template;
	
	RowMapper<Student> rowMapper=(rs,rowNum) ->{
		
		Student s=new Student();		
		s.setRoll(rs.getLong("roll"));
		s.setName(rs.getString("name"));
		s.setCity(rs.getString("city"));
		return s;
	};
	
	public List<Student> findByRoll(long roll) {
		System.out.println("findByRoll Roll is=="+roll);
	    String mySql = "SELECT * FROM students WHERE roll = ?";
	    return template.query(mySql, new Object[]{roll}, rowMapper);
	}


	@Override
	public int save(Student s) {
		String mySql="insert into students (roll,name,city) values (?,?,?)";
		return template.update(mySql,s.getRoll(),s.getName(),s.getCity());
	}

	@Override
	public int update(Student s) {
		String mysql="update students set name=?,city=? where roll=? ";
		System.out.println("update mysql= "+mysql);
		return template.update(mysql,s.getName(),s.getCity(),s.getRoll());
	}
	public int delete(long roll) {
	    String mysql = "DELETE FROM students WHERE roll = ?";
	    System.out.println("delete mysql= " + mysql);
	    return template.update(mysql, roll);
	}


	@Override
	public List<Student> findAll() {
		 String mySql = "SELECT * FROM students";
		    return template.query(mySql, rowMapper);
	}

	

}
