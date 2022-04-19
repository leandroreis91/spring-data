package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeTrabalhoService {

	private Boolean system = true;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

	public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
	}

	public void init(Scanner scanner) {
		
		while(system) {
			System.out.println("Qual ação de unidade de trabalho desejar executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Deletar");
			
			int action = scanner.nextInt();
			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizar();
				break;
			case 4:
				deletar(scanner);
				break;

			default:
				system = false;
				break;
			}
		}
	}

	public void salvar(Scanner scanner) {
		System.out.println("Descrição da unidade");
		String descricao = scanner.next();
		System.out.println("Endereço da unidade");
		String endereco = scanner.next();
		UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
		unidadeTrabalho.setDescricao(descricao);
		unidadeTrabalho.setEndereco(endereco);
		unidadeTrabalhoRepository.save(unidadeTrabalho);

		System.out.println("Salvo com sucesso");

	}
	
	public void atualizar(Scanner scanner) {
		System.out.println("ID");
		int id = scanner.nextInt();
		System.out.println("Descrição");
		String descricao = scanner.next();
		System.out.println("Endereço");
		String endereco = scanner.next();
		
		
		UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
		unidadeTrabalho.setId(id);
		unidadeTrabalho.setDescricao(descricao);
		unidadeTrabalho.setEndereco(endereco);
		unidadeTrabalhoRepository.save(unidadeTrabalho);

		System.out.println("Salvo com sucesso");

	}
	

	
	public void visualizar() {
		
		Iterable<UnidadeTrabalho>  unidadeTrabalhos = unidadeTrabalhoRepository.findAll();
		unidadeTrabalhos.forEach(unidadeTrabalho -> System.out.println(unidadeTrabalho));
		System.out.println("Listado com sucesso");

	}
	
	public void deletar(Scanner scanner) {
		System.out.println("ID");
		int id = scanner.nextInt();
		unidadeTrabalhoRepository.deleteById(id);

		System.out.println("Deletado com sucesso");

	}

}
