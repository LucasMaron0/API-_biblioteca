package compass.microservice.usuario.controller.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import compass.microservice.usuario.modelo.Endereco;

public class BuscarLivroProximoForm {
	
	@NotEmpty @NotNull
	private Long userId;
	
	private List<String> nomeLivros;
	
	@NotEmpty @NotNull @Length(message = "Inserir sigla do estado", max = 2)
	private String estado;
	
	@NotEmpty @NotNull
	private String cidade;
	
	@NotEmpty @NotNull
	private String bairro;
	
	@NotEmpty @NotNull
	private String rua;
	
	@NotEmpty @NotNull @Length(message = "Inserir, ao menos, '0'", min = 1)
	private int numero;
	
	public BuscarLivroProximoForm() {
		
	}
	
	public BuscarLivroProximoForm(long id, Endereco endereco, List<String> nomeLivros) {

		this.userId = id;
		this.nomeLivros = nomeLivros;
		this.estado= endereco.getEstado().toString();
		this.cidade=endereco.getCidade();
		this.bairro=endereco.getBairro();
		this.rua= endereco.getRua();
		this.numero = endereco.getNumero();


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


	public List<String> getNomeLivros() {
		return nomeLivros;
	}


	public void setNomeLivros(List<String> nomeLivros) {
		this.nomeLivros = nomeLivros;
	}

	
	


}
