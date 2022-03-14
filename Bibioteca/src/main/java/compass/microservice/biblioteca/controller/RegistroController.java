package compass.microservice.biblioteca.controller;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import compass.microservice.biblioteca.controller.dto.RegistroDto;
import compass.microservice.biblioteca.modelos.Registro;
import compass.microservice.biblioteca.modelos.StatusRegistro;
import compass.microservice.biblioteca.repository.BibliotecaRepository;
import compass.microservice.biblioteca.repository.LivrosRepository;
import compass.microservice.biblioteca.repository.RegistroRepository;
import compass.microservice.biblioteca.service.BibliotecaService;

@RestController
@RequestMapping("/registro")
public class RegistroController {

	@Autowired
	private BibliotecaRepository bRepo;

	@Autowired
	private LivrosRepository lRepo;

	@Autowired
	private RegistroRepository rRepo;

	@Autowired
	private BibliotecaService bService;

	@GetMapping
	public Page<RegistroDto> registroLivros(@RequestParam(required = false) StatusRegistro statusRegistro,
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		if (Objects.isNull(statusRegistro)) {
			Page<Registro> registros = rRepo.findAll(paginacao);
			return RegistroDto.converter(registros);
		} else {
			Page<Registro> registros = rRepo.findByStatusRegistro(statusRegistro, paginacao);
			return RegistroDto.converter(registros);
		}

	}

	@GetMapping("/checkarMultas")
	public List<Registro> checkarMultas() {

		List<Registro> checkarMultas = rRepo.findByStatusRegistro(StatusRegistro.EM_ANDAMENTO);
		for (Registro registro : checkarMultas) {
			Double multa = registro.multaGerada();
			registro.setMultaGerada(multa);
			rRepo.save(registro);

		}

		return null;
	}

	@GetMapping("/biblioteca/{id}")
	@PreAuthorize("hasRole('ROLE_BIBLIOTECA')")
	public Page<RegistroDto> registrosBiblioteca(@PathVariable Long id, 
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){
		Page<Registro> registros = rRepo.findByBiblioteca_id(id, paginacao);
		return RegistroDto.converter(registros);

	}
	
	@PutMapping("/encerrar/{id}")
	@PreAuthorize("hasRole('ROLE_BIBLIOTECA')")
	@Transactional
	public ResponseEntity<?> encerrarRegistro(@PathVariable Long id) {

		ResponseEntity<?> encPedido = bService.encerrarPedido(id);

		return ResponseEntity.ok(encPedido);
	}
}
