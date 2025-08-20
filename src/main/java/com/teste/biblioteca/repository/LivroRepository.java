package com.teste.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.teste.biblioteca.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

	
}
