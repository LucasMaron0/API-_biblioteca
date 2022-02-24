package compass.microservice.biblioteca.modelos;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Livro {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name= "biblioteca_id")	
	private Biblioteca biblioteca;
	

	@ManyToOne
	@JoinColumn(name= "registro_id")
	private Registro registro;
	
	
	private String nome;
	
	private String autor;
	
	@Enumerated(EnumType.STRING)
	private StatusLivro statusLivro = StatusLivro.DISPONIVEL;
	
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	private String editora;
	
	private LocalDate lancamento;
	
		
	
	public Livro () {
		
	}
	

	public Livro(String nome, String autor, Categoria categoria, String editora, LocalDate lancamento, Biblioteca biblioteca) {
		
		this.biblioteca = biblioteca;
		this.nome = nome;
		this.autor = autor;
		this.categoria = categoria;
		this.editora = editora;
		this.lancamento = lancamento;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}




	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public String getEditora() {
		return editora;
	}


	public void setEditora(String editora) {
		this.editora = editora;
	}


	public LocalDate getLancamento() {
		return lancamento;
	}


	public void setLancamento(LocalDate lancamento) {
		this.lancamento = lancamento;
	}


	public Biblioteca getBiblioteca() {
		return biblioteca;
	}


	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}


	public Registro getRegistro() {
		return registro;
	}


	public void setRegistro(Registro registro) {
		this.registro = registro;
	}


	public StatusLivro getStatusLivro() {
		return statusLivro;
	}


	public void setStatusLivro(StatusLivro statusLivro) {
		this.statusLivro = statusLivro;
	}
	
	
	
	
	
	
	
	
	

}
