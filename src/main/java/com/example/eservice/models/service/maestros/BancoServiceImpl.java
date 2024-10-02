package com.example.eservice.models.service.maestros;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.maestros.IBancoDao;
import com.example.eservice.models.entity.maestros.Banco;

@Service
public class BancoServiceImpl implements IBancoService {
	@Autowired
	private IBancoDao bancoDao;
	
	@Override
	public List<Banco> getAll() {
		return bancoDao.findAll();
	}

}