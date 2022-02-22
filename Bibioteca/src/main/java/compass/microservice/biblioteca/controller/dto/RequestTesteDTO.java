package compass.microservice.biblioteca.controller.dto;

public class RequestTesteDTO {
	
	
	private String nome;
	private String mensagem;
	private String status;
	
	

	

	public RequestTesteDTO(String nome, String mensagem, String status) {
		super();
		this.nome = nome;
		this.mensagem = mensagem;
		this.status = status;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
