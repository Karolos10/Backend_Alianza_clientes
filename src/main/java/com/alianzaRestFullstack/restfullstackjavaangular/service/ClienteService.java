package com.alianzaRestFullstack.restfullstackjavaangular.service;

import com.alianzaRestFullstack.restfullstackjavaangular.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Cliente save(Cliente cliente);
    List<Cliente> findAll();
    List<Cliente> findByWordKey(String palabraClave);
    Cliente findById (Integer id);
    //Cliente findByNombreCompleto(String nombreCompleto);
    void deleteById(Integer id);
    Cliente update(Cliente cliente);
    Cliente getBySharedKey(String sharedKey);
}
