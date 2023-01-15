package com.TheParkers.TheParkers.service;

import java.util.List;

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
	public Departamento BuscarDepartamentoPorId(int id) {
		return repDepartamento.findById(id).get();
	}

	@Override
	public boolean GuardarDepartamento(Departamento departamento) {
		return false;
	}

	@Override
	public boolean BorrarDepartamentoPorId(int id) {
		return false;
	}
}
