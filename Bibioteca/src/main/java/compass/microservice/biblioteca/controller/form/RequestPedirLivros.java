package compass.microservice.biblioteca.controller.form;

import java.util.List;

public class RequestPedirLivros {

	private Long idUser;
	private Long idBiblioteca;
	private List<Long> idLivros;

	public RequestPedirLivros() {

	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdBiblioteca() {
		return idBiblioteca;
	}

	public void setIdBiblioteca(Long idBiblioteca) {
		this.idBiblioteca = idBiblioteca;
	}

	public List<Long> getIdLivros() {
		return idLivros;
	}

	public void setIdLivros(List<Long> idLivros) {
		this.idLivros = idLivros;
	}

}
