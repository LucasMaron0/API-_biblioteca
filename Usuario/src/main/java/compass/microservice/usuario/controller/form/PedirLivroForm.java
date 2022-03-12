package compass.microservice.usuario.controller.form;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PedirLivroForm {

	@NotNull(message = "Escolher um Usuário válido")
	private Long idUser;

	@NotNull(message = "Escolher uma Biblioteca válida")
	private Long idBiblioteca;

	@Size(min = 1, message = "Escolha pelo menos 1 Livro")
	private List<Long> idLivros;

	public PedirLivroForm() {

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
