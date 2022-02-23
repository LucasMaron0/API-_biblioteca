package compass.microservice.usuario.controller.dto;

import java.time.LocalDate;

import org.springframework.data.domain.Page;



public class LivroDto {

	private Long id;
	private String nome;
	private String autor;
	private String categoria;
	private String editora;
	private LocalDate lançamento;

	private String biblioteca;





	public LivroDto() {
		super();
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

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public LocalDate getLançamento() {
		return lançamento;
	}

	public void setLançamento(LocalDate lançamento) {
		this.lançamento = lançamento;
	}

	public String getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(String biblioteca) {
		this.biblioteca = biblioteca;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	





}
