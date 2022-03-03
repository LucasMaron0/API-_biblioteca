package compass.microservice.usuario.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
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

import compass.microservice.usuario.controller.dto.EndBibliotecaDto;
import compass.microservice.usuario.controller.dto.ErroBuscaLivroDto;
import compass.microservice.usuario.controller.dto.InfoLocLivroDto;
import compass.microservice.usuario.controller.dto.LivroDto;
import compass.microservice.usuario.controller.dto.RegistroDto;
import compass.microservice.usuario.controller.dto.RetornoPedidoDto;
import compass.microservice.usuario.controller.dto.RetornoRequestTesteDto;
import compass.microservice.usuario.controller.dto.UsuarioDto;
import compass.microservice.usuario.controller.form.BuscarLivroProximoForm;
import compass.microservice.usuario.controller.form.BuscarNomeLivrosForm;
import compass.microservice.usuario.controller.form.CadastrarUsuarioForm;
import compass.microservice.usuario.controller.form.MandarEnderecoUsuario;
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
	public ResponseEntity<RetornoRequestTesteDto> cadastrar(@RequestBody @Valid TesteForm form) {

		RetornoRequestTesteDto retorno = uService.teste(form);

		return ResponseEntity.ok(retorno);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid CadastrarUsuarioForm form,
			UriComponentsBuilder uriBuilder) {

		Usuario usuario = form.converter();
		uRepo.save(usuario);

		URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));

	}

	@PostMapping("/pedido")
	@Transactional
	public ResponseEntity<?> pedirlivro(@RequestBody @Valid PedirLivroForm form) {

		Optional<Usuario> optional = uRepo.findById(form.getIdUser());

		if (optional.isPresent()) {
			RetornoPedidoDto retorno = uService.pedirLivros(form);

			return ResponseEntity.ok().body(retorno);
		}

		return ResponseEntity.badRequest().body("Usuario não existe");


	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Usuario> optional = uRepo.findById(id);
		if (optional.isPresent()) {
			List<RegistroDto> registros  = listarRegistrosPorUsuario(optional.get().getId());
			Boolean deletavel = true;

			for (RegistroDto r : registros) {
				if (r.getStatusRegistro().equals("EM_ANDAMENTO")) {
					deletavel = false;
				}
			}
			if (!deletavel) {
				return ResponseEntity.badRequest().body("Não é possível excluir um usuário com pedidos em andamento.");
			} else {
				uRepo.deleteById(id);
				return ResponseEntity.ok().build();
			}
		}
		return ResponseEntity.notFound().build();

	}

	@PostMapping("/pedidoAvancado/{id}")
	@Transactional
	public ResponseEntity<?> pedidoAvancado (@PathVariable long id, @RequestBody BuscarNomeLivrosForm nomeLivros ){

		Optional<Usuario> op = uRepo.findById(id);
		if (op.isPresent()) {
			BuscarLivroProximoForm  form = new BuscarLivroProximoForm(op.get().getId(),
					op.get().getEndereco(), nomeLivros.getNomeLivros(), nomeLivros.isMostrarIndisponiveis());

			HashMap<String, List<Object>> retorno = uService.pedidoAvancado(form);


			List<Object> pedidos = retorno.get("pedidos");
			List<Object> erros =  retorno.get("erros");


			List<Object> respostas = new ArrayList<>();
			respostas.addAll(pedidos);
			respostas.addAll(erros);

			return ResponseEntity.ok().body(respostas);
			;
		}


		}
		return ResponseEntity.badRequest().body("Usuario não existe");
	}



	@GetMapping
	public Page<UsuarioDto> listAllUsuarios(
			@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {

		Page<Usuario> usuarios = uRepo.findAll(paginacao);
		return UsuarioDto.converter(usuarios);

	}


	@GetMapping("/livros/{id}")
	public List<LivroDto> livrosBiblioteca(	@PathVariable Long id){
		return uService.listarLivros(id);
	}

	@GetMapping("/buscarBiblioteca/{userId}")
	public ResponseEntity<?> buscarBiblioMaisProxima (@PathVariable long userId) {
		Optional<Usuario> optional = uRepo.findById(userId);
		if (optional.isPresent()) {

			MandarEnderecoUsuario end = new MandarEnderecoUsuario(userId, optional.get().getEndereco()); 
			EndBibliotecaDto retorno = uService.buscarBibliMaisProxima(end);

			return ResponseEntity.ok(retorno);
		}

		return ResponseEntity.badRequest().body("Usuario não existe");
	}

	@GetMapping("/registros/{idUsuario}")
	public List<RegistroDto> listarRegistrosPorUsuario(@PathVariable Long idUsuario){
		return uService.listarRegistrosPorUsuario(idUsuario);
	}

	@GetMapping("/buscarLivroProximo/{id}")

	public ResponseEntity<?> buscarLivroProximo (@PathVariable long id, @RequestBody BuscarNomeLivrosForm nomeLivros ){

		Optional<Usuario> op = uRepo.findById(id);
		if (op.isPresent()) {
			BuscarLivroProximoForm  form = new BuscarLivroProximoForm(op.get().getId(),
					op.get().getEndereco(), nomeLivros.getNomeLivros(), nomeLivros.isMostrarIndisponiveis());



			List<InfoLocLivroDto> livros = uService.buscarLivroProximo(form);
			if(livros.isEmpty()) {
				return ResponseEntity.badRequest().body("Nenhum livro foi encontrado");
			}else {
				return ResponseEntity.ok().body(livros);
			}

		}
		return ResponseEntity.badRequest().body("Usuario não existe");
	}

	@PostMapping("/pedidoAvancado/{id}")
	@Transactional
	public ResponseEntity<?> pedidoAvancado (@PathVariable long id, @RequestBody BuscarNomeLivrosForm nomeLivros ) throws ClassNotFoundException{

		Optional<Usuario> op = uRepo.findById(id);
		if (op.isPresent()) {
			BuscarLivroProximoForm  form = new BuscarLivroProximoForm(op.get().getId(),
					op.get().getEndereco(), nomeLivros.getNomeLivros(), nomeLivros.isMostrarIndisponiveis());

			HashMap<String, List<Object>> retorno = uService.pedidoAvancado(form);

			List<Object> pedidos = retorno.get("pedidos");
			List<Object> erros =  retorno.get("erros");


			List<Object> respostas = new ArrayList<>();
			respostas.addAll(pedidos);
			respostas.addAll(erros);

			return ResponseEntity.ok().body(respostas);


		}
		return ResponseEntity.badRequest().body("Usuario não existe");
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
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Usuario> optional = uRepo.findById(id);
		if (optional.isPresent()) {
			List<RegistroDto> registros  = listarRegistrosPorUsuario(optional.get().getId());
			Boolean deletavel = true;

			for (RegistroDto r : registros) {
				if (r.getStatusRegistro().equals("EM_ANDAMENTO")) {
					deletavel = false;
				}
			}
			if (!deletavel) {
				return ResponseEntity.badRequest().body("Não é possível excluir um usuário com pedidos em andamento.");
			} else {
				uRepo.deleteById(id);
				return ResponseEntity.ok().build();
			}
		}
		return ResponseEntity.notFound().build();
	}




}


