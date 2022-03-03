package compass.microservice.usuario.controller.dto;

public class ErroBuscaLivroDto {


	private String nomeLivro;
	private String status;

	public ErroBuscaLivroDto() {

	}

	public String getNomeLivro() {
		return nomeLivro;
	}
	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
