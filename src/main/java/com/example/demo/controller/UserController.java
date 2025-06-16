package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TaskDTO;
import com.example.demo.entity.TaskEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping("/getTasks")
	public ResponseEntity<List<TaskDTO>> getAllUsers(@RequestParam String email){
		 List<TaskDTO> taskDTOs = taskRepository.getUserTasks(email)
	                .stream()
	                .map(task -> new TaskDTO(task.getId(),task.getTitle(), task.getDescription()))
	                .toList();
	        
	        System.out.print(taskDTOs);

	        return ResponseEntity.ok(taskDTOs);

	}
	@PostMapping("/add")
	public ResponseEntity<List<TaskDTO>> addTask(@RequestBody TaskEntity taskEntity) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    if (authentication != null && authentication.isAuthenticated()) {
	        String email = authentication.getName();

	        UserEntity user = userRepository.findById(email)
	                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

	        taskEntity.setUserEntity(user);
	        taskRepository.save(taskEntity);

	        List<TaskDTO> taskDTOs = taskRepository.getUserTasks(email)
	                .stream()
	                .map(task -> new TaskDTO(task.getId(),task.getTitle(), task.getDescription()))
	                .toList();
	        
	        System.out.print(taskDTOs);

	        return ResponseEntity.ok(taskDTOs);
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
	}


	 
	 @DeleteMapping("/delete/{id}")
	 public ResponseEntity<Boolean> deleteTaskById(@PathVariable Long id) {
		 taskRepository.deleteById(id);
		 boolean isDeleted = taskRepository.findById(id).isEmpty();
		 return ResponseEntity.ok(isDeleted);
	 }
	
}
