package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

@Service
public class CrudCargoService {

	private Boolean system = true;
	private final CargoRepository cargoRepository;

	public CrudCargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public void init(Scanner scanner) {
		
		while(system) {
			System.out.println("Qual ação de cargo desejar executar?");
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
		System.out.println("Descrição do cargo");
		String descricao = scanner.next();
		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);

		System.out.println("Salvo com sucesso");

	}
	
	public void atualizar(Scanner scanner) {
		System.out.println("ID do cargo");
		int id = scanner.nextInt();
		System.out.println("Descrição do cargo");
		String descricao = scanner.next();
		Cargo cargo = new Cargo();
		cargo.setId(id);
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);

		System.out.println("Salvo com sucesso");

	}
	

	
	public void visualizar() {
		
		Iterable<Cargo>  cargos = cargoRepository.findAll();
		cargos.forEach(cargo -> System.out.println(cargo));
		System.out.println("Listado com sucesso");

	}
	
	public void deletar(Scanner scanner) {
		System.out.println("ID do cargo");
		int id = scanner.nextInt();
		cargoRepository.deleteById(id);

		System.out.println("Deletado com sucesso");

	}

}
