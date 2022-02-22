package compass.microservice.biblioteca.controller.dto.erros;

public class ErroCadastrarLivro {
	
	
	private String mensagem;
	
	
	

	public ErroCadastrarLivro(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	
	
	
}
