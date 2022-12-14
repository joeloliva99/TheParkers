package com.TheParkers.TheParkers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TheParkers.TheParkers.model.Lavado;
import com.TheParkers.TheParkers.repository.RepositorioLavado;


@Service
@Transactional
public class LavadoServiceImpl implements LavadoService{

	@Autowired
	RepositorioLavado repLavado;
	
	public LavadoServiceImpl (RepositorioLavado repLavado) {
		this.repLavado = repLavado;
		
	}

	@Override
	public List<Lavado> buscarTodosLosLavados() {
		return repLavado.findAll();
	}

	@Override
	public Lavado BuscarLavadoPorId(int id) {
		return repLavado.findById(id).get();
	}
	
	@Override
	public Optional<Lavado>findLavadoById(int id_lavado){
		return repLavado.findLavadoById(id_lavado);
	}

}
