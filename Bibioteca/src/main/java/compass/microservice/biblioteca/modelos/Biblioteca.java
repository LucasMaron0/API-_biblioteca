package compass.microservice.biblioteca.modelos;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;




@Entity
public class Biblioteca {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String nome;


	@OneToMany(
			mappedBy = "biblioteca", 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Livro> livros;
	
	
	
	@OneToMany(
			mappedBy = "biblioteca", 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Registro> registro;

	@OneToOne(mappedBy = "biblioteca",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Endereco endereco;
	
	


	public Biblioteca () {

	}
	
	

	public Biblioteca(String nome) {
		super();
		this.nome = nome;
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

	public List<Livro> getLivros() {
		return livros;
	}


	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}


	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}



	public List<Registro> getRegistro() {
		return registro;
	}



	public void setRegistro(List<Registro> registro) {
		this.registro = registro;
	}



	


	








}
