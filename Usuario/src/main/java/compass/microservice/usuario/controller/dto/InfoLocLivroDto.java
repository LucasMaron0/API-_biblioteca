package compass.microservice.usuario.controller.dto;

import java.util.List;

public class InfoLocLivroDto {

	
	private Long idLivro;
	
	private String nomeLivro;

	private String nomeBiblioteca;

	private String endBiblioteca;

	public String getNomeLivro() {
		return nomeLivro;
	}

	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}

	public String getNomeBiblioteca() {
		return nomeBiblioteca;
	}

	public void setNomeBiblioteca(String nomeBiblioteca) {
		this.nomeBiblioteca = nomeBiblioteca;
	}

	public String getEndBiblioteca() {
		return endBiblioteca;
	}

	public void setEndBiblioteca(String endBiblioteca) {
		this.endBiblioteca = endBiblioteca;
	}

	public Long getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(Long idLivro) {
		this.idLivro = idLivro;
	}

	

}

