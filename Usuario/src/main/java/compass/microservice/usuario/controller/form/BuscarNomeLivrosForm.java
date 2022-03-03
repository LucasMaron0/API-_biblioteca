package compass.microservice.usuario.controller.form;

import java.util.List;

public class BuscarNomeLivrosForm {

	private List<String> nomeLivros;

	private boolean mostrarIndisponiveis;


	public BuscarNomeLivrosForm() {

	}


	public boolean isMostrarIndisponiveis() {
		return mostrarIndisponiveis;
	}

	public void setMostrarIndisponiveis(boolean mostrarIndisponiveis) {
		this.mostrarIndisponiveis = mostrarIndisponiveis;
	}

	public List<String> getNomeLivros() {
		return nomeLivros;
	}

	public void setNomeLivros(List<String> nomeLivros) {
		this.nomeLivros = nomeLivros;
	}

}
