package compass.microservice.biblioteca.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ReceberEnderecoUsuario {
	
	
	private Long userId;
	
	
	private String estado;
	
	
	private String cidade;
	
	
	private String bairro;
	
	
	private String rua;
	
	
	private int numero;

	public ReceberEnderecoUsuario() {
	}

	public ReceberEnderecoUsuario(BuscarLivroProximoForm form) {
		this.userId = form.getUserId();
		this.estado = form.getEstado();
		this.cidade = form.getCidade();
		this.bairro = form.getBairro();
		this.rua = form.getRua();
		this.numero = form.getNumero();
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
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
