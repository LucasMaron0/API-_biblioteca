package compass.microservice.biblioteca.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

	@GetMapping("/biblioteca/{id}")
	public ResponseEntity<BibliotecaDto> listById(@PathVariable long id) {
		Optional<Biblioteca> biblioteca = bRepo.findById(id);
		if (biblioteca.isPresent()) {
			return ResponseEntity.ok(new BibliotecaDto(biblioteca.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/nomeAutor/{autor}")
	public ResponseEntity<LivroDto> buscarPorAutor(@PathVariable String autor) {
		Optional<Livro> optional = lRepo.findByAutorContainingIgnoreCase(autor);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new LivroDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/nomeLivro/{nome}")
	public ResponseEntity<LivroDto> buscarPorNomeLivro(@PathVariable String nome) {
		Optional<Livro> optional = lRepo.findByNomeContainingIgnoreCase(nome);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new LivroDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}


	@PutMapping("/{id}")
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
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Biblioteca> optional = bRepo.findById(id);
		if (optional.isPresent()) {
			bRepo.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
