package com.example.eservice.models.service.entregas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.entregas.IContactoDao;
import com.example.eservice.models.entity.entregas.ContactoEntrega;

@Service
public class ContactoImpl implements IContactoService {
	
	@Autowired
	private IContactoDao contactoDao;

	@Override
	public ContactoEntrega save(ContactoEntrega contacto) {
		return contactoDao.save(contacto);
	}

}
