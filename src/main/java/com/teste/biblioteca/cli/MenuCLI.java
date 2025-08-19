package com.teste.biblioteca.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.teste.biblioteca.model.Cliente;
import com.teste.biblioteca.model.Livro;
import com.teste.biblioteca.repository.ClienteRepository;
import com.teste.biblioteca.repository.LivroRepository;

import jakarta.transaction.Transactional;

import java.util.Scanner;

@Component
public class MenuCLI implements CommandLineRunner {

	private final ClienteRepository clienteRepo;
	private final LivroRepository livroRepo;

	public MenuCLI(ClienteRepository clienteRepo, LivroRepository livroRepo) {
		this.clienteRepo = clienteRepo;
		this.livroRepo = livroRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		boolean menuAtivo = true;

		while (menuAtivo) {
			System.out.println("1 - Cadastrar Clientes");
			System.out.println("2 - Listar Clientes");
			System.out.println("3 - Cadastrar Livros");
			System.out.println("4 - Listar Livros");
			System.out.println("5 - Associar livro a cliente");
			System.out.println("0 - Sair");
			int option = sc.nextInt();
			sc.nextLine();

			switch (option) {
			case 1:
				System.out.print("Nome do cliente: ");
				String nome = sc.nextLine();
				System.out.println("Senha do cliente: ");
				String senha = sc.nextLine();

				Cliente cliente = new Cliente(nome, senha);
				clienteRepo.save(cliente);
				break;
			case 2:
				clienteRepo.findAll().forEach(c -> {
					System.out.println(c);
					if (c.getLivros().isEmpty()) {
						System.out.println("Nenhum livro emprestado!");
					} else {
						System.out.println("Livros emprestados: ");
						c.getLivros().forEach(l -> System.out.println(l));
					}
				});
				break;
			case 3:
				System.out.print("Nome do livro: ");
				String nomeLivro = sc.nextLine();
				System.out.print("Autor do livro: ");
				String autor = sc.nextLine();
				System.out.print("Idade recomendada: ");
				int idadeRecomendada = sc.nextInt();
				sc.nextLine();

				Livro livro = new Livro(nomeLivro, autor, idadeRecomendada);
				livroRepo.save(livro);
				break;
			case 4:
				livroRepo.findAll().forEach(l -> System.out.println(l));
				break;
			case 5:
				System.out.println("Clientes disponiveis: ");
				clienteRepo.findAll().forEach(c -> System.out.println(c));
				System.out.println("Selecione o ID do cliente: ");
				Long id = sc.nextLong();
				sc.nextLine();
				
				System.out.println("Livros disponiveis: ");
				livroRepo.findAll().forEach(l -> System.out.println(l));
				System.out.println("Selecione o ID do livro: ");
				Long idLivro = sc.nextLong();
				sc.nextLine();
				
				Cliente c = clienteRepo.findById(id).orElse(null);
				Livro l = livroRepo.findById(idLivro).orElse(null);
			
				if(c != null && l != null) {
					l.setCliente(c);
					livroRepo.save(l);
					System.out.println("Livro emprestado!");
				} else {
					System.out.println("Livro ou cliente nao encontrado!");
				}
				break;
			case 0:
				menuAtivo = false;
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Digite um numero valido!");
				;
			}
		}
		sc.close();
	}
}
