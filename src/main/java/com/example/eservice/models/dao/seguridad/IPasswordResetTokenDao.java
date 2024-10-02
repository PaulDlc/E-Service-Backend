package com.example.eservice.models.dao.seguridad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eservice.models.entity.seguridad.PasswordResetToken;

public interface IPasswordResetTokenDao extends JpaRepository<PasswordResetToken, Integer>{
	
	@Query("SELECT t FROM PasswordResetToken t WHERE t.token = ?1")
	PasswordResetToken findByToken(String token);
}