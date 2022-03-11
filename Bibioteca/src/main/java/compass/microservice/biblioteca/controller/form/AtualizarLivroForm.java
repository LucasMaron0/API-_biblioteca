package compass.microservice.biblioteca.controller.form;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import compass.microservice.biblioteca.modelos.Biblioteca;
import compass.microservice.biblioteca.modelos.Categoria;
import compass.microservice.biblioteca.modelos.Livro;

public class AtualizarLivroForm {

	@NotBlank(message = "Inserir o nome do Livro")
	private String nome;

	@NotBlank(message = "Inserir o nome do Autor")
	@Pattern(regexp = "^[A-Za-z ]*$", message = "Digite um nome válido (apenas letras)")
	private String autor;

	@NotNull(message = "Escolher uma Categoria dentre as listadas")
	private Categoria categoria;

	@NotBlank(message = "Inserir o nome da Editora")
	private String editora;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@NotNull(message = "Digite uma data")
	private LocalDate lançamento;

	public Livro converter(Biblioteca biblioteca) {
		return new Livro(nome, autor, categoria, editora, lançamento, biblioteca);
	}

	public Livro atualizar(Livro l, Biblioteca b) {

		l.setBiblioteca(b);
		l.setAutor(autor);
		l.setNome(nome);
		l.setCategoria(categoria);
		l.setEditora(editora);
		l.setLancamento(lançamento);

		return l;

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
		return lançamento;
	}

	public void setLançamento(LocalDate lançamento) {
		this.lançamento = lançamento;
	}

}
