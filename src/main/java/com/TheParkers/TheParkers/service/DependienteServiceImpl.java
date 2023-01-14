package com.TheParkers.TheParkers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TheParkers.TheParkers.model.Dependiente;
import com.TheParkers.TheParkers.repository.DependienteRepository;

@Service
@Transactional
public class DependienteServiceImpl implements DependienteService{
	
	@Autowired
	DependienteRepository repDependiente;

	@Override
	public List<Dependiente> buscarTodosLosDependientes() {
		return repDependiente.findAll();
	}

	@Override
	public Dependiente BuscarDependientePorRut(String rutDependiente) {
		return repDependiente.findDependienteByRutDependiente(rutDependiente).get();
	}

	@Override
	public boolean Guardar(Dependiente dependiente) {
		repDependiente.saveAndFlush(dependiente);
		Optional<Dependiente>dependienteOptional = repDependiente.findDependienteByRutDependiente(dependiente.getRutDependiente());
		return dependienteOptional.isPresent();
		
	}

	@Override
	public boolean BorrarDependientePorId(int id_dependiente) {
		Optional<Dependiente>dependienteOptional = repDependiente.findById(id_dependiente);
		
		if(dependienteOptional.isPresent()) {
			repDependiente.deleteById(id_dependiente);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Optional<Dependiente> findDependienteByRut(String rutDependiente) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}


	
	
	
}
