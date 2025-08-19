package com.teste.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Livros")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String autor;
	@Column(nullable = false)
	private int idadeRecomendada;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	public Livro() {
		// TODO Auto-generated constructor stub
	}

	public Livro(String nome, String autor, int idadeRecomendada) {
		this.nome = nome;
		this.autor = autor;
		this.idadeRecomendada = idadeRecomendada;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getIdadeRecomendada() {
		return idadeRecomendada;
	}

	public void setIdadeRecomendada(int idadeRecomendada) {
		this.idadeRecomendada = idadeRecomendada;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String toString() {
		return "Livro: " + nome + ", Autor: " + autor;
	}
}
