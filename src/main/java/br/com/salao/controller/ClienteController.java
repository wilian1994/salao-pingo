package br.com.salao.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.salao.HttpPostExample;
import br.com.salao.model.Cliente;
import br.com.salao.model.Tipo;
import br.com.salao.service.ClienteService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ClienteController {

	@Autowired
	ClienteService service;

	@PostMapping("/salvar")
	public ModelAndView salvar(Cliente cliente) throws Exception {
		
		HttpPostExample http = new HttpPostExample();
		System.out.println("olha:" +cliente.getTelefone());
		if(!cliente.getTelefone().equals("")){http.sendPost(cliente);};
		service.salvar(cliente);
		
		SimpleDateFormat s = new SimpleDateFormat("ddMMyyyy");
		return new ModelAndView("redirect:/lista/" + s.format(cliente.getDataAgendamento().getTime()));
	}

	@RequestMapping("/")
	public ModelAndView lista() {

		String horarios[] = { "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00",
				"15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30" };

		List<HorarioDTO> horariosNovos = new ArrayList<HorarioDTO>();

		for (int i = 0; i < horarios.length; i++) {

			List<Cliente> clientes = service.findByDataAgendamentoAndHorario(Calendar.getInstance(), horarios[i]);
			HorarioDTO horarioAgenda = new HorarioDTO();

			for (Cliente cliente : clientes) {
				if (cliente.getTipo().equals(Tipo.CORTE)) {
					horarioAgenda.setCorte(cliente.getNome());
					horarioAgenda.setIdCorte(cliente.getId());

				}

				if (cliente.getTipo().equals(Tipo.PROGRESSIVA)) {
					horarioAgenda.setProgressiva(cliente.getNome());
					horarioAgenda.setIdProgressiva(cliente.getId());
				}
			}
			horarioAgenda.setHorario(horarios[i]);
			horariosNovos.add(horarioAgenda);

		}

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("cliente", new Cliente());
		modelAndView.addObject("dataSelecionado", Calendar.getInstance());
		modelAndView.addObject("horarios", horariosNovos);

		return modelAndView;
	}

	@RequestMapping("/lista/{data}")
	public ModelAndView listaComData(@PathVariable("data") String data) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(data));

		String horarios[] = { "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00",
				"15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30" };

		List<HorarioDTO> horariosNovos = new ArrayList<HorarioDTO>();

		for (int i = 0; i < horarios.length; i++) {

			List<Cliente> clientes = service.findByDataAgendamentoAndHorario(c, horarios[i]);
			HorarioDTO horarioAgenda = new HorarioDTO();

			for (Cliente cliente : clientes) {
				if (cliente.getTipo().equals(Tipo.CORTE)) {
					horarioAgenda.setCorte(cliente.getNome());
					horarioAgenda.setIdCorte(cliente.getId());

				}

				if (cliente.getTipo().equals(Tipo.PROGRESSIVA)) {
					horarioAgenda.setProgressiva(cliente.getNome());
					horarioAgenda.setIdProgressiva(cliente.getId());
				}
			}
			horarioAgenda.setHorario(horarios[i]);
			horariosNovos.add(horarioAgenda);

		}

		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("cliente", new Cliente());
		modelAndView.addObject("dataSelecionado", c);
		modelAndView.addObject("horarios", horariosNovos);

		return modelAndView;
	}

	@GetMapping("/carregaCliente/{id}")
	@ResponseBody
	public String carregaCliente(@PathVariable("id") Long id) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(service.findOne(id));

	}

	@GetMapping("/excluir")
	public ModelAndView excluir(@RequestParam("id") Long id) {
		Cliente cliente = service.findOne(id);
		service.remove(cliente);
		SimpleDateFormat s = new SimpleDateFormat("ddMMyyyy");
		return new ModelAndView("redirect:/lista/" + s.format(cliente.getDataAgendamento().getTime()));
	}

	public class HorarioDTO {

		private long idCorte;
		private long idProgressiva;
		private String horario;
		private String corte;
		private String progressiva;

		public void setIdCorte(long idCorte) {
			this.idCorte = idCorte;
		}

		public void setIdProgressiva(long idProgressiva) {
			this.idProgressiva = idProgressiva;
		}

		public long getIdCorte() {
			return idCorte;
		}

		public long getIdProgressiva() {
			return idProgressiva;
		}

		public String getCorte() {
			return corte;
		}

		public void setCorte(String corte) {
			this.corte = corte;
		}

		public String getProgressiva() {
			return progressiva;
		}

		public void setProgressiva(String progressiva) {
			this.progressiva = progressiva;
		}

		public String getHorario() {
			return horario;
		}

		public void setHorario(String horario) {
			this.horario = horario;
		}

	}

}
