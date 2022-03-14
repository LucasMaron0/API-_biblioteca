package compass.microservice.usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import compass.microservice.usuario.controller.dto.EncerrarPedidoDto;
import compass.microservice.usuario.service.UsuarioService;

@RestController
@RequestMapping("/service")
public class UsuarioServiceController {
	
	@Autowired
	private UsuarioService uService;

	//responde requisiçoes de serviço  da biblioteca
	
	
}
