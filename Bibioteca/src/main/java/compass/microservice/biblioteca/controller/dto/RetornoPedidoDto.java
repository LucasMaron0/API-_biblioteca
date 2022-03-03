package compass.microservice.biblioteca.controller.dto;

import java.util.List;

public class RetornoPedidoDto {


	private String status;

	private String biblioteca;

	private List<Long> livrosDisponiveis;

	private List<Long> livriosIndisponiveis;


	public RetornoPedidoDto() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getBiblioteca() {
		return biblioteca;
	}

	public void setBiblioteca(String biblioteca) {
		this.biblioteca = biblioteca;
	}

	public List<Long> getLivrosDisponiveis() {
		return livrosDisponiveis;
	}

	public void setLivrosDisponiveis(List<Long> livrosDisponiveis) {
		this.livrosDisponiveis = livrosDisponiveis;
	}

	public List<Long> getLivriosIndisponiveis() {
		return livriosIndisponiveis;
	}

	public void setLivriosIndisponiveis(List<Long> livriosIndisponiveis) {
		this.livriosIndisponiveis = livriosIndisponiveis;
	}









}