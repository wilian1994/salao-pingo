package br.com.salao.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.salao.model.Cliente;
import br.com.salao.model.Tipo;
import br.com.salao.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public List<Cliente> list() {
		return (List<Cliente>) repository.findAll();
	}

	public Cliente findOne(Long id) {
		return repository.findOne(id);
	}
	
	public void remove(Cliente cliente){
		repository.delete(cliente);
	}

	public void salvar(Cliente cliente) {
		repository.save(cliente);
	}

	public List<Cliente> listaPorTipo(Tipo tipo) {
		return repository.findByTipo(tipo);
	}

	public List<Cliente> findByDataAgendamentoAndHorario(Calendar data, String horario) {
		return repository.findByDataAgendamentoAndHorario(data, horario);
	}

}
