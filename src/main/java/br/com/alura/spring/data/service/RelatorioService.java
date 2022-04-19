package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class RelatorioService {

	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final FuncionarioRepository funcionarioRepository;

	public RelatorioService(FuncionarioRepository unidadeTrabalhoRepository) {
		this.funcionarioRepository = unidadeTrabalhoRepository;
	}

	public void init(Scanner scanner) {
		
		while(system) {
			System.out.println("Qual ação de unidade de trabalho desejar executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca Funcionário por nome");
			System.out.println("2 - Busca Funcionário por nome, data contratacao e salário maior");
			System.out.println("3 - Busca Funcionário data contratacao");
			
			int action = scanner.nextInt();
			switch (action) {
			case 1:
				buscaFuncionarioPorNome(scanner);
				break;
			case 2:
				buscaFuncionarioPorNomeSalarioMaiorData(scanner);
				break;
			case 3:
				buscaFuncionarioDataContratacao(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}

	public void buscaFuncionarioPorNome(Scanner scanner) { 
		System.out.println("Informe o nome do funcionário");
		String nome = scanner.next();
		List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);
		funcionarios.forEach(System.out::println);

		System.out.println("Relatório pesquisado com sucesso");

	}
	

	public void buscaFuncionarioPorNomeSalarioMaiorData(Scanner scanner) { 
		System.out.println("Informe o nome do funcionário");
		String nome = scanner.next();

		System.out.println("Informe a data de contratação");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		System.out.println("Informe o salário");
		Double salario = scanner.nextDouble();
		
		List<Funcionario> funcionarios = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		funcionarios.forEach(System.out::println);

		System.out.println("Relatório pesquisado com sucesso");

	}
	
	public void buscaFuncionarioDataContratacao(Scanner scanner) { 

		System.out.println("Informe a data de contratação");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		List<Funcionario> funcionarios = funcionarioRepository.findDataContratacaoMaior(localDate);
		funcionarios.forEach(System.out::println);

		System.out.println("Relatório pesquisado com sucesso");

	}
	


}
