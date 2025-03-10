package compass.microservice.biblioteca.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import compass.microservice.biblioteca.controller.dto.BibliotecaDto;
import compass.microservice.biblioteca.controller.dto.LivroDto;
import compass.microservice.biblioteca.controller.form.CadastrarBibliotecaForm;
import compass.microservice.biblioteca.modelos.Biblioteca;
import compass.microservice.biblioteca.modelos.Livro;
import compass.microservice.biblioteca.modelos.Registro;
import compass.microservice.biblioteca.modelos.StatusRegistro;
import compass.microservice.biblioteca.repository.BibliotecaRepository;
import compass.microservice.biblioteca.repository.LivrosRepository;
import compass.microservice.biblioteca.repository.RegistroRepository;

@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

	@Autowired
	private BibliotecaRepository bRepo;

	@Autowired
	private LivrosRepository lRepo;

	@Autowired
	private RegistroRepository rRepo;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_BIBLIOTECA')")
	@Transactional
	public ResponseEntity<BibliotecaDto> cadastrarBiblioteca(@RequestBody @Valid CadastrarBibliotecaForm form,
			UriComponentsBuilder uriBuilder) {

		Biblioteca biblioteca = form.converter();
		bRepo.save(biblioteca);

		URI uri = uriBuilder.path("/bb/{id}").buildAndExpand(biblioteca.getId()).toUri();
		return ResponseEntity.created(uri).body(new BibliotecaDto(biblioteca));

	}

	@GetMapping
	public Page<BibliotecaDto> listAll(
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

		Page<Biblioteca> bibliotecas = bRepo.findAll(paginacao);
		return BibliotecaDto.converter(bibliotecas);

	}

	@GetMapping("/{id}")
	public ResponseEntity<BibliotecaDto> listById(@PathVariable long id) {
		Optional<Biblioteca> biblioteca = bRepo.findById(id);
		if (biblioteca.isPresent()) {
			return ResponseEntity.ok(new BibliotecaDto(biblioteca.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_BIBLIOTECA')")
	@Transactional
	public ResponseEntity<BibliotecaDto> atualizar(@PathVariable Long id,
			@RequestBody @Valid CadastrarBibliotecaForm form) {
		Optional<Biblioteca> optional = bRepo.findById(id);
		if (optional.isPresent()) {
			Biblioteca b = optional.get();
			Biblioteca bAtualizada = form.atualizar(b);
			return ResponseEntity.ok(new BibliotecaDto(bAtualizada));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_BIBLIOTECA')")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Biblioteca> optional = bRepo.findById(id);
		if (optional.isPresent()) {
			List<Registro> registros = rRepo.findByBiblioteca_id(id, null).getContent();
			boolean deletavel = true;
			for (Registro r : registros) {
				if(r.getStatusRegistro().equals(StatusRegistro.EM_ANDAMENTO)) {
					deletavel = false;
				}
			}
			if (deletavel) {
				bRepo.deleteById(id);
				return ResponseEntity.ok().build();
			}else {
				return ResponseEntity.badRequest().body("Não é possível deletar bibliotecas com pedios em andamento.");
			}
		}

		return ResponseEntity.notFound().build();
	}

}
