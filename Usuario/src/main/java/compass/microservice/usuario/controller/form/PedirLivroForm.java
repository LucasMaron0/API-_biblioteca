package compass.microservice.usuario.controller.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PedirLivroForm {
	
	
	private Long idUser;
	
	
	private Long idBiblioteca;
	
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
