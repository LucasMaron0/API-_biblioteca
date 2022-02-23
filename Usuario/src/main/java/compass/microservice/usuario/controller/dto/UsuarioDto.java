package compass.microservice.usuario.controller.dto;

import org.springframework.data.domain.Page;

import compass.microservice.usuario.modelo.Estado;
import compass.microservice.usuario.modelo.Usuario;

public class UsuarioDto {


	private Long id;
	
	private String nome;

	private Estado estado;

	private String cidade;

	private String bairro;

	private String rua;

	private int numero;


	public UsuarioDto() {
		
	}


	public UsuarioDto(Usuario usuario) {
		
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.estado = usuario.getEndereco().getEstado();
		this.cidade = usuario.getEndereco().getCidade();
		this.bairro = usuario.getEndereco().getBairro();
		this.rua= usuario.getEndereco().getRua();
		this.numero = usuario.getEndereco().getNumero();

	}

	

	public static Page<UsuarioDto> converter(Page <Usuario> usuarios){
		return usuarios.map(UsuarioDto::new);

	}




	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Estado getEstado() {
		return estado;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
	


}
