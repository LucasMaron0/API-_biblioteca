package compass.microservice.biblioteca.controller.dto;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import compass.microservice.biblioteca.modelos.Categoria;
import compass.microservice.biblioteca.modelos.Livro;

public class LivroDto {

	private Long id;
	private String nome;
	private String autor;
	private Categoria categoria;
	private String editora;
	private LocalDate lancamento;

	private String biblioteca;

	public LivroDto(Livro livro) {

		this.id= livro.getId();
		this.nome= livro.getNome();
		this.autor = livro.getAutor();
		this.categoria = livro.getCategoria();
		this.editora = livro.getEditora();
		this.lancamento= livro.getLancamento();
		this.biblioteca = livro.getBiblioteca().getNome();

	}
	
	
	public static Page<LivroDto> converter(Page <Livro> livros){
		return livros.map(LivroDto::new);

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

	public LocalDate getLançamento() {
		return lancamento;
	}

	public void setLançamento(LocalDate lançamento) {
		this.lancamento = lançamento;
	}

	public String getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(String biblioteca) {
		this.biblioteca = biblioteca;
	}

	


	
	



}
