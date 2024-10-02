package com.example.eservice.models.service.seguridad;

import com.example.eservice.models.entity.seguridad.PasswordResetToken;

public interface IPasswordResetTokenService {

	public PasswordResetToken findByToken(String token);
	public PasswordResetToken save(PasswordResetToken passwordResetToken);

}