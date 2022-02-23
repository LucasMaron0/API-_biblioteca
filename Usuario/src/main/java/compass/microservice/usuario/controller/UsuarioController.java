package compass.microservice.usuario.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import compass.microservice.usuario.controller.dto.LivroDto;
import compass.microservice.usuario.controller.dto.RetornoPedidoDto;
import compass.microservice.usuario.controller.dto.RetornoRequestTesteDto;
import compass.microservice.usuario.controller.dto.UsuarioDto;
import compass.microservice.usuario.controller.form.CadastrarUsuarioForm;
import compass.microservice.usuario.controller.form.PedirLivroForm;
import compass.microservice.usuario.controller.form.TesteForm;
import compass.microservice.usuario.modelo.Usuario;
import compass.microservice.usuario.repository.UsuarioRepository;
import compass.microservice.usuario.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


	@Autowired
	private UsuarioService uService;

	@Autowired
	private UsuarioRepository uRepo;



	@PostMapping("/service-teste")
	@Transactional
	public ResponseEntity<RetornoRequestTesteDto> cadastrar(@RequestBody @Valid TesteForm form){

		RetornoRequestTesteDto retorno = uService.teste(form);	

		return ResponseEntity.ok(retorno);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrarUsuario (@RequestBody @Valid CadastrarUsuarioForm form, UriComponentsBuilder uriBuilder ){

		Usuario usuario = form.converter();
		uRepo.save(usuario);

		URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));

	}



	@GetMapping
	public Page<UsuarioDto> listAllUsuarios(
			@PageableDefault(sort="id", direction= Direction.ASC,page=0, size=10)Pageable paginacao){

		Page<Usuario> usuarios = uRepo.findAll(paginacao);
		return UsuarioDto.converter(usuarios);

	}


	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<UsuarioDto> atualizar(@PathVariable Long id, @RequestBody @Valid CadastrarUsuarioForm form) {
		Optional<Usuario> optional = uRepo.findById(id);
		if (optional.isPresent()) {
			Usuario u = optional.get();
			Usuario uAtualizado = form.atualizar(u);
			return ResponseEntity.ok(new UsuarioDto(uAtualizado));
		}

		return ResponseEntity.notFound().build();
	}



	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id)  {
		Optional<Usuario> optional = uRepo.findById(id);
		if (optional.isPresent()) {
			if(optional.get().getNumeroDePedidos() >= 1) {
				return ResponseEntity.badRequest().body("Não é possível excluir um usuário com pedidos em andamento.");
			}else {
				uRepo.deleteById(id);
				return ResponseEntity.ok().build();
			}
		}
		return ResponseEntity.notFound().build();
	}


	@PostMapping("/pedido")
	@Transactional
	public ResponseEntity<?> pedirlivro(@RequestBody @Valid PedirLivroForm form){

		Optional<Usuario> optional = uRepo.findById(form.getIdUser());

		if(optional.isPresent()) {
			RetornoPedidoDto retorno = uService.pedirLivros(form);

			if (retorno.getStatus().equals("Pedido Realizado com sucesso")) {

				int numeroPedidos = optional.get().getNumeroDePedidos() + 1;
				optional.get().setNumeroDePedidos(numeroPedidos);
				return ResponseEntity.ok().body(retorno);
			}else {

				return ResponseEntity.ok().body(retorno);
			}
		}

		return ResponseEntity.badRequest().body("Usuario não existe");

	}

	@GetMapping("/livros/{id}")
	public List<LivroDto> livrosBiblioteca(	@PathVariable Long id){

		return uService.listarLivros(id);
	}
}
