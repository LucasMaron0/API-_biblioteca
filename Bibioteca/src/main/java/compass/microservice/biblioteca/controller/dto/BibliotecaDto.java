package compass.microservice.biblioteca.controller.dto;

import org.springframework.data.domain.Page;

import compass.microservice.biblioteca.modelos.Biblioteca;
import compass.microservice.biblioteca.modelos.Estado;


public class BibliotecaDto {


	private Long id;

	private String nome;

	private Estado estado;

	private String cidade;

	private String bairro;

	private String rua;

	private int numero;



	public BibliotecaDto() {

	}


	public BibliotecaDto(Biblioteca biblioteca) {

		this.id = biblioteca.getId();
		this.nome = biblioteca.getNome();
		this.estado = biblioteca.getEndereco().getEstado();
		this.cidade = biblioteca.getEndereco().getCidade();
		this.bairro = biblioteca.getEndereco().getBairro();
		this.rua= biblioteca.getEndereco().getRua();
		this.numero = biblioteca.getEndereco().getNumero();

	}



	public static Page<BibliotecaDto> converter(Page <Biblioteca> usuarios){
		return usuarios.map(BibliotecaDto::new);

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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
	






}
