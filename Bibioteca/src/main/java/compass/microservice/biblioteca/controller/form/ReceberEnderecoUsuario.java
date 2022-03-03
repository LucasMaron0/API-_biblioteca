package compass.microservice.biblioteca.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ReceberEnderecoUsuario {
	
	@NotNull @NotEmpty
	private Long userId;
	
	@NotEmpty @NotNull @Length(message = "Inserir sigla do estado", max = 2)
	private String estado;
	
	@NotNull @NotEmpty
	private String cidade;
	
	@NotNull @NotEmpty
	private String bairro;
	
	@NotNull @NotEmpty
	private String rua;
	
	@NotEmpty @NotNull @Length(message = "Inserir, ao menos, '0'", min = 1)
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
