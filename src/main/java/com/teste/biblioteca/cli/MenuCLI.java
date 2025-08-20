package com.teste.biblioteca.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.teste.biblioteca.model.Cliente;
import com.teste.biblioteca.model.Livro;
import com.teste.biblioteca.repository.ClienteRepository;
import com.teste.biblioteca.repository.LivroRepository;

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

		while (true) {
			System.out.println("BEM VINDO");
			System.out.println("1-Usuario");
			System.out.println("2-Administrador");
			int firstOption = sc.nextInt();
			sc.nextLine();

			if (firstOption == 2) {
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

						if (c != null && l != null) {
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
			} else {
				int optionUser;
				do {
					System.out.println("1 - Login");
					System.out.println("2 - Cadastro");
					System.out.println("3 - Sair");
					optionUser = sc.nextInt();
					sc.nextLine();
					if (optionUser == 1) {
						System.out.print("Digite seu nome: ");
						String nome = sc.nextLine();
						System.out.println("Digite sua senha: ");
						String senha = sc.nextLine();

						Cliente cliente = clienteRepo.findByNomeAndSenha(nome, senha);

						if (cliente != null) {
							int optionUserLog;
							do {
								System.out.println("Bem vindo " + nome);
								System.out.println("1 - Emprestar Livro");
								System.out.println("2 - Devolver Livro");
								System.out.println("3 - Ver Meus Livros");
								System.out.println("4 - Sair");
								optionUserLog = sc.nextInt();
								sc.nextLine();

								switch (optionUserLog) {
								case 1:
									System.out.println("Livros disponiveis: ");
									livroRepo.findAll().forEach(l -> System.out.println(l));
									System.out.println("Selecione o ID do livro: ");
									Long idLivro = sc.nextLong();

									Livro l = livroRepo.findById(idLivro).orElse(null);
									if (l != null) {
										l.setCliente(cliente);
										livroRepo.save(l);
										System.out.println("Livro emprestado com sucesso!");
									} else {
										System.out.println("ID invalido tente novamente!");
									}
									break;
								case 2:
									System.out.println("Livros para devolver: ");
									cliente.getLivros().forEach(livros -> System.out.println(livros));
									System.out.println("Digite o ID do livro a ser devolvido: ");
									idLivro = sc.nextLong();
									Livro livro = livroRepo.findById(idLivro).orElse(null);

									if (livro != null && livro.getCliente() != null) {
										livro.setCliente(null);
										livroRepo.save(livro);
										System.out.println("Livro entregue.");
									}
									break;
								case 3:
									System.out.println("Livros emprestados: ");
									cliente.getLivros().forEach(liv -> System.out.println(liv));
									break;
								case 4:
									System.out.println("Saindo...");
									break;
								}
							} while (optionUserLog != 4);

						} else {
							System.out.println("Usuario ou senha incorreta!");
						}
					} else if (optionUser == 2) {
						System.out.println("Digite seu nome: ");
						String nome = sc.nextLine();
						System.out.println("Digite sua senha: ");
						String senha = sc.nextLine();
						Cliente cliente = new Cliente(nome, senha);
						clienteRepo.save(cliente);
					} else {
						System.out.println("Saindo...");
					}
				} while (optionUser != 3);
			}
		}
	}
}
