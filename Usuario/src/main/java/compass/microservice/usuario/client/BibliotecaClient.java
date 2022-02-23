package compass.microservice.usuario.client;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import compass.microservice.usuario.controller.dto.LivroDto;
import compass.microservice.usuario.controller.dto.RetornoPedidoDto;
import compass.microservice.usuario.controller.dto.RetornoRequestTesteDto;
import compass.microservice.usuario.controller.form.PedirLivroForm;
import compass.microservice.usuario.controller.form.TesteForm;

@FeignClient("biblioteca")//nome do serviço pra entrar em contato
public interface BibliotecaClient {
	//entra contato com a biblioteca

	
	@RequestMapping(method = RequestMethod.POST, value = "/service/teste")
	RetornoRequestTesteDto teste(@Valid TesteForm form);

	@RequestMapping(method = RequestMethod.POST, value = "/service/pedido")
	RetornoPedidoDto pedirLivros(@Valid PedirLivroForm form);

	@RequestMapping(method = RequestMethod.POST, value = "/service/listarLivros")
	List<LivroDto> listarLivros(Long id);
	


}
