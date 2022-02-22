package compass.microservice.usuario.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import compass.microservice.usuario.client.BibliotecaClient;
import compass.microservice.usuario.controller.dto.EncerrarPedidoDto;
import compass.microservice.usuario.controller.dto.RetornoPedidoDto;
import compass.microservice.usuario.controller.dto.RetornoRequestTesteDto;
import compass.microservice.usuario.controller.form.PedirLivroForm;
import compass.microservice.usuario.controller.form.TesteForm;
import compass.microservice.usuario.modelo.Usuario;
import compass.microservice.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

	//recebe e manda requests para a biblioteca

	@Autowired
	private BibliotecaClient bClient;

	@Autowired
	private UsuarioRepository uRepo;

	//Manda
	
	public RetornoRequestTesteDto teste(@Valid TesteForm form) {

		RetornoRequestTesteDto retorno = bClient.teste(form);

		return retorno;
	}

	public RetornoPedidoDto pedirLivros(@Valid PedirLivroForm form) {

		RetornoPedidoDto retorno = bClient.pedirLivros(form);

		return retorno;
	}


	//Recebe

	public boolean encerrarRegistro(EncerrarPedidoDto encPedido) {

		Optional<Usuario> optUsuario = uRepo.findById(encPedido.getIdUser());
		if (optUsuario.isPresent()) {
			Usuario usuario =  optUsuario.get();

			int numeroPedidos = usuario.getNumeroDePedidos() - 1;
			usuario.setNumeroDePedidos(numeroPedidos);
			uRepo.save(usuario);
			return true;


		}else {
			return false;
		}
	}





}
