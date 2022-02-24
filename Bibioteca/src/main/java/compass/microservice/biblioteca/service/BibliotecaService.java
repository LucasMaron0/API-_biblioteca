package compass.microservice.biblioteca.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;


import compass.microservice.biblioteca.client.UsuarioClient;
import compass.microservice.biblioteca.controller.BibliotecaController;
import compass.microservice.biblioteca.controller.LivroController;
import compass.microservice.biblioteca.controller.dto.BibliotecaDto;
import compass.microservice.biblioteca.controller.dto.EncerrarPedidoDto;
import compass.microservice.biblioteca.controller.dto.LivroDto;
import compass.microservice.biblioteca.controller.dto.RequestPedirLivroDto;
import compass.microservice.biblioteca.controller.dto.RequestTesteDTO;
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

	//recebe e manda requests para o  usuario

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


	//Manda
	public ResponseEntity<?> encerrarPedido(Long id) {

		Optional<Registro> optRegistro = rRepo.findById(id);

		if(optRegistro.isEmpty()) {
			return ResponseEntity.badRequest().body("Este registro não existe");
		}

		Registro registro = optRegistro.get();
		List<Livro> livros = registro.getLivros();

		for(Livro l: livros) {
			l.setRegistro(null);
			l.setStatusLivro(StatusLivro.DISPONIVEL);
			lRepo.save(l);
		}

		registro.setStatusRegistro(StatusRegistro.FINALIZADO);

		EncerrarPedidoDto encPedido = new EncerrarPedidoDto(registro.getIdUsuario(), registro.getId(),"encerrado");

		boolean b = uClient.encerrarRegistro(encPedido);

		if(b) {
			return ResponseEntity.ok(encPedido);
		}else {
			return ResponseEntity.ok("erro");
		}


	}



	//Recebe (da BibliotecaServiceController que recebe do  Usuario)
	public ResponseEntity<RequestTesteDTO> teste(RequestTesteForm form) {

		String mensagemRetorno = form.getNome() + "---OK--- mensagem  processada pela  biblioteca";
		RequestTesteDTO teste = new RequestTesteDTO(form.getNome(), form.getMensagem(), mensagemRetorno);

		return ResponseEntity.ok(teste);
	}




	public ResponseEntity<RequestPedirLivroDto> pedirLivros(RequestPedirLivros form) {

		RequestPedirLivroDto request = new RequestPedirLivroDto();

		Optional<Biblioteca> opBiblio = bRepo.findById(form.getIdBiblioteca());

		Optional<Registro> optRegistro = rRepo.findByIdUsuario(form.getIdUser());

		if(opBiblio.isEmpty()) {
			request.setStatus("Biblioteca não existe");
			return  ResponseEntity.ok(request);

		}else {

			if(optRegistro.isPresent()) {
				if(optRegistro.get().getBiblioteca().getId() == opBiblio.get().getId()) {
					request.setStatus("O usuário já tem um pedido em andamento nesta biblioteca");
					return ResponseEntity.ok(request);
				}
			}

			List<Long> livros = form.getIdLivros();
			List<Long> livrosDisponiveis = new ArrayList<Long>();
			List<Long> livrosIndisponiveis = new ArrayList<Long>();
			List<Livro> livrosPedido = new ArrayList<Livro>();

			for (Long id : livros) {

				Optional<Livro> livro = lRepo.findById(id);

				if(livro.isPresent() && livro.get().getStatusLivro().equals(StatusLivro.DISPONIVEL)) {
					livrosPedido.add(livro.get());
					livrosDisponiveis.add(livro.get().getId());

				}else{
					livrosIndisponiveis.add(id);
				}
			}

			request.setBiblioteca(opBiblio.get().getNome());
			request.setLivrosDisponiveis(livrosDisponiveis);
			request.setLivriosIndisponiveis(livrosIndisponiveis);

			if (livrosIndisponiveis.size() > 0) {
				request.setStatus("Alguns dos livros que você pediu não estão disponíveis");
				return ResponseEntity.ok(request);

			}else {
				Registro registro = new Registro(
						form.getIdUser(),
						livrosPedido,
						opBiblio.get());

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




	public BibliotecaDto listarLivros(ReceberEnderecoUsuario form) throws Exception {
		List<Biblioteca> bibliotecas = bRepo.findAll();
		Localizacao loc = new Localizacao();
		Biblioteca biblioteca = loc.procurarBibliotecaMaisProxima(form, bibliotecas);
		return new BibliotecaDto(biblioteca);
	}



}

