package compass.microservice.biblioteca.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import compass.microservice.biblioteca.controller.dto.BibliotecaDto;
import compass.microservice.biblioteca.controller.dto.InfoLocLivroDto;
import compass.microservice.biblioteca.controller.dto.LivroDto;
import compass.microservice.biblioteca.controller.dto.RegistroDto;
import compass.microservice.biblioteca.controller.dto.RequestTesteDTO;
import compass.microservice.biblioteca.controller.dto.RetornoPedidoDto;
import compass.microservice.biblioteca.controller.form.BuscarLivroProximoForm;
import compass.microservice.biblioteca.controller.form.ReceberEnderecoUsuario;
import compass.microservice.biblioteca.controller.form.RequestPedirLivros;
import compass.microservice.biblioteca.controller.form.RequestTesteForm;
import compass.microservice.biblioteca.service.BibliotecaService;

@RestController
@RequestMapping("/service")
public class BibliotecaServiceController {

	@Autowired
	private BibliotecaService bService;


	@RequestMapping(method = RequestMethod.POST, value = "/pedido")
	public ResponseEntity<RetornoPedidoDto> pedirLivros(@RequestBody RequestPedirLivros form,
			@RequestHeader("Authorization") String token) {
		return bService.pedirLivros(form);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/listarLivros")
	public List<LivroDto> listarLivros(@RequestBody Long id) {
		return bService.listarLivros(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/biblioteca-mais-proxima")
	public BibliotecaDto listarLivros(@RequestBody ReceberEnderecoUsuario form) throws Exception {
		return bService.buscarBiblioProxima(form);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/listarRegistrosPorUsuario")
	public List<RegistroDto> listarRegistrosPorUsuario(@RequestBody Long idUsuario,
			@RequestHeader("Authorization") String token) {
		return bService.getByIdUsuario(idUsuario);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/livro-mais-proximo")
	public List<InfoLocLivroDto> livroMaisProximo(@RequestBody BuscarLivroProximoForm form) throws Exception {
		return bService.buscarLivroProximo(form);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/pedido-avancado")
	public HashMap<String, List<Object>> pedidoAvancado(@RequestBody BuscarLivroProximoForm form,
			@RequestHeader("Authorization") String token) throws Exception {
		return bService.pedidoAvancado(form);
	}

}
