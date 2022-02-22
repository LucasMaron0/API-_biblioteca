package compass.microservice.biblioteca.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import compass.microservice.biblioteca.controller.dto.EncerrarPedidoDto;

@FeignClient("usuario")//nome do serviço pra entrar em contato
public interface UsuarioClient {
	
	//entra contato com o usuario	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/service/encerrar-registro")
	boolean encerrarRegistro(EncerrarPedidoDto encPedido);

}
