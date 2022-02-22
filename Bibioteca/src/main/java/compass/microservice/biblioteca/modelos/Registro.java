package compass.microservice.biblioteca.modelos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Registro {
	
	
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    private Long idUsuario;
	
	@OneToMany(
			mappedBy = "registro", 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Livro> livros;
	
	
	@ManyToOne
	@JoinColumn(name= "biblioteca_id")	
	private Biblioteca biblioteca;
	
	@Enumerated(EnumType.STRING)
	private StatusRegistro statusRegistro;
	
	
	private LocalDate dataLocacao;
	
	private LocalDate dataVencimento;
	
	
	public Registro () {
		
	}
	
	
	

	public Registro(Long idUser, List<Livro> livrosPedido, Biblioteca biblioteca) {
		
		this.idUsuario = idUser;
		this.livros = livrosPedido;
		this.biblioteca= biblioteca;
		this.statusRegistro = StatusRegistro.EM_ANDAMENTO;
		this.dataLocacao = LocalDate.now();
		this.dataVencimento = dataLocacao.plus(7, ChronoUnit.DAYS);
		
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

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
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

	public Biblioteca getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}

	public StatusRegistro getStatusRegistro() {
		return statusRegistro;
	}

	public void setStatusRegistro(StatusRegistro statusRegistro) {
		this.statusRegistro = statusRegistro;
	}
	
	
	
	

}
