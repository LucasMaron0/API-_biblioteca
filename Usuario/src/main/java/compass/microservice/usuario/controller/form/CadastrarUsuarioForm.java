package compass.microservice.usuario.controller.form;

import compass.microservice.usuario.modelo.Endereco;
import compass.microservice.usuario.modelo.Usuario;

public class CadastrarUsuarioForm {



	private String nome;

	private String estado;

	private String cidade;

	private String bairro;

	private String rua;

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
