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

import compass.microservice.biblioteca.controller.dto.BibliotecaDto;
import compass.microservice.biblioteca.controller.dto.InfoLocLivroDto;
import compass.microservice.biblioteca.controller.dto.LivroDto;
import compass.microservice.biblioteca.controller.dto.RegistroDto;
import compass.microservice.biblioteca.controller.dto.RequestPedirLivroDto;
import compass.microservice.biblioteca.controller.dto.RequestTesteDTO;
import compass.microservice.biblioteca.controller.form.BuscarLivroProximoForm;
import compass.microservice.biblioteca.controller.form.ReceberEnderecoUsuario;
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

	@RequestMapping(method = RequestMethod.POST, value = "/biblioteca-mais-proxima")	
	public BibliotecaDto listarLivros(@RequestBody ReceberEnderecoUsuario form) throws Exception {				
		return bService.listarLivros(form);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/listarRegistrosPorUsuario")
	public List<RegistroDto> listarRegistrosPorUsuario(@RequestBody Long idUsuario) {
		return bService.getByIdUsuario(idUsuario);
	}
	@RequestMapping(method = RequestMethod.POST, value = "/livro-mais-proximo")
	public List<InfoLocLivroDto> listarRegistrosPorUsuario(@RequestBody BuscarLivroProximoForm form) throws Exception {
		return bService.buscarLivroProximo(form);
	}

}
