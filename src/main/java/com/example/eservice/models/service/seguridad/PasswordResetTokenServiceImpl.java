package com.example.eservice.models.service.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.seguridad.IPasswordResetTokenDao;
import com.example.eservice.models.entity.seguridad.PasswordResetToken;

@Service
public class PasswordResetTokenServiceImpl implements IPasswordResetTokenService {
	
	@Autowired
	private IPasswordResetTokenDao passwordResetTokenDao;

	@Override
	public PasswordResetToken findByToken(String token) {
		return passwordResetTokenDao.findByToken(token);
	}

	@Override
	public PasswordResetToken save(PasswordResetToken passwordResetToken) {
		return passwordResetTokenDao.save(passwordResetToken);
	}

}