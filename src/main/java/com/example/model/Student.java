package com.example.model;

public class Student {

	private long roll;
	private String name;
	private String city;
	
	
	public Student()
	{
		
	}
	
	
	public Student(long roll, String name, String city) {
		super();
		this.roll = roll;
		this.name = name;
		this.city = city;
	}


	public long getRoll() {
		return roll;
	}


	public void setRoll(long roll) {
		this.roll = roll;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}
	
	
	
	
}
