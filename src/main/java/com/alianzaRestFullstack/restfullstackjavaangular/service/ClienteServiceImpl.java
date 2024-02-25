package com.alianzaRestFullstack.restfullstackjavaangular.service;

import com.alianzaRestFullstack.restfullstackjavaangular.entity.Cliente;
import com.alianzaRestFullstack.restfullstackjavaangular.exception.ResourceNotFoundException;
import com.alianzaRestFullstack.restfullstackjavaangular.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService{


    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        cliente.setSharedKey(createSharedKey(cliente));
        return clienteRepository.save(cliente);
    }

    private String createSharedKey(Cliente cliente){
        if(cliente.getNombreCompleto() == null){
            try {
                throw new IllegalAccessException("El nombre no puede estar vacio");

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        String[] nombreCompleto = cliente.getNombreCompleto().toLowerCase().split(" ");
        char nombreApellido = nombreCompleto[0].charAt(0);
        return String.valueOf(nombreApellido).concat(nombreCompleto[1]);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public List<Cliente> findByWordKey(String palabraClave){
        if(palabraClave != null){
            //return clienteRepository.findAll(palabraClave);
        }
        return clienteRepository.findAll(palabraClave);
    }

    @Override
    public Cliente findById(Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () ->{
                    throw  new ResourceNotFoundException("Cliente con id" +id+ "no se encuentra en la base de datos");
                }
        );
        return cliente;
    }

    @Override
    public void deleteById(Integer id) {
        clienteRepository.deleteById(id);

    }

    @Override
    public Cliente update(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente getBySharedKey(String sharedKey) {
        return clienteRepository.findBySharedKey(sharedKey).orElseThrow();
    }

}
