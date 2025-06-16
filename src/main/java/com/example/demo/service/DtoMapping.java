package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;

@Service
public class DtoMapping {

	public UserDTO userEntityToDTO(UserEntity user) {
		return new UserDTO(user.getName(),user.getEmail(),user.getRole(),user.getTasks());
	}
}
