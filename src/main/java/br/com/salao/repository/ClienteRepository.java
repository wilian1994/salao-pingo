package br.com.salao.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.salao.model.Cliente;
import br.com.salao.model.Tipo;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

	List<Cliente> findByTipo(Tipo tipo);

	List<Cliente> findByDataAgendamentoAndHorario(Calendar data, String horario);

}
