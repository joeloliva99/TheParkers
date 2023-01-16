package com.TheParkers.TheParkers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.TheParkers.TheParkers.model.Departamento;
import com.TheParkers.TheParkers.repository.DepartamentoRepository;

@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService{
	@Autowired
	DepartamentoRepository repDepartamento;

	@Override
	public List<Departamento> buscarTodosLosDepartamentos() {
		return repDepartamento.findAll();
	}

	
	@Override
	public Optional<Departamento> findDepartamentoById(int id) {
		return repDepartamento.findDepartamentoById(id);
	}

	@Override
	public boolean GuardarDepartamento(Departamento departamento) {
		repDepartamento.saveAndFlush(departamento);
		Optional<Departamento>departamentoOptional = repDepartamento.findDepartamentoById(departamento.getDepartamentoID());
		if(departamentoOptional.isEmpty()){
			repDepartamento.saveAndFlush(departamento);
			departamentoOptional = repDepartamento.findDepartamentoById(departamento.getDepartamentoID());
			return departamentoOptional.isPresent();
		}else {
			return false;
		}
	}
	
	@Override
	public boolean BorrarDepartamentoPorId(int id) {
		Optional<Departamento> departamentoOptional = repDepartamento.findById(id);
        if (departamentoOptional.isPresent()){
            repDepartamento.deleteById(id);
            return true;
        } else {
            return false;
        }
	}	
}
