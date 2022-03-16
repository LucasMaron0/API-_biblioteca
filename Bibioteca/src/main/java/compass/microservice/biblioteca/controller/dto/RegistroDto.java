package compass.microservice.biblioteca.controller.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import compass.microservice.biblioteca.modelos.Livro;
import compass.microservice.biblioteca.modelos.Registro;
import compass.microservice.biblioteca.modelos.StatusRegistro;

public class RegistroDto {

	private Long id;

	private Long idUsuario;
	
	private Long idBiblioteca;

	private StatusRegistro statusRegistro;

	private LocalDate dataLocacao;

	private LocalDate dataVencimento;

	private List<String> livros;

	private double multaGerada = 0.0;



	public RegistroDto() {

	}

	public RegistroDto(Registro registro) {
		this.id = registro.getId();
		this.idUsuario = registro.getIdUsuario();
		this.idBiblioteca = registro.getBiblioteca().getId();
		this.statusRegistro = registro.getStatusRegistro();
		this.dataLocacao = registro.getDataLocacao();
		this.dataVencimento = registro.getDataVencimento();
		this.multaGerada = registro.getMultaGerada();
		
		List<String> nomes = new ArrayList<>();
		for(Livro l:registro.getLivros()) {
			nomes.add(l.getNome());
		}
		
		this.livros = nomes;
		
	}

	public static Page<RegistroDto> converter(Page<Registro> registros) {
		return registros.map(RegistroDto::new);
	}

	public static Registro convert(RegistroDto registroDto) {
		Registro registro = new Registro();
		registro.setId(registroDto.getId());
		registro.setIdUsuario(registroDto.getIdUsuario());
		registro.setStatusRegistro(registroDto.getStatusRegistro());
		registro.setDataLocacao(registroDto.getDataLocacao());
		registro.setDataVencimento(registroDto.getDataVencimento());
		
		
		
		return registro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public StatusRegistro getStatusRegistro() {
		return statusRegistro;
	}

	public void setStatusRegistro(StatusRegistro statusRegistro) {
		this.statusRegistro = statusRegistro;
	}

	public LocalDate getDataLocacao() {
		return dataLocacao;
	}

	public void setDataLocacao(LocalDate dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public double getMultaGerada() {
		return multaGerada;
	}

	public void setMultaGerada(double multaGerada) {
		this.multaGerada = multaGerada;
	}

	public List<String> getLivros() {
		return livros;
	}

	public void setLivros(List<String> livros) {
		this.livros = livros;
	}

	public Long getIdBiblioteca() {
		return idBiblioteca;
	}

	public void setIdBiblioteca(Long idBiblioteca) {
		this.idBiblioteca = idBiblioteca;
	}

	




}
