package compass.microservice.biblioteca.controller.form;

import java.time.LocalDate;

import compass.microservice.biblioteca.modelos.Biblioteca;
import compass.microservice.biblioteca.modelos.Categoria;
import compass.microservice.biblioteca.modelos.Livro;

public class AtualizarLivroForm {
	
	
	
	private String nome;
	
	private String autor;
	
		
	private Categoria categoria;
	
	
	private String editora;
	
	private LocalDate lançamento;
	
	
	
	
	public Livro converter (Biblioteca biblioteca) {
        return new Livro (nome, autor, categoria, editora, lançamento, biblioteca);
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
