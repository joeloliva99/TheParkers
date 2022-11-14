package com.ThParkers.TheParkers.service;
import com.ThParkers.TheParkers.model.Cliente;
import com.ThParkers.TheParkers.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;
    public ClienteService (ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    public List<Cliente> findAllClientes() {
        return clienteRepository.findAll();
    }

    public boolean save(Cliente cliente) {
        clienteRepository.saveAndFlush(cliente);
        Optional<Cliente> clienteOptional = clienteRepository.findClienteByRutCliente(cliente.getRutCliente());
        return clienteOptional.isPresent();
    }

    public Optional<Cliente> findClienteByRUT(String rutCliente) {
        return clienteRepository.findClienteByRutCliente(rutCliente);
    }

    public boolean deleteClienteById(int idClient) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(idClient);
        if (clienteOptional.isPresent()){
            clienteRepository.deleteById(idClient);
            return true;
        } else {
            return false;
        }
    }

}
