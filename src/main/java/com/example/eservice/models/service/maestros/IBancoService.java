package com.example.eservice.models.service.maestros;

import java.util.List;

import com.example.eservice.models.entity.maestros.Banco;

public interface IBancoService {
	List<Banco> getAll();
}