package compass.microservice.biblioteca.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import compass.microservice.biblioteca.client.UsuarioClient;
import compass.microservice.biblioteca.controller.LivroController;
import compass.microservice.biblioteca.controller.dto.BibliotecaDto;
import compass.microservice.biblioteca.controller.dto.EncerrarPedidoDto;
import compass.microservice.biblioteca.controller.dto.InfoLocLivroDto;
import compass.microservice.biblioteca.controller.dto.LivroDto;
import compass.microservice.biblioteca.controller.dto.RegistroDto;
import compass.microservice.biblioteca.controller.dto.RequestTesteDTO;
import compass.microservice.biblioteca.controller.dto.RetornoPedidoDto;
import compass.microservice.biblioteca.controller.dto.erros.ErroBuscaLivroDto;
import compass.microservice.biblioteca.controller.form.BuscarLivroProximoForm;
import compass.microservice.biblioteca.controller.form.ReceberEnderecoUsuario;
import compass.microservice.biblioteca.controller.form.RequestPedirLivros;
import compass.microservice.biblioteca.controller.form.RequestTesteForm;
import compass.microservice.biblioteca.modelos.Biblioteca;
import compass.microservice.biblioteca.modelos.Livro;
import compass.microservice.biblioteca.modelos.Registro;
import compass.microservice.biblioteca.modelos.StatusLivro;
import compass.microservice.biblioteca.modelos.StatusRegistro;
import compass.microservice.biblioteca.repository.BibliotecaRepository;
import compass.microservice.biblioteca.repository.LivrosRepository;
import compass.microservice.biblioteca.repository.RegistroRepository;

@Service
public class BibliotecaService {

	// recebe e manda requests para o usuario

	@Autowired
	private BibliotecaRepository bRepo;

	@Autowired
	private LivrosRepository lRepo;

	@Autowired
	private RegistroRepository rRepo;

	@Autowired
	private UsuarioClient uClient;

	@Autowired
	private LivroController lController;

	// Manda
	public ResponseEntity<?> encerrarPedido(Long id) {

		Optional<Registro> optRegistro = rRepo.findById(id);

		if (optRegistro.isEmpty()) {
			return ResponseEntity.badRequest().body("Este registro não existe");
		}

		Registro registro = optRegistro.get();

		if(registro.getStatusRegistro().equals(StatusRegistro.FINALIZADO)) {
			return ResponseEntity.badRequest().body("Este registro já foi finalizado.");
		}

		List<Livro> livros = registro.getLivros();

		for (Livro l : livros) {
			l.setRegistro(null);
			l.setStatusLivro(StatusLivro.DISPONIVEL);
			lRepo.save(l);
		}

		registro.setStatusRegistro(StatusRegistro.FINALIZADO);

		EncerrarPedidoDto encPedido = new EncerrarPedidoDto(registro.getIdUsuario(), registro.getId(), "encerrado");

		return ResponseEntity.ok(encPedido);


	}

	// Recebe (da BibliotecaServiceController que recebe do Usuario)

	public ResponseEntity<RetornoPedidoDto> pedirLivros(RequestPedirLivros form) {

		RetornoPedidoDto request = new RetornoPedidoDto();

		Optional<Biblioteca> opBiblio = bRepo.findById(form.getIdBiblioteca());

		List<Registro> registros = rRepo.findAllByIdUsuario(form.getIdUser());

		if (opBiblio.isEmpty()) {
			request.setStatus("Biblioteca não existe");
			return ResponseEntity.ok(request);

		} else {

			if (!registros.isEmpty()) {
				for(Registro r:registros) {
					if (r.getBiblioteca().getId() == opBiblio.get().getId() && r.getStatusRegistro().equals(StatusRegistro.EM_ANDAMENTO)) {
						request.setStatus("O usuário já tem um pedido em andamento nesta biblioteca");
						return ResponseEntity.ok(request);
					}
				}
			}

			List<Long> livros = form.getIdLivros();
			List<String> livrosDisponiveis = new ArrayList<>();
			List<Long> livrosIndisponiveis = new ArrayList<Long>();
			List<Livro> livrosPedido = new ArrayList<Livro>();

			for (Long id : livros) {

				Optional<Livro> livro = lRepo.findById(id);

				if (livro.isPresent() && livro.get().getStatusLivro().equals(StatusLivro.DISPONIVEL)) {
					livrosPedido.add(livro.get());
					livrosDisponiveis.add(livro.get().getNome());

				} else {
					livrosIndisponiveis.add(id);
				}
			}

			request.setBiblioteca(opBiblio.get().getNome());
			request.setLivrosDisponiveis(livrosDisponiveis);
			request.setLivriosIndisponiveis(livrosIndisponiveis);

			if (livrosIndisponiveis.size() > 0) {
				request.setStatus("Alguns dos livros que você pediu não estão disponíveis");
				return ResponseEntity.ok(request);

			} else {
				Registro registro = new Registro(form.getIdUser(), livrosPedido, opBiblio.get());

				rRepo.save(registro);

				for (Livro livro : livrosPedido) {
					livro.setRegistro(registro);
					livro.setStatusLivro(StatusLivro.INDISPONIVEL);
					lRepo.save(livro);
				}

				request.setStatus("Pedido Realizado com sucesso");
				return ResponseEntity.ok(request);
			}

		}

	}

	public List<LivroDto> listarLivros(Long id) {

		Page<LivroDto> livrosBiblioteca = lController.livrosBiblioteca(id,null);

		return livrosBiblioteca.getContent();
	}

	public List<RegistroDto> getByIdUsuario(Long idUsuario) {
		List<Registro> registros = rRepo.findAllByIdUsuario(idUsuario);
		return registros.stream().map(RegistroDto::new).collect(Collectors.toList());
	}

	public BibliotecaDto buscarBiblioProxima(ReceberEnderecoUsuario form) throws Exception {
		List<Biblioteca> bibliotecas = bRepo.findAll();
		Localizacao loc = new Localizacao();
		Biblioteca biblioteca = loc.procurarBibliotecaMaisProxima(form, bibliotecas);
		return new BibliotecaDto(biblioteca);
	}


	public List<InfoLocLivroDto> buscarLivroProximo(BuscarLivroProximoForm form) throws Exception {

		Localizacao loc = new Localizacao();
		ReceberEnderecoUsuario usuarioEnd= new ReceberEnderecoUsuario(form);
		List<InfoLocLivroDto> livrosMaisProximos = new ArrayList<InfoLocLivroDto>();

		for (String s: form.getNomeLivros()) {

			List<Livro> livros = lRepo.findByNome(s);

			if (livros.size()>=2) {

				Optional<Livro> opLivroMaisProximo = Optional.ofNullable(loc.livroMaisProximo(usuarioEnd, livros,form.isMostrarIndisponiveis()));

				if(opLivroMaisProximo.isPresent()) {
					Livro livroMaisProximo = opLivroMaisProximo.get();
					InfoLocLivroDto info = new InfoLocLivroDto(livroMaisProximo, livroMaisProximo.getBiblioteca());
					livrosMaisProximos.add(info);
				}


			}else if (livros.isEmpty())  {

				InfoLocLivroDto info = new InfoLocLivroDto(s);
				livrosMaisProximos.add(info);

			}else {
				Livro livro = livros.get(0);
				if(!form.isMostrarIndisponiveis()) {
					if(livro.getStatusLivro().equals(StatusLivro.DISPONIVEL)) {
						InfoLocLivroDto info = new InfoLocLivroDto(livro, livro.getBiblioteca());
						livrosMaisProximos.add(info);
					}
				}else {
					InfoLocLivroDto info = new InfoLocLivroDto(livro, livro.getBiblioteca());
					livrosMaisProximos.add(info);
				}
			}
		}



		return livrosMaisProximos;

	}

	public HashMap<String, List<Object>> pedidoAvancado(BuscarLivroProximoForm form) throws Exception {

		HashMap<Long, List<Livro>> livrosPedidos = new HashMap<>();
		List<Object> erros = new ArrayList<>();
		Localizacao loc = new Localizacao();
		ReceberEnderecoUsuario usuarioEnd= new ReceberEnderecoUsuario(form);

		List<Registro> registrosUsuario= rRepo.findAllByIdUsuario(form.getUserId());
		List<Long> bibliosComPedidosEmAndamento = new ArrayList<>();

		for(Registro r: registrosUsuario) {
			if (r.getStatusRegistro().equals(StatusRegistro.EM_ANDAMENTO)) {
				bibliosComPedidosEmAndamento.add(r.getBiblioteca().getId());
			}
		}



		for (String s: form.getNomeLivros()) {
			List<Livro> livros = lRepo.findByNome(s);
			Livro livroMaisProximo = null;

			boolean emBbComPedidoEmAndamento = false;

			

			if (!bibliosComPedidosEmAndamento.isEmpty()) {
				if (!livros.isEmpty()) {
					Iterator<Livro> i = livros.iterator();
					while(i.hasNext()) {
						Livro l = i.next();
						if (bibliosComPedidosEmAndamento.contains(l.getBiblioteca().getId())) {
							i.remove();
							
							emBbComPedidoEmAndamento = true;
						}
					}
				}
			}

			

			if (livros.size()>1) {
				Optional<Livro> opLivroMaisProximo = Optional.ofNullable(loc.livroMaisProximo(usuarioEnd, livros, false));
				if(opLivroMaisProximo.isPresent()) {
					livroMaisProximo = opLivroMaisProximo.get();
				}else {
					ErroBuscaLivroDto erro = new ErroBuscaLivroDto(s, "Nenhuma unidade deste livro disponível.");
					erros.add(erro);
				}

			}else if (livros.size() == 1)  {
				Livro l = livros.get(0);
				if (l.getStatusLivro().equals(StatusLivro.DISPONIVEL)) {
					livroMaisProximo = l;
				}else {
					ErroBuscaLivroDto erro = new ErroBuscaLivroDto(s, "Nenhuma unidade deste livro disponível.");
					erros.add(erro);
				}

			}else {
				if(!emBbComPedidoEmAndamento) {
					ErroBuscaLivroDto erro = new ErroBuscaLivroDto(s, "Livro não encontrado.");
					erros.add(erro);
				}else {
					ErroBuscaLivroDto erro = new ErroBuscaLivroDto(s, "Você já tem um pedido em andamento na Biblioteca que possui este livro");
					erros.add(erro);
				}
			}

			if(!(livroMaisProximo == null)) {

				Optional<List<Livro>> mapList = Optional.ofNullable(livrosPedidos.get(livroMaisProximo.getBiblioteca().getId()));
				if(mapList.isPresent()) {
					mapList.get().add(livroMaisProximo);
					livrosPedidos.put(livroMaisProximo.getBiblioteca().getId(), mapList.get());
				}else {
					List<Livro> newMapList = new ArrayList<>();
					newMapList.add(livroMaisProximo);
					livrosPedidos.put(livroMaisProximo.getBiblioteca().getId(), newMapList);
				}
			}
		}

		List<Object> pedidosRealizados = new ArrayList<>();
		if(!livrosPedidos.isEmpty()) {

			for (Map.Entry<Long, List<Livro>> entry: livrosPedidos.entrySet()) {
				RequestPedirLivros pedido = new  RequestPedirLivros ();
				List<Long> idLivros = new ArrayList<>();

				for (Livro l : entry.getValue()) {
					idLivros.add(l.getId());		
				}

				pedido.setIdBiblioteca(entry.getKey());
				pedido.setIdLivros(idLivros);
				pedido.setIdUser(form.getUserId());


				pedidosRealizados.add(pedirLivros(pedido).getBody());
			}
		}
		HashMap<String, List<Object>> mapRetorno = new HashMap<String, List<Object>>();
		mapRetorno.put("pedidos", pedidosRealizados);
		mapRetorno.put("erros", erros);
		return mapRetorno;
	}

}
