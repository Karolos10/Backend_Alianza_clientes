package com.alianzaRestFullstack.restfullstackjavaangular.controller;

import com.alianzaRestFullstack.restfullstackjavaangular.entity.Cliente;
import com.alianzaRestFullstack.restfullstackjavaangular.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/createClientes")
    public Cliente save(@RequestBody Cliente cliente){
        return clienteService.save(cliente);
    }

    @GetMapping
    public List<Cliente> findAll(){
        return clienteService.findAll();
    }

    @PutMapping
    public Cliente updateCliente(@RequestBody Cliente cliente){
        Cliente clienteDB = clienteService.findById(cliente.getId());
        clienteDB.setNombreCompleto(cliente.getNombreCompleto());
        clienteDB.setEmail(cliente.getEmail());
        clienteDB.setTelefono(cliente.getTelefono());
        clienteDB.setFecha(cliente.getFecha());
        return clienteService.update(clienteDB);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        clienteService.deleteById(id);
    }

    @GetMapping("bySharedKey/{sharedKey}")
    public Cliente findBySharedKet(@PathVariable String sharedKey){
        return clienteService.getBySharedKey(sharedKey);
    }
}
