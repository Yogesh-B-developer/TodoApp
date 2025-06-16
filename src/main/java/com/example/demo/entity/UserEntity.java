package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="user")
public class UserEntity {

	
	private String name;
	@Id
	private String email;
	
	private String password; 
	
	private String role;
	
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
	
	private List<TaskEntity> tasks = new ArrayList<>();

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<TaskEntity> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskEntity> tasks) {
		this.tasks = tasks;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserEntity(String name, String email, String password, String role, List<TaskEntity> tasks) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.tasks = tasks;
	}

	public UserEntity() {
		super();
	}

	@Override
	public String toString() {
		return "UserEntity [name=" + name + ", email=" + email + ", password=" + password + ", role=" + role + "]";
	}

	
	
	
}
