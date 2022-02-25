package compass.microservice.biblioteca.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import compass.microservice.biblioteca.controller.dto.LivroDto;
import compass.microservice.biblioteca.controller.dto.RequestPedirLivroDto;
import compass.microservice.biblioteca.controller.dto.RequestTesteDTO;
import compass.microservice.biblioteca.controller.form.RequestPedirLivros;
import compass.microservice.biblioteca.controller.form.RequestTesteForm;
import compass.microservice.biblioteca.modelos.Livro;
import compass.microservice.biblioteca.repository.LivrosRepository;
import compass.microservice.biblioteca.service.BibliotecaService;

@RestController
@RequestMapping("/service")
public class BibliotecaServiceController {
	// responde requisiçoes de serviço do usuario

	@Autowired
	private BibliotecaService bService;

	@Autowired
	private LivrosRepository lRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/teste")
	public ResponseEntity<RequestTesteDTO> teste(@RequestBody RequestTesteForm form) {
		return bService.teste(form);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/pedido")
	public ResponseEntity<RequestPedirLivroDto> pedirLivros(@RequestBody RequestPedirLivros form) {
		return bService.pedirLivros(form);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/listarLivros")
	public List<LivroDto> listarLivros(@RequestBody Long id) {
		return bService.listarLivros(id);
	}

	@GetMapping("/nomeAutor/{autor}")
	public ResponseEntity<LivroDto> buscarPorAutor(@PathVariable String autor) {
		Optional<Livro> optional = lRepository.findByAutorContainingIgnoreCase(autor);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new LivroDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/nomeLivro/{nome}")
	public ResponseEntity<LivroDto> buscarPorNomeLivro(@PathVariable String nome) {
		Optional<Livro> optional = lRepository.findByNomeContainingIgnoreCase(nome);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new LivroDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

}
