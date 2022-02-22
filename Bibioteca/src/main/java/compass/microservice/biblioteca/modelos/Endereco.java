package compass.microservice.biblioteca.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;



@Entity
public class Endereco {


	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	@OneToOne()
	@JoinColumn(name = "biblioteca_id", referencedColumnName = "id")
	private Biblioteca biblioteca;


	private String estado;

	private String cidade;

	private String bairro;

	private String rua;

	private int numero;

	

	public Endereco() {
		
	}

	

	public Endereco( Biblioteca biblioteca, String estado, String cidade, String bairro, String rua,int numero) {
	
		this.biblioteca = biblioteca;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.rua = rua;
		this.numero = numero;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public Biblioteca getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
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
