package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>{

	

	@Query("SELECT t FROM TaskEntity t WHERE t.userEntity.email = :email") 
	List<TaskEntity> getUserTasks(@Param("email") String email);

}
