package com.alianzaRestFullstack.restfullstackjavaangular.controller;

import com.alianzaRestFullstack.restfullstackjavaangular.entity.Cliente;
import com.alianzaRestFullstack.restfullstackjavaangular.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public Cliente save(@RequestBody Cliente cliente){
        return clienteService.save(cliente);
    }

    @GetMapping
    public List<Cliente> findAll(){
        return clienteService.findAll();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> findBywordker(@RequestParam String palabraClave){
        List<Cliente> clientes = clienteService.findByWordKey(palabraClave);
        return null;
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
