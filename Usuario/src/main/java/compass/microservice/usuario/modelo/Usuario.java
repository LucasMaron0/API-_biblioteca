package compass.microservice.usuario.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
public class Usuario {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	@OneToOne(mappedBy = "usuario",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Endereco endereco;
	
	private int numeroDePedidos;


	private String nome;


	public Usuario () {

	}
	
	
	public Usuario(String nome) {
		this.nome = nome;
	}


	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Endereco getEndereco() {
		return endereco;
	}


	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public int getNumeroDePedidos() {
		return numeroDePedidos;
	}


	public void setNumeroDePedidos(int numeroDePedidos) {
		this.numeroDePedidos = numeroDePedidos;
	}


	
	
	








}







