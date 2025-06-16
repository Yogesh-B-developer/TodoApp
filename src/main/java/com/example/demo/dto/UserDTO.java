package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.TaskEntity;

public class UserDTO {

	
	private String name;
	
    private String email;
	 
	private String role;
	
	private List<TaskEntity> tasks;

	public List<TaskEntity> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskEntity> tasks) {
		this.tasks = tasks;
	}



	public UserDTO(String name, String email, String role, List<TaskEntity> tasks) {
		super();
		this.name = name;
		this.email = email;
		this.role = role;
		this.tasks = tasks;
	}

	public UserDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", email=" + email + ", role=" + role + "]";
	}
	
	

}
