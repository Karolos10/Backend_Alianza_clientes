package com.alianzaRestFullstack.restfullstackjavaangular.repository;

import com.alianzaRestFullstack.restfullstackjavaangular.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findBySharedKey(String sharedKey);

    @Query("SELECT c From Cliente c WHERE c.nombreCompleto LIKE %?1%"
            + "OR c.sharedKey LIKE %?1%"
            + "OR c.email LIKE %?1%"
            + "OR c.telefono LIKE %?1%"
            + "OR c.fecha LIKE %?1%")
    public List<Cliente> findAll(String palabraClave);
}
