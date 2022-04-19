package br.com.alura.spring.data.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer> {
	
	List<Funcionario> findByNome(String nome);
	
	//Abaixo segue alternativa para mais legibilidade na consulta, utilizando @Query
	//List<Funcionario> findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, Double salario, LocalDate dataContratacao);
	
	@Query("SELECT f FROM Funcionario f "
			+ "WHERE f.nome = :nome AND f.salario >= :salario "
			+ "AND f.dataContratacao = :data")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);	
	
	@Query(value = "SELECT * FROM funcionario f WHERE f.data_contratacao >= :data",
			nativeQuery = true)
	List<Funcionario> findDataContratacaoMaior(LocalDate data);
	
	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionario f",
			nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();

}
