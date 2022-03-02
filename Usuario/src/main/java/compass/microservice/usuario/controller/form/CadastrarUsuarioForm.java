package compass.microservice.usuario.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import compass.microservice.usuario.modelo.Endereco;
import compass.microservice.usuario.modelo.Estado;
import compass.microservice.usuario.modelo.Usuario;

public class CadastrarUsuarioForm {


	@NotEmpty @NotNull
	private String nome;
	
	@NotEmpty @NotNull @Length(message = "Inserir sigla do estado", max = 2)
	private Estado estado;
	
	@NotEmpty @NotNull
	private String cidade;
	
	@NotEmpty @NotNull
	private String bairro;
	
	@NotEmpty @NotNull
	private String rua;
	
	@NotEmpty @NotNull @Length(message = "Inserir, ao menos, '0'", min = 1)
	private int numero;




	public CadastrarUsuarioForm () {

	}


	public Usuario converter() {

		Usuario usuario = new Usuario(nome);
		Endereco end = new Endereco( usuario ,estado, cidade , bairro , rua , numero);
		usuario.setEndereco(end);


		return usuario;
	}


	public Usuario atualizar(Usuario u) {

		u.setNome(this.nome);
		Endereco end = new Endereco(
				u,
				this.estado,
				this.cidade,
				this.bairro,
				this.rua,
				this.numero);
		u.setEndereco(end);


		return u;
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


	public Estado getEstado() {
		return estado;
	}


	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	








}
