package com.alianzaRestFullstack.restfullstackjavaangular.controller;

import com.alianzaRestFullstack.restfullstackjavaangular.entity.Cliente;
import com.alianzaRestFullstack.restfullstackjavaangular.service.ClienteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("api/clients")
public class ClienteController {

    private final ClienteService clienteService;

    private static final Logger logger = Logger.getLogger(ClienteController.class.getName());


    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/exportar-csv")
    public ResponseEntity<String> exportarCSV() {
        List<Cliente> clientes = clienteService.findAll();

        try {
            String csvData = clientes.stream()
                    .map(cliente -> cliente.getNombreCompleto() + "," + cliente.getEmail() + "," + cliente.getFecha() + "," + cliente.getSharedKey() + "," + cliente.getTelefono())
                    .collect(Collectors.joining("\n"));

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clientes.csv");

            logger.info("Se exportaron " + clientes.size() + " clientes a CSV correctamente.");

            return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.severe("Error al exportar a CSV: " + e.getMessage());
            return new ResponseEntity<>("Error al exportar a CSV", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        try {
            Cliente savedCliente = clienteService.save(cliente);
            logger.info("Cliente guardado con éxito: " + savedCliente.getId());
            return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.severe("Error al guardar el cliente: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public List<Cliente> findAll(){
        return clienteService.findAll();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Cliente>> findBywordker(@RequestParam String keyWord){

        try {
            List<Cliente> clientes = clienteService.findByWordKey(keyWord);

            if (clientes.isEmpty()) {
                logger.info("No se encontraron clientes con la palabra clave: " + keyWord);
            } else {
                logger.info("Clientes encontrados con la palabra clave '" + keyWord + "': " + clientes.size());
            }

            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            logger.severe("Error al buscar clientes por palabra clave: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente){

        try {
            Cliente clienteDB = clienteService.findById(cliente.getId());
            if (clienteDB == null) {
                // Cliente no encontrado
                logger.warning("No se encontró el cliente con ID " + cliente.getId() + " para actualizar.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Actualizar los datos del cliente
            clienteDB.setNombreCompleto(cliente.getNombreCompleto());
            clienteDB.setEmail(cliente.getEmail());
            clienteDB.setTelefono(cliente.getTelefono());
            clienteDB.setFecha(cliente.getFecha());

            // Guardar la actualización
            Cliente updatedCliente = clienteService.update(clienteDB);

            logger.info("Cliente actualizado con éxito. ID: " + updatedCliente.getId());

            return new ResponseEntity<>(updatedCliente, HttpStatus.OK);
        } catch (Exception e) {
            logger.severe("Error al actualizar el cliente: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        try {
            // Eliminar el cliente por ID
            clienteService.deleteById(id);

            // Registrar información de la eliminación en el log
            logger.info("Cliente eliminado con éxito. ID: " + id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.severe("Error al eliminar el cliente con ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
