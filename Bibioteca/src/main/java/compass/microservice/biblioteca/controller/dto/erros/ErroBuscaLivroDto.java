package compass.microservice.biblioteca.controller.dto.erros;

public class ErroBuscaLivroDto {

	private String nomeLivro;
	private String status;
	
	public ErroBuscaLivroDto(String nome, String status) {
		this.nomeLivro = nome;
		this.status = status;
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
