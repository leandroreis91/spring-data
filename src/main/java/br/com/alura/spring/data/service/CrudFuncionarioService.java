package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final CargoRepository cargoRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	private final FuncionarioRepository funcionarioRepository;

	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, UnidadeTrabalhoRepository unidadeTrabalhoRepository, CargoRepository cargoRepository) {
		
		this.funcionarioRepository = funcionarioRepository;
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
		this.cargoRepository = cargoRepository;
	}

	public void init(Scanner scanner) {
		
		while(system) {
			System.out.println("Qual ação de funcionário desejar executar?");
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
				visualizar(scanner);
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
		System.out.println("Nome");
		String nome = scanner.next();
		
		System.out.println("CPF");
		String cpf = scanner.next();
		
		System.out.println("Salário");
		Double salario = scanner.nextDouble();
		
        System.out.println("Data de contracao");
        String dataContratacao = scanner.next();

        System.out.println("ID do Cargo");
        Integer cargoId = scanner.nextInt();
		
        List<UnidadeTrabalho> unidades = unidade(scanner);
        
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);        
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        funcionario.setCargo(cargo.get());
        funcionario.setUnidadeTrabalhos(unidades);
		funcionarioRepository.save(funcionario);

		System.out.println("Salvo com sucesso");

	}
	
	private List<UnidadeTrabalho> unidade(Scanner scanner) {
        Boolean isTrue = true;
        List<UnidadeTrabalho> unidades = new ArrayList<>();

        while (isTrue) {
            System.out.println("Id da Unidade (Para sair digite 0)");
            Integer unidadeId = scanner.nextInt();

            if(unidadeId != 0) {
                Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
                unidades.add(unidade.get());
            } else {
                isTrue = false;
            }
        }

        return unidades;
    }
	
	public void atualizar(Scanner scanner) {
		System.out.println("ID");
		int id = scanner.nextInt();
		
		System.out.println("Nome");
		String nome = scanner.next();
		
		System.out.println("CPF");
		String cpf = scanner.next();
		
		System.out.println("Salário");
		Double salario = scanner.nextDouble();
		
        System.out.println("Data de contracao");
        String dataContratacao = scanner.next();

        System.out.println("ID do Cargo");
        Integer cargoId = scanner.nextInt();
        
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);        
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        funcionario.setCargo(cargo.get());
		funcionarioRepository.save(funcionario);

		System.out.println("Salvo com sucesso");

	}
	

	
	public void visualizar(Scanner scanner) {

		System.out.println("Informe a página que você deseja visualizar");
		Integer page = scanner.nextInt();
		
		Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "salario"));
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
		
		System.out.println(funcionarios);
		System.out.println("Página atual "+ funcionarios.getNumber());
		System.out.println("Total elemento "+ funcionarios.getTotalElements());
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
		System.out.println("Listado com sucesso");

	}
	
	public void deletar(Scanner scanner) {
		System.out.println("ID");
		int id = scanner.nextInt();
		funcionarioRepository.deleteById(id);

		System.out.println("Deletado com sucesso");

	}

}
