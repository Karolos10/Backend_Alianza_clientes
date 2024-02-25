package com.alianzaRestFullstack.restfullstackjavaangular.controller;

import com.alianzaRestFullstack.restfullstackjavaangular.entity.Cliente;
import com.alianzaRestFullstack.restfullstackjavaangular.service.ClienteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/exportar-csv")
    public ResponseEntity<String> exportarCSV() {
        List<Cliente> clientes = clienteService.findAll();

        try {
            // Convertir la lista de clientes a un formato CSV (puedes usar alguna librería como OpenCSV)
            // Aquí se muestra un ejemplo simple
            String csvData = clientes.stream()
                    .map(cliente -> cliente.getNombreCompleto() + "," + cliente.getEmail() + "," + cliente.getFecha() + "," + cliente.getSharedKey() + "," + cliente.getTelefono())
                    .collect(Collectors.joining("\n"));

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clientes.csv");

            return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al exportar a CSV", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public Cliente save(@RequestBody Cliente cliente){
        return clienteService.save(cliente);
    }

    @GetMapping
    public List<Cliente> findAll(){
        return clienteService.findAll();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Cliente>> findBywordker(@RequestParam String keyWord){
        List<Cliente> clientes = clienteService.findByWordKey(keyWord);
        return ResponseEntity.ok(clientes);
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

    @GetMapping("/{id}")
    public Cliente getById(@PathVariable Integer id){
        return clienteService.findById(id);
    }

    @GetMapping("bySharedKey/{sharedKey}")
    public Cliente findBySharedKet(@PathVariable String sharedKey){
        return clienteService.getBySharedKey(sharedKey);
    }
}
