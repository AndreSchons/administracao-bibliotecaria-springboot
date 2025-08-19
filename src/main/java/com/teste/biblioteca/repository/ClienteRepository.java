package com.teste.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.teste.biblioteca.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	
}
