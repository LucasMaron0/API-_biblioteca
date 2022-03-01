package compass.microservice.usuario.controller.form;

import java.util.List;

public class BuscarNomeLivrosForm {
	
	private List<String> nomeLivros;

	
	public BuscarNomeLivrosForm() {
		
	}
	
	public List<String> getNomeLivros() {
		return nomeLivros;
	}

	public void setNomeLivros(List<String> nomeLivros) {
		this.nomeLivros = nomeLivros;
	}

}
