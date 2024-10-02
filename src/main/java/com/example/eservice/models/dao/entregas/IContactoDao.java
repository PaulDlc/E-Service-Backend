package com.example.eservice.models.dao.entregas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eservice.models.entity.entregas.ContactoEntrega;

public interface IContactoDao extends JpaRepository<ContactoEntrega, Integer> {
	
}
