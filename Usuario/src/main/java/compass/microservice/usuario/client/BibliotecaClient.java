package compass.microservice.usuario.client;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import compass.microservice.usuario.controller.dto.EndBibliotecaDto;
import compass.microservice.usuario.controller.dto.InfoLocLivroDto;
import compass.microservice.usuario.controller.dto.LivroDto;
import compass.microservice.usuario.controller.dto.RetornoPedidoDto;
import compass.microservice.usuario.controller.dto.RetornoRequestTesteDto;
import compass.microservice.usuario.controller.form.BuscarLivroProximoForm;
import compass.microservice.usuario.controller.form.MandarEnderecoUsuario;
import compass.microservice.usuario.controller.form.PedirLivroForm;
import compass.microservice.usuario.controller.form.TesteForm;
import feign.Headers;
import compass.microservice.usuario.controller.dto.RegistroDto;

@FeignClient("biblioteca") // nome do serviço pra entrar em contato
public interface BibliotecaClient {
	// entra contato com a biblioteca



	@RequestMapping(method = RequestMethod.POST, value = "/service/pedido")
	RetornoPedidoDto pedirLivros(@Valid PedirLivroForm form, @RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.POST, value = "/service/listarLivros")
	List<LivroDto> listarLivros(Long id);

	@RequestMapping(method = RequestMethod.POST, value = "/service/biblioteca-mais-proxima")
	EndBibliotecaDto buscarBiblioMaisProxima(MandarEnderecoUsuario end);

	@RequestMapping(method = RequestMethod.POST, value = "/service/listarRegistrosPorUsuario")
	List<RegistroDto> listarRegistrosPorUsuario(Long idUsuario,  @RequestHeader("Authorization") String token);
	
	@RequestMapping(method = RequestMethod.POST, value = "/service/livro-mais-proximo")
	List<InfoLocLivroDto> buscarLivroProximo(BuscarLivroProximoForm form);

	@RequestMapping(method = RequestMethod.POST, value = "/service/pedido-avancado")
	HashMap<String, List<Object>> pedidoAvancado(BuscarLivroProximoForm form, @RequestHeader("Authorization") String token);

}
